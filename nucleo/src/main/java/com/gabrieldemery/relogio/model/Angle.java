package com.gabrieldemery.relogio.model;

public class Angle {

    private final Double angle;

    public Angle(Double angle) {
        this.angle = angle;
    }

    public Double getAngle() {
        return this.angle;
    }

    @Override
    public String toString() {
        return "Ângulo{ângulo=" + this.angle + "}";
    }
}

