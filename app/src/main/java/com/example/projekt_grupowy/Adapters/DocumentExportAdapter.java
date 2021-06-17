package com.example.projekt_grupowy.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_grupowy.Formats.Format;
import com.example.projekt_grupowy.Models.Document;
import com.example.projekt_grupowy.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;

public class DocumentExportAdapter extends RecyclerView.Adapter<DocumentExportAdapter.ViewHolder> {

    Context context;
    Document document;
    Format format;

    HashMap<String, String> formatFields;
    HashMap<String, Boolean> isFieldRequired;
    ArrayList<String> keys;

    private ArrayList<Pair<TextWatcher, TextInputEditText>> TextWatcherList = new ArrayList<>();
    AlertDialog.Builder builder;

    public DocumentExportAdapter(Context context, Document document, Format format)
    {
        this.context = context;
        this.format = format;
        this.document = document;

        this.keys = format.getKeys();
        this.isFieldRequired = format.getIsFieldRequired();
        this.formatFields = new HashMap<>();
    }

    @NonNull
    @Override
    public DocumentExportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_rv_exp_to, parent, false);
        DocumentExportAdapter.ViewHolder holder = new DocumentExportAdapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentExportAdapter.ViewHolder holder, int position) {
        if (keys.get(position) != null) {

            int _position = position;

            holder.tv_Title.setText(keys.get(_position));
            if(isFieldRequired.get(keys.get(_position))){
                holder.TVFieldRequired.setVisibility(View.VISIBLE);
            }

            ArrayList <String> documentFields = new ArrayList<>();
            documentFields.add("null");
            documentFields.addAll(document._getAllFieldsNames());

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.item_spinner_style, documentFields);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.spinner_exp.setAdapter(adapter);

            if(document.getDocumentHashMap().containsKey(keys.get(_position))){
                formatFields.put(keys.get(_position), (String) document.getDocumentHashMap().get(keys.get(_position)));
                int spinnerPosition = adapter.getPosition(keys.get(_position));
                holder.spinner_exp.setSelection(spinnerPosition);
            }

            holder.spinner_exp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(!holder.spinner_exp.getSelectedItem().toString().equals("null")){
                        formatFields.put(keys.get(_position), (String) document.getDocumentHashMap().get(holder.spinner_exp.getSelectedItem().toString()));
                    }else {
                        formatFields.remove(keys.get(_position));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    formatFields.remove(keys.get(_position));
                }
            });
        }
    }

    public HashMap<String, String> getFormatFields(){
        return formatFields;
    }

    @Override
    public int getItemCount() {
        return keys.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_Title;
        TextView TVFieldRequired;
        Spinner spinner_exp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_Title = (TextView) itemView.findViewById(R.id.tv_Title);
            TVFieldRequired = (TextView) itemView.findViewById(R.id.TVFieldRequired);
            spinner_exp = (Spinner) itemView.findViewById(R.id.spinner_exp);
        }
    }
}
