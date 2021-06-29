package com.example.projekt_grupowy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.projekt_grupowy.Adapters.DocumentPropertiesAdapter;
import com.example.projekt_grupowy.Models.Document;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class DocumentProperties extends AppCompatActivity {

    //public static int documentPositionInUserDocumentsArrayList;
    public static Document document;

    //private Document document;
    ArrayList<Pair<String,String>> documentFields;

    //TextView tv_docName;
    TextInputEditText et_docname;
    RecyclerView recyclerView;
    Button addButton;
    Button save;
    Button cancel;
    DocumentPropertiesAdapter adapter;
    private FirebaseFirestore db;
    CollectionReference ColRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_properties);

        db = FirebaseFirestore.getInstance();
        Log.d("appuser uid", MainActivity.appUser.getUID());
        ColRef = db.collection("users").document(MainActivity.appUser.getUID()).collection("documents");

        //document = MainActivity.appUser.getDocuments().get(documentPositionInUserDocumentsArrayList);

        save = (Button) findViewById(R.id.button_Save);
        addButton = (Button) findViewById(R.id.addButton);
        et_docname = (TextInputEditText) findViewById(R.id.etDocumentName);
        recyclerView = (RecyclerView) findViewById(R.id.documentsRV);

        et_docname.setText(document.getName());

        cancel = findViewById(R.id.button_cancel2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), activity_documents.class);
                startActivity(i);
            }
        });


        initializeFieldList();
        setRv();
        setOnClickListeners();
        //TODO:cała reszta wyświetlanie pól etc.
    }

    private void setRv(){
        adapter = new DocumentPropertiesAdapter(this,documentFields);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListeners() {


        save.setOnClickListener(new View.OnClickListener() {
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


    private void initializeFieldList()
    {
        documentFields = new ArrayList<Pair<String, String>>();

        HashMap<String, Object> hashMap = document.getDocumentHashMap();

        for (String key : hashMap.keySet()) {
            if(!key.equals("name")){
                documentFields.add(new Pair(key,(String) hashMap.get(key)));
            }
        }
        Collections.sort(documentFields, (Pair<String,String> a1, Pair<String,String> a2) -> a1.first.compareTo(a2.first));

    }

    private void Confirm() {

        if(et_docname.getText().toString().isEmpty()){
            et_docname.setError("Document name is required!");
            et_docname.requestFocus();

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
        temp.put("name", et_docname.getText());
        toDb.put("name", ""+et_docname.getText());
        for (Pair<String, String> var : adapter.getDocumentFields()) {
            temp.put(var.first, var.second);
            toDb.put(var.first, var.second);
        }


        temp.forEach((key, value) -> {
            Log.d("HASZMAP", key + " = " + value);
        });


        Document newDocument = new Document(document.getUID(), temp );

        Log.d("appUser przed edycja dokumentu: ", MainActivity.appUser.toString());
        //MainActivity.appUser.addDocument(newDocument);
        MainActivity.appUser.setDocument(newDocument);
        Log.d("appUser po edycji dokumentu: ", MainActivity.appUser.toString());

        ColRef.document(newDocument.getUID()).set(toDb);

        Toast.makeText(this,"Successfully saved document!",Toast.LENGTH_LONG).show();

    }
}