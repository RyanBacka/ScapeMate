package com.scapemate.fullsail.backaryan.scapemate.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.scapemate.fullsail.backaryan.scapemate.DataHelper;
import com.scapemate.fullsail.backaryan.scapemate.fragments.MainFragment;
import com.scapemate.fullsail.backaryan.scapemate.R;
import com.scapemate.fullsail.backaryan.scapemate.fragments.MainMenuFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    MainFragment mainFragment;
    private static final String SAVED = "com.scapemate.fullsail.backaryan.scapemate.SAVED";
    private static final String COMPANY_NAME = "com.scapemate.fullsail.backaryan.scapemate.COMPANY_NAME";
    private static final String MAINT_COST = "com.scapemate.fullsail.backaryan.scapemate.MAINT_COST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitle("");
        toolbar.setSubtitle("");

        findViewById(R.id.menu).setOnClickListener(this);

        DataHelper dataHelper = new DataHelper();
        boolean companyCreated = dataHelper.companyCreatedRead(this);

        if (!companyCreated) {
            mainFragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mainFragment)
                    .commit();
        } else {
            Intent intent = new Intent(getApplicationContext(), BidListActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        MainMenuFragment menuFragment = MainMenuFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, menuFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {

    }
}
