package com.example.ebridgeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Password_Activity extends AppCompatActivity {

    Button forgotPasswordButton;
    EditText forgotEmail;
    TextInputLayout forgot_Email_InputLayout;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password_);
        forgotPasswordButton = (Button)findViewById(R.id.student_forgot_Button);
        forgotEmail = (EditText) findViewById(R.id.student_email_forgot_activity);
        forgot_Email_InputLayout = (TextInputLayout) findViewById(R.id.forgotPassword_inputLayout);

        firebaseAuth = FirebaseAuth.getInstance();
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEmail();
                resetPassword();

            }
        });
    }

    private void resetPassword() {
        String Email = forgotEmail.getText().toString().trim();
        if (Email.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter Email",Toast.LENGTH_LONG).show();
        }else {
            firebaseAuth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Password reset Email has been send",Toast.LENGTH_LONG).show();
                        finish();
                        Intent intent = new Intent(getApplicationContext(),student_Login_Activity.class);
                        startActivity(intent);

                    }else {

                        Toast.makeText(getApplicationContext(),"error in sending Password reset Email",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    private Boolean validateEmail() {
        String InputEmail = forgot_Email_InputLayout.getEditText().getText().toString().trim();
        if (InputEmail.isEmpty()){
            forgot_Email_InputLayout.setError("Field can't be empty");
            return false;
        }else {
            forgot_Email_InputLayout.setError(null);
            forgot_Email_InputLayout.setErrorEnabled(false);
            return true;
        }
    }
}
