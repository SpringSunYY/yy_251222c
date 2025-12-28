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
import com.lz.manage.mapper.DormitoryBedHistoryMapper;
import com.lz.manage.model.domain.Building;
import com.lz.manage.model.domain.Dormitory;
import com.lz.manage.model.domain.DormitoryBedHistory;
import com.lz.manage.model.dto.dormitoryBedHistory.DormitoryBedHistoryQuery;
import com.lz.manage.model.vo.dormitoryBedHistory.DormitoryBedHistoryVo;
import com.lz.manage.service.IBuildingService;
import com.lz.manage.service.IDormitoryBedHistoryService;
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
 * 床位历史Service业务层处理
 *
 * @author YY
 * @date 2025-12-27
 */
@Service
public class DormitoryBedHistoryServiceImpl extends ServiceImpl<DormitoryBedHistoryMapper, DormitoryBedHistory> implements IDormitoryBedHistoryService {
    private static final Logger log = LoggerFactory.getLogger(DormitoryBedHistoryServiceImpl.class);

    /**
     * 导入用户数据校验器
     */
    private static Validator validator;

    @Resource
    private DormitoryBedHistoryMapper dormitoryBedHistoryMapper;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IBuildingService buildingService;

    @Resource
    private IDormitoryService dormitoryService;


    {
        validator = SpringUtils.getBean(Validator.class);
    }

    //region mybatis代码

    /**
     * 查询床位历史
     *
     * @param id 床位历史主键
     * @return 床位历史
     */
    @Override
    public DormitoryBedHistory selectDormitoryBedHistoryById(Long id) {
        return dormitoryBedHistoryMapper.selectDormitoryBedHistoryById(id);
    }

    /**
     * 查询床位历史列表
     *
     * @param dormitoryBedHistory 床位历史
     * @return 床位历史
     */
    @Override
    public List<DormitoryBedHistory> selectDormitoryBedHistoryList(DormitoryBedHistory dormitoryBedHistory) {
        List<DormitoryBedHistory> dormitoryBedHistories = dormitoryBedHistoryMapper.selectDormitoryBedHistoryList(dormitoryBedHistory);
        for (DormitoryBedHistory info : dormitoryBedHistories) {
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
        return dormitoryBedHistories;
    }

    /**
     * 新增床位历史
     *
     * @param dormitoryBedHistory 床位历史
     * @return 结果
     */
    @Override
    public int insertDormitoryBedHistory(DormitoryBedHistory dormitoryBedHistory) {
        dormitoryBedHistory.setUserId(SecurityUtils.getUserId());
        dormitoryBedHistory.setCreateTime(DateUtils.getNowDate());
        return dormitoryBedHistoryMapper.insertDormitoryBedHistory(dormitoryBedHistory);
    }

    /**
     * 修改床位历史
     *
     * @param dormitoryBedHistory 床位历史
     * @return 结果
     */
    @Override
    public int updateDormitoryBedHistory(DormitoryBedHistory dormitoryBedHistory) {
        dormitoryBedHistory.setUpdateTime(DateUtils.getNowDate());
        return dormitoryBedHistoryMapper.updateDormitoryBedHistory(dormitoryBedHistory);
    }

    /**
     * 批量删除床位历史
     *
     * @param ids 需要删除的床位历史主键
     * @return 结果
     */
    @Override
    public int deleteDormitoryBedHistoryByIds(Long[] ids) {
        return dormitoryBedHistoryMapper.deleteDormitoryBedHistoryByIds(ids);
    }

    /**
     * 删除床位历史信息
     *
     * @param id 床位历史主键
     * @return 结果
     */
    @Override
    public int deleteDormitoryBedHistoryById(Long id) {
        return dormitoryBedHistoryMapper.deleteDormitoryBedHistoryById(id);
    }

    //endregion
    @Override
    public QueryWrapper<DormitoryBedHistory> getQueryWrapper(DormitoryBedHistoryQuery dormitoryBedHistoryQuery) {
        QueryWrapper<DormitoryBedHistory> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = dormitoryBedHistoryQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = dormitoryBedHistoryQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long buildingId = dormitoryBedHistoryQuery.getBuildingId();
        queryWrapper.eq(StringUtils.isNotNull(buildingId), "building_id", buildingId);

        Long dormitoryId = dormitoryBedHistoryQuery.getDormitoryId();
        queryWrapper.eq(StringUtils.isNotNull(dormitoryId), "dormitory_id", dormitoryId);

        Date createTime = dormitoryBedHistoryQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<DormitoryBedHistoryVo> convertVoList(List<DormitoryBedHistory> dormitoryBedHistoryList) {
        if (StringUtils.isEmpty(dormitoryBedHistoryList)) {
            return Collections.emptyList();
        }
        return dormitoryBedHistoryList.stream().map(DormitoryBedHistoryVo::objToVo).collect(Collectors.toList());
    }

    /**
     * 导入床位历史数据
     *
     * @param dormitoryBedHistoryList 床位历史数据列表
     * @param isUpdateSupport         是否更新支持，如果已存在，则进行更新数据
     * @param operName                操作用户
     * @return 结果
     */
    @Override
    public String importDormitoryBedHistoryData(List<DormitoryBedHistory> dormitoryBedHistoryList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(dormitoryBedHistoryList) || dormitoryBedHistoryList.size() == 0) {
            throw new ServiceException("导入床位历史数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (DormitoryBedHistory dormitoryBedHistory : dormitoryBedHistoryList) {
            try {
                // 验证是否存在这个床位历史
                Long id = dormitoryBedHistory.getId();
                DormitoryBedHistory dormitoryBedHistoryExist = null;
                if (StringUtils.isNotNull(id)) {
                    dormitoryBedHistoryExist = dormitoryBedHistoryMapper.selectDormitoryBedHistoryById(id);
                }
                if (StringUtils.isNull(dormitoryBedHistoryExist)) {
                    BeanValidators.validateWithException(validator, dormitoryBedHistory);
                    dormitoryBedHistory.setCreateTime(DateUtils.getNowDate());
                    dormitoryBedHistoryMapper.insertDormitoryBedHistory(dormitoryBedHistory);
                    successNum++;
                    String idStr = StringUtils.isNotNull(id) ? id.toString() : "新记录";
                    successMsg.append("<br/>" + successNum + "、床位历史 " + idStr + " 导入成功");
                } else if (isUpdateSupport) {
                    BeanValidators.validateWithException(validator, dormitoryBedHistory);
                    dormitoryBedHistory.setUpdateTime(DateUtils.getNowDate());
                    dormitoryBedHistoryMapper.updateDormitoryBedHistory(dormitoryBedHistory);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、床位历史 " + id.toString() + " 更新成功");
                } else {
                    failureNum++;
                    String idStr = StringUtils.isNotNull(id) ? id.toString() : "未知";
                    failureMsg.append("<br/>" + failureNum + "、床位历史 " + idStr + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                Long id = dormitoryBedHistory.getId();
                String idStr = StringUtils.isNotNull(id) ? id.toString() : "未知";
                String msg = "<br/>" + failureNum + "、床位历史 " + idStr + " 导入失败：";
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
