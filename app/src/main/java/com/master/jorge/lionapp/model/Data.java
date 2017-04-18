package com.master.jorge.lionapp.model;

/**
 * Created by jorge on 15/04/17.
 */

public class Data {
    private Individual data;

    public Individual getData() {
        return data;
    }

    public void setData(Individual data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Data{" +
                "data=" + data +
                '}';
    }
}
