package com.plp.signalingsystem;
import com.plp.signalingsystem.model.Direction;
import com.plp.signalingsystem.model.Intersection;
import com.plp.signalingsystem.model.LightStatus;
import com.plp.signalingsystem.model.TrafficLight;

import java.util.ArrayList;

public class TrafficLightController {
    boolean simulationIsOn = false;
    LightStatusThread thread = new LightStatusThread();

    public Intersection initializeIntersections() {

        TrafficLight northBound = new TrafficLight("Northbound Light", 30,LightStatus.GREEN,  Direction.NORTH);
        TrafficLight southBound = new TrafficLight("Southbound Light", 30,LightStatus.RED,  Direction.SOUTH);
        TrafficLight eastBound = new TrafficLight("Eastbound Light", 10,LightStatus.RED,  Direction.EAST);
        TrafficLight westBound = new TrafficLight("Westbound Light", 10,LightStatus.RED,  Direction.WEST);

        ArrayList<TrafficLight> intersectionLights = new ArrayList<TrafficLight>();

        intersectionLights.add(northBound);
        intersectionLights.add(southBound);
        intersectionLights.add(eastBound);
        intersectionLights.add(westBound);

        return new Intersection("Four Way Intersection", intersectionLights);
    }

    public void startSimulation(){

        simulationIsOn = true;
        Intersection intersection = initializeIntersections();
        TrafficLight currentLight = new TrafficLight("", 0, LightStatus.RED, Direction.NORTH);

        for (TrafficLight t : intersection.lights) {
            if (t.status == LightStatus.GREEN) {
                currentLight = t;
                break;
            }
        }

        printLightStatus(currentLight);
        thread = new LightStatusThread(currentLight,intersection.lights);
        thread.start();
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
                    Thread.sleep(currentLight.timingInterval * 1000);
                    currentLight.status = LightStatus.YELLOW;
                    printLightStatus(currentLight);

                    Thread.sleep(3000);

                    currentLight.status = LightStatus.RED;
                    printLightStatus(currentLight);

                    int index = lights.indexOf(currentLight);
                    index++;

                    if (index >= max)
                        index = 0;

                    currentLight = lights.get(index);
                    currentLight.status = LightStatus.GREEN;

                    printLightStatus(currentLight);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void printLightStatus(TrafficLight light){
            System.out.println(light.name + ": " + light.status);
    }
}
