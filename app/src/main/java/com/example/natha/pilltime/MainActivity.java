package com.example.natha.pilltime;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private Fragment todayFragment;
    private Fragment medListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null){
            getFragmentManager().beginTransaction().add(R.id.frame, new MedicineTodayFragment()).commit();
        }

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);

        ActionBar.Tab todayTab = actionBar.newTab().setText("Today");
        ActionBar.Tab medListTab = actionBar.newTab().setText("Med List");

        todayFragment = new MedicineTodayFragment();
        medListFragment = new MedicineListFragment();

        todayTab.setTabListener(new MainTabListener(todayFragment, getApplicationContext()));
        medListTab.setTabListener(new MainTabListener(medListFragment, getApplicationContext()));

        actionBar.addTab(todayTab);
        actionBar.addTab(medListTab);
    }

    public void addNewMedication(View view){
        //pass intent to new page edit_medication_activity
        Intent editMedication = new Intent(getApplicationContext(), EditMedicationActivity.class);
        startActivity(editMedication);
    }

    class MainTabListener implements ActionBar.TabListener{
        public Fragment fragment;

        public MainTabListener(Fragment f, Context c) { fragment = f; }
        @Override public void onTabReselected(ActionBar.Tab tab, FragmentTransaction t) {}
        @Override public void onTabSelected(ActionBar.Tab tab, FragmentTransaction t){
            t.replace(R.id.frame, fragment);
        }
        @Override public  void onTabUnselected(ActionBar.Tab tab, FragmentTransaction t){
            t.remove(fragment);
        }
    }
}
