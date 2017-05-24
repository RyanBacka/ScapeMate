package com.scapemate.fullsail.backaryan.scapemate;

import com.scapemate.fullsail.backaryan.scapemate.objects.Bid;
import com.scapemate.fullsail.backaryan.scapemate.objects.Company;
import com.scapemate.fullsail.backaryan.scapemate.objects.Employee;
import com.scapemate.fullsail.backaryan.scapemate.objects.Equipment;
import com.scapemate.fullsail.backaryan.scapemate.objects.Materials;
import com.scapemate.fullsail.backaryan.scapemate.objects.TruckTrailer;

import java.util.ArrayList;

public class BidHelper {

    public double getCrewAverageWage(Bid bid){
        ArrayList<Employee> employees = bid.getEmployees();
        double costOfEmployee = 0;
        for (Employee employee:employees){
            costOfEmployee = costOfEmployee+employee.getEmployeeWage();
        }

        return costOfEmployee/employees.size();
    }

    public double getOvertimeFactor(Bid bid){
        double crewAverageWage = getCrewAverageWage(bid);

        return crewAverageWage*0.1;
    }

    public double getRiskFactor(Bid bid){
        double crewAverageWage = getCrewAverageWage(bid);
        double overtimeFactor = getOvertimeFactor(bid);

        return (crewAverageWage+overtimeFactor)*0.1;
    }

    public double getLaborBurden(Bid bid){
        double crewAverageWage = getCrewAverageWage(bid);
        double overtimeFactor = getOvertimeFactor(bid);
        double riskFactor = getRiskFactor(bid);

        return (crewAverageWage+overtimeFactor+riskFactor)*bid.getJobHours();
    }

    public double getEquipCost(Company company, Bid bid){

        ArrayList<Equipment> equipment = bid.getEquipment();
        double runningEquipCost = 0;
        for (Equipment equip:equipment){
            double weeklyUsageCost = equip.getWeeklyUsageCost();
            double weeklyUsageHours = equip.getWeeklyUsageHours();
            runningEquipCost = (weeklyUsageCost/weeklyUsageHours)+runningEquipCost;
        }
        ArrayList<TruckTrailer> truckTrailers = bid.getTrucks();
        double runningTruckCost = 0;
        for(TruckTrailer truck:truckTrailers){
            double weeklyUsageCost = truck.getTruckTrailerUsageCost();
            double weeklyUsageHours = truck.getTruckTrailerUsageHours();
            runningTruckCost = (weeklyUsageCost/weeklyUsageHours)+runningTruckCost;
        }
        return(runningEquipCost*bid.getJobHours())+(runningTruckCost*bid.getJobHours());
    }

    public double getMaintCost(Company company, Bid bid){
        double maintCost = company.getHourlyMaint();
        ArrayList<Equipment> equipment = bid.getEquipment();
        for (Equipment equip:equipment){
            double weeklyUsageHours = equip.getWeeklyUsageHours();
            double weeklyMaintCost = equip.getWeeklyMaintCost();
            maintCost = (weeklyMaintCost/weeklyMaintCost)+maintCost;
        }
        ArrayList<TruckTrailer> truckTrailers = bid.getTrucks();
        for(TruckTrailer truck:truckTrailers){

            double weeklyUsageHours = truck.getTruckTrailerUsageHours();
            double weeklyMaintCost = truck.getTruckTrailerMaintCost();
            maintCost = (weeklyMaintCost/weeklyUsageHours)+maintCost;
        }

        return maintCost*bid.getJobHours();
    }

    public double getManRate(Company company, Bid bid){
        double laborBurden = getLaborBurden(bid);
        double equipCost = getEquipCost(company,bid);
        double maintCost = getMaintCost(company,bid);

        return laborBurden+equipCost+maintCost;
    }

    public double getMaterialCost(Bid bid){
        ArrayList<Materials> materials = bid.getMaterials();
        double totalCostOfMaterials = 0;
        for(Materials item:materials){
            totalCostOfMaterials = totalCostOfMaterials+item.getCost();
        }
        return totalCostOfMaterials;
    }

    public double getBreakEven(Company company, Bid bid){
        double manRate = getManRate(company,bid);
        double materialCost = getMaterialCost(bid);

        return manRate+materialCost;
    }

    public double getProfit(Company company, Bid bid){
        double breakEven = getBreakEven(company,bid);

        return breakEven*bid.getProfitPercent();
    }

    public double getEstimatePrice(Company company, Bid bid){
        double breakEven = getBreakEven(company,bid);
        double profit = getProfit(company,bid);

        return breakEven+profit;
    }
}
