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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appodeal.ads.Appodeal;
import com.lima.douglas.apptabuadamultiplicar.repository.RecordesRepository;
import com.lima.douglas.apptabuadamultiplicar.util.Constantes;
import com.lima.douglas.apptabuadamultiplicar.util.RecordesEstrutura;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DesafioDificilActivity extends AppCompatActivity {


    TextView txtResposta, txtTitulo, txtTempo;
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
    Thread thread;
    boolean sairThread = false, ativarContador = true;
    Handler handler;
    MenuItem MenuItem;


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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desafio_dificil_activity);

        // renomeando action bar.
        getSupportActionBar().hide();

        // instanciando view.
        txtResposta = (TextView) findViewById(R.id.txtResposta);
        txtAlternar = (TextView) findViewById(R.id.txtAlternar);
        txtPadrao = (TextView) findViewById(R.id.txtPadrao);
        random = new Random();
        repository = new RecordesRepository(this);
        handler = new Handler();

        txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        txtTempo = (TextView) findViewById(R.id.txtTempo);
        txtTitulo.setText(R.string.desafio_dificil);


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


        // solucionando problema da tela de 3.2 (normal).
        if (getTamanhoHeight(1) == 480 && getTamanhoHeight(0) == 320) {
            alterarTamBotao();
        }

        if (Constantes.PROPAGANDA < Constantes.QTD_PROPAGANDA) {
            // inicializando appodeal para monetização.
            Appodeal.initialize(this, Constantes.APP_KEY, Appodeal.INTERSTITIAL);
            Appodeal.setTesting(Constantes.TESTEAPPODEAL);
            // definindo cache para armazenar a propaganda.
            Appodeal.cache(DesafioDificilActivity.this, Appodeal.INTERSTITIAL);
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

        // obtendo densidade da tela.
        float density = getResources().getDisplayMetrics().density;

        int tam = (int) (density * 45);
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
                            txtTempo.setText(String.valueOf(contador));
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

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alert_desafio, null);
        ImageView imvFim = (ImageView) view.findViewById(R.id.imvFim);
        TextView txtTempoDialog = (TextView) view.findViewById(R.id.txtTempoDialog);
        TextView txtNovoRecorde = (TextView) view.findViewById(R.id.txtNovoRecorde);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.alertDialog);


        int i = 0;

        // o contador pode ser menor que zero por causa da penalização de -5, por clicar no errado.
        if (contador < 0)
            contador = 0;

        //zerando o tempo o tempo.
        txtTempo.setText("0");

        // calculando placar do jogador.
        pontuacao = (placar * 4) + (contador * 4);

        // inserindo pontuacao no alertdialog
        txtTempoDialog.setText(String.valueOf(pontuacao));

        if (pontuacao > 0) {

            // buscar do banco de dados.
            List<RecordesEstrutura> recordes = repository.getRecordes("DIFICIL");
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
                            repository.removerRecorde("DIFICIL", array.get(2));

                            // gravando valores no banco.
                            repository.setRecorde("DIFICIL", pontuacao);

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
                repository.setRecorde("DIFICIL", pontuacao);

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

        // mostrando propaganda se ele já estiver carregada
        if( Appodeal.isLoaded(Appodeal.INTERSTITIAL) && Constantes.PROPAGANDA < Constantes.QTD_PROPAGANDA){
            Appodeal.show(this, Appodeal.INTERSTITIAL);
            Constantes.PROPAGANDA++;
        }

    }

    public void retornarMenu() {
        i = new Intent(this, PrincipalActivity.class);
        finish();
        startActivity(i);
    }


    public void onBackPressed() {
        // ativar finalizar thread.
        sairThread = true;
        finish();
        overridePendingTransition(R.anim.slide_in_right2, R.anim.slide_out_left2);
        super.onBackPressed();
    }


}