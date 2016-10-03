package com.lima.douglas.apptabuadamultiplicar.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lima.douglas.apptabuadamultiplicar.util.Constantes;
import com.lima.douglas.apptabuadamultiplicar.util.RecordesEstrutura;

import java.util.ArrayList;
import java.util.List;


public class RecordesRepository extends SQLiteOpenHelper {

    public String sql = "CREATE TABLE IF NOT EXISTS RECORDES (" +
                        "_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "PONTUACAO INTEGER NOT NULL);";

    public RecordesRepository(Context context) {
        super(context, Constantes.BD_NOME, null, Constantes.BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int i, int i1) {
    }

    public List<RecordesEstrutura> getRecordes() {
        RecordesEstrutura estrutura;
        List<RecordesEstrutura> listRecordes = new ArrayList<RecordesEstrutura>();
        SQLiteDatabase bd = getReadableDatabase();

        Cursor cursor = bd.query("RECORDES", null, "PONTUACAO > ?", new String[]{"0"}, "PONTUACAO", null, "PONTUACAO DESC", "8");

        while(cursor.moveToNext()) {
            estrutura = new RecordesEstrutura();
            estrutura.setId(cursor.getInt(cursor.getColumnIndex("_ID")));
            estrutura.setPontucacao(cursor.getInt(cursor.getColumnIndex("PONTUACAO")));
            listRecordes.add(estrutura);
        }

        return listRecordes;
    }
}
