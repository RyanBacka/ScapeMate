package com.scapemate.fullsail.backaryan.scapemate.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.scapemate.fullsail.backaryan.scapemate.DataHelper;
import com.scapemate.fullsail.backaryan.scapemate.R;
import com.scapemate.fullsail.backaryan.scapemate.objects.Bid;
import com.scapemate.fullsail.backaryan.scapemate.objects.Company;
import com.scapemate.fullsail.backaryan.scapemate.objects.Employee;
import com.scapemate.fullsail.backaryan.scapemate.objects.Equipment;
import com.scapemate.fullsail.backaryan.scapemate.objects.Materials;
import com.scapemate.fullsail.backaryan.scapemate.objects.TruckTrailer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

public class NewBIdFragment extends Fragment implements View.OnClickListener {


    private static final String TAG = "NewBidFragment";
    private static final String SAVED = "com.scapemate.fullsail.backaryan.scapemate.SAVED";
    DataHelper dataHelper;
    Company company;
    int checkId = 0;
    ArrayList<Employee> employees = new ArrayList<>();
    ArrayList<Equipment> equipment = new ArrayList<>();
    ArrayList<TruckTrailer> truckTrailers = new ArrayList<>();
    View rootView;

    public NewBIdFragment() {
        // Required empty public constructor
    }

    public static NewBIdFragment newInstance() {
        return new NewBIdFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_new_bid, container, false);
        (rootView.findViewById(R.id.addMaterials)).setOnClickListener(this);
        (rootView.findViewById(R.id.newBidSave)).setOnClickListener(this);
        dataHelper = new DataHelper();
        company = dataHelper.readCompany(getActivity());
        ArrayList<Bid> bids = company.getBids();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat bidNumFormat = new SimpleDateFormat("ddMMyyyy");
        DateFormat dateFormat = DateFormat.getDateInstance();
        String bidNum = bidNumFormat.format(calendar.getTime());
        bidNum = bidNum + "-" + bids.size();
        EditText bidNumET = ((EditText) rootView.findViewById(R.id.bidNumET));
        bidNumET.setText(bidNum, TextView.BufferType.EDITABLE);
        EditText dateET = ((EditText) rootView.findViewById(R.id.bidDateET));
        String date = dateFormat.format(calendar.getTime());
        dateET.setText(date);

        employees = company.getEmployees();
        equipment = company.getEquipment();
        truckTrailers = company.getTrucksTrailers();
        ViewGroup employeesContainer = (ViewGroup) rootView.findViewById(R.id.employeeLayout);
        ViewGroup equipmentContainer = (ViewGroup) rootView.findViewById(R.id.equipmentLayout);
        for (int i = 0; i < employees.size(); i++) {
            CheckBox checkBox = new CheckBox(getActivity());
            Employee employee = employees.get(i);
            checkBox.setId(checkId);
            checkBox.setText(employee.getEmployeeName());
            employeesContainer.addView(checkBox);
            checkId++;
        }
        for (int i = 0; i < equipment.size(); i++) {
            CheckBox checkBox = new CheckBox(getActivity());
            Equipment piece = equipment.get(i);
            checkBox.setId(checkId);
            checkBox.setText(piece.getEquipmentName());
            equipmentContainer.addView(checkBox);
            checkId++;
        }
        for (int i = 0; i < truckTrailers.size(); i++) {
            CheckBox checkBox = new CheckBox(getActivity());
            TruckTrailer truckTrailer = truckTrailers.get(i);
            checkBox.setId(checkId);
            checkBox.setText(truckTrailer.getTruckTrailerName());
            equipmentContainer.addView(checkBox);
            checkId++;
        }

        SharedPreferences bidData = getActivity().getSharedPreferences("bidForm", Context.MODE_APPEND);
        if(bidData.getBoolean(SAVED,false)){

            String savedBidNum = bidData.getString("bidNum","");
            Log.d(TAG,savedBidNum);

            if(!savedBidNum.equalsIgnoreCase("")) {
                ((EditText) rootView.findViewById(R.id.bidNumET)).setText(savedBidNum);
            }


            String bidDate = bidData.getString("bidDate","");
            if(!bidDate.equalsIgnoreCase("")) {
                ((EditText) rootView.findViewById(R.id.bidDateET)).setText(bidDate);
            }


            String customerName = bidData.getString("customerName","");
            if(!customerName.equalsIgnoreCase("")) {
                ((EditText) rootView.findViewById(R.id.customerNameET)).setText(customerName);
            }

            String customerAddress = bidData.getString("customerAddress","");
            if(!customerAddress.equalsIgnoreCase("")) {
                ((EditText) rootView.findViewById(R.id.customerAddressET)).setText(customerAddress);
            }

            String customerNumber = bidData.getString("customerNumber","");
            if(!customerName.equalsIgnoreCase("")) {
                ((EditText) rootView.findViewById(R.id.customerPhoneET)).setText(customerNumber);
            }

            String jobHours = bidData.getString("jobHours","");
            if(!jobHours.equalsIgnoreCase("")) {
                ((EditText) rootView.findViewById(R.id.hoursET)).setText(jobHours);
            }

            String profitPercent = bidData.getString("profitPercent","");
            if(!profitPercent.equalsIgnoreCase("")) {
                ((EditText) rootView.findViewById(R.id.enteredProfitPercent)).setText(profitPercent);
            }

            String jobType = bidData.getString("jobType","");
            int position = 0;
            switch (jobType) {
                case "Maintenance":
                    position = 0;
                    break;
                case "Mulch":
                    position = 1;
                    break;
                case "Leaf Removal":
                    position = 2;
                    break;
                case "Seeding":
                    position = 3;
                    break;
                case "Aeration":
                    position = 4;
                    break;
                case "Snow Removal":
                    position = 5;
                    break;
                case "Design":
                    position = 6;
                    break;
            }
            ((Spinner) rootView.findViewById(R.id.jobSpinner)).setSelection(position);

            String selectedCheck = bidData.getString("selectedString", "");
            int selectedSize = bidData.getInt("selected",0);
            int employeesSize = bidData.getInt("employees",0);
            int equipmentSize = bidData.getInt("equipment",0);
            int trucksSize = bidData.getInt("trucks",0);
            StringTokenizer st = new StringTokenizer(selectedCheck, ",");
            ArrayList<Integer> savedList = new ArrayList<>();
            for (int i = 0; i < selectedSize; i++) {
                savedList.add(Integer.parseInt(st.nextToken()));
            }

            if(employeesSize==employees.size() && equipmentSize==equipment.size() && trucksSize==truckTrailers.size()){
                for(Integer i:savedList){
                    CheckBox checkBox = (CheckBox) rootView.findViewById(i);
                    checkBox.setChecked(true);
                }
            } else if(employeesSize<employees.size() || equipmentSize<equipment.size()){
                int empAdder = employees.size()-employeesSize;
                for(Integer i:savedList){
                    int topParam = employees.size()+equipmentSize;
                    if(i>=employeesSize && i<topParam) {
                        int viewId = i+empAdder;
                        CheckBox checkBox = (CheckBox) rootView.findViewById(viewId);
                        checkBox.setChecked(true);
                    } else if(i>=employeesSize+equipmentSize){
                        int truckID = (equipment.size()-equipmentSize)+empAdder+i;
                        CheckBox checkBox = (CheckBox) rootView.findViewById(truckID);
                        checkBox.setChecked(true);
                    } else {
                        CheckBox checkBox = (CheckBox) rootView.findViewById(i);
                        checkBox.setChecked(true);
                    }
                }
            }


        }

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addMaterials) {
            MaterialsCalculatorFragment materialsCalc = MaterialsCalculatorFragment.newInstance();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.listContainer, materialsCalc)
                    .addToBackStack(null)
                    .commit();
        } else if (v.getId() == R.id.newBidSave) {
            ArrayList<Integer> selected = new ArrayList<>();
            ArrayList<Employee> selectedEmployees = new ArrayList<>();
            ArrayList<Equipment> selectedEquipment = new ArrayList<>();
            ArrayList<TruckTrailer> selectedTrucks = new ArrayList<>();
            ArrayList<Materials> materials = dataHelper.readMaterials(getActivity());
            for (int i = 0; i < checkId; i++) {
                CheckBox checkBox = (CheckBox) getActivity().findViewById(i);
                if (checkBox.isChecked()) {
                    Log.d(TAG, "check box checked: " + i);
                    selected.add(i);
                }
            }
            for (Integer i : selected) {
                Log.d(TAG, "selected: " + i);
                if (i < employees.size()) {
                    Log.d(TAG, "employees index: " + i);
                    selectedEmployees.add(employees.get(i));
                } else if (i > employees.size() - 1) {
                    int equipInt = i - (employees.size());
                    Log.d(TAG, "equipment index: " + equipInt);
                    if (equipInt < equipment.size()) {
                        selectedEquipment.add(equipment.get(equipInt));
                    } else if (equipInt > equipment.size() - 1) {
                        int truckInt = equipInt - (equipment.size());
                        Log.d(TAG, "truck index: " + truckInt);
                        selectedTrucks.add(truckTrailers.get(truckInt));
                    }
                }
            }

            if (((EditText) getActivity().findViewById(R.id.bidNumET)).getText().toString().isEmpty() ||
                    ((EditText) getActivity().findViewById(R.id.bidDateET)).getText().toString().isEmpty() ||
                    ((EditText) getActivity().findViewById(R.id.customerNameET)).getText().toString().isEmpty() ||
                    ((EditText) getActivity().findViewById(R.id.customerAddressET)).getText().toString().isEmpty() ||
                    ((EditText) getActivity().findViewById(R.id.customerPhoneET)).getText().toString().isEmpty() ||
                    ((EditText) getActivity().findViewById(R.id.hoursET)).getText().toString().isEmpty() ||
                    ((EditText) getActivity().findViewById(R.id.enteredProfitPercent)).getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "One or more fields are empty", Toast.LENGTH_LONG).show();
            } else {
                String bidNum = ((EditText) rootView.findViewById(R.id.bidNumET)).getText().toString();
                String bidDate = ((EditText) rootView.findViewById(R.id.bidDateET)).getText().toString();
                String customerName = ((EditText) rootView.findViewById(R.id.customerNameET)).getText().toString();
                String customerAddress = ((EditText) rootView.findViewById(R.id.customerAddressET)).getText().toString();
                String customerNumber = ((EditText) rootView.findViewById(R.id.customerPhoneET)).getText().toString();
                String jobType = ((Spinner) getActivity().findViewById(R.id.jobSpinner)).getSelectedItem().toString();
                double jobHours = Double.parseDouble(((EditText) getActivity().findViewById(R.id.hoursET)).getText().toString());
                double profitPercent = Double.parseDouble(((EditText) getActivity().findViewById(R.id.enteredProfitPercent)).getText().toString()) / 100;

                // Still need to add materials if job requires materials once MaterialsCalcFrag is set up

                Bid newBid = new Bid(bidNum, bidDate, customerName, customerAddress, customerNumber, jobType, jobHours, profitPercent, selectedEmployees, selectedEquipment, selectedTrucks, materials, 0);
                ArrayList<Bid> bids = company.getBids();
                bids.add(newBid);
                company.addBid(bids);
                dataHelper.saveCompany(company, getActivity());

                ((EditText) rootView.findViewById(R.id.bidNumET)).getText().clear();
                ((EditText) rootView.findViewById(R.id.bidDateET)).getText().clear();
                ((EditText) rootView.findViewById(R.id.customerNameET)).getText().clear();
                ((EditText) rootView.findViewById(R.id.customerAddressET)).getText().clear();
                ((EditText) rootView.findViewById(R.id.customerPhoneET)).getText().clear();
                ((Spinner) rootView.findViewById(R.id.jobSpinner)).setSelection(0);
                ((EditText) rootView.findViewById(R.id.hoursET)).getText().clear();
                ((EditText) rootView.findViewById(R.id.enteredProfitPercent)).getText().clear();


                CompletedBidFragment completedBidFragment = CompletedBidFragment.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.listContainer, completedBidFragment)
                        .commit();
            }
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_main_setting2).setVisible(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        ArrayList<Integer> selected = new ArrayList<>();
        for (int i = 0; i < checkId; i++) {
            CheckBox checkBox = (CheckBox) getActivity().findViewById(i);
            if (checkBox.isChecked()) {
                selected.add(i);
            }
        }
        SharedPreferences.Editor outState = getActivity().getSharedPreferences("bidForm", Context.MODE_APPEND).edit();
        outState.putBoolean(SAVED, true);
        String bidNum = ((EditText) getActivity().findViewById(R.id.bidNumET)).getText().toString();
        String bidDate = ((EditText) getActivity().findViewById(R.id.bidDateET)).getText().toString();
        String customerName = ((EditText) getActivity().findViewById(R.id.customerNameET)).getText().toString();
        String customerAddress = ((EditText) getActivity().findViewById(R.id.customerAddressET)).getText().toString();
        String customerNumber = ((EditText) getActivity().findViewById(R.id.customerPhoneET)).getText().toString();
        String jobType = ((Spinner) getActivity().findViewById(R.id.jobSpinner)).getSelectedItem().toString();
        String jobHours = ((EditText) getActivity().findViewById(R.id.hoursET)).getText().toString();
        String profitPercent = ((EditText) getActivity().findViewById(R.id.enteredProfitPercent)).getText().toString();

        StringBuilder selectedString = new StringBuilder();
        for (int i = 0; i < selected.size(); i++) {
            selectedString.append(selected.get(i)).append(",");
        }
        outState.putInt("selected",selected.size());
        outState.putInt("employees",employees.size());
        outState.putInt("equipment",equipment.size());
        outState.putInt("trucks",truckTrailers.size());
        outState.putString("selectedString", selectedString.toString());
        outState.putString("bidNum", bidNum);
        outState.putString("bidDate", bidDate);
        outState.putString("customerName", customerName);
        outState.putString("customerAddress", customerAddress);
        outState.putString("customerNumber", customerNumber);
        outState.putString("jobType", jobType);
        outState.putString("jobHours", jobHours);
        outState.putString("profitPercent", profitPercent);

        outState.apply();
    }

}
