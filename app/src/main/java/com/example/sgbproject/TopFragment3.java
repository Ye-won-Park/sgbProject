package com.example.sgbproject;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sgbproject.Decorator.EventDecorator;
import com.example.sgbproject.Decorator.SaturdayDecorator;
import com.example.sgbproject.Decorator.SundayDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

public class TopFragment3 extends Fragment {
    private MaterialCalendarView calendarView;
    CalendarDay selectedDay = null;
    boolean Selected;
    String DATE;
    TextView textView_detail;
    TextView textView_date;
    int year;
    int month;
    int day;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.homefragment, container, false);
        String[] EventDay = getContext().fileList();
        for(int i = 0 ; i < EventDay.length ; i++){
            EventDay[i].replace("3-","");
            EventDay[i].replace(".txt","");
        }

        calendarView = v.findViewById(R.id.calendarView_schedule);
        calendarView.setSelectedDate(CalendarDay.today());
        textView_detail = v.findViewById(R.id.DetailTextView);
        textView_date = v.findViewById(R.id.Date);
        // 계획이 있는 날 표시해줘야함 ( 현재는 오늘만 표시 )
        calendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator()
        );

        for(int i = 0 ; i< EventDay.length ; i ++) {
            if(EventDay[i].contains("3_")) {
                String[] strings = EventDay[i].split("-");
                int y = Integer.parseInt(strings[0]);
                int m = Integer.parseInt(strings[1]);
                int d = Integer.parseInt(strings[2]);
                calendarView.addDecorators(new EventDecorator(Color.GREEN, Collections.singletonList(CalendarDay.from(y, m, d))));
            }
        }

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if (selectedDay == date) {
                    selected = false;
                    Selected = selected;
                } else {
                    selected = true;
                    Selected = selected;
                }
                selectedDay = date;
                DATE = selectedDay.toString();
                String[] parsedDATA = DATE.split("[{]");
                parsedDATA = parsedDATA[1].split("[}]");
                parsedDATA = parsedDATA[0].split("-");
                year = Integer.parseInt(parsedDATA[0]);
                month = Integer.parseInt(parsedDATA[1]) + 1;
                day = Integer.parseInt(parsedDATA[2]);
                String Day = year + "년 " + month + "월 " + day + "일";
                textView_date.setText(Day);

                try{
                    FileInputStream fis = v.getContext().openFileInput("3-"+year + month + day+".txt");
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);

                    textView_detail.setText(new String(buffer));
                    fis.close();
                } catch (IOException e) {
                    textView_detail.setText("");
                }
            }
        }

        );

        return v;
    }
}