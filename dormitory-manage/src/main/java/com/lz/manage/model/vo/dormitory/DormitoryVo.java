package com.lz.manage.model.vo.dormitory;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.Dormitory;
/**
 * 宿舍Vo对象 tb_dormitory
 *
 * @author YY
 * @date 2025-12-27
 */
@Data
public class DormitoryVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 楼栋 */
    private Long buildingId;

    /** 名称 */
    private String name;

    /** 人数限制 */
    private Long peopleNumberLimit;

    /** 宿舍人数 */
    private Long peopleNumber;

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
     * @param dormitory Dormitory实体对象
     * @return DormitoryVo
     */
    public static DormitoryVo objToVo(Dormitory dormitory) {
        if (dormitory == null) {
            return null;
        }
        DormitoryVo dormitoryVo = new DormitoryVo();
        BeanUtils.copyProperties(dormitory, dormitoryVo);
        return dormitoryVo;
    }
}
