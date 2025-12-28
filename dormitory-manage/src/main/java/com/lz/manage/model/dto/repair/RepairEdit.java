package com.lz.manage.model.dto.repair;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.Repair;
/**
 * 报修记录Vo对象 tb_repair
 *
 * @author YY
 * @date 2025-12-27
 */
@Data
public class RepairEdit implements Serializable
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

    /** 发现时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date repairTime;

    /** 问题照片 */
    private String repairImage;

    /** 问题内容 */
    private String repairContent;

    /** 联系人 */
    private String contactUser;

    /** 联系电话 */
    private String contactPhone;

    /** 完成时间 */
    private Date completedTime;

    /** 处理费用 */
    private Long dealWithCost;

    /** 备注 */
    private String remark;

    /** 处理人 */
    private Long dealWithId;

    /** 创建人 */
    private Long userId;

    /**
     * 对象转封装类
     *
     * @param repairEdit 编辑对象
     * @return Repair
     */
    public static Repair editToObj(RepairEdit repairEdit) {
        if (repairEdit == null) {
            return null;
        }
        Repair repair = new Repair();
        BeanUtils.copyProperties(repairEdit, repair);
        return repair;
    }
}
