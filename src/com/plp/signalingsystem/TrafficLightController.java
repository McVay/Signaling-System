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

    public void startSimulation(){

        simulationIsOn = true;

        ArrayList<Intersection> intersection = initializeIntersections();
        for(Intersection i: intersection){
            for(TrafficLight l: i.Lights){
                if(l.Status == LightStatus.GREEN){
                    i.CurrentLight = l;
                    break;
                }
            }
            LightStatusThread thread = new LightStatusThread(i.CurrentLight, i.Lights);
            thread.start();
        }
    }

    public void stopSimulation(){
        simulationIsOn = false;
    }

    public class LightStatusThread extends Thread {
        private TrafficLight currentLight;
        private  ArrayList<TrafficLight> lights;

        public LightStatusThread(){ }

        public LightStatusThread(TrafficLight currentLight, ArrayList<TrafficLight> lights){
            this.currentLight = currentLight;
            this.lights = lights;
        }

        @Override
        public void run() {
            while (simulationIsOn) {
                int max = lights.size();
                try {
                    printLightStatus(currentLight);

                    Thread.sleep(currentLight.TimingInterval * 1000);
                    currentLight.Status = LightStatus.YELLOW;
                    printLightStatus(currentLight);

                    Thread.sleep(3000);

                    currentLight.Status = LightStatus.RED;
                    printLightStatus(currentLight);

                    int index = lights.indexOf(currentLight);
                    index++;

                    if (index >= max)
                        index = 0;

                    currentLight = lights.get(index);
                    currentLight.Status = LightStatus.GREEN;

                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void printLightStatus(TrafficLight light){
            System.out.println(light.Name + ": " + light.Status);
    }
}
