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
import android.widget.TextView;

import com.lima.douglas.apptabuadamultiplicar.repository.RecordesRepository;
import com.lima.douglas.apptabuadamultiplicar.util.RecordesEstrutura;

import java.util.ArrayList;
import java.util.List;


public class RecordesActivity extends AppCompatActivity {
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
        txtPontuacaoFacil1 = (TextView) findViewById(R.id.txtPontuacaoFacil1);
        txtPontuacaoFacil2 = (TextView) findViewById(R.id.txtPontuacaoFacil2);
        txtPontuacaoFacil3 = (TextView) findViewById(R.id.txtPontuacaoFacil3);

        txtPontuacaoMedio1 = (TextView) findViewById(R.id.txtPontuacaoMedio1);
        txtPontuacaoMedio2 = (TextView) findViewById(R.id.txtPontuacaoMedio2);
        txtPontuacaoMedio3 = (TextView) findViewById(R.id.txtPontuacaoMedio3);

        txtPontuacaoDificil1 = (TextView) findViewById(R.id.txtPontuacaoDificil1);
        txtPontuacaoDificil2 = (TextView) findViewById(R.id.txtPontuacaoDificil2);
        txtPontuacaoDificil3 = (TextView) findViewById(R.id.txtPontuacaoDificil3);


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

        txtPontuacaoFacil1.setText(String.valueOf(arrayPontuacaoFacil.get(0)));
        txtPontuacaoFacil2.setText(String.valueOf(arrayPontuacaoFacil.get(1)));
        txtPontuacaoFacil3.setText(String.valueOf(arrayPontuacaoFacil.get(2)));

//////////////////////////////////////////////////////////////////

        // recebendo valores do banco de dados..
        recordes = repository.getRecordes("MEDIO");

        // criando arraylist.
        ArrayList<Integer> arrayPontuacaoMedio = new ArrayList<Integer>();

        // inserindo valores da list<integer> no arraylist.
        for(RecordesEstrutura recordesEstrutura: recordes) {
            arrayPontuacaoMedio.add(recordesEstrutura.getPontucacao());
        }

        txtPontuacaoMedio1.setText(String.valueOf(arrayPontuacaoMedio.get(0)));
        txtPontuacaoMedio2.setText(String.valueOf(arrayPontuacaoMedio.get(1)));
        txtPontuacaoMedio3.setText(String.valueOf(arrayPontuacaoMedio.get(2)));


/////////////////////////////////////////////////////////

        // recebendo valores do banco de dados..
        recordes = repository.getRecordes("DIFICIL");

        // criando arraylist.
        ArrayList<Integer> arrayPontuacaoDificil = new ArrayList<Integer>();

        // inserindo valores da list<integer> no arraylist.
        for(RecordesEstrutura recordesEstrutura: recordes) {
            arrayPontuacaoDificil.add(recordesEstrutura.getPontucacao());
        }

        txtPontuacaoDificil1.setText(String.valueOf(arrayPontuacaoDificil.get(0)));
        txtPontuacaoDificil2.setText(String.valueOf(arrayPontuacaoDificil.get(1)));
        txtPontuacaoDificil3.setText(String.valueOf(arrayPontuacaoDificil.get(2)));

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
