package com.example.mahmoudshaeer.chatus.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahmoudshaeer.chatus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by mahmoud Shaeer on 5/8/2017.
 */
public class Login extends AppCompatActivity {
    TextView register;
    EditText username, password;
    Button loginButton;
    String user, pass;
    List<String> allUsers;

   private DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        register = (TextView) findViewById(R.id.register);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButton);

        allUsers = new ArrayList<String>();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString()=="")
                    username.setError("write Your name");
                if(password.getText().toString().length()<5)
                    password.setError("Password shoud more than 5 char");
                else {
                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Set<String> set = new HashSet<String>();
                            Iterator i = dataSnapshot.getChildren().iterator();
                            while (i.hasNext()) {
                                set.add(((DataSnapshot) i.next()).getKey());
                            }
                            allUsers.clear();
                            allUsers.addAll(set);
                            Toast.makeText(Login.this, "" + allUsers.size(), Toast.LENGTH_SHORT).show();
                            if (allUsers.contains(username.getText().toString())) {
                                Intent intent = new Intent(Login.this, Users.class);
                                intent.putExtra("userName",username.getText().toString());
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(Login.this, "please Registration... ", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

    }
}
