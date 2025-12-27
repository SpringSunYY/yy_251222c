package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.DormitoryBed;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 宿舍床位Mapper接口
 * 
 * @author YY
 * @date 2025-12-27
 */
public interface DormitoryBedMapper extends BaseMapper<DormitoryBed>
{
    /**
     * 查询宿舍床位
     * 
     * @param id 宿舍床位主键
     * @return 宿舍床位
     */
    public DormitoryBed selectDormitoryBedById(Long id);

    /**
     * 查询宿舍床位列表
     * 
     * @param dormitoryBed 宿舍床位
     * @return 宿舍床位集合
     */
    public List<DormitoryBed> selectDormitoryBedList(DormitoryBed dormitoryBed);

    /**
     * 新增宿舍床位
     * 
     * @param dormitoryBed 宿舍床位
     * @return 结果
     */
    public int insertDormitoryBed(DormitoryBed dormitoryBed);

    /**
     * 修改宿舍床位
     * 
     * @param dormitoryBed 宿舍床位
     * @return 结果
     */
    public int updateDormitoryBed(DormitoryBed dormitoryBed);

    /**
     * 删除宿舍床位
     * 
     * @param id 宿舍床位主键
     * @return 结果
     */
    public int deleteDormitoryBedById(Long id);

    /**
     * 批量删除宿舍床位
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDormitoryBedByIds(Long[] ids);
}
