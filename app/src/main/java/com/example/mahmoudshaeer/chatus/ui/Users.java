package com.example.mahmoudshaeer.chatus.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mahmoudshaeer.chatus.R;
import com.example.mahmoudshaeer.chatus.adaptes.UserAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by mahmoud Shaeer on 5/8/2017.
 */

public class Users extends AppCompatActivity {
    ListView usersList;
    String UserNAme="";
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<String>();
    int totalUsers = 0;
    ProgressDialog pd;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        usersList = (ListView)findViewById(R.id.usersList);
        noUsersText = (TextView)findViewById(R.id.noUsersText);
        UserNAme=getIntent().getExtras().getString("userName");
        doOnSuccess();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
     //   return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.log_out:
                Intent aboutIntent = new Intent(Users.this, Login.class);
                startActivity(aboutIntent);
                break;
            case R.id.exit:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    public void doOnSuccess(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    set.add(((DataSnapshot) i.next()).getKey());
                }
                al.clear();
                al.addAll(set);

              //  Toast.makeText(Users.this, ""+al.size(), Toast.LENGTH_SHORT).show();
                if(al.size()<=1){
                    noUsersText.setVisibility(View.VISIBLE);
                    usersList.setVisibility(View.GONE);
                }
                else{
                      noUsersText.setVisibility(View.GONE);
                    usersList.setVisibility(View.VISIBLE);
                }
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        UserAdapter userAdapter=new UserAdapter(this,al,UserNAme);
        usersList.setAdapter(userAdapter);
    }
}