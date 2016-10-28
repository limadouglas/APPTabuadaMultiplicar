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
        i = new Intent(this, MenuNivelTreinamentoActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void desafio (View view) {
        i = new Intent(this, NivelDesafioActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void tabuada (View view) {
        i = new Intent(this, TabuadaActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void recordes (View view) {
        i = new Intent(this, RecordesActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}
