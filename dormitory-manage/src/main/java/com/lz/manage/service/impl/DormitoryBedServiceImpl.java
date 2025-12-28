package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.bean.BeanUtils;
import com.lz.common.utils.bean.BeanValidators;
import com.lz.common.utils.spring.SpringUtils;
import com.lz.manage.mapper.DormitoryBedMapper;
import com.lz.manage.model.domain.Building;
import com.lz.manage.model.domain.Dormitory;
import com.lz.manage.model.domain.DormitoryBed;
import com.lz.manage.model.domain.DormitoryBedHistory;
import com.lz.manage.model.dto.dormitoryBed.DormitoryBedQuery;
import com.lz.manage.model.enums.DormitoryStatusEnum;
import com.lz.manage.model.vo.dormitoryBed.DormitoryBedVo;
import com.lz.manage.service.IBuildingService;
import com.lz.manage.service.IDormitoryBedHistoryService;
import com.lz.manage.service.IDormitoryBedService;
import com.lz.manage.service.IDormitoryService;
import com.lz.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 宿舍床位Service业务层处理
 *
 * @author YY
 * @date 2025-12-27
 */
@Service
public class DormitoryBedServiceImpl extends ServiceImpl<DormitoryBedMapper, DormitoryBed> implements IDormitoryBedService {
    private static final Logger log = LoggerFactory.getLogger(DormitoryBedServiceImpl.class);

    /**
     * 导入用户数据校验器
     */
    private static Validator validator;

    @Resource
    private DormitoryBedMapper dormitoryBedMapper;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IBuildingService buildingService;

    @Resource
    private IDormitoryService dormitoryService;

    @Resource
    private IDormitoryBedHistoryService dormitoryBedHistoryService;

    {
        validator = SpringUtils.getBean(Validator.class);
    }

    //region mybatis代码

    /**
     * 查询宿舍床位
     *
     * @param id 宿舍床位主键
     * @return 宿舍床位
     */
    @Override
    public DormitoryBed selectDormitoryBedById(Long id) {
        return dormitoryBedMapper.selectDormitoryBedById(id);
    }

    /**
     * 查询宿舍床位列表
     *
     * @param dormitoryBed 宿舍床位
     * @return 宿舍床位
     */
    @Override
    public List<DormitoryBed> selectDormitoryBedList(DormitoryBed dormitoryBed) {
        List<DormitoryBed> dormitoryBeds = dormitoryBedMapper.selectDormitoryBedList(dormitoryBed);
        for (DormitoryBed info : dormitoryBeds) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
            Building building = buildingService.selectBuildingById(info.getBuildingId());
            if (StringUtils.isNotNull(building)) {
                info.setBuildingName(building.getName());
            }
            Dormitory dormitory = dormitoryService.selectDormitoryById(info.getDormitoryId());
            if (StringUtils.isNotNull(dormitory)) {
                info.setDormitoryName(dormitory.getName());
            }

            SysUser belongUser = sysUserService.selectUserById(info.getBelongUserId());
            if (StringUtils.isNotNull(belongUser)) {
                info.setBelongUserName(belongUser.getUserName());
            }
        }
        return dormitoryBeds;
    }

    /**
     * 新增宿舍床位
     *
     * @param dormitoryBed 宿舍床位
     * @return 结果
     */
    @Override
    public int insertDormitoryBed(DormitoryBed dormitoryBed) {
        Dormitory dormitory = checkDormitoryBedAdd(dormitoryBed);
        //如果床位还没大于最大限制
        long count = this.count(new LambdaQueryWrapper<DormitoryBed>()
                .eq(DormitoryBed::getDormitoryId, dormitory.getId()));
        if (count >= dormitory.getPeopleNumberLimit()) {
            throw new ServiceException("该宿舍床位已满,不可添加床位");
        }
        dormitoryBed.setStatus(DormitoryStatusEnum.DORMITORY_STATUS_0.getValue());
        dormitoryBed.setBuildingId(dormitory.getBuildingId());
        dormitoryBed.setUserId(SecurityUtils.getUserId());
        dormitoryBed.setCreateTime(DateUtils.getNowDate());
        return dormitoryBedMapper.insertDormitoryBed(dormitoryBed);
    }

    private Dormitory checkDormitoryBedAdd(DormitoryBed dormitoryBed) {
        //查询宿舍是否存在
        Dormitory dormitory = dormitoryService.selectDormitoryById(dormitoryBed.getDormitoryId());
        if (StringUtils.isNull(dormitory)) {
            throw new ServiceException("宿舍不存在");
        }
        return dormitory;
    }

    /**
     * 修改宿舍床位
     *
     * @param dormitoryBed 宿舍床位
     * @return 结果
     */
    @Override
    public int updateDormitoryBed(DormitoryBed dormitoryBed) {
        dormitoryBed.setUpdateTime(DateUtils.getNowDate());
        return dormitoryBedMapper.updateDormitoryBed(dormitoryBed);
    }

    /**
     * 批量删除宿舍床位
     *
     * @param ids 需要删除的宿舍床位主键
     * @return 结果
     */
    @Override
    public int deleteDormitoryBedByIds(Long[] ids) {
        return dormitoryBedMapper.deleteDormitoryBedByIds(ids);
    }

    /**
     * 删除宿舍床位信息
     *
     * @param id 宿舍床位主键
     * @return 结果
     */
    @Override
    public int deleteDormitoryBedById(Long id) {
        return dormitoryBedMapper.deleteDormitoryBedById(id);
    }

    //endregion
    @Override
    public QueryWrapper<DormitoryBed> getQueryWrapper(DormitoryBedQuery dormitoryBedQuery) {
        QueryWrapper<DormitoryBed> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = dormitoryBedQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = dormitoryBedQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long buildingId = dormitoryBedQuery.getBuildingId();
        queryWrapper.eq(StringUtils.isNotNull(buildingId), "building_id", buildingId);

        Long dormitoryId = dormitoryBedQuery.getDormitoryId();
        queryWrapper.eq(StringUtils.isNotNull(dormitoryId), "dormitory_id", dormitoryId);

        String status = dormitoryBedQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        Date createTime = dormitoryBedQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<DormitoryBedVo> convertVoList(List<DormitoryBed> dormitoryBedList) {
        if (StringUtils.isEmpty(dormitoryBedList)) {
            return Collections.emptyList();
        }
        return dormitoryBedList.stream().map(DormitoryBedVo::objToVo).collect(Collectors.toList());
    }

    /**
     * 导入宿舍床位数据
     *
     * @param dormitoryBedList 宿舍床位数据列表
     * @param isUpdateSupport  是否更新支持，如果已存在，则进行更新数据
     * @param operName         操作用户
     * @return 结果
     */
    @Override
    public String importDormitoryBedData(List<DormitoryBed> dormitoryBedList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(dormitoryBedList) || dormitoryBedList.size() == 0) {
            throw new ServiceException("导入宿舍床位数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (DormitoryBed dormitoryBed : dormitoryBedList) {
            try {
                // 验证是否存在这个宿舍床位
                Long id = dormitoryBed.getId();
                DormitoryBed dormitoryBedExist = null;
                if (StringUtils.isNotNull(id)) {
                    dormitoryBedExist = dormitoryBedMapper.selectDormitoryBedById(id);
                }
                if (StringUtils.isNull(dormitoryBedExist)) {
                    BeanValidators.validateWithException(validator, dormitoryBed);
                    dormitoryBed.setCreateTime(DateUtils.getNowDate());
                    dormitoryBedMapper.insertDormitoryBed(dormitoryBed);
                    successNum++;
                    String idStr = StringUtils.isNotNull(id) ? id.toString() : "新记录";
                    successMsg.append("<br/>" + successNum + "、宿舍床位 " + idStr + " 导入成功");
                } else if (isUpdateSupport) {
                    BeanValidators.validateWithException(validator, dormitoryBed);
                    dormitoryBed.setUpdateTime(DateUtils.getNowDate());
                    dormitoryBedMapper.updateDormitoryBed(dormitoryBed);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、宿舍床位 " + id.toString() + " 更新成功");
                } else {
                    failureNum++;
                    String idStr = StringUtils.isNotNull(id) ? id.toString() : "未知";
                    failureMsg.append("<br/>" + failureNum + "、宿舍床位 " + idStr + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                Long id = dormitoryBed.getId();
                String idStr = StringUtils.isNotNull(id) ? id.toString() : "未知";
                String msg = "<br/>" + failureNum + "、宿舍床位 " + idStr + " 导入失败：";
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

    @Override
    public int allotDormitoryBed(DormitoryBed dormitoryBed) {
        DormitoryBed dormitoryBedExist = dormitoryBedMapper.selectDormitoryBedById(dormitoryBed.getId());
        if (StringUtils.isNull(dormitoryBedExist)) {
            throw new ServiceException("该床位不存在");
        }
        Dormitory dormitory = dormitoryService.selectDormitoryById(dormitoryBed.getDormitoryId());
        if (StringUtils.isNull(dormitory)) {
            throw new ServiceException("该宿舍不存在，请删除脏数据");
        }
        //判断，如果床位状态不是空闲，且传过来所属用户
        if (!dormitoryBedExist.getStatus().equals(DormitoryStatusEnum.DORMITORY_STATUS_0.getValue())
                && StringUtils.isNotNull(dormitoryBed.getBelongUserId())) {
            throw new ServiceException("该床位已使用或已分配，请勿分配，如需分配请先解除分配或待床位正常");
        }
        //如果没有传来用户表示释放
        if (StringUtils.isNull(dormitoryBed.getBelongUserId())) {
            dormitoryBedMapper.update(null,
                    new LambdaUpdateWrapper<DormitoryBed>()
                            .set(DormitoryBed::getBelongUserId, null)
                            .set(DormitoryBed::getStatus, DormitoryStatusEnum.DORMITORY_STATUS_0.getValue())
                            .eq(DormitoryBed::getId, dormitoryBed.getId())
            );
        } else {
            //传过来用户
            dormitoryBed.setStatus(DormitoryStatusEnum.DORMITORY_STATUS_1.getValue());
            //先判断他是否已经有宿舍，如果有则需要更新原宿舍
            DormitoryBed dormitoryBedExistUser = this.getOne(new LambdaQueryWrapper<DormitoryBed>()
                    .eq(DormitoryBed::getBelongUserId, dormitoryBed.getBelongUserId()));
            if (StringUtils.isNotNull(dormitoryBedExistUser)) {
                Dormitory oldDormitory = dormitoryService.selectDormitoryById(dormitoryBedExistUser.getDormitoryId());
                dormitoryBedMapper.update(null,
                        new LambdaUpdateWrapper<DormitoryBed>()
                                .set(DormitoryBed::getBelongUserId, null)
                                .set(DormitoryBed::getStatus, DormitoryStatusEnum.DORMITORY_STATUS_0.getValue())
                                .eq(DormitoryBed::getId, dormitoryBedExistUser.getId()));
                //计算人数
                long count = this.count(new LambdaQueryWrapper<DormitoryBed>()
                        .eq(DormitoryBed::getDormitoryId, dormitoryBedExistUser.getDormitoryId())
                        .isNotNull(DormitoryBed::getBelongUserId));
                oldDormitory.setPeopleNumber(count);
                dormitoryService.updateDormitory(oldDormitory);
            }
        }
        dormitoryBedMapper.updateDormitoryBed(dormitoryBed);
        //如果更新了，则添加历史
        DormitoryBedHistory dormitoryBedHistory = new DormitoryBedHistory();
        BeanUtils.copyProperties(dormitoryBedExist, dormitoryBedHistory);
        dormitoryBedHistoryService.insertDormitoryBedHistory(dormitoryBedHistory);
        //计算人数
        long count = this.count(new LambdaQueryWrapper<DormitoryBed>()
                .eq(DormitoryBed::getDormitoryId, dormitoryBed.getDormitoryId())
                .isNotNull(DormitoryBed::getBelongUserId));
        dormitory.setPeopleNumber(count);
        return dormitoryService.updateDormitory(dormitory);
    }

}
