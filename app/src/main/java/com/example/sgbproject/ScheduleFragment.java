package com.example.sgbproject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ScheduleFragment extends Fragment {
    //사용할 객체 변수들
    TextView textview_todo;
    Button saveBtn;

    String fileName = null;
    String str = null;

    //리스트 뷰를 위한 클래스 변수 선언
    private ListView sch_ListView;
    private ArrayAdapter<String> sch_Adapter;
    private ArrayList<String> items = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        String date = bundle.getString("Date");

        View v = inflater.inflate(R.layout.fragment_schedule, container, false);
        String[] splitData = date.split("-");


        textview_todo = v.findViewById(R.id.editTodo);
        saveBtn = v.findViewById(R.id.saveBtn);
        //리스트뷰와 어댑터 초기화
        sch_ListView = v.findViewById(R.id.list);
        sch_Adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_multiple_choice, items);
        sch_ListView.setAdapter(sch_Adapter);



        try {
            FileInputStream fis = v.getContext().openFileInput("1_" + date + ".txt");
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            String temp;
            while ((temp = bufferReader.readLine()) != null) {
                Log.v(temp, "현재문구" + temp);
                sch_Adapter.add(temp);
            }
        } catch (Exception e) {

        }
        sch_Adapter.notifyDataSetChanged();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textview_todo.getText().toString() == "") {

                }
                try {
                    //파일 이름 만들기
                    fileName = "1_" + date + ".txt";
                    //파일생성 - 추가 갱신 저장
                    FileOutputStream fos = getActivity().openFileOutput(fileName, Context.MODE_APPEND);
                    str = textview_todo.getText().toString() + "\n";
                    if (textview_todo.getText().toString().length() < 1) {
                        Toast.makeText(getActivity(), "할 일을 입력하세요", Toast.LENGTH_SHORT).show();
                    } else {
                        items.add(str);
                        textview_todo.setText("");
                        fos.write(str.getBytes(StandardCharsets.UTF_8));
                        fos.close();
                        Toast.makeText(getActivity(), "추가완료", Toast.LENGTH_SHORT).show();
                        sch_Adapter.notifyDataSetChanged();
                    }

                } catch (Exception e) {

                }
            }


        });

        sch_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                items.remove(position);
                sch_ListView.clearChoices();
                sch_Adapter.notifyDataSetChanged();
                String path = getActivity().getFilesDir().getAbsolutePath();
                File file = new File(path+"/"+"1_"+date+".txt");
                try{
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                    String dummy = "";
                    String line;
                    if(items.size() == 1){
                    }
                    else if(position == 0){
                        String delData = br.readLine();

                        while (((line = br.readLine()) != null)) {
                            dummy += line;
                        }
                    }else {
                        for (int a = 0; a < position; a++) {
                            line = br.readLine();
                            dummy += line;
                        }
                        String delData = br.readLine();

                        while (((line = br.readLine()) != null)) {
                            dummy += line;
                        }
                    }

                    FileWriter fw = new FileWriter("1_"+date+".txt");
                    fw.write(dummy);
                    //bw.close();
                    fw.close();
                    br.close();
                }
                catch (IOException e) {

                }
            }
        });


        return v;
    }
}