package com.example.ebridgeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton_admin,radioButton_teacher,radioButton_student;
    Button goToNext_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radioGroup);
        radioButton_admin = findViewById(R.id.main_activity_admin_radio_button);
        radioButton_student = findViewById(R.id.main_activity_student_radio_button);
        radioButton_teacher = findViewById(R.id.main_activity_teacher_radio_button);
        goToNext_Button = findViewById(R.id.main_activity_Button);

        goToNext_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButton_admin.isChecked()){
                    Intent intent = new Intent(getApplicationContext(),admin_Login_Activity.class);
                    startActivity(intent);
                }else if (radioButton_student.isChecked()){
                    Intent intent = new Intent(getApplicationContext(),student_Login_Activity.class);
                    startActivity(intent);
                }else if (radioButton_teacher.isChecked()){
                    Intent intent = new Intent(getApplicationContext(),teacher_Login_Activity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Please select",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
