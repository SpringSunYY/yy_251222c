package com.lz.manage.model.dto.dormitoryBed;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.DormitoryBed;
/**
 * 宿舍床位Vo对象 tb_dormitory_bed
 *
 * @author YY
 * @date 2025-12-27
 */
@Data
public class DormitoryBedInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 楼栋 */
    private Long buildingId;

    /** 宿舍 */
    private Long dormitoryId;

    /** 床位状态 */
    private String status;

    /** 所属人 */
    private Long belongUserId;

    /** 备注 */
    private String remark;

    /** 创建人 */
    private Long userId;

    /**
     * 对象转封装类
     *
     * @param dormitoryBedInsert 插入对象
     * @return DormitoryBedInsert
     */
    public static DormitoryBed insertToObj(DormitoryBedInsert dormitoryBedInsert) {
        if (dormitoryBedInsert == null) {
            return null;
        }
        DormitoryBed dormitoryBed = new DormitoryBed();
        BeanUtils.copyProperties(dormitoryBedInsert, dormitoryBed);
        return dormitoryBed;
    }
}
