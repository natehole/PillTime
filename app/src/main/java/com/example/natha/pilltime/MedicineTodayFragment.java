package com.example.natha.pilltime;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * Created by natha on 11/30/2017.
 */

public class MedicineTodayFragment extends Fragment {

    //dbHelper db;
    public MedicineTodayFragment() {
    }

    dbHelper db = new dbHelper(getActivity().getApplicationContext());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        //pull active medicines from db
        Vector<Pill> activePills = db.getActivePills();

        Vector<Pill> takenPills = new Vector<>();
        Vector<Pill> notTakenPills = new Vector<>();

        //For each pill that is marked active, search through the times listed to take it and for each time listed
        //Add the pill the taken Pills or not taken pills
        //This list will have duplicates of  pills, one for each time slot
        for(Pill p : activePills) {
            Iterator it = p.getTimeTaken().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                if(pair.getValue() == "1"){
                    takenPills.add(p);
                } else {
                    notTakenPills.add(p);
                }
                it.remove();
            }
        }

        View todayView = inflater.inflate(R.layout.today_fragment, container, false);
        ListView medicinesTaken = (ListView) todayView.findViewById(R.id.takenLV);
        ListView medicinesNotTaken = (ListView) todayView.findViewById(R.id.notTakenLV);
        //Set adapater and create it from queried data
        //I think takenPills and notTakenPills each need to be parsed and turned into just strings or something
        // each pill needs its own time at this point, fuuuuck

        return todayView;
    }
}