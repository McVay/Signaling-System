package com.plp.signalingsystem.model;

import java.util.ArrayList;

public class Intersection {
    public String getIntersectionName() {
        return mIntersectionName;
    }

    public void setIntersectionName(String intersectionName) {
        mIntersectionName = intersectionName;
    }

    public ArrayList<TrafficLight> getLights() {
        return mLights;
    }

    public void setLights(ArrayList<TrafficLight> lights) {
        mLights = lights;
    }

    public TrafficLight getCurrentLight() {
        return mCurrentLight;
    }

    public void setCurrentLight(TrafficLight currentLight) {
        mCurrentLight = currentLight;
    }

    private String mIntersectionName;
    private ArrayList<TrafficLight> mLights;
    private TrafficLight mCurrentLight;

    public Intersection(String RoadName, ArrayList<TrafficLight> lights){
        this.mIntersectionName = RoadName;
        this.mLights = lights;
    }
}
