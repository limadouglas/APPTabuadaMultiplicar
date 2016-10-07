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
    ListView lstPontuacaoFacil;
    ListView lstPontuacaoMedio;
    ListView lstPontuacaoDificil;

    int i=0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recordes_activity);
        // renomeando action bar.
        ActionBar actionBar =  getSupportActionBar();
        actionBar.setTitle(R.string.recordes);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // instanciando listview
        lstPontuacaoFacil = (ListView) findViewById(R.id.lstPontuacaoFacil);
        lstPontuacaoMedio = (ListView) findViewById(R.id.lstPontuacaoMedio);
        lstPontuacaoDificil = (ListView) findViewById(R.id.lstPontuacaoDificil);


////////////////////////////////////////////////////////

        // instanciando banco de dados.
        repository = new RecordesRepository(this);

        List<RecordesEstrutura> recordes = new ArrayList<RecordesEstrutura>();
        // recebendo valores do banco de dados..
        recordes = repository.getRecordes("FACIL");

        // criando arraylist.
        ArrayList<Integer> arrayPontuacaoFacil = new ArrayList<Integer>();

        // inserindo valores da list<integer> no arraylist.
        for(RecordesEstrutura recordesEstrutura: recordes) {
            arrayPontuacaoFacil.add(recordesEstrutura.getPontucacao());
        }

        // criando array adapter.
        ArrayAdapter<Integer> adapterPontuacaoFacil = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, arrayPontuacaoFacil);
        // inserindo ArrayAdapter no listview
        lstPontuacaoFacil.setAdapter(adapterPontuacaoFacil);
        // desabilitando listview, não da para desabilitar direto no xml(não sei pq).
        lstPontuacaoFacil.setEnabled(false);

//////////////////////////////////////////////////////////////////

        // recebendo valores do banco de dados..
        recordes = repository.getRecordes("MEDIO");

        // criando arraylist.
        ArrayList<Integer> arrayPontuacaoMedio = new ArrayList<Integer>();

        // inserindo valores da list<integer> no arraylist.
        for(RecordesEstrutura recordesEstrutura: recordes) {
            arrayPontuacaoMedio.add(recordesEstrutura.getPontucacao());
        }

        // criando array adapter.
        ArrayAdapter<Integer> adapterPontuacaoMedio = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, arrayPontuacaoMedio);
        // inserindo ArrayAdapter no listview
        lstPontuacaoMedio.setAdapter(adapterPontuacaoMedio);
        // desabilitando listview, não da para desabilitar direto no xml(não sei pq).
        lstPontuacaoMedio.setEnabled(false);

/////////////////////////////////////////////////////////

        // recebendo valores do banco de dados..
        recordes = repository.getRecordes("DIFICIL");

        // criando arraylist.
        ArrayList<Integer> arrayPontuacaoDificil = new ArrayList<Integer>();

        // inserindo valores da list<integer> no arraylist.
        for(RecordesEstrutura recordesEstrutura: recordes) {
            arrayPontuacaoDificil.add(recordesEstrutura.getPontucacao());
        }

        // criando array adapter.
        ArrayAdapter<Integer> adapterPontuacaoDificil = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, arrayPontuacaoDificil);
        // inserindo ArrayAdapter no listview
        lstPontuacaoDificil.setAdapter(adapterPontuacaoDificil);
        // desabilitando listview, não da para desabilitar direto no xml(não sei pq).
        lstPontuacaoDificil.setEnabled(false);
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
