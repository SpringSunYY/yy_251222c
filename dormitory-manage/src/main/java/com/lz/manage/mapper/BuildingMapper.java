package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.Building;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 楼栋信息Mapper接口
 * 
 * @author YY
 * @date 2025-12-27
 */
public interface BuildingMapper extends BaseMapper<Building>
{
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
     * 删除楼栋信息
     * 
     * @param id 楼栋信息主键
     * @return 结果
     */
    public int deleteBuildingById(Long id);

    /**
     * 批量删除楼栋信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBuildingByIds(Long[] ids);
}
