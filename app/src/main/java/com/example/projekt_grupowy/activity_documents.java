package com.example.projekt_grupowy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.projekt_grupowy.Adapters.DocumentsAdapter;
import com.example.projekt_grupowy.Models.Document;

import java.util.ArrayList;
import java.util.Collections;

public class activity_documents extends AppCompatActivity {

    RecyclerView rv;
    Button addDocumentButton;
    ImageView ImageView_LogOut;
    ArrayList<Document>documents;
    ArrayList<Document>documentsAll;
    CharSequence search="";
    EditText searchBar;
    String AA;
    DocumentsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);

        rv = (RecyclerView) findViewById(R.id.documentsRV);
        addDocumentButton = findViewById(R.id.button_addField);
        documents=MainActivity.appUser.getDocuments();

        Collections.sort(documents, (Document a1, Document a2) -> a1.getName().compareTo(a2.getName()));
        searchBar=findViewById(R.id.et_search_2);
        documentsAll=documents;
        setRv(documentsAll);


        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
                search=s;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



//        searchBar.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                for(int i=0;i<documentsAll.size();i++){
//                    System.out.println(documentsAll.get(i).getName());
//
//                }
//           AA=s.toString();
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                System.out.println("koniec");
//                for(int i=0;i<documentsAll.size();i++){
//                    if(documentsAll.get(i).getName().contains(AA))
//                    {
//                        documentsFiltered.add(documentsAll.get(i));
//                    }
//
//                }
//
//                setRv(documentsFiltered);
//            }
//
//        });


        addDocumentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAddDocument();
            }
        });

        ImageView_LogOut = (ImageView) findViewById(R.id.ImageView_LogOut);

        ImageView_LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.logout();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                // Uwaga: wyczyszczenie flag zablokuje mozliwosc powrotu do poprzedniego ekranu, ale w zamian korzystajac z przycisku
                // powrotu wywali uzytkownika do ekranu (zminimalizuje apke)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        setRv();
//    }

    private void navigateToAddDocument(){
        Intent intent = new Intent(getApplicationContext(), AddDocumentActivity.class);
        startActivity(intent);
    }


    private void setRv(ArrayList<Document>document){
        adapter= new DocumentsAdapter(this,document);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.setAdapter(adapter);
    }
}