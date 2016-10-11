package com.lima.douglas.apptabuadamultiplicar;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lima.douglas.apptabuadamultiplicar.repository.RecordesRepository;

import java.util.Random;


public class TelaTreinamentoInicianteActivity extends AppCompatActivity {

    ActionBar actionBar;
    TextView txtPadrao;
    TextView txtPlacar;
    TextView txtAlternar;
    int novoNumero = 0, antigoNumero[] = {0, 0, 0, 0, 0, 0}, placar = 0;
    boolean verificarRepetidos = true, ativarContador = true;
    int multInicial;
    int contador;
    int pontuacao = 0, resMultiplicacao;
    AlertDialog dialogTabela;
    AlertDialog alertDialog;
    Intent i;
    ContentValues values;
    Thread thread;
    boolean sairThread = false, sairPlacar = false;
    Handler handler;
    Button um;
    Button dois;
    int arrayTag, resultado, resultadoErrado;
    MenuItem menuItem;
    Random random;
    ImageView imvTabela;
    String valor;
    SQLiteDatabase bd;
    RecordesRepository repository;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_treinamento_iniciante_activity);

        valor = getIntent().getStringExtra("valor");
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Iniciante");

        // instanciando view.
        txtPlacar = (TextView) findViewById(R.id.txtPlacar);
        txtAlternar = (TextView) findViewById(R.id.txtAlternar);
        txtPadrao = (TextView) findViewById(R.id.txtPadrao);
        repository = new RecordesRepository(this);
        handler = new Handler();
        um = (Button) findViewById(R.id.btnUm);
        dois = (Button) findViewById(R.id.btnDois);
        random = new Random();
        imvTabela = new ImageView(this);
        alertDialog = new AlertDialog.Builder(this).create();
        dialogTabela = new AlertDialog.Builder(this).create();


        //inserindo um valor no txtPadrao para ele começar com o numero escolhido pelo usuario.
        txtPadrao.setText(valor);

        // inserindo um valor no txtAlternar para ele começar com numeros diferentes.
        do {
            multInicial = random.nextInt(10) + 1;
        } while (multInicial < 0);
        antigoNumero[0] = multInicial;
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

        // iniciando contador.
        if (ativarContador) {
            contagem();
            ativarContador = false;
        }

        // bloqueio sem isto, se o usuario for muito rapido ele consegue fazer 17/16, na pontuação.
        if (!txtPlacar.getText().toString().equals("16")) {
            if (Integer.valueOf(view.getTag().toString()) == (Integer.valueOf(txtPadrao.getText().toString())) * (Integer.valueOf(txtAlternar.getText().toString()))) {
                calcular();
                gerarTagsBotao();
            } else if (Integer.valueOf(txtPlacar.getText().toString()) > 0) {
                txtPlacar.setText(String.valueOf(Integer.valueOf(txtPlacar.getText().toString()) - 1));
                if (contador < 120)
                    contador += 10;
            }
        }
    }

    public void calcular() {

        verificarRepetidos = true;

        // gerando um novo valor.
        while (verificarRepetidos) {
            // gerando novo numero
            novoNumero = random.nextInt(10) + 1;
            // verificando se o novo numero não é igual aos ultimos  cindo numeros gerados.
            // necessario novoNumero ser maior que zero, senão vai encher o vetor de numeros negativos.
            if (novoNumero != antigoNumero[0]) {
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
        txtAlternar.setText(String.valueOf(novoNumero));

        // somando um no placar.
        txtPlacar.setText(String.valueOf(Integer.valueOf(txtPlacar.getText().toString()) + 1));

        if (txtPlacar.getText().toString().equals("16"))
            sairPlacar = true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_tela_treinamento, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            sairThread = true;
            finish();
            overridePendingTransition(R.anim.slide_in_right2, R.anim.slide_out_left2);
        } else {
            switch (valor) {
                case "1":
                    imvTabela.setBackgroundResource(R.drawable.um);
                    break;
                case "2":
                    imvTabela.setBackgroundResource(R.drawable.dois);
                    break;
                case "3":
                    imvTabela.setBackgroundResource(R.drawable.tres);
                    break;
                case "4":
                    imvTabela.setBackgroundResource(R.drawable.quatro);
                    break;
                case "5":
                    imvTabela.setBackgroundResource(R.drawable.cinco);
                    break;
                case "6":
                    imvTabela.setBackgroundResource(R.drawable.seis);
                    break;
                case "7":
                    imvTabela.setBackgroundResource(R.drawable.sete);
                    break;
                case "8":
                    imvTabela.setBackgroundResource(R.drawable.oito);
                    break;
                case "9":
                    imvTabela.setBackgroundResource(R.drawable.nove);
                    break;
                case "10":
                    imvTabela.setBackgroundResource(R.drawable.dez);
                    break;
            }
            dialogTabela.setView(imvTabela);
            dialogTabela.show();
        }

        return true;
    }


    public void contagem() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                for (contador = 0; contador != 120 && !sairPlacar && !sairThread; contador++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }

                if (!sairThread)
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mensFimTreinamento();
                        }
                    });

            }
        };

        thread = new Thread(runnable);
        thread.start();
    }


    public void mensFimTreinamento() {

        // instancindo meu repositorio
        repository = new RecordesRepository(this);
        String star = repository.getTreinamento(valor);
        ContentValues values = new ContentValues();
        // instanciando banco de dados.
        bd = repository.getWritableDatabase();

        alertDialog = new AlertDialog.Builder(this, R.style.alertDialog).create();
        // verificando em quanto tempo o usuario respondeu as questões e dando nota a ele.
        if (contador < 25) {
            alertDialog.setTitle("Excelente");
            alertDialog.setIcon(R.drawable.trofeu_ouro);
            if (star.equals("NAO") || star.equals("BRONZE") || star.equals("PRATA")) {
                values.put("STARTIPO", "OURO");
                values.put("NUMTABUADA", valor);
                bd.update("TREINAMENTO", values, "NUMTABUADA = ?", new String[]{valor});
            }
        } else if (contador < 35) {
            alertDialog.setTitle("Ótimo");
            alertDialog.setIcon(R.drawable.trofeu_prata);
            if (star.equals("NAO") || star.equals("BRONZE")) {
                values.put("STARTIPO", "PRATA");
                values.put("NUMTABUADA", valor);
                bd.update("TREINAMENTO", values, "NUMTABUADA = ?", new String[]{valor});
            }
        } else if (contador < 70) {
            alertDialog.setTitle("Bom");
            alertDialog.setIcon(R.drawable.trofeu_bronze);
            if (star.equals("NAO")) {
                values.put("STARTIPO", "BRONZE");
                bd.update("TREINAMENTO", values, "NUMTABUADA = ?", new String[]{valor});
            }
        } else if (contador < 90) {
            alertDialog.setTitle("Nada Mal!");
            alertDialog.setIcon(R.drawable.trofeu_bronze);
            if (star.equals("NAO")) {
                values.put("STARTIPO", "BRONZE");
                bd.update("TREINAMENTO", values, "NUMTABUADA = ?", new String[]{valor});
            }
        }

        alertDialog.setCancelable(false);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Novamente", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                startActivity(getIntent());
                overridePendingTransition(R.anim.slide_in_right_y, R.anim.slide_out_left_y);
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Retornar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                overridePendingTransition(R.anim.slide_in_right2, R.anim.slide_out_left2);
            }
        });

        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }


    public void onBackPressed() {
        // finalizando activity e cancelando thread.
        sairThread = true;
        finish();
        overridePendingTransition(R.anim.slide_in_right2, R.anim.slide_out_left2);
        super.onBackPressed();
    }

}
