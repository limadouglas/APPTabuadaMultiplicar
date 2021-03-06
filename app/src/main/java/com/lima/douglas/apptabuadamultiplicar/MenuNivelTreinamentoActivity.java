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


public class MenuNivelTreinamentoActivity extends AppCompatActivity {

    ActionBar actionBar;
    Intent i;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_nivel_treinamento_activity);
        actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.titulo_nivel_treinamento);
        actionBar.setDisplayHomeAsUpEnabled(true);
        i = new Intent(this, MenuTreinamentoActivity.class);
    }


    public void iniciante(View view) {
        i.putExtra("tipo", "iniciante");
        startActivityForResult(i, 0);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void intermediario(View view) {
        i.putExtra("tipo", "intermediario");
        startActivityForResult(i, 0);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void experiente(View view) {
        i.putExtra("tipo", "experiente");
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

    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_right2, R.anim.slide_out_left2);
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Appodeal.show(this, Appodeal.BANNER_BOTTOM); //necessario pois o MenuTreinamentoActivity da um hide no Appodeal.Banner_BOTTOM.
    }

    @Override
    protected void onResume() {
        super.onResume();
        Appodeal.onResume(this, Appodeal.BANNER);// iniciando o banner junto com a activity.
    }


}
