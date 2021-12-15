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

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public class ScheduleFragment extends Fragment {
    //사용할 객체 변수들
    TextView textview_todo;
    Button saveBtn;

    String fileName = null;
    String str = null;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_schedule,container,false);

        textview_todo = v.findViewById(R.id.editTodo);
        saveBtn = v.findViewById(R.id.saveBtn);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    //파일 이름 만들기
                    fileName = "1_";
                    //파일생성 - 추가 갱신 저장
                    FileOutputStream fos = getActivity().openFileOutput(fileName, Context.MODE_APPEND);
                    str = textview_todo.getText().toString();
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
