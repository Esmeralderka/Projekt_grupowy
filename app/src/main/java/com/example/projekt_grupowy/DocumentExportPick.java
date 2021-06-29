package com.example.projekt_grupowy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DocumentExportPick extends AppCompatActivity {

    public static int documentPositionInUserDocumentsArrayList;

    Button BExport;
    Spinner spinner;
    Spinner spinnerFormatType;
    //AutoCompleteTextView ACTV;
    RadioGroup radioGroup;
    TextView tv_docName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_pick);
        tv_docName = (TextView) findViewById(R.id.tv_docName);
        tv_docName.setText(MainActivity.appUser.getDocument(documentPositionInUserDocumentsArrayList).getName());
        if(tv_docName.getText().length() > 15){
            String tempTitle = MainActivity.appUser.getDocument(documentPositionInUserDocumentsArrayList).getName().substring(0,15);
            tempTitle+="...";
            tv_docName.setText(tempTitle);
        }
        BExport = (Button) findViewById(R.id.BExport);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinnerFormatType = (Spinner) findViewById(R.id.spinnerFormatType);

        populateSpinner();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner.getSelectedItem().toString().equals("RIS")){
                    populateSpinnerRIS();
                    spinnerFormatType.setVisibility(View.VISIBLE);
                }else if(spinner.getSelectedItem().toString().equals("BibTeX")){
                    populateSpinnerBibTeX();
                    spinnerFormatType.setVisibility(View.VISIBLE);
                }else{
                    spinnerFormatType.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);


        BExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentExport.documentFormat = spinner.getSelectedItem().toString();

                if(spinner.getSelectedItem().toString().equals("RIS")){

                    ArrayList<ArrayList<String>> risFormats = new ArrayList<>();
                    risFormats.add(new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.risFormatsName))));
                    risFormats.add(new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.risFormatsFullName))));

                    DocumentExport.RIS_format = risFormats.get(0).get(spinnerFormatType.getSelectedItemPosition());
                }else if(spinner.getSelectedItem().toString().equals("BibTeX")){
                    DocumentExport.documentFormat = spinnerFormatType.getSelectedItem().toString();
                }

                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                DocumentExport.exportTo = (String) radioButton.getText();

                DocumentExport.documentPositionInUserDocumentsArrayList = documentPositionInUserDocumentsArrayList;

                Intent intent = new Intent(getApplicationContext(), DocumentExport.class);
                startActivity(intent);
            }
        });
    }

    private void populateSpinner() {
        String [] formats = getResources().getStringArray(R.array.formats);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_actv_format, formats);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.item_spinner_style, getResources().getStringArray(R.array.formats));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);
    }

    private void populateSpinnerRIS() {
        String [] formats = getResources().getStringArray(R.array.formats);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_actv_format, formats);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.item_spinner_style, getResources().getStringArray(R.array.risFormatsFullName));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFormatType.setAdapter(adapter2);
    }

    private void populateSpinnerBibTeX() {
        String [] formats = getResources().getStringArray(R.array.formats);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_actv_format, formats);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.item_spinner_style, getResources().getStringArray(R.array.bibTexFormatName));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFormatType.setAdapter(adapter2);
    }

}