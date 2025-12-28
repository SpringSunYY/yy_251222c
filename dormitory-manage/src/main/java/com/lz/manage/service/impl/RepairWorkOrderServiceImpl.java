package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.bean.BeanValidators;
import com.lz.common.utils.spring.SpringUtils;
import com.lz.manage.mapper.RepairWorkOrderMapper;
import com.lz.manage.model.domain.Repair;
import com.lz.manage.model.domain.RepairWorkOrder;
import com.lz.manage.model.dto.repairWorkOrder.RepairWorkOrderQuery;
import com.lz.manage.model.enums.RepairStatusEnum;
import com.lz.manage.model.enums.WorkOrderStatusEnum;
import com.lz.manage.model.vo.repairWorkOrder.RepairWorkOrderVo;
import com.lz.manage.service.IRepairService;
import com.lz.manage.service.IRepairWorkOrderService;
import com.lz.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 维修工单Service业务层处理
 *
 * @author YY
 * @date 2025-12-27
 */
@Service
public class RepairWorkOrderServiceImpl extends ServiceImpl<RepairWorkOrderMapper, RepairWorkOrder> implements IRepairWorkOrderService {
    private static final Logger log = LoggerFactory.getLogger(RepairWorkOrderServiceImpl.class);

    /**
     * 导入用户数据校验器
     */
    private static Validator validator;

    @Resource
    private RepairWorkOrderMapper repairWorkOrderMapper;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IRepairService repairService;

    {
        validator = SpringUtils.getBean(Validator.class);
    }

    //region mybatis代码

    /**
     * 查询维修工单
     *
     * @param id 维修工单主键
     * @return 维修工单
     */
    @Override
    public RepairWorkOrder selectRepairWorkOrderById(Long id) {
        return repairWorkOrderMapper.selectRepairWorkOrderById(id);
    }

    /**
     * 查询维修工单列表
     *
     * @param repairWorkOrder 维修工单
     * @return 维修工单
     */
    @Override
    public List<RepairWorkOrder> selectRepairWorkOrderList(RepairWorkOrder repairWorkOrder) {
        //如果没有查看全部
        if (SecurityUtils.hasPermi("manage:repairWorkOrder:all")) {
            repairWorkOrder.setDealWithId(SecurityUtils.getUserId());
        }
        List<RepairWorkOrder> repairWorkOrders = repairWorkOrderMapper.selectRepairWorkOrderList(repairWorkOrder);
        for (RepairWorkOrder info : repairWorkOrders) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
            SysUser dealWithUser = sysUserService.selectUserById(info.getDealWithId());
            if (StringUtils.isNotNull(dealWithUser)) {
                info.setDealWithName(dealWithUser.getUserName());
            }
        }
        return repairWorkOrders;
    }

    /**
     * 新增维修工单
     *
     * @param repairWorkOrder 维修工单
     * @return 结果
     */
    @Override
    public int insertRepairWorkOrder(RepairWorkOrder repairWorkOrder) {
        Repair repair = checkWorkOrder(repairWorkOrder);
        repairWorkOrder.setStatus(WorkOrderStatusEnum.WORK_ORDER_STATUS_0.getValue());
        repairWorkOrder.setUserId(SecurityUtils.getUserId());
        repairWorkOrder.setCreateTime(DateUtils.getNowDate());
        repairService.updateRepair(repair);
        return repairWorkOrderMapper.insertRepairWorkOrder(repairWorkOrder);
    }

    private Repair checkWorkOrder(RepairWorkOrder repairWorkOrder) {
        //查询维修信息
        Repair repair = repairService.selectRepairById(repairWorkOrder.getRepairId());
        if (StringUtils.isNull(repair)) {
            throw new ServiceException("报修信息不存在");
        }
        if (repair.getStatus().equals(RepairStatusEnum.REPAIR_STATUS_2.getValue())) {
            throw new ServiceException("该报修单已处理");
        }
        //更新维修单处理人
        repair.setDealWithId(repairWorkOrder.getDealWithId());
        return repair;
    }

    /**
     * 修改维修工单
     *
     * @param repairWorkOrder 维修工单
     * @return 结果
     */
    @Override
    public int updateRepairWorkOrder(RepairWorkOrder repairWorkOrder) {
        checkWorkOrder(repairWorkOrder);
        //查询数据是否已完成
        RepairWorkOrder orderDb = this.selectRepairWorkOrderById(repairWorkOrder.getId());
        if (StringUtils.isNull(orderDb)) {
            throw new ServiceException("数据不存在");
        }
        if (orderDb.getStatus().equals(WorkOrderStatusEnum.WORK_ORDER_STATUS_1.getValue())) {
            throw new ServiceException("该工单已处理");
        }
        //如果传过来的是完成
        Date nowDate = DateUtils.getNowDate();
        if (repairWorkOrder.getStatus().equals(WorkOrderStatusEnum.WORK_ORDER_STATUS_1.getValue())) {
            //拿到工单
            Repair repair = repairService.selectRepairById(orderDb.getRepairId());
            repair.setStatus(RepairStatusEnum.REPAIR_STATUS_2.getValue());
            repair.setCompletedTime(nowDate);
            repair.setDealWithCost(repairWorkOrder.getDealWithCost());
            repairService.updateRepair(repair);
            repairWorkOrder.setCompletedTime(nowDate);
        }
        repairWorkOrder.setUpdateTime(nowDate);
        return repairWorkOrderMapper.updateRepairWorkOrder(repairWorkOrder);
    }

    /**
     * 批量删除维修工单
     *
     * @param ids 需要删除的维修工单主键
     * @return 结果
     */
    @Override
    public int deleteRepairWorkOrderByIds(Long[] ids) {
        return repairWorkOrderMapper.deleteRepairWorkOrderByIds(ids);
    }

    /**
     * 删除维修工单信息
     *
     * @param id 维修工单主键
     * @return 结果
     */
    @Override
    public int deleteRepairWorkOrderById(Long id) {
        return repairWorkOrderMapper.deleteRepairWorkOrderById(id);
    }

    //endregion
    @Override
    public QueryWrapper<RepairWorkOrder> getQueryWrapper(RepairWorkOrderQuery repairWorkOrderQuery) {
        QueryWrapper<RepairWorkOrder> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = repairWorkOrderQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = repairWorkOrderQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long repairId = repairWorkOrderQuery.getRepairId();
        queryWrapper.eq(StringUtils.isNotNull(repairId), "repair_id", repairId);

        String status = repairWorkOrderQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        Date createTime = repairWorkOrderQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<RepairWorkOrderVo> convertVoList(List<RepairWorkOrder> repairWorkOrderList) {
        if (StringUtils.isEmpty(repairWorkOrderList)) {
            return Collections.emptyList();
        }
        return repairWorkOrderList.stream().map(RepairWorkOrderVo::objToVo).collect(Collectors.toList());
    }

    /**
     * 导入维修工单数据
     *
     * @param repairWorkOrderList 维修工单数据列表
     * @param isUpdateSupport     是否更新支持，如果已存在，则进行更新数据
     * @param operName            操作用户
     * @return 结果
     */
    @Override
    public String importRepairWorkOrderData(List<RepairWorkOrder> repairWorkOrderList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(repairWorkOrderList) || repairWorkOrderList.size() == 0) {
            throw new ServiceException("导入维修工单数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (RepairWorkOrder repairWorkOrder : repairWorkOrderList) {
            try {
                // 验证是否存在这个维修工单
                Long id = repairWorkOrder.getId();
                RepairWorkOrder repairWorkOrderExist = null;
                if (StringUtils.isNotNull(id)) {
                    repairWorkOrderExist = repairWorkOrderMapper.selectRepairWorkOrderById(id);
                }
                if (StringUtils.isNull(repairWorkOrderExist)) {
                    BeanValidators.validateWithException(validator, repairWorkOrder);
                    repairWorkOrder.setCreateTime(DateUtils.getNowDate());
                    repairWorkOrderMapper.insertRepairWorkOrder(repairWorkOrder);
                    successNum++;
                    String idStr = StringUtils.isNotNull(id) ? id.toString() : "新记录";
                    successMsg.append("<br/>" + successNum + "、维修工单 " + idStr + " 导入成功");
                } else if (isUpdateSupport) {
                    BeanValidators.validateWithException(validator, repairWorkOrder);
                    repairWorkOrder.setUpdateTime(DateUtils.getNowDate());
                    repairWorkOrderMapper.updateRepairWorkOrder(repairWorkOrder);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、维修工单 " + id.toString() + " 更新成功");
                } else {
                    failureNum++;
                    String idStr = StringUtils.isNotNull(id) ? id.toString() : "未知";
                    failureMsg.append("<br/>" + failureNum + "、维修工单 " + idStr + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                Long id = repairWorkOrder.getId();
                String idStr = StringUtils.isNotNull(id) ? id.toString() : "未知";
                String msg = "<br/>" + failureNum + "、维修工单 " + idStr + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

}
