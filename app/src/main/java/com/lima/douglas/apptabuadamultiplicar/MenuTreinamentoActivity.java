package com.lima.douglas.apptabuadamultiplicar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.lima.douglas.apptabuadamultiplicar.repository.RecordesRepository;


public class MenuTreinamentoActivity extends AppCompatActivity {
    Intent i;

    String medalha = new String();

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
    //todo fazer aparecer os trofeus em todos os botões;

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
        actionBar.setTitle(R.string.treinamento);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // pegando argumento passado pela intent.
        i = getIntent();
        tipo = i.getStringExtra("tipo");
        setIcon();

    }

    public void abrirTreino(View view) {
        if (tipo.equals("iniciante")) {
            i = new Intent(this, TelaTreinamentoInicianteActivity.class);
            String valor = (String) view.getTag();
            i.putExtra("valor", valor);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

        if (tipo.equals("intermediario")) {
            i = new Intent(this, TelaTreinamentoIntermediarioActivity.class);
            String valor = (String) view.getTag();
            i.putExtra("valor", valor);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }


        if (tipo.equals("experiente")) {
            i = new Intent(this, TelaTreinamentoExperienteActivity.class);
            String valor = (String) view.getTag();
            i.putExtra("valor", valor);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
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
                    medalha = repository.getTreinamento("INTERMEDIARIO", String.valueOf(i));
                    alterarBotao(medalha, i);
                }
                break;


            case "experiente":
                for (int i = 1; i <= 10; i++) {
                    medalha = repository.getTreinamento("EXPERIENTE", String.valueOf(i));
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
                btn1.setPadding(25, 0, 50, 0);
            if (medalha.equals("OURO"))
                btn1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro, 0, 0, 0);
            else if (medalha.equals("PRATA"))
                btn1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata, 0, 0, 0);
            else if (medalha.equals("BRONZE"))
                btn1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze, 0, 0, 0);
        } else if (i == 2) {
            if (!medalha.equals("NAO"))
                btn2.setPadding(25, 0, 50, 0);
            if (medalha.equals("OURO"))
                btn2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro, 0, 0, 0);
            else if (medalha.equals("PRATA"))
                btn2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata, 0, 0, 0);
            else if (medalha.equals("BRONZE"))
                btn2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze, 0, 0, 0);
        } else if (i == 3) {
            if (!medalha.equals("NAO"))
                btn3.setPadding(25, 0, 50, 0);
            if (medalha.equals("OURO"))
                btn3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro, 0, 0, 0);
            else if (medalha.equals("PRATA"))
                btn3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata, 0, 0, 0);
            else if (medalha.equals("BRONZE"))
                btn3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze, 0, 0, 0);
        } else if (i == 4) {
            if (!medalha.equals("NAO"))
                btn4.setPadding(25, 0, 50, 0);
            if (medalha.equals("OURO"))
                btn4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro, 0, 0, 0);
            else if (medalha.equals("PRATA"))
                btn4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata, 0, 0, 0);
            else if (medalha.equals("BRONZE"))
                btn4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze, 0, 0, 0);
        } else if (i == 5) {
            if (!medalha.equals("NAO"))
                btn5.setPadding(25, 0, 50, 0);
            if (medalha.equals("OURO"))
                btn5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro, 0, 0, 0);
            else if (medalha.equals("PRATA"))
                btn5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata, 0, 0, 0);
            else if (medalha.equals("BRONZE"))
                btn5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze, 0, 0, 0);
        } else if (i == 6) {
            if (!medalha.equals("NAO"))
                btn6.setPadding(25, 0, 50, 0);
            if (medalha.equals("OURO"))
                btn6.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro, 0, 0, 0);
            else if (medalha.equals("PRATA"))
                btn6.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata, 0, 0, 0);
            else if (medalha.equals("BRONZE"))
                btn6.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze, 0, 0, 0);
        } else if (i == 7) {
            if (!medalha.equals("NAO"))
                btn7.setPadding(25, 0, 50, 0);
            if (medalha.equals("OURO"))
                btn7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro, 0, 0, 0);
            else if (medalha.equals("PRATA"))
                btn7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata, 0, 0, 0);
            else if (medalha.equals("BRONZE"))
                btn7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze, 0, 0, 0);
        } else if (i == 8) {
            if (!medalha.equals("NAO"))
                btn8.setPadding(25, 0, 50, 0);
            if (medalha.equals("OURO"))
                btn8.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro, 0, 0, 0);
            else if (medalha.equals("PRATA"))
                btn8.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata, 0, 0, 0);
            else if (medalha.equals("BRONZE"))
                btn8.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze, 0, 0, 0);
        } else if (i == 9) {
            if (!medalha.equals("NAO"))
                btn9.setPadding(25, 0, 50, 0);
            if (medalha.equals("OURO"))
                btn9.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro, 0, 0, 0);
            else if (medalha.equals("PRATA"))
                btn9.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata, 0, 0, 0);
            else if (medalha.equals("BRONZE"))
                btn9.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze, 0, 0, 0);
        } else if (i == 10) {
            if (!medalha.equals("NAO"))
                btn10.setPadding(25, 0, 50, 0);
            if (medalha.equals("OURO"))
                btn10.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_ouro, 0, 0, 0);
            else if (medalha.equals("PRATA"))
                btn10.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_prata, 0, 0, 0);
            else if (medalha.equals("BRONZE"))
                btn10.setCompoundDrawablesWithIntrinsicBounds(R.drawable.trofeu_bronze, 0, 0, 0);
        }

    }

    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_right2, R.anim.slide_out_left2);
        super.onBackPressed();
    }
}
