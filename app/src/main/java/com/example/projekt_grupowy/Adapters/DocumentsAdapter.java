package com.example.projekt_grupowy.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_grupowy.DocumentProperties;
import com.example.projekt_grupowy.MainActivity;
import com.example.projekt_grupowy.Models.Document;
import com.example.projekt_grupowy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class DocumentsAdapter  extends RecyclerView.Adapter<DocumentsAdapter.ViewHolder>{

    private static final String TAG = "DocumentsAdapter";
    ArrayList<Document> documents;

    Context context;
    AlertDialog.Builder builder;


    public DocumentsAdapter(Context context) {
        this.documents = MainActivity.appUser.getDocuments();
        Collections.sort(documents, (Document a1, Document a2) -> a1.getName().compareTo(a2.getName()));

        this.context = context;
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

            holder.tv_Title.setText(documents.get(position).getName());
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
                    //TODO
                }
            });

            holder.iv_delete_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setUpDialog("Warning", "Are you sure about deleting this document? \n" +
                             "Document name:" + documents.get(_position).getName(), _position);
                }
            });
        }
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
