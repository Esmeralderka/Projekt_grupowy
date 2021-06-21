package com.example.projekt_grupowy;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.projekt_grupowy.Adapters.DocumentsAdapter;
import com.example.projekt_grupowy.Models.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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
                Intent gallery = new Intent();
                gallery.setType("*/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select document"),1);
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

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode)
        {
            case 1:
                if(resultCode==RESULT_OK) {
                    String patch = data.getData().getLastPathSegment();
                    tx.setText(patch);
                    System.out.println(data.getData());

                    try {
                        Uri imageuri = data.getData();
                        InputStream stream = null;
                        String tempID= "", id ="";
                        Uri uri = data.getData();
                        String actualfilepath = null;
                        if (imageuri.getAuthority().equals("media")){
                            tempID =   imageuri.toString();
                            tempID = tempID.substring(tempID.lastIndexOf("/")+1);
                            id = tempID;
                            Uri contenturi = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                            String selector = MediaStore.Images.Media._ID+"=?";
                            actualfilepath = getColunmData( contenturi, selector, new String[]{id}  );
                        }else if (imageuri.getAuthority().equals("com.android.providers.media.documents")){
                            tempID = DocumentsContract.getDocumentId(imageuri);
                            String[] split = tempID.split(":");
                            String type = split[0];
                            id = split[1];
                            Uri contenturi = null;
                            int i = 10;
                            if (type.equals("image")){
                                contenturi = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                            }else if (type.equals("video")){
                                contenturi = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                            }else if (type.equals("audio")){
                                contenturi = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                            }
                            String selector = "_id=?";
                            actualfilepath = getColunmData( contenturi, selector, new String[]{id}  );
                        } else if (imageuri.getAuthority().equals("com.android.providers.downloads.documents")){
                            tempID =   imageuri.toString();
                            tempID = tempID.substring(tempID.lastIndexOf("/")+1);
                            id = tempID;
                            Uri contenturi = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                            // String selector = MediaStore.Images.Media._ID+"=?";
                            actualfilepath = getColunmData( contenturi, null, null  );
                        }else if (imageuri.getAuthority().equals("com.android.externalstorage.documents")){
                            tempID = DocumentsContract.getDocumentId(imageuri);
                            String[] split = tempID.split(":");
                            String type = split[0];
                            id = split[1];
                            Uri contenturi = null;
                            if (type.equals("primary")){
                                actualfilepath=  Environment.getExternalStorageDirectory()+"/"+id;
                            }
                        }
                        File myFile = new File(actualfilepath);
                        // MessageDialog dialog = new MessageDialog(Home.this, " file details --"+actualfilepath+"\n---"+ uri.getPath() );
                        // dialog.displayMessageShow();
                        String temppath =  uri.getPath();
                        if (temppath.contains("//")){
                            temppath = temppath.substring(temppath.indexOf("//")+1);
                        }
                        if ( actualfilepath.equals("") || actualfilepath.equals(" ")) {
                            myFile = new File(temppath);
                        }else {
                            myFile = new File(actualfilepath);
                        }
                        //File file = new File(actualfilepath);
                        //Log.e(TAG, " actual file path is "+ actualfilepath + "  name ---"+ file.getName());
//                    File myFile = new File(actualfilepath);
                        readfile(myFile);
                        // lyf path  - /storage/emulated/0/kolektap/04-06-2018_Admin_1528088466207_file.xls
                    } catch (Exception e) {
                    }
                    //------------  /document/primary:kolektap/30-05-2018_Admin_1527671367030_file.xls
                }
        }
    }
    public String getColunmData( Uri uri, String selection, String[] selectarg){
        String filepath ="";
        Cursor cursor = null;
        String colunm = "_data";
        String[] projection = {colunm};
        cursor =  getContentResolver().query( uri, projection, selection, selectarg, null);
        if (cursor!= null){
            cursor.moveToFirst();
            filepath = cursor.getString(cursor.getColumnIndex(colunm));
        }
        if (cursor!= null)
            cursor.close();
        return  filepath;
    }
    public void readfile(File file){
        // File file = new File(Environment.getExternalStorageDirectory(), "mytextfile.txt");
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine())!=null){
                builder.append(line);
                builder.append("\n");
            }
            br.close();
        }catch (Exception e){
            Log.e("main", " error is "+e.toString());
        }
        System.out.println(builder.toString());
    }
}