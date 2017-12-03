package com.example.natha.pilltime;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
        final dbHelper db = new dbHelper(getActivity().getApplicationContext());
        View medListView = inflater.inflate(R.layout.medlist_fragment, container, false);
        ListView medicines = (ListView) medListView.findViewById(R.id.medicationLV);
        Vector<Pill> allPills = db.getAllPills();

        final Vector<String> allMeds = new Vector<>();

        for(Pill p : allPills) {
            Vector<Integer> allTimes = db.getAllTimes(p);
            String times = "";
            for(Integer i : allTimes){ // i = time
                if (i%100 < 10) {
                    times += (i - i % 100) / 100 + ":" + "0" + (i%100) + "\n";
                }
                else{
                    times += (i - i % 100) / 100 + ":" + (i%100) +"\n";
                }
              //  times += i + "\n";
            }
            allMeds.add(p.getName() + '\n' +
                    p.getDosage() + '\n' +
                    p.getActive() + '\n' +
                    times +
                    p.getNotes());
        }

        final ArrayAdapter<String> meds = new ArrayAdapter<>(getActivity(), R.layout.pill_list_item, R.id.pillItemTV, allMeds);
        medicines.setAdapter(meds);

        final Intent editIntent = new Intent(getActivity(), EditMedicationActivity.class);

        medicines.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id){
                new AlertDialog.Builder(getActivity())
                        .setTitle("Edit/Delete record")
                        .setMessage("Press edit to edit pill, Delete to delete this pill")
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                editIntent.putExtra("pill", meds.getItem(position));
                                startActivity(editIntent);
                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String pill = meds.getItem(position);
                                String[] extraPillInfo = pill.split("\n");
                                Pill p = db.getPillByName(extraPillInfo[0]);
                                db.removePill(p);
                                allMeds.remove(pill);
                                meds.notifyDataSetChanged();
                            }
                        })
                        .setNeutralButton("Close", null).show();

            }
        });

        return medListView;
    }
}
