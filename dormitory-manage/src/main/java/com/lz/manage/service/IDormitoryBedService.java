package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.DormitoryBed;
import com.lz.manage.model.vo.dormitoryBed.DormitoryBedVo;
import com.lz.manage.model.dto.dormitoryBed.DormitoryBedQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 宿舍床位Service接口
 *
 * @author YY
 * @date 2025-12-27
 */
public interface IDormitoryBedService extends IService<DormitoryBed>
{
    //region mybatis代码
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
     * 批量删除宿舍床位
     *
     * @param ids 需要删除的宿舍床位主键集合
     * @return 结果
     */
    public int deleteDormitoryBedByIds(Long[] ids);

    /**
     * 删除宿舍床位信息
     *
     * @param id 宿舍床位主键
     * @return 结果
     */
    public int deleteDormitoryBedById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param dormitoryBedQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<DormitoryBed> getQueryWrapper(DormitoryBedQuery dormitoryBedQuery);

    /**
     * 转换vo
     *
     * @param dormitoryBedList DormitoryBed集合
     * @return DormitoryBedVO集合
     */
    List<DormitoryBedVo> convertVoList(List<DormitoryBed> dormitoryBedList);

    /**
     * 导入宿舍床位数据
     *
     * @param dormitoryBedList 宿舍床位数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importDormitoryBedData(List<DormitoryBed> dormitoryBedList, Boolean isUpdateSupport, String operName);

    int allotDormitoryBed(DormitoryBed dormitoryBed);
}
