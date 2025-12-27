package com.lz.manage.model.vo.dormitoryBed;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.DormitoryBed;
/**
 * 宿舍床位Vo对象 tb_dormitory_bed
 *
 * @author YY
 * @date 2025-12-27
 */
@Data
public class DormitoryBedVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

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

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


     /**
     * 对象转封装类
     *
     * @param dormitoryBed DormitoryBed实体对象
     * @return DormitoryBedVo
     */
    public static DormitoryBedVo objToVo(DormitoryBed dormitoryBed) {
        if (dormitoryBed == null) {
            return null;
        }
        DormitoryBedVo dormitoryBedVo = new DormitoryBedVo();
        BeanUtils.copyProperties(dormitoryBed, dormitoryBedVo);
        return dormitoryBedVo;
    }
}
