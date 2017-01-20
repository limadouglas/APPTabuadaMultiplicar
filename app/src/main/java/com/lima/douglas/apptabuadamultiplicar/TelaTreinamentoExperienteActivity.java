package com.lima.douglas.apptabuadamultiplicar;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;
import com.lima.douglas.apptabuadamultiplicar.repository.RecordesRepository;
import com.lima.douglas.apptabuadamultiplicar.util.Constantes;
import com.lima.douglas.apptabuadamultiplicar.util.GeradorDeTabuada;

import java.util.Random;

public class TelaTreinamentoExperienteActivity extends AppCompatActivity {


    TextView txtResposta, txtTitulo;
    TextView txtPlacar;
    TextView txtPadrao;
    TextView txtAlternar;
    Random random;
    int novoNumero = 0, antigoNumero[] = {0, 0, 0, 0, 0, 0}, resMultiplicacao, contador;
    boolean verificarRepetidos = true, ativarContador = true;
    AlertDialog dialogTabela;
    ImageView imvTabuada, imvTabela;
    String valor;
    AlertDialog alertDialog;
    Intent iAtualizar;
    Intent iVoltar;
    Thread thread;
    boolean sairThread = false, sairPlacar = false;
    Handler handler = new Handler();
    int multInicial;
    RecordesRepository repository;
    SQLiteDatabase bd;

    // declarando botões.
    Button btn0;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;
    ImageButton btnApagar;
    TextView txtX;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_treinamento_experiente_activity);

        getSupportActionBar().hide();

        // recebendo intent com valor da multiplicação a ser feita.
        Intent i = getIntent();
        valor = i.getExtras().getString("valor");
        // convervendo para integer a string recebid no intent.
        // Integer num = Integer.valueOf(valor);

        // instanciando view.
        txtResposta = (TextView) findViewById(R.id.txtResposta);
        txtAlternar = (TextView) findViewById(R.id.txtAlternar);
        txtPadrao = (TextView) findViewById(R.id.txtPadrao);
        imvTabela = new ImageView(this);
        dialogTabela = new AlertDialog.Builder(this).create();
        txtPadrao.setText(valor);
        random = new Random();
        alertDialog = new AlertDialog.Builder(this).create();
        iAtualizar = new Intent(this, TelaTreinamentoExperienteActivity.class);
        iVoltar = new Intent(this, MenuTreinamentoActivity.class);
        txtTitulo = (TextView) findViewById(R.id.txtTitulo);


        // alterando pontos da toolbar.
        txtPlacar = (TextView) findViewById(R.id.txtPlacar);

        // alterando titulo da toolbar.
        txtTitulo.setText(R.string.treinamento_experiente);

        // instanciando a tabuada.
        imvTabuada = (ImageView) findViewById(R.id.imvTabuada);


        // inserindo um valor no txtAlternar para ele começar com numeros diferentes.
        do {
            multInicial = random.nextInt(10) + 1;
        } while (multInicial < 0);
        antigoNumero[0] = multInicial;
        txtAlternar.setText(String.valueOf(multInicial));

        // solucionando problema da tela de 3.2 (normal).
        if (getTamanhoHeight(1) == 480 && getTamanhoHeight(0) == 320) {
            alterarTamBotao();
        }

        if (Constantes.PROPAGANDA < Constantes.QTD_PROPAGANDA) {
            // inicializando appodeal para monetização.
            Appodeal.initialize(this, Constantes.APP_KEY, Appodeal.INTERSTITIAL);
            Appodeal.setTesting(Constantes.TESTEAPPODEAL);
            // definindo cache para armazenar a propaganda.
            Appodeal.cache(TelaTreinamentoExperienteActivity.this, Appodeal.INTERSTITIAL);
        }

    }


    // retornando tamanho da tela.
    public int getTamanhoHeight(int i) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        if (i == 1)
            return height;
        else
            return width;
    }

    public void alterarTamBotao() {

        // instanciando botões.
        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btnApagar = (ImageButton) findViewById(R.id.btnApagar);
        txtX = (TextView) findViewById(R.id.txtX);

        // obtendo densidade da tela.
        float density = getResources().getDisplayMetrics().density;

        // tamanho do botao.
        int tam = (int) (density * 45);

        // tamanho da letra.
        float tamLetra = (density * 45);

        //alterando tamanho(height).
        btn0.getLayoutParams().height = tam;
        btn1.getLayoutParams().height = tam;
        btn2.getLayoutParams().height = tam;
        btn3.getLayoutParams().height = tam;
        btn4.getLayoutParams().height = tam;
        btn5.getLayoutParams().height = tam;
        btn6.getLayoutParams().height = tam;
        btn7.getLayoutParams().height = tam;
        btn8.getLayoutParams().height = tam;
        btn9.getLayoutParams().height = tam;
        btnApagar.getLayoutParams().height = tam;

        txtPadrao.setTextSize(tamLetra);
        txtX.setTextSize(tamLetra);
        txtAlternar.setTextSize(tamLetra);
        txtResposta.setTextSize(tamLetra - 20);

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


    public void mostrarTabuada(View v) {
        GeradorDeTabuada geradorDeTabuada = new GeradorDeTabuada();
        AlertDialog.Builder builder;

        builder = geradorDeTabuada.mostrarTabuada(this, valor);

        dialogTabela = builder.create();
        dialogTabela.show();
    }


    public void contagem() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                for (contador = 0; contador != 200 && !sairPlacar && !sairThread; contador++) {
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

        // variaveis para alertDialog Personalizado.
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.alertDialog);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.alert_treinamento, null);
        ImageView imvTrofeu = (ImageView) dialogView.findViewById(R.id.imvTrofeu);
        TextView txtPontuacaoAlert = (TextView) dialogView.findViewById(R.id.txtPontuacaoAlert);
        TextView txtTempo = (TextView) dialogView.findViewById(R.id.txtTempo);

        // instancindo meu repositorio
        repository = new RecordesRepository(this);
        String star = repository.getTreinamento("EXPERIENTE", String.valueOf((Integer.valueOf(valor) + 20)));
        ContentValues values = new ContentValues();
        // instanciando banco de dados.
        bd = repository.getWritableDatabase();

        // verificando em quanto tempo o usuario respondeu as questões e dando nota a ele.
        if (contador < 25) {
            txtPontuacaoAlert.setText(R.string.msg_pontuacao_excelente);
            imvTrofeu.setImageResource(R.drawable.excelente_dialog);
            if (star.equals("NAO") || star.equals("BRONZE") || star.equals("PRATA")) {
                values.put("STARTIPO", "OURO");
                bd.update("TREINAMENTO", values, "_ID = ?", new String[]{String.valueOf((Integer.valueOf(valor) + 20))});
            }
        } else if (contador < 35) {
            txtPontuacaoAlert.setText(R.string.msg_pontuacao_otimo);
            imvTrofeu.setImageResource(R.drawable.otimo_dialog);
            if (star.equals("NAO") || star.equals("BRONZE")) {
                values.put("STARTIPO", "PRATA");
                bd.update("TREINAMENTO", values, "_ID = ?", new String[]{String.valueOf((Integer.valueOf(valor) + 20))});
            }
        } else if (contador < 70) {
            txtPontuacaoAlert.setText(R.string.msg_pontuacao_bom);
            imvTrofeu.setImageResource(R.drawable.bom_dialog);
            if (star.equals("NAO")) {
                values.put("STARTIPO", "BRONZE");
                bd.update("TREINAMENTO", values, "_ID = ?", new String[]{String.valueOf((Integer.valueOf(valor) + 20))});
            }
        } else if (contador >= 70) {
            imvTrofeu.setImageResource(R.drawable.continue_dialog);
            txtPontuacaoAlert.setText(R.string.msg_pontuacao_cont_treinando);
        }

        // fechando banco de dados.
        bd.close();

        builder.setCancelable(false);

        builder.setPositiveButton(R.string.msg_botao_novamente, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                startActivity(getIntent());
                overridePendingTransition(R.anim.slide_in_right_y, R.anim.slide_out_left_y);
            }
        });

        builder.setNegativeButton(R.string.msg_botao_retornar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                overridePendingTransition(R.anim.slide_in_right2, R.anim.slide_out_left2);
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });

        txtTempo.setText(String.valueOf(contador));
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.show();

        // mostrando propaganda se ele já estiver carregada
        if( Appodeal.isLoaded(Appodeal.INTERSTITIAL) && Constantes.PROPAGANDA < Constantes.QTD_PROPAGANDA){
            Appodeal.show(TelaTreinamentoExperienteActivity.this, Appodeal.INTERSTITIAL);
            Constantes.PROPAGANDA++;
        }
    }


    public void onBackPressed() {
        // finalizando activity e cancelando thread.
        sairThread = true;
        finish();
        overridePendingTransition(R.anim.slide_in_right2, R.anim.slide_out_left2);
        super.onBackPressed();
    }


}
