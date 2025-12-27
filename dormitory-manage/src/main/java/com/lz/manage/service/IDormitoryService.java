package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.Dormitory;
import com.lz.manage.model.vo.dormitory.DormitoryVo;
import com.lz.manage.model.dto.dormitory.DormitoryQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 宿舍Service接口
 * 
 * @author YY
 * @date 2025-12-27
 */
public interface IDormitoryService extends IService<Dormitory>
{
    //region mybatis代码
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
     * 批量删除宿舍
     * 
     * @param ids 需要删除的宿舍主键集合
     * @return 结果
     */
    public int deleteDormitoryByIds(Long[] ids);

    /**
     * 删除宿舍信息
     * 
     * @param id 宿舍主键
     * @return 结果
     */
    public int deleteDormitoryById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param dormitoryQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<Dormitory> getQueryWrapper(DormitoryQuery dormitoryQuery);

    /**
     * 转换vo
     *
     * @param dormitoryList Dormitory集合
     * @return DormitoryVO集合
     */
    List<DormitoryVo> convertVoList(List<Dormitory> dormitoryList);

    /**
     * 导入宿舍数据
     *
     * @param dormitoryList 宿舍数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importDormitoryData(List<Dormitory> dormitoryList, Boolean isUpdateSupport, String operName);
}
