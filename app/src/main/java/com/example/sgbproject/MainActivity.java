package com.example.sgbproject;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    TopFragment2 fragment2;
    TopFragment3 fragment3;
    HomeFragment homeFragment;
    CalendarFragment calendarFragment;
    AccountFragment accountFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        toolbar = (Toolbar) findViewById(R.id.toolbarC);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        fragment2 = new TopFragment2();
        fragment3 = new TopFragment3();
        homeFragment = new HomeFragment();
        calendarFragment = new CalendarFragment();
        accountFragment = new AccountFragment();
        TextView textView = findViewById(R.id.titleTextC);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("스케쥴러"));
        tabs.addTab(tabs.newTab().setText("다이어리"));
        tabs.addTab(tabs.newTab().setText("가계부"));
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment selected = null;
                if (position == 0) {
                    selected = homeFragment;
                    textView.setText("Schedule");
                } else if (position == 1) {
                    selected = fragment2;
                    textView.setText("Diary");
                } else if (position == 2) {
                    selected = fragment3;
                    textView.setText("Account");
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


        BottomNavigationView buttomNavigationView = findViewById(R.id.bottom_navigation);
        buttomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.tab1:
                                if(tabs.getTabCount() == 0) {
                                    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                                    tabs.addTab(tabs.newTab().setText("스케쥴러"));
                                    tabs.addTab(tabs.newTab().setText("다이어리"));
                                    tabs.addTab(tabs.newTab().setText("가계부"));
                                    tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                        @Override
                                        public void onTabSelected(TabLayout.Tab tab) {
                                            int position = tab.getPosition();

                                            Fragment selected = null;
                                            if (position == 0) {
                                                selected = homeFragment;
                                                textView.setText("Schedule");
                                            } else if (position == 1) {
                                                selected = fragment2;
                                                textView.setText("Diary");
                                            } else if (position == 2) {
                                                selected = fragment3;
                                                textView.setText("Account");
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
                                return true;
                            case R.id.tab2:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, calendarFragment).commit();
                                textView.setText("Calandar");
                                tabs.removeAllTabs();
                                return true;
                            case R.id.tab3:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, accountFragment).commit();
                                textView.setText("Account");
                                tabs.removeAllTabs();
                                return true;
                        }

                        return false;
                    }
                }
        );


    }

}