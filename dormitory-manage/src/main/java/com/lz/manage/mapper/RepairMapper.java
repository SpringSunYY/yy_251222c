package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.Repair;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 报修记录Mapper接口
 * 
 * @author YY
 * @date 2025-12-27
 */
public interface RepairMapper extends BaseMapper<Repair>
{
    /**
     * 查询报修记录
     * 
     * @param id 报修记录主键
     * @return 报修记录
     */
    public Repair selectRepairById(Long id);

    /**
     * 查询报修记录列表
     * 
     * @param repair 报修记录
     * @return 报修记录集合
     */
    public List<Repair> selectRepairList(Repair repair);

    /**
     * 新增报修记录
     * 
     * @param repair 报修记录
     * @return 结果
     */
    public int insertRepair(Repair repair);

    /**
     * 修改报修记录
     * 
     * @param repair 报修记录
     * @return 结果
     */
    public int updateRepair(Repair repair);

    /**
     * 删除报修记录
     * 
     * @param id 报修记录主键
     * @return 结果
     */
    public int deleteRepairById(Long id);

    /**
     * 批量删除报修记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRepairByIds(Long[] ids);
}
