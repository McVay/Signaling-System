package com.plp.signalingsystem.model;

import java.util.ArrayList;

public class Intersection {
    public String RoadName;
    public ArrayList<TrafficLight> lights;

    public Intersection(String RoadName, ArrayList<TrafficLight> lights){
        this.RoadName = RoadName;
        this.lights = lights;
    }
}
