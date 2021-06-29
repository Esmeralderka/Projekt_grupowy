package com.example.projekt_grupowy.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Filter;
import android.widget.Filterable;
import com.example.projekt_grupowy.DocumentExportPick;
import com.example.projekt_grupowy.DocumentProperties;
import com.example.projekt_grupowy.MainActivity;
import com.example.projekt_grupowy.Models.Document;
import com.example.projekt_grupowy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;

public class DocumentsAdapter  extends RecyclerView.Adapter<DocumentsAdapter.ViewHolder> implements Filterable {

    private static final String TAG = "DocumentsAdapter";
    ArrayList<Document> documents;
    ArrayList<Document> filteredDocuments;

    Context context;
    AlertDialog.Builder builder;


    public DocumentsAdapter(Context context,ArrayList<Document>document){
        this.documents =document;
        this.context = context;
        this.filteredDocuments=document;

        builder = new AlertDialog.Builder(context);

        Log.d("documents adapter docs:", MainActivity.appUser.toString());
    }

    @NonNull
    @Override
    public DocumentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_rv_documents, parent, false);

        return new DocumentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentsAdapter.ViewHolder holder, int position) {
        if(documents.get(position) != null )
        {
            int _position = position;

            holder.tv_Title.setText(documents.get(_position).getName());
            if(documents.get(_position).getName().length() > 15){
                String tempTitle = documents.get(_position).getName().substring(0,15);
                tempTitle+="...";
                holder.tv_Title.setText(tempTitle);
            }

            holder.tv_Title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DocumentProperties.documentPositionInUserDocumentsArrayList = _position;
                    Intent intent =  new Intent(v.getContext(), DocumentProperties.class);
                    v.getContext().startActivity(intent);
                }
            });

            holder.iv_export.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DocumentExportPick.documentPositionInUserDocumentsArrayList = _position;
                    Intent intent =  new Intent(v.getContext(), DocumentExportPick.class);
                    v.getContext().startActivity(intent);
                }
            });

            holder.iv_delete_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setUpDialog("Warning", "Are you sure about deleting this document? \n" +
                             "Document name: " + documents.get(_position).getName(), _position);
                }
            });
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key = constraint.toString();
                System.out.println("zawartosc Key "+Key);
                if(Key.isEmpty()){
                    documents=filteredDocuments;
                }else{
                    ArrayList<Document> lstFiltered=new ArrayList<>();
                    for(Document row:filteredDocuments){
                        if(row.getName().toLowerCase().contains(Key.toLowerCase())){
                            lstFiltered.add(row);
                        }
                    }
                    documents=lstFiltered;
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=documents;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                documents =(ArrayList<Document>)results.values;
                notifyDataSetChanged();
            }
        };
    }
    @Override
    public int getItemCount() {
        return documents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_Title;
        ImageView iv_export;
        ImageView iv_delete_2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_Title = (TextView) itemView.findViewById(R.id.tv_Title);
            iv_export = (ImageView) itemView.findViewById(R.id.iv_export);
            iv_delete_2 = (ImageView) itemView.findViewById(R.id.iv_delete_2);
        }
    }

    private void deleteDocument(int docPosition){

        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        CollectionReference ColRef = db.collection("users").document(MainActivity.appUser.getUID()).collection("documents");

        ColRef.document(documents.get(docPosition).getUID()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        documents.remove(docPosition);
                        notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    public void setUpDialog(String title, String msg, int _position)
    {
        builder.setTitle(title);
        builder.setMessage(msg);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                deleteDocument(_position);
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
}
