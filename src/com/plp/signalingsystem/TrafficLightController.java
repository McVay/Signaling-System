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
    volatile boolean simulationIsOn = false;
    int mTimeScale = 1;

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

    public void startSimulation(GUIController GUI) {

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
            LightStatusThread thread = new LightStatusThread(i.getCurrentLight(), i.getLights(), GUI);
            thread.start();
        }
    }

    public void stopSimulation(){
        simulationIsOn = false;
    }

    public class LightStatusThread extends Thread {
        private TrafficLight currentLight;
        private  ArrayList<TrafficLight> lights;
        private GUIController GUI;

        public LightStatusThread(TrafficLight currentLight, ArrayList<TrafficLight> lights, GUIController GUI){
            this.currentLight = currentLight;
            this.lights = lights;
            this.GUI = GUI;
        }

        @Override
        public void run() {
            while (simulationIsOn) {
                int max = lights.size();
                try {
                    GUI.changeLightColor(currentLight);

                    Thread.sleep((currentLight.getTimingInterval() * 1000)/mTimeScale);
                    currentLight.setStatus(LightStatus.Yellow);
                    GUI.changeLightColor(currentLight);

                    Thread.sleep(3000/mTimeScale);

                    currentLight.setStatus(LightStatus.Red);
                    GUI.changeLightColor(currentLight);

                    int index = lights.indexOf(currentLight);
                    index++;

                    if (index >= max)
                        index = 0;

                    currentLight = lights.get(index);
                    currentLight.setStatus(LightStatus.Green);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                } catch (ClassNotFoundException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}
