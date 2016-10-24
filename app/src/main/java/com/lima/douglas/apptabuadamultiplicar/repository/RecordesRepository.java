package com.lima.douglas.apptabuadamultiplicar.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lima.douglas.apptabuadamultiplicar.util.Constantes;
import com.lima.douglas.apptabuadamultiplicar.util.RecordesEstrutura;
import com.lima.douglas.apptabuadamultiplicar.util.RecordesTreinamento;

import java.util.ArrayList;
import java.util.List;


public class RecordesRepository extends SQLiteOpenHelper {

    public String sql = "CREATE TABLE IF NOT EXISTS RECORDES (" +
            "_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "PONTUACAO INTEGER," +
            "TIPORECORDE TEXT);";

    public String sql2 = "CREATE TABLE IF NOT EXISTS TREINAMENTO (" +
            "_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "STARTIPO TEXT NOT NULL, " +
            "NIVEL TEXT);";


    public RecordesRepository(Context context) {
        super(context, Constantes.BD_NOME, null, Constantes.BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL(sql);
        bd.execSQL(sql2);

        ContentValues values = new ContentValues();
        // iniciando todos os valores da tabela treinamento com  nao como padrao;
        for (int i = 1; i <= 10; i++) {
            values.put("STARTIPO", "NAO");
            values.put("NIVEL", "INICIANTE");
            bd.insert("TREINAMENTO", null, values);
        }
        // iniciando todos os valores da tabela treinamento com  nao como padrao;
        for (int i = 1; i <= 10; i++) {
            values.put("STARTIPO", "NAO");
            values.put("NIVEL", "INTERMEDIARIO");
            bd.insert("TREINAMENTO", null, values);
        }
        // iniciando todos os valores da tabela treinamento com  nao como padrao;
        for (int i = 1; i <= 10; i++) {
            values.put("STARTIPO", "NAO");
            values.put("NIVEL", "EXPERIENTE");
            bd.insert("TREINAMENTO", null, values);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int i, int i1) {}

    public List<RecordesEstrutura> getRecordes(String tipo) {
        RecordesEstrutura estrutura;
        List<RecordesEstrutura> listRecordes = new ArrayList<RecordesEstrutura>();
        SQLiteDatabase bd = getReadableDatabase();

        Cursor cursor = bd.query("RECORDES", null, "PONTUACAO > ? and TIPORECORDE = ?", new String[]{"0", tipo}, "PONTUACAO", null, "PONTUACAO DESC", "3");

        while (cursor.moveToNext()) {
            estrutura = new RecordesEstrutura();
            estrutura.setId(cursor.getInt(cursor.getColumnIndex("_ID")));
            estrutura.setPontuacao(cursor.getInt(cursor.getColumnIndex("PONTUACAO")));
            listRecordes.add(estrutura);
        }
        // fechando banco;
        cursor.close();
        bd.close();

        return listRecordes;
    }

    public String getTreinamento(String nivel, String numero) {
        String resultado;
        SQLiteDatabase bd = getReadableDatabase();
        Cursor cursor = bd.query("TREINAMENTO", null, "_ID = ? and NIVEL = ?", new String[]{numero, nivel}, null, null, null, null);
        cursor.moveToFirst();

        resultado = cursor.getString( cursor.getColumnIndex("STARTIPO") );

        // fechando banco;
        cursor.close();
        bd.close();

        return resultado;
    }

    public void setRecorde(String nivel, int pontuacao) {
        SQLiteDatabase bd = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("TIPORECORDE", nivel);
        values.put("PONTUACAO", pontuacao);

        // inserindo novo recorde no banco.
        bd.insert("RECORDES", null, values);
    }

    public void removerRecorde(String nivel, int pontuacao) {
        SQLiteDatabase bd = getWritableDatabase();
        // removendo valor do banco.
        bd.delete("RECORDES", "PONTUACAO = ? and TIPORECORDE = ?", new String[] {String.valueOf(pontuacao), nivel});
    }

}
