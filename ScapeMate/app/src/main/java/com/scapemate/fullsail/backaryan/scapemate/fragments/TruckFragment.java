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
import android.widget.EditText;
import android.widget.Toast;

import com.scapemate.fullsail.backaryan.scapemate.DataHelper;
import com.scapemate.fullsail.backaryan.scapemate.R;
import com.scapemate.fullsail.backaryan.scapemate.activities.BidListActivity;
import com.scapemate.fullsail.backaryan.scapemate.activities.MainActivity;
import com.scapemate.fullsail.backaryan.scapemate.objects.Company;
import com.scapemate.fullsail.backaryan.scapemate.objects.TruckTrailer;

import java.util.ArrayList;

public class TruckFragment extends Fragment implements View.OnClickListener {


    private static final String TAG = "TruckFragment";
    DataHelper dataHelper = new DataHelper();

    public TruckFragment() {
        // Required empty public constructor
    }

    public static TruckFragment newInstance() {
        return new TruckFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_truck, container, false);
        view.findViewById(R.id.truckSave).setOnClickListener(this);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final TruckTrailer truckTrailer = getTruckTrailer();
        if (truckTrailer != null) {
            Company company = dataHelper.readCompany(getActivity());
            if (company.getTrucksTrailers() == null) {
                ArrayList<TruckTrailer> truckTrailers = readTruck(truckTrailer);
                dataHelper.saveTruck(truckTrailers, getActivity());
            } else {
                ArrayList<TruckTrailer> truckTrailers = company.getTrucksTrailers();
                truckTrailers.add(truckTrailer);
                company.setTrucksTrailers(truckTrailers);
                dataHelper.saveCompany(company, getActivity());
            }
            ((EditText) getActivity().findViewById(R.id.truckNameET)).getText().clear();
            ((EditText) getActivity().findViewById(R.id.truckHoursET)).getText().clear();
            ((EditText) getActivity().findViewById(R.id.truckCostET)).getText().clear();
            ((EditText) getActivity().findViewById(R.id.truckMaintET)).getText().clear();
            Toast.makeText(getActivity(), "Add another Truck or Trailer.", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        final TruckTrailer truckTrailer = getTruckTrailer();
        if (truckTrailer != null) {
            Company company = dataHelper.readCompany(getActivity());
            if (company.getTrucksTrailers() == null) {
                ArrayList<TruckTrailer> truckTrailers = readTruck(truckTrailer);
                dataHelper.saveTruck(truckTrailers, getActivity());
            } else {
                ArrayList<TruckTrailer> truckTrailers = company.getTrucksTrailers();
                truckTrailers.add(truckTrailer);
                company.setTrucksTrailers(truckTrailers);
                dataHelper.saveCompany(company, getActivity());
            }
        }
        DataHelper dataHelper = new DataHelper();
        boolean companyCreated = dataHelper.companyCreatedRead(getActivity());

        if (!companyCreated) {
            Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
            getActivity().startActivity(intent);
            getActivity().finish();
        } else {
            Intent intent = new Intent(getActivity().getApplicationContext(),BidListActivity.class);
            intent.putExtra("SCREEN",1);
            getActivity().startActivity(intent);
            getActivity().finish();
        }
    }

    private TruckTrailer getTruckTrailer() {
        String name = ((EditText) getActivity().findViewById(R.id.truckNameET)).getText().toString();
        String hours = ((EditText) getActivity().findViewById(R.id.truckHoursET)).getText().toString();
        String usage = ((EditText) getActivity().findViewById(R.id.truckCostET)).getText().toString();
        String maint = ((EditText) getActivity().findViewById(R.id.truckMaintET)).getText().toString();
        if (name.equalsIgnoreCase("") || hours.equalsIgnoreCase("") || usage.equalsIgnoreCase("") || maint.equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "One or more fields is empty", Toast.LENGTH_LONG).show();
        } else {
            Log.d(TAG, "Create truck");
            return new TruckTrailer(name, Double.parseDouble(hours), Double.parseDouble(usage), Double.parseDouble(maint));
        }
        return null;
    }

    private ArrayList<TruckTrailer> readTruck(TruckTrailer truckTrailer) {
        ArrayList<TruckTrailer> truckArray;
        truckArray = dataHelper.readTruck(getActivity());
        truckArray.add(truckTrailer);
        return truckArray;
    }
}
