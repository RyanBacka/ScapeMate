package com.scapemate.fullsail.backaryan.scapemate.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.scapemate.fullsail.backaryan.scapemate.fragments.EmployeeFragment;
import com.scapemate.fullsail.backaryan.scapemate.fragments.EquipmentFragment;
import com.scapemate.fullsail.backaryan.scapemate.R;
import com.scapemate.fullsail.backaryan.scapemate.fragments.TruckFragment;

public class AddActivity extends AppCompatActivity {

    private static final String TAG = "AddActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar.setTitle("");
        toolbar.setSubtitle("");
        toolbar.inflateMenu(R.menu.add_menu);

        int whichScreen = getIntent().getIntExtra("SCREEN",0);
        if (whichScreen==1){
            EmployeeFragment employeeFragment = EmployeeFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.addContainer,employeeFragment)
                    .commit();
        } else if(whichScreen==2){
            EquipmentFragment equipmentFragment = EquipmentFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.addContainer,equipmentFragment)
                    .commit();
        } else if(whichScreen==3){
            TruckFragment truckFragment = TruckFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.addContainer,truckFragment)
                    .commit();
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu,menu);
        return true;
    }
}
