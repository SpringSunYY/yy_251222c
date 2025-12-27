package com.lz.manage.model.vo.dormitoryBedHistory;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.DormitoryBedHistory;
/**
 * 床位历史Vo对象 tb_dormitory_bed_history
 *
 * @author YY
 * @date 2025-12-27
 */
@Data
public class DormitoryBedHistoryVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 楼栋 */
    private Long buildingId;

    /** 宿舍 */
    private Long dormitoryId;

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
     * @param dormitoryBedHistory DormitoryBedHistory实体对象
     * @return DormitoryBedHistoryVo
     */
    public static DormitoryBedHistoryVo objToVo(DormitoryBedHistory dormitoryBedHistory) {
        if (dormitoryBedHistory == null) {
            return null;
        }
        DormitoryBedHistoryVo dormitoryBedHistoryVo = new DormitoryBedHistoryVo();
        BeanUtils.copyProperties(dormitoryBedHistory, dormitoryBedHistoryVo);
        return dormitoryBedHistoryVo;
    }
}
