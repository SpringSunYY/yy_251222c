package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.RepairWorkOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 维修工单Mapper接口
 * 
 * @author YY
 * @date 2025-12-27
 */
public interface RepairWorkOrderMapper extends BaseMapper<RepairWorkOrder>
{
    /**
     * 查询维修工单
     * 
     * @param id 维修工单主键
     * @return 维修工单
     */
    public RepairWorkOrder selectRepairWorkOrderById(Long id);

    /**
     * 查询维修工单列表
     * 
     * @param repairWorkOrder 维修工单
     * @return 维修工单集合
     */
    public List<RepairWorkOrder> selectRepairWorkOrderList(RepairWorkOrder repairWorkOrder);

    /**
     * 新增维修工单
     * 
     * @param repairWorkOrder 维修工单
     * @return 结果
     */
    public int insertRepairWorkOrder(RepairWorkOrder repairWorkOrder);

    /**
     * 修改维修工单
     * 
     * @param repairWorkOrder 维修工单
     * @return 结果
     */
    public int updateRepairWorkOrder(RepairWorkOrder repairWorkOrder);

    /**
     * 删除维修工单
     * 
     * @param id 维修工单主键
     * @return 结果
     */
    public int deleteRepairWorkOrderById(Long id);

    /**
     * 批量删除维修工单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRepairWorkOrderByIds(Long[] ids);
}
