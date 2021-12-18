package com.example.sgbproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class account_list extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    List<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_list);


        File location = new File("/data/data/com.example.sgbproject/files");   // 날짜와 구매 내역이 저장된 파일이 속한 경로
        String[] filename = location.list();     // 해당 디렉토리의 파일목록을 String배열로 반환한다.
        for (int i = 0; i < filename.length; i++) {
            String[] title = filename[i].split(".txt");
            title = title[0].split("_");
            if (title[0].equals("3")) {
                title = title[1].split(("-"));
                items.add(title[0] + "년 " + title[1] + "월 " + title[2] + "일"); // items에 년-월-일 / 제목 의 형태로 파일명이 저장
            }

            Collections.sort(items); // 날짜으로 리스트 아이템을 정렬. 만약 동일한 날짜면 제목 순으로 정렬
            adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, items);
            listView = findViewById(R.id.listView);
            listView.setAdapter(adapter);



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  // 리스트뷰의 아이템을 선택했을 때의 이벤트리스너
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Toast.makeText(getApplicationContext(), adapter.getItem(position), Toast.LENGTH_SHORT).show();  // 파일 정보를 토스트메시지로 출력

                    Intent intent = new Intent(view.getContext(), fragment_detail_account.class);  //인텐트를 통해 구매 상세 정보 액티비티 실행
                    intent.putExtra("fileName", filename[position]);
                    startActivity(intent);
                }
            });


        }
    }
}
