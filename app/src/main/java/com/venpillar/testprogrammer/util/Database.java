package com.venpillar.testprogrammer.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    public Database(Context context) {
        super(context, "DatabaseKaryawan.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table table_karyawan(id TEXT primary key, name TEXT, date Date, age Number)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists table_karyawan");
    }

    private String generateId(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select id from table_karyawan", null);
        int count = cursor.getCount() + 1;
        String generatedId = String.format("%3s", count).replace(" ","0");
        return generatedId;
    }

    public Boolean insertKaryawan(String name, String date, String age)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", generateId());
        contentValues.put("name", name);
        contentValues.put("date", date);
        contentValues.put("age", age);

        long result = DB.insert("table_karyawan", null, contentValues);

        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public Boolean updateKaryawan(String id, String name, String date, String age)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("date", date);
        contentValues.put("age", age);

        Cursor cursor = DB.rawQuery("Select * from table_karyawan where id = ?", new String[]{id});

        if (cursor.getCount() > 0) {
            long result = DB.update("table_karyawan", contentValues, "id=?", new String[]{id});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    public Boolean deleteKaryawan (String id)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from table_karyawan where id = ?", new String[]{id});

        if (cursor.getCount() > 0) {
            long result = DB.delete("table_karyawan", "id = ?", new String[]{id});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getdata (String fromName, String toName, String fromAge, String toAge, String fromDate, String toDate)
    {
        String query = "SELECT * from table_karyawan";

        Boolean prev = false;

        if(!fromName.equals("") || !toName.equals("") || !fromAge.equals("") || !toAge.equals("") || !fromDate.equals("") || !toDate.equals("")){
            query +=  " where ";
        }

        if(!fromName.equals("")) {
            query += " name LIKE \"%"+fromName+"%\" ";
            prev = true;
        }

        if(!toName.equals("")){
            if(prev){
                query += " OR ";
            }

            query += " name LIKE \"%"+toName+"%\" ";
            prev = true;
        }

        if(!fromAge.equals("") && !toAge.equals("")){
            if(prev){
                query += " AND ";
            }

            query += " age >= "+fromAge+" AND age <= "+toAge+" ";
            prev = true;
        }

        if(!fromDate.equals("") && !toDate.equals("")) {
            if(prev){
                query += " AND ";
            }

            query += " date >= \""+fromDate+"\" AND date <= \""+toDate+"\" ";
        }

        Log.e("Error", query);

        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery(query, null);

        return cursor;
    }
}
