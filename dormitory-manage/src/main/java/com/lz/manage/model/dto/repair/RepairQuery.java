package com.lz.manage.model.dto.repair;

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
import com.lz.manage.model.domain.Repair;
/**
 * 报修记录Query对象 tb_repair
 *
 * @author YY
 * @date 2025-12-27
 */
@Data
public class RepairQuery implements Serializable
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

    /** 报修类型 */
    private String type;

    /** 异常状态 */
    private String abnormalStatus;

    /** 报修状态 */
    private String status;

    /** 报修位置 */
    private String repairAddress;

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
     * @param repairQuery 查询对象
     * @return Repair
     */
    public static Repair queryToObj(RepairQuery repairQuery) {
        if (repairQuery == null) {
            return null;
        }
        Repair repair = new Repair();
        BeanUtils.copyProperties(repairQuery, repair);
        return repair;
    }
}
