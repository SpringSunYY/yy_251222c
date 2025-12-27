package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.DormitoryBedHistory;
import com.lz.manage.model.vo.dormitoryBedHistory.DormitoryBedHistoryVo;
import com.lz.manage.model.dto.dormitoryBedHistory.DormitoryBedHistoryQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 床位历史Service接口
 * 
 * @author YY
 * @date 2025-12-27
 */
public interface IDormitoryBedHistoryService extends IService<DormitoryBedHistory>
{
    //region mybatis代码
    /**
     * 查询床位历史
     * 
     * @param id 床位历史主键
     * @return 床位历史
     */
    public DormitoryBedHistory selectDormitoryBedHistoryById(Long id);

    /**
     * 查询床位历史列表
     * 
     * @param dormitoryBedHistory 床位历史
     * @return 床位历史集合
     */
    public List<DormitoryBedHistory> selectDormitoryBedHistoryList(DormitoryBedHistory dormitoryBedHistory);

    /**
     * 新增床位历史
     * 
     * @param dormitoryBedHistory 床位历史
     * @return 结果
     */
    public int insertDormitoryBedHistory(DormitoryBedHistory dormitoryBedHistory);

    /**
     * 修改床位历史
     * 
     * @param dormitoryBedHistory 床位历史
     * @return 结果
     */
    public int updateDormitoryBedHistory(DormitoryBedHistory dormitoryBedHistory);

    /**
     * 批量删除床位历史
     * 
     * @param ids 需要删除的床位历史主键集合
     * @return 结果
     */
    public int deleteDormitoryBedHistoryByIds(Long[] ids);

    /**
     * 删除床位历史信息
     * 
     * @param id 床位历史主键
     * @return 结果
     */
    public int deleteDormitoryBedHistoryById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param dormitoryBedHistoryQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<DormitoryBedHistory> getQueryWrapper(DormitoryBedHistoryQuery dormitoryBedHistoryQuery);

    /**
     * 转换vo
     *
     * @param dormitoryBedHistoryList DormitoryBedHistory集合
     * @return DormitoryBedHistoryVO集合
     */
    List<DormitoryBedHistoryVo> convertVoList(List<DormitoryBedHistory> dormitoryBedHistoryList);

    /**
     * 导入床位历史数据
     *
     * @param dormitoryBedHistoryList 床位历史数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importDormitoryBedHistoryData(List<DormitoryBedHistory> dormitoryBedHistoryList, Boolean isUpdateSupport, String operName);
}
