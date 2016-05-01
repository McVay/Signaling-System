package com.plp.signalingsystem.model;

import java.util.ArrayList;

public class Intersection {
    public String getIntersectionName() {
        return mIntersectionName;
    }

    public ArrayList<TrafficLight> getLights() {
        return mLights;
    }

    private String mIntersectionName;
    private ArrayList<TrafficLight> mLights;
    private ArrayList<LightState> mLightStates;

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
