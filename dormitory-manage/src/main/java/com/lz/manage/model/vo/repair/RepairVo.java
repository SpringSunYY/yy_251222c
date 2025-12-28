package com.lz.manage.model.vo.repair;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.Repair;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 报修记录Vo对象 tb_repair
 *
 * @author YY
 * @date 2025-12-27
 */
@Data
public class RepairVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 楼栋
     */
    private Long buildingId;
    private String buildingName;

    /**
     * 宿舍
     */
    private Long dormitoryId;
    private String dormitoryName;

    /**
     * 床位
     */
    private Long bedId;
    private String bedNum;

    /**
     * 报修类型
     */
    private String type;

    /**
     * 异常状态
     */
    private String abnormalStatus;

    /**
     * 报修状态
     */
    private String status;

    /**
     * 报修位置
     */
    private String repairAddress;

    /**
     * 发现时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date repairTime;

    /**
     * 问题照片
     */
    private String repairImage;

    /**
     * 问题内容
     */
    private String repairContent;

    /**
     * 联系人
     */
    private String contactUser;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 完成时间
     */
    private Date completedTime;

    /**
     * 处理费用
     */
    private Long dealWithCost;

    /**
     * 备注
     */
    private String remark;

    /**
     * 处理人
     */
    private Long dealWithId;
    private String dealWithName;

    /**
     * 创建人
     */
    private Long userId;
    private String userName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


    /**
     * 对象转封装类
     *
     * @param repair Repair实体对象
     * @return RepairVo
     */
    public static RepairVo objToVo(Repair repair) {
        if (repair == null) {
            return null;
        }
        RepairVo repairVo = new RepairVo();
        BeanUtils.copyProperties(repair, repairVo);
        return repairVo;
    }
}
