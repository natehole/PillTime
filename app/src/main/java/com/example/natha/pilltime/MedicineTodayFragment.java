package com.example.natha.pilltime;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by natha on 11/30/2017.
 */

public class MedicineTodayFragment extends Fragment {

    public MedicineTodayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        //pull active medicines from db
        //for each medicine taken today fill an array or something

        View todayView = inflater.inflate(R.layout.today_fragment, container, false);
        ListView medicinesTaken = (ListView) todayView.findViewById(R.id.takenLV);
        ListView medicinesNotTaken = (ListView) todayView.findViewById(R.id.notTakenLV);
        //Set adapater and create it from queried data

        return todayView;
    }
}