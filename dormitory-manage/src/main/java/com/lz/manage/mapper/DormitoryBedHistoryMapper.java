package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.DormitoryBedHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 床位历史Mapper接口
 * 
 * @author YY
 * @date 2025-12-27
 */
public interface DormitoryBedHistoryMapper extends BaseMapper<DormitoryBedHistory>
{
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
     * 删除床位历史
     * 
     * @param id 床位历史主键
     * @return 结果
     */
    public int deleteDormitoryBedHistoryById(Long id);

    /**
     * 批量删除床位历史
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDormitoryBedHistoryByIds(Long[] ids);
}
