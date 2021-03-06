package com.lima.douglas.apptabuadamultiplicar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


public class TabuadaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabuada_activity);
        // renomeando action bar.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.tabuada);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_right2, R.anim.slide_out_left2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_right2, R.anim.slide_out_left2);
        super.onBackPressed();
    }
}
