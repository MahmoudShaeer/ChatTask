package com.example.mahmoudshaeer.chatus.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahmoudshaeer.chatus.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mahmoud Shaeer on 5/8/2017.
 */

public class ChatRoom extends AppCompatActivity {

    private  LinearLayout layout;
    private  ImageView sendButton;
    private   EditText messageArea;
    private  ScrollView scrollView;
    private    String Sender="";
    private   String Reacver="";
    private  String massegae,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        layout = (LinearLayout)findViewById(R.id.layout1);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);

         Sender=getIntent().getExtras().getString("Sender");
         Reacver=getIntent().getExtras().getString("Reciver");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chat With "+Reacver);

        final DatabaseReference   reference1 =FirebaseDatabase.getInstance()
                .getReference("message").child(Sender).child(Reacver);

        final DatabaseReference reference2 = FirebaseDatabase.getInstance().
                getReference("message").child(Reacver).child(Sender);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();
                if(!messageText.equals("")){
                    Map<String ,Object> map=new HashMap<String,Object>();
                    String key=reference1.push().getKey();
                    reference1.updateChildren(map);
                       DatabaseReference databaseReference= reference1.child(key);
                    Map<String,Object>map2=new HashMap<String,Object>();
                    map2.put("name",Sender);
                    map2.put("massege",messageText);
                    databaseReference.updateChildren(map2);

                    Map<String ,Object> mapp=new HashMap<String,Object>();
                    String keyy=reference2.push().getKey();
                    reference2.updateChildren(mapp);
                    DatabaseReference databaseReferencee= reference2.child(keyy);
                    databaseReferencee.updateChildren(map2);
                    messageArea.setText("");
                    Toast.makeText(ChatRoom.this, "Sended", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    messageArea.setError("Empty massage ...");
                }
            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Iterator i =dataSnapshot.getChildren().iterator();
                while(i.hasNext())
                {
                    massegae=(String)((DataSnapshot)i.next()).getValue();
                    name=(String)((DataSnapshot)i.next()).getValue();

                    if(Sender.equals(name)){
                        addMessageBox("You:-\n" + massegae, 1);
                    }
                    else{
                        addMessageBox(Reacver + ":-\n" + massegae, 2);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    //    return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
              finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addMessageBox(String message, int type){

        TextView textView = new TextView(ChatRoom.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 10);
        textView.setLayoutParams(lp);
        if(type == 1) {
            textView.setBackgroundResource(R.drawable.rounded_corner1);
        }
        else{
            textView.setBackgroundResource(R.drawable.rounded_corner2);
        }
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}
