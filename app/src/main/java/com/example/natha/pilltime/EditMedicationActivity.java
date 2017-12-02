package com.example.natha.pilltime;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Vector;

/**
 * Created by natha on 11/30/2017.
 */

public class EditMedicationActivity extends Activity {

    EditText etName;
    CheckBox cbActive;
    EditText etPillCount;
    EditText etDosage;
    EditText etNotes;
    EditText etTime;
    private TimePicker time_picker;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_medication_activity);
        Initialize();

    }

    public void Initialize(){
        etName = (EditText) findViewById(R.id.nameET);
        cbActive = (CheckBox) findViewById(R.id.activeCB);
        etPillCount = (EditText) findViewById(R.id.pillCountET);
        etDosage = (EditText) findViewById(R.id.dosageET);
        etNotes = (EditText) findViewById(R.id.notesET);
        time_picker= (TimePicker) findViewById(R.id.timePicker);

    }
    public void insertToDb(View view){
        dbHelper db = new dbHelper(this);
        int active = 0;
        if(cbActive.isChecked()){
           active = 1;
        }
        else
        {
            active = 0;
        }
        int hour = time_picker.getCurrentHour();
        int minute = time_picker.getCurrentMinute();
        Pill pill = new Pill(
                0,
                etName.getText().toString(),
                active,
                Integer.parseInt(etPillCount.getText().toString()),
                etDosage.getText().toString(),
                etNotes.getText().toString()
        );
        pill.setTimeTake(hour + minute, 0);
        db.addPill(pill);
        db.close();Vector<String> allMeds = new Vector<>();
        Vector<Integer> Times = pill.getAllTimes();
        Toast.makeText(this, Times.get(0), Toast.LENGTH_SHORT).show();
    }
    public void test(View view){
        int hour = time_picker.getCurrentHour();
        int minute = time_picker.getCurrentMinute();
        Toast.makeText(this, hour + ":" + minute, Toast.LENGTH_SHORT).show();


    }
}
