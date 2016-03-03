package com.plp.signalingsystem.model;

public class TrafficLight {
    public final String Name;
    public int TimingInterval;
    public LightStatus Status;
    public final Direction LightDirection;

    public TrafficLight() {
        this.Name = "";
        this.LightDirection = Direction.NULL;
    }
    public TrafficLight(String name, int timingInterval, LightStatus status, Direction direction) {
        this.Name = name;
        this.TimingInterval = timingInterval;
        this.Status = status;
        this.LightDirection = direction;
    }
}
