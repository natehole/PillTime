package com.example.natha.pilltime;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Jordan on 11/30/2017.
 */

public class Pill extends Activity{
    private int Id;
    private String name;
    private Integer Active;
    private Integer pillCount;
    private String dosage;
    private String notes;
    private Map<Integer, Integer> timeTaken = new HashMap<Integer, Integer>();
    // Key is Time i.e. 700 is 7am,
    // Value is 0 or 1 for false or true as in the medication to take at that time has been taken or has not been taken

    public Pill() {}

    public Pill(int id, String name, int active, int pillCount, String dosage, String notes) {
        this.Id = id;
        this.name = name;
        this.Active = active;
        this.pillCount = pillCount;
        this.dosage = dosage;
        this.notes = notes;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String n) {name = n;}
    public int getActive()
    {
        //1 = true, 0 = false
        return  this.Active;
    }
    public Integer getPillCount() {
        return pillCount;
    }

    public void setPillCount(Integer pillCount) {
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

    public Vector<Integer> getAllTimes(){
        Vector<Integer> allTimes = new Vector<>();
        for (int key: timeTaken.keySet()) {
            allTimes.add(key);
        }
        return allTimes;
    }
    public void setTimeTake(int t, int taken){
        timeTaken.put(t,taken);
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

    public Vector<String> getAllTimesS(){
        Vector<String> allTimes = new Vector<>();
        for (Integer key: timeTaken.keySet()) {
            allTimes.add(key.toString());
        }
        return allTimes;
    }
}
