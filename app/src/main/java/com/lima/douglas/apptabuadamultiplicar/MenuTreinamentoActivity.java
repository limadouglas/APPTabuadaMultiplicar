package com.lima.douglas.apptabuadamultiplicar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;


public class MenuTreinamentoActivity extends AppCompatActivity {
    Intent i;
    String tipo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_treinamento_activity);

        // renomeando action bar.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.treinamento);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // pegando argumento passado pela intent.
        i = getIntent();
        tipo = i.getStringExtra("tipo");

    }

    public void abrirTreino(View view) {
        if(tipo.equals("iniciante")) {
            i = new Intent(this, TelaTreinamentoInicianteActivity.class);
            String valor = (String) view.getTag();
            i.putExtra("valor", valor);
            startActivity(i);
        }

        if(tipo.equals("intermediario")) {
            i = new Intent(this, TelaTreinamentoIntermediarioActivity.class);
            String valor = (String) view.getTag();
            i.putExtra("valor", valor);
            startActivity(i);
        }


        if(tipo.equals("experiente")) {
            i = new Intent(this, TelaTreinamentoExperienteActivity.class);
            String valor = (String) view.getTag();
            i.putExtra("valor", valor);
            startActivity(i);
        }
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
