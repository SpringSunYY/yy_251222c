package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.Building;
import com.lz.manage.model.vo.building.BuildingVo;
import com.lz.manage.model.dto.building.BuildingQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 楼栋信息Service接口
 * 
 * @author YY
 * @date 2025-12-27
 */
public interface IBuildingService extends IService<Building>
{
    //region mybatis代码
    /**
     * 查询楼栋信息
     * 
     * @param id 楼栋信息主键
     * @return 楼栋信息
     */
    public Building selectBuildingById(Long id);

    /**
     * 查询楼栋信息列表
     * 
     * @param building 楼栋信息
     * @return 楼栋信息集合
     */
    public List<Building> selectBuildingList(Building building);

    /**
     * 新增楼栋信息
     * 
     * @param building 楼栋信息
     * @return 结果
     */
    public int insertBuilding(Building building);

    /**
     * 修改楼栋信息
     * 
     * @param building 楼栋信息
     * @return 结果
     */
    public int updateBuilding(Building building);

    /**
     * 批量删除楼栋信息
     * 
     * @param ids 需要删除的楼栋信息主键集合
     * @return 结果
     */
    public int deleteBuildingByIds(Long[] ids);

    /**
     * 删除楼栋信息信息
     * 
     * @param id 楼栋信息主键
     * @return 结果
     */
    public int deleteBuildingById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param buildingQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<Building> getQueryWrapper(BuildingQuery buildingQuery);

    /**
     * 转换vo
     *
     * @param buildingList Building集合
     * @return BuildingVO集合
     */
    List<BuildingVo> convertVoList(List<Building> buildingList);

    /**
     * 导入楼栋信息数据
     *
     * @param buildingList 楼栋信息数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importBuildingData(List<Building> buildingList, Boolean isUpdateSupport, String operName);
}
