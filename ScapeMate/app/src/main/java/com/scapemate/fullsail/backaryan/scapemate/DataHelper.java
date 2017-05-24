package com.scapemate.fullsail.backaryan.scapemate;

import android.content.Context;
import android.os.AsyncTask;

import com.scapemate.fullsail.backaryan.scapemate.objects.Company;
import com.scapemate.fullsail.backaryan.scapemate.objects.Employee;
import com.scapemate.fullsail.backaryan.scapemate.objects.Equipment;
import com.scapemate.fullsail.backaryan.scapemate.objects.Materials;
import com.scapemate.fullsail.backaryan.scapemate.objects.TruckTrailer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataHelper {
    private static final String EMPLOYEE_REF = "com.scapemate.fullsail.backaryan.scapemate.EMPLOYEE";
    private static final String EQUIPMENT_REF = "com.scapemate.fullsail.backaryan.scapemate.EQUIPMENT";
    private static final String TRUCK_REF = "com.scapemate.fullsail.backaryan.scapemate.TRUCK";
    private static final String COMPANY_BOOL = "com.scapemate.fullsail.backaryan.scapemate.COMPANY_CREATED";
    private static final String COMPANY_REF = "com.scapemate.fullsail.backaryan.scapemate.COMPANY";
    private static final String MATERIALS_REF = "com.scapemate.fullsail.backaryan.scapemate.MATERIALS";


    public Company readCompany(Context context){
        Company company = new Company();

        try {
            FileInputStream fis = context.openFileInput(COMPANY_REF);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object object = ois.readObject();
            ois.close();
            fis.close();
            if(object instanceof Company){
                company = (Company) object;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return company;
    }

    public ArrayList<Employee> readEmployee(Context context) {
        ArrayList<Employee> employee = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput(EMPLOYEE_REF);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object object = ois.readObject();
            ois.close();
            fis.close();
            if (object instanceof ArrayList<?>) {
                ArrayList<?> list = (ArrayList) object;
                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        Object myObject = list.get(i);
                        if(myObject instanceof Employee){
                            employee.add((Employee) myObject);
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public ArrayList<Materials> readMaterials(Context context) {
        ArrayList<Materials> materialsList = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput(MATERIALS_REF);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object object = ois.readObject();
            ois.close();
            fis.close();
            if (object instanceof ArrayList<?>) {
                ArrayList<?> list = (ArrayList) object;
                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        Object myObject = list.get(i);
                        if(myObject instanceof Materials){
                            materialsList.add((Materials) myObject);
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return materialsList;
    }

    public ArrayList<Equipment> readEquipment(Context context) {
        ArrayList<Equipment> equipment = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput(EQUIPMENT_REF);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object object = ois.readObject();
            ois.close();
            fis.close();
            if (object instanceof ArrayList<?>) {
                ArrayList<?> list = (ArrayList) object;
                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        Object myObject = list.get(i);
                        if(myObject instanceof Equipment){
                            equipment.add((Equipment) myObject);
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return equipment;
    }

    public ArrayList<TruckTrailer> readTruck(Context context) {
        ArrayList<TruckTrailer> truck = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput(TRUCK_REF);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object object = ois.readObject();
            ois.close();
            fis.close();
            if (object instanceof ArrayList<?>) {
                ArrayList<?> list = (ArrayList) object;
                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        Object myObject = list.get(i);
                        if(myObject instanceof TruckTrailer){
                            truck.add((TruckTrailer) myObject);
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return truck;
    }

    public boolean companyCreatedRead(Context context){
        boolean created = false;
        try {
            FileInputStream fis = context.openFileInput(COMPANY_BOOL);
            DataInputStream dis = new DataInputStream(fis);
            created = dis.readBoolean();
            dis.close();
            fis.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return created;
    }

    public void companyCreated(boolean created, Context c){
        try{
            FileOutputStream fos = c.openFileOutput(COMPANY_BOOL, Context.MODE_PRIVATE);
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeBoolean(created);
            dos.close();
            fos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void saveEmployee(ArrayList<Employee> currentObject, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(EMPLOYEE_REF, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(currentObject);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveMaterials(ArrayList<Materials> currentObject, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(MATERIALS_REF, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(currentObject);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveEquipment(ArrayList<Equipment> currentObject, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(EQUIPMENT_REF, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(currentObject);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCompany(Company company, Context context){
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(COMPANY_REF, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(company);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveTruck(ArrayList<TruckTrailer> currentObject, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(TRUCK_REF, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(currentObject);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
