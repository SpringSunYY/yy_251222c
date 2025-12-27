package com.lz.manage.model.dto.dormitoryBedHistory;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.DormitoryBedHistory;
/**
 * 床位历史Vo对象 tb_dormitory_bed_history
 *
 * @author YY
 * @date 2025-12-27
 */
@Data
public class DormitoryBedHistoryEdit implements Serializable
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

    /**
     * 对象转封装类
     *
     * @param dormitoryBedHistoryEdit 编辑对象
     * @return DormitoryBedHistory
     */
    public static DormitoryBedHistory editToObj(DormitoryBedHistoryEdit dormitoryBedHistoryEdit) {
        if (dormitoryBedHistoryEdit == null) {
            return null;
        }
        DormitoryBedHistory dormitoryBedHistory = new DormitoryBedHistory();
        BeanUtils.copyProperties(dormitoryBedHistoryEdit, dormitoryBedHistory);
        return dormitoryBedHistory;
    }
}
