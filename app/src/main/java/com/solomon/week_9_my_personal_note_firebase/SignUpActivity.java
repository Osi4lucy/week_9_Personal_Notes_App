package com.solomon.week_9_my_personal_note_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.ArrayList;

import static android.widget.Toast.*;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editEmail, editPassword;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editEmail = findViewById(R.id.editTextEmail);
        editPassword = findViewById(R.id.editTextPassword);
        progressBar = findViewById(R.id.progressbar);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signUpButton).setOnClickListener(this);
        findViewById(R.id.loginTextView).setOnClickListener(this);


    }

    //Method to register a new user

    private void registerUser(){
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if(email.isEmpty()){
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Please enter a valid email");
            editEmail.requestFocus();
            return;
        }

        if(password.length()<6){
            editPassword.setError("Password must be a minimum of 6 characters");
            editPassword.requestFocus();
            return;
        }
        //show visibility of progress bar
       progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()) {
                    makeText(SignUpActivity.this, "User registration successful", LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered, logon!", LENGTH_SHORT).show();
                    } else {
                        makeText(getApplicationContext(), task.getException().getMessage(), LENGTH_SHORT).show();
                    }

                }
            }
        });

        if(password.isEmpty()){
            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpButton:
                registerUser();
                break;
            case R.id.loginTextView:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
