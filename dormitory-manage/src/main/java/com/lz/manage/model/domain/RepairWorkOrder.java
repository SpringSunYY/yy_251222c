package com.lz.manage.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lz.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 维修工单对象 tb_repair_work_order
 *
 * @author YY
 * @date 2025-12-27
 */
@TableName("tb_repair_work_order")
@Data
public class RepairWorkOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 报修
     */
    @Excel(name = "报修")
    private Long repairId;

    /**
     * 工单状态
     */
    @Excel(name = "工单状态", dictType = "work_order_status")
    private String status;

    /**
     * 维修工
     */
    @Excel(name = "维修工", type = Excel.Type.IMPORT)
    private Long dealWithId;
    @Excel(name = "处理工", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String dealWithName;
    /**
     * 处理时间
     */
    @Excel(name = "处理时间")
    private Date dealWithTime;

    /**
     * 处理费用
     */
    @Excel(name = "处理费用")
    private Long dealWithCost;

    /**
     * 处理内容
     */
    @Excel(name = "处理内容")
    private String dealWithContent;

    /**
     * 处理照片
     */
    @Excel(name = "处理照片")
    private String dealWithImage;

    /**
     * 完成时间
     */
    @Excel(name = "完成时间")
    private Date completedTime;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 创建人
     */
    @Excel(name = "创建人", type = Excel.Type.IMPORT)
    private Long userId;
    @Excel(name = "创建人", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String userName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
