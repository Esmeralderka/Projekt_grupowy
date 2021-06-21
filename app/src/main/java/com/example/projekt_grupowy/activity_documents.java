package com.example.projekt_grupowy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.projekt_grupowy.Adapters.DocumentsAdapter;
import com.example.projekt_grupowy.Models.Document;

import java.util.ArrayList;

public class activity_documents extends AppCompatActivity {

    RecyclerView rv;
    Button addDocumentButton;
    Button importFileButton;
    ImageView ImageView_LogOut;
    Intent myFileIntent;
    TextView tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);



        rv = (RecyclerView) findViewById(R.id.documentsRV);
        tx =  findViewById(R.id.textView8);
        addDocumentButton = findViewById(R.id.button_addField);

        addDocumentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAddDocument();
            }
        });
        setRv();

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

        importFileButton = findViewById(R.id.button_importFile_2);
        importFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // MainActivity.logout();
                myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                myFileIntent.setType("documents/*");
                startActivityForResult(Intent.createChooser(myFileIntent, "Select document"),1);
               // Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                // Uwaga: wyczyszczenie flag zablokuje mozliwosc powrotu do poprzedniego ekranu, ale w zamian korzystajac z przycisku
                // powrotu wywali uzytkownika do ekranu (zminimalizuje apke)
               // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
               // startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRv();
    }

    private void navigateToAddDocument(){
        Intent intent = new Intent(getApplicationContext(), AddDocumentActivity.class);
        startActivity(intent);
    }


    private void setRv(){
        DocumentsAdapter adapter = new DocumentsAdapter(this);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case 10:
                if(requestCode==RESULT_OK)
                {
                    String patch = data.getData().getPath();
                    tx.setText(patch);
                }
        break;
        }

      //  super.onActivityResult(requestCode, resultCode, data);
    }
}