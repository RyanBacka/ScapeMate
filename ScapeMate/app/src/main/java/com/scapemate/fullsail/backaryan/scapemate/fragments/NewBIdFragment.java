package com.scapemate.fullsail.backaryan.scapemate.fragments;

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
import android.widget.Switch;
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
import java.util.Locale;

public class NewBIdFragment extends Fragment implements View.OnClickListener {


    private static final String TAG = "NewBidFragment";
    DataHelper dataHelper;
    Company company;
    int checkId = 0;
    ArrayList<Employee> employees = new ArrayList<>();
    ArrayList<Equipment> equipment = new ArrayList<>();
    ArrayList<TruckTrailer> truckTrailers = new ArrayList<>();
    String jobType;

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
        View view = inflater.inflate(R.layout.fragment_new_bid, container, false);
        (view.findViewById(R.id.addMaterials)).setOnClickListener(this);
        (view.findViewById(R.id.newBidSave)).setOnClickListener(this);
        (getActivity().findViewById(R.id.menu)).setOnClickListener(this);
        dataHelper = new DataHelper();
        company = dataHelper.readCompany(getActivity());
        ArrayList<Bid> bids = company.getBids();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat bidNumFormat = new SimpleDateFormat("ddMMyyyy");
        DateFormat dateFormat = DateFormat.getDateInstance();
        String bidNum = bidNumFormat.format(calendar.getTime());
        bidNum = bidNum + "-" + bids.size();
        EditText bidNumET = ((EditText) view.findViewById(R.id.bidNumET));
        bidNumET.setText(bidNum, TextView.BufferType.EDITABLE);
        EditText dateET = ((EditText) view.findViewById(R.id.bidDateET));
        String date = dateFormat.format(calendar.getTime());
        dateET.setText(date);

        employees = company.getEmployees();
        equipment = company.getEquipment();
        truckTrailers = company.getTrucksTrailers();
        ViewGroup employeesContainer = (ViewGroup) view.findViewById(R.id.employeeLayout);
        ViewGroup equipmentContainer = (ViewGroup) view.findViewById(R.id.equipmentLayout);
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

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addMaterials) {

            MaterialsCalculatorFragment materialsCalc = MaterialsCalculatorFragment.newInstance();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.listContainer, materialsCalc)
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
                    Log.d(TAG,"check box checked: "+i);
                    selected.add(i);
                }
            }
            for (Integer i : selected) {
                Log.d(TAG,"selected: "+i);
                if (i < employees.size()) {
                    Log.d(TAG,"employees index: "+i);
                    selectedEmployees.add(employees.get(i));
                } else if (i > employees.size()-1) {
                    int equipInt = i - (employees.size());
                    Log.d(TAG,"equipment index: "+equipInt);
                    if (equipInt < equipment.size()) {
                        selectedEquipment.add(equipment.get(equipInt));
                    } else if (equipInt > equipment.size()-1) {
                        int truckInt = equipInt - (equipment.size());
                        Log.d(TAG,"truck index: "+truckInt);
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
                String bidNum = ((EditText) getActivity().findViewById(R.id.bidNumET)).getText().toString();
                String bidDate = ((EditText) getActivity().findViewById(R.id.bidDateET)).getText().toString();
                String customerName = ((EditText) getActivity().findViewById(R.id.customerNameET)).getText().toString();
                String customerAddress = ((EditText) getActivity().findViewById(R.id.customerAddressET)).getText().toString();
                String customerNumber = ((EditText) getActivity().findViewById(R.id.customerPhoneET)).getText().toString();
                String jobType = ((Spinner) getActivity().findViewById(R.id.jobSpinner)).getSelectedItem().toString();
                double jobHours = Double.parseDouble(((EditText) getActivity().findViewById(R.id.hoursET)).getText().toString());
                double profitPercent = Double.parseDouble(((EditText) getActivity().findViewById(R.id.enteredProfitPercent)).getText().toString()) / 100;

                // Still need to add materials if job requires materials once MaterialsCalcFrag is set up

                Bid newBid = new Bid(bidNum, bidDate, customerName, customerAddress, customerNumber, jobType, jobHours, profitPercent, selectedEmployees, selectedEquipment, selectedTrucks, materials, 0);
                ArrayList<Bid> bids = company.getBids();
                bids.add(newBid);
                company.addBid(bids);
                dataHelper.saveCompany(company, getActivity());

                CompletedBidFragment completedBidFragment = CompletedBidFragment.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.listContainer, completedBidFragment)
                        .commit();
            }
        } else {
            MenuFragment menuFragment = MenuFragment.newInstance();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.listContainer,menuFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_main_setting2).setVisible(false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String bidNum = ((EditText) getActivity().findViewById(R.id.bidNumET)).getText().toString();
        String bidDate = ((EditText) getActivity().findViewById(R.id.bidDateET)).getText().toString();
        String customerName = ((EditText) getActivity().findViewById(R.id.customerNameET)).getText().toString();
        String customerAddress = ((EditText) getActivity().findViewById(R.id.customerAddressET)).getText().toString();
        String customerNumber = ((EditText) getActivity().findViewById(R.id.customerPhoneET)).getText().toString();
        String jobType = ((Spinner) getActivity().findViewById(R.id.jobSpinner)).getSelectedItem().toString();
        String jobHours = ((EditText) getActivity().findViewById(R.id.hoursET)).getText().toString();
        String profitPercent = ((EditText) getActivity().findViewById(R.id.enteredProfitPercent)).getText().toString();
        outState.putString("bidNum",bidNum);
        outState.putString("bidDate",bidDate);
        outState.putString("customerName",customerName);
        outState.putString("customerAddress",customerAddress);
        outState.putString("customerNumber",customerNumber);
        outState.putString("jobType",jobType);
        outState.putString("jobHours",jobHours);
        outState.putString("profitPercent",profitPercent);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null) {
            if(savedInstanceState.containsKey("bidNum")) {
                String bidNum = savedInstanceState.getString("bidNum");
                if(bidNum!=null || bidNum.equalsIgnoreCase("")) {
                    ((EditText) getActivity().findViewById(R.id.bidNumET)).setText(bidNum);
                }
            }

            if(savedInstanceState.containsKey("bidDate")) {
                String bidDate = savedInstanceState.getString("bidDate");
                if(bidDate!=null || bidDate.equalsIgnoreCase("")) {
                    ((EditText) getActivity().findViewById(R.id.bidDateET)).setText(bidDate);
                }
            }

            if(savedInstanceState.containsKey("customerName")) {
                String customerName = savedInstanceState.getString("customerName");
                if(customerName!=null || customerName.equalsIgnoreCase("")) {
                    ((EditText) getActivity().findViewById(R.id.customerNameET)).setText(customerName);
                }
            }

            if(savedInstanceState.containsKey("customerAddress")) {
                String customerAddress = savedInstanceState.getString("customerAddress");
                if(customerAddress!=null || customerAddress.equalsIgnoreCase("")) {
                    ((EditText) getActivity().findViewById(R.id.customerAddressET)).setText(customerAddress);
                }
            }

            if(savedInstanceState.containsKey("customerNumber")) {
                String customerNumber = savedInstanceState.getString("customerNumber");
                if(customerNumber!=null || customerNumber.equalsIgnoreCase("")) {
                    ((EditText) getActivity().findViewById(R.id.customerPhoneET)).setText(customerNumber);
                }
            }

            if(savedInstanceState.containsKey("jobHours")) {
                String jobHours = savedInstanceState.getString("jobHours");
                if(jobHours!=null || jobHours.equalsIgnoreCase("")) {
                    ((EditText) getActivity().findViewById(R.id.hoursET)).setText(jobHours);
                }
            }

            if(savedInstanceState.containsKey("profitPercent")) {
                String profitPercent = savedInstanceState.getString("profitPercent");
                if(profitPercent!=null || profitPercent.equalsIgnoreCase("")) {
                    ((EditText) getActivity().findViewById(R.id.enteredProfitPercent)).setText(profitPercent);
                }
            }

            String jobType = savedInstanceState.getString("jobType");
            int position = 0;
            switch (jobType){
                case "Maintenance": position=0;
                case "Mulch": position=1;
                case "Leaf Removal": position=2;
                case "Seeding": position=3;
                case "Aeration": position=4;
                case "Snow Removal": position=5;
                case "Design": position=6;
            }

            ((Spinner) getActivity().findViewById(R.id.jobSpinner)).setSelection(position);
        }
    }
}
