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
 * 报修记录对象 tb_repair
 *
 * @author YY
 * @date 2025-12-27
 */
@TableName("tb_repair")
@Data
public class Repair implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 楼栋
     */
    @Excel(name = "楼栋", type = Excel.Type.IMPORT)
    private Long buildingId;
    @Excel(name = "楼栋", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String buildingName;

    /**
     * 宿舍
     */
    @Excel(name = "宿舍", type = Excel.Type.IMPORT)
    private Long dormitoryId;
    @Excel(name = "宿舍", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String dormitoryName;

    /**
     * 床位
     */
    @Excel(name = "床位", type = Excel.Type.IMPORT)
    private Long bedId;
    @Excel(name = "床位", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String bedNum;

    /**
     * 报修类型
     */
    @Excel(name = "报修类型", dictType = "repair_type")
    private String type;

    /**
     * 异常状态
     */
    @Excel(name = "异常状态", dictType = "abnormal_status")
    private String abnormalStatus;

    /**
     * 报修状态
     */
    @Excel(name = "报修状态", dictType = "repair_status")
    private String status;

    /**
     * 报修位置
     */
    @Excel(name = "报修位置", dictType = "repair_status")
    private String repairAddress;

    /**
     * 发现时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发现时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date repairTime;

    /**
     * 问题照片
     */
    @Excel(name = "问题照片")
    private String repairImage;

    /**
     * 问题内容
     */
    @Excel(name = "问题内容")
    private String repairContent;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
    private String contactUser;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String contactPhone;

    /**
     * 完成时间
     */
    @Excel(name = "完成时间")
    private Date completedTime;

    /**
     * 处理费用
     */
    @Excel(name = "处理费用")
    private Long dealWithCost;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 处理人
     */
    @Excel(name = "处理人", type = Excel.Type.IMPORT)
    private Long dealWithId;
    @Excel(name = "处理人", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String dealWithName;

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
