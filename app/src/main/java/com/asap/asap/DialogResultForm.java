package com.asap.asap;
import static com.asap.asap.TextUpload1Activity.resultFormSelectTextView;
import static com.asap.asap.TextUpload1Activity.selectedResultFormText;

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

public class DialogResultForm extends Dialog implements View.OnClickListener {

    Context context;
    Button yesButton, noButton;
    RadioGroup radioGroup;
    EditText etcPurposeEditText;
    RadioButton etcPurposeRadioButton;
    String selectedRadioButtonText;

    public DialogResultForm(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_result_form);

        radioGroup = findViewById(R.id.resultFormSelectRadioGroup);
        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);
        etcPurposeEditText = findViewById(R.id.etcPurposeEditText);
        etcPurposeRadioButton = findViewById(R.id.etcResultFormRadioButton);

        yesButton.setOnClickListener(this);
        noButton.setOnClickListener(this);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                if(radioButton != null) {
                    selectedRadioButtonText = radioButton.getText().toString();
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.yesButton) {
            selectedResultFormText = getSelectedText().trim();
            resultFormSelectTextView.setText(selectedResultFormText);
            resultFormSelectTextView.setTextColor(ContextCompat.getColor(context, R.color.inputText));
            dismiss();
        } else if (viewId == R.id.noButton) {
            selectedResultFormText= getSelectedText().trim();
            //resultFormSelectTextView.setText(selectedResultFormText);
            dismiss();
        }
    }


    public String getSelectedText() {
        if (etcPurposeRadioButton.isChecked()) {
           // 기타에 뭐 입력한 경우
            return etcPurposeEditText.getText().toString().trim();
        } else if (selectedRadioButtonText != null && !selectedRadioButtonText.isEmpty()) {
            // 나머지 3개 중 선택
            return selectedRadioButtonText;
        } else {
            //  선택 안했을 때
            return "";
        }
    }
}
