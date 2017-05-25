package com.scapemate.fullsail.backaryan.scapemate.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scapemate.fullsail.backaryan.scapemate.BidHelper;
import com.scapemate.fullsail.backaryan.scapemate.DataHelper;
import com.scapemate.fullsail.backaryan.scapemate.R;
import com.scapemate.fullsail.backaryan.scapemate.objects.Bid;
import com.scapemate.fullsail.backaryan.scapemate.objects.Company;
import com.scapemate.fullsail.backaryan.scapemate.objects.Materials;

import java.util.ArrayList;
import java.util.Locale;

public class CompletedBidFragment extends Fragment implements View.OnClickListener {


    private static final String TAG = "CompleteBidFrag";

    public CompletedBidFragment() {
        // Required empty public constructor
    }

    public static CompletedBidFragment newInstance() {
        return new CompletedBidFragment();
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
        View view = inflater.inflate(R.layout.fragment_completed_bid, container, false);
        DataHelper dataHelper = new DataHelper();
        Company company = dataHelper.readCompany(getActivity());
        ArrayList<Bid> bids = company.getBids();
        Bid bid = bids.get(bids.size() - 1);
        BidHelper bidHelper = new BidHelper();
        ((TextView) view.findViewById(R.id.crewWage)).setText(String.format(Locale.ENGLISH, "%.2f", bidHelper.getCrewAverageWage(bid)));
        ((TextView) view.findViewById(R.id.overtimeFactor)).setText(String.format(Locale.ENGLISH, "%.2f", bidHelper.getOvertimeFactor(bid)));
        ((TextView) view.findViewById(R.id.riskFactor)).setText(String.format(Locale.ENGLISH, "%.2f", bidHelper.getRiskFactor(bid)));
        ((TextView) view.findViewById(R.id.laborBurden)).setText(String.format(Locale.ENGLISH, "%.2f", bidHelper.getLaborBurden(bid)));
        ((TextView) view.findViewById(R.id.equipmentCost)).setText(String.format(Locale.ENGLISH, "%.2f", bidHelper.getEquipCost(company, bid)));
        ((TextView) view.findViewById(R.id.adminMaint)).setText(String.format(Locale.ENGLISH, "%.2f", bidHelper.getMaintCost(company, bid)));
        ((TextView) view.findViewById(R.id.manRate)).setText(String.format(Locale.ENGLISH, "%.2f", bidHelper.getManRate(company, bid)));
        ((TextView) view.findViewById(R.id.materialCost)).setText(String.format(Locale.ENGLISH, "%.2f", bidHelper.getMaterialCost(bid)));
        ((TextView) view.findViewById(R.id.breakEven)).setText(String.format(Locale.ENGLISH, "%.2f", bidHelper.getBreakEven(company, bid)));
        ((TextView) view.findViewById(R.id.profitPercent)).setText(String.format(Locale.ENGLISH, "%.2f", bidHelper.getProfit(company, bid)));
        ((TextView) view.findViewById(R.id.estimatePrice)).setText(String.format(Locale.ENGLISH, "%.2f", bidHelper.getEstimatePrice(company, bid)));
        (view.findViewById(R.id.doneButton)).setOnClickListener(this);
        dataHelper.saveCompany(company, getActivity());
        return view;
    }

    @Override
    public void onClick(View v) {
        BidListFragment bidListFragment = BidListFragment.newInstance();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.listContainer, bidListFragment)
                .commit();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_main_setting2).setVisible(false);
    }
}
