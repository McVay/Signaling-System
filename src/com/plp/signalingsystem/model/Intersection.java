package com.plp.signalingsystem.model;

import java.util.ArrayList;

public class Intersection {
    public String IntersectionName;
    public ArrayList<TrafficLight> Lights;
    public TrafficLight CurrentLight;

    public Intersection(String RoadName, ArrayList<TrafficLight> lights){
        this.IntersectionName = RoadName;
        this.Lights = lights;
    }
}
