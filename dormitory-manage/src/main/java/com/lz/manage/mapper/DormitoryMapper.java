package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.Dormitory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 宿舍Mapper接口
 * 
 * @author YY
 * @date 2025-12-27
 */
public interface DormitoryMapper extends BaseMapper<Dormitory>
{
    /**
     * 查询宿舍
     * 
     * @param id 宿舍主键
     * @return 宿舍
     */
    public Dormitory selectDormitoryById(Long id);

    /**
     * 查询宿舍列表
     * 
     * @param dormitory 宿舍
     * @return 宿舍集合
     */
    public List<Dormitory> selectDormitoryList(Dormitory dormitory);

    /**
     * 新增宿舍
     * 
     * @param dormitory 宿舍
     * @return 结果
     */
    public int insertDormitory(Dormitory dormitory);

    /**
     * 修改宿舍
     * 
     * @param dormitory 宿舍
     * @return 结果
     */
    public int updateDormitory(Dormitory dormitory);

    /**
     * 删除宿舍
     * 
     * @param id 宿舍主键
     * @return 结果
     */
    public int deleteDormitoryById(Long id);

    /**
     * 批量删除宿舍
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDormitoryByIds(Long[] ids);
}
