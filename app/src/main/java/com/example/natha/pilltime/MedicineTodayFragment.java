package com.example.natha.pilltime;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;


public class MedicineTodayFragment extends Fragment {
    public MedicineTodayFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstance) {
        final dbHelper db = new dbHelper(getActivity().getApplicationContext());
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
<<<<<<< HEAD

                    if (pair.getValue().equals("1")) {
=======
                    if (pair.getValue().equals(1)) {
>>>>>>> 57a6515b4ab40c989d43ce44ecbf3421b57ceb65
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
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                startAlert(taken.getItem(position));
                final String pillS = taken.getItem(position);
                final String[] extraPillInfo = pillS.split("\n");
                final String name = extraPillInfo[0].substring(5, extraPillInfo[0].length());
                String message = "Notes: " + db.getPillNotes(name);
                new AlertDialog.Builder(getActivity())
                        .setTitle("Did you take your medication?")
                        .setMessage(name)
                        .setPositiveButton("Untake Pill", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String time = extraPillInfo[2].substring(5, extraPillInfo[2].length());
                                int timeI = unFormatTime(time); //integer to hold the time unformatted
                                Pill p = db.getPillByName(name);
                                p.setTimeTake(timeI, 0);
                                p.setPillCount(p.getPillCount() + 1);

                                db.updateTaken(p, timeI, 0);
                                db.updatePill(p);

                                taken.remove(pillS);
                                notTaken.add(pillS);

                                sortMedication(notTaken);
                                sortMedication(taken);

                                notTaken.notifyDataSetChanged();
                                taken.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .setNeutralButton("Close", null).show();
            }
        });
        medicinesNotTaken.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                startAlert(notTaken.getItem(position));
                final String pillS = notTaken.getItem(position);
                final String[] extraPillInfo = pillS.split("\n");
                final String name = extraPillInfo[0].substring(5, extraPillInfo[0].length());
                String message = name + "\n" + "Notes: " + db.getPillNotes(name);
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Did you take your medication?")
                            .setMessage(message)
                            .setPositiveButton("Take Pill", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String pillS = notTaken.getItem(position);
                                    String time = extraPillInfo[2].substring(5, extraPillInfo[2].length());
                                    int timeI = unFormatTime(time); //integer to hold the time unformatted
                                    Pill p = db.getPillByName(name  );
                                    p.setTimeTake(timeI, 1);
                                    p.setPillCount(p.getPillCount() - 1);
                                    db.updatePill(p);
                                    db.updateTaken(p, timeI, 1);
                                    db.testTimes();
                                    notTaken.remove(pillS);
                                    taken.add(pillS);

                                    sortMedication(taken);
                                    sortMedication(notTaken);

                                    notTaken.notifyDataSetChanged();
                                    taken.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            })
                            .setNeutralButton("Close", null).show();
            }
        });
        db.close();
        return todayView;
    }

    public void startAlert(String data){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        String dataArray[] = data.split("\n");
        alertDialog.setTitle(dataArray[0]);
    }
    public String formatTime(int i){
        String times = "";
        if(i < 60) {
            if(i < 10) {
                times += ("0:0"+i + "\n");
            }
            else{
                times += ("0:" + i) + "\n";
            }
        }
        else if (i%100 < 10) {
            times += (i - i % 100) / 100 + ":" + "0" + (i%100) + "\n";
        }
        else{
            times += (i - i % 100) / 100 + ":" + (i%100) +"\n";}
        return times;
    }
    public int unFormatTime(String i){
        String[] rawString = i.split(":");
        int hour = Integer.parseInt(rawString[0]) * 100;
        int minute = Integer.parseInt(rawString[1]);
        int rawTime = hour + minute;
        return rawTime;
    }

    public void sortMedication(ArrayAdapter<String> arrayAdapter){
        arrayAdapter.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String []arrayO1 = o1.split("\n");
                String []arrayO2 = o2.split("\n");

                Integer rawO1Time = unFormatTime(arrayO1[2].substring(5));
                Integer rawO2Time = unFormatTime(arrayO2[2].substring(5));

                if (rawO1Time < rawO2Time) {
                    return -1;
                }
                else{
                    return 1;
                }

            }
        });
    }
}
