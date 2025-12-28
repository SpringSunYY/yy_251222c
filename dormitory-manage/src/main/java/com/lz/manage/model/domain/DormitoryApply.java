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
 * 宿舍申请对象 tb_dormitory_apply
 *
 * @author YY
 * @date 2025-12-27
 */
@TableName("tb_dormitory_apply")
@Data
public class DormitoryApply implements Serializable {
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
     * 申请状态
     */
    @Excel(name = "申请状态", dictType = "apply_status")
    private String status;

    /**
     * 申请理由
     */
    @Excel(name = "申请理由")
    private String applyContent;

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
