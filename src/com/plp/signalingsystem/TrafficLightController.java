package com.plp.signalingsystem;

import com.google.gson.Gson;
import com.plp.signalingsystem.model.Direction;
import com.plp.signalingsystem.model.LightStatus;
import com.plp.signalingsystem.model.TrafficLight;

public class TrafficLightController {
    public void convertToJson(){
        Gson gson = new Gson();
        TrafficLight testToJson = new TrafficLight(3, LightStatus.GREEN, Direction.NORTH);

        String json = gson.toJson(testToJson);
        System.out.println("JSON: " + json);
    }
}
