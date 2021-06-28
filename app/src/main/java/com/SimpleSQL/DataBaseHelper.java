package com.SimpleSQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    // This defines the table in general, it stipulates the database and table name and the
    // subsequent columns.
    public static final String DATABASE_NAME = "Notes.db";
    public static final String TABLE_NAME = "Notes_Table";
    public static final String COL_1 = "ID"; //0
    public static final String COL_2 = "DATE"; //1
    public static final String COL_3 = "NAME"; //2
    public static final String COL_4 = "SUBJ"; //3
    public static final String COL_5 = "DATEDUE"; //4
    public static final String COL_6 = "TIME"; //5
    public static final String COL_7 = "NOTES"; //6
    // The constructor class
    public DataBaseHelper(@Nullable Context context ) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT, " +
                COL_3 + " TEXT, " +
                COL_4 + " TEXT, " +
                COL_5 + " TEXT, " +
                COL_6 + " TEXT, " +
                COL_7 + " TEXT" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);}
    // This adds the data to the table.
    public void addData(String dat, String nam, String subj, String ddue, String time, String note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cV = new ContentValues();
        cV.put(COL_2, dat);
        cV.put(COL_3, nam);
        cV.put(COL_4, subj);
        cV.put(COL_5, ddue);
        cV.put(COL_6, time);
        cV.put(COL_7, note);
        db.insert(TABLE_NAME,null,cV);}
    // This takes the ID and deletes the other inputs associated with it, it is the whereClause, by
    // changing the whereClause it will change the criteria for selecting the table row.

    public void delData(String nam){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "NAME =?", new String[]{ nam });
    }

    // Shows all the entries
    public Cursor viewAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor R = db.rawQuery("select * from "+TABLE_NAME,null);
        return R;
        //OR return db.rawQuery("select * from "+TABLE_NAME,null);
    }

    // Takes the id from an input in the main example and use it to update and edit an existing
    // entry by id. Again by modifying the whereClause you can change by which characteristics
    // you modify the table.
    public void update(String dat, String nam, String subj, String ddue, String time, String note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cV = new ContentValues();
        cV.put(COL_2, dat);
        cV.put(COL_3, nam);
        cV.put(COL_4, subj);
        cV.put(COL_5, ddue);
        cV.put(COL_6, time);
        cV.put(COL_7, note);
        db.update(TABLE_NAME, cV, "NAME =?", new String[]{ nam });
    }
}
