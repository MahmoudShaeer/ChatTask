package com.example.mahmoudshaeer.chatus.adaptes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mahmoudshaeer.chatus.ui.ChatRoom;
import com.example.mahmoudshaeer.chatus.R;

import java.util.ArrayList;

/**
 * Created by mahmoud Shaeer on 5/9/2017.
 */

public class UserAdapter extends ArrayAdapter<String> {

    ArrayList<String>arr;
    String userName;
    Context context;
    public UserAdapter(@NonNull Context context,
                       @NonNull ArrayList<String> objects ,
                       String Username) {
        super(context, 0, objects);
        arr=objects;
        this.context=context;
        userName=Username;
    }

        @NonNull
        @Override
    public View getView (final int position, @Nullable View convertView, @NonNull ViewGroup parent){

            String item = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_user, parent, false);
            }
            TextView UserName=(TextView)convertView.findViewById(R.id.user_friend_name);
            UserName.setText(item);
            ImageView clicked=(ImageView)convertView.findViewById(R.id.start_convarstaion);
            clicked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ChatRoom.class);
                    intent.putExtra("Reciver",arr.get(position));
                    intent.putExtra("Sender",userName);
                   context.startActivity(intent);
                }
            });
            return convertView;
        }
    }
