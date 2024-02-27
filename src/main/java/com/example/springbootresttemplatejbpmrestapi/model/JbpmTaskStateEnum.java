package com.example.springbootresttemplatejbpmrestapi.model;

public enum JbpmTaskStateEnum {
    NOT_INITIATED("Henüz Başlamadı"), INITIATED("HAZIR"),COMPLETED("TAMAMLANDI");
    private final String displayName;

    JbpmTaskStateEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
    public static JbpmTaskStateEnum getByIndex(int index) {
        JbpmTaskStateEnum[] values = JbpmTaskStateEnum.values();
        if (index >= 0 && index < values.length) {
            return values[index];
        } else {
            throw new IllegalArgumentException("Invalid index");
        }
    }
}
