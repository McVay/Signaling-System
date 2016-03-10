package com.plp.signalingsystem.model;
import javafx.scene.image.ImageView;

public class StoplightUIElement {
    private ImageView mGridLight;
    private ImageView mRoadLight;

    public StoplightUIElement(ImageView gridLight, ImageView roadLight){
        this.mGridLight = gridLight;
        this.mRoadLight = roadLight;
    }

    public ImageView getGridLight() {
        return mGridLight;
    }

    public void setGridLight(ImageView gridLight) {
        mGridLight = gridLight;
    }

    public ImageView getRoadLight() {
        return mRoadLight;
    }

    public void setRoadLight(ImageView roadLight) {
        mRoadLight = roadLight;
    }
}
