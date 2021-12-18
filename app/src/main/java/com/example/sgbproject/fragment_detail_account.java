package com.example.sgbproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class fragment_detail_account extends AppCompatActivity {

    private String fileName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_account);
        fileName = getIntent().getStringExtra("fileName"); // 인텐트의 getStringExtra를 통해 선택된 파일명을 가져와 fileName에 저장
        String[] name = fileName.split(" ");  // 공백을 기준으로 스플릿

        TextView date = findViewById(R.id.sDate);
        TextView cate = findViewById(R.id.sCate);
        TextView pay = findViewById(R.id.sPay);
        TextView memo = findViewById(R.id.sMemo);




        Button editB = findViewById(R.id.editB);

        LinearLayout dialogView;

        try {
            FileInputStream f = openFileInput((fileName));
            byte[] buffer = new byte[f.available()];
            f.read(buffer);

            String[] data = (new String(buffer)).split("\n"); // 한 줄을 기준으로 분할하여 배열에 저장

            date.setText(data[0]);
            cate.setText(data[1]);
            pay.setText(data[2]);
            memo.setText(data[3]);

            f.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        editB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater vi =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout dialog_view = (LinearLayout) vi.inflate(R.layout.dialog_view,null);

                TextView edtD = dialog_view.findViewById(R.id.edtDate);
                final EditText edtC = (EditText) dialog_view.findViewById(R.id.edtCate);
                final EditText edtP = (EditText) dialog_view.findViewById(R.id.edtPay);
                final EditText edtM = (EditText) dialog_view.findViewById(R.id.edtMemo);

                //EditText 기본 데이터 삽입(텍스트 읽기)
                try {
                    FileInputStream f = openFileInput((fileName));
                    byte[] buffer = new byte[f.available()];
                    f.read(buffer);

                    String[] data = (new String(buffer)).split("\n"); // 한 줄을 기준으로 분할하여 배열에 저장

                    edtD.setText(data[0]);
                    edtC.setText(data[1]);
                    edtP.setText(data[2]);
                    edtM.setText(data[3]);

                    f.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //다이얼로그 띄우기
                AlertDialog.Builder dlg = new AlertDialog.Builder(fragment_detail_account.this).setTitle("가계부 수정").setView(dialog_view);
                dlg.setPositiveButton("수정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        date.setText(edtD.getText().toString()); // textView 변경
                        cate.setText(edtC.getText().toString());
                        pay.setText(edtP.getText().toString());
                        memo.setText(edtM.getText().toString());

                        FileOutputStream Mfile = null;
                        //파일 내용 수정
                        try{
                            Mfile = openFileOutput(fileName, Context.MODE_PRIVATE);   // 파일을 열어서 내역 덮어쓰기
                            Mfile.write((date + "\n").getBytes(StandardCharsets.UTF_8));
                            Mfile.write((cate.getText().toString() + "\n").getBytes(StandardCharsets.UTF_8));
                            Mfile.write((pay.getText().toString() + "\n").getBytes(StandardCharsets.UTF_8));
                            Mfile.write((memo.getText().toString() + "\n").getBytes(StandardCharsets.UTF_8));
                        }catch (IOException e){

                        }

                    }
                });
                dlg.setNegativeButton("취소",null);
                dlg.show();

            }
        });

    }




    public void onClick(View v){
        Context c = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(fragment_detail_account.this);
        builder.setMessage("정말 삭제하시겠습니까?");

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {  // 예를 눌렀을 때
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    FileOutputStream file = c.openFileOutput(fileName,Context.MODE_PRIVATE);
                    c.deleteFile(fileName);  // 삭제시 해당 파일을 삭제하고 토스트 메시지 출력
                    Toast.makeText(fragment_detail_account.this, "파일이 삭제되었습니다.",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(fragment_detail_account.this, account_list.class);  // 시작 화면으로 돌아감
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 쌓인 액티비티를 모두 종료
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {  // 아니오 버튼을 눌렀다면
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {  // 아무런 작용 x
            }
        });
        builder.show(); //대화상자 화면을 출력
    }
}