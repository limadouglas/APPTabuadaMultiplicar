package com.lima.douglas.apptabuadamultiplicar;


import android.content.DialogInterface;
import android.content.Intent;
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
import com.lima.douglas.apptabuadamultiplicar.util.RecordesEstrutura;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DesafioMedioActivity extends AppCompatActivity {

    TextView txtPadrao, txtTitulo, txtTempo;
    TextView txtAlternar;
    Random random;
    int novoNumero = 0, antigoNumero[] = {0, 0, 0, 0, 0}, antigoNumero2[] = {0, 0, 0, 0, 0, 0}, placar = 0;
    boolean verificarRepetidos = true;
    int multInicial;
    int contador = 59;
    int pontuacao = 0;
    AlertDialog dialog;
    Intent i;
    RecordesRepository repository;
    Thread thread;
    boolean sairThread = false, ativarContador = true;
    Handler handler;
    Button um;
    Button dois;
    Button tres;
    Button quatro;
    int arrayTag, resultado, resultadoErrado;
    boolean efeitoBotao =  true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desafio_medio_activity);

        getSupportActionBar().hide();

        // instanciando view.
        txtAlternar = (TextView) findViewById(R.id.txtAlternar);
        txtPadrao = (TextView) findViewById(R.id.txtPadrao);
        random = new Random();
        repository = new RecordesRepository(this);
        handler = new Handler();
        um = (Button) findViewById(R.id.btnUm);
        dois = (Button) findViewById(R.id.btnDois);
        tres = (Button) findViewById(R.id.btnTres);
        quatro = (Button) findViewById(R.id.btnQuatro);

        txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        txtTempo = (TextView) findViewById(R.id.txtTempo);
        txtTitulo.setText(R.string.desafio_medio);

        // verificando versão do android, versões menores que 10 tem problemas sobre efeito clique nos botões.
        if(Build.VERSION.SDK_INT <= 13) {
            efeitoBotao = false;
        }

        //inserindo um valor no txtAlternar para ele começar com numeros diferentes.
        do {
            multInicial = random.nextInt(7) + 1;
        } while (multInicial < 0);
        antigoNumero[0] = multInicial;
        txtPadrao.setText(String.valueOf(multInicial));

        // inserindo um valor no txtAlternar para ele começar com numeros diferentes.
        do {
            multInicial = random.nextInt(10) + 1;
        } while (multInicial < 0);
        antigoNumero2[0] = multInicial;
        txtAlternar.setText(String.valueOf(multInicial));

        gerarTagsBotao();

        // solucionando problema da tela de 3.2 (normal).
        if (getTamanhoHeight(1) == 480 && getTamanhoHeight(0) == 320) {
            alterarTamBotao();
        }

        // inicializando appodeal para monetização.
        Appodeal.initialize(this, Constantes.APP_KEY, Appodeal.INTERSTITIAL | Appodeal.MREC);
        Appodeal.setTesting(Constantes.TESTEAPPODEAL);
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


    // verificando a tag do botão que o usuario criou.
    public void respostaUsuario(View view) {

        if (ativarContador) {
            contagem();
            ativarContador = false;
        }

        if ((Integer.valueOf(view.getTag().toString())) == ((Integer.valueOf(txtPadrao.getText().toString())) * (Integer.valueOf(txtAlternar.getText().toString())))) {
            calcularPadrao();
            calcularAlterar();
            gerarTagsBotao();
        } else {
            contador -= 5;
        }
    }


    // gerando nova tag.
    public void gerarTagsBotao() {

        arrayTag = random.nextInt(4);
        resultado = (Integer.valueOf(txtPadrao.getText().toString())) * (Integer.valueOf(txtAlternar.getText().toString()));


        if (arrayTag == 0) {
            um.setText(String.valueOf(resultado));
            um.setTag(String.valueOf(resultado));

            if(efeitoBotao) {
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


            if(efeitoBotao) {
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


            if(efeitoBotao) {
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
            if(efeitoBotao) {
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


    public void calcularPadrao() {

        verificarRepetidos = true;

        // gerando um novo valor.
        while (verificarRepetidos || novoNumero < 1) {
            // gerando novo numero
            novoNumero = random.nextInt(7) + 1;
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

        while (verificarRepetidos) {
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
                            txtTempo.setText(String.valueOf(contador));
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

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view =  inflater.inflate(R.layout.alert_desafio, null);
        ImageView imvFim = (ImageView) view.findViewById(R.id.imvFim);
        TextView txtTempoDialog = (TextView) view.findViewById(R.id.txtTempoDialog);
        TextView txtNovoRecorde = (TextView) view.findViewById(R.id.txtNovoRecorde);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.alertDialog);


        int i = 0;

        // o contador pode ser menor que zero por causa da penalização de -5, por clicar no errado.
        if (contador < 0)
            contador = 0;

        // calculando placar do jogador.
        pontuacao = (placar * 4) + (contador * 4);

        // inserindo pontuacao no alertdialog
        txtTempoDialog.setText(String.valueOf(pontuacao));

        // buscar do banco de dados.
        List<RecordesEstrutura> recordes = repository.getRecordes("MEDIO");
        ArrayList<Integer> array = new ArrayList<Integer>();

        for (RecordesEstrutura re : recordes) {
            array.add(re.getPontuacao());
            i++;
        }

        if (i >= 3) {
            if ((pontuacao != array.get(0)) && (pontuacao != array.get(1)) && (pontuacao != array.get(2))) {
                for (int j = 0; j < i; j++) {
                    if (pontuacao > array.get(j)) {
                        // removerndo último valor do banco.
                        repository.removerRecorde("MEDIO", array.get(2));

                        // gravando valores no banco.
                        repository.setRecorde("MEDIO", pontuacao);

                        // saindo o loop.
                        break;
                    }
                }
            }

            if (pontuacao > array.get(0)) {
                imvFim.setImageResource(R.drawable.excelente_dialog);
                txtNovoRecorde.setLayoutParams(txtTempoDialog.getLayoutParams());
            }

        } else {
            // gravando valores no banco.
            repository.setRecorde("MEDIO", pontuacao);

            if (i >= 1) { // mostrando alertdialog personalizado com novo recorde.
                if (pontuacao > array.get(0)) {
                    imvFim.setImageResource(R.drawable.excelente_dialog);
                    txtNovoRecorde.setLayoutParams(txtTempoDialog.getLayoutParams());
                }
            } else {
                imvFim.setImageResource(R.drawable.excelente_dialog);
                txtNovoRecorde.setLayoutParams(txtTempoDialog.getLayoutParams());
            }
        }

        // necessario para que o usuario não clique fora do alert para sair.
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
                Appodeal.show(DesafioMedioActivity.this, Appodeal.INTERSTITIAL);
            }
        });

        // necessario para que o usuario não clique fora do alert para sair.
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });

        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }



    // metodo sobrescreve o nativo do android.
    public void onBackPressed() {
        // ativar finalizar thread.
        sairThread = true;
        finish();
        overridePendingTransition(R.anim.slide_in_right2, R.anim.slide_out_left2);

        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Appodeal.onResume(this, Appodeal.BANNER);
    }

}
