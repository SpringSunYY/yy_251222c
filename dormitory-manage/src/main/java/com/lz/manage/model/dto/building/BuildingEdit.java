package com.lz.manage.model.dto.building;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.Building;
/**
 * 楼栋信息Vo对象 tb_building
 *
 * @author YY
 * @date 2025-12-27
 */
@Data
public class BuildingEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 名称 */
    private String name;

    /** 备注 */
    private String remark;

    /** 创建人 */
    private Long userId;

    /**
     * 对象转封装类
     *
     * @param buildingEdit 编辑对象
     * @return Building
     */
    public static Building editToObj(BuildingEdit buildingEdit) {
        if (buildingEdit == null) {
            return null;
        }
        Building building = new Building();
        BeanUtils.copyProperties(buildingEdit, building);
        return building;
    }
}
