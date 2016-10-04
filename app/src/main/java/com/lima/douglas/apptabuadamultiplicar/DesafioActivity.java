package com.lima.douglas.apptabuadamultiplicar;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lima.douglas.apptabuadamultiplicar.repository.RecordesRepository;

import java.util.Random;


public class DesafioActivity extends AppCompatActivity {

    EditText edtResultado;
    TextView txtPlacar;
    TextView txtPadrao;
    TextView txtAlternar;
    Random random;
    int novoNumero = 0, getNovoNumero2 = 0, antigoNumero[] = {0, 0, 0, 0, 0}, antigoNumero2[] = {0, 0, 0, 0, 0}, resMultiplicacao;
    boolean verificarRepetidos = true;
    int multInicial;
    int contador = 50;
    int pontuacao = 0;
    String padrao;
    String alternar;
    AlertDialog dialog;
    Intent i;
    RecordesRepository repository;
    SQLiteDatabase bd;
    ContentValues values;
    Thread thread;
    boolean sairThread = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desafio_activity);
        // renomeando action bar.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.desafio);

        // instanciando view.
        edtResultado = (EditText) findViewById(R.id.edtResultado);
        txtAlternar = (TextView) findViewById(R.id.txtAlternar);
        txtPadrao = (TextView) findViewById(R.id.txtPadrao);
        txtPlacar = (TextView) findViewById(R.id.txtPlacar);
        random = new Random();
        repository = new RecordesRepository(this);


        //inserindo um valor no txtAlternar para ele começar com numeros diferentes.
        do {
            multInicial = random.nextInt(11);
        }while (multInicial < 0);
        antigoNumero[0] = multInicial;
        txtPadrao.setText(String.valueOf(multInicial));

        // inserindo um valor no txtAlternar para ele começar com numeros diferentes.
        do {
            multInicial = random.nextInt(11);
        }while (multInicial < 0);
        antigoNumero2[0] = multInicial;
        txtAlternar.setText(String.valueOf(multInicial));

        contagem();
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
        if (edtResultado.getText().toString().length() > 0) {

            // instanciando textview
            padrao = (String) txtPadrao.getText();
            alternar = (String) txtAlternar.getText();
            // descobrindo a resposta da multiplicacao.
            resMultiplicacao = Integer.valueOf(padrao) * Integer.valueOf(alternar);

            // verificando se o valor digitado é igual a resposta.
            if (resMultiplicacao == Integer.valueOf(edtResultado.getText().toString())) {
                calcularPadrao();
                calcularAlterar();
            }
        }

    }

    public void calcularPadrao() {

        verificarRepetidos = true;

        // gerando um novo valor.
        while (verificarRepetidos || novoNumero < 1) {
            // gerando novo numero
            novoNumero = random.nextInt(10) + 1;
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
        txtPadrao.setText(String.valueOf(novoNumero));
    }

    public void calcularAlterar() {

        verificarRepetidos = true;
        // gerando um novo valor.

        while (verificarRepetidos || novoNumero < 0) {
            // gerando novo numero
            novoNumero = random.nextInt(11);
            // verificando se o novo numero não é igual aos ultimos  cindo numeros gerados.
            // necessario novoNumero ser maior que zero, senão vai encher o vetor de numeros negativos.
            if (novoNumero != antigoNumero2[0] && novoNumero >= 0) {
                if (novoNumero != antigoNumero2[1]) {
                    if (novoNumero != antigoNumero2[2]) {
                        if (novoNumero != antigoNumero2[3]) {
                            if (novoNumero != antigoNumero2[4]) {
                                for (int j = 4; j != 0; j--) { // deslocando os valores para esquerda do vetor.
                                    antigoNumero2[j] = antigoNumero2[j - 1];
                                }
                                antigoNumero2[0] = novoNumero;
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

    public void contagem() {

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {

                for (int i = contador; i >= 0 && !txtPlacar.getText().equals("25"); i--) {
                    if (sairThread)
                        return;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    contador = i;
                    if (sairThread)
                        return;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        finalizarDesafio();
                    }
                });
            }
        };

        thread = new Thread(runnable);
        thread.start();
    }



    public void finalizarDesafio() {
        pontuacao = (Integer.valueOf(txtPlacar.getText().toString()) * 4) + (contador * 4);

        bd = repository.getWritableDatabase();

        values = new ContentValues();
        values.put("PONTUACAO", pontuacao);
        bd.insert("RECORDES", null, values);

        dialog = new AlertDialog.Builder(this).create();
        // necessario para que o usuario não clique fora do alert para sair.
        dialog.setCancelable(false);
        dialog.setTitle("Pontuação");
        dialog.setMessage(String.valueOf(pontuacao));

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Novamente", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                startActivity(getIntent());
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                retornarMenu();
            }
        });

        // necessario para que o usuario não clique fora do alert para sair.
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void retornarMenu() {
        i = new Intent(this, PrincipalActivity.class);
        finish();
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Id correspondente ao botão Up/Home da actionbar
            case android.R.id.home:
                sairThread = true;
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        //nada acontece usando este
        finish();
        sairThread = true;
        //nem este, continua saindo de todo o app e não para a tela anterior
        super.onBackPressed();
    }

}