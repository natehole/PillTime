package com.example.natha.pilltime;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Jordan on 11/30/2017.
 */

public class dbHelper extends SQLiteOpenHelper {
    private static final Integer DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "medicineReminder";

    private static final String medicineTableName = "medicineTable";
    private static final String reminderTableName = "reminderTable";

    private static final String keyId = "_id";
    private static final String keyName = "name";
    private static final String keyActive = "active";
    private static final String keyPill_Count = "pill_count";
    private static final String keyDosage = "dosage";
    private static final String keyNotes = "notes";

    //table two value
    private static final String keyIdTable = medicineTableName + "_" + keyId;
    private static final String keyIdR = "_idr"; //primary key for table 2
    private static final String keyRemindTime = "remind_time";
    private static final String keyTaken = "taken";



    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createMedicineTable = "CREATE TABLE " + medicineTableName + " ("
                + keyId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + keyName + " TEXT NOT NULL, "
                + keyActive + " TEXT, "
                + keyPill_Count  + " INTEGER, "
                + keyDosage + " TEXT, "
                + keyNotes  + " TEXT)";

        String createReminderTable = "CREATE TABLE " + reminderTableName + " ("
                + keyIdR + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + keyIdTable +  " INTEGER, "
                + keyRemindTime + " INTEGER, "
                + keyTaken + " TEXT)";
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
    public void addTime(Pill pill, Integer time){
        pill.getTimeTaken().put(time, 0);
        SQLiteDatabase db = this.getWritableDatabase();
        String insertStm = "INSERT INTO " + reminderTableName + " (" +
                keyIdTable + ","
                + keyRemindTime  + ","
                + keyTaken + ")"
                + " VALUES( "
                + pill.getId()  + " , '"
                + time + "', "
                + "0)";
        db.execSQL(insertStm);
        db.close();
    }
    public Integer getPillId(Pill pill){
        SQLiteDatabase db = this.getReadableDatabase();
        Integer id = 0;
        String getStm = "SELECT * FROM " + medicineTableName + " WHERE "
                + keyName + " = '" + pill.getName() + "'";
        Cursor c = db.rawQuery(getStm, null);
        if( c != null){
            try {
                c.moveToFirst();
                do {
                    id = c.getInt(0);
                }while(c.moveToNext());
            } catch (Exception e) {}
        }
        db.close();
        return id;
    }
    public void removePill(Pill pill){ // removes the pill
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteStm = "DELETE FROM " + medicineTableName + " WHERE "
                +keyName + " = '" + pill.getName() + "'";
        db.execSQL(deleteStm);
        db.close();
    }

    public void removePillTime(int pillId, int time){
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteStm = "DELETE FROM " + reminderTableName + " WHERE "
                + keyIdTable + " = " + pillId + " AND " + keyRemindTime + " = " + time;
        db.execSQL(deleteStm);
        db.close();
    }

    public void updatePillTime(int pillId, int oldTime, int newTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        String updateStm = "UPDATE " + reminderTableName + " SET "
                + keyRemindTime  + " = " + newTime
                + " WHERE " + keyIdTable + " = " + pillId +
                " AND " + keyRemindTime + " = " + oldTime;
        db.execSQL(updateStm);
        db.close();
    }

    public void updatePill(Pill pill){ //updates pill
        SQLiteDatabase db = this.getWritableDatabase();
        String updateStm = "UPDATE " + medicineTableName + " SET "
                + keyName + " = '" + pill.getName() + "' , "
                + keyActive+ " = " + pill.getActive() + " , "
                + keyPill_Count + " = '" + pill.getPillCount() + "' , "
                + keyDosage + " = '" + pill.getDosage() + "' , "
                + keyNotes   + " = '" + pill.getNotes() + "' "
                + "WHERE " + keyId + " = " + pill.getId();
        db.execSQL(updateStm);
        db.close();
    }
    public Pill getPill(Pill pill){
        SQLiteDatabase db = this.getReadableDatabase();
        String getPillStm = "SELECT * FROM " + medicineTableName + " WHERE "
                + keyId + " = " + pill.getId();
        db.execSQL(getPillStm);
        Cursor c = db.rawQuery(getPillStm, null);
        if( c != null){
            c.moveToFirst();
        }
        //ID, name, active, pillcount , doseage, notes
        Pill pillReturn = new Pill(
                c.getInt(0)
                , c.getString(1)
                , Integer.parseInt(c.getString(2))
                , c.getInt(3)
                , c.getString(4)
                , c.getString(5));
        db.close();
        return pillReturn;
    }
    public Pill getPillByName(String s){
        SQLiteDatabase db = this.getReadableDatabase();
        String getPillStm = "SELECT * FROM " + medicineTableName + " WHERE "
                + keyName + " = '" + s + "'";
        Cursor c = db.rawQuery(getPillStm, null);
        if( c != null){
            c.moveToFirst();
        }
        //ID, name, active, pillcount , doseage, notes
        Pill pillReturn = new Pill(
                c.getInt(0)
                , c.getString(1)
                , Integer.parseInt(c.getString(2))
                , c.getInt(3)
                , c.getString(4)
                , c.getString(5));
        db.close();
        return pillReturn;
    }
    public void getTimes(Pill pill){
        SQLiteDatabase db = this.getReadableDatabase();
        String getTimesStm = "SElECT * FROM " + reminderTableName + " WHERE "
                + keyId + " = " + pill.getId();
        Cursor c = db.rawQuery(getTimesStm, null);
        if( c != null){
            c.moveToFirst();
        }
    }
    public Vector<Pill> getActivePills(){
        SQLiteDatabase db = this.getReadableDatabase();
        String getActiveStm = "SELECT * FROM " + medicineTableName + " WHERE "
                + keyActive + " = 1";
        Cursor c = db.rawQuery(getActiveStm, null);
        Vector<Pill> activePills = new Vector<Pill>();
        if( c != null){
            try {
                c.moveToFirst();
                do {
                    //Pill(Integer id, String name, Integer active, Integer pillCount, String dosage, String notes)
                    activePills.add(new Pill(
                            c.getInt(0), //id
                            c.getString(1), //name
                            Integer.parseInt(c.getString(2)), //active
                            c.getInt(3),
                            c.getString(4),
                            c.getString(5)));
                }while(c.moveToNext());
            } catch (Exception e) {}
            for(Pill p : activePills){
                Map<Integer, Integer> allTimes = this.getAllTimesTaken(p);
                for (Integer i : allTimes.keySet()){
                    p.setTimeTake(i, allTimes.get(i));
                }
            }
        }
        db.close();
        return activePills;
    }
    public Vector<Pill> getAllPills(){
        SQLiteDatabase db = this.getReadableDatabase();
        String insertStm = "SELECT * FROM "+ medicineTableName;
        Cursor c = db.rawQuery(insertStm, null);
        Vector<Pill> allPills = new Vector<Pill>();
        if( c != null){
            try {
                c.moveToFirst();
                do {
                    //Pill(Integerid, String name, Integeractive, IntegerpillCount, String dosage, String notes)
                    allPills.add(new Pill(
                            c.getInt(0), //id
                            c.getString(1), //name
                            Integer.parseInt(c.getString(2)), //active
                            c.getInt(3),
                            c.getString(4),
                            c.getString(5)));
                }while(c.moveToNext());
            } catch (Exception e) {}
            for(Pill p : allPills){
                Vector<Integer> allTimes = p.getAllTimes();
                for (Integer i : allTimes){
                    p.setTimeTake(i, 0);
                }
            }
        }
        db.close();
        return allPills;
    }
    public Map<Integer, Integer> getAllTimesTaken(Pill pill){
        SQLiteDatabase db = this.getReadableDatabase();
        String insertStm = "SELECT * FROM " + reminderTableName + " WHERE "
                + keyIdTable + " = " + pill.getId();
        Cursor c = db.rawQuery(insertStm, null);
        Map<Integer, Integer> allTimes = new HashMap<Integer, Integer>();
        if( c != null){
            try {
                c.moveToFirst();
                do {
                    //Pill(Integer id, String name, Integer active, Integer pillCount, String dosage, String notes)
                    allTimes.put(c.getInt(2), c.getInt(3));
                }while(c.moveToNext());
            } catch (Exception e) {}
        }
        db.close();
        return allTimes;
    }
    public Vector<Integer> getAllTimes(Pill pill){
        SQLiteDatabase db = this.getReadableDatabase();
        String insertStm = "SELECT * FROM " + reminderTableName + " WHERE "
                + keyIdTable + " = " + pill.getId();
        Cursor c = db.rawQuery(insertStm, null);
        Vector<Integer> allTimes = new Vector<Integer>();
        if( c != null){
            try {
                c.moveToFirst();
                do {
                    //Pill(Integer id, String name, Integer active, Integer pillCount, String dosage, String notes)
                    allTimes.add(c.getInt(2));
                }while(c.moveToNext());
            } catch (Exception e) {}
        }
        db.close();
        Collections.sort(allTimes);
        return allTimes;
    }

    /*public Vector<String> getTakenPills(Pill p, Integer taken){
        SQLiteDatabase db = this.getReadableDatabase();
        String getTakenStm = "SELECT * FROM " + reminderTableName + " WHERE "
                + keyTaken + " = '" + taken + "', AND "
                + keyId + " = " + p.getId();
        Vector<Pill> takenPills = new Vector<>();

        Cursor c = db.rawQuery(getTakenStm, null);
        if( c != null){
            c.moveToFirst();
            do {

            }while(c.moveToNext());
            takenPills.add(new Pill(
                    c.getInt(0), //id
                    c.getString(1), //name
                    Integer.parseInt(c.getString(2)), //active
                    c.getInt(3),
                    c.getString(4),
                    c.getString(5)));
        }

        return takenPills;
    }*/

    public boolean checkDB(String s){ //s = name
        Boolean inDb = false; //boolean to display if pill is in DB
        SQLiteDatabase db = this.getReadableDatabase();
        String getPillStm = "SELECT * FROM " + medicineTableName + " WHERE "
                + keyName + " = '" + s + "'";
        Cursor c = db.rawQuery(getPillStm, null);
        if (c!=null){
            c.moveToFirst();
            try{ //try to get the first value
                if(c.getInt(0) > 0)
                {
                    inDb = true;
                }
            }catch(Exception e){ //if there is nothing in database it will fail
                inDb = false;
            }

        }


        db.close();
        return inDb;
    }

    public Integer testTimes(){
        SQLiteDatabase db = this.getReadableDatabase();
        String insertStm = "SELECT * FROM " + reminderTableName;
        Cursor c = db.rawQuery(insertStm, null);
        Integer placeholder = 0;
        Integer placeholder2 = 0;
        Integer placeholder3 = 0;
        Integer placeholder4 = 0;
        if( c != null){
            try {
                c.moveToFirst();
                do {
                    placeholder2 = c.getInt(1);
                    placeholder3 = c.getInt(2);
                    placeholder4 = c.getInt(3);
                    placeholder = c.getInt(0);

                }while(c.moveToNext());
            } catch (Exception e) {}
        }
        db.close();
        return placeholder;
    }
    public String getPillNotes(String s){
        SQLiteDatabase db = this.getReadableDatabase();
        String getPillStm = "SELECT * FROM " + medicineTableName + " WHERE "
                + keyName + " = '" + s + "'";
        Cursor c = db.rawQuery(getPillStm, null);
        if( c != null){
            c.moveToFirst();
        }
        //ID, name, active, pillcount , doseage, notes
        Pill pillReturn = new Pill(
                c.getInt(0)
                , c.getString(1)
                , Integer.parseInt(c.getString(2))
                , c.getInt(3)
                , c.getString(4)
                , c.getString(5));
        db.close();
        return pillReturn.getNotes();
    }
    public boolean hasTime(Pill p, int i){
        Boolean inDb = false; //boolean to display if pill is in DB
        SQLiteDatabase db = this.getReadableDatabase();
        String getPillStm = "SELECT * FROM " + reminderTableName + " WHERE "
                + keyId + " = '" + p.getId() + "'" + " AND " +
                keyRemindTime + " = " + i;
        Cursor c = db.rawQuery(getPillStm, null);
        if( c != null){
            c.moveToFirst();
        }
        if (c.getInt(0) > 0)
        {
            db.close();
            return true;
        }
        else
        {
            db.close();
            return false;
        }
    }
    public void updateTaken(Pill pill, int time, int taken){
        SQLiteDatabase db = this.getWritableDatabase();
        String updateStm = "UPDATE " + reminderTableName + " SET "
                + keyTaken  + " = " + taken
                + " WHERE " + keyIdTable + " = '" + pill.getId() +
                "' AND " + keyRemindTime + " = '" + time + "'";
        db.execSQL(updateStm);
        db.close();
    }
}
