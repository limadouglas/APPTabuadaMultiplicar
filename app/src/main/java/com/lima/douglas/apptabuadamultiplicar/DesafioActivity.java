package com.lima.douglas.apptabuadamultiplicar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;


public class DesafioActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desafio_activity);
        // renomeando action bar.
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setTitle(R.string.desafio);
    }
}
