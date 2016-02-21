package com.plp.signalingsystem.model;

public class TrafficLight {
    public float timingInterval;
    public LightStatus status;
    public final Direction direction;

    public TrafficLight(float timingInterval, LightStatus status, Direction direction) {
        this.timingInterval = timingInterval;
        this.status = status;
        this.direction = direction;
    }
}
