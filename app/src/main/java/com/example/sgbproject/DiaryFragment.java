package com.example.sgbproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DiaryFragment extends Fragment {

    TextView textview_context;
    TextView textView_title;
    Button saveBtn;

    String fileName = null;
    String str = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        String date = bundle.getString("Date");



        View v = inflater.inflate(R.layout.fragment_diary, container, false);
        TextView YearTv = v.findViewById(R.id.ShowYear);
        TextView MonthTv = v.findViewById(R.id.ShowMonth);
        TextView DayTv = v.findViewById(R.id.ShowDay);
        String[] splitData = date.split("-");
        YearTv.setText(splitData[0]);
        MonthTv.setText(splitData[1]+"월");
        DayTv.setText(splitData[2]);
        saveBtn = v.findViewById(R.id.button);
        textview_context=v.findViewById(R.id.ContextTextView);
        textView_title=v.findViewById(R.id.TitleTextView);

        try{
            FileInputStream fis = v.getContext().openFileInput("2_" + date + ".txt");
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            String Title = bufferReader.readLine();
            String context = "";
            String line;
            while((line = bufferReader.readLine()) != null) {
                context += line;
            }
            textView_title.setText(Title);
            textview_context.setText(context);
        } catch (IOException e) {

        }



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    //파일 이름 만들기
                    fileName = "2_" + splitData[0] +"-"+ splitData[1]+"-" + splitData[2] +".txt";
                    //파일생성 - 추가 갱신 저장
                    FileOutputStream fos = getActivity().openFileOutput(fileName, Context.MODE_PRIVATE);
                    str = textView_title.getText().toString() + "\n" + textview_context.getText().toString();

                    fos.write(str.getBytes(StandardCharsets.UTF_8));
                    fos.close();
                    Toast.makeText(getActivity(),"추가완료", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){

                }
            }
        });

        return v;
    }
}
