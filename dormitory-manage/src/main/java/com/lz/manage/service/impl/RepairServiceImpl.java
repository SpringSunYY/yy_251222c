package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lz.common.utils.StringUtils;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.bean.BeanValidators;
import com.lz.common.utils.spring.SpringUtils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.RepairMapper;
import com.lz.manage.model.domain.Repair;
import com.lz.manage.service.IRepairService;
import com.lz.manage.model.dto.repair.RepairQuery;
import com.lz.manage.model.vo.repair.RepairVo;

/**
 * 报修记录Service业务层处理
 *
 * @author YY
 * @date 2025-12-27
 */
@Service
public class RepairServiceImpl extends ServiceImpl<RepairMapper, Repair> implements IRepairService
{
    private static final Logger log = LoggerFactory.getLogger(RepairServiceImpl.class);

    /** 导入用户数据校验器 */
    private static Validator validator;

    @Resource
    private RepairMapper repairMapper;

    {
        validator = SpringUtils.getBean(Validator.class);
    }

    //region mybatis代码
    /**
     * 查询报修记录
     *
     * @param id 报修记录主键
     * @return 报修记录
     */
    @Override
    public Repair selectRepairById(Long id)
    {
        return repairMapper.selectRepairById(id);
    }

    /**
     * 查询报修记录列表
     *
     * @param repair 报修记录
     * @return 报修记录
     */
    @Override
    public List<Repair> selectRepairList(Repair repair)
    {
        return repairMapper.selectRepairList(repair);
    }

    /**
     * 新增报修记录
     *
     * @param repair 报修记录
     * @return 结果
     */
    @Override
    public int insertRepair(Repair repair)
    {
        repair.setCreateTime(DateUtils.getNowDate());
        return repairMapper.insertRepair(repair);
    }

    /**
     * 修改报修记录
     *
     * @param repair 报修记录
     * @return 结果
     */
    @Override
    public int updateRepair(Repair repair)
    {
        repair.setUpdateTime(DateUtils.getNowDate());
        return repairMapper.updateRepair(repair);
    }

    /**
     * 批量删除报修记录
     *
     * @param ids 需要删除的报修记录主键
     * @return 结果
     */
    @Override
    public int deleteRepairByIds(Long[] ids)
    {
        return repairMapper.deleteRepairByIds(ids);
    }

    /**
     * 删除报修记录信息
     *
     * @param id 报修记录主键
     * @return 结果
     */
    @Override
    public int deleteRepairById(Long id)
    {
        return repairMapper.deleteRepairById(id);
    }
    //endregion
    @Override
    public QueryWrapper<Repair> getQueryWrapper(RepairQuery repairQuery){
        QueryWrapper<Repair> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = repairQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = repairQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long buildingId = repairQuery.getBuildingId();
        queryWrapper.eq( StringUtils.isNotNull(buildingId),"building_id",buildingId);

        Long dormitoryId = repairQuery.getDormitoryId();
        queryWrapper.eq( StringUtils.isNotNull(dormitoryId),"dormitory_id",dormitoryId);

        Long bedId = repairQuery.getBedId();
        queryWrapper.eq( StringUtils.isNotNull(bedId),"bed_id",bedId);

        String type = repairQuery.getType();
        queryWrapper.eq(StringUtils.isNotEmpty(type) ,"type",type);

        String abnormalStatus = repairQuery.getAbnormalStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(abnormalStatus) ,"abnormal_status",abnormalStatus);

        String status = repairQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

        String repairAddress = repairQuery.getRepairAddress();
        queryWrapper.eq(StringUtils.isNotEmpty(repairAddress) ,"repair_address",repairAddress);

        Date createTime = repairQuery.getCreateTime();
        queryWrapper.eq( StringUtils.isNotNull(createTime),"create_time",createTime);

        return queryWrapper;
    }

    @Override
    public List<RepairVo> convertVoList(List<Repair> repairList) {
        if (StringUtils.isEmpty(repairList)) {
            return Collections.emptyList();
        }
        return repairList.stream().map(RepairVo::objToVo).collect(Collectors.toList());
    }

    /**
     * 导入报修记录数据
     *
     * @param repairList 报修记录数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importRepairData(List<Repair> repairList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(repairList) || repairList.size() == 0)
        {
            throw new ServiceException("导入报修记录数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (Repair repair : repairList)
        {
            try
            {
                // 验证是否存在这个报修记录
                Long id = repair.getId();
                Repair repairExist = null;
                if (StringUtils.isNotNull(id))
                {
                    repairExist = repairMapper.selectRepairById(id);
                }
                if (StringUtils.isNull(repairExist))
                {
                    BeanValidators.validateWithException(validator, repair);
                    repair.setCreateTime(DateUtils.getNowDate());
                    repairMapper.insertRepair(repair);
                    successNum++;
                    String idStr = StringUtils.isNotNull(id) ? id.toString() : "新记录";
                    successMsg.append("<br/>" + successNum + "、报修记录 " + idStr + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, repair);
                    repair.setUpdateTime(DateUtils.getNowDate());
                    repairMapper.updateRepair(repair);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、报修记录 " + id.toString() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    String idStr = StringUtils.isNotNull(id) ? id.toString() : "未知";
                    failureMsg.append("<br/>" + failureNum + "、报修记录 " + idStr + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                Long id = repair.getId();
                String idStr = StringUtils.isNotNull(id) ? id.toString() : "未知";
                String msg = "<br/>" + failureNum + "、报修记录 " + idStr + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

}
