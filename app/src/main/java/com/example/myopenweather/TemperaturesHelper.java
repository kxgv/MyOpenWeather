package com.example.myopenweather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TemperaturesHelper extends SQLiteOpenHelper {

    private static int DB_VERSION = 1;
    private static String DB_NAME = "OpenWeather.db";

    public TemperaturesHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TemperaturesContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TemperaturesContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void saveData(String nomCiutat, List<Temp> blocs) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = null;
        for(int i = 0; i < blocs.size(); i++){
            values = new ContentValues();
            values.put(TemperaturesContract.NOMBRE_COLUMNA_NOMCIUTAT, nomCiutat);
            values.put(TemperaturesContract.NOMBRE_COLUMNA_HORES, blocs.get(i).getData());
            values.put(TemperaturesContract.NOMBRE_COLUMNA_TEMPS, blocs.get(i).getTempe().replace(",", "."));

            db.insert(TemperaturesContract.TABLE_NAME, null, values);
        }
    }

    public void deleteData(String nomCiutat) {

        String whereClause = TemperaturesContract.NOMBRE_COLUMNA_NOMCIUTAT + " = ?";
        String[] whereArgs = {nomCiutat};

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TemperaturesContract.TABLE_NAME, whereClause, whereArgs);
        //db.delete(String table, String whereClause, String[] whereArgs)
    }

    public List<Temp> readData(String nomCiutat) throws ParseException {
        Temp ciutat;
        List<Temp> mostrar = new ArrayList<Temp>();
        SQLiteDatabase db = this.getReadableDatabase();

        String [] projection = {
                TemperaturesContract.NOMBRE_COLUMNA_NOMCIUTAT,
                TemperaturesContract.NOMBRE_COLUMNA_HORES,
                TemperaturesContract.NOMBRE_COLUMNA_TEMPS
        };

        String selection = TemperaturesContract.NOMBRE_COLUMNA_NOMCIUTAT + " = ?";
        String[] selectionArgs = {nomCiutat};

        Cursor cursor = db.query(
                TemperaturesContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String calorFred;
            String hora = cursor.getString(1);
            String temperatura = cursor.getString(2);

            Double tempInt = Double.parseDouble(temperatura);

            if (tempInt >= 20) {
                calorFred = "Calor";
            } else {
                calorFred = "Fred";
            }

            ciutat = new Temp(hora, temperatura, calorFred,"");
            mostrar.add(ciutat);
        }
        cursor.close();
        return mostrar;
    }

    public boolean estaDescarregada(String nomCiutat) throws ParseException{
        return true;
    }

    public boolean estaActualitzada(String nomCiutat) throws ParseException {

        SQLiteDatabase db = this.getReadableDatabase();
        String hora="";
        Date fechaBBDD= new Date();
        Date fechanow = new Date();

        long miliseconds = fechaBBDD.getTime();
        Date newfecha = new Date(miliseconds);

        return true;
    }
}