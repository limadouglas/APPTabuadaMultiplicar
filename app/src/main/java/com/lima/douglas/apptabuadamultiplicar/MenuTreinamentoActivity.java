package com.lima.douglas.apptabuadamultiplicar;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;
import com.lima.douglas.apptabuadamultiplicar.repository.RecordesRepository;
import com.lima.douglas.apptabuadamultiplicar.util.GeradorDeTabuada;


public class MenuTreinamentoActivity extends AppCompatActivity {
    Intent i;

    String medalha = new String();

    boolean smartphone = false; // verificador de smartphone ou tablet.

    RecordesRepository repository;

    String tipo;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;
    Button btn10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_treinamento_activity);

        //instanciando botões.
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btn10 = (Button) findViewById(R.id.btn10);

        // instanciando banco de dados.
        repository = new RecordesRepository(this);

        // renomeando action bar.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.titulo_escolha_tabuada);
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView tipoAparelho = (TextView) findViewById(R.id.tipo); // verificador de tablet ou smartphone.


        // inicializando botões 1 e 2 com imagens, para não desalinhar caso apenas um deles tenha trofeu.
        // verificando se é smartphone ou tablet...
        if (tipoAparelho.getText().equals("tablet")) {
            btn1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_transparente_large, 0, R.drawable.trofeu_transparente_large, 0);
            btn2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_transparente_large, 0, R.drawable.trofeu_transparente_large, 0);
            btn3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_transparente_large, 0, R.drawable.trofeu_transparente_large, 0);
            btn4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_transparente_large, 0, R.drawable.trofeu_transparente_large, 0);
            btn5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_transparente_large, 0, R.drawable.trofeu_transparente_large, 0);
            btn6.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_transparente_large, 0, R.drawable.trofeu_transparente_large, 0);
            btn7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_transparente_large, 0, R.drawable.trofeu_transparente_large, 0);
            btn8.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_transparente_large, 0, R.drawable.trofeu_transparente_large, 0);
            btn9.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_transparente_large, 0, R.drawable.trofeu_transparente_large, 0);
            btn10.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_transparente_large, 0, R.drawable.trofeu_transparente_large, 0);
        } else {
            btn1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_transparente, 0, R.drawable.trofeu_transparente, 0);
            btn2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_transparente, 0, R.drawable.trofeu_transparente, 0);
            btn3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_transparente, 0, R.drawable.trofeu_transparente, 0);
            btn4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_transparente, 0, R.drawable.trofeu_transparente, 0);
            btn5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_transparente, 0, R.drawable.trofeu_transparente, 0);
            btn6.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_transparente, 0, R.drawable.trofeu_transparente, 0);
            btn7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_transparente, 0, R.drawable.trofeu_transparente, 0);
            btn8.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_transparente, 0, R.drawable.trofeu_transparente, 0);
            btn9.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_transparente, 0, R.drawable.trofeu_transparente, 0);
            btn10.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_transparente, 0, R.drawable.trofeu_transparente, 0);
            smartphone = true;
        }

        // pegando argumento passado pela intent.
        i = getIntent();
        tipo = i.getStringExtra("tipo");

        if (!tipo.equals("tabuada"))
            setIcon();

        // solucionando problema da tela de 3.2 (normal).
        if (getTamanhoHeight(1) == 480 && getTamanhoHeight(0) == 320) {
            alterarTamBotao();
        }

        Appodeal.hide(this, Appodeal.BANNER);
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

        int tam = (int) (density * 60);
        //alterando tamanho(height).
        btn10.getLayoutParams().height = tam;
        btn1.getLayoutParams().height = tam;
        btn2.getLayoutParams().height = tam;
        btn3.getLayoutParams().height = tam;
        btn4.getLayoutParams().height = tam;
        btn5.getLayoutParams().height = tam;
        btn6.getLayoutParams().height = tam;
        btn7.getLayoutParams().height = tam;
        btn8.getLayoutParams().height = tam;
        btn9.getLayoutParams().height = tam;

    }

    public void abrirTreino(View view) {

        if (tipo.equals("iniciante")) {
            i = new Intent(this, TelaTreinamentoInicianteActivity.class);
            String valor = (String) view.getTag();
            i.putExtra("valor", valor);

            startActivityForResult(i, 0);

            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (tipo.equals("intermediario")) {
            i = new Intent(this, TelaTreinamentoIntermediarioActivity.class);
            String valor = (String) view.getTag();
            i.putExtra("valor", valor);
            startActivityForResult(i, 0); // chamando activity, e depois recebendo o resultado dela.
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (tipo.equals("experiente")) {
            i = new Intent(this, TelaTreinamentoExperienteActivity.class);
            String valor = (String) view.getTag();
            i.putExtra("valor", valor);
            startActivityForResult(i, 0);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (tipo.equals("tabuada")) {
            GeradorDeTabuada geradorDeTabuada = new GeradorDeTabuada();
            AlertDialog.Builder builder = geradorDeTabuada.mostrarTabuada(this, view.getTag().toString());
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }


    // este metodo nativo do android será chamado quando voltar de uma activity chamada pelo metodo startActivityForResult().
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setIcon();
    }


    public void setIcon() {

        switch (tipo) {
            case "iniciante":
                for (int i = 1; i <= 10; i++) {
                    medalha = repository.getTreinamento("INICIANTE", String.valueOf(i));
                    alterarBotao(medalha, i);
                }
                break;


            case "intermediario": // erro no intermediario.
                for (int i = 1; i <= 10; i++) {
                    medalha = repository.getTreinamento("INTERMEDIARIO", String.valueOf(i + 10));
                    alterarBotao(medalha, i);
                }
                break;


            case "experiente":
                for (int i = 1; i <= 10; i++) {
                    medalha = repository.getTreinamento("EXPERIENTE", String.valueOf(i + 20));
                    alterarBotao(medalha, i);
                }
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_right2, R.anim.slide_out_left2);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void alterarBotao(String medalha, int i) {

        if (i == 1) {
            if (!medalha.equals("NAO"))
                btn1.setPadding(btn1.getPaddingLeft(), 0, btn1.getPaddingLeft() * 2, 0);
            if (medalha.equals("OURO"))
                if (smartphone)
                    btn1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro, 0, 0, 0);
                else
                    btn1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro_large, 0, 0, 0);
            else if (medalha.equals("PRATA"))
                if (smartphone)
                    btn1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata, 0, 0, 0);
                else
                    btn1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata_large, 0, 0, 0);
            else if (medalha.equals("BRONZE"))
                if (smartphone)
                    btn1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze, 0, 0, 0);
                else
                    btn1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze_large, 0, 0, 0);
        } else if (i == 2) {
            if (!medalha.equals("NAO"))
                btn2.setPadding(btn1.getPaddingLeft(), 0, btn1.getPaddingLeft() * 2, 0);
            if (medalha.equals("OURO"))
                if (smartphone)
                    btn2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro, 0, 0, 0);
                else
                    btn2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro_large, 0, 0, 0);
            else if (medalha.equals("PRATA"))
                if (smartphone)
                    btn2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata, 0, 0, 0);
                else
                    btn2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata_large, 0, 0, 0);
            else if (medalha.equals("BRONZE"))
                if (smartphone)
                    btn2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze, 0, 0, 0);
                else
                    btn2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze_large, 0, 0, 0);
        } else if (i == 3) {
            if (!medalha.equals("NAO"))
                btn3.setPadding(btn1.getPaddingLeft(), 0, btn1.getPaddingLeft() * 2, 0);
            if (medalha.equals("OURO"))
                if (smartphone)
                    btn3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro, 0, 0, 0);
                else
                    btn3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro_large, 0, 0, 0);
            else if (medalha.equals("PRATA"))
                if (smartphone)
                    btn3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata, 0, 0, 0);
                else
                    btn3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata_large, 0, 0, 0);
            else if (medalha.equals("BRONZE"))
                if (smartphone)
                    btn3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze, 0, 0, 0);
                else
                    btn3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze_large, 0, 0, 0);
        } else if (i == 4) {
            if (!medalha.equals("NAO"))
                btn4.setPadding(btn1.getPaddingLeft(), 0, btn1.getPaddingLeft() * 2, 0);
            if (medalha.equals("OURO"))
                if (smartphone)
                    btn4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro, 0, 0, 0);
                else
                    btn4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro_large, 0, 0, 0);
            else if (medalha.equals("PRATA"))
                if (smartphone)
                    btn4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata, 0, 0, 0);
                else
                    btn4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata_large, 0, 0, 0);
            else if (medalha.equals("BRONZE"))
                if (smartphone)
                    btn4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze, 0, 0, 0);
                else
                    btn4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze_large, 0, 0, 0);
        } else if (i == 5) {
            if (!medalha.equals("NAO"))
                btn5.setPadding(btn1.getPaddingLeft(), 0, btn1.getPaddingLeft() * 2, 0);
            if (medalha.equals("OURO"))
                if (smartphone)
                    btn5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro, 0, 0, 0);
                else
                    btn5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro_large, 0, 0, 0);
            else if (medalha.equals("PRATA"))
                if (smartphone)
                    btn5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata, 0, 0, 0);
                else
                    btn5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata_large, 0, 0, 0);
            else if (medalha.equals("BRONZE"))
                if (smartphone)
                    btn5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze, 0, 0, 0);
                else
                    btn5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze_large, 0, 0, 0);
        } else if (i == 6) {
            if (!medalha.equals("NAO"))
                btn6.setPadding(btn1.getPaddingLeft(), 0, btn1.getPaddingLeft() * 2, 0);
            if (medalha.equals("OURO"))
                if (smartphone)
                    btn6.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro, 0, 0, 0);
                else
                    btn6.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro_large, 0, 0, 0);
            else if (medalha.equals("PRATA"))
                if (smartphone)
                    btn6.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata, 0, 0, 0);
                else
                    btn6.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata_large, 0, 0, 0);
            else if (medalha.equals("BRONZE"))
                if (smartphone)
                    btn6.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze, 0, 0, 0);
                else
                    btn6.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze_large, 0, 0, 0);
        } else if (i == 7) {
            if (!medalha.equals("NAO"))
                btn7.setPadding(btn1.getPaddingLeft(), 0, btn1.getPaddingLeft() * 2, 0);
            if (medalha.equals("OURO"))
                if (smartphone)
                    btn7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro, 0, 0, 0);
                else
                    btn7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro_large, 0, 0, 0);
            else if (medalha.equals("PRATA"))
                if (smartphone)
                    btn7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata, 0, 0, 0);
                else
                    btn7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata_large, 0, 0, 0);
            else if (medalha.equals("BRONZE"))
                if (smartphone)
                    btn7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze, 0, 0, 0);
                else
                    btn7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze_large, 0, 0, 0);
        } else if (i == 8) {
            if (!medalha.equals("NAO"))
                btn8.setPadding(btn1.getPaddingLeft(), 0, btn1.getPaddingLeft() * 2, 0);
            if (medalha.equals("OURO"))
                if (smartphone)
                    btn8.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro, 0, 0, 0);
                else
                    btn8.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro_large, 0, 0, 0);
            else if (medalha.equals("PRATA"))
                if (smartphone)
                    btn8.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata, 0, 0, 0);
                else
                    btn8.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata_large, 0, 0, 0);
            else if (medalha.equals("BRONZE"))
                if (smartphone)
                    btn8.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze, 0, 0, 0);
                else
                    btn8.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze_large, 0, 0, 0);
        } else if (i == 9) {
            if (!medalha.equals("NAO"))
                btn9.setPadding(btn1.getPaddingLeft(), 0, btn1.getPaddingLeft() * 2, 0);
            if (medalha.equals("OURO"))
                if (smartphone)
                    btn9.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro, 0, 0, 0);
                else
                    btn9.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro_large, 0, 0, 0);
            else if (medalha.equals("PRATA"))
                if (smartphone)
                    btn9.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata, 0, 0, 0);
                else
                    btn9.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata_large, 0, 0, 0);
            else if (medalha.equals("BRONZE"))
                if (smartphone)
                    btn9.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze, 0, 0, 0);
                else
                    btn9.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze_large, 0, 0, 0);
        } else if (i == 10) {
            if (!medalha.equals("NAO"))
                btn10.setPadding(btn1.getPaddingLeft(), 0, btn1.getPaddingLeft() * 2, 0);
            if (medalha.equals("OURO"))
                if (smartphone)
                    btn10.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro, 0, 0, 0);
                else
                    btn10.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro_large, 0, 0, 0);
            else if (medalha.equals("PRATA"))
                if (smartphone)
                    btn10.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata, 0, 0, 0);
                else
                    btn10.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata_large, 0, 0, 0);
            else if (medalha.equals("BRONZE"))
                if (smartphone)
                    btn10.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze, 0, 0, 0);
                else
                    btn10.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze_large, 0, 0, 0);
        }

    }


    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_right2, R.anim.slide_out_left2);
        super.onBackPressed();
    }

}
