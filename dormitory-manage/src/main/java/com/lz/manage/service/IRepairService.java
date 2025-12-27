package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.Repair;
import com.lz.manage.model.vo.repair.RepairVo;
import com.lz.manage.model.dto.repair.RepairQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 报修记录Service接口
 * 
 * @author YY
 * @date 2025-12-27
 */
public interface IRepairService extends IService<Repair>
{
    //region mybatis代码
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
     * 批量删除报修记录
     * 
     * @param ids 需要删除的报修记录主键集合
     * @return 结果
     */
    public int deleteRepairByIds(Long[] ids);

    /**
     * 删除报修记录信息
     * 
     * @param id 报修记录主键
     * @return 结果
     */
    public int deleteRepairById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param repairQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<Repair> getQueryWrapper(RepairQuery repairQuery);

    /**
     * 转换vo
     *
     * @param repairList Repair集合
     * @return RepairVO集合
     */
    List<RepairVo> convertVoList(List<Repair> repairList);

    /**
     * 导入报修记录数据
     *
     * @param repairList 报修记录数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importRepairData(List<Repair> repairList, Boolean isUpdateSupport, String operName);
}
