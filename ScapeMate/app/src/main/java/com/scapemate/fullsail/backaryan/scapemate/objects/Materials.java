package com.scapemate.fullsail.backaryan.scapemate.objects;

import java.io.Serializable;

public class Materials implements Serializable{
    double cubicYards;
    double cost;
    String typeOfMaterial;

    public Materials(double cubicYards, double cost, String typeOfMaterial){
        this.cubicYards = cubicYards;
        this.cost = cost;
        this.typeOfMaterial = typeOfMaterial;
    }

    public double getCost() {
        return cost;
    }
}
