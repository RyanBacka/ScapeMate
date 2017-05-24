package com.scapemate.fullsail.backaryan.scapemate.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.scapemate.fullsail.backaryan.scapemate.DataHelper;
import com.scapemate.fullsail.backaryan.scapemate.fragments.MainFragment;
import com.scapemate.fullsail.backaryan.scapemate.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitle("");
        toolbar.setSubtitle("");

        DataHelper dataHelper = new DataHelper();
        boolean companyCreated = dataHelper.companyCreatedRead(this);

        if (!companyCreated) {
            MainFragment mainFragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mainFragment)
                    .commit();
        } else {
            Intent intent = new Intent(getApplicationContext(), BidListActivity.class);
            startActivity(intent);
        }

    }
}
