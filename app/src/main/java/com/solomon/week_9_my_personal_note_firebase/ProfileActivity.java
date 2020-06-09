package com.solomon.week_9_my_personal_note_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    Button submitBtn, retBtn;
    DatabaseReference rootRef, notesRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editText = findViewById(R.id.inputEditText);
        submitBtn = findViewById(R.id.submitBtn);
        textView = findViewById(R.id.retrieveText);
        retBtn = findViewById(R.id.retrieveBtn);


        // database ref pointing to root of db

        rootRef = FirebaseDatabase.getInstance().getReference();

        // database ref pointing to notes node
        notesRef = rootRef.child("notes");


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = editText.getText().toString();

                // Push creates a unique id in database
                notesRef.setValue(value);
                editText.setVisibility(View.INVISIBLE);
            }

        });



        retBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        textView.setText("Your output text: " + value);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(ProfileActivity.this, "Error fetching text from database", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    }

