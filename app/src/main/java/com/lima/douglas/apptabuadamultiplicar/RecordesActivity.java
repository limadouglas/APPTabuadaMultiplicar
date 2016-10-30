package com.lima.douglas.apptabuadamultiplicar;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lima.douglas.apptabuadamultiplicar.repository.RecordesRepository;
import com.lima.douglas.apptabuadamultiplicar.util.RecordesEstrutura;

import java.util.ArrayList;
import java.util.List;


public class RecordesActivity extends AppCompatActivity {

    // instanciando banco de dados.
    RecordesRepository repository;

    TextView txtPontuacaoFacil1;
    TextView txtPontuacaoFacil2;
    TextView txtPontuacaoFacil3;

    TextView txtPontuacaoMedio1;
    TextView txtPontuacaoMedio2;
    TextView txtPontuacaoMedio3;

    TextView txtPontuacaoDificil1;
    TextView txtPontuacaoDificil2;
    TextView txtPontuacaoDificil3;

    TextView txtLinhaPontuacaoFacil1;
    TextView txtLinhaPontuacaoFacil2;
    TextView txtLinhaPontuacaoMedio1;
    TextView txtLinhaPontuacaoMedio2;
    TextView txtLinhaPontuacaoDificil1;
    TextView txtLinhaPontuacaoDificil2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recordes_activity);
        // renomeando action bar.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.titulo_recordes);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // instanciando listview
        txtPontuacaoFacil1 = (TextView) findViewById(R.id.txtPontuacaoFacil1);
        txtPontuacaoFacil2 = (TextView) findViewById(R.id.txtPontuacaoFacil2);
        txtPontuacaoFacil3 = (TextView) findViewById(R.id.txtPontuacaoFacil3);

        txtPontuacaoMedio1 = (TextView) findViewById(R.id.txtPontuacaoMedio1);
        txtPontuacaoMedio2 = (TextView) findViewById(R.id.txtPontuacaoMedio2);
        txtPontuacaoMedio3 = (TextView) findViewById(R.id.txtPontuacaoMedio3);

        txtPontuacaoDificil1 = (TextView) findViewById(R.id.txtPontuacaoDificil1);
        txtPontuacaoDificil2 = (TextView) findViewById(R.id.txtPontuacaoDificil2);
        txtPontuacaoDificil3 = (TextView) findViewById(R.id.txtPontuacaoDificil3);

        // instanciando linhas.
        txtLinhaPontuacaoFacil1 = (TextView) findViewById(R.id.txtLinhaPontuacaoFacil1);
        txtLinhaPontuacaoFacil2 = (TextView) findViewById(R.id.txtLinhaPontuacaoFacil2);

        txtLinhaPontuacaoMedio1 = (TextView) findViewById(R.id.txtLinhaPontuacaoMedio1);
        txtLinhaPontuacaoMedio2 = (TextView) findViewById(R.id.txtLinhaPontuacaoMedio2);

        txtLinhaPontuacaoDificil1 = (TextView) findViewById(R.id.txtLinhaPontuacaoDificil1);
        txtLinhaPontuacaoDificil2 = (TextView) findViewById(R.id.txtLinhaPontuacaoDificil2);


        // instanciando classe que ira retornar os valores do banco de dados.
        repository = new RecordesRepository(this);

////////////////////////////////////////////////////////
        getRecordes("FACIL", txtPontuacaoFacil1, txtPontuacaoFacil2, txtPontuacaoFacil3, txtLinhaPontuacaoFacil1, txtLinhaPontuacaoFacil2);
        getRecordes("MEDIO", txtPontuacaoMedio1, txtPontuacaoMedio2, txtPontuacaoMedio3, txtLinhaPontuacaoMedio1, txtLinhaPontuacaoMedio2);
        getRecordes("DIFICIL", txtPontuacaoDificil1, txtPontuacaoDificil2, txtPontuacaoDificil3, txtLinhaPontuacaoDificil1, txtLinhaPontuacaoDificil2);

    }


    public void getRecordes(String nivel, TextView um, TextView dois, TextView tres, TextView lUm, TextView lDois) {

        // contador que ira armazenar a quantidade de resultados do banco.
        int i = 0;

        // buscando recordes do banco.
        List<RecordesEstrutura> recordes = repository.getRecordes(nivel);

        // criando array de inteiros para armazenar os valores retornados do banco de dados.
        ArrayList<Integer> arrayPontuacao = new ArrayList<Integer>();

        // inserindo valores da list<integer> no arraylist.
        for (RecordesEstrutura recordesEstrutura : recordes) {
            arrayPontuacao.add(recordesEstrutura.getPontuacao());
            i++;
        }

        // Atualizado valores dos TextView.
        if (i >= 1)
            um.setText(String.valueOf(arrayPontuacao.get(0)));
        else {
            um.setHeight(0);
            lUm.setVisibility(View.GONE);
            lDois.setHeight(View.GONE);
        }

        if (i >= 2)
            dois.setText(String.valueOf(arrayPontuacao.get(1)));
        else {
            dois.setHeight(0);
            lUm.setVisibility(View.GONE);
        }

        if (i >= 3)
            tres.setText(String.valueOf(arrayPontuacao.get(2)));
        else{
            tres.setHeight(0);
            lDois.setVisibility(View.GONE);

        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Id correspondente ao botão Up/Home da actionbar
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
