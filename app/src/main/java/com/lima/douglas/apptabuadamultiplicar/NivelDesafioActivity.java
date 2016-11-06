package com.lima.douglas.apptabuadamultiplicar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.appodeal.ads.Appodeal;
import com.lima.douglas.apptabuadamultiplicar.util.Constantes;


public class NivelDesafioActivity extends AppCompatActivity {
    ActionBar actionBar;
    Intent i;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.titulo_nivel_desafio);
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.nivel_desafio_activity);
    }



    public  void facil(View view) {
        i = new Intent(this, DesafioFacilActivity.class);
        startActivityForResult(i, 0);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    public  void medio(View view) {
        i = new Intent(this, DesafioMedioActivity.class);
        startActivityForResult(i, 0);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    public  void dificil(View view) {
        i = new Intent(this, DesafioDificilActivity.class);
        startActivityForResult(i, 0);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // controle para aparecer menos propaganda, dependendo da tela vai aparecer uma propaganda.
        if(Constantes.PROPAGANDA) {

            // inicializando appodeal para monetização.
            Appodeal.initialize(this, Constantes.APP_KEY, Appodeal.INTERSTITIAL | Appodeal.MREC);
            Appodeal.setTesting(Constantes.TESTEAPPODEAL);

            // mostrando a propaganda.
            Appodeal.show(this, Appodeal.INTERSTITIAL);
            Constantes.PROPAGANDA = false;
        }

    }

    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_right2, R.anim.slide_out_left2);
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Appodeal.onResume(this, Appodeal.BANNER);
    }

}
