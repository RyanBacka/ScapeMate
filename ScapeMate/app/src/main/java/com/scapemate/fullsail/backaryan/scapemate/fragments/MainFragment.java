package com.scapemate.fullsail.backaryan.scapemate.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    private static final String COMPANY_NAME = "com.scapemate.fullsail.backaryan.scapemate.COMPANY_NAME";
    private static final String MAINT_COST = "com.scapemate.fullsail.backaryan.scapemate.MAINT_COST";
    private static final String SAVED = "com.scapemate.fullsail.backaryan.scapemate.SAVED";
    DataHelper dataHelper = new DataHelper();
    View rootView;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment mainFragment = new MainFragment();
        mainFragment.setArguments(new Bundle());
        return mainFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        (rootView.findViewById(R.id.addEmployeesButton)).setOnClickListener(this);
        (rootView.findViewById(R.id.addEquipmentButton)).setOnClickListener(this);
        (rootView.findViewById(R.id.addTruckButton)).setOnClickListener(this);
        (rootView.findViewById(R.id.companySave)).setOnClickListener(this);

        SharedPreferences formData = getActivity().getSharedPreferences("form", Context.MODE_APPEND);
        if(formData.getBoolean(SAVED,false)) {
            String companyName = formData.getString(COMPANY_NAME,"");
            String hourlyMaint = formData.getString(MAINT_COST,"");
            if(!companyName.equalsIgnoreCase("")) {
                ((EditText) rootView.findViewById(R.id.companyNameET)).setText(companyName);
            }
            if(!hourlyMaint.equalsIgnoreCase("")) {
                ((EditText) rootView.findViewById(R.id.hourlyMaintET)).setText(hourlyMaint);
            }
        }
        return rootView;
    }

    @Override
    public void onClick(View v) {
        int whichScreen = 0;
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
                    ((EditText)getActivity().findViewById(R.id.companyNameET)).getText().clear();
                    ((EditText)getActivity().findViewById(R.id.hourlyMaintET)).getText().clear();
                    Intent intent = new Intent(getActivity().getApplicationContext(), BidListActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor outState = getActivity().getSharedPreferences("form", Context.MODE_APPEND).edit();
        String companyName = ((EditText)rootView.findViewById(R.id.companyNameET)).getText().toString();
        String maintCost = ((EditText)rootView.findViewById(R.id.hourlyMaintET)).getText().toString();
        outState.putBoolean(SAVED, true);
        outState.putString(COMPANY_NAME, companyName);
        outState.putString(MAINT_COST,maintCost);
        outState.apply();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor outState = getActivity().getSharedPreferences("form", Context.MODE_APPEND).edit();
        outState.remove("form").commit();
    }
}


