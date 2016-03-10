package com.plp.signalingsystem.model;

public class TrafficLight {
    private final String mName;
    private int mTimingInterval;
    private LightStatus mStatus;
    private final Direction mLightDirection;

    public TrafficLight() {
        this.mName = "";
        this.mLightDirection = Direction.NULL;
    }

    public String getName() {
        return mName;
    }

    public int getTimingInterval() {
        return mTimingInterval;
    }

    public void setTimingInterval(int timingInterval) {
        mTimingInterval = timingInterval;
    }

    public LightStatus getStatus() {
        return mStatus;
    }

    public void setStatus(LightStatus status) {
        mStatus = status;
    }

    public Direction getLightDirection() {
        return mLightDirection;
    }

    public TrafficLight(String name, int timingInterval, LightStatus status, Direction direction) {
        this.mName = name;
        this.mTimingInterval = timingInterval;

        this.mStatus = status;
        this.mLightDirection = direction;
    }
}
