package com.nicholas.notesapp;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Sub Class to create references of the views in Crad
// view (here "person.xml")
public class notesViewholder extends RecyclerView.ViewHolder {
    TextView title, description;
    public ImageButton btnDelete,btnEdit;
    public notesViewholder(@NonNull View itemView)
    {
        super(itemView);

        title = itemView.findViewById(R.id.notetitle);
        description = itemView.findViewById(R.id.notesdescription);
        btnDelete=itemView.findViewById(R.id.deletebtn);
        btnEdit=itemView.findViewById(R.id.updatebtn);
        
    }
}