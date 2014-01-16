package com.startandroid.task.ru.geotask;

import java.io.Serializable;

public class LatLngSerializable implements Serializable {
    private double la, lo;

    public double getLa() {
        return la;
    }

    public void setLa(double la) {
        this.la = la;
    }

    public double getLo() {
        return lo;
    }

    public void setLo(double lo) {
        this.lo = lo;
    }

    public LatLngSerializable(double la, double lo) {

        this.la = la;
        this.lo = lo;
    }
}
