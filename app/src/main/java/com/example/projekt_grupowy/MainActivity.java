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

import com.example.projekt_grupowy.Formats.BibTeX_Article;
import com.example.projekt_grupowy.Formats.BibTeX_Book;
import com.example.projekt_grupowy.Formats.EndNote_Generic;
import com.example.projekt_grupowy.Models.Document;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public static User appUser = null;

    EditText _Email;
    EditText _Password;
    Button _LogIn;

    TextView _SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // test format
        System.out.println();
            //
            BibTeX_Article newArticle = new BibTeX_Article();

            newArticle.setDocumentName("dokumencik");

            newArticle.setField("author", "Jan Matejko");
            newArticle.setField("title", "Kosmiczne przygody");
            newArticle.setField("journal", "Odkrywca");
            newArticle.setField("year", "1887");

            newArticle.setField("pages", "54");
            newArticle.setField("month", "04");
            newArticle.setField("note", "super sie czyta UwU");

            System.out.println(newArticle.getFormat());


            //
            BibTeX_Book newBook = new BibTeX_Book();

            newBook.setDocumentName("eksplozje");

            newBook.setField("author", "Megumin");
            newBook.setField("title", "Explosion!");
            newBook.setField("publisher", "Szymek");
            newBook.setField("year", "2021");

            newBook.setField("edition", "Dynamite");
            newBook.setField("note", "EXPLOSIOOON!!!");

            System.out.println(newBook.getFormat());

            //
            EndNote_Generic newBookEN = new EndNote_Generic();

            //newBookEN.setDocumentName("eksplozje");

            newBookEN.setField("Author", "Megumin");
            newBookEN.setField("Title", "Explosion!");
            newBookEN.setField("Publisher", "Szymek");
            newBookEN.setField("Year", "2021");

            newBookEN.setField("Edition", "Dynamite");
            newBookEN.setField("Notes", "EXPLOSIOOON!!!");

            System.out.println(newBookEN.getFormat());
        //


        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        _Email = findViewById(R.id.editTextTextEmailAddress);
        _Password = findViewById(R.id.editTextTextPassword);
        _LogIn = findViewById(R.id.logSignIn);
        _SignUp = findViewById(R.id.logJoinUs);

        //_Email.setText("janrodo@gmail.com");
        //_Password.setText("Janrodo1!");

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

        DocumentReference DocRef = db.collection("users").document(UserId);
        DocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                appUser = new User(UserId);

                appUser.getDocumentReference().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {

                                try {
                                    appUser.getUserFirebase().set(document.getData());

                                    appUser.getDocumentReference().collection("documents")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            try {
                                                                Map<String, Object> map = document.getData();

                                                                HashMap<String, Object> hashMap =
                                                                        (map instanceof HashMap)
                                                                                ? (HashMap) map
                                                                                : new HashMap<String, Object>(map);

                                                                appUser.addDocument(new Document(document.getId(), hashMap));

                                                                System.out.println("User document:" + document.getId());
                                                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                                            } catch (Exception error) {
                                                                Toast.makeText(MainActivity.this, "Something went wrong (>m<) - wrong document map? \n ", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }


                                                        System.out.println(appUser.toString());

                                                        Toast.makeText(MainActivity.this, "You successfully log in (OwO)", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(getApplicationContext(), activity_documents.class);
                                                        startActivity(intent);

                                                    } else {
                                                        Toast.makeText(MainActivity.this, "Something went wrong (>m<) - wrong documents \n ", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });



                                } catch (NullPointerException error) {
                                    Toast.makeText(MainActivity.this, "Something went wrong (>m<) - null document field \n ", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Something went wrong (>m<) - no document \n ", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Something went wrong (>m<) \n ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Something went wrong (>m<) \n ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void logout(){
        appUser = null;
        FirebaseAuth mAuth;
        FirebaseAuth.getInstance().signOut();
    }
}