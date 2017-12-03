package com.example.natha.pilltime;

import android.app.AlertDialog;
import android.app.Fragment;
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
            if(p.getActive() == 1) {
                Iterator it = p.getTimeTaken().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    if (pair.getValue() == "1") {
                        int i = (int) pair.getKey();
                        String time = formatTime(i);
                        String out = "Name:" +  p.getName() + '\n'
                                + "Dosage:" + p.getDosage() + "\n"
                                + "time:" + time;
                        takenPills.add(out);
                    } else {
                        int i = (int) pair.getKey();
                        String time = formatTime(i);
                        String out = "Name:" +  p.getName() + '\n'
                                + "Dosage:" + p.getDosage() + "\n"
                                + "time:" + time;

                        notTakenPills.add(out);
                    }
                    it.remove();
                }
            }
        }
        final ArrayAdapter<String> taken = new ArrayAdapter<String>(getActivity(), R.layout.pill_list_item, R.id.pillItemTV, takenPills);
        final ArrayAdapter<String> notTaken = new ArrayAdapter<String>(getActivity(), R.layout.pill_list_item, R.id.pillItemTV, notTakenPills);

        View todayView = inflater.inflate(R.layout.today_fragment, container, false);
        ListView medicinesTaken = (ListView) todayView.findViewById(R.id.takenLV);
        ListView medicinesNotTaken = (ListView) todayView.findViewById(R.id.notTakenLV);

        medicinesTaken.setAdapter(taken);
        medicinesNotTaken.setAdapter(notTaken);

        medicinesTaken.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                startAlert(taken.getItem(position));
            }
        });

        medicinesNotTaken.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startAlert(notTaken.getItem(position));
            }
        });
        return todayView;
    }

    public void startAlert(String data){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        String dataArray[] = data.split("\n");
        alertDialog.setTitle(dataArray[0]);
    }
    public String formatTime(int i){
        String times = "";
        if (i%100 < 10) {
            times += (i - i % 100) / 100 + ":" + "0" + (i%100) + "\n";
        }
        else{
            times += (i - i % 100) / 100 + ":" + (i%100) +"\n";}
        return times;
    }

}
