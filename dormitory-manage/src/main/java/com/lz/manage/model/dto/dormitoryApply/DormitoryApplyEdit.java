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
public class DormitoryApplyEdit implements Serializable
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

    /**
     * 对象转封装类
     *
     * @param dormitoryApplyEdit 编辑对象
     * @return DormitoryApply
     */
    public static DormitoryApply editToObj(DormitoryApplyEdit dormitoryApplyEdit) {
        if (dormitoryApplyEdit == null) {
            return null;
        }
        DormitoryApply dormitoryApply = new DormitoryApply();
        BeanUtils.copyProperties(dormitoryApplyEdit, dormitoryApply);
        return dormitoryApply;
    }
}
