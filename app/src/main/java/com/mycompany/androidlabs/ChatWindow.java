package com.mycompany.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

        //Initializing chat adapter items
        final ChatAdapter messageAdapter = new ChatAdapter(this);
        chatHistory = new ArrayList<String>();
        chatListView.setAdapter(messageAdapter);

        //Initialize the database helper and writeable database object
        ChatDatabaseHelper dbHelper = new ChatDatabaseHelper(getApplicationContext());
        final SQLiteDatabase dbWriteable = dbHelper.getWritableDatabase();

        //Cursors contain rows from a query. Used to iterate through the db.
        Cursor cursor = dbWriteable.rawQuery("SELECT * FROM MESSAGES", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String text = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE))   ;
            chatHistory.add(text);

            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) ) );
            Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount() );

            //Prints out the column name of each column returned by the cursor
            for(int i=0; i<cursor.getColumnCount(); i++)
                Log.i(ACTIVITY_NAME, cursor.getColumnName(i));

            cursor.moveToNext();
        }

        //Send button gets an onClickListener. Adds input from editText field to chatHistory array.
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = chatTextField.getText().toString();
                chatHistory.add(text);

                ContentValues cValues = new ContentValues();
                cValues.put(ChatDatabaseHelper.KEY_MESSAGE, text);
                dbWriteable.insert(ChatDatabaseHelper.TABLE_NAME, "null", cValues);

                messageAdapter.notifyDataSetChanged();//Restarts messageAdapter's getCount() and getView()
                chatTextField.setText("");

            }
        });

    }

    protected void onDestroy(){
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        super.onDestroy();
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
