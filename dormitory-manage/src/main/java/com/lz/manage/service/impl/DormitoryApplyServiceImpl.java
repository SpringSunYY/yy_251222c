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
import com.lz.manage.mapper.DormitoryApplyMapper;
import com.lz.manage.model.domain.DormitoryApply;
import com.lz.manage.service.IDormitoryApplyService;
import com.lz.manage.model.dto.dormitoryApply.DormitoryApplyQuery;
import com.lz.manage.model.vo.dormitoryApply.DormitoryApplyVo;

/**
 * 宿舍申请Service业务层处理
 *
 * @author YY
 * @date 2025-12-27
 */
@Service
public class DormitoryApplyServiceImpl extends ServiceImpl<DormitoryApplyMapper, DormitoryApply> implements IDormitoryApplyService
{
    private static final Logger log = LoggerFactory.getLogger(DormitoryApplyServiceImpl.class);

    /** 导入用户数据校验器 */
    private static Validator validator;

    @Resource
    private DormitoryApplyMapper dormitoryApplyMapper;

    {
        validator = SpringUtils.getBean(Validator.class);
    }

    //region mybatis代码
    /**
     * 查询宿舍申请
     *
     * @param id 宿舍申请主键
     * @return 宿舍申请
     */
    @Override
    public DormitoryApply selectDormitoryApplyById(Long id)
    {
        return dormitoryApplyMapper.selectDormitoryApplyById(id);
    }

    /**
     * 查询宿舍申请列表
     *
     * @param dormitoryApply 宿舍申请
     * @return 宿舍申请
     */
    @Override
    public List<DormitoryApply> selectDormitoryApplyList(DormitoryApply dormitoryApply)
    {
        return dormitoryApplyMapper.selectDormitoryApplyList(dormitoryApply);
    }

    /**
     * 新增宿舍申请
     *
     * @param dormitoryApply 宿舍申请
     * @return 结果
     */
    @Override
    public int insertDormitoryApply(DormitoryApply dormitoryApply)
    {
        dormitoryApply.setCreateTime(DateUtils.getNowDate());
        return dormitoryApplyMapper.insertDormitoryApply(dormitoryApply);
    }

    /**
     * 修改宿舍申请
     *
     * @param dormitoryApply 宿舍申请
     * @return 结果
     */
    @Override
    public int updateDormitoryApply(DormitoryApply dormitoryApply)
    {
        dormitoryApply.setUpdateTime(DateUtils.getNowDate());
        return dormitoryApplyMapper.updateDormitoryApply(dormitoryApply);
    }

    /**
     * 批量删除宿舍申请
     *
     * @param ids 需要删除的宿舍申请主键
     * @return 结果
     */
    @Override
    public int deleteDormitoryApplyByIds(Long[] ids)
    {
        return dormitoryApplyMapper.deleteDormitoryApplyByIds(ids);
    }

    /**
     * 删除宿舍申请信息
     *
     * @param id 宿舍申请主键
     * @return 结果
     */
    @Override
    public int deleteDormitoryApplyById(Long id)
    {
        return dormitoryApplyMapper.deleteDormitoryApplyById(id);
    }
    //endregion
    @Override
    public QueryWrapper<DormitoryApply> getQueryWrapper(DormitoryApplyQuery dormitoryApplyQuery){
        QueryWrapper<DormitoryApply> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = dormitoryApplyQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = dormitoryApplyQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long buildingId = dormitoryApplyQuery.getBuildingId();
        queryWrapper.eq( StringUtils.isNotNull(buildingId),"building_id",buildingId);

        Long dormitoryId = dormitoryApplyQuery.getDormitoryId();
        queryWrapper.eq( StringUtils.isNotNull(dormitoryId),"dormitory_id",dormitoryId);

        Long bedId = dormitoryApplyQuery.getBedId();
        queryWrapper.eq( StringUtils.isNotNull(bedId),"bed_id",bedId);

        String status = dormitoryApplyQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

        Date createTime = dormitoryApplyQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<DormitoryApplyVo> convertVoList(List<DormitoryApply> dormitoryApplyList) {
        if (StringUtils.isEmpty(dormitoryApplyList)) {
            return Collections.emptyList();
        }
        return dormitoryApplyList.stream().map(DormitoryApplyVo::objToVo).collect(Collectors.toList());
    }

    /**
     * 导入宿舍申请数据
     *
     * @param dormitoryApplyList 宿舍申请数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importDormitoryApplyData(List<DormitoryApply> dormitoryApplyList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(dormitoryApplyList) || dormitoryApplyList.size() == 0)
        {
            throw new ServiceException("导入宿舍申请数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (DormitoryApply dormitoryApply : dormitoryApplyList)
        {
            try
            {
                // 验证是否存在这个宿舍申请
                Long id = dormitoryApply.getId();
                DormitoryApply dormitoryApplyExist = null;
                if (StringUtils.isNotNull(id))
                {
                    dormitoryApplyExist = dormitoryApplyMapper.selectDormitoryApplyById(id);
                }
                if (StringUtils.isNull(dormitoryApplyExist))
                {
                    BeanValidators.validateWithException(validator, dormitoryApply);
                    dormitoryApply.setCreateTime(DateUtils.getNowDate());
                    dormitoryApplyMapper.insertDormitoryApply(dormitoryApply);
                    successNum++;
                    String idStr = StringUtils.isNotNull(id) ? id.toString() : "新记录";
                    successMsg.append("<br/>" + successNum + "、宿舍申请 " + idStr + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    BeanValidators.validateWithException(validator, dormitoryApply);
                    dormitoryApply.setUpdateTime(DateUtils.getNowDate());
                    dormitoryApplyMapper.updateDormitoryApply(dormitoryApply);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、宿舍申请 " + id.toString() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    String idStr = StringUtils.isNotNull(id) ? id.toString() : "未知";
                    failureMsg.append("<br/>" + failureNum + "、宿舍申请 " + idStr + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                Long id = dormitoryApply.getId();
                String idStr = StringUtils.isNotNull(id) ? id.toString() : "未知";
                String msg = "<br/>" + failureNum + "、宿舍申请 " + idStr + " 导入失败：";
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
