package com.example.ebridgeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class admin_Signup_Activity extends AppCompatActivity {
    private EditText fullName;
    private EditText userName;
    private EditText email;
    private EditText password;
    private Button admin_register;
    private Button admin_login;
    private TextInputLayout InputLayout_fullname;
    private TextInputLayout InputLayout_username;
    private TextInputLayout InputLayout_email;
    private TextInputLayout InputLayout_password;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__signup_);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Admin");
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(admin_Signup_Activity.this);

        fullName = (EditText)findViewById(R.id.admin_fullname_SignUp_activity);
        userName = (EditText)findViewById(R.id.admin_username_SignUp_activity);
        email = (EditText)findViewById(R.id.admin_email_SignUp_activity);
        password = (EditText)findViewById(R.id.admin_password_SignUp_activity);
        admin_register = (Button)findViewById(R.id.admin_register_SignUp_activity);
        admin_login =(Button)findViewById(R.id.admin_Login_SignUp_activity);
        InputLayout_fullname = (TextInputLayout)findViewById(R.id.admin_fullname_InputLayout_SignUp_activity);
        InputLayout_username = (TextInputLayout)findViewById(R.id.admin_username_InputLayout_SignUp_activity);
        InputLayout_email = (TextInputLayout)findViewById(R.id.admin_email_InputLayout_SignUp_activity);
        InputLayout_password = (TextInputLayout)findViewById(R.id.admin_password_InputLayout_SignUp_activity);

        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(admin_Signup_Activity.this,admin_Login_Activity.class);
                startActivity(intent1);
            }
        });
        admin_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               validateFullname();
               validateUsername();
               validateEmail();
               validatePassword();
                confirmInput();

                createNewAccount();
            }
        });

    }
    private boolean validateFullname(){
        String Inputfullname = InputLayout_fullname.getEditText().getText().toString().trim();
        if (Inputfullname.isEmpty()){
            InputLayout_fullname.setError("Field can't be empty");
            return false;
        }else {
            InputLayout_fullname.setError(null);
            InputLayout_fullname.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateUsername(){
        String Inputusername = InputLayout_username.getEditText().getText().toString().trim();
        if (Inputusername.isEmpty()){
            InputLayout_username.setError("Field can't be empty");
            return false;
        }else {
            InputLayout_username.setError(null);
            InputLayout_username.setErrorEnabled(false);
            return true;
        }
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
    public void confirmInput(){
        if (!validateFullname() | !validateUsername() | !validateEmail() | !validatePassword()){
            return;
        }
        String input = "FullName: " + InputLayout_fullname.getEditText().getText().toString().trim();
        input += "\n";
        input += "username: " + InputLayout_username.getEditText().getText().toString().trim();
        input += "\n";
        input += "email: " + InputLayout_email.getEditText().getText().toString().trim();
        input += "\n";
        input += "password: " + InputLayout_password.getEditText().getText().toString().trim();

        Toast.makeText(admin_Signup_Activity.this,input,Toast.LENGTH_LONG).show();

    }


    private void createNewAccount() {


       final String fName = fullName.getText().toString().trim();
       final String uName = userName.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String pword = password.getText().toString().trim();
        if (!TextUtils.isEmpty(fName) && !TextUtils.isEmpty(uName) &&
                !TextUtils.isEmpty(mail) && !TextUtils.isEmpty(pword)){
            progressDialog.setMessage("creating account...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(mail, pword)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            if (authResult != null){


                                String adminid = firebaseAuth.getCurrentUser().getUid();
                                DatabaseReference currentDb = databaseReference.child(adminid);
                                currentDb.child("FullName").setValue(fName);
                                currentDb.child("UserName").setValue(uName);

                                progressDialog.dismiss();

                                Intent intent = new Intent(admin_Signup_Activity.this,admin_Home_Activity.class);
                                startActivity(intent);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(admin_Signup_Activity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            });

        }
    }

}
