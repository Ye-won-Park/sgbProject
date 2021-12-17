package com.example.sgbproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sgbproject.Decorator.SaturdayDecorator;
import com.example.sgbproject.Decorator.SundayDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

public class TopFragment2 extends Fragment {
    private MaterialCalendarView calendarView;
    int ranindex;
    CalendarDay selectedDay = null;
    boolean Selected;
    String DATE;
    TextView textView_detail;
    TextView textView_date;
    TextView question;
    Button button;
    int year;
    int month;
    int day;
    private ArrayList<String> Question = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.topfragment2, container, false);
        String[] EventDay = getContext().fileList();

        for(int i = 0 ; i < EventDay.length ; i++){
            EventDay[i] = EventDay[i].substring(0,EventDay[i].length()-4);
        }

        Question.add("이날 하루를 한 단어로 표현해주세요.");
        Question.add("이날 하루 먹었던 음식은 무엇인가요?");
        Question.add("이날 하루의 날씨는 어땠나요?");
        Question.add("이날 하루 가장 기억에 남는 일은 무엇인가요?");

        calendarView = v.findViewById(R.id.calendarView_schedule);
        calendarView.setSelectedDate(CalendarDay.today());
        textView_detail = v.findViewById(R.id.textView_detail2);
        textView_date = v.findViewById(R.id.date);
        question = v.findViewById(R.id.question);
        Random random = new Random();
        button = v.findViewById(R.id.button2);
        ranindex = random.nextInt(4);
        String today = CalendarDay.today().toString();
        String[] parsedDATA = today.split("[{]");
        parsedDATA = parsedDATA[1].split("[}]");
        parsedDATA = parsedDATA[0].split("-");
        textView_date.setText(parsedDATA[0] + "년 " + (Integer.parseInt(parsedDATA[1])+1) + "월 " + parsedDATA[2] +"일");
        try{
            FileInputStream fis = v.getContext().openFileInput("2_" + parsedDATA[0] + "-" + (Integer.parseInt(parsedDATA[1])+1) + "-" + parsedDATA[2] +".txt");
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            String Title = bufferReader.readLine();
            String context = "";
            String line;
            while((line = bufferReader.readLine()) != null) {
                context += line;
            }
            question.setText(Title);
            textView_detail.setText(context);
        } catch (IOException e) {
            question.setText(Question.get(ranindex));
            textView_detail.setText("");
            button.setVisibility(View.VISIBLE);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    //파일 이름 만들기
                    String fileName = "2_" + year+"-"+month+"-"+day +".txt";
                    //파일생성 - 추가 갱신 저장
                    FileOutputStream fos = getActivity().openFileOutput(fileName, Context.MODE_PRIVATE);
                    String str = question.getText().toString() + "\n" + textView_detail.getText().toString();

                    fos.write(str.getBytes(StandardCharsets.UTF_8));
                    fos.close();
                    Toast.makeText(getActivity(),"추가완료", Toast.LENGTH_SHORT).show();
                    textView_detail.setClickable(false);
                    textView_detail.setFocusable(false);
                    button.setVisibility(View.INVISIBLE);

                }
                catch (Exception e){

                }
            }
        });

        calendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator()
        );

//        for(int i = 0 ; i< EventDay.length ; i++) {
//            if(EventDay[i].contains("2_")) {
//                String[] newEventDay = EventDay[i].split("_");
//                String[] strings = newEventDay[1].split("-");
//                int y = Integer.parseInt(strings[0]);
//                int m = Integer.parseInt(strings[1]);
//                int d = Integer.parseInt(strings[2]);
//                calendarView.addDecorators(new EventDecorator(Color.GREEN, Collections.singletonList(CalendarDay.from(y, m, d))));
//            }
//        }

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
                ranindex = random.nextInt(4);
                selectedDay = date;
                DATE = selectedDay.toString();
                question.setText("");
                String[] parsedDATA = DATE.split("[{]");
                parsedDATA = parsedDATA[1].split("[}]");
                parsedDATA = parsedDATA[0].split("-");
                year = Integer.parseInt(parsedDATA[0]);
                month = Integer.parseInt(parsedDATA[1])+1;
                day = Integer.parseInt(parsedDATA[2]);
                String Day = year + "년 " + month + "월 " + day +"일";
                textView_date.setText(Day);

                try{
                    FileInputStream fis = v.getContext().openFileInput("2_" + year +"-" +month +"-" + day+ ".txt");
                    BufferedReader bufferReader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
                    String Title = bufferReader.readLine();
                    String context = "";
                    String line;
                    while((line = bufferReader.readLine()) != null) {
                        context += line;
                    }
                    question.setText(Title);
                    textView_detail.setText(context);
                    textView_detail.setClickable(false);
                    textView_detail.setFocusable(false);
                    button.setVisibility(View.INVISIBLE);
                } catch (IOException e) {
                    question.setText(Question.get(ranindex));
                    textView_detail.setText("");
                    button.setVisibility(View.VISIBLE);
                    textView_detail.setClickable(true);
                    textView_detail.setFocusable(true);
                }


            }
        }

        );

        return v;
    }
}
