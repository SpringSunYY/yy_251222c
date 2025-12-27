package com.lz.manage.model.dto.dormitoryApply;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.DormitoryApply;
/**
 * 宿舍申请Vo对象 tb_dormitory_apply
 *
 * @author YY
 * @date 2025-12-27
 */
@Data
public class DormitoryApplyInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

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

    /**
     * 对象转封装类
     *
     * @param dormitoryApplyInsert 插入对象
     * @return DormitoryApplyInsert
     */
    public static DormitoryApply insertToObj(DormitoryApplyInsert dormitoryApplyInsert) {
        if (dormitoryApplyInsert == null) {
            return null;
        }
        DormitoryApply dormitoryApply = new DormitoryApply();
        BeanUtils.copyProperties(dormitoryApplyInsert, dormitoryApply);
        return dormitoryApply;
    }
}
