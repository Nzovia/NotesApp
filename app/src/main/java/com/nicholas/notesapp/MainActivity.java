package com.nicholas.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton floatbtn;
    private FirebaseAuth mauth;
    //create an object for the adapter class
    //create an object for the database reference
    DatabaseReference mydb;
    private  String Editedtitle;
    private String Editednote;
    private  String pkey;
    private FirebaseRecyclerAdapter<NotesModel,notesViewholder> adapter1;
    FirebaseRecyclerOptions<NotesModel> options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create an instance of the databse and get its reference
        //mydb= FirebaseDatabase.getInstance().getReference();
        mauth = FirebaseAuth.getInstance();
        mydb = FirebaseDatabase.getInstance().getReference().child("notes").child( mauth.getCurrentUser().getUid());
        //create an hook for the recyclerview
        recyclerView = findViewById(R.id.recycler);
        floatbtn= findViewById(R.id.sharenote);

        floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogNotes();
            }
        });

        //set a code to display recyclerview linearly
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        options = new FirebaseRecyclerOptions.Builder<NotesModel>().setQuery(mydb,NotesModel.class).build();
        adapter1 = new FirebaseRecyclerAdapter<NotesModel, notesViewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull notesViewholder holder, int position, @NonNull NotesModel model) {
                holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showUpdateDeleteDialog(model.getId(),model.getTitle(),model.getDescription());

                    }
                });
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mydb.child(model.getId()).removeValue();
                        Toast.makeText(getApplicationContext(),"note deleted",Toast.LENGTH_LONG).show();
                    }
                });
                holder.title.setText(model.getTitle());
                holder.description.setText(model.getDescription());

            }

            @NonNull
            @Override
            public notesViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_notes,parent,false);
                return  new notesViewholder(view);
            }
        };
        adapter1.startListening();
        recyclerView.setAdapter(adapter1);
        //this is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
//        FirebaseRecyclerOptions<NotesModel> options
//                = new FirebaseRecyclerOptions.Builder<NotesModel>()
//                .setQuery(mydb, NotesModel.class)
//                .build();


    }
    private  void fetch()
    {


    }
    //addnotes using the dialog box
    private void showDialogNotes() {
        AlertDialog.Builder mydialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater layoutInflater=LayoutInflater.from(MainActivity.this);
        View view=layoutInflater.inflate(R.layout.items_dialog, null);
        final AlertDialog dialog=mydialog.create();
        dialog.setView(view);

        Button addbtn=view.findViewById(R.id.add_btn);
        EditText editText=view.findViewById(R.id.notestitle);
        EditText editText1=view.findViewById(R.id.editnotes);

        //code for closing the dialog once the close button is clicked
        ImageButton imageButton=view.findViewById(R.id.close);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=mydb.push().getKey();
                String title=editText.getText().toString();
                String description = editText1.getText().toString();
                //first check the notes area is empty and if so then throew an error
                if(TextUtils.isEmpty(description)){
                    editText1.setError("Can not add an empty note");
                }else if(TextUtils.isEmpty(title)){
                    editText.setError("add note title");
                }

                else{
                    NotesModel notes=new NotesModel(id,title,description);
                    mydb.child(id).setValue(notes).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "note successfully added", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }

                    });
                }

            }
        });
        dialog.show();
    }
    //the followng function tells the app to start fetching data from the database

    @Override
    protected void onStart() {
        super.onStart();
      //  adapter.startListening();
    }
    // Function to tell the app to stop getting
    // data from database on stoping of the activity


    @Override
    protected void onStop() {
        super.onStop();
       // adapter.stopListening();
    }

    //create a method that will be called to enhance deletion
    private boolean updateNotes(String id,String title,String description){
        //get a specified database reference
        DatabaseReference mydb=FirebaseDatabase.getInstance().getReference("Notes").child(id);

        //updating list
        NotesModel notes=new NotesModel (id, title, description);
        mydb.setValue(notes);
        Toast.makeText(getApplicationContext(),"note updated", Toast.LENGTH_SHORT).show();
        return true;
    }
    //defining the deletion method
    private boolean deleteNote(String id){
        DatabaseReference db=FirebaseDatabase.getInstance().getReference("Notes").child(id);
        db.removeValue();

        Toast.makeText(getApplicationContext(), "note deleted", Toast.LENGTH_LONG).show();

        return true;


    }
    public void showUpdateDeleteDialog(final  String id,String title, String description){
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        final View dialogView=inflater.inflate(R.layout.items_dialog,null);
        dialogBuilder.setView(dialogView);

        final EditText noteTitle= dialogView.findViewById(R.id.notestitle);
        final EditText noteDescription= dialogView.findViewById(R.id.editnotes);
        final Button update=dialogView.findViewById(R.id.add_btn);
        final ImageButton close=dialogView.findViewById(R.id.close);
        noteTitle.setText(title);
        noteDescription.setText(description);
        //dialogBuilder.setTitle(title);
        //dialogBuilder.setTitle(description);
        final AlertDialog b=dialogBuilder.create();
        b.show();

        update.setText("UPDATE");
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_SHORT).show();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();

            }
        });
//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String title=noteTitle.getText().toString().trim();
//                String description=noteDescription.getText().toString().trim();
//
//                if(!TextUtils.isEmpty(title)){
//                    updateNotes(id,title,description);
//                    b.dismiss();
//                }
//            }
//        });


    }
}