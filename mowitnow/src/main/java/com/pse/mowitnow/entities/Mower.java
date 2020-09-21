package com.pse.mowitnow.entities;

import com.pse.mowitnow.business.Orientation;

public class Mower {

    private int mowerId;
    private Position position;
    private Orientation orientation;


    public Mower(int mowerId, Position position, Orientation orientation) {
        this.position = position;
        this.orientation = orientation;
        this.mowerId = mowerId;
    }



    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public int getMowerId() {
        return mowerId;
    }

    @Override
    public String toString() {
        return this.position.getX() + " " + this.position.getY() + " " + this.orientation.name();
    }
}
