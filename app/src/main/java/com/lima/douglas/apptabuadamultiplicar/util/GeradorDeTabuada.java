package com.lima.douglas.apptabuadamultiplicar.util;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lima.douglas.apptabuadamultiplicar.R;

public class GeradorDeTabuada extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    // criando alertdialog customizado da tabuada e retornando um buider.
    public AlertDialog.Builder mostrarTabuada( Activity activity, String valor) {

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View viewAlertDialog = inflater.inflate(R.layout.tabuada_activity, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //builder.setTitle("Tabuada do " + valor);
        ((TextView) viewAlertDialog.findViewById(R.id.txtNumTabuada)).setText(valor + " X");

        TextView um = (TextView) viewAlertDialog.findViewById(R.id.txt1);
        TextView dois = (TextView) viewAlertDialog.findViewById(R.id.txt2);
        TextView tres = (TextView) viewAlertDialog.findViewById(R.id.txt3);
        TextView quatro = (TextView) viewAlertDialog.findViewById(R.id.txt4);
        TextView cinco = (TextView) viewAlertDialog.findViewById(R.id.txt5);
        TextView seis = (TextView) viewAlertDialog.findViewById(R.id.txt6);
        TextView sete = (TextView) viewAlertDialog.findViewById(R.id.txt7);
        TextView oito = (TextView) viewAlertDialog.findViewById(R.id.txt8);
        TextView nove = (TextView) viewAlertDialog.findViewById(R.id.txt9);
        TextView dez = (TextView) viewAlertDialog.findViewById(R.id.txt10);

        um.setText(valor + " * 1   = " + String.valueOf(Integer.valueOf(valor) * 1));
        dois.setText(valor + " * 2   = " + String.valueOf(Integer.valueOf(valor) * 2));
        tres.setText(valor + " * 3   = " + String.valueOf(Integer.valueOf(valor) * 3));
        quatro.setText(valor + " * 4   = " + String.valueOf(Integer.valueOf(valor) * 4));
        cinco.setText(valor + " * 5   = " + String.valueOf(Integer.valueOf(valor) * 5));
        seis.setText(valor + " * 6   = " + String.valueOf(Integer.valueOf(valor) * 6));
        sete.setText(valor + " * 7   = " + String.valueOf(Integer.valueOf(valor) * 7));
        oito.setText(valor + " * 8   = " + String.valueOf(Integer.valueOf(valor) * 8));
        nove.setText(valor + " * 9   = " + String.valueOf(Integer.valueOf(valor) * 9));
        dez.setText(valor + " * 10 = " + String.valueOf(Integer.valueOf(valor) * 10));


        // solucionando problema da tela de 3.2 (normal).
        if (getTamanhoHeight(1, activity) == 480 && getTamanhoHeight(0, activity) == 320) {

            // obtendo densidade da tela.
            float density = activity.getResources().getDisplayMetrics().density;

            // escolhendo tamanho dos TextView.
            int tam = (int)  (density * 20);

            //alterando tamanho dos TextView.
            um.setTextSize(tam);
            dois.setTextSize(tam);
            tres.setTextSize(tam);
            quatro.setTextSize(tam);
            cinco.setTextSize(tam);
            seis.setTextSize(tam);
            sete.setTextSize(tam);
            oito.setTextSize(tam);
            nove.setTextSize(tam);
            dez.setTextSize(tam);
        }

        builder.setView(viewAlertDialog);
        return builder;
    }



    // retornando tamanho da tela.
    public int getTamanhoHeight(int i, Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        if (i == 1)
            return height;
        else
            return width;
    }

}
