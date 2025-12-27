package com.lz.manage.model.vo.repairWorkOrder;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.RepairWorkOrder;
/**
 * 维修工单Vo对象 tb_repair_work_order
 *
 * @author YY
 * @date 2025-12-27
 */
@Data
public class RepairWorkOrderVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 报修 */
    private Long repairId;

    /** 工单状态 */
    private String status;

    /** 维修工 */
    private Long dealWithId;

    /** 处理时间 */
    private Long dealWithTime;

    /** 处理费用 */
    private Long dealWithCost;

    /** 处理内容 */
    private String dealWithContent;

    /** 处理照片 */
    private String dealWithImage;

    /** 完成时间 */
    private Long completedTime;

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
     * @param repairWorkOrder RepairWorkOrder实体对象
     * @return RepairWorkOrderVo
     */
    public static RepairWorkOrderVo objToVo(RepairWorkOrder repairWorkOrder) {
        if (repairWorkOrder == null) {
            return null;
        }
        RepairWorkOrderVo repairWorkOrderVo = new RepairWorkOrderVo();
        BeanUtils.copyProperties(repairWorkOrder, repairWorkOrderVo);
        return repairWorkOrderVo;
    }
}
