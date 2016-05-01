package com.plp.signalingsystem.model;

public class TrafficLight {
    private final String mName;
    private LightStatus mStatus;

    public String getName() {
        return mName;
    }

    public TrafficLight(String name, LightStatus status) {
        this.mName = name;
        this.mStatus = status;
    }
}
