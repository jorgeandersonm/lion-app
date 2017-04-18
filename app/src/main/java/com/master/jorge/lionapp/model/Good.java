package com.master.jorge.lionapp.model;

/**
 * Created by jorge on 16/04/17.
 */

public class Good {
    private String name;
    private String category;
    private int id;
    private int individual_id;
    private Double value;

    public Good() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndividual_id() {
        return individual_id;
    }

    public void setIndividual_id(int individual_id) {
        this.individual_id = individual_id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Good: " + name + System.lineSeparator()  +
                "Value: " + value ;
    }
}
