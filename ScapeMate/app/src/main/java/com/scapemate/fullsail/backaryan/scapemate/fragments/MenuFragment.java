package com.scapemate.fullsail.backaryan.scapemate.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.scapemate.fullsail.backaryan.scapemate.R;
import com.scapemate.fullsail.backaryan.scapemate.activities.AddActivity;
import com.scapemate.fullsail.backaryan.scapemate.activities.BidListActivity;
import com.scapemate.fullsail.backaryan.scapemate.activities.MainActivity;

public class MenuFragment extends Fragment implements View.OnClickListener{


    private static final String TAG = "MenuFragment";

    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance(){
        return new MenuFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        (view.findViewById(R.id.menu)).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.newBidButton).setOnClickListener(this);
        view.findViewById(R.id.newEmployeeButton).setOnClickListener(this);
        view.findViewById(R.id.newEquipmentButton).setOnClickListener(this);
        view.findViewById(R.id.newTruckButton).setOnClickListener(this);
        view.findViewById(R.id.bidListButton).setOnClickListener(this);
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
            startActivity(intent);
        } else if(v.getId() == R.id.newEmployeeButton){
            Intent intent = new Intent(getActivity().getApplicationContext(), AddActivity.class);
            intent.putExtra("SCREEN",1);
            startActivity(intent);
        }else if(v.getId() == R.id.newEquipmentButton){
            Intent intent = new Intent(getActivity().getApplicationContext(), AddActivity.class);
            intent.putExtra("SCREEN",2);
            startActivity(intent);
        }else if(v.getId() == R.id.newTruckButton){
            Intent intent = new Intent(getActivity().getApplicationContext(), AddActivity.class);
            intent.putExtra("SCREEN",3);
            startActivity(intent);
        }else{
            Intent intent = new Intent(getActivity().getApplicationContext(), BidListActivity.class);
            startActivity(intent);
        }
    }
}
