package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.DormitoryApply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 宿舍申请Mapper接口
 * 
 * @author YY
 * @date 2025-12-27
 */
public interface DormitoryApplyMapper extends BaseMapper<DormitoryApply>
{
    /**
     * 查询宿舍申请
     * 
     * @param id 宿舍申请主键
     * @return 宿舍申请
     */
    public DormitoryApply selectDormitoryApplyById(Long id);

    /**
     * 查询宿舍申请列表
     * 
     * @param dormitoryApply 宿舍申请
     * @return 宿舍申请集合
     */
    public List<DormitoryApply> selectDormitoryApplyList(DormitoryApply dormitoryApply);

    /**
     * 新增宿舍申请
     * 
     * @param dormitoryApply 宿舍申请
     * @return 结果
     */
    public int insertDormitoryApply(DormitoryApply dormitoryApply);

    /**
     * 修改宿舍申请
     * 
     * @param dormitoryApply 宿舍申请
     * @return 结果
     */
    public int updateDormitoryApply(DormitoryApply dormitoryApply);

    /**
     * 删除宿舍申请
     * 
     * @param id 宿舍申请主键
     * @return 结果
     */
    public int deleteDormitoryApplyById(Long id);

    /**
     * 批量删除宿舍申请
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDormitoryApplyByIds(Long[] ids);
}
