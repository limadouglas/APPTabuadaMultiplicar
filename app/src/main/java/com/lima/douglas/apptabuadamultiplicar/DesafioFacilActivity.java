package com.lima.douglas.apptabuadamultiplicar;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.MovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.InputDevice;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lima.douglas.apptabuadamultiplicar.repository.RecordesRepository;
import com.lima.douglas.apptabuadamultiplicar.util.RecordesEstrutura;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.Inflater;


public class DesafioFacilActivity extends AppCompatActivity {

    TextView txtPadrao;
    TextView txtAlternar;
    Random random;
    int novoNumero = 0, antigoNumero[] = {0, 0, 0, 0, 0}, antigoNumero2[] = {0, 0, 0, 0, 0, 0}, placar = 0;
    boolean verificarRepetidos = true, ativarContador = true;
    int multInicial;
    int contador = 10;
    int pontuacao = 0;
    AlertDialog dialog;
    Intent i;
    RecordesRepository repository;
    Thread thread;
    boolean sairThread = false;
    Handler handler;
    Button um;
    Button dois;
    int arrayTag, resultado, resultadoErrado;
    TextView txtTitulo;
    TextView txtTempo;
    Drawable drawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desafio_facil_activity);

        // escondendo actionbar padrão.
        getSupportActionBar().hide();

        // instanciando view.
        txtAlternar = (TextView) findViewById(R.id.txtAlternar);
        txtPadrao = (TextView) findViewById(R.id.txtPadrao);
        random = new Random();
        repository = new RecordesRepository(this);
        handler = new Handler();
        um = (Button) findViewById(R.id.btnUm);
        dois = (Button) findViewById(R.id.btnDois);

        txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        txtTempo = (TextView) findViewById(R.id.txtTempo);
        txtTitulo.setText("Fácil");


        //inserindo um valor no txtAlternar para ele começar com numeros diferentes.
        do {
            multInicial = random.nextInt(5) + 1;
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

        int tam = (int) (density * 180);
        //alterando tamanho(height).
        um.getLayoutParams().height = tam;
        dois.getLayoutParams().height = tam;
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
            um.setBackgroundResource(R.drawable.btn_evento_backgroud_correto);
            dois.setText(String.valueOf(resultadoErrado));
            dois.setTag(String.valueOf(resultadoErrado));
            dois.setBackgroundResource(R.drawable.btn_evento_backgroud_errado);
        } else {
            dois.setText(String.valueOf(resultado));
            dois.setTag(String.valueOf(resultado));
            dois.setBackgroundResource(R.drawable.btn_evento_backgroud_correto);
            um.setText(String.valueOf(resultadoErrado));
            um.setTag(String.valueOf(resultadoErrado));
            um.setBackgroundResource(R.drawable.btn_evento_backgroud_errado);
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
        } else
            contador -= 5;
    }


    public void calcularPadrao() {

        verificarRepetidos = true;

        // gerando um novo valor.
        while (verificarRepetidos) {
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


    // contador em uma thread separada.
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


    /// finanlizando desafio de mostrand resultado.
    public void finalizarDesafio() {

        int i = 0;

        // o contador pode ser menor que zero por causa da penalização de -5, por clicar no errado.
        if (contador < 0)
            contador = 0;

        // calculando placar do jogador.
        pontuacao = (placar * 4) + (contador * 4);

        // buscar do banco de dados.
        List<RecordesEstrutura> recordes = repository.getRecordes("FACIL");
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
                        repository.removerRecorde("FACIL", array.get(2));

                        // gravando valores no banco.
                        repository.setRecorde("FACIL", pontuacao);

                        // saindo o loop.
                        break;
                    }
                }
            }
        } else {
            // gravando valores no banco.
            repository.setRecorde("FACIL", pontuacao);
        }

        dialog = new AlertDialog.Builder(this, R.style.alertDialog).create();
        // necessario para que o usuario não clique fora do alert para sair.
        dialog.setCancelable(false);
        dialog.setTitle(R.string.msg_titulo_pontuacao);
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


    // metodo sobrescreve o nativo do android.
    public void onBackPressed() {
        // ativar finalizar thread.
        sairThread = true;
        finish();
        overridePendingTransition(R.anim.slide_in_right2, R.anim.slide_out_left2);
        super.onBackPressed();
    }

}
