package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.DormitoryApply;
import com.lz.manage.model.vo.dormitoryApply.DormitoryApplyVo;
import com.lz.manage.model.dto.dormitoryApply.DormitoryApplyQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 宿舍申请Service接口
 * 
 * @author YY
 * @date 2025-12-27
 */
public interface IDormitoryApplyService extends IService<DormitoryApply>
{
    //region mybatis代码
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
     * 批量删除宿舍申请
     * 
     * @param ids 需要删除的宿舍申请主键集合
     * @return 结果
     */
    public int deleteDormitoryApplyByIds(Long[] ids);

    /**
     * 删除宿舍申请信息
     * 
     * @param id 宿舍申请主键
     * @return 结果
     */
    public int deleteDormitoryApplyById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param dormitoryApplyQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<DormitoryApply> getQueryWrapper(DormitoryApplyQuery dormitoryApplyQuery);

    /**
     * 转换vo
     *
     * @param dormitoryApplyList DormitoryApply集合
     * @return DormitoryApplyVO集合
     */
    List<DormitoryApplyVo> convertVoList(List<DormitoryApply> dormitoryApplyList);

    /**
     * 导入宿舍申请数据
     *
     * @param dormitoryApplyList 宿舍申请数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importDormitoryApplyData(List<DormitoryApply> dormitoryApplyList, Boolean isUpdateSupport, String operName);
}
