package com.scapemate.fullsail.backaryan.scapemate.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.scapemate.fullsail.backaryan.scapemate.BidAdapter;
import com.scapemate.fullsail.backaryan.scapemate.DataHelper;
import com.scapemate.fullsail.backaryan.scapemate.R;
import com.scapemate.fullsail.backaryan.scapemate.objects.Bid;
import com.scapemate.fullsail.backaryan.scapemate.objects.Company;

import java.util.ArrayList;

public class BidListFragment extends ListFragment implements SearchView.OnQueryTextListener, View.OnClickListener{


    private static final String TAG = "BidListFrag";
    ArrayList<Bid> bids;

    public BidListFragment() {
        // Required empty public constructor
    }

    public static BidListFragment newInstance(){
        return new BidListFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bid_list, container, false);
        ((SearchView)view.findViewById(R.id.bidSearch)).setOnQueryTextListener(this);
        (getActivity().findViewById(R.id.menu)).setOnClickListener(this);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DataHelper dataHelper = new DataHelper();
        Company company = dataHelper.readCompany(getActivity());
        bids = company.getBids();
        BidAdapter adapter = new BidAdapter(getContext(),bids);
        if(bids!=null){
            setListAdapter(adapter);
        }
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
        BidAdapter bidAdapter = new BidAdapter(getContext(),searchBids);
        setListAdapter(bidAdapter);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ArrayList<Bid> searchBids = doSearch(newText);
        BidAdapter bidAdapter = new BidAdapter(getContext(),searchBids);
        setListAdapter(bidAdapter);
        return false;
    }

    public ArrayList<Bid> doSearch(String query){
        ArrayList<Bid> searchBids = new ArrayList<>();
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
    public void onClick(View v) {
        MenuFragment menuFragment = MenuFragment.newInstance();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.listContainer,menuFragment)
                .addToBackStack(null)
                .commit();
    }
}
