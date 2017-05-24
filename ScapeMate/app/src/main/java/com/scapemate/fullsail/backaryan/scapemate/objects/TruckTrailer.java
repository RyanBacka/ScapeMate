package com.scapemate.fullsail.backaryan.scapemate.objects;

import java.io.Serializable;

public class TruckTrailer implements Serializable{

    String truckTrailerName;
    double truckTrailerUsageHours;
    double truckTrailerUsageCost;
    double truckTrailerMaintCost;

    public TruckTrailer(String truckTrailerName, double truckTrailerUsageHours, double truckTrailerUsageCost, double truckTrailerMaintCost){
        this.truckTrailerName = truckTrailerName;
        this.truckTrailerUsageHours = truckTrailerUsageHours;
        this.truckTrailerUsageCost = truckTrailerUsageCost;
        this.truckTrailerMaintCost = truckTrailerMaintCost;
    }

    public String getTruckTrailerName() {
        return truckTrailerName;
    }

    public double getTruckTrailerUsageHours() {
        return truckTrailerUsageHours;
    }

    public double getTruckTrailerUsageCost() {
        return truckTrailerUsageCost;
    }

    public double getTruckTrailerMaintCost() {
        return truckTrailerMaintCost;
    }
}
