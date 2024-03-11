package com.asap.asap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TextUpload1Activity extends AppCompatActivity {
    ImageButton leftButton, rightButton;
    EditText storeNameEditText;
    public static TextView purposeSelectTextView, resultFormSelectTextView, themeSelectTextView;
    public static String selectedPurposeText, selectedResultFormText, selectedThemeText;
    DialogPurpose dialogPurpose;
    DialogResultForm dialogResultForm;
    DialogTheme dialogTheme;

    // 입력 받는 것
    String storeName, theme, purpose, resultForm;
    String imageUriString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_upload1);
        
        // 이전 ImageUploadActivity에서 uri 받아오기
        imageUriString = getIntent().getStringExtra("imageUri");

        // 객체 가져오기
        leftButton = findViewById(R.id.textUpload1LeftImageButton);
        rightButton = findViewById(R.id.textUpload1RightImageButton);
        storeNameEditText = findViewById(R.id.storeNameEditText);
        purposeSelectTextView = findViewById(R.id.purposeSelectTextView);
        resultFormSelectTextView = findViewById(R.id.resultFormSelectTextView);
        themeSelectTextView = findViewById(R.id.themeSelectTextView);


        // 버튼 이동 처리
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TextUpload1Activity.this, ImageUploadActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자 입력 데이터 확인
                storeName = storeNameEditText.getText().toString().trim();
                theme = themeSelectTextView.getText().toString().trim();
                purpose = purposeSelectTextView.getText().toString().trim();
                resultForm = resultFormSelectTextView.getText().toString().trim();


                // 모든 입력 비어있지 않은지 확인
                if (!storeName.isEmpty() && !theme.isEmpty() && !purpose.isEmpty() && !resultForm.isEmpty()) {
                    // 모든 입력 채워져 있으면 
                    
                    // REST API로 보내야 함
                    
                    // 다음 액티비티로 이동
                    Intent intent = new Intent(TextUpload1Activity.this, TextUpload2Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    intent.putExtra("imageUri", imageUriString);
                    // 나머지 텍스트 항목도 보내기
                    intent.putExtra("storeName", storeName);
                    intent.putExtra("theme", theme);
                    intent.putExtra("purpose", purpose);
                    intent.putExtra("resultForm", resultForm);
                    //
                    startActivity(intent);
                } else {
                    // 빈 값이 있으면 Toast 메시지 표시
                    Toast.makeText(TextUpload1Activity.this, "빈칸 없이 채워주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*다이얼로그 띄우기*/
        purposeSelectTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogPurpose();
            }
        });
        resultFormSelectTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogResultForm();
            }
        });
        themeSelectTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogTheme();
            }
        });



    }

    public void showDialogPurpose(){
        // 원하는 디자인 및 기능 추가
        // 주의: findViewById() 사용 시 앞에 반드시 다이얼로그 이름 붙이기
        dialogPurpose = new DialogPurpose(this);
        dialogPurpose.show();

    }

    public void showDialogResultForm(){
        dialogResultForm = new DialogResultForm(this);
        dialogResultForm.show();
    }

    public void showDialogTheme(){
        dialogTheme = new DialogTheme(this);
        dialogTheme.show();
    }
}