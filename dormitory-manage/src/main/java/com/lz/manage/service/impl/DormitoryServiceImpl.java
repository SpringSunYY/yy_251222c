package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lz.common.utils.StringUtils;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.bean.BeanValidators;
import com.lz.common.utils.spring.SpringUtils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.DormitoryMapper;
import com.lz.manage.model.domain.Dormitory;
import com.lz.manage.service.IDormitoryService;
import com.lz.manage.model.dto.dormitory.DormitoryQuery;
import com.lz.manage.model.vo.dormitory.DormitoryVo;

/**
 * 宿舍Service业务层处理
 *
 * @author YY
 * @date 2025-12-27
 */
@Service
public class DormitoryServiceImpl extends ServiceImpl<DormitoryMapper, Dormitory> implements IDormitoryService
{
    private static final Logger log = LoggerFactory.getLogger(DormitoryServiceImpl.class);

    /** 导入用户数据校验器 */
    private static Validator validator;

    @Resource
    private DormitoryMapper dormitoryMapper;

    {
        validator = SpringUtils.getBean(Validator.class);
    }

    //region mybatis代码
    /**
     * 查询宿舍
     *
     * @param id 宿舍主键
     * @return 宿舍
     */
    @Override
    public Dormitory selectDormitoryById(Long id)
    {
        return dormitoryMapper.selectDormitoryById(id);
    }

    /**
     * 查询宿舍列表
     *
     * @param dormitory 宿舍
     * @return 宿舍
     */
    @Override
    public List<Dormitory> selectDormitoryList(Dormitory dormitory)
    {
        return dormitoryMapper.selectDormitoryList(dormitory);
    }

    /**
     * 新增宿舍
     *
     * @param dormitory 宿舍
     * @return 结果
     */
    @Override
    public int insertDormitory(Dormitory dormitory)
    {
        dormitory.setCreateTime(DateUtils.getNowDate());
        return dormitoryMapper.insertDormitory(dormitory);
    }

    /**
     * 修改宿舍
     *
     * @param dormitory 宿舍
     * @return 结果
     */
    @Override
    public int updateDormitory(Dormitory dormitory)
    {
        dormitory.setUpdateTime(DateUtils.getNowDate());
        return dormitoryMapper.updateDormitory(dormitory);
    }

    /**
     * 批量删除宿舍
     *
     * @param ids 需要删除的宿舍主键
     * @return 结果
     */
    @Override
    public int deleteDormitoryByIds(Long[] ids)
    {
        return dormitoryMapper.deleteDormitoryByIds(ids);
    }

    /**
     * 删除宿舍信息
     *
     * @param id 宿舍主键
     * @return 结果
     */
    @Override
    public int deleteDormitoryById(Long id)
    {
        return dormitoryMapper.deleteDormitoryById(id);
    }
    //endregion
    @Override
    public QueryWrapper<Dormitory> getQueryWrapper(DormitoryQuery dormitoryQuery){
        QueryWrapper<Dormitory> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = dormitoryQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = dormitoryQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long buildingId = dormitoryQuery.getBuildingId();
        queryWrapper.eq( StringUtils.isNotNull(buildingId),"building_id",buildingId);

        String name = dormitoryQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name) ,"name",name);

        Date createTime = dormitoryQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<DormitoryVo> convertVoList(List<Dormitory> dormitoryList) {
        if (StringUtils.isEmpty(dormitoryList)) {
            return Collections.emptyList();
        }
        return dormitoryList.stream().map(DormitoryVo::objToVo).collect(Collectors.toList());
    }

    /**
     * 导入宿舍数据
     *
     * @param dormitoryList 宿舍数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importDormitoryData(List<Dormitory> dormitoryList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(dormitoryList) || dormitoryList.size() == 0)
        {
            throw new ServiceException("导入宿舍数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (Dormitory dormitory : dormitoryList)
        {
            try
            {
                // 验证是否存在这个宿舍
                Long id = dormitory.getId();
                Dormitory dormitoryExist = null;
                if (StringUtils.isNotNull(id))
                {
                    dormitoryExist = dormitoryMapper.selectDormitoryById(id);
                }
                if (StringUtils.isNull(dormitoryExist))
                {
                    BeanValidators.validateWithException(validator, dormitory);
                    dormitory.setCreateTime(DateUtils.getNowDate());
                    dormitoryMapper.insertDormitory(dormitory);
                    successNum++;
                    String idStr = StringUtils.isNotNull(id) ? id.toString() : "新记录";
                    successMsg.append("<br/>" + successNum + "、宿舍 " + idStr + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, dormitory);
                    dormitory.setUpdateTime(DateUtils.getNowDate());
                    dormitoryMapper.updateDormitory(dormitory);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、宿舍 " + id.toString() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    String idStr = StringUtils.isNotNull(id) ? id.toString() : "未知";
                    failureMsg.append("<br/>" + failureNum + "、宿舍 " + idStr + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                Long id = dormitory.getId();
                String idStr = StringUtils.isNotNull(id) ? id.toString() : "未知";
                String msg = "<br/>" + failureNum + "、宿舍 " + idStr + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

}
