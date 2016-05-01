package com.plp.signalingsystem.model;

public class LightState {
    private int mDuration;
    private String mLightPattern;
    private String mIntersectionName;

    public int getDuration() {
        return mDuration;
    }

    public String getLightPattern() {
        return mLightPattern;
    }

    public  LightState(String intersectionName, int duration, String lightPattern){
        this.mIntersectionName = intersectionName;
        this.mDuration = duration;
        this.mLightPattern = lightPattern;
    }
}
