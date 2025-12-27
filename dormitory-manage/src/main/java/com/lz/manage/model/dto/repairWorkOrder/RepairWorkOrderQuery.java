package com.lz.manage.model.dto.repairWorkOrder;

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
import com.lz.manage.model.domain.RepairWorkOrder;
/**
 * 维修工单Query对象 tb_repair_work_order
 *
 * @author YY
 * @date 2025-12-27
 */
@Data
public class RepairWorkOrderQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 报修 */
    private Long repairId;

    /** 工单状态 */
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
     * @param repairWorkOrderQuery 查询对象
     * @return RepairWorkOrder
     */
    public static RepairWorkOrder queryToObj(RepairWorkOrderQuery repairWorkOrderQuery) {
        if (repairWorkOrderQuery == null) {
            return null;
        }
        RepairWorkOrder repairWorkOrder = new RepairWorkOrder();
        BeanUtils.copyProperties(repairWorkOrderQuery, repairWorkOrder);
        return repairWorkOrder;
    }
}
