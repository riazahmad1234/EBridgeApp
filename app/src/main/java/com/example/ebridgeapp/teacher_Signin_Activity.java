package com.example.ebridgeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class teacher_Signin_Activity extends AppCompatActivity {

    private EditText teacher_Name;
    private EditText teacher_phoneNo;
    private EditText teacher_Class;
    private EditText teacher_semester;
    private EditText teacher_email;
    private EditText teacher_password;
    private TextInputLayout teacher_InputLayout_Name;
    private TextInputLayout teacher_InputLayout_phoneNo;
    private TextInputLayout teacher_InputLayout_class;
    private TextInputLayout teacher_InputLayout_semester;
    private TextInputLayout teacher_InputLayout_email;
    private TextInputLayout teacher_InputLayout_password;
    private Button teacher_register;
    private Button teacher_chooseImage;
    private TextView teacher_goTo_login_activity;
    private RadioButton radioGenderMale;
    private RadioButton radioGenderFemale;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private String Gender;
    private Uri teacherImageFile = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__signin_);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Teacher");
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(teacher_Signin_Activity.this);

        teacher_Name = (EditText) findViewById(R.id.teacher_Name);
        teacher_phoneNo = (EditText) findViewById(R.id.teacher_phoneNo);
        teacher_Class = (EditText) findViewById(R.id.teacher_class);
        teacher_semester = (EditText) findViewById(R.id.teacher_semester);
        teacher_email = (EditText) findViewById(R.id.teacher_email);
        teacher_password = (EditText) findViewById(R.id.teacher_password);
        teacher_InputLayout_Name = (TextInputLayout)findViewById(R.id.teacher_Name_InputLayout);
        teacher_InputLayout_phoneNo = (TextInputLayout)findViewById(R.id.teacher_PhoneNo_InputLayout);
        teacher_InputLayout_class = (TextInputLayout)findViewById(R.id.teacher_class_InputLayout);
        teacher_InputLayout_semester = (TextInputLayout)findViewById(R.id.teacher_semester_InputLayout);
        teacher_InputLayout_email = (TextInputLayout)findViewById(R.id.teacher_email_InputLayout);
        teacher_InputLayout_password = (TextInputLayout)findViewById(R.id.teacher_password_InputLayout);
        teacher_goTo_login_activity = (TextView) findViewById(R.id.teacher_goTo_LogIn_activity);
        teacher_chooseImage = (Button) findViewById(R.id.teacher_imageButton);
        teacher_register = (Button) findViewById(R.id.teacher_registerButton);
        radioGenderMale = (RadioButton) findViewById(R.id.teacher_radio_male_registration_activity);
        radioGenderFemale = (RadioButton) findViewById(R.id.teacher_radio_female_registration_activity);


        teacher_goTo_login_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(teacher_Signin_Activity.this, teacher_Login_Activity.class);
                startActivity(intent);
            }
        });
        teacher_chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        teacher_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               validateName();
               validatePhoneNo();
               validateClass();
               validateSemester();
               validateEmail();
               validatePassword();
               confirmInput();
               validateChooseImage();
                createTeacherAccount();
            }
        });
    }

    private void validateChooseImage() {

            if (teacherImageFile == null) {
                Toast.makeText(getApplicationContext(), "Please Select Image", Toast.LENGTH_LONG).show();
            }
    }


    private void chooseImage() {

        Intent pickImage = new Intent();
        pickImage.setType("image/*");
        pickImage.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pickImage,02);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 02 && resultCode == RESULT_OK){
            teacherImageFile = data.getData();
        }
    }

    private boolean validateName(){
        String InputName = teacher_InputLayout_Name.getEditText().getText().toString().trim();
        if (InputName.isEmpty()){
            teacher_InputLayout_Name.setError("Field can't be empty");
            return false;
        }else {
            teacher_InputLayout_Name.setError(null);
            teacher_InputLayout_Name.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhoneNo(){
        String InputPhoneNo = teacher_InputLayout_phoneNo.getEditText().getText().toString().trim();
        if (InputPhoneNo.isEmpty()){
            teacher_InputLayout_phoneNo.setError("Field can't be empty");
            return false;
        }else {
            teacher_InputLayout_phoneNo.setError(null);
            teacher_InputLayout_phoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateClass(){
        String InputClass = teacher_InputLayout_class.getEditText().getText().toString().trim();
        if (InputClass.isEmpty()){
            teacher_InputLayout_class.setError("Field can't be empty");
            return false;
        }else {
            teacher_InputLayout_class.setError(null);
            teacher_InputLayout_class.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateSemester(){
        String InputSemester = teacher_InputLayout_semester.getEditText().getText().toString().trim();
        if (InputSemester.isEmpty()){
            teacher_InputLayout_semester.setError("Field can't be empty");
            return false;
        }else {
            teacher_InputLayout_semester.setError(null);
            teacher_InputLayout_semester.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validateEmail(){
        String InputEmail = teacher_InputLayout_email.getEditText().getText().toString().trim();
        if (InputEmail.isEmpty()){
            teacher_InputLayout_email.setError("Field can't be empty");
            return false;
        }else {
            teacher_InputLayout_email.setError(null);
            teacher_InputLayout_email.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePassword(){
        String InputPassword = teacher_InputLayout_password.getEditText().getText().toString().trim();
        if (InputPassword.isEmpty()){
            teacher_InputLayout_password.setError("Field can't be empty");
            return false;
        }else {
            teacher_InputLayout_password.setError(null);
            teacher_InputLayout_password.setErrorEnabled(false);
            return true;
        }
    }
    public void confirmInput(){
        if (!validateName() | !validatePhoneNo() | validateClass() | validateSemester() | !validateEmail() | !validatePassword()){
            return;
        }
        String input = "Name: " + teacher_InputLayout_Name.getEditText().getText().toString().trim();
        input += "\n";
        input += "Phone Number: " + teacher_InputLayout_phoneNo.getEditText().getText().toString().trim();
        input += "\n";
        input += "Class: " + teacher_InputLayout_class.getEditText().getText().toString().trim();
        input += "\n";
        input += "Semester: " + teacher_InputLayout_semester.getEditText().getText().toString().trim();
        input += "\n";
        input += "email: " + teacher_InputLayout_email.getEditText().getText().toString().trim();
        input += "\n";
        input += "password: " + teacher_InputLayout_password.getEditText().getText().toString().trim();

        Toast.makeText(teacher_Signin_Activity.this,input,Toast.LENGTH_LONG).show();

    }

    private void createTeacherAccount() {

        final String TName = teacher_Name.getText().toString().trim();
        final String TPhone = teacher_phoneNo.getText().toString().trim();
        final String TClass = teacher_Class.getText().toString().trim();
        final String TSemester = teacher_semester.getText().toString().trim();
        final String TEmail = teacher_email.getText().toString().trim();
        final String TPassword = teacher_password.getText().toString().trim();
        if (radioGenderMale.isChecked()) {
            Gender = "Male";
        }
        if (radioGenderFemale.isChecked()) {
            Gender = "Female";
        }
      /*  if (fname.isEmpty()){

            Toast.makeText(teacher_Signin_Activity.this, "Please enter FullName", Toast.LENGTH_SHORT).show();
        }
        if (uname.isEmpty()){

            Toast.makeText(teacher_Signin_Activity.this, "Please enter UserName", Toast.LENGTH_SHORT).show();
        }
        if (mail.isEmpty()){
            Toast.makeText(teacher_Signin_Activity.this, "Please enter email", Toast.LENGTH_SHORT).show();
        }else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            Toast.makeText(teacher_Signin_Activity.this,"Please enter proper email address",Toast.LENGTH_LONG).show();

        }
        if (pword.isEmpty()){
            Toast.makeText(teacher_Signin_Activity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
        }else if (pword.length()<6){
            Toast.makeText(teacher_Signin_Activity.this,"minimum password length must be 6",Toast.LENGTH_LONG).show();

        }*/
        if (!TextUtils.isEmpty(TName) && !TextUtils.isEmpty(TPhone) &&
                !TextUtils.isEmpty(TClass) && !TextUtils.isEmpty(TSemester) &&
                !TextUtils.isEmpty(TEmail) && !TextUtils.isEmpty(TPassword)) {
            progressDialog.setMessage("creating account...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(TEmail, TPassword)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            if (authResult != null) {

                                uploadTeacherImage(teacherImageFile);

                                String teacherId = firebaseAuth.getCurrentUser().getUid();
                                DatabaseReference currentDb = databaseReference.child(teacherId);
                                currentDb.child("Teacher Name").setValue(TName);
                                currentDb.child("Teacher PhoneNo").setValue(TPhone);
                                currentDb.child("Teacher Class").setValue(TClass);
                                currentDb.child("Teacher Semester").setValue(TSemester);
                                currentDb.child("Gender").setValue(Gender);
                                currentDb.child("Teacher Email").setValue(TEmail);
                                currentDb.child("Teacher Password").setValue(TPassword);



                                progressDialog.dismiss();

                                Intent intent = new Intent(teacher_Signin_Activity.this, teacher_Home_Activity.class);
                                startActivity(intent);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(teacher_Signin_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            });

        }


    }

    private void uploadTeacherImage(Uri teacherImageFile) {
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference("Teacher Images/"+firebaseAuth.getCurrentUser().getUid());
        storageReference.putFile(teacherImageFile).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()){
                    throw task.getException();
                }
                return storageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                if (task.isSuccessful()){

                    Uri DownloadUrl = task.getResult();
                    PutToDatabase(DownloadUrl);

                }else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"URL not generated",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void PutToDatabase(Uri DownloadUrl) {
        String teacherId = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference currentDb = databaseReference.child(teacherId);
        currentDb.child("Teacher Image").setValue(DownloadUrl.toString());

    }


}
