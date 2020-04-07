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

public class admin_Login_Activity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private TextInputLayout InputLayout_email;
    private TextInputLayout InputLayout_password;
    private Button admin_login;
    private TextView ForgotPassword;
    private TextView register;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__login_);

        email = (EditText) findViewById(R.id.admin_email_login_activity);
        password = (EditText)findViewById(R.id.admin_password_login_activity);
        InputLayout_email = (TextInputLayout)findViewById(R.id.admin_email_InputLayout_login_activity);
        InputLayout_password = (TextInputLayout)findViewById(R.id.admin_password_InputLayout_login_activity);
        admin_login = (Button)findViewById(R.id.admin_login_login_activity);
        register = (TextView) findViewById(R.id.admin_register_login_activity);
        ForgotPassword = (TextView) findViewById(R.id.admin_forgotPassword_login_activity);

        firebaseAuth = FirebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),admin_Signup_Activity.class);
                startActivity(intent);
            }
        });
        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validatePassword();
                validateEmail();
                adminLogin();
            }
        });

    }

    private void adminLogin() {


        String admin_email =email.getText().toString().trim();
        String admin_password = password.getText().toString().trim();
        if (TextUtils.isEmpty(admin_email)){
            Toast.makeText(admin_Login_Activity.this,"Please enter Email",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(admin_password)){
            Toast.makeText(admin_Login_Activity.this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(admin_email,admin_password)
                .addOnCompleteListener(admin_Login_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(admin_Login_Activity.this,"Login Successfully",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(admin_Login_Activity.this,admin_Home_Activity.class);
                            startActivity(intent);

                        }else {
                            Toast.makeText(admin_Login_Activity.this,"Login failed! Try again",Toast.LENGTH_LONG).show();

                        }
                    }
                });



    }

    private boolean validateEmail(){
        String Inputemail = InputLayout_email.getEditText().getText().toString().trim();
        if (Inputemail.isEmpty()){
            InputLayout_email.setError("Field can't be empty");
            return false;
        }else {
            InputLayout_email.setError(null);
            InputLayout_email.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePassword(){
        String Inputpassword = InputLayout_password.getEditText().getText().toString().trim();
        if (Inputpassword.isEmpty()){
            InputLayout_password.setError("Field can't be empty");
            return false;
        }else {
            InputLayout_password.setError(null);
            InputLayout_password.setErrorEnabled(false);
            return true;
        }
    }

}
