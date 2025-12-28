package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 报修状态 枚举
 * 对应字典类型：repair_status
 */
@Getter
public enum RepairStatusEnum {

    /** 已提交 */
    REPAIR_STATUS_0("0", "已提交"),

    /** 进行中 */
    REPAIR_STATUS_1("1", "进行中"),

    /** 已完成 */
    REPAIR_STATUS_2("2", "已完成");

    private static final Map<String, RepairStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (RepairStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    private final String value;
    private final String label;

    RepairStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 根据 value 获取对应的枚举
     */
    public static Optional<RepairStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
