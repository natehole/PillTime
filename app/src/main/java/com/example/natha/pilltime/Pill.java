package com.example.natha.pilltime;

import android.app.Activity;

import java.util.Vector;

/**
 * Created by Jordan on 11/30/2017.
 */

public class Pill extends Activity{
    private int Id;
    private String name;
    private int Active;
    private int pillCount;
    private String dosage;
    private  Vector<Integer> time = new Vector<Integer>();
    private String notes;
    public Pill(int id, String name, int active, int pillCount, String dosage, int times, String notes) {
        this.Id = id;
        this.name = name;
        Active = active;
        this.pillCount = pillCount;
        this.dosage = dosage;
        this.notes = notes;
    }
    public String getName()
    {
        return name;
    }
    public int getActive()
    {
        //1 = true, 0 = false
        return  Active;
    }
    public int getPillCount() {
        return pillCount;
    }

    public void setPillCount(int pillCount) {
        this.pillCount = pillCount;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setActive(int active) {
        Active = active;
    }

    public int getTimes(int x) {
        return time.get(x);
    }

    public void setTimes(int t) {
        time.add(t);
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
