package com.scapemate.fullsail.backaryan.scapemate.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Bid implements Serializable {

    String bidNum;
    String bidDate;
    String customerName;
    String customerAddress;
    String customerPhoneNum;
    String jobType;
    int bidStatus;
    double jobHours;
    double profitPercent;
    ArrayList<Materials> materials;
    ArrayList<Employee> jobEmployees;
    ArrayList<Equipment> equipmentUsed;
    ArrayList<TruckTrailer> trucksUsed;


    public Bid(){
    }

    public Bid(String bidNum, String bidDate, String customerName, String customerAddress,
               String customerPhoneNum, String jobType, double jobHours, double profitPercent,
               ArrayList<Employee> jobEmployees, ArrayList<Equipment> equipmentUsed, ArrayList<TruckTrailer> trucksUsed,ArrayList<Materials> materials, int bidStatus){
        this.bidNum = bidNum;
        this.bidDate = bidDate;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhoneNum = customerPhoneNum;
        this.jobType = jobType;
        this.jobHours = jobHours;
        this.profitPercent = profitPercent;
        this.jobEmployees = jobEmployees;
        this.equipmentUsed = equipmentUsed;
        this.trucksUsed = trucksUsed;
        this.bidStatus = bidStatus;
        this.materials=materials;
    }

    public void updateBidStatus(int status){
        bidStatus=status;
    }

    public ArrayList<Employee> getEmployees() {
        return jobEmployees;
    }

    public ArrayList<Equipment> getEquipment() {
        return equipmentUsed;
    }

    public ArrayList<Materials> getMaterials() {
        return materials;
    }

    public ArrayList<TruckTrailer> getTrucks() {
        return trucksUsed;
    }

    public double getJobHours() {
        return jobHours;
    }

    public double getProfitPercent() {
        return profitPercent;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getBidStatus() {
        return bidStatus;
    }

    public String getBidNum() {
        return bidNum;
    }

    public String getJobType() {
        return jobType;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getCustomerPhoneNum() {
        return customerPhoneNum;
    }
}
