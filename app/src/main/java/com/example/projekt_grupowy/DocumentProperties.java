package com.example.projekt_grupowy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.projekt_grupowy.Adapters.DocumentPropertiesAdapter;
import com.example.projekt_grupowy.Adapters.DocumentsAdapter;
import com.example.projekt_grupowy.Models.Document;

import java.util.ArrayList;
import java.util.HashMap;

public class DocumentProperties extends AppCompatActivity {

    public static int documentPositionInUserDocumentsArrayList;

    private Document document;
    ArrayList <String> fieldNames;
    ArrayList <String> fieldContent;

    TextView tv_docName;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_properties);

        document = MainActivity.appUser.getDocuments().get(documentPositionInUserDocumentsArrayList);

        tv_docName = (TextView) findViewById(R.id.tv_docName);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        tv_docName.setText(document.getName());


        initializeFieldList();
        setRv();

        //TODO:cała reszta wyświetlanie pól etc.
    }

    private void setRv(){
        DocumentPropertiesAdapter adapter = new DocumentPropertiesAdapter(fieldNames, fieldContent);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }


    private void initializeFieldList()
    {
        fieldNames = new ArrayList<>();
        fieldContent = new ArrayList<>();

        HashMap<String, Object> hashMap = document.getDocumentHashMap();

        for (String key : hashMap.keySet()) {
            fieldNames.add(key);
            fieldContent.add((String) hashMap.get(key));
        }
    }
}