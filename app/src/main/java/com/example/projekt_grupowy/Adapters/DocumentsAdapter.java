package com.example.projekt_grupowy.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

import java.util.ArrayList;

public class DocumentsAdapter  extends RecyclerView.Adapter<DocumentsAdapter.ViewHolder>{

    ArrayList<Document> documents;

    public DocumentsAdapter() {
        this.documents = MainActivity.appUser.getDocuments();

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
                    //TODO
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


}
