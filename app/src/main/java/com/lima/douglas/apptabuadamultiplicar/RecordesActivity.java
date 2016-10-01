package com.lima.douglas.apptabuadamultiplicar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;


public class RecordesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recordes_activity);
        // renomeando action bar.
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setTitle(R.string.recordes);
    }
}
