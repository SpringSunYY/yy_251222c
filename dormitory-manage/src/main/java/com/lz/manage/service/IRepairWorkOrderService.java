package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.RepairWorkOrder;
import com.lz.manage.model.vo.repairWorkOrder.RepairWorkOrderVo;
import com.lz.manage.model.dto.repairWorkOrder.RepairWorkOrderQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 维修工单Service接口
 * 
 * @author YY
 * @date 2025-12-27
 */
public interface IRepairWorkOrderService extends IService<RepairWorkOrder>
{
    //region mybatis代码
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
     * 批量删除维修工单
     * 
     * @param ids 需要删除的维修工单主键集合
     * @return 结果
     */
    public int deleteRepairWorkOrderByIds(Long[] ids);

    /**
     * 删除维修工单信息
     * 
     * @param id 维修工单主键
     * @return 结果
     */
    public int deleteRepairWorkOrderById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param repairWorkOrderQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<RepairWorkOrder> getQueryWrapper(RepairWorkOrderQuery repairWorkOrderQuery);

    /**
     * 转换vo
     *
     * @param repairWorkOrderList RepairWorkOrder集合
     * @return RepairWorkOrderVO集合
     */
    List<RepairWorkOrderVo> convertVoList(List<RepairWorkOrder> repairWorkOrderList);

    /**
     * 导入维修工单数据
     *
     * @param repairWorkOrderList 维修工单数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importRepairWorkOrderData(List<RepairWorkOrder> repairWorkOrderList, Boolean isUpdateSupport, String operName);
}
