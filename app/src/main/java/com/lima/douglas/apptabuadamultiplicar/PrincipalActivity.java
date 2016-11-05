package com.lima.douglas.apptabuadamultiplicar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.appodeal.ads.Appodeal;
import com.lima.douglas.apptabuadamultiplicar.util.Constantes;


public class PrincipalActivity extends AppCompatActivity {

    Intent i;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_activity);

        // inicializando appodeal para monetização.
        Appodeal.initialize(this, Constantes.APP_KEY, Appodeal.INTERSTITIAL | Appodeal.BANNER | Appodeal.MREC | Appodeal.NATIVE);
        Appodeal.setTesting(Constantes.TESTEAPPODEAL);
        //Appodeal.show(this, Appodeal.BANNER_BOTTOM);
    }

    // chamando activity.
    public void treinamento (View view) {
        Appodeal.hide(this, Appodeal.BANNER_BOTTOM);
        i = new Intent(this, MenuNivelTreinamentoActivity.class);
        startActivityForResult(i, 1);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void desafio (View view) {
        Appodeal.hide(this, Appodeal.BANNER_BOTTOM);
        i = new Intent(this, NivelDesafioActivity.class);
        startActivityForResult(i, 1);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void tabuada (View view) {
        Appodeal.hide(this, Appodeal.BANNER_BOTTOM);
        i = new Intent(this, MenuTreinamentoActivity.class);
        i.putExtra("tipo", "tabuada");
        startActivityForResult(i, 1);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void recordes (View view) {
        Appodeal.hide(this, Appodeal.BANNER_BOTTOM);
        i = new Intent(this, RecordesActivity.class);
        startActivityForResult(i, 1);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Appodeal.onResume(this, Appodeal.BANNER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Appodeal.show(this, Appodeal.BANNER_BOTTOM);
    }
}
