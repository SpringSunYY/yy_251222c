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
public class BuildingInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 名称 */
    private String name;

    /** 备注 */
    private String remark;

    /** 创建人 */
    private Long userId;

    /**
     * 对象转封装类
     *
     * @param buildingInsert 插入对象
     * @return BuildingInsert
     */
    public static Building insertToObj(BuildingInsert buildingInsert) {
        if (buildingInsert == null) {
            return null;
        }
        Building building = new Building();
        BeanUtils.copyProperties(buildingInsert, building);
        return building;
    }
}
