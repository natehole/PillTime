package com.example.natha.pilltime;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by natha on 11/30/2017.
 */

public class MedicineListFragment extends Fragment {
    public MedicineListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View medListView = inflater.inflate(R.layout.medlist_fragment, container, false);
        ListView medicines = (ListView) medListView.findViewById(R.id.medicationLV);
        ArrayAdapter<String> meds new ArrayAdapter<String>();

        return medListView;
    }
}
