package com.plp.signalingsystem.model;

public class LightState {
    private int mDuration;
    private String mLightSequence;
    private String mIntersectionName;

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public String getLightSequence() {
        return mLightSequence;
    }

    public void setLightSequence(String lightSequence) {
        mLightSequence = lightSequence;
    }

    public String getIntersectionName() {
        return mIntersectionName;
    }

    public void setIntersectionName(String intersectionName) {
        this.mIntersectionName = intersectionName;
    }

    public  LightState(String intersectionName, int duration, String lightSequence){
        this.mIntersectionName = intersectionName;
        this.mDuration = duration;
        this.mLightSequence = lightSequence;
    }
}
