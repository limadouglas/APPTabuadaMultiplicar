package com.lima.douglas.apptabuadamultiplicar;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;
import com.lima.douglas.apptabuadamultiplicar.repository.RecordesRepository;
import com.lima.douglas.apptabuadamultiplicar.util.Constantes;
import com.lima.douglas.apptabuadamultiplicar.util.GeradorDeTabuada;

import java.util.Random;


public class TelaTreinamentoIntermediarioActivity extends AppCompatActivity {


    TextView txtPadrao;
    TextView txtAlternar;
    int novoNumero = 0, antigoNumero[] = {0, 0, 0, 0, 0, 0};
    boolean verificarRepetidos = true, ativarContador = true;
    int multInicial;
    int contador;
    AlertDialog dialogTabela;
    AlertDialog alertDialog;
    Intent i;
    RecordesRepository repository;
    SQLiteDatabase bd;
    Thread thread;
    boolean sairThread = false, sairPlacar = false;
    Handler handler;
    Button um;
    Button dois;
    Button tres;
    Button quatro;
    int arrayTag, resultado, resultadoErrado;
    Random random;
    ImageView imvTabela;
    String valor;
    TextView txtPlacar;
    TextView txtTitulo;
    ImageView imvTabuada;
    boolean efeitoBotao = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_treinamento_intermediario_activity);

        valor = getIntent().getStringExtra("valor");
        getSupportActionBar().hide();

        // instanciando view.
        txtPlacar = (TextView) findViewById(R.id.txtPlacar);
        txtAlternar = (TextView) findViewById(R.id.txtAlternar);
        txtPadrao = (TextView) findViewById(R.id.txtPadrao);
        repository = new RecordesRepository(this);
        handler = new Handler();
        um = (Button) findViewById(R.id.btnUm);
        dois = (Button) findViewById(R.id.btnDois);
        tres = (Button) findViewById(R.id.btnTres);
        quatro = (Button) findViewById(R.id.btnQuatro);
        random = new Random();
        imvTabela = new ImageView(this);
        alertDialog = new AlertDialog.Builder(this).create();
        dialogTabela = new AlertDialog.Builder(this).create();
        txtTitulo = (TextView) findViewById(R.id.txtTitulo);

        // verificando versão do android, versões menores que 10 tem problemas sobre efeito clique nos botões.
        if(Build.VERSION.SDK_INT <= 13) {
            efeitoBotao = false;
        }

        // alterando pontos da toolbar.
        txtPlacar = (TextView) findViewById(R.id.txtPlacar);

        // alterando titulo da toolbar.
        txtTitulo.setText(R.string.treinamento_intermediario);

        // instanciando a tabuada.
        imvTabuada = (ImageView) findViewById(R.id.imvTabuada);

        //inserindo um valor no txtPadrao para ele começar com o numero escolhido pelo usuario.
        txtPadrao.setText(valor);

        // inserindo um valor no txtAlternar para ele começar com numeros diferentes.
        do {
            multInicial = random.nextInt(10) + 1;
        } while (multInicial < 0);
        antigoNumero[0] = multInicial;
        txtAlternar.setText(String.valueOf(multInicial));

        gerarTagsBotao();

        // solucionando problema da tela de 3.2 (normal).
        if (getTamanhoHeight(1) == 480 && getTamanhoHeight(0) == 320) {
            alterarTamBotao();
        }

        if (Constantes.PROPAGANDA < Constantes.QTD_PROPAGANDA) {
            // inicializando appodeal para monetização.
            Appodeal.initialize(this, Constantes.APP_KEY, Appodeal.INTERSTITIAL);
            Appodeal.setTesting(Constantes.TESTEAPPODEAL);
            // definindo cache para armazenar a propaganda.
            Appodeal.cache(TelaTreinamentoIntermediarioActivity.this, Appodeal.INTERSTITIAL);
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

        // obtendo densidade da tela.
        float density = getResources().getDisplayMetrics().density;

        int tam = (int) (density * 90);
        //alterando tamanho(height).
        um.getLayoutParams().height = tam;
        dois.getLayoutParams().height = tam;
        tres.getLayoutParams().height = tam;
        quatro.getLayoutParams().height = tam;
    }

    // gerando nova tag.
    public void gerarTagsBotao() {

        arrayTag = random.nextInt(4);
        resultado = (Integer.valueOf(txtPadrao.getText().toString())) * (Integer.valueOf(txtAlternar.getText().toString()));


        if (arrayTag == 0) {
            um.setText(String.valueOf(resultado));
            um.setTag(String.valueOf(resultado));

            if(efeitoBotao) { // controlando pois versões menoes ou iguais a 10 não suportam a troca de efeito no botao.
                // definindo efeito ao clicar no botão.
                um.setBackgroundResource(R.drawable.btn_evento_backgroud_correto);
                dois.setBackgroundResource(R.drawable.btn_evento_backgroud_errado);
                tres.setBackgroundResource(R.drawable.btn_evento_backgroud_errado);
                quatro.setBackgroundResource(R.drawable.btn_evento_backgroud_errado);
            }
            do {
                resultadoErrado = random.nextInt(((resultado + 5) - (resultado - 5)) + 1) + (resultado - 5);
            } while (resultadoErrado < 0 || resultadoErrado == resultado);
            dois.setText(String.valueOf(resultadoErrado));
            dois.setTag(String.valueOf(resultadoErrado));
            do {
                resultadoErrado = random.nextInt(((resultado + 5) - (resultado - 5)) + 1) + (resultado - 5);
            }
            while (resultadoErrado < 0 || resultadoErrado == Integer.valueOf(um.getText().toString()) || resultadoErrado == Integer.valueOf(dois.getText().toString()));
            tres.setText(String.valueOf(resultadoErrado));
            tres.setTag(String.valueOf(resultadoErrado));
            do {
                resultadoErrado = random.nextInt(((resultado + 5) - (resultado - 5)) + 1) + (resultado - 5);
            }
            while (resultadoErrado < 0 || resultadoErrado == Integer.valueOf(um.getText().toString()) || resultadoErrado == Integer.valueOf(dois.getText().toString()) || resultadoErrado == Integer.valueOf(tres.getText().toString()));
            quatro.setText(String.valueOf(resultadoErrado));
            quatro.setTag(String.valueOf(resultadoErrado));
        } else if (arrayTag == 1) {
            dois.setText(String.valueOf(resultado));
            dois.setTag(String.valueOf(resultado));

            if(efeitoBotao) { // controlando pois versões menoes ou iguais a 10 não suportam a troca de efeito no botao.
                // definindo efeito ao clicar no botão.
                dois.setBackgroundResource(R.drawable.btn_evento_backgroud_correto);
                um.setBackgroundResource(R.drawable.btn_evento_backgroud_errado);
                tres.setBackgroundResource(R.drawable.btn_evento_backgroud_errado);
                quatro.setBackgroundResource(R.drawable.btn_evento_backgroud_errado);
            }
            do {
                resultadoErrado = random.nextInt(((resultado + 5) - (resultado - 5)) + 1) + (resultado - 5);
            } while (resultadoErrado < 0 || resultadoErrado == resultado);
            um.setText(String.valueOf(resultadoErrado));
            um.setTag(String.valueOf(resultadoErrado));
            do {
                resultadoErrado = random.nextInt(((resultado + 5) - (resultado - 5)) + 1) + (resultado - 5);
            }
            while (resultadoErrado < 0 || resultadoErrado == Integer.valueOf(um.getText().toString()) || resultadoErrado == Integer.valueOf(dois.getText().toString()));
            tres.setText(String.valueOf(resultadoErrado));
            tres.setTag(String.valueOf(resultadoErrado));
            do {
                resultadoErrado = random.nextInt(((resultado + 5) - (resultado - 5)) + 1) + (resultado - 5);
            }
            while (resultadoErrado < 0 || resultadoErrado == Integer.valueOf(um.getText().toString()) || resultadoErrado == Integer.valueOf(dois.getText().toString()) || resultadoErrado == Integer.valueOf(tres.getText().toString()));
            quatro.setText(String.valueOf(resultadoErrado));
            quatro.setTag(String.valueOf(resultadoErrado));

        } else if (arrayTag == 2) {
            tres.setText(String.valueOf(resultado));
            tres.setTag(String.valueOf(resultado));

            if(efeitoBotao) { // controlando pois versões menoes ou iguais a 10 não suportam a troca de efeito no botao.
                // definindo efeito ao clicar no botão.
                tres.setBackgroundResource(R.drawable.btn_evento_backgroud_correto);
                um.setBackgroundResource(R.drawable.btn_evento_backgroud_errado);
                dois.setBackgroundResource(R.drawable.btn_evento_backgroud_errado);
                quatro.setBackgroundResource(R.drawable.btn_evento_backgroud_errado);
            }
            do {
                resultadoErrado = random.nextInt(((resultado + 5) - (resultado - 5)) + 1) + (resultado - 5);
            } while (resultadoErrado < 0 || resultadoErrado == resultado);
            um.setText(String.valueOf(resultadoErrado));
            um.setTag(String.valueOf(resultadoErrado));
            do {
                resultadoErrado = random.nextInt(((resultado + 5) - (resultado - 5)) + 1) + (resultado - 5);
            }
            while (resultadoErrado < 0 || resultadoErrado == Integer.valueOf(um.getText().toString()) || resultadoErrado == Integer.valueOf(tres.getText().toString()));
            dois.setText(String.valueOf(resultadoErrado));
            dois.setTag(String.valueOf(resultadoErrado));
            do {
                resultadoErrado = random.nextInt(((resultado + 5) - (resultado - 5)) + 1) + (resultado - 5);
            }
            while (resultadoErrado < 0 || resultadoErrado == Integer.valueOf(um.getText().toString()) || resultadoErrado == Integer.valueOf(dois.getText().toString()) || resultadoErrado == Integer.valueOf(tres.getText().toString()));
            quatro.setText(String.valueOf(resultadoErrado));
            quatro.setTag(String.valueOf(resultadoErrado));
        } else if (arrayTag == 3) {
            quatro.setText(String.valueOf(resultado));
            quatro.setTag(String.valueOf(resultado));

            if(efeitoBotao) { // controlando pois versões menoes ou iguais a 10 não suportam a troca de efeito no botao.
                // definindo efeito ao clicar no botão.
                quatro.setBackgroundResource(R.drawable.btn_evento_backgroud_correto);
                um.setBackgroundResource(R.drawable.btn_evento_backgroud_errado);
                dois.setBackgroundResource(R.drawable.btn_evento_backgroud_errado);
                tres.setBackgroundResource(R.drawable.btn_evento_backgroud_errado);
            }
            do {
                resultadoErrado = random.nextInt(((resultado + 5) - (resultado - 5)) + 1) + (resultado - 5);
            } while (resultadoErrado < 0 || resultadoErrado == resultado);
            um.setText(String.valueOf(resultadoErrado));
            um.setTag(String.valueOf(resultadoErrado));
            do {
                resultadoErrado = random.nextInt(((resultado + 5) - (resultado - 5)) + 1) + (resultado - 5);
            }
            while (resultadoErrado < 0 || resultadoErrado == Integer.valueOf(um.getText().toString()) || resultadoErrado == Integer.valueOf(quatro.getText().toString()));
            dois.setText(String.valueOf(resultadoErrado));
            dois.setTag(String.valueOf(resultadoErrado));
            do {
                resultadoErrado = random.nextInt(((resultado + 5) - (resultado - 5)) + 1) + (resultado - 5);
            }
            while (resultadoErrado < 0 || resultadoErrado == Integer.valueOf(um.getText().toString()) || resultadoErrado == Integer.valueOf(dois.getText().toString()) || resultadoErrado == Integer.valueOf(quatro.getText().toString()));
            tres.setText(String.valueOf(resultadoErrado));
            tres.setTag(String.valueOf(resultadoErrado));
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
        String star = repository.getTreinamento("INTERMEDIARIO", String.valueOf((Integer.valueOf(valor) + 10)));
        ContentValues values = new ContentValues();
        // instanciando banco de dados.
        bd = repository.getWritableDatabase();

        // verificando em quanto tempo o usuario respondeu as questões e dando nota a ele.
        if (contador < 25) {
            txtPontuacaoAlert.setText(R.string.msg_pontuacao_excelente);
            imvTrofeu.setImageResource(R.drawable.excelente_dialog);
            if (star.equals("NAO") || star.equals("BRONZE") || star.equals("PRATA")) {
                values.put("STARTIPO", "OURO");
                bd.update("TREINAMENTO", values, "_ID = ?", new String[]{String.valueOf((Integer.valueOf(valor) + 10))});
            }
        } else if (contador < 35) {
            txtPontuacaoAlert.setText(R.string.msg_pontuacao_otimo);
            imvTrofeu.setImageResource(R.drawable.otimo_dialog);
            if (star.equals("NAO") || star.equals("BRONZE")) {
                values.put("STARTIPO", "PRATA");
                bd.update("TREINAMENTO", values, "_ID = ?", new String[]{String.valueOf((Integer.valueOf(valor) + 10))});
            }
        } else if (contador < 70) {
            txtPontuacaoAlert.setText(R.string.msg_pontuacao_bom);
            imvTrofeu.setImageResource(R.drawable.bom_dialog);
            if (star.equals("NAO")) {
                values.put("STARTIPO", "BRONZE");
                bd.update("TREINAMENTO", values, "_ID = ?", new String[]{String.valueOf((Integer.valueOf(valor) + 10))});
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
            Appodeal.show(TelaTreinamentoIntermediarioActivity.this, Appodeal.INTERSTITIAL);
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
