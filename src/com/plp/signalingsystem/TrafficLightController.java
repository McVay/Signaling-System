package com.plp.signalingsystem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.plp.signalingsystem.model.Intersection;
import com.plp.signalingsystem.model.LightState;
import com.plp.signalingsystem.model.TrafficLight;

import java.io.*;
import java.util.ArrayList;

public class TrafficLightController {
    private volatile ArrayList<Intersection> intersections = new ArrayList<Intersection>();
    private GUIController GUI;
    private volatile boolean simulationIsOn = false;
    private volatile int mTimeScale = 1;

    TrafficLightController(GUIController gui) {
        this.GUI = gui;
    }

    public void setTimeScale(int scale)
    {
        this.mTimeScale = scale;
    }

    private ArrayList<Intersection> initializeIntersections() {

        Gson GsonConverter = new Gson();

        try {
            String currentDir = System.getProperties().getProperty("user.dir");
            JsonReader reader = new JsonReader(new FileReader(currentDir + "/src/com/plp/signalingsystem/data/lights.json"));
            return  GsonConverter.fromJson(reader, new TypeToken<ArrayList<Intersection>>() {}.getType());
        }
        catch (FileNotFoundException ex) {
            ex.getCause();
            System.out.println("Light configuration file not found.\nStopping simulation.");
            stopSimulation();
        }

        return null;
    }

    private ArrayList<LightState> initializeLightStates() {

        Gson GsonConverter = new Gson();

        try {
            String currentDir = System.getProperties().getProperty("user.dir");
            JsonReader reader = new JsonReader(new FileReader(currentDir + "/src/com/plp/signalingsystem/data/lightStates.json"));
            return  GsonConverter.fromJson(reader, new TypeToken<ArrayList<LightState>>() {}.getType());
        }
        catch (FileNotFoundException ex) {
            ex.getCause();
            System.out.println("Light state configuration file not found.\nStopping simulation.");
            stopSimulation();
        }

        return null;
    }

    public void startSimulation() {

        simulationIsOn = true;

        intersections = initializeIntersections();
        ArrayList<LightState> lightStates = initializeLightStates();
        for(Intersection i: intersections) {

                ArrayList<LightState> states = new ArrayList<LightState>();
                for(LightState l: lightStates) {
                    if(i.getIntersectionName().equals(l.getIntersectionName())) {
                        states.add(l);
                    }
                }

                LightStatusThread thread = new LightStatusThread(i, states);
                thread.start();
        }
    }

    public void stopSimulation(){
        simulationIsOn = false;
        GUI.turnAllLightsOff();
    }

    private class LightStatusThread extends Thread  {
        private ArrayList<LightState> lightStates;
        private Intersection intersection;

        private LightStatusThread(Intersection i, ArrayList<LightState> lightStates){
            this.lightStates = lightStates;
            this.intersection = i;
        }

        @Override
        public void run() {
            while (simulationIsOn) {
                for(LightState s: lightStates)
                {
                    long startTime = System.currentTimeMillis();

                    try {
                        GUI.changeLightColor(this.intersection, s.getLightSequence());
                    } catch (ClassNotFoundException ex) {
                        System.out.println(ex.getMessage());
                    }

                    do{
                        if(!simulationIsOn) {
                            return;
                        }
                    }
                    while(((s.getDuration()*1000)/mTimeScale) >= System.currentTimeMillis() - startTime);
                }
            }
        }
    }
}
