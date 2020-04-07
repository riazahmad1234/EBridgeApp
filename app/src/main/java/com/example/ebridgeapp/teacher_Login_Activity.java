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

public class teacher_Login_Activity extends AppCompatActivity {

    private EditText teacher_email;
    private EditText teacher_password;
    private TextInputLayout teacher_Input_email;
    private TextInputLayout teacher_Input_password;
    private Button teacher_login;
    private TextView teacher_register;
    private TextView teacher_forgotPassword;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__login);
        teacher_email =(EditText)findViewById(R.id.teacher_email_login_login_activity);
        teacher_password = (EditText)findViewById(R.id.teacher_password_login_login_activity);
        teacher_Input_email = (TextInputLayout)findViewById(R.id.teacher_email_InputLayout_login_activity);
        teacher_Input_password =(TextInputLayout)findViewById(R.id.teacher_password_InputLayout_login_activity);
        teacher_login = (Button)findViewById(R.id.teacher_login_login_activity);
        teacher_register = (TextView) findViewById(R.id.teacher_register_login_activity);
        teacher_forgotPassword = (TextView)findViewById(R.id.teacher_forgotPassword_login_activity);

        firebaseAuth =FirebaseAuth.getInstance();

        teacher_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(teacher_Login_Activity.this,teacher_Signin_Activity.class);
                startActivity(intent);
            }
        });
        teacher_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEmail();
                validatePassword();
                teacherLogin();
            }
        });
    }

    private void teacherLogin() {
        String email = teacher_email.getText().toString().trim();
        String password = teacher_password.getText().toString().trim();


        if (TextUtils.isEmpty(email)){
            Toast.makeText(teacher_Login_Activity.this,"Please enter Email",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(teacher_Login_Activity.this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(teacher_Login_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(teacher_Login_Activity.this,"Login Successfully",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(teacher_Login_Activity.this,teacher_Home_Activity.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(teacher_Login_Activity.this,"Login failed! Try again",Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    private boolean validateEmail(){
        String Inputemail = teacher_Input_email.getEditText().getText().toString().trim();
        if (Inputemail.isEmpty()){
            teacher_Input_email.setError("Field can't be empty");
            return false;
        }else {
            teacher_Input_email.setError(null);
            teacher_Input_email.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePassword(){
        String Inputpassword = teacher_Input_password.getEditText().getText().toString().trim();
        if (Inputpassword.isEmpty()){
            teacher_Input_password.setError("Field can't be empty");
            return false;
        }else {
            teacher_Input_password.setError(null);
            teacher_Input_password.setErrorEnabled(false);
            return true;
        }
    }

}
