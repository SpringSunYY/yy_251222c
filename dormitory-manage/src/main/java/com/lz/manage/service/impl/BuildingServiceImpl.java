package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.validation.Validator;

import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.SecurityUtils;
import com.lz.system.service.ISysUserService;
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
import com.lz.manage.mapper.BuildingMapper;
import com.lz.manage.model.domain.Building;
import com.lz.manage.service.IBuildingService;
import com.lz.manage.model.dto.building.BuildingQuery;
import com.lz.manage.model.vo.building.BuildingVo;

/**
 * 楼栋信息Service业务层处理
 *
 * @author YY
 * @date 2025-12-27
 */
@Service
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper, Building> implements IBuildingService
{
    private static final Logger log = LoggerFactory.getLogger(BuildingServiceImpl.class);

    /** 导入用户数据校验器 */
    private static Validator validator;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private BuildingMapper buildingMapper;

    {
        validator = SpringUtils.getBean(Validator.class);
    }

    //region mybatis代码
    /**
     * 查询楼栋信息
     *
     * @param id 楼栋信息主键
     * @return 楼栋信息
     */
    @Override
    public Building selectBuildingById(Long id)
    {
        return buildingMapper.selectBuildingById(id);
    }

    /**
     * 查询楼栋信息列表
     *
     * @param building 楼栋信息
     * @return 楼栋信息
     */
    @Override
    public List<Building> selectBuildingList(Building building)
    {
        List<Building> buildings = buildingMapper.selectBuildingList(building);
        for (Building info : buildings) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
        }
        return buildings;
    }

    /**
     * 新增楼栋信息
     *
     * @param building 楼栋信息
     * @return 结果
     */
    @Override
    public int insertBuilding(Building building)
    {
        building.setUserId(SecurityUtils.getUserId());
        building.setCreateTime(DateUtils.getNowDate());
        return buildingMapper.insertBuilding(building);
    }

    /**
     * 修改楼栋信息
     *
     * @param building 楼栋信息
     * @return 结果
     */
    @Override
    public int updateBuilding(Building building)
    {
        building.setUpdateTime(DateUtils.getNowDate());
        return buildingMapper.updateBuilding(building);
    }

    /**
     * 批量删除楼栋信息
     *
     * @param ids 需要删除的楼栋信息主键
     * @return 结果
     */
    @Override
    public int deleteBuildingByIds(Long[] ids)
    {
        return buildingMapper.deleteBuildingByIds(ids);
    }

    /**
     * 删除楼栋信息信息
     *
     * @param id 楼栋信息主键
     * @return 结果
     */
    @Override
    public int deleteBuildingById(Long id)
    {
        return buildingMapper.deleteBuildingById(id);
    }
    //endregion
    @Override
    public QueryWrapper<Building> getQueryWrapper(BuildingQuery buildingQuery){
        QueryWrapper<Building> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = buildingQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = buildingQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        String name = buildingQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name) ,"name",name);

        Date createTime = buildingQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<BuildingVo> convertVoList(List<Building> buildingList) {
        if (StringUtils.isEmpty(buildingList)) {
            return Collections.emptyList();
        }
        return buildingList.stream().map(BuildingVo::objToVo).collect(Collectors.toList());
    }

    /**
     * 导入楼栋信息数据
     *
     * @param buildingList 楼栋信息数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importBuildingData(List<Building> buildingList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(buildingList) || buildingList.size() == 0)
        {
            throw new ServiceException("导入楼栋信息数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (Building building : buildingList)
        {
            try
            {
                // 验证是否存在这个楼栋信息
                Long id = building.getId();
                Building buildingExist = null;
                if (StringUtils.isNotNull(id))
                {
                    buildingExist = buildingMapper.selectBuildingById(id);
                }
                if (StringUtils.isNull(buildingExist))
                {
                    BeanValidators.validateWithException(validator, building);
                    building.setCreateTime(DateUtils.getNowDate());
                    buildingMapper.insertBuilding(building);
                    successNum++;
                    String idStr = StringUtils.isNotNull(id) ? id.toString() : "新记录";
                    successMsg.append("<br/>" + successNum + "、楼栋信息 " + idStr + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, building);
                    building.setUpdateTime(DateUtils.getNowDate());
                    buildingMapper.updateBuilding(building);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、楼栋信息 " + id.toString() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    String idStr = StringUtils.isNotNull(id) ? id.toString() : "未知";
                    failureMsg.append("<br/>" + failureNum + "、楼栋信息 " + idStr + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                Long id = building.getId();
                String idStr = StringUtils.isNotNull(id) ? id.toString() : "未知";
                String msg = "<br/>" + failureNum + "、楼栋信息 " + idStr + " 导入失败：";
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
