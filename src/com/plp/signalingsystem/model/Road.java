package com.plp.signalingsystem.model;

import java.util.List;

/**
 * Created by Rahat on 2/21/2016.
 */
public class Road {
    public String RoadName;
    public final Direction direction;
    public List<TrafficLight> lights;

    public Road(String RoadName, Direction direction, List<TrafficLight> lights){
        this.RoadName = RoadName;
        this.direction = direction;
        this.lights = lights;
    }
}
