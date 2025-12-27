package com.lz.manage.model.vo.building;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.Building;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 楼栋信息Vo对象 tb_building
 *
 * @author YY
 * @date 2025-12-27
 */
@Data
public class BuildingVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

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
     * @param building Building实体对象
     * @return BuildingVo
     */
    public static BuildingVo objToVo(Building building) {
        if (building == null) {
            return null;
        }
        BuildingVo buildingVo = new BuildingVo();
        BeanUtils.copyProperties(building, buildingVo);
        return buildingVo;
    }
}
