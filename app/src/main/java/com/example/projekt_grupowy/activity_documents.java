package com.example.projekt_grupowy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.projekt_grupowy.Adapters.DocumentsAdapter;
import com.example.projekt_grupowy.Models.Document;

import java.util.ArrayList;

public class activity_documents extends AppCompatActivity {

    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);

        rv = (RecyclerView) findViewById(R.id.documentsRV);
        setRv();
    }


    private void setRv(){
        DocumentsAdapter adapter = new DocumentsAdapter();
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.setAdapter(adapter);
    }
}