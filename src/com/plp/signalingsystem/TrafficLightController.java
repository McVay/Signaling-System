package com.plp.signalingsystem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.*;
import com.plp.signalingsystem.model.Intersection;
import com.plp.signalingsystem.model.LightState;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class TrafficLightController {
    private volatile ArrayList<Intersection> intersections = new ArrayList<>();
    private GUIController GUI;
    private volatile boolean simulationIsOn = false;
    private volatile int mTimeScale = 1;
    private Map<String, LightStatusThread> threadPool =  new HashMap<>();
    private String lightJson;

    TrafficLightController(GUIController gui) {
        this.GUI = gui;
    }

    public void setTimeScale(int scale)
    {
        this.mTimeScale = scale;
    }

    private ArrayList<Intersection> initializeIntersections() throws Exception {

        Gson GsonConverter = new Gson();
        lightJson = readUrl("http://traffic.jordankeefe.com/Submissions/LightStates.json");
        return  GsonConverter.fromJson(lightJson, new TypeToken<ArrayList<Intersection>>() {}.getType());
    }
    class CheckJSON extends TimerTask {
        public void run() {
            try {
                checkJSON();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    private void checkJSON() throws Exception {
        String json =  readUrl("http://traffic.jordankeefe.com/Submissions/LightStates.json");
        if(!json.equals(lightJson))
        {
            Gson GsonConverter = new Gson();
            lightJson = json;
            ArrayList<Intersection> intersections = GsonConverter.fromJson(lightJson, new TypeToken<ArrayList<Intersection>>() {}.getType());
            for(Intersection i: intersections){
                LightStatusThread t = threadPool.get(i.getIntersectionName());
                String oldPattern = GsonConverter.toJson(t.intersection.getmLightStates());
                String newPattern = GsonConverter.toJson(i.getmLightStates());

                if(!oldPattern.equals(newPattern))
                {
                    System.out.println("The light pattern for " + t.intersection.getIntersectionName() + " has changed!");
                    System.out.println("The old pattern was: " + oldPattern);
                    t.intersection.setmLightStates(i.getmLightStates());
                    System.out.println("The new pattern is: " + newPattern);

                }
            }
        }
    }

    public void startSimulation() throws Exception {

        simulationIsOn = true;
        intersections = initializeIntersections();

        for(Intersection i: intersections) {
                LightStatusThread thread = new LightStatusThread(i);
                threadPool.put(i.getIntersectionName(),thread);
                thread.start();
        }
        new Thread(() -> {
            Timer timer = new Timer();
            timer.schedule(new CheckJSON(), 0, 5000);
        }).run();
    }

    public void stopSimulation(){
        simulationIsOn = false;

        Iterator<Map.Entry<String,LightStatusThread>> iterator = threadPool.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String,LightStatusThread> entry = iterator.next();
            entry.getValue().interrupt();
            iterator.remove();
        }

        GUI.turnAllLightsOff();
    }

    private class LightStatusThread extends Thread  {
        private volatile Intersection intersection;

        private LightStatusThread(Intersection i){
            this.intersection = i;
        }

        @Override
        public void run() {
            while (simulationIsOn) {
                for(LightState s: intersection.getmLightStates())
                {
                    long startTime = System.currentTimeMillis();
                    new Thread(()-> {
                        try {
                            GUI.changeLightColor(this.intersection, s.getLightPattern());
                        } catch (ClassNotFoundException ex) {
                            System.out.println(ex.getMessage());
                    }}).start();

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


    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder builder = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                builder.append(chars, 0, read);

            return builder.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
