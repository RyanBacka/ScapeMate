package com.scapemate.fullsail.backaryan.scapemate.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.scapemate.fullsail.backaryan.scapemate.DataHelper;
import com.scapemate.fullsail.backaryan.scapemate.R;
import com.scapemate.fullsail.backaryan.scapemate.activities.BidListActivity;
import com.scapemate.fullsail.backaryan.scapemate.activities.MainActivity;
import com.scapemate.fullsail.backaryan.scapemate.objects.Company;
import com.scapemate.fullsail.backaryan.scapemate.objects.Employee;
import com.scapemate.fullsail.backaryan.scapemate.objects.Equipment;

import java.util.ArrayList;

public class EquipmentFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    private static final String TAG = "EquipmentFragment";
    DataHelper dataHelper = new DataHelper();

    public EquipmentFragment() {
        // Required empty public constructor
    }

    public static EquipmentFragment newInstance() {
        return new EquipmentFragment();
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
        View view = inflater.inflate(R.layout.fragment_equipment, container, false);
        view.findViewById(R.id.equipmentSave).setOnClickListener(this);
        Spinner spinner = (Spinner) view.findViewById(R.id.equipmentSpinner);
        spinner.setOnItemSelectedListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        final Equipment equipment = getEquipment();
        if (equipment != null) {
            Company company = dataHelper.readCompany(getActivity());
            if (company.getEquipment() == null) {
                ArrayList<Equipment> equipmentArray = readEquipment(equipment);
                dataHelper.saveEquipment(equipmentArray, getActivity());
            } else {
                ArrayList<Equipment> equipmentArray = company.getEquipment();
                equipmentArray.add(equipment);
                company.setEquipment(equipmentArray);
                dataHelper.saveCompany(company, getActivity());
            }

            DataHelper dataHelper = new DataHelper();
            boolean companyCreated = dataHelper.companyCreatedRead(getActivity());

            if (!companyCreated) {
                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            } else {
                Intent intent = new Intent(getActivity().getApplicationContext(), BidListActivity.class);
                intent.putExtra("SCREEN",1);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Equipment equipment = getEquipment();
        if (equipment != null) {
            Company company = dataHelper.readCompany(getActivity());
            if (company.getEquipment() == null) {
                ArrayList<Equipment> equipmentArray = readEquipment(equipment);
                dataHelper.saveEquipment(equipmentArray, getActivity());
            } else {
                ArrayList<Equipment> equipmentArray = company.getEquipment();
                equipmentArray.add(equipment);
                company.setEquipment(equipmentArray);
                dataHelper.saveCompany(company, getActivity());
            }

            ((EditText) getActivity().findViewById(R.id.equipmentNameET)).getText().clear();
            ((EditText) getActivity().findViewById(R.id.equipmentHoursET)).getText().clear();
            ((EditText) getActivity().findViewById(R.id.equipmentUsageET)).getText().clear();
            ((EditText) getActivity().findViewById(R.id.equipmentMaintET)).getText().clear();
            Toast.makeText(getActivity(), "Add another piece of Equipment", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private Equipment getEquipment() {
        String name = ((EditText) getActivity().findViewById(R.id.equipmentNameET)).getText().toString();
        String hours = ((EditText) getActivity().findViewById(R.id.equipmentHoursET)).getText().toString();
        String usage = ((EditText) getActivity().findViewById(R.id.equipmentUsageET)).getText().toString();
        String maint = ((EditText) getActivity().findViewById(R.id.equipmentMaintET)).getText().toString();
        String spinnerText = ((Spinner) getActivity().findViewById(R.id.equipmentSpinner)).getSelectedItem().toString();
        if (name.equalsIgnoreCase("") ||
                hours.equalsIgnoreCase("") ||
                usage.equalsIgnoreCase("") ||
                maint.equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "One or more fields is empty", Toast.LENGTH_LONG).show();
        } else {
            boolean owned = false;
            if (spinnerText.equalsIgnoreCase("own")) {
                owned = true;
            }
            return new Equipment(name, Double.parseDouble(hours), Double.parseDouble(usage), Double.parseDouble(maint), owned);
        }
        return null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, position + "");
        if (position == 0) {
            EditText maintET = (EditText) getActivity().findViewById(R.id.equipmentMaintET);
            maintET.setHint("Weekly Maintenance Cost");
        } else {
            EditText maintET = (EditText) getActivity().findViewById(R.id.equipmentMaintET);
            maintET.setHint("Rental Cost");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private ArrayList<Equipment> readEquipment(Equipment equipment) {
        ArrayList<Equipment> equipmentArray;
        equipmentArray = dataHelper.readEquipment(getActivity());
        equipmentArray.add(equipment);
        return equipmentArray;
    }
}
