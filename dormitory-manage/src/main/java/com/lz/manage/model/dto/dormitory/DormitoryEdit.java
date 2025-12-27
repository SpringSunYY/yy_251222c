package com.lz.manage.model.dto.dormitory;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.Dormitory;
/**
 * 宿舍Vo对象 tb_dormitory
 *
 * @author YY
 * @date 2025-12-27
 */
@Data
public class DormitoryEdit implements Serializable
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

    /**
     * 对象转封装类
     *
     * @param dormitoryEdit 编辑对象
     * @return Dormitory
     */
    public static Dormitory editToObj(DormitoryEdit dormitoryEdit) {
        if (dormitoryEdit == null) {
            return null;
        }
        Dormitory dormitory = new Dormitory();
        BeanUtils.copyProperties(dormitoryEdit, dormitory);
        return dormitory;
    }
}
