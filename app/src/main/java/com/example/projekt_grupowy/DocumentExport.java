package com.example.projekt_grupowy;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_grupowy.Adapters.DocumentExportAdapter;
import com.example.projekt_grupowy.Adapters.DocumentPropertiesAdapter;
import com.example.projekt_grupowy.Formats.BibTeX_Article;
import com.example.projekt_grupowy.Formats.BibTeX_Book;
import com.example.projekt_grupowy.Formats.BibTeX_Booklet;
import com.example.projekt_grupowy.Formats.BibTeX_Conference;
import com.example.projekt_grupowy.Formats.BibTeX_Inbook;
import com.example.projekt_grupowy.Formats.BibTeX_Incollection;
import com.example.projekt_grupowy.Formats.BibTeX_Inproceedings;
import com.example.projekt_grupowy.Formats.BibTeX_Manual;
import com.example.projekt_grupowy.Formats.BibTeX_Mastersthesis;
import com.example.projekt_grupowy.Formats.BibTeX_Misc;
import com.example.projekt_grupowy.Formats.BibTeX_Phdthesis;
import com.example.projekt_grupowy.Formats.BibTeX_Proceedings;
import com.example.projekt_grupowy.Formats.BibTeX_Techreport;
import com.example.projekt_grupowy.Formats.BibTeX_Unpublished;
import com.example.projekt_grupowy.Formats.EndNote_Generic;
import com.example.projekt_grupowy.Formats.Format;
import com.example.projekt_grupowy.Formats.RIS_Generic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class DocumentExport extends AppCompatActivity {

    public static int documentPositionInUserDocumentsArrayList;

    public static String documentFormat;
    public static String RIS_format;
    public static String exportTo;

    private Format format;
    private  DocumentExportAdapter adapter;

    RecyclerView RVExport;
    ImageView BBack;
    Button BExport;
    TextView tv_docType;
    TextView tv_docName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_to);

        System.out.println(documentFormat + ",  " + exportTo);

        tv_docType = (TextView) findViewById(R.id.tv_docType);
        tv_docName = (TextView) findViewById(R.id.tv_docName);

        BBack = (ImageView) findViewById(R.id.BBack);
        BExport = (Button) findViewById(R.id.BExport);
        RVExport = (RecyclerView) findViewById(R.id.RVExport);

        setFormat();
        format.setDocumentName(MainActivity.appUser.getDocument(documentPositionInUserDocumentsArrayList).getName());

        tv_docType.setText(format.getFileFormat() + " "+ format.getFileType());
        tv_docName.setText(MainActivity.appUser.getDocument(documentPositionInUserDocumentsArrayList).getName());
        if(tv_docName.getText().length() > 15){
            String tempTitle = MainActivity.appUser.getDocument(documentPositionInUserDocumentsArrayList).getName().substring(0,15);
            tempTitle+="...";
            tv_docName.setText(tempTitle);
        }

        setRv();

        BExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                export();
            }
        });
    }

    private void export() {
        HashMap <String,String> newFormatContent = adapter.getFormatFields();

        System.out.println("################################################################");
        newFormatContent.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + " " + entry.getValue());
        });

        Boolean validation = true;

        for (String key : format.getKeys()) {
            if(format.getIsFieldRequired().get(key) && !newFormatContent.containsKey(key)){
                validation = false;
            }
        }

        if(validation){
            for (String key : format.getKeys()) {
                if(newFormatContent.containsKey(key)){
                    if(newFormatContent.get(key) != null )
                    format.setField(key, newFormatContent.get(key));
                }
            }
            System.out.println(format.getFormat());

            System.out.println("EXPORT TO: " + exportTo);


            if(exportTo.equals("clipboard")){
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("doc", format.getFormat());
                clipboard.setPrimaryClip(clip);
            }else if(exportTo.equals("file")){
                saveToFile();
            }
        }
    }

    private void saveToFile(){

        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

        FileOutputStream fos = null;

        String fileName = MainActivity.appUser.getDocument(documentPositionInUserDocumentsArrayList).getName();
        fileName += ".";
        fileName += format.getFileFormat();

        //if user OS is greater than 23, runtime permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String [] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

                //ask for permission
                requestPermissions(permissions, 1);
            }else {
                //permission already granted
                save();
            }
        }else{
            //user OS < 23, no need for permission
            save();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                //if permission is not granted, grantResults array is empty
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    save();
                }else {
                    Toast.makeText(this, "Storage permission is required to save a file", Toast.LENGTH_LONG);
                }
        }
    }

    private void save(){

        try {
            //path to storage
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

            //create folder "DocPol_documents"
            File dir = new File(path + "/DocPol_documents/");
            //File dir = new File(String.valueOf(path));
            if (! dir.exists()){
                dir.mkdirs();
            }

            if (!dir.mkdirs()) { Log.d("Un Available:" , path.getAbsolutePath()); }

            //file name based on document name
            String fileName = MainActivity.appUser.getDocument(documentPositionInUserDocumentsArrayList).getName();
            fileName += ".";
            fileName += format.getFileFormat();

            File new_file = new File(dir, fileName);

            //FileWriter class is used to storage characters in file
            FileWriter fw = new FileWriter(new_file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(format.getFormat());
            bw.close();

            //info
            Toast.makeText(this, "file " + fileName + " saved to:\n" + dir, Toast.LENGTH_LONG);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
        }
    }

    private void setRv(){
        adapter = new DocumentExportAdapter(this,MainActivity.appUser.getDocuments().get(documentPositionInUserDocumentsArrayList), format);
        RVExport.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RVExport.setAdapter(adapter);
    }

    private void setFormat()
    {
        switch (documentFormat){
            case "BibTeX_Article":
                format = new BibTeX_Article();
                break;
            case "BibTeX_Book":
                format = new BibTeX_Book();
                break;
            case "BibTeX_Booklet":
                format = new BibTeX_Booklet();
                break;
            case "BibTeX_Conference":
                format = new BibTeX_Conference();
                break;
            case "BibTeX_Inbook":
                format = new BibTeX_Inbook();
                break;
            case "BibTeX_Incollection":
                format = new BibTeX_Incollection();
                break;
            case "BibTeX_Inproceedings":
                format = new BibTeX_Inproceedings();
                break;
            case "BibTeX_Manual":
                format = new BibTeX_Manual();
                break;
            case "BibTeX_Mastersthesis":
                format = new BibTeX_Mastersthesis();
                break;
            case "BibTeX_Misc":
                format = new BibTeX_Misc();
                break;
            case "BibTeX_Phdthesis":
                format = new BibTeX_Phdthesis();
                break;
            case "BibTeX_Proceedings":
                format = new BibTeX_Proceedings();
                break;
            case "BibTeX_Techreport":
                format = new BibTeX_Techreport();
                break;
            case "BibTeX_Unpublished":
                format = new BibTeX_Unpublished();
                break;
            case "EndNote_Generic":
                format = new EndNote_Generic();
                break;
            case "RIS":
                format = new RIS_Generic(RIS_format);
                break;
        }
    }
}
