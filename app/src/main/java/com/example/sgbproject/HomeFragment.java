package com.example.sgbproject;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.sgbproject.Decorator.SaturdayDecorator;
import com.example.sgbproject.Decorator.SundayDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private MaterialCalendarView calendarView;
    CalendarDay selectedDay = null;
    boolean Selected;
    String DATE;
    TextView textView_detail;
    TextView textView_date;
    int year;
    int month;
    int day;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.homefragment, container, false);
        String[] EventDay = getContext().fileList();
        ArrayList<CalendarDay> dates = new ArrayList<>();

        for(int i = 0 ; i < EventDay.length ; i++){
            EventDay[i] = EventDay[i].substring(0,EventDay[i].length()-4);
        }

        calendarView = v.findViewById(R.id.calendarView_schedule);
        calendarView.setSelectedDate(CalendarDay.today());
        textView_detail = v.findViewById(R.id.DetailTextView);
        textView_date = v.findViewById(R.id.Date);


        String today = CalendarDay.today().toString();
        String[] parsedDATA = today.split("[{]");
        parsedDATA = parsedDATA[1].split("[}]");
        parsedDATA = parsedDATA[0].split("-");
        textView_date.setText(parsedDATA[0] + "년 " + (Integer.parseInt(parsedDATA[1])+1) + "월 " + parsedDATA[2] +"일");
        try{
            FileInputStream fis = v.getContext().openFileInput("1_"+parsedDATA[0] +"-"+ (Integer.parseInt(parsedDATA[1])+1) +"-"+ parsedDATA[0]+".txt");
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);

            textView_detail.setText(new String(buffer));
            fis.close();
        } catch (IOException e) {
            textView_detail.setText("");
        }

        calendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator()
        );




        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if(selectedDay == date){
                    selected = false;
                    Selected = selected;
                } else{
                    selected = true;
                    Selected = selected;
                }
                selectedDay = date;
                DATE = selectedDay.toString();

                String[] parsedDATA = DATE.split("[{]");
                parsedDATA = parsedDATA[1].split("[}]");
                parsedDATA = parsedDATA[0].split("-");
                year = Integer.parseInt(parsedDATA[0]);
                month = Integer.parseInt(parsedDATA[1])+1;
                day = Integer.parseInt(parsedDATA[2]);
                String Day = year + "년 " + month + "월 " + day +"일";
                textView_date.setText(Day);

                try{
                    FileInputStream fis = v.getContext().openFileInput("1_"+year +"-"+ month +"-"+ day+".txt");
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
