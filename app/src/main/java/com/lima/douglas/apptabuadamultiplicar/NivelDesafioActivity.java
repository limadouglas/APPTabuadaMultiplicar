package com.lima.douglas.apptabuadamultiplicar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;



public class NivelDesafioActivity extends AppCompatActivity {
    ActionBar actionBar;
    Intent i;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Nivel do Desafio");
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.nivel_desafio_activity);
    }



    public  void facil(View view) {
        i = new Intent(this, DesafioFacilActivity.class);
        startActivity(i);

    }

    public  void medio(View view) {
        i = new Intent(this, DesafioMedioActivity.class);
        startActivity(i);

    }

    public  void dificil(View view) {
        i = new Intent(this, DesafioDificilActivity.class);
        startActivity(i);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
