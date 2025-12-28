package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 宿舍状态 枚举
 * 对应字典类型：dormitory_status
 */
@Getter
public enum DormitoryStatusEnum {

    /** 空闲 */
    DORMITORY_STATUS_0("0", "空闲"),

    /** 已使用 */
    DORMITORY_STATUS_1("1", "已使用"),

    /** 故障 */
    DORMITORY_STATUS_2("2", "故障");

    private static final Map<String, DormitoryStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (DormitoryStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    private final String value;
    private final String label;

    DormitoryStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 根据 value 获取对应的枚举
     */
    public static Optional<DormitoryStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
