package com.bcp.yamaha.constants;

public enum ScheduleType {
    SCHEDULE_VISIT("Schedule Visit"),
    TEST_DRIVE("Test Drive"),
    SCHEDULE_LATER("Schedule Later");

    private final String displayName;

    ScheduleType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

