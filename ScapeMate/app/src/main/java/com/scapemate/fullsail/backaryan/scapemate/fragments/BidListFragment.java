package com.scapemate.fullsail.backaryan.scapemate.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.scapemate.fullsail.backaryan.scapemate.BidAdapter;
import com.scapemate.fullsail.backaryan.scapemate.DataHelper;
import com.scapemate.fullsail.backaryan.scapemate.R;
import com.scapemate.fullsail.backaryan.scapemate.objects.Bid;
import com.scapemate.fullsail.backaryan.scapemate.objects.Company;

import java.util.ArrayList;

public class BidListFragment extends ListFragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener{


    private static final String TAG = "BidListFrag";
    BidAdapter adapter;
    View view;

    public BidListFragment() {
        // Required empty public constructor
    }

    public static BidListFragment newInstance(){
        return new BidListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        DataHelper dataHelper = new DataHelper();
        Company company = dataHelper.readCompany(getActivity());
        ArrayList<Bid> bids = company.getBids();
        Log.d(TAG,bids.size()+"");
        adapter = new BidAdapter(getContext(),bids);
        setListAdapter(adapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bid_list, container, false);
        SearchView searchView = ((SearchView)view.findViewById(R.id.bidSearch));
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        DataHelper dataHelper = new DataHelper();
        Company company = dataHelper.readCompany(getActivity());
        ArrayList<Bid> bids = company.getBids();
        ((TextView)view.findViewById(R.id.companyNameTV)).setText(company.getCompanyName());
        if(bids.size()==0){
            view.findViewById(R.id.exp_view).setVisibility(View.VISIBLE);
            view.findViewById(R.id.addBidTV).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.exp_view).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.addBidTV).setVisibility(View.INVISIBLE);
        }
        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("bidForm", Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.commit();
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            NewBIdFragment newBIdFragment = NewBIdFragment.newInstance();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.listContainer, newBIdFragment)
                    .addToBackStack(null)
                    .commit();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        SelectedBidFragment selectedBidFragment = SelectedBidFragment.newInstance(position);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.listContainer,selectedBidFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        ArrayList<Bid> searchBids = doSearch(query);
        if(searchBids.size()>0) {
            BidAdapter searchAdapter = new BidAdapter(getContext(), searchBids);
            setListAdapter(null);
            setListAdapter(searchAdapter);
            return true;
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.equalsIgnoreCase("")){
            setListAdapter(null);
            setListAdapter(adapter);
        } else {
            ArrayList<Bid> searchBids = doSearch(newText);
            BidAdapter searchAdapter = new BidAdapter(getContext(), searchBids);
            setListAdapter(null);
            setListAdapter(searchAdapter);
        }
        return false;
    }



    public ArrayList<Bid> doSearch(String query){
        ArrayList<Bid> searchBids = new ArrayList<>();
        DataHelper dataHelper = new DataHelper();
        Company company = dataHelper.readCompany(getActivity());
        ArrayList<Bid> bids = company.getBids();
        for(Bid bid:bids){
            if(bid.getCustomerName().contains(query)){
                searchBids.add(bid);
            }
            if(bid.getBidNum().contains(query)){
                searchBids.add(bid);
            }
        }
        return searchBids;
    }

    @Override
    public boolean onClose() {
        setListAdapter(null);
        setListAdapter(adapter);
        return false;
    }
}
