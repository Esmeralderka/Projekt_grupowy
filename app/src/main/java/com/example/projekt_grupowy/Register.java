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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    EditText _Email;
    EditText _Password;
    EditText _ConfirmPassword;

    // Przycisk do powrotu do ekranu logowania
    TextView regSignIn;

    Button regSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //witam wszystkich bardzo serdecznie ;3

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        _Email = findViewById(R.id.regMail);
        _Password = findViewById(R.id.regPassword);
        _ConfirmPassword = findViewById(R.id.regConfirmPassword);

        regSignIn = findViewById(R.id.regSignIn);
        regSignUp = findViewById(R.id.regSignUp);

        // Powrót do ekranu logowania
        regSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        regSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = _Email.getText().toString().trim();
                String Password = _Password.getText().toString().trim();
                String ConfirmPassword = _ConfirmPassword.getText().toString().trim();

                if(validation()){
                    singUp(Email, Password);
                }
            }
        });
    }

    private void singUp(String Email, String Password){

        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Register.this, "User created", Toast.LENGTH_SHORT).show();

                    String userId = mAuth.getCurrentUser().getUid();
                    DocumentReference docRef = db.collection("users").document(userId);

                    User user = new User(Email);



                    docRef.set(user).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Register.this, "Something went wrong (Firestore):c \n " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Register.this, "Login after registration (Firestore):c \n ", Toast.LENGTH_SHORT).show();

                            mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this, "You successfully register (OwO)", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(Register.this, "Something went wrong (>m<) \n " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(Register.this, "Something went wrong (Auth) :c \n " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validation(){
        String Email = _Email.getText().toString().trim();
        String Password = _Password.getText().toString().trim();
        String CPassword = _ConfirmPassword.getText().toString().trim();

        if(Email.isEmpty()){
            _Email.setError("Email address is required!");
            _Email.requestFocus();
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            _Email.setError("Incorrect email!");
            _Email.requestFocus();
            return false;
        }

        if(Password.isEmpty()){
            _Password.setError("Password is required!");
            _Password.requestFocus();
            return false;
        }

        if(Password.length() < 8){
            _Password.setError("Password can't have less than 8 characters!");
            _Password.requestFocus();
            return false;
        }

        Pattern letterLower = Pattern.compile("[a-z]");
        Matcher hasLetterLower = letterLower.matcher(Password);
        if(!hasLetterLower.find()){
            _Password.setError("Password must contain at least one lowercase letter");
            _Password.requestFocus();
            return false;
        }

        Pattern letterUpper = Pattern.compile("[A-Z]");
        Matcher hasLetterUpper = letterUpper.matcher(Password);
        if(!hasLetterUpper.find()){
            _Password.setError("Password must contain at least one uppercase letter");
            _Password.requestFocus();
            return false;
        }

        Pattern digit = Pattern.compile("[0-9]");
        Matcher hasDigit = digit.matcher(Password);
        if(!hasDigit.find()){
            _Password.setError("Password must contain at least one digit");
            _Password.requestFocus();
            return false;
        }

        Pattern special = Pattern.compile("[^A-Za-z0-9]");
        Matcher hasSpecial = special.matcher(Password);
        if(!hasSpecial.find()){
            _Password.setError("Password must contain at least one special character");
            _Password.requestFocus();
            return false;
        }

        if(CPassword.isEmpty()){
            _ConfirmPassword.setError("You have to confirm password!");
            _ConfirmPassword.requestFocus();
            return false;
        }

        if(!Password.equals(CPassword)){
            _ConfirmPassword.setError("Password confirmation doesn't match password");
            _ConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }
}