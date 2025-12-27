package com.lz.manage.model.dto.dormitoryBed;

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
import com.lz.manage.model.domain.DormitoryBed;
/**
 * 宿舍床位Query对象 tb_dormitory_bed
 *
 * @author YY
 * @date 2025-12-27
 */
@Data
public class DormitoryBedQuery implements Serializable
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
     * @param dormitoryBedQuery 查询对象
     * @return DormitoryBed
     */
    public static DormitoryBed queryToObj(DormitoryBedQuery dormitoryBedQuery) {
        if (dormitoryBedQuery == null) {
            return null;
        }
        DormitoryBed dormitoryBed = new DormitoryBed();
        BeanUtils.copyProperties(dormitoryBedQuery, dormitoryBed);
        return dormitoryBed;
    }
}
