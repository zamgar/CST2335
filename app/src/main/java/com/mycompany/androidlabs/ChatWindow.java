package com.mycompany.androidlabs;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "ChatWindowActivity";

    ListView chatListView;
    EditText chatTextField;
    Button sendButton;
    ArrayList<String> chatHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        //Initializing chat window items
        chatListView = (ListView)findViewById(R.id.chatWindowActivity_listView);
        chatTextField = (EditText)findViewById(R.id.chatWindowActivity_editTextField);
        sendButton = (Button)findViewById(R.id.chatWindowActivity_sendButton);
        chatHistory = new ArrayList<String>();

        //Initializing
        final ChatAdapter messageAdapter = new ChatAdapter(this);
        chatListView.setAdapter(messageAdapter);

        //Send button gets an onClickListener. Adds input from editText field to chatHistory array.
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatHistory.add(chatTextField.getText().toString());
                messageAdapter.notifyDataSetChanged();//Restarts getCount() and getView()
            }
        });

    }


    //Inner class ChatAdapter. Instructions from Lab 4 followed here.
    public class ChatAdapter extends ArrayAdapter<String>{

        public ChatAdapter(Context ctx){
            super(ctx, 0);
        }

        public int getCount(){
            return chatHistory.size();
        }

        public String getItem(int position){
            return chatHistory.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;
            if(position % 2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }
    }

}//End of class
