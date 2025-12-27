package com.lz.manage.model.dto.dormitoryBedHistory;

import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.DormitoryBedHistory;
/**
 * 床位历史Query对象 tb_dormitory_bed_history
 *
 * @author YY
 * @date 2025-12-27
 */
@Data
public class DormitoryBedHistoryQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 楼栋 */
    private Long buildingId;

    /** 宿舍 */
    private Long dormitoryId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param dormitoryBedHistoryQuery 查询对象
     * @return DormitoryBedHistory
     */
    public static DormitoryBedHistory queryToObj(DormitoryBedHistoryQuery dormitoryBedHistoryQuery) {
        if (dormitoryBedHistoryQuery == null) {
            return null;
        }
        DormitoryBedHistory dormitoryBedHistory = new DormitoryBedHistory();
        BeanUtils.copyProperties(dormitoryBedHistoryQuery, dormitoryBedHistory);
        return dormitoryBedHistory;
    }
}
