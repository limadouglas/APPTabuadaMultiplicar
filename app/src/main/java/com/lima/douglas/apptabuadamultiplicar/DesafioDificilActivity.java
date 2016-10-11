package com.lima.douglas.apptabuadamultiplicar;

import android.content.ClipData;
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
import android.support.v7.view.menu.MenuView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lima.douglas.apptabuadamultiplicar.repository.RecordesRepository;

import java.util.Random;


public class DesafioDificilActivity extends AppCompatActivity {


    TextView txtResposta;
    TextView txtPadrao;
    TextView txtAlternar;
    Random random;
    int novoNumero = 0, antigoNumero[] = {0, 0, 0, 0, 0, 0}, antigoNumero2[] = {0, 0, 0, 0, 0, 0}, resMultiplicacao, placar = 0;
    boolean verificarRepetidos = true;
    int multInicial;
    int contador = 59;
    int pontuacao = 0;
    String padrao;
    String alternar;
    AlertDialog dialog;
    Intent i;
    RecordesRepository repository;
    SQLiteDatabase bd;
    ContentValues values;
    Thread thread;
    boolean sairThread = false, ativarContador = true;
    Handler handler;
    MenuInflater menuInflater;
    MenuItem MenuItem;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desafio_dificil_activity);

        // renomeando action bar.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Desafio Difícil");

        // instanciando view.
        txtResposta = (TextView) findViewById(R.id.txtResposta);
        txtAlternar = (TextView) findViewById(R.id.txtAlternar);
        txtPadrao = (TextView) findViewById(R.id.txtPadrao);
        random = new Random();
        repository = new RecordesRepository(this);
        handler = new Handler();


        //inserindo um valor no txtAlternar para ele começar com numeros diferentes.
        do {
            multInicial = random.nextInt(10) + 1;
        } while (multInicial < 0);
        antigoNumero[0] = multInicial;
        txtPadrao.setText(String.valueOf(multInicial));

        // inserindo um valor no txtAlternar para ele começar com numeros diferentes.
        do {
            multInicial = random.nextInt(10) + 1;
        } while (multInicial < 0);
        antigoNumero2[0] = multInicial;
        txtAlternar.setText(String.valueOf(multInicial));


    }

    // inserindo valor no textview.
    public void addValor(View view) {

        // iniciando contador.
        if (ativarContador) {
            contagem();
            ativarContador = false;
        }


        String valTag = (String) view.getTag();
        String edtString = txtResposta.getText().toString();


        // caso a tag da view seja -1, então tem que apagar um numero do edittext.
        if (!"-1".equals(valTag) && edtString.length() < 3)
            txtResposta.setText(txtResposta.getText().toString() + valTag);
        else if (edtString.length() > 0 && "-1".equals(valTag)) // se for um numero maior ou igual a zero, insira este numero no edittext.
            txtResposta.setText(edtString.substring(0, edtString.length() - 1));

        // necessario verificar pois o usuario pode estar clicando em apagar, então não é necessario chamar o metodo
        // calcular.
        if (txtResposta.getText().toString().length() > 0) {

            // instanciando textview
            padrao = (String) txtPadrao.getText();
            alternar = (String) txtAlternar.getText();
            // descobrindo a resposta da multiplicacao.
            resMultiplicacao = Integer.valueOf(padrao) * Integer.valueOf(alternar);

            // verificando se o valor digitado é igual a resposta.
            if (resMultiplicacao == Integer.valueOf(txtResposta.getText().toString())) {
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
                                if (novoNumero != antigoNumero[5]) {
                                    for (int j = 5; j != 0; j--) { // deslocando os valores para esquerda do vetor.
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
        }

        // inserindo novoNumero no textView
        txtPadrao.setText(String.valueOf(novoNumero));
    }

    public void calcularAlterar() {

        verificarRepetidos = true;
        // gerando um novo valor.

        while (verificarRepetidos || novoNumero < 0) {
            // gerando novo numero
            novoNumero = random.nextInt(10) + 1;
            // verificando se o novo numero não é igual aos ultimos  cindo numeros gerados.
            // necessario novoNumero ser maior que zero, senão vai encher o vetor de numeros negativos.
            if (novoNumero != antigoNumero2[0] && novoNumero >= 0) {
                if (novoNumero != antigoNumero2[1]) {
                    if (novoNumero != antigoNumero2[2]) {
                        if (novoNumero != antigoNumero2[3]) {
                            if (novoNumero != antigoNumero2[4]) {
                                if (novoNumero != antigoNumero2[5]) {
                                    for (int j = 5; j != 0; j--) { // deslocando os valores para esquerda do vetor.
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
        }

        // inserindo novoNumero no textView
        txtAlternar.setText(String.valueOf(novoNumero));

        // limpando edittext.
        txtResposta.setText("");

        // somando um no placar
        placar++;
    }


    public void contagem() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                for (int i = contador; i >= 0 && !sairThread; i--) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MenuItem.setTitle(String.valueOf(contador));
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    contador = i;
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

        // o contador pode ser menor que zero por causa da penalização de -5, por clicar no errado.
        if (contador < 0)
            contador = 0;

        pontuacao = (placar * 4) + (contador * 4);

        bd = repository.getWritableDatabase();

        values = new ContentValues();
        values.put("PONTUACAO", pontuacao);
        values.put("TIPORECORDE", "DIFICIL");
        bd.insert("RECORDES", null, values);

        dialog = new AlertDialog.Builder(this, R.style.alertDialog).create();
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
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Retornar", new DialogInterface.OnClickListener() {
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
                overridePendingTransition(R.anim.slide_in_right2, R.anim.slide_out_left2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        // ativar finalizar thread.
        sairThread = true;
        finish();
        overridePendingTransition(R.anim.slide_in_right2, R.anim.slide_out_left2);
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.time_dificil, menu);
        // desabilitando o click.
        menu.setGroupEnabled(0, false);
        // pegando instancia do menu para poder alterar.
        MenuItem = menu.findItem(R.id.itmTime);

        return super.onCreateOptionsMenu(menu);
    }
}