package com.bcp.yamaha.constants;

public enum ScheduleType {
    SCHEDULE_VISIT("Schedule Visit"),
    TEST_DRIVE("Test Drive");

    private final String displayName;

    ScheduleType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

