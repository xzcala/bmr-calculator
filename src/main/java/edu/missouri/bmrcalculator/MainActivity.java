package edu.missouri.bmrcalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView title_textView;
    private EditText feet_editText;
    private EditText inch_editText;
    private EditText weight_editText;
    private EditText age_editText;
    private TextView bmr_textView;
    private RadioGroup gender_radioGroup;
    private RadioButton male_radioButton;
    private RadioButton female_radioButton;
    private CheckBox activity_checkBox;
    private Button clear_Button;
    private Button calculate_Button;
    private int padding;
    private double factor;
    private double bmr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title_textView=findViewById(R.id.title_textView);
        feet_editText=findViewById(R.id.feet_editText);
        inch_editText=findViewById(R.id.inch_editText);
        weight_editText=findViewById(R.id.weight_editText);
        age_editText=findViewById(R.id.age_editText);
        bmr_textView=findViewById(R.id.bmr_textView);
        gender_radioGroup=findViewById(R.id.gender_radioGroup);
        male_radioButton=findViewById(R.id.male_radioButton);
        female_radioButton=findViewById(R.id.female_radioButton);
        activity_checkBox=findViewById(R.id.activity_checkBox);
        clear_Button=findViewById(R.id.clear_button);
        calculate_Button=findViewById(R.id.calculate_button);

        gender_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.male_radioButton:
                        padding = 5;
                        break;
                    case R.id.female_radioButton:
                        padding = -161;
                        break;
                }
            }
        });

        clear_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmr_textView.setText(null);
                feet_editText.setText(null);
                inch_editText.setText(null);
                weight_editText.setText(null);
                age_editText.setText(null);
                gender_radioGroup.clearCheck();
                activity_checkBox.setChecked(false);
            }
        });

        calculate_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weight, heightFeet, heightInches, age;
                if (feet_editText.getText().toString().isEmpty()) {
                    showAlertDialog(String.format("%s field is missing", feet_editText.getHint().toString()));
                    return;
                }
                if (weight_editText.getText().toString().isEmpty()){
                    showAlertDialog(String.format("%s field is missing", weight_editText.getHint().toString()));
                    return;
                }
                if (age_editText.getText().toString().isEmpty()){
                    showAlertDialog(String.format("%s field is missing", age_editText.getHint().toString()));
                    return;
                }
                if (!male_radioButton.isChecked() && !female_radioButton.isChecked()){
                    showAlertDialog(String.format("%s field is missing", male_radioButton.getHint().toString()));
                    return;
                } else {
                    if (activity_checkBox.isChecked()) {
                        factor=1.2;
                    } else {
                        factor=1.0;
                    }
                    weight = weight_editText.getText().toString();
                    heightFeet = feet_editText.getText().toString();
                    heightInches = inch_editText.getText().toString();
                    age = age_editText.getText().toString();
                    if (inch_editText.getText().toString().isEmpty()) {
                        bmr = ((4.536 * Double.parseDouble(weight)) + (15.88 * (12 * Double.parseDouble(heightFeet))) - (5 * Double.parseDouble(age)) + padding) * factor;
                    } else {
                        bmr = ((4.536 * Double.parseDouble(weight)) + (15.88 * ((12 * Double.parseDouble(heightFeet)) + Double.parseDouble(heightInches))) - (5 * Double.parseDouble(age)) + padding) * factor;
                    }
                    bmr_textView.setText(String.valueOf((int)bmr)+ " Calories");
                }
            }
        });
    }

    //code from example 3
    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
