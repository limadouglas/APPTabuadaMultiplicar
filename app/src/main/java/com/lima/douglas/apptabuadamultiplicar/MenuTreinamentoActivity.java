package com.lima.douglas.apptabuadamultiplicar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;



public class MenuTreinamentoActivity extends AppCompatActivity {
    Intent i;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_treinamento_activity);

        // renomeando action bar.
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setTitle(R.string.treinamento);


    }

    public void abrirTreino(View view) {
        i = new Intent(this, TelaTreinamentoActivity.class);
        String valor = (String) view.getTag();
        i.putExtra("valor", valor);
        startActivity(i);
    }
}
