package com.bcp.yamaha.constants;

public enum BikeType {
    CRUISER("Cruiser"),
    STREET("Street/Naked"),
    ADVENTURE("Adventure"),
    SCOOTER("Scooter"),
    SPORTS("Sports");

    private final String displayName;

    BikeType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

