package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 工单状态 枚举
 * 对应字典类型：work_order_status
 */
@Getter
public enum WorkOrderStatusEnum {

    /** 进行中 */
    WORK_ORDER_STATUS_0("0", "进行中"),

    /** 已完成 */
    WORK_ORDER_STATUS_1("1", "已完成");

    private static final Map<String, WorkOrderStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (WorkOrderStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    private final String value;
    private final String label;

    WorkOrderStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 根据 value 获取对应的枚举
     */
    public static Optional<WorkOrderStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
