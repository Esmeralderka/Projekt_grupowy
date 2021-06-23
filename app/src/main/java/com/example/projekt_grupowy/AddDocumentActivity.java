package com.example.projekt_grupowy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_grupowy.Adapters.AddDocumentAdapter;
import com.example.projekt_grupowy.Models.Document;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class AddDocumentActivity extends AppCompatActivity {

    Button addButton;
    Button confirm;
    private FirebaseFirestore db;
    CollectionReference ColRef;

    RecyclerView rv;
    AddDocumentAdapter adapter;
    TextInputEditText etDocumentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_document);
        etDocumentName = findViewById(R.id.etDocumentName);

        db = FirebaseFirestore.getInstance();
        Log.d("appuser uid", MainActivity.appUser.getUID());
        ColRef = db.collection("users").document(MainActivity.appUser.getUID()).collection("documents");


        setRecyclerView();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        addButton = findViewById(R.id.addButton);

        confirm = findViewById(R.id.button_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirm();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addNewField();
            }
        });
    }

    public void setRecyclerView() {
        adapter = new AddDocumentAdapter(this);
        adapter.initializeArray();
        rv = findViewById(R.id.documentsRV);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.setAdapter(adapter);
    }

    private void navigate(){
        Intent intent = new Intent(getApplicationContext(), activity_documents.class);
        startActivity(intent);
    }

    private void Confirm() {

        if(etDocumentName.getText().toString().isEmpty()){
            etDocumentName.setError("Document name is required!");
            etDocumentName.requestFocus();

            return;
        }

        ArrayList<String> templist = new ArrayList<>();
        adapter.getDocumentFields().forEach(stringStringPair -> templist.add(stringStringPair.first));

        //Set<Pair<String,String>> set = new HashSet<Pair<String,String>>(adapter.getDocumentFields());
        Set<String> set = new HashSet<String>(templist);
        if(set.size() < adapter.getDocumentFields().size())
        {
            Toast.makeText(this,"Remove duplicate type of fields!", Toast.LENGTH_LONG).show();
            return;
        }




        for (Pair<String, String> var : adapter.getDocumentFields()) {
            if(var.first.isEmpty())
            {
                Toast.makeText(this,"Field types cannot be empty!", Toast.LENGTH_LONG).show();
                return;
            }

            if(var.first.equals("name"))
            {
                Toast.makeText(this,"Field type \"name\" is reserved!", Toast.LENGTH_LONG).show();
                return;
            }

        }

        HashMap<String, Object> temp = new HashMap<String, Object>();
        Map<String,Object> toDb = new HashMap<>();
        temp.put("name", etDocumentName.getText());
        toDb.put("name", ""+etDocumentName.getText());
        for (Pair<String, String> var : adapter.getDocumentFields()) {
            temp.put(var.first, var.second);
            toDb.put(var.first, var.second);
        }


        temp.forEach((key, value) -> {
            Log.d("HASZMAP", key + " = " + value);
        });

        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        Document newDocument = new Document(uuidAsString,
                temp
        );

        Log.d("appUser przed dodaniem dokumentu: ", MainActivity.appUser.toString());
        MainActivity.appUser.addDocument(newDocument);
        Log.d("appUser po dodaniu dokumentu: ", MainActivity.appUser.toString());

        ColRef.document(newDocument.getUID()).set(toDb);

        navigate();
    }
}