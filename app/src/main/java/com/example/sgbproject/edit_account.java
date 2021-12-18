package com.example.sgbproject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

public class edit_account extends AppCompatActivity {

    private EditText editCategory;  // 소비의 종류를 저장할 문자형 변수
    private EditText editPay;
    private EditText editMemo;

    private TextView consumeDate;   //소비 날짜를 저장할 텍스트뷰 변수 선언
    private DatePickerDialog.OnDateSetListener MdateSet;  //날짜 설정 시의 이벤트를 위한 OnDateSetListener 변수 선언
    Calendar Today = Calendar.getInstance();  // 선택하는 날짜를 인수로 가져오기 위해 Calendar 객체에 getInstance 적용

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_account);

        this.InitializeView();    // 두 함수를 호출 (함수 기능 설명은 아래)
        this.InitializeListener();
    }

    public void InitializeView() {    // 사용자가 지정한 날짜 정보를 텍스트뷰에 표시하기 위해 findViewById로 텍스트뷰 참조 객체를 얻어옴
        consumeDate = (TextView) findViewById(R.id.consumeDate);
    }

    public void InitializeListener() {     // OnDateSetListener를 구현하는 함수
        // onDateSet() 함수를 오버라이딩하며 다이얼로그 창의 완료 버튼을 클릭하였을 때 실행되는 CallBack 함수
        // 매개변수로 사용자가 입력한 날짜 정보를 받음
        MdateSet = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                consumeDate.setText(year + "년" + (month + 1) + "월" + dayOfMonth + "일");
            }
        };
    }

    public void OnClickHandler(View v) {   //버튼 이벤트 발생에 대해 처리를 담당하는 함수
        DatePickerDialog dialog = new DatePickerDialog(this, MdateSet,   // 버튼 클릭시 DatePickerDialog 객체를 생성하며 인자를 받는다
                Today.get(Calendar.YEAR), Today.get(Calendar.MONTH),
                Today.get(Calendar.DAY_OF_MONTH));

        dialog.show();   // 선택하는 날의 날짜를 불러오고 달력을 DatePicker를 화면에 보여줌
    }

    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.save) {
            String date = ((TextView) findViewById(R.id.consumeDate)).getText().toString();
            String pay = ((EditText) findViewById(R.id.editPay)).getText().toString();  // 입력한 소비 금액
            String M = ((EditText) findViewById(R.id.editMemo)).getText().toString();
            String M2 = ((EditText) findViewById(R.id.editCategory)).getText().toString();


            AlertDialog.Builder builder = new AlertDialog.Builder(edit_account.this);   // AlertDialog를 화면에 보여주기 위한 객체 생성
            builder.setTitle("소비내역 저장");
            builder.setMessage("날짜 : " + date + "\n소비 종류 : " + M2 +
                    "\n지출금액 : " + pay + " 원\n메모 : " + M + " \n저장 하겠습니까?");

            String fileName = "3_" + date + ".txt";   //생성되는 파일의 이름을 저장하는 fileName (파일 형식은 3_년도-월-일.txt)

            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {    // 만약 예 버튼을 눌렀을 때,
                    FileOutputStream Mfile = null;
                    try {
                        Mfile = openFileOutput(fileName, Context.MODE_PRIVATE);   // 파일을 열어서 내역 저장
                        Mfile.write((date + "\n").getBytes(StandardCharsets.UTF_8));
                        Mfile.write((M2 + "\n").getBytes(StandardCharsets.UTF_8));
                        Mfile.write((pay + "\n").getBytes(StandardCharsets.UTF_8));
                        Mfile.write((M + "\n").getBytes(StandardCharsets.UTF_8));
                        Mfile.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(edit_account.this, "저장 완료.", Toast.LENGTH_SHORT).show();  // 저장이 완료되었음을 Toast 메시지로 출력
                    Intent intent = new Intent(edit_account.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);  // 중복 및 오류를 방지하기 위한 flag 설정
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent); // 저장이 완료되면 영화 목록(메인 액티비티) 액티비티가 실행되며 돌아감

                }
            });
            builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {   //만약 아니오 버튼을 눌렀다면 취소되었음을 Toast 메시지로 출력
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(edit_account.this, "취소되었습니다.", Toast.LENGTH_SHORT).show();

                }
            });
            builder.show();

        }
    }
}
