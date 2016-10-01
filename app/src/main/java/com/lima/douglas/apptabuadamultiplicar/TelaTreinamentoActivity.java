package com.lima.douglas.apptabuadamultiplicar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class TelaTreinamentoActivity extends AppCompatActivity {

    EditText edtResultado;
    TextView txtPlacar;
    TextView txtPadrao;
    TextView txtAlternar;
    Random random;
    int novoNumero = -1, antigoNumero= 0, val ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_treinamento_activity);

        // renomeando action bar.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.treinamento);

        // recebendo intent com valor da multiplicação a ser feita.
        Intent i = getIntent();
        String valor = i.getExtras().getString("valor");
        // convervendo para integer a string recebid no intent.
        // Integer num = Integer.valueOf(valor);

        // instanciando view.
        edtResultado = (EditText) findViewById(R.id.edtResultado);
        txtAlternar = (TextView) findViewById(R.id.txtAlternar);
        txtPadrao = (TextView) findViewById(R.id.txtPadrao);
        txtPlacar = (TextView) findViewById(R.id.txtPlacar);


        txtPadrao.setText(valor);
        random = new Random();

    }

    public void addValor(View view) {
        String valTag = (String)view.getTag();
        String edtString = edtResultado.getText().toString();

        if(!"-1".equals(valTag))
            edtResultado.setText(edtResultado.getText().append(valTag));
         else if(edtString.length() > 0)
            edtResultado.setText(edtString.substring(0, edtString.length()-1));

        if(edtResultado.getText().toString().length() > 0)
            calcular();
    }

    public void calcular() {
        String padrao = (String) txtPadrao.getText();
        String alternar = (String) txtAlternar.getText();
        val =  Integer.valueOf(padrao) * Integer.valueOf(alternar);

        if (val == Integer.valueOf(edtResultado.getText().toString())){

            while(novoNumero < 0 || novoNumero == antigoNumero)
                novoNumero = random.nextInt() % 9;

            antigoNumero = novoNumero;

            txtAlternar.setText(String.valueOf(novoNumero));
            edtResultado.setText("");
            txtPlacar.setText(String.valueOf( Integer.valueOf(txtPlacar.getText().toString()) + 1 ));

        }

    }

}
