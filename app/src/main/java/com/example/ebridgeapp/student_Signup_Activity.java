package com.example.ebridgeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class student_Signup_Activity extends AppCompatActivity {

    private EditText student_Name;
    private EditText student_phoneNo;
    private EditText student_class;
    private EditText student_semester;
    private EditText student_RollNo;
    private EditText student_email;
    private EditText student_password;
    private TextInputLayout student_InputLayout_name;
    private TextInputLayout student_InputLayout_phoneNo;
    private TextInputLayout student_InputLayout_class;
    private TextInputLayout student_InputLayout_semester;
    private TextInputLayout student_InputLayout_RollNo;
    private TextInputLayout student_InputLayout_email;
    private TextInputLayout student_InputLayout_password;
    private Button student_register;
    private Button student_chooseImage;
    private TextView goTo_student_login_Activity;
    private RadioButton radioGenderMale;
    private RadioButton radioGenderFemale;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private String Gender;
    private Uri studentImageFile = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__signup_);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Student");
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(student_Signup_Activity.this);

        student_Name = (EditText) findViewById(R.id.student_Name);
        student_phoneNo =(EditText)  findViewById(R.id.student_phoneNo);
        student_class = (EditText) findViewById(R.id.student_class);
        student_semester = (EditText) findViewById(R.id.student_semester);
        student_RollNo = (EditText) findViewById(R.id.student_RollNo);
        student_email = (EditText) findViewById(R.id.student_email_registration_activity);
        student_password = (EditText) findViewById(R.id.student_password_registration_activity);
        student_InputLayout_name = (TextInputLayout)findViewById(R.id.student_Name_InputLayout);
        student_InputLayout_phoneNo = (TextInputLayout)findViewById(R.id.student_phoneNo_InputLayout);
        student_InputLayout_class = (TextInputLayout)findViewById(R.id.student_class_InputLayout);
        student_InputLayout_semester = (TextInputLayout)findViewById(R.id.student_semester_InputLayout);
        student_InputLayout_RollNo = (TextInputLayout)findViewById(R.id.student_RollNo_InputLayout);
        student_InputLayout_email = (TextInputLayout)findViewById(R.id.student_email_InputLayout_registration_activity);
        student_InputLayout_password = (TextInputLayout)findViewById(R.id.student_password_InputLayout_registration_activity);
        goTo_student_login_Activity = (TextView) findViewById(R.id.student_goTo_LogIn_activity);
        student_register = (Button) findViewById(R.id.student_registration_Button);
        student_chooseImage = (Button) findViewById(R.id.student_ChooseImage_Button);
        radioGenderMale = (RadioButton) findViewById(R.id.student_radio_male_registration_activity);
        radioGenderFemale = (RadioButton) findViewById(R.id.student_radio_female_registration_activity);

        goTo_student_login_Activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(student_Signup_Activity.this,student_Login_Activity.class);
                startActivity(intent);
            }
        });
        student_chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        student_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  validateName();
                  validatePhoneNo();
                  validateClass();
                  validateSemester();
                  validateRollNo();
                  validateEmail();
                  validatePassword();
                  confirmInput();
                  validateChooseImage();
                   createStudentAccount();



            }
        });
    }

    private void validateChooseImage() {
        if (studentImageFile == null){
            Toast.makeText(getApplicationContext(),"Please Select Image",Toast.LENGTH_LONG).show();
        }
    }

    private void chooseImage() {

        Intent pickImage = new Intent();
        pickImage.setType("image/*");
        pickImage.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pickImage,01);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 01 && resultCode == RESULT_OK){

            studentImageFile = data.getData();
        }
    }

    private boolean validateName(){
        String InputName = student_InputLayout_name.getEditText().getText().toString().trim();
        if (InputName.isEmpty()){
            student_InputLayout_name.setError("Field can't be empty");
            return false;
        }else {
            student_InputLayout_name.setError(null);
            student_InputLayout_name.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhoneNo(){
        String InputPhoneNo = student_InputLayout_phoneNo.getEditText().getText().toString().trim();
        if (InputPhoneNo.isEmpty()){
            student_InputLayout_phoneNo.setError("Field can't be empty");
            return false;
        }else {
            student_InputLayout_phoneNo.setError(null);
            student_InputLayout_phoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateClass(){
        String InputClass = student_InputLayout_class.getEditText().getText().toString().trim();
        if (InputClass.isEmpty()){
            student_InputLayout_class.setError("Field can't be empty");
            return false;
        }else {
            student_InputLayout_class.setError(null);
            student_InputLayout_class.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateSemester(){
        String InputSemester = student_InputLayout_semester.getEditText().getText().toString().trim();
        if (InputSemester.isEmpty()){
            student_InputLayout_semester.setError("Field can't be empty");
            return false;
        }else {
            student_InputLayout_semester.setError(null);
            student_InputLayout_semester.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateRollNo(){
        String InputRollNo = student_InputLayout_RollNo.getEditText().getText().toString().trim();
        if (InputRollNo.isEmpty()){
            student_InputLayout_RollNo.setError("Field can't be empty");
            return false;
        }else {
            student_InputLayout_RollNo.setError(null);
            student_InputLayout_RollNo.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validateEmail(){
        String InputEmail = student_InputLayout_email.getEditText().getText().toString().trim();
        if (InputEmail.isEmpty()){
            student_InputLayout_email.setError("Field can't be empty");
            return false;
        }else {
            student_InputLayout_email.setError(null);
            student_InputLayout_email.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePassword(){
        String InputPassword = student_InputLayout_password.getEditText().getText().toString().trim();
        if (InputPassword.isEmpty()){
            student_InputLayout_password.setError("Field can't be empty");
            return false;
        }else {
            student_InputLayout_password.setError(null);
            student_InputLayout_password.setErrorEnabled(false);
            return true;
        }
    }
    public void confirmInput(){
        if (!validateName() | !validatePhoneNo() | validateClass() | validateSemester() | validateRollNo() | !validateEmail() | !validatePassword()){
            return;
        }
        String input = "Name: " + student_InputLayout_name.getEditText().getText().toString().trim();
        input += "\n";
        input += "Phone Number: " + student_InputLayout_phoneNo.getEditText().getText().toString().trim();
        input += "\n";
        input += "Class: " + student_InputLayout_class.getEditText().getText().toString().trim();
        input += "\n";
        input += "Semester: " + student_InputLayout_semester.getEditText().getText().toString().trim();
        input += "\n";
        input += "Roll Number: " + student_InputLayout_RollNo.getEditText().getText().toString().trim();
        input += "\n";
        input += "email: " + student_InputLayout_email.getEditText().getText().toString().trim();
        input += "\n";
        input += "password: " + student_InputLayout_password.getEditText().getText().toString().trim();

        Toast.makeText(student_Signup_Activity.this,input,Toast.LENGTH_LONG).show();

    }
    private void createStudentAccount() {

        final String SName = student_Name.getText().toString().trim();
        final String SPhone = student_phoneNo.getText().toString().trim();
        final String SClass = student_class.getText().toString().trim();
        final String SSemester = student_semester.getText().toString().trim();
        final String SRollNo = student_RollNo.getText().toString().trim();
        final String SEmail = student_email.getText().toString().trim();
        final String SPassword = student_password.getText().toString().trim();

        if (radioGenderMale.isChecked()){
            Gender = "Male";
        }
        if (radioGenderFemale.isChecked()){
            Gender = "Female";
        }
        if (!TextUtils.isEmpty(SName) && !TextUtils.isEmpty(SPhone) &&
                !TextUtils.isEmpty(SClass) && !TextUtils.isEmpty(SSemester)
                && !TextUtils.isEmpty(SRollNo) &&
                !TextUtils.isEmpty(SEmail) && !TextUtils.isEmpty(SPassword)) {
            progressDialog.setMessage("creating account...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(SEmail,SPassword)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            try {




                             if (authResult != null) {


                                 uploadTeacherImage(studentImageFile);

                                 String studentId = firebaseAuth.getCurrentUser().getUid();
                                 DatabaseReference currentDb = databaseReference.child(studentId);
                                 currentDb.child("Student Name").setValue(SName);
                                 currentDb.child("Student Phone No").setValue(SPhone);
                                 currentDb.child("Student Class").setValue(SClass);
                                 currentDb.child("Student Semester").setValue(SSemester);
                                 currentDb.child("Student Roll No").setValue(SRollNo);
                                 currentDb.child("Student Email").setValue(SEmail);
                                 currentDb.child("Student Password").setValue(SPassword);
                                 currentDb.child("Student Gender").setValue(Gender);

                                 progressDialog.dismiss();

                                 Intent intent = new Intent(student_Signup_Activity.this, student_Home_Activity.class);
                                 startActivity(intent);
                             }
                            }catch (Exception e){
                                Toast.makeText(student_Signup_Activity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(student_Signup_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            });

        }

    }

    private void uploadTeacherImage(Uri studentImageFile) {

        final StorageReference storageReference = FirebaseStorage.getInstance().getReference("Student Images/"+firebaseAuth.getCurrentUser().getUid());
        storageReference.putFile(studentImageFile).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
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
        currentDb.child("Student Image").setValue(DownloadUrl.toString());

    }


}
