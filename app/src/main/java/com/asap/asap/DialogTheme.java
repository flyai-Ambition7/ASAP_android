package com.asap.asap;

import static com.asap.asap.TextUpload1Activity.themeSelectTextView;
import static com.asap.asap.TextUpload1Activity.selectedThemeText;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.core.content.ContextCompat;

public class DialogTheme extends Dialog implements View.OnClickListener {

    Context context;
    Button yesButton, noButton;
    RadioGroup radioGroup;
    RadioButton etcThemeRadioButton;
    EditText etcThemeEditText;
    String selectedRadioButtonText;

    public DialogTheme(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_theme);

        radioGroup = findViewById(R.id.themeSelectRadioGroup);
        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);
        etcThemeRadioButton = findViewById(R.id.etcThemeRadioButton);
        etcThemeEditText = findViewById(R.id.etcThemeEditText);

        yesButton.setOnClickListener(this);
        noButton.setOnClickListener(this);

        // 체크표시 바뀌면 거기 있는 값 가져옴

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                if (radioButton != null) {
                    selectedRadioButtonText = radioButton.getText().toString();
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.yesButton) {
            selectedThemeText  = getSelectedText().trim();
            themeSelectTextView.setText(selectedThemeText);
            themeSelectTextView.setTextColor(ContextCompat.getColor(context, R.color.inputText));
            dismiss();
        } else if (viewId == R.id.noButton) {
            selectedThemeText  = getSelectedText().trim();
            //themeSelectTextView.setText(selectedThemeText);
            dismiss();
        }
    }

    // Method to retrieve the selected text from radio button or entered text from EditText
    public String getSelectedText() {
        if (etcThemeRadioButton.isChecked()) {
            // "기타(직접 입력) 라디오 버튼 체크일 때

            return etcThemeEditText.getText().toString().trim();
        } else if (selectedRadioButtonText != null && !selectedRadioButtonText.isEmpty()) {
            // 다른 체크일 때 해당 라디오 버튼 이름 가져옴
            return selectedRadioButtonText;
        } else {
            // 아무런 체크 없을 때 빈칸
            return "";
        }
    }
}