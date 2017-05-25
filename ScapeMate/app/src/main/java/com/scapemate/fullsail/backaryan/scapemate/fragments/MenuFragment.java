package com.scapemate.fullsail.backaryan.scapemate.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.scapemate.fullsail.backaryan.scapemate.DataHelper;
import com.scapemate.fullsail.backaryan.scapemate.R;
import com.scapemate.fullsail.backaryan.scapemate.activities.AddActivity;
import com.scapemate.fullsail.backaryan.scapemate.activities.BidListActivity;
import com.scapemate.fullsail.backaryan.scapemate.activities.MainActivity;

public class MenuFragment extends Fragment implements View.OnClickListener{

    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance(){
        return new MenuFragment();
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
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        view.findViewById(R.id.newBidButton).setOnClickListener(this);
        view.findViewById(R.id.newEmployeeButton).setOnClickListener(this);
        view.findViewById(R.id.newEquipmentButton).setOnClickListener(this);
        view.findViewById(R.id.newTruckButton).setOnClickListener(this);
        view.findViewById(R.id.bidListButton).setOnClickListener(this);
        view.findViewById(R.id.newCompanyButton).setOnClickListener(this);
        getActivity().findViewById(R.id.menu).setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.newBidButton){
            Intent intent = new Intent(getActivity().getApplicationContext(), BidListActivity.class);
            intent.putExtra("SCREEN",1);
            getActivity().finish();
            startActivity(intent);
        } else if(v.getId() == R.id.newEmployeeButton){
            Intent intent = new Intent(getActivity().getApplicationContext(), AddActivity.class);
            intent.putExtra("SCREEN",1);
            getActivity().finish();
            startActivity(intent);
        }else if(v.getId() == R.id.newEquipmentButton){
            Intent intent = new Intent(getActivity().getApplicationContext(), AddActivity.class);
            intent.putExtra("SCREEN",2);
            getActivity().finish();
            startActivity(intent);
        }else if(v.getId() == R.id.newTruckButton){
            Intent intent = new Intent(getActivity().getApplicationContext(), AddActivity.class);
            intent.putExtra("SCREEN",3);
            getActivity().finish();
            startActivity(intent);
        }else if(v.getId() == R.id.bidListButton){
            Intent intent = new Intent(getActivity().getApplicationContext(), BidListActivity.class);
            getActivity().finish();
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().findViewById(R.id.menu).setVisibility(View.VISIBLE);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.menu_main_setting2).setVisible(false);
    }
}

