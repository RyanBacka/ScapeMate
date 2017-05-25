package com.scapemate.fullsail.backaryan.scapemate.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.scapemate.fullsail.backaryan.scapemate.R;
import com.scapemate.fullsail.backaryan.scapemate.fragments.BidListFragment;
import com.scapemate.fullsail.backaryan.scapemate.fragments.MenuFragment;
import com.scapemate.fullsail.backaryan.scapemate.fragments.NewBIdFragment;

public class BidListActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "BidListActivity";
    BidListFragment bidListFragment;
    private static final String SAVED = "com.scapemate.fullsail.backaryan.scapemate.SAVED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar.setTitle("");
        toolbar.setSubtitle("");
        toolbar.inflateMenu(R.menu.add_menu);

        int screen = getIntent().getIntExtra("SCREEN",0);

        findViewById(R.id.menu).setOnClickListener(this);

        if(screen==1){
            NewBIdFragment newBIdFragment = NewBIdFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.listContainer,newBIdFragment)
                    .commit();
        } else {
            bidListFragment = BidListFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.listContainer, bidListFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu,menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        MenuFragment menuFragment = MenuFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.listContainer,menuFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {

    }
}
