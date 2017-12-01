package com.example.natha.pilltime;

import android.app.Activity;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
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

    // Key is Time i.e. 700 is 7am,
    // Value is 0 or 1 for false or true as in the medication to take at that time has been taken or has not been taken
    //private Dictionary<Integer, Integer> timeTaken = new Dictionary<Integer, Integer>();
    private Map<Integer, Integer> timeTaken = new HashMap<Integer, Integer>();
    //private Vector<Integer> time = new Vector<Integer>();
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

    public Map<Integer, Integer> getTimeTaken(){
        return timeTaken;
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
