package com.plp.signalingsystem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.plp.signalingsystem.model.Intersection;
import com.plp.signalingsystem.model.LightState;

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


    public void startSimulation() {

        simulationIsOn = true;

        intersections = initializeIntersections();
        for(Intersection i: intersections) {
                LightStatusThread thread = new LightStatusThread(i);
                thread.start();
        }
    }

    public void stopSimulation(){
        simulationIsOn = false;
        GUI.turnAllLightsOff();
    }

    private class LightStatusThread extends Thread  {
        private Intersection intersection;

        private LightStatusThread(Intersection i){
            this.intersection = i;
        }

        @Override
        public void run() {
            while (simulationIsOn) {
                for(LightState s: intersection.getmLightStates())
                {
                    long startTime = System.currentTimeMillis();

                    new Thread(() -> {
                        try {
                            GUI.changeLightColor(this.intersection, s.getLightSequence());
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }).start();

                    while(((s.getDuration()*1000)/mTimeScale) >= System.currentTimeMillis() - startTime)
                    {
                        if(!simulationIsOn) {
                            return;
                        }
                    }
                }
            }
        }
    }
}
