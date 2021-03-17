package com.nicholas.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private TextView banner,registerU;
    private Button register;
    private EditText editTextName,editTextEmail,editTextPhonenumber,editTextPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth here
        mAuth = FirebaseAuth.getInstance();
        banner=(TextView)findViewById(R.id.banner);
        banner.setOnClickListener(this);

        register=(Button)findViewById(R.id.register);
        // registerU=(Button)findViewById(R.id.registerU);
        register.setOnClickListener(this);
        editTextName=(EditText)findViewById(R.id.name);
        editTextEmail=(EditText)findViewById(R.id.email);
        editTextPhonenumber=(EditText)findViewById(R.id.phone);
        editTextPassword=(EditText)findViewById(R.id.password);

        progressBar=(ProgressBar)findViewById(R.id.progressbar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.banner:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.register:
                register();
                break;
            //generate register user method
        }
    }

    private void register() {
        //here create a view string variables where the input is conversted into a string
        String name=editTextName.getText().toString().trim();
        String email=editTextEmail.getText().toString().trim();
        String phone=editTextPhonenumber.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();

        //do some validations for the edittext
        if(name.isEmpty()){
            editTextName.setError("full_name required");
            editTextName.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("email required");
            editTextEmail.requestFocus();
            return;
        }
        if(phone.isEmpty()){
            editTextPhonenumber.setError("phone number required");
            editTextPhonenumber.requestFocus();
            return;
        }
        if (password.isEmpty()){
            editTextPassword.setError("fill password");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            editTextPassword.setError("min password characters should be six");
            editTextPassword.requestFocus();
            return;
        }
        //set the visibility of the progresssbar
        progressBar.setVisibility(View.VISIBLE);

        //here call our mauth object for firebase authentication
        mAuth.createUserWithEmailAndPassword(email,password)
                //check if the user has been registered ,,,by adding on complete listener and
                // then implement onComplete listener auth result
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //check if the task has been completed, if the user has been registered
                        if(task.isSuccessful()){
                            //create user object
                            UserData userData=new UserData(name, email,phone);
                            //send the user object to realtime database(firebase)

                            //call firebase database object
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this,"registration successful",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(RegisterActivity.this,"registration faled",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }

                                }
                            });

                        }else{
                            Toast.makeText(RegisterActivity.this,"please Try again",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });
    }
}