package com.example.projekt_grupowy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.projekt_grupowy.Models.Document;

public class DocumentProperties extends AppCompatActivity {

    public static int documentPositionInUserDocumentsArrayList;

    private Document document;

    TextView tv_docName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_properties);

        document = MainActivity.appUser.getDocuments().get(documentPositionInUserDocumentsArrayList);

        tv_docName = (TextView) findViewById(R.id.tv_docName);

        tv_docName.setText(document.getName());

        //TODO:cała reszta wyświetlanie pól etc.
    }
}