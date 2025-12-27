package com.lz.manage.model.vo.dormitoryApply;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.DormitoryApply;
/**
 * 宿舍申请Vo对象 tb_dormitory_apply
 *
 * @author YY
 * @date 2025-12-27
 */
@Data
public class DormitoryApplyVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 楼栋 */
    private Long buildingId;

    /** 宿舍 */
    private Long dormitoryId;

    /** 床位 */
    private Long bedId;

    /** 申请状态 */
    private String status;

    /** 申请理由 */
    private String applyContent;

    /** 备注 */
    private String remark;

    /** 创建人 */
    private Long userId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


     /**
     * 对象转封装类
     *
     * @param dormitoryApply DormitoryApply实体对象
     * @return DormitoryApplyVo
     */
    public static DormitoryApplyVo objToVo(DormitoryApply dormitoryApply) {
        if (dormitoryApply == null) {
            return null;
        }
        DormitoryApplyVo dormitoryApplyVo = new DormitoryApplyVo();
        BeanUtils.copyProperties(dormitoryApply, dormitoryApplyVo);
        return dormitoryApplyVo;
    }
}
