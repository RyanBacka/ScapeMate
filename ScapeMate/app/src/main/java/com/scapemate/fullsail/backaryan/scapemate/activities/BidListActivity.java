package com.scapemate.fullsail.backaryan.scapemate.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.scapemate.fullsail.backaryan.scapemate.R;
import com.scapemate.fullsail.backaryan.scapemate.fragments.BidListFragment;
import com.scapemate.fullsail.backaryan.scapemate.fragments.NewBIdFragment;

public class BidListActivity extends AppCompatActivity {

    private static final String TAG = "BidListActivity";

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

        if(screen==1){
            NewBIdFragment newBIdFragment = NewBIdFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.listContainer,newBIdFragment)
                    .commit();
        } else {
            BidListFragment bidListFragment = BidListFragment.newInstance();
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
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
