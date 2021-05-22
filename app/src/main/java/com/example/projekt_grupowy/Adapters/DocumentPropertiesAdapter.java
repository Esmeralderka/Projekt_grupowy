package com.example.projekt_grupowy.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_grupowy.DocumentProperties;
import com.example.projekt_grupowy.R;

import java.util.ArrayList;

public class DocumentPropertiesAdapter extends RecyclerView.Adapter<DocumentPropertiesAdapter.ViewHolder>{

    ArrayList<String> fieldNames;
    ArrayList <String> fieldContent;

    public DocumentPropertiesAdapter(ArrayList<String> fieldNames, ArrayList<String> fieldContent) {
        this.fieldNames = fieldNames;
        this.fieldContent = fieldContent;
    }

    @NonNull
    @Override
    public DocumentPropertiesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_rv_doc_prop, parent, false);

        return new DocumentPropertiesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentPropertiesAdapter.ViewHolder holder, int position) {
        if(fieldNames.get(position) != null )
        {
            int _position = position;

            holder.tv_field_name.setText(fieldNames.get(position));
            holder.tv_field_content.setText(fieldContent.get(position));

            holder.iv_delete_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
                }
            });
        }
    }

    @Override
    public int getItemCount() { return fieldNames.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_field_name;
        TextView tv_field_content;
        ImageView iv_delete_2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_field_name = (TextView) itemView.findViewById(R.id.tv_field_name);
            tv_field_content = (TextView) itemView.findViewById(R.id.tv_field_content);
            iv_delete_2 = (ImageView) itemView.findViewById(R.id.iv_delete_2);
        }
    }

}
