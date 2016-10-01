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
    int novoNumero = 0, antigoNumero[] = {0, 0, 0, 0, 0}, resMultiplicacao;
    boolean verificarRepetidos = true;

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
        String valTag = (String) view.getTag();
        String edtString = edtResultado.getText().toString();

        // caso a tag da view seja -1, então tem que apagar um numero do edittext.
        if (!"-1".equals(valTag))
            edtResultado.setText(edtResultado.getText().append(valTag));
        else if (edtString.length() > 0) // se for um numero maior ou igual a zero, insira este numero no edittext.
            edtResultado.setText(edtString.substring(0, edtString.length() - 1));

        // necessario verificar pois o usuario pode estar clicando em apagar, então não é necessario chamar o metodo
        // calcular.
        if (edtResultado.getText().toString().length() > 0)
            calcular();
    }

    public void calcular() {

        // instanciando textview
        String padrao = (String) txtPadrao.getText();
        String alternar = (String) txtAlternar.getText();
        // descobrindo a resposta da multiplicacao.
        resMultiplicacao = Integer.valueOf(padrao) * Integer.valueOf(alternar);


        verificarRepetidos = true;
        // gerando um novo valor.
        // verificando se o valor digitado é igual a resposta.

        if (resMultiplicacao == Integer.valueOf(edtResultado.getText().toString())) {
            while (verificarRepetidos || novoNumero < 0) {
                // gerando novo numero
                novoNumero = random.nextInt() % 11;
                // verificando se o novo numero não é igual aos ultimos  cindo numeros gerados.
                // necessario novoNumero ser maior que zero, senão vai encher o vetor de numeros negativos.
                if (novoNumero != antigoNumero[0] && novoNumero >= 0) {
                    if (novoNumero != antigoNumero[1]) {
                        if (novoNumero != antigoNumero[2]) {
                            if (novoNumero != antigoNumero[3]) {
                                if (novoNumero != antigoNumero[4]) {
                                    for (int j = 4; j != 0; j--) { // deslocando os valores para esquerda do vetor.
                                        antigoNumero[j] = antigoNumero[j - 1];
                                    }
                                    antigoNumero[0] = novoNumero;
                                    // caso chegue ate aqui, então não tem numeros repetidos, já pode sair do loop.
                                    verificarRepetidos = false;
                                }
                            }
                        }
                    }
                }
            }

            // inserindo novoNumero no textView
            txtAlternar.setText(String.valueOf(novoNumero));
            // limpando edittext.
            edtResultado.setText("");
            // somando um no placar.
            txtPlacar.setText(String.valueOf(Integer.valueOf(txtPlacar.getText().toString()) + 1));

        }

    }

}
