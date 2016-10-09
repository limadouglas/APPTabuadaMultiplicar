package com.lima.douglas.apptabuadamultiplicar;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lima.douglas.apptabuadamultiplicar.repository.RecordesRepository;

import java.util.Random;


public class DesafioFacilActivity extends AppCompatActivity {
    ActionBar actionBar;
    TextView txtPadrao;
    TextView txtAlternar;
    Random random;
    int novoNumero = 0, antigoNumero[] = {0, 0, 0, 0, 0}, antigoNumero2[] = {0, 0, 0, 0, 0}, placar = 0;
    boolean verificarRepetidos = true, ativarContador = true;
    int multInicial;
    int contador = 59;
    int pontuacao = 0;
    AlertDialog dialog;
    Intent i;
    RecordesRepository repository;
    SQLiteDatabase bd;
    ContentValues values;
    Thread thread;
    boolean sairThread = false;
    Handler handler;
    Button um;
    Button dois;
    int arrayTag, resultado, resultadoErrado;
    MenuItem menuItem;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desafio_facil_activity);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Desafio Fácil");
        actionBar.setDisplayHomeAsUpEnabled(true);

        // instanciando view.
        txtAlternar = (TextView) findViewById(R.id.txtAlternar);
        txtPadrao = (TextView) findViewById(R.id.txtPadrao);
        random = new Random();
        repository = new RecordesRepository(this);
        handler = new Handler();
        um = (Button) findViewById(R.id.btnUm);
        dois = (Button) findViewById(R.id.btnDois);


        //inserindo um valor no txtAlternar para ele começar com numeros diferentes.
        do {
            multInicial = random.nextInt(6);
        } while (multInicial < 0);
        antigoNumero[0] = multInicial;
        txtPadrao.setText(String.valueOf(multInicial));

        // inserindo um valor no txtAlternar para ele começar com numeros diferentes.
        do {
            multInicial = random.nextInt(11);
        } while (multInicial < 0);
        antigoNumero2[0] = multInicial;
        txtAlternar.setText(String.valueOf(multInicial));

        gerarTagsBotao();
    }


    // gerando nova tag.
    public void gerarTagsBotao() {

        arrayTag = random.nextInt(2);
        resultado = (Integer.valueOf(txtPadrao.getText().toString())) * (Integer.valueOf(txtAlternar.getText().toString()));
        do {
            resultadoErrado = random.nextInt(((resultado + 5) - (resultado - 5)) + 1) + (resultado - 5);
        } while (resultadoErrado < 0 || resultadoErrado == resultado);

        if (arrayTag == 0) {
            um.setText(String.valueOf(resultado));
            um.setTag(String.valueOf(resultado));
            dois.setText(String.valueOf(resultadoErrado));
            dois.setTag(String.valueOf(resultadoErrado));
        } else {
            dois.setText(String.valueOf(resultado));
            dois.setTag(String.valueOf(resultado));
            um.setText(String.valueOf(resultadoErrado));
            um.setTag(String.valueOf(resultadoErrado));
        }
    }


    // verificando a tag do botão que o usuario criou.
    public void respostaUsuario(View view) {

        if (ativarContador) {
            contagem();
            ativarContador = false;
        }

        if (Integer.valueOf(view.getTag().toString()) == (Integer.valueOf(txtPadrao.getText().toString())) * (Integer.valueOf(txtAlternar.getText().toString()))) {
            calcularPadrao();
            calcularAlterar();
            gerarTagsBotao();
        } else {
            contador -= 5;
        }
    }


    public void calcularPadrao() {

        verificarRepetidos = true;

        // gerando um novo valor.
        while (verificarRepetidos || novoNumero < 1) {
            // gerando novo numero
            novoNumero = random.nextInt(5) + 1;
            // verificando se o novo numero não é igual aos ultimos  cindo numeros gerados.
            // necessario novoNumero ser maior que zero, senão vai encher o vetor de numeros negativos.
            if (novoNumero != antigoNumero[0] && novoNumero >= 0) {
                if (novoNumero != antigoNumero[1]) {
                    if (novoNumero != antigoNumero[2]) {
                        if (novoNumero != antigoNumero[3]) {
                            for (int j = 3; j != 0; j--) { // deslocando os valores para esquerda do vetor.
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
        // somando um ponto no placar.
        placar++;
    }


    public void contagem() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                for (; contador >= 0 && !sairThread; contador--) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            menuItem.setTitle(String.valueOf(contador));
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }

                if (!sairThread)
                    handler.post(new Runnable() {
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
        pontuacao = (placar * 4) + (contador * 4);

        bd = repository.getWritableDatabase();

        values = new ContentValues();
        values.put("PONTUACAO", pontuacao);
        values.put("TIPORECORDE", "FACIL");
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
                overridePendingTransition(R.anim.slide_in_right_y, R.anim.slide_out_left_y);
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                overridePendingTransition(R.anim.slide_in_right2, R.anim.slide_out_left2);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.time_dificil, menu);
        // desabilitando o click.
        menu.setGroupEnabled(0, false);
        // pegando instancia do menu para poder alterar.
        menuItem = menu.findItem(R.id.itmTime);
        return super.onCreateOptionsMenu(menu);
    }


    // verificando qual item foi selecionado na actionBar.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                sairThread = true;
                finish();
                overridePendingTransition(R.anim.slide_in_right2, R.anim.slide_out_left2);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    // metodo sobrescreve o nativo do android.
    public void onBackPressed() {
        // ativar finalizar thread.
        sairThread = true;
        finish();
        overridePendingTransition(R.anim.slide_in_right2, R.anim.slide_out_left2);
        super.onBackPressed();
    }


}
