package com.lz.manage.model.dto.building;

import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.Building;
/**
 * 楼栋信息Query对象 tb_building
 *
 * @author YY
 * @date 2025-12-27
 */
@Data
public class BuildingQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 名称 */
    private String name;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param buildingQuery 查询对象
     * @return Building
     */
    public static Building queryToObj(BuildingQuery buildingQuery) {
        if (buildingQuery == null) {
            return null;
        }
        Building building = new Building();
        BeanUtils.copyProperties(buildingQuery, building);
        return building;
    }
}
