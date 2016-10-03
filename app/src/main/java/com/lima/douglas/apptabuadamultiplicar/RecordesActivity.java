package com.lima.douglas.apptabuadamultiplicar;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lima.douglas.apptabuadamultiplicar.repository.RecordesRepository;
import com.lima.douglas.apptabuadamultiplicar.util.RecordesEstrutura;

import java.util.ArrayList;
import java.util.List;


public class RecordesActivity extends AppCompatActivity {
    RecordesRepository repository;
    ListView lstPontuacao;
    int i=0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recordes_activity);
        // renomeando action bar.
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setTitle(R.string.recordes);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //instanciando banco de dados.
        repository = new RecordesRepository(this);

        // instanciando listview
        lstPontuacao = (ListView) findViewById(R.id.lstPontuacao);

        List<RecordesEstrutura> recordes = new ArrayList<RecordesEstrutura>();
        // recebendo valores do banco de dados..
        recordes = repository.getRecordes();

        // criando arraylist.
        ArrayList<Integer> arrayPontuacao = new ArrayList<Integer>();

        // inserindo valores da list<integer> no arraylist.
        for(RecordesEstrutura recordesEstrutura: recordes) {
            arrayPontuacao.add(recordesEstrutura.getPontucacao());
        }

        // criando array adapter.
        ArrayAdapter<Integer> adapterPontuacao = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, arrayPontuacao);
        // inserindo ArrayAdapter no listview
        lstPontuacao.setAdapter(adapterPontuacao);
        // desabilitando listview, não da para desabilitar direto no xml(não sei pq).
        lstPontuacao.setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Id correspondente ao botão Up/Home da actionbar
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
