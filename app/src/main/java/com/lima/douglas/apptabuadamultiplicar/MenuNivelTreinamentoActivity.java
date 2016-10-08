package com.lima.douglas.apptabuadamultiplicar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;


public class MenuNivelTreinamentoActivity extends AppCompatActivity {

    ActionBar actionBar;
    Intent i;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_nivel_treinamento_activity);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Nivel Treinamento");
        actionBar.setDisplayHomeAsUpEnabled(true);
        i = new Intent(this, MenuTreinamentoActivity.class);
    }


    public void iniciante(View view) {
        i.putExtra("tipo", "iniciante");
        startActivity(i);
    }

    public void intermediario(View view) {
        i.putExtra("tipo", "intermediario");
        startActivity(i);
    }

    public void experiente(View view) {
        i.putExtra("tipo", "experiente");
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
