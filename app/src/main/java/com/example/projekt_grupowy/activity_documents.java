package com.example.projekt_grupowy;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_grupowy.Adapters.AddDocumentAdapter;
import com.example.projekt_grupowy.Adapters.DocumentsAdapter;
import com.example.projekt_grupowy.Models.Document;
import com.example.projekt_grupowy.Models.Import;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;

public class activity_documents extends AppCompatActivity {

    RecyclerView rv;
    Button addDocumentButton;
    Button importFileButton;
    ImageView ImageView_LogOut;
    Intent myFileIntent;
    DocumentBuilder builder;
    ImageView refresh;

    AddDocumentActivity doc_activ;
    AddDocumentAdapter adapter2;
    DocumentsAdapter adapter ;//= new DocumentsAdapter(this);
    ArrayList<Document> documents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);
        rv = (RecyclerView) findViewById(R.id.documentsRV);
        addDocumentButton = findViewById(R.id.button_addField);


        refresh = findViewById(R.id.button_refresh_4);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // REFRESH

              //  documents.get(docPosition).getUID())

                rv.removeAllViews();

                setRv();
                adapter.notifyDataSetChanged();
               // MainActivity.appUser.addDocument(newDocument);
//                doc_activ.setRecyclerView();
              //  adapter.onBindViewHolder();
                /*adapter2 = new AddDocumentAdapter(v.getContext());
                adapter2.initializeArray();
                rv = findViewById(R.id.documentsRV);
                rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                rv.setAdapter(adapter2);*/

              /*  rv.removeAllViews();
                adapter.notifyItemInserted(8);*/
            //    navigate();
                //System.out.println(rv.getI);
               /* adapter = new DocumentsAdapter(v.getContext());
                rv.invalidate();

                setRv();
                System.out.println("HALO DO CHOLERY DZIEJE SIE COS??");
                adapter.notifyDataSetChanged();*/
                //doc_activ.set

                /*adapter = new AddDocumentAdapter(v.getContext());
                adapter.initializeArray();
                rv = findViewById(R.id.documentsRV);
                rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                rv.setAdapter(adapter);*/


                //AddDocumentAdapter addDocumentAdapter = new AddDocumentAdapter(v.getContext());
               // addDocumentAdapter.onCreateViewHolder();

               /* Intent i = new Intent(getApplicationContext(), activity_documents.class);
                startActivity(i);*/
             /* DocumentsAdapter adapter = new DocumentsAdapter(v.getContext());
                rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                rv.setAdapter(adapter);*/

            }
        });

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
                Intent gallery = new Intent();
                gallery.setType("*/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select document"),1);

                // Uwaga: wyczyszczenie flag zablokuje mozliwosc powrotu do poprzedniego ekranu, ale w zamian korzystajac z przycisku
                // powrotu wywali uzytkownika do ekranu (zminimalizuje apke)
                // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setRv();
    }

    private void navigateToAddDocument(){
        Intent intent = new Intent(getApplicationContext(), AddDocumentActivity.class);
        startActivity(intent);
    }

    private void setRv(){
        adapter = new DocumentsAdapter(this);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.setAdapter(adapter);
        System.out.println("HALO WYWOLUJE SIE TO KIEDY??");
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        HashMap<String, Object> txt_line = new HashMap<>();
        switch(requestCode)
        {
            case 1:
                if(resultCode==RESULT_OK) {
                    String patch = data.getData().getPath();
                    System.out.println(data.getData());
                    System.out.println("getAuthority" + data.getData().getAuthority());

                    final Uri uri = data.getData();
                    InputStream inputStream = null;
                    String str = "";
                    StringBuffer buf = new StringBuffer();
                    try {
                        inputStream = getContentResolver().openInputStream(uri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    if (inputStream!=null){
                        try {
                            while((str = reader.readLine())!=null){

                                buf.append(str+"\n");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println(" XxXxXxXxXxX@@@@@@@@@@@@@@@@@@@XxXxXxXxXxX" + buf.toString());

                      //  String s = buf.toString();

                    /**TUTAJ*/

                        if(true) {

                            String s = buf.toString();

                            int start = 0;
                            int end = 0;


                            start = s.indexOf("{", start);

                           // if (start == -1) break;
                            end = s.indexOf(",", start);
                            int end22 = s.indexOf(" ", start);

                            if (end == -1) end = end22;
                            if (end == -1 && end22 == -1) end = s.length();


                            String ss2 = s.substring(start+1, end);//zeby nie bylo {

                            System.out.println("UWAGA WYPISUJE SOBIE TYTUL "+ ss2);

                         //   start = end + 1;

                            ArrayList<String>line = new ArrayList<>();
                            for ( int i = 0; i < s.length(); ++i) {

                                start = s.indexOf("\n", start);

                                if (start == -1) break;
                                end = s.indexOf("\n", start+1);
                                //int end2 = s.indexOf("}", start+1);

                               /* if (end == -1) end = end2;*/
                                if (end == -1 ) end = s.length();


                                String ss = s.substring(start, end);
                                line.add(ss);
                                System.out.println("UWAGA WYPISUJE SOBIE COS "+i+ ss);

                                start = end ;

                            }


                            for(int i=0; i<line.size()-2;i++)
                            {
                                start = 0;
                                end = 0;

                                    String key, content;

                                   // start=0;
                                    end = line.get(i).indexOf("=", start+1);
                                    int end2 = line.get(i).indexOf(" ", start+1);

                                    if (end == -1) end = end2;
                                    if (end == -1 && end2==-1 ) end = line.get(i).length();

                                    key=line.get(i).substring(1, end);
                                    System.out.println("KLUUUCZ "+key);



                                    start = line.get(i).indexOf("=", start+1);
                                    switch(line.get(i).charAt(start+2)){
                                        case '\"' :
                                            start += 3;
                                            end = line.get(i).indexOf('\"', start);
                                            break;
                                        case '{' :

                                            start+=3;
                                            end = line.get(i).indexOf('}', start);

                                            break;
                                        default:
                                            start+=2;
                                            end = line.get(i).length();
                                            if(line.get(i).charAt(end-1)==',')
                                            {
                                                end-=1;
                                            }

                                            break;

                                        }
                                    if (start == -1) break;

                                    if (end == -1 ) end = line.get(i).length();


                                    content = line.get(i).substring(start, end);

                                    System.out.println("KONTENT "+ content);
                                txt_line.put(key.trim(),content);
                            }
                            txt_line.put("name", ss2.trim());
                            for (Map.Entry<String, Object> entry : txt_line.entrySet()) {
                                String keyz = entry.getKey();
                                Object value = entry.getValue();
                                System.out.println("keyz: "+keyz);
                                System.out.println("value: "+value);
                            }
                        }

                    }
                    Document document = new Document();
                    UUID uuid = UUID.randomUUID();
                    String uuidAsString = uuid.toString();
                    document.setDocumentHashMap(txt_line);
                    document.setUID(uuidAsString);

                    FirebaseFirestore.getInstance()
                            .collection("users")
                            .document(MainActivity.appUser.getUID())
                            .collection("documents")
                            .document().set(txt_line)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                         //   rv.se
                            MainActivity.appUser.addDocument(document);

                            //setRv();
                            System.out.println("UDALO SIEE");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("NIEEEE " + e.toString());
                        }
                    });
                    }
                }
    }
}