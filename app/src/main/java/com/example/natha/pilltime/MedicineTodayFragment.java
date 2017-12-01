package com.example.natha.pilltime;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;


public class MedicineTodayFragment extends Fragment {
    public MedicineTodayFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstance) {
        dbHelper db = new dbHelper(getActivity().getApplicationContext());
        //pull active medicines from db
        Vector<Pill> activePills = db.getActivePills();

        Vector<String> takenPills = new Vector<String>();
        Vector<String> notTakenPills = new Vector<String>();

        //For each pill that is marked active, search through the times listed to take it and for each time listed
        //Add the pill the taken Pills or not taken pills
        //This list will have duplicates of  pills, one for each time slot
        for(Pill p : activePills) {
            Iterator it = p.getTimeTaken().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                if(pair.getValue() == "1"){
                    String out = p.getName() + '\n' + p.getDosage()  + "\n" + pair.getKey();
                    takenPills.add(out);
                } else {
                    String out = p.getName() + '\n' + p.getDosage()  + "\n" + pair.getKey();
                    notTakenPills.add(out);
                }
                it.remove();
            }
        }

        final ArrayAdapter<String> taken = new ArrayAdapter<String>(getActivity(), R.layout.pill_list_item, R.id.pillItemTV, takenPills);
        final ArrayAdapter<String> notTaken = new ArrayAdapter<String>(getActivity(), R.layout.pill_list_item, R.id.pillItemTV, notTakenPills);

        View todayView = inflater.inflate(R.layout.today_fragment, container, false);
        ListView medicinesTaken = (ListView) todayView.findViewById(R.id.takenLV);
        ListView medicinesNotTaken = (ListView) todayView.findViewById(R.id.notTakenLV);

        medicinesTaken.setAdapter(taken);
        medicinesNotTaken.setAdapter(notTaken);

        final Intent editIntent = Intent(, EditMedicationActivity.class);

        medicinesTaken.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                editIntent.putExtra(taken.getItem(position));
            }
        });

        medicinesNotTaken.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editIntent.putExtra(notTaken.getItem(position));
            }
        });

        /*AdapterView.OnItemClickListener medListener = new AdapterView.OnItemClickListener(){
            @Override
            public onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                String selectedItem = mainEventAdapter.getItem(i);
            }
        }*/


        return todayView;
    }
}