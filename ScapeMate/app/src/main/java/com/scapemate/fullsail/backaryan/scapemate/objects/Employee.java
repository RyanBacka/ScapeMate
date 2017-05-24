package com.scapemate.fullsail.backaryan.scapemate.objects;

import java.io.Serializable;

public class Employee implements Serializable {

    String employeeName;
    double employeeWage;
    double employeeHours;

    public Employee(String _employeeName, double _employeeWage, double _employeeHours){
        employeeName = _employeeName;
        employeeWage = _employeeWage;
        employeeHours = _employeeHours;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public double getEmployeeWage() {
        return employeeWage;
    }

    public double getEmployeeHours() {
        return employeeHours;
    }
}
