package com.lima.douglas.apptabuadamultiplicar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class PrincipalActivity extends AppCompatActivity {

    Intent i;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_activity);
    }

    // chamando activity.

    public void treinamento (View view) {
        i = new Intent(this, MenuTreinamentoActivity.class);
        startActivity(i);
    }

    public void desafio (View view) {
        i = new Intent(this, DesafioActivity.class);
        startActivity(i);
    }

    public void tabuada (View view) {
        i = new Intent(this, TabuadaActivity.class);
        startActivity(i);
    }

    public void recordes (View view) {
        i = new Intent(this, RecordesActivity.class);
        startActivity(i);
    }

}
