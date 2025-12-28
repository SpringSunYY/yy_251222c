package com.lz.manage.model.dto.dormitoryBed;

import com.lz.manage.model.domain.DormitoryBed;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * 宿舍床位Vo对象 tb_dormitory_bed
 *
 * @author YY
 * @date 2025-12-27
 */
@Data
public class DormitoryBedEdit implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 楼栋
     */
    private Long buildingId;

    /**
     * 宿舍
     */
    private Long dormitoryId;

    private String bedNum;

    /**
     * 床位状态
     */
    private String status;

    /**
     * 所属人
     */
    private Long belongUserId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private Long userId;

    /**
     * 对象转封装类
     *
     * @param dormitoryBedEdit 编辑对象
     * @return DormitoryBed
     */
    public static DormitoryBed editToObj(DormitoryBedEdit dormitoryBedEdit) {
        if (dormitoryBedEdit == null) {
            return null;
        }
        DormitoryBed dormitoryBed = new DormitoryBed();
        BeanUtils.copyProperties(dormitoryBedEdit, dormitoryBed);
        return dormitoryBed;
    }
}
