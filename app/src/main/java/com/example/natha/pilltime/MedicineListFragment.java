package com.example.natha.pilltime;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Vector;

/**
 * Created by natha on 11/30/2017.
 */

public class MedicineListFragment extends Fragment {
    public MedicineListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        dbHelper db = new dbHelper(getActivity().getApplicationContext());
        View medListView = inflater.inflate(R.layout.medlist_fragment, container, false);
        ListView medicines = (ListView) medListView.findViewById(R.id.medicationLV);
        Vector<Pill> allPills = db.getAllPills();

        Vector<String> allMeds = new Vector<>();

        for(Pill p : allPills) {
            allMeds.add(p.getName() + '\n' +
                    p.getDosage() + '\n' +
                    p.getActive() + '\n' +
                    p.getTimeTaken() + '\n' +
                    p.getNotes());
        }

        ArrayAdapter<String> meds = new ArrayAdapter<>(getActivity(), R.layout.pill_list_item, R.id.pillItemTV, allMeds);
        medicines.setAdapter(meds);

        return medListView;
    }
}
