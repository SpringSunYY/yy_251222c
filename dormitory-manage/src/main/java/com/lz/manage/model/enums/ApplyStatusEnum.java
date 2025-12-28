package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 申请状态 枚举
 * 对应字典类型：apply_status
 */
@Getter
public enum ApplyStatusEnum {

    /** 待审核 */
    APPLY_STATUS_0("0", "待审核"),

    /** 同意 */
    APPLY_STATUS_1("1", "同意"),

    /** 拒绝 */
    APPLY_STATUS_2("2", "拒绝");

    private static final Map<String, ApplyStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (ApplyStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    private final String value;
    private final String label;

    ApplyStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 根据 value 获取对应的枚举
     */
    public static Optional<ApplyStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
