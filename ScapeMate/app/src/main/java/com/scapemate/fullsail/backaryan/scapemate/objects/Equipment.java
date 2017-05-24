package com.scapemate.fullsail.backaryan.scapemate.objects;

import java.io.Serializable;

public class Equipment implements Serializable {

    String equipmentName;
    double weeklyUsageHours;
    double weeklyUsageCost;
    double weeklyMaintCost;
    boolean owned;

    public Equipment(String _equipmentName, double _weeklyUsageHours, double _weeklyUsageCost, double _weeklyMaintCost, boolean _owned){
        equipmentName = _equipmentName;
        weeklyUsageHours = _weeklyUsageHours;
        weeklyUsageCost = _weeklyUsageCost;
        weeklyMaintCost = _weeklyMaintCost;
        owned = _owned;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public double getWeeklyUsageHours() {
        return weeklyUsageHours;
    }

    public double getWeeklyUsageCost() {
        return weeklyUsageCost;
    }

    public double getWeeklyMaintCost() {
        return weeklyMaintCost;
    }

}
