//package com.nicholas.notesapp;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//
//import java.text.BreakIterator;
//
//// FirebaseRecyclerAdapter is a class provided by
//// FirebaseUI. it provides functions to bind, adapt and show
//// database contents in a Recycler View
//class notesAdapter extends FirebaseRecyclerAdapter<
//        NotesModel, notesViewholder> {
//   // private Context context;
//    //private List< Notes> notesList;
//
//    public notesAdapter(
//            @NonNull FirebaseRecyclerOptions<NotesModel> options)
//    {
//        super(options);
//    }
//
//    // Function to bind the view in Card view(here
//    // "person.xml") iwth data in
//    // model class(here "person.class")
//    @Override
//    protected void
//    onBindViewHolder(@NonNull notesViewholder holder,
//                     int position, @NonNull NotesModel model)
//    {
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.btnEdit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                       // holder.title.setText("Clicked");
//                        MainActivity mainActivity=new MainActivity();
//                    }
//                });
//
//
//            }
//        });
//        holder.title.setText(model.getTitle());
//        holder.description.setText(model.getDescription());
//
//
//
//    }
//
//    private void update() {
//
//    }
//
//
//
//    private void delete(){
//
//    }
//
//    // Function to tell the class about the Card view (here
//    // "item_notes.xml")in
//    // which the data will be shown
//    @NonNull
//    @Override
//    public notesViewholder
//    onCreateViewHolder(@NonNull ViewGroup parent,
//                       int viewType)
//    {
//        View view
//                = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.items_notes, parent, false);
//        return new notesViewholder(view);
//    }
//
//
//}
