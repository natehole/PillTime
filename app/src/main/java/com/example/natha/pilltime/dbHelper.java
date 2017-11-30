package com.example.natha.pilltime;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jordan on 11/30/2017.
 */

public class dbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "medicineReminder";

    private static final String medicineTableName = "medicineTable";
    private static final String reminderTableName = "reminderTable";

    private static final String keyId = "_id";
    private static final String keyName = "name";
    private static final String keyActive = "active";
    private static final String keyPill_Count = "pill_count";
    private static final String keyDosage = "doseage";
    private static final String keyNotes = "notes";

    //table two value
    private static final String keyIdR = "_idr"; //primary key for table 2
    private static final String keyRemindTime = "remind_time";



    public dbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createMedicineTable = "CREATE TABLE " + medicineTableName + " ("
                + keyId + " INTERGER PRIMARY KEY, "
                + keyName + " TEXT NOT NULL, "
                + keyActive + " TEXT, "
                + keyPill_Count  + " INTEGER, "
                + keyDosage + " TEXT, "
                + keyNotes  + " TEXT, "
                + ")";

        String createReminderTable = "CREATE TABLE " + reminderTableName + " ("
                + keyIdR + " INTERGER PRIMARY KEY, "
                + medicineTableName + "_" + keyId + " INTEGER, "
                + keyRemindTime + " TEXT, ";
        db.execSQL(createMedicineTable);
        db.execSQL(createReminderTable);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + medicineTableName);
        db.execSQL("DROP TABLE IF EXISTS " + reminderTableName);
        onCreate(db);
    }
    //pill object
    public void addPill(Pill pill){

        SQLiteDatabase db = this.getWritableDatabase();
        String insertStm = "INSERT INTO " + medicineTableName + " (" +
                keyName + ","
                + keyActive + ","
                + keyPill_Count  + ","
                + keyDosage + ","
                + keyNotes   + ")"
                + " VALUES( '"
                + pill.getName() + "' , '"
                + pill.getActive() + "' , '"
                + pill.getPillCount() + "' , '"
                + pill.getDosage() + "' , '"
                + pill.getNotes() + "' )";
        db.execSQL(insertStm);
        db.close();
    }

    public void addTime(Pill pill, int x){

        SQLiteDatabase db = this.getWritableDatabase();
        String insertStm = "INSERT INTO " + medicineTableName + " (" +
                keyId + ","
                + keyRemindTime  + ")"
                + " VALUES( '"
                + pill.getId()  + "' , '"
                + pill.getTimes(x) + "' )";
        db.execSQL(insertStm);
        db.close();
    }
    public void removePill(Pill pill){ // removes the pill
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteStm = "DELETE FROM " + medicineTableName + " WHERE "
                +keyName + " = '" + pill.getName() + "'";
    }
    public void updatePill(Pill pill){ //updates pill
        SQLiteDatabase db = this.getWritableDatabase();
        String updateStm = "UPDATE " + medicineTableName + " SET "
                + keyName + " = '" + pill.getName() + "' , "
                + keyActive+ " = '" + pill.getActive() + "' , "
                + keyPill_Count + " = '" + pill.getPillCount() + "' , "
                + keyDosage + " = '" + pill.getDosage() + "' , "
                + keyNotes   + " = ;" + pill.getNotes() + "' "
                + "WHERE " + keyId + " = " + pill.getId();
        db.execSQL(updateStm);
        db.close();
    }
    public Pill getPill(Pill pill){
        SQLiteDatabase db = this.getWritableDatabase();
        String getPillStm = "SELECT * FROM " + medicineTableName + " WHERE "
                + keyId + " = " + pill.getId();
        db.execSQL(getPillStm);
        Cursor c = db.rawQuery(getPillStm, null);
        if( c != null){
            c.moveToFirst();
        }
        //ID, name, active, pillcount , doseage, notes
        Pill pillReturn = new Pill(
                Integer.parseInt(c.getString(0))
                , c.getString(1)
                , Integer.parseInt(c.getString(2))
                , Integer.parseInt(c.getString(3))
                , c.getString(4)
                , Integer.parseInt(c.getString(5))
                , c.getString(6));
        db.close();
        return pillReturn;
    }
    public void getTimes(Pill pill){
        SQLiteDatabase db = this.getWritableDatabase();
        String getTimesStm = "SElECT * FROM " + reminderTableName + " WHERE "
                + keyId + " = " + pill.getId();
        Cursor c = db.rawQuery(getTimesStm, null);
        if( c != null){
            c.moveToFirst();
        }

    }
}
