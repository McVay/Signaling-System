package com.plp.signalingsystem.model;

public class TrafficLight {
    public final String name;
    public int timingInterval;
    public LightStatus status;
    public final Direction direction;

    public TrafficLight() {
        this.name = "";
        this.direction = Direction.NULL;
    }
    public TrafficLight(String name, int timingInterval, LightStatus status, Direction direction) {
        this.name = name;
        this.timingInterval = timingInterval;
        this.status = status;
        this.direction = direction;
    }
}
