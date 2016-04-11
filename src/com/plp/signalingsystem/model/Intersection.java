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

    private String mIntersectionName;
    private ArrayList<TrafficLight> mLights;
    private ArrayList<LightState> mLightStates;

    public String getmIntersectionName() {
        return mIntersectionName;
    }

    public void setmIntersectionName(String mIntersectionName) {
        this.mIntersectionName = mIntersectionName;
    }

    public ArrayList<TrafficLight> getmLights() {
        return mLights;
    }

    public void setmLights(ArrayList<TrafficLight> mLights) {
        this.mLights = mLights;
    }

    public ArrayList<LightState> getmLightStates() {
        return mLightStates;
    }

    public void setmLightStates(ArrayList<LightState> mLightStates) {
        this.mLightStates = mLightStates;
    }

    public Intersection(String RoadName, ArrayList<TrafficLight> lights, ArrayList<LightState> lightStates){
        this.mIntersectionName = RoadName;
        this.mLights = lights;
        this.mLightStates = lightStates;
    }
}
