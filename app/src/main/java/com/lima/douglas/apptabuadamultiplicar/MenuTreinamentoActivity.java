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
    //todo fazer aparecer os trofeus em todos os botões;

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
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

        if(tipo.equals("intermediario")) {
            i = new Intent(this, TelaTreinamentoIntermediarioActivity.class);
            String valor = (String) view.getTag();
            i.putExtra("valor", valor);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }


        if(tipo.equals("experiente")) {
            i = new Intent(this, TelaTreinamentoExperienteActivity.class);
            String valor = (String) view.getTag();
            i.putExtra("valor", valor);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
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
