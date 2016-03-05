package com.plp.signalingsystem.model;
import javafx.scene.image.ImageView;

/**
 * Created by Dylan on 3/4/2016.
 */
public class StoplightUIElement {
    public ImageView GridLight;
    public ImageView RoadLight;

    public StoplightUIElement(ImageView gridLight, ImageView roadLight){
        this.GridLight = gridLight;
        this.RoadLight = roadLight;
    }
}
