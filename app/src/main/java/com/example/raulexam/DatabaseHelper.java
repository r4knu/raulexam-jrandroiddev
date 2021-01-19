package com.example.raulexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="INVENTORY.db";
    public  static  final int DB_VERSION=1;

    public static final String TABLE_NAME="product";

    public static final String COL_1="ID";
    public static final String COL_2="IMAGE";
    public static final String COL_3="NAME";
    public static final String COL_4="UNIT";
    public static final String COL_5="PRICE";
    public static final String COL_6="DATE" ;
    public  static final String COL_7="QTY";

    public static final String CREATE_TABLE ="CREATE TABLE " + TABLE_NAME + " ("
            + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_2 + " TEXT, "
            + COL_3 + " TEXT, "
            + COL_4 + " TEXT, "
            + COL_5 + " TEXT, "
            + COL_6 + " TEXT, "
            + COL_7 + " TEXT "
            +");";
    public DatabaseHelper(@Nullable Context context){
        super(context,DB_NAME,null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertProd(String image, String name, String unit, String price,String date,String quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2,image);
        values.put(COL_3,name);
        values.put(COL_4,unit);
        values.put(COL_5,price);
        values.put(COL_6,date);
        values.put(COL_7,quantity);

        Long resID = db.insert(TABLE_NAME,null,values);
        db.close();
        if(resID == 0){
            return false;
        }else {
            return true;
        }
    }
    public void updateProd(String id,String name, String unit, String price,String date,String quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_3,name);
        values.put(COL_4,unit);
        values.put(COL_5,price);
        values.put(COL_6,date);
        values.put(COL_7,quantity);
        db.update(TABLE_NAME,values,COL_1+"=?",new String[]{id});
    }
    public ArrayList<Model> getAllData(String orderBy){
        ArrayList<Model> arrayList = new ArrayList<>();
        String query = " SELECT * FROM "+TABLE_NAME+" ORDER BY "+orderBy;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToNext()){
            do{

                Model model = new Model(
                                ""+cursor.getInt(cursor.getColumnIndex(COL_1)),
                        ""+cursor.getString(cursor.getColumnIndex(COL_2)),
                        ""+cursor.getString(cursor.getColumnIndex(COL_3)),
                        ""+cursor.getString(cursor.getColumnIndex(COL_4)),
                        ""+cursor.getString(cursor.getColumnIndex(COL_5)),
                        ""+cursor.getString(cursor.getColumnIndex(COL_6)),
                        ""+cursor.getString(cursor.getColumnIndex(COL_7))
                );
                arrayList.add(model);
            }while(cursor.moveToNext());
        }
        db.close();
        return arrayList;
    }
}
