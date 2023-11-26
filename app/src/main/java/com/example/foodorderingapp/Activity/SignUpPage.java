package com.example.foodorderingapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.foodorderingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class SignUpPage extends AppCompatActivity {

    TextInputLayout Fname, Lname, email, Pass, cPass, mobileno, houseno, area, pincode, city;
    Spinner stateSpin;
    TextView signupbtn, emailbtn, phonebtn;
    CountryCodePicker ccp;
    FirebaseAuth FAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    String FirstName, LastName, Email, Password, Cpass, MobileNo, HouseNo, Area, Pincode, State, City;
    String role="Customer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        Fname= (TextInputLayout)findViewById(R.id.FirstName) ;
        Lname= (TextInputLayout)findViewById(R.id.LastName);
        email= (TextInputLayout)findViewById(R.id.Email);
        Pass= (TextInputLayout)findViewById(R.id.Password);
        cPass= (TextInputLayout)findViewById(R.id.Cpass);
        mobileno= (TextInputLayout)findViewById(R.id.MobileNo);
        houseno= (TextInputLayout)findViewById(R.id.HouseNo);
        area= (TextInputLayout)findViewById(R.id.Area);
        pincode= (TextInputLayout)findViewById(R.id.Pincode);
        city= (TextInputLayout)findViewById(R.id.City);

        stateSpin= (Spinner)findViewById(R.id.State);
        signupbtn= (TextView)findViewById(R.id.LogIn);
        emailbtn= (TextView)findViewById(R.id.SignWithEmail);
        phonebtn= (TextView)findViewById(R.id.SignWithPhone);
        ccp= (CountryCodePicker)findViewById(R.id.CountryCode);

        stateSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object value = adapterView.getItemAtPosition(i);
                State = value.toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        databaseReference = firebaseDatabase.getInstance().getReference("Customer");
        FAuth = FirebaseAuth.getInstance();
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirstName = Fname.getEditText().getText().toString().trim();
                LastName = Lname.getEditText().getText().toString().trim();
                Email = email.getEditText().getText().toString().trim();
                Password = Pass.getEditText().getText().toString().trim();
                Cpass = cPass.getEditText().getText().toString().trim();
                MobileNo = mobileno.getEditText().getText().toString().trim();
                HouseNo = houseno.getEditText().getText().toString().trim();
                Area = area.getEditText().getText().toString().trim();
                Pincode = pincode.getEditText().getText().toString().trim();
                City = city.getEditText().getText().toString().trim();

                if (isValid()) {
                    final ProgressDialog mDialog = new ProgressDialog(SignUpPage.this);
                    mDialog.setCancelable(false);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setMessage("Registration in progress, please wait....");
                    mDialog.show();

                    FAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference("User").child(userId);
                                final HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("Role", role);
                                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        HashMap<String, String> hashMap1 = new HashMap<>();
                                        hashMap1.put("Mobile_No", MobileNo);
                                        hashMap1.put("First_Name", FirstName);
                                        hashMap1.put("Last_Name", LastName);
                                        hashMap1.put("Email Id", Email);
                                        hashMap1.put("City", City);
                                        hashMap1.put("Area", Area);
                                        hashMap1.put("Pin_Code", Pincode);
                                        hashMap1.put("State", State);
                                        hashMap1.put("House_No", HouseNo);
                                        hashMap1.put("Password", Password);
                                        hashMap1.put("Confirm_Password", Cpass);

                                        firebaseDatabase.getInstance().getReference("Customer")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        mDialog.dismiss();

                                                        FAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpPage.this);
                                                                    builder.setMessage("Registration Successful !! Now verify your Email");
                                                                    builder.setCancelable(false);
                                                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                                            dialogInterface.dismiss();

                                                                            String phonenumber = ccp.getSelectedCountryCodeWithPlus() + MobileNo;
                                                                            Intent b = new Intent(SignUpPage.this, VerifyPhone.class);
                                                                            b.putExtra("phonenumber",phonenumber);
                                                                            startActivity(b);
                                                                        }
                                                                    });
                                                                    AlertDialog Alert = builder.create();
                                                                    Alert.show();
                                                                } else {
                                                                    mDialog.dismiss();
                                                                    ReusableCodeForAll.ShowAlert(SignUpPage.this, "Error", task.getException().getMessage());
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                    }
                                });

                            }
                            else {
                                mDialog.dismiss();
                                ReusableCodeForAll.ShowAlert(SignUpPage.this, "Error", task.getException().getMessage());
                            }
                        }
                    });
                }
            }
        });

        emailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpPage.this,LoginEmail.class));
                finish();
            }
        });
        phonebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpPage.this,LoginPhone.class));
                finish();
            }
        });

    }

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public boolean isValid(){
        email.setErrorEnabled(false);
        email.setError("");
        Fname.setErrorEnabled(false);
        Fname.setError("");
        Lname.setErrorEnabled(false);
        Lname.setError("");
        Pass.setErrorEnabled(false);
        Pass.setError("");
        cPass.setErrorEnabled(false);
        cPass.setError("");
        mobileno.setErrorEnabled(false);
        mobileno.setError("");
        houseno.setErrorEnabled(false);
        houseno.setError("");
        area.setErrorEnabled(false);
        area.setError("");
        pincode.setErrorEnabled(false);
        pincode.setError("");
        city.setErrorEnabled(false);
        city.setError("");

        boolean isValid = false, isValidFname = false, isValidLname = false, isValidEmail = false, isValidPassword = false,
                isValidcPass = false, isValidMobileNo = false, isValidarea = false, isValidhouseNo = false, isValidpincode = false,
                isValidcity = false;

        if(TextUtils.isEmpty(FirstName)){
            Fname.setErrorEnabled(true);
            Fname.setError("Enter First Name");
        }
        else{
            isValidFname = true;
        }

        if(TextUtils.isEmpty(LastName)){
            Lname.setErrorEnabled(true);
            Lname.setError("Enter Last Name");
        }
        else{
            isValidLname = true;
        }

        if(TextUtils.isEmpty(Email)){
            email.setErrorEnabled(true);
            email.setError("Email is required");
        }
        else{
            if(Email.matches(emailPattern)) {
                isValidEmail = true;
            }
            else{
                email.setErrorEnabled(true);
                email.setError("Enter a Valid Email id");
            }
        }

        if(TextUtils.isEmpty(Password)){
            Pass.setErrorEnabled(true);
            Pass.setError("Enter Password");
        }
        else{
            if(Password.length()<8){
                Pass.setErrorEnabled(true);
                Pass.setError("Password is weak");
            }
            else {
                isValidPassword = true;
            }
        }

        if(TextUtils.isEmpty(Cpass)){
            cPass.setErrorEnabled(true);
            cPass.setError("Enter Password Again");
        }
        else{
            if(!Password.equals(Cpass)){
                cPass.setErrorEnabled(true);
                cPass.setError("Password doesn't match");
            }
            else {
                isValidcPass = true;
            }
        }

        if(TextUtils.isEmpty(MobileNo)){
            mobileno.setErrorEnabled(true);
            mobileno.setError("Mobile Number is required");
        }
        else{
            if(MobileNo.length()<10){
                mobileno.setErrorEnabled(true);
                mobileno.setError("Invalid Mobile Number");
            }
            else{
                isValidMobileNo = true;
            }
        }

        if(TextUtils.isEmpty(HouseNo)){
            houseno.setErrorEnabled(true);
            houseno.setError("Enter House Number");
        }
        else{
            isValidhouseNo = true;
        }

        if(TextUtils.isEmpty(Area)){
            area.setErrorEnabled(true);
            area.setError("Enter Area");
        }
        else{
            isValidarea = true;
        }

        if(TextUtils.isEmpty(Pincode)){
            pincode.setErrorEnabled(true);
            pincode.setError("Enter Pincode");
        }
        else{
            isValidpincode = true;
        }

        if(TextUtils.isEmpty(City)){
            city.setErrorEnabled(true);
            city.setError("Enter City");
        }
        else{
            isValidcity = true;
        }

        isValid = (isValidFname && isValidLname && isValidEmail && isValidPassword && isValidcPass &&
                isValidMobileNo && isValidarea && isValidhouseNo && isValidpincode &&
                isValidcity) ? true : false;

        return isValid;
    }
}