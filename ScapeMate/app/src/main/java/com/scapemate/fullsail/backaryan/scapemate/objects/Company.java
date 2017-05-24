package com.scapemate.fullsail.backaryan.scapemate.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Company implements Serializable{

    String companyName;
    ArrayList<Employee> employees;
    ArrayList<Equipment> equipment;
    ArrayList<TruckTrailer> trucksTrailers;
    ArrayList<Bid> bids;
    double hourlyMaint;

    public Company(){

    }

    public Company(String companyName, ArrayList<Employee> employees, ArrayList<Equipment> equipment, ArrayList<TruckTrailer> trucksTrailers, double hourlyMaint){
        this.companyName = companyName;
        this.employees = employees;
        this.equipment = equipment;
        this.trucksTrailers = trucksTrailers;
        this.hourlyMaint = hourlyMaint;
    }

    public void addBid(ArrayList<Bid> bids){
        this.bids = bids;
    }

    public ArrayList<Bid> getBids() {
        if(bids==null){
            return new ArrayList<>();
        } else {
            return bids;
        }
    }

    public void setEmployees(ArrayList<Employee> employees){
        this.employees=employees;
    }

    public void setEquipment(ArrayList<Equipment> equipment){
        this.equipment = equipment;
    }

    public void setTrucksTrailers(ArrayList<TruckTrailer> trucksTrailers){
        this.trucksTrailers = trucksTrailers;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public ArrayList<Equipment> getEquipment() {
        return equipment;
    }

    public ArrayList<TruckTrailer> getTrucksTrailers() {
        return trucksTrailers;
    }

    public double getHourlyMaint() {
        return hourlyMaint;
    }

    public String getCompanyName() {
        return companyName;
    }
}
