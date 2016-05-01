package com.plp.signalingsystem.model;

public class LightState {
    private int mDuration;
    private String mLightPattern;
    private String mIntersectionName;

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public String getLightPattern() {
        return mLightPattern;
    }

    public void setLightSequence(String lightPattern) {
        mLightPattern = lightPattern;
    }

    public String getIntersectionName() {
        return mIntersectionName;
    }

    public void setIntersectionName(String intersectionName) {
        this.mIntersectionName = intersectionName;
    }

    public  LightState(String intersectionName, int duration, String lightPattern){
        this.mIntersectionName = intersectionName;
        this.mDuration = duration;
        this.mLightPattern = lightPattern;
    }
}
