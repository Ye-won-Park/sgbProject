package com.example.sgbproject;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class CalendarActivity extends AppCompatActivity {
    Toolbar toolbar;
    ScheduleFragment scheduleFragment;
    DiaryFragment diaryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calandar);

        toolbar = (Toolbar) findViewById(R.id.toolbarC);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        diaryFragment = new DiaryFragment();
        scheduleFragment = new ScheduleFragment();
        TextView textView = findViewById(R.id.titleTextC);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("다이어리"));
        tabs.addTab(tabs.newTab().setText("스케쥴러"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment selected = null;
                if (position == 0) {
                    selected = diaryFragment;
                    textView.setText("다이어리");
                } else if (position == 1) {
                    selected = scheduleFragment;
                    textView.setText("스케쥴러");
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

}
