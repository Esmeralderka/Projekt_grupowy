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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_grupowy.AddDocumentActivity;
import com.example.projekt_grupowy.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class AddDocumentAdapter extends RecyclerView.Adapter<AddDocumentAdapter.ViewHolder> {

    Context context;
    ArrayList<Pair<String, String>> documentFields;
    private ArrayList<Pair<TextWatcher,TextInputEditText>> TextWatcherList = new ArrayList<>();
    AlertDialog.Builder builder;

    public AddDocumentAdapter(Context context)
    {
        this.context = context;
        builder = new AlertDialog.Builder(context);
    }

    public void setUpDialog(String title, String msg, int position)
    {
        builder.setTitle(title);
        builder.setMessage(msg);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                documentFields.remove(position);
                MyNotifyDataSetChanged();

                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }



    @NonNull
    @Override
    public AddDocumentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.add_document_item, parent, false);
        return new AddDocumentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddDocumentAdapter.ViewHolder holder, int position) {
        if (documentFields.get(position) != null) {
            //Pair<String, String> item = documentFields.get(position);

            TextWatcher TextWatcherNames;
            TextWatcher TextWatcherValues;

            holder.fieldName.setText(documentFields.get(position).first);
            holder.fieldValue.setText(documentFields.get(position).second);



            holder.deleteField.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("usuwanie pola na pozycji " , Integer.toString(position));
                    //documentFields.add(new Pair("", ""));
                    setUpDialog("Warning", "Are you sure about deleting this field? \n" +
                            "Field type: " + documentFields.get(position).first,position);
                }
            });


            //holder.fieldName.addTextChangedListener(new TextWatcher() {
            TextWatcherNames = (new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    documentFields.set(position, new Pair(s.toString(), documentFields.get(position).second));

                    Log.d("para w listenerze: dla pozycji = ", Integer.toString(position) + " " +s.toString() + " " + documentFields.get(position).second);
                }
            });

            //holder.fieldValue.addTextChangedListener(new TextWatcher() {
            TextWatcherValues = (new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    documentFields.set(position, new Pair(documentFields.get(position).first, s.toString()));

                    Log.d("para w listenerze: dla pozycji = ",Integer.toString(position) +" "+ documentFields.get(position).first + " " + s.toString());
                }
            });



            setListener(TextWatcherNames,holder.fieldName);
            setListener(TextWatcherValues,holder.fieldValue);
        }
    }

    void MyNotifyDataSetChanged()
    {
        removeListeners();
        notifyDataSetChanged();
    }

    private void setListener(TextWatcher listener, TextInputEditText editText) {
        editText.addTextChangedListener(listener);
        TextWatcherList.add(new Pair(listener,editText));
    }

    private void removeListeners() {
        for (Pair<TextWatcher,TextInputEditText> t : TextWatcherList)
            t.second.removeTextChangedListener(t.first);

        TextWatcherList.clear();
    }



    @Override
    public int getItemCount() {
        return documentFields.size();
    }

    public ArrayList<Pair<String, String>> getDocumentFields() {
        return documentFields;
    }

    public void addNewField() {
        documentFields.add(new Pair("", ""));

        MyNotifyDataSetChanged();
    }

    public void initializeArray() {
        documentFields = new ArrayList<Pair<String, String>>();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextInputEditText fieldName;
        TextInputEditText fieldValue;
        ImageView deleteField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fieldName = (TextInputEditText) itemView.findViewById(R.id.fieldName);
            fieldValue = (TextInputEditText) itemView.findViewById(R.id.fieldValue);
            deleteField = (ImageView) itemView.findViewById(R.id.deleteField);
        }
    }


}
