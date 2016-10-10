package com.lima.douglas.apptabuadamultiplicar;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class TelaTreinamentoExperienteActivity extends AppCompatActivity {


    TextView txtResposta;
    TextView txtPlacar;
    TextView txtPadrao;
    TextView txtAlternar;
    Random random;
    int novoNumero = 0, antigoNumero[] = {0, 0, 0, 0, 0, 0}, resMultiplicacao, contador;
    boolean verificarRepetidos = true, ativarContador = true;
    AlertDialog dialogTabela;
    ImageView imvTabela;
    String valor;
    AlertDialog alertDialog;
    Intent iAtualizar;
    Intent iVoltar;
    Thread thread;
    boolean sairThread = false, sairPlacar = false;
    Handler handler = new Handler();
    int multInicial;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_treinamento_experiente_activity);

        // renomeando action bar.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Experiente");
        actionBar.setDisplayHomeAsUpEnabled(true);

        // recebendo intent com valor da multiplicação a ser feita.
        Intent i = getIntent();
        valor = i.getExtras().getString("valor");
        // convervendo para integer a string recebid no intent.
        // Integer num = Integer.valueOf(valor);

        // instanciando view.
        txtResposta = (TextView) findViewById(R.id.txtResposta);
        txtAlternar = (TextView) findViewById(R.id.txtAlternar);
        txtPadrao = (TextView) findViewById(R.id.txtPadrao);
        txtPlacar = (TextView) findViewById(R.id.txtPlacar);
        imvTabela = new ImageView(this);
        dialogTabela = new AlertDialog.Builder(this).create();
        txtPadrao.setText(valor);
        random = new Random();
        alertDialog = new AlertDialog.Builder(this).create();
        iAtualizar = new Intent(this, TelaTreinamentoExperienteActivity.class);
        iVoltar = new Intent(this, MenuTreinamentoActivity.class);


        // inserindo um valor no txtAlternar para ele começar com numeros diferentes.
        do {
            multInicial = random.nextInt(10) + 1;
        } while (multInicial < 0);
        antigoNumero[0] = multInicial;
        txtAlternar.setText(String.valueOf(multInicial));

    }


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


        // bloqueio sem isto, se o usuario for muito rapido ele consegue fazer 17/16, na pontuação.
        if (!txtPlacar.getText().toString().equals("16")) {
            // necessario verificar pois o usuario pode estar clicando em apagar, então não é necessario chamar o metodo
            // calcular.
            if (txtResposta.getText().toString().length() > 0)
                calcular();
        }
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

        if (resMultiplicacao == Integer.valueOf(txtResposta.getText().toString())) {
            while (verificarRepetidos) {
                // gerando novo numero
                novoNumero = random.nextInt(10) + 1;// gera numeros de 0 a 10
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
            txtAlternar.setText(String.valueOf(novoNumero));
            // limpando edittext.
            txtResposta.setText("");
            // somando um no placar.
            txtPlacar.setText(String.valueOf(Integer.valueOf(txtPlacar.getText().toString()) + 1));
        }
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

        alertDialog = new AlertDialog.Builder(this).create();
        // verificando em quanto tempo o usuario respondeu as questões e dando nota a ele.
        if (contador < 25) {
            alertDialog.setTitle("Excelente");
        } else if (contador < 35) {
            alertDialog.setTitle("Ótimo");
        } else if (contador < 70) {
            alertDialog.setTitle("Bom");
        } else if (contador < 90) {
            alertDialog.setTitle("Nada Mal");
        } else if (contador < 121) {
            alertDialog.setTitle("Continue Treinando!");
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
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Sair", new DialogInterface.OnClickListener() {
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
