package com.lima.douglas.apptabuadamultiplicar;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lima.douglas.apptabuadamultiplicar.repository.RecordesRepository;
import com.lima.douglas.apptabuadamultiplicar.util.RecordesEstrutura;

import java.util.ArrayList;
import java.util.List;


public class RecordesActivity extends AppCompatActivity {
    RecordesRepository repository;
    ListView lstPontuacao;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recordes_activity);
        // renomeando action bar.
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setTitle(R.string.recordes);

        //instanciando banco de dados.
        repository = new RecordesRepository(this);

        // instanciando listview
        lstPontuacao = (ListView) findViewById(R.id.lstPontuacao);

        List<RecordesEstrutura> recordes = new ArrayList<RecordesEstrutura>();
        recordes = repository.getRecordes();

        ArrayList<Integer> arrayPontuacao = new ArrayList<Integer>();

        for(RecordesEstrutura recordesEstrutura: recordes) {
            arrayPontuacao.add(recordesEstrutura.getPontucacao());
        }

        ArrayAdapter<Integer> adapterPontuacao = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, arrayPontuacao);
        lstPontuacao.setAdapter(adapterPontuacao);
    }
}
