package com.plp.signalingsystem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.plp.signalingsystem.model.Intersection;
import com.plp.signalingsystem.model.LightStatus;
import com.plp.signalingsystem.model.TrafficLight;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class TrafficLightController {
    private GUIController GUI;
    volatile boolean simulationIsOn = false;
    volatile int mTimeScale = 1;


    TrafficLightController(GUIController gui) {
        this.GUI = gui;
    }
    public void setTimeScale(int scale)
    {
        this.mTimeScale = scale;
    }

    public ArrayList<Intersection> initializeIntersections() {

        Gson GsonConverter = new Gson();

        try {
            String currentDir = System.getProperties().getProperty("user.dir");
            JsonReader reader = new JsonReader(new FileReader(currentDir + "\\src\\com\\plp\\signalingsystem\\data\\lights.json"));
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

        ArrayList<Intersection> intersection = initializeIntersections();
        for(Intersection i: intersection){
            for(TrafficLight l: i.getLights()){
                if(l.getStatus() == LightStatus.Green){
                    i.setCurrentLight(l);
                }
                try {
                    GUI.changeLightColor(l);
                } catch (ClassNotFoundException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            LightStatusThread thread = new LightStatusThread(i.getCurrentLight(), i.getLights());
            thread.start();
        }
    }

    public void stopSimulation(){
        simulationIsOn = false;
        GUI.turnAllLightsOff();
    }

    public class LightStatusThread extends Thread {
        private TrafficLight currentLight;
        private  ArrayList<TrafficLight> lights;

        public LightStatusThread(TrafficLight currentLight, ArrayList<TrafficLight> lights){
            this.currentLight = currentLight;
            this.lights = lights;
        }

        @Override
        public void run() {
            while (simulationIsOn) {
                int max = lights.size();
                try {
                    GUI.changeLightColor(currentLight);

                    long timeGreen = System.currentTimeMillis();
                    while(true) {
                        if(!simulationIsOn) {
                            return;
                        }
                        long duration = System.currentTimeMillis() - timeGreen;
                        if(duration >= currentLight.getTimingInterval() * 1000/mTimeScale) {
                            currentLight.setStatus(LightStatus.Yellow);
                            GUI.changeLightColor(currentLight);
                            break;
                        }
                    }

                    long timeYellow = System.currentTimeMillis();
                    while(true) {
                        if(!simulationIsOn) {
                            return;
                        }
                        long duration = System.currentTimeMillis() - timeYellow;
                        if(duration >= 3000/mTimeScale) {
                            currentLight.setStatus(LightStatus.Red);
                            GUI.changeLightColor(currentLight);
                            break;
                        }
                    }

                    if(simulationIsOn) {
                        int index = lights.indexOf(currentLight);
                        index++;

                        if (index >= max)
                            index = 0;

                        currentLight = lights.get(index);
                        currentLight.setStatus(LightStatus.Green);
                    }
                } catch (ClassNotFoundException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}
