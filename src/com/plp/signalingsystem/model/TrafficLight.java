package com.plp.signalingsystem.model;

public class TrafficLight {
    private final String mName;
    private LightStatus mStatus;

    public TrafficLight() {
        this.mName = "";
    }

    public String getName() {
        return mName;
    }

    public LightStatus getStatus() {
        return mStatus;
    }

    public void setStatus(LightStatus status) {
        mStatus = status;
    }

    public TrafficLight(String name, LightStatus status) {
        this.mName = name;
        this.mStatus = status;
    }
}
