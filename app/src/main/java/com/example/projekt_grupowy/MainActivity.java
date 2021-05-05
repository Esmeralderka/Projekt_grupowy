package com.example.projekt_grupowy;
// pozdrawiam wszystkie anime dziewczynki!!! UwU~
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    Button _LogIn;

    TextView _SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //witam wszystkich bardzo serdecznie ;3

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        _Email = findViewById(R.id.editTextTextEmailAddress);
        _Password = findViewById(R.id.editTextTextPassword);
        _LogIn = findViewById(R.id.logSignIn);
        _SignUp = findViewById(R.id.logJoinUs);

        _SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

        _LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = _Email.getText().toString().trim();
                String Password = _Password.getText().toString().trim();

                if(Email.isEmpty()){
                    _Email.setError("Email address is required!");
                    _Email.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    _Email.setError("Incorrect email!");
                    _Email.requestFocus();
                    return;
                }

                if(Password.isEmpty()){
                    _Password.setError("Password is required!");
                    _Password.requestFocus();
                    return;
                }

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

    private void logIn(){

        String UserId = mAuth.getCurrentUser().getUid();

        DocumentReference DocRef = db.collection("user").document(UserId);
        DocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Toast.makeText(MainActivity.this, "You successfully log in (OwO)", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), activity_documents.class);
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