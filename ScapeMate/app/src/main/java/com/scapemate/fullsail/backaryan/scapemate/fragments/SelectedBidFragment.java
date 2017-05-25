package com.scapemate.fullsail.backaryan.scapemate.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.scapemate.fullsail.backaryan.scapemate.BidHelper;
import com.scapemate.fullsail.backaryan.scapemate.DataHelper;
import com.scapemate.fullsail.backaryan.scapemate.R;
import com.scapemate.fullsail.backaryan.scapemate.objects.Bid;
import com.scapemate.fullsail.backaryan.scapemate.objects.Company;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SelectedBidFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "SelectedBidFrag";
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0x00100;
    DataHelper dataHelper = new DataHelper();
    Company company;
    Bid bid;

    public SelectedBidFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public static SelectedBidFragment newInstance(int selected) {
        SelectedBidFragment fragment = new SelectedBidFragment();
        Bundle args = new Bundle();
        args.putInt("selected", selected);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_selected_bid, container, false);
        company = dataHelper.readCompany(getActivity());
        ArrayList<Bid> bids = company.getBids();
        int selected = getArguments().getInt("selected");
        bid = bids.get(selected);
        ((TextView) view.findViewById(R.id.selectedCustomerName)).setText(bid.getCustomerName());
        ((TextView) view.findViewById(R.id.selectedCustomerAddress)).setText(bid.getCustomerAddress());
        ((TextView) view.findViewById(R.id.selectedCustomerPhone)).setText(bid.getCustomerPhoneNum());
        BidHelper bidHelper = new BidHelper();
        String manRate = "Man Rate: " + String.format(Locale.ENGLISH, "%.2f", bidHelper.getManRate(company, bid));
        ((TextView) view.findViewById(R.id.selectedManRate)).setText(manRate);
        String materialCost = "Material Cost: " + String.format(Locale.ENGLISH, "%.2f", bidHelper.getMaterialCost(bid));
        ((TextView) view.findViewById(R.id.selectedMaterialCost)).setText(materialCost);
        String breakEven = "Break Even: " + String.format(Locale.ENGLISH, "%.2f", bidHelper.getBreakEven(company, bid));
        ((TextView) view.findViewById(R.id.selectedBreakEven)).setText(breakEven);
        String bidPrice = "Bid Price: " + String.format(Locale.ENGLISH, "%.2f", bidHelper.getEstimatePrice(company, bid));
        ((TextView) view.findViewById(R.id.selectedBidPrice)).setText(bidPrice);
        ((Spinner) view.findViewById(R.id.statusSpinner)).setOnItemSelectedListener(this);
        ((Spinner) view.findViewById(R.id.statusSpinner)).setSelection(bid.getBidStatus());
        (view.findViewById(R.id.scheduleButton)).setOnClickListener(this);
        (view.findViewById(R.id.selectedBidSave)).setOnClickListener(this);
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_main_setting2).setVisible(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.selectedBidSave) {
            BidListFragment bidListFragment = BidListFragment.newInstance();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.listContainer, bidListFragment)
                    .commit();

        } else if (v.getId() == R.id.scheduleButton) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            } else if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {


                Calendar calendar = Calendar.getInstance();
                double jobLength = bid.getJobHours();

                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendar.getTimeInMillis());
                calendar.add(Calendar.HOUR, +(int) jobLength);
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calendar.getTimeInMillis())
                        .putExtra(CalendarContract.Events.TITLE, bid.getCustomerName())
                        .putExtra(CalendarContract.Events.DESCRIPTION, bid.getJobType())
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, bid.getCustomerAddress())
                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String bidStatus = ((Spinner) getActivity().findViewById(R.id.statusSpinner)).getSelectedItem().toString();
        int selected = getArguments().getInt("selected");
        ArrayList<Bid> bids = company.getBids();
        if (bidStatus.equalsIgnoreCase("accepted")) {
            bid.updateBidStatus(1);
            bids.set(selected, bid);
            company.addBid(bids);
            dataHelper.saveCompany(company, getActivity());
        } else if (bidStatus.equalsIgnoreCase("pending")) {
            bid.updateBidStatus(0);
            bids.set(selected, bid);
            company.addBid(bids);
            dataHelper.saveCompany(company, getActivity());
        } else if (bidStatus.equalsIgnoreCase("denied")) {
            bid.updateBidStatus(2);
            bids.set(selected, bid);
            company.addBid(bids);
            dataHelper.saveCompany(company, getActivity());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
