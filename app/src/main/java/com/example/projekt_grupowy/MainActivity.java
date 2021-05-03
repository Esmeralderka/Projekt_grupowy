package com.example.projekt_grupowy;
// pozdrawiam wszystkie anime dziewczynki!!! UwU~
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projekt_grupowy.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    EditText _Email;
    EditText _Password;
    Button _Button2;
    //Button _Button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //witam wszystkich bardzo serdecznie ;3

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        _Email = findViewById(R.id.editTextTextEmailAddress);
        _Password = findViewById(R.id.editTextTextPassword);
        _Button2 = findViewById(R.id.logSignIn);
        //_Button3 = findViewById(R.id.button3);

//        _Button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String Email = _Email.getText().toString().trim();
//                String Password = _Password.getText().toString().trim();
//
//                singUp();
//            }
//        });

        _Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = _Email.getText().toString().trim();
                String Password = _Password.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            logIn();
                        }else {
                            Toast.makeText(MainActivity.this, "Something went wrong (>m<) \n " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void singUp(){

        String Email = _Email.getText().toString().trim();
        String Password = _Password.getText().toString().trim();

        //TODO tu dać walidacje jak będziemy wiedzieć co właściwie podajemy przy tworzeniu konta i jakie są wymogi
        if(true) {

            mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "User created", Toast.LENGTH_SHORT).show();

                        String userId = mAuth.getCurrentUser().getUid();
                        DocumentReference docRef = db.collection("users").document(userId);

                        User user = new User(Email);


                        docRef.set(user).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Something went wrong (Firestore):c \n " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Login after registration (Firestore):c \n ", Toast.LENGTH_SHORT).show();

                                mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if(task.isSuccessful()){
                                            logIn();
                                        }else {
                                            Toast.makeText(MainActivity.this, "Something went wrong (>m<) \n " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
                    } else {
                        Toast.makeText(MainActivity.this, "Something went wrong (Auth) :c \n " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void logIn(){

        String UserId = mAuth.getCurrentUser().getUid();

        DocumentReference DocRef = db.collection("user").document(UserId);

        DocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Toast.makeText(MainActivity.this, "You successfully log in (>w<)", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Activity_base.class);
                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Something went wrong (>m<) \n ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}