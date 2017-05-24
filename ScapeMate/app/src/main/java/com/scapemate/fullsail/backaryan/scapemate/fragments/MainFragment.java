package com.scapemate.fullsail.backaryan.scapemate.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.scapemate.fullsail.backaryan.scapemate.DataHelper;
import com.scapemate.fullsail.backaryan.scapemate.activities.AddActivity;
import com.scapemate.fullsail.backaryan.scapemate.R;
import com.scapemate.fullsail.backaryan.scapemate.activities.BidListActivity;
import com.scapemate.fullsail.backaryan.scapemate.objects.Company;
import com.scapemate.fullsail.backaryan.scapemate.objects.Employee;
import com.scapemate.fullsail.backaryan.scapemate.objects.Equipment;
import com.scapemate.fullsail.backaryan.scapemate.objects.TruckTrailer;

import java.util.ArrayList;

public class MainFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "MainFragment";
    DataHelper dataHelper = new DataHelper();

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        (view.findViewById(R.id.addEmployeesButton)).setOnClickListener(this);
        (view.findViewById(R.id.addEquipmentButton)).setOnClickListener(this);
        (view.findViewById(R.id.addTruckButton)).setOnClickListener(this);
        (view.findViewById(R.id.companySave)).setOnClickListener(this);
        (getActivity().findViewById(R.id.menu)).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int whichScreen = 0;
        Log.d(TAG, v.getId() + "");
        if (v.getId() == R.id.addEmployeesButton) {
            whichScreen = 1;
            Intent intent = new Intent(getActivity().getApplicationContext(), AddActivity.class);
            intent.putExtra("SCREEN", whichScreen);
            startActivity(intent);
        } else if (v.getId() == R.id.addEquipmentButton) {
            whichScreen = 2;
            Intent intent = new Intent(getActivity().getApplicationContext(), AddActivity.class);
            intent.putExtra("SCREEN", whichScreen);
            startActivity(intent);
        } else if (v.getId() == R.id.addTruckButton) {
            whichScreen = 3;
            Intent intent = new Intent(getActivity().getApplicationContext(), AddActivity.class);
            intent.putExtra("SCREEN", whichScreen);
            startActivity(intent);
        } else if (v.getId() == R.id.companySave) {
            ArrayList<Employee> employees = dataHelper.readEmployee(getActivity());
            ArrayList<Equipment> equipment = dataHelper.readEquipment(getActivity());
            ArrayList<TruckTrailer> truckTrailers = dataHelper.readTruck(getActivity());
            if (((EditText) getActivity().findViewById(R.id.companyNameET)).getText().toString().equalsIgnoreCase("") ||
                    ((EditText) getActivity().findViewById(R.id.hourlyMaintET)).getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(getActivity(), "One or more fields are empty", Toast.LENGTH_LONG).show();
            } else {
                if (employees.isEmpty()) {
                    Toast.makeText(getActivity(), "No Employees added", Toast.LENGTH_LONG).show();
                } else if (equipment.isEmpty()) {
                    Toast.makeText(getActivity(), "No Equipment added", Toast.LENGTH_LONG).show();
                } else if (truckTrailers.isEmpty()) {
                    Toast.makeText(getActivity(), "No Trucks or Trailers added", Toast.LENGTH_LONG).show();
                } else {
                    String name = ((EditText) getActivity().findViewById(R.id.companyNameET)).getText().toString();
                    double hourlyMaint = Double.parseDouble(((EditText) getActivity().findViewById(R.id.hourlyMaintET)).getText().toString());
                    Company company = dataHelper.readCompany(getActivity());
                    if (company.getCompanyName() == null) {
                        company = new Company(name, employees, equipment, truckTrailers, hourlyMaint);
                        dataHelper.saveCompany(company, getActivity());
                        dataHelper.companyCreated(true, getActivity());
                    }
                    Intent intent = new Intent(getActivity().getApplicationContext(), BidListActivity.class);
                    startActivity(intent);
                }
            }
        } else {
            MenuFragment menuFragment = MenuFragment.newInstance();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,menuFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}


