package com.example.ebridgeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class student_Login_Activity extends AppCompatActivity {

    private EditText student_email;
    private EditText student_password;
    private TextInputLayout student_input_email;
    private TextInputLayout student_input_password;
    private Button student_login;
    private TextView student_forgotPassword;
    private TextView student_register;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__login_);

        student_email = (EditText)findViewById(R.id.student_email_login_activity);
        student_password =(EditText)findViewById(R.id.student_password_login_activity);
        student_input_email = (TextInputLayout)findViewById(R.id.student_email_InputLayout_login_activity);
        student_input_password = (TextInputLayout)findViewById(R.id.student_password_InputLayout_login_activity);
        student_login = (Button)findViewById(R.id.student_login_login_activity);
        student_register =(TextView) findViewById(R.id.student_register_login_activity);
        student_forgotPassword = (TextView) findViewById(R.id.student_forgotPassword_login_activity);


        firebaseAuth = FirebaseAuth.getInstance();
        student_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                validateEmail();
                validatePassword();
                studentLogin();

            }
        });
        student_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),student_Signup_Activity.class);
                startActivity(intent);
            }
        });
        student_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Forgot_Password_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void studentLogin() {

        String SEmail = student_email.getText().toString().trim();
        String SPassword = student_password.getText().toString().trim();
        if (TextUtils.isEmpty(SEmail)){
            Toast.makeText(student_Login_Activity.this,"Please enter Email",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(SPassword)){
            Toast.makeText(student_Login_Activity.this,"Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(SEmail,SPassword)
                .addOnCompleteListener(student_Login_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            Toast.makeText(student_Login_Activity.this,"Login Successfully",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(student_Login_Activity.this,student_Home_Activity.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(student_Login_Activity.this,"Login failed! Try again",Toast.LENGTH_LONG).show();

                        }
                    }
                });


    }

    private boolean validateEmail(){
        String Inputemail = student_input_email.getEditText().getText().toString().trim();
        if (Inputemail.isEmpty()){
            student_input_email.setError("Field can't be empty");
            return false;
        }else {
            student_input_email.setError(null);
            student_input_email.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePassword(){
        String Inputpassword = student_input_password.getEditText().getText().toString().trim();
        if (Inputpassword.isEmpty()){
            student_input_password.setError("Field can't be empty");
            return false;
        }else {
            student_input_password.setError(null);
            student_input_password.setErrorEnabled(false);
            return true;
        }
    }

}
