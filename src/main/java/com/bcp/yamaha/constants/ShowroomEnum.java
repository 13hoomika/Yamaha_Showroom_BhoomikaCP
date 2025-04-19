package com.bcp.yamaha.constants;

public enum ShowroomEnum {
    RAJAJINAGAR("Rajajinagar"),
    BASAVESHWARANAGARA("Basaveshwaranagara"),
    MALLESHWARA("Malleshwara"),
    MAHALAKSHMIPURA("Mahalakshmipura"),
    MAHALAKSHMI_LAYOUT("Mahalakshmi Layout"),
    VIJAYANAGARA("Vijayanagara");

    private final String displayName;

    ShowroomEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

