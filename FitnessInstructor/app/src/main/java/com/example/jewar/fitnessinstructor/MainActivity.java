package com.example.jewar.fitnessinstructor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Declare variables
    EditText NameTxt, EmailTxt, PhoneTxt, AddressTxt;
    List<User> Users = new ArrayList<User>();
    ListView userListView;
    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set view
        setContentView(R.layout.activity_main);
        try {
            // Create RSS reader
            RssReader rssReader = new RssReader("http://twitrss.me/twitter_user_to_rss/?user=TheGymGroup");
            // Get a ListView from m ain view
            ListView itcItems = (ListView) findViewById(R.id.RsslistView);
            // Create a list adapter
            ArrayAdapter adapter = new ArrayAdapter<RssItem>(this, android.R.layout.simple_list_item_1, rssReader.getItems());
            // Set list adapter for the ListView
            itcItems.setAdapter(adapter);
            // Set list view item click listener
            itcItems.setOnItemClickListener(new ListListener(rssReader.getItems(), this));
        } catch (Exception e) {
            Log.e("ITCRssReader", e.getMessage());
        }


            //assign variables
            NameTxt = (EditText) findViewById(R.id.NameTxt);
            EmailTxt = (EditText) findViewById(R.id.EmailTxt);
            PhoneTxt = (EditText) findViewById(R.id.PhoneTxt);
            AddressTxt = (EditText) findViewById(R.id.AddressTxt);
            TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
            userListView = (ListView) findViewById(R.id.listView);
            dbHandler = new DatabaseHandler(getApplicationContext());

            //setup tab
            tabHost.setup();

            TabHost.TabSpec tabSpec = tabHost.newTabSpec("User Creator");
            tabSpec.setContent(R.id.TabCreator);
            tabSpec.setIndicator("Creator");
            tabHost.addTab(tabSpec);

            tabSpec = tabHost.newTabSpec("Contact List");
            tabSpec.setContent(R.id.TabContactStore);
            tabSpec.setIndicator("List");
            tabHost.addTab(tabSpec);

            final Button addBtn = (Button) findViewById(R.id.BtnAdd);

            //message for user creation
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    User user = new User(dbHandler.getUserCount(), String.valueOf(NameTxt.getText()), String.valueOf(EmailTxt.getText()), String.valueOf(PhoneTxt.getText()), String.valueOf(AddressTxt.getText()));
                    dbHandler.createUser(user);
                    Users.add(user);
                    populateList();
                    Toast.makeText(getApplicationContext(), NameTxt.getText().toString() + " has been added to users list ", Toast.LENGTH_SHORT).show();
                }
            });

            NameTxt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    addBtn.setEnabled(NameTxt.getText().toString().trim().length() > 0);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

    //add contacts to the list
            List<User> addableUsers = dbHandler.getAllUsers();
            int userCount = dbHandler.getUserCount();

            for (int i = 0; i < userCount; i++) {
                Users.add(addableUsers.get(i));
            }

            if (addableUsers.isEmpty())
                populateList();

        }

    private void populateList(){
        ArrayAdapter<User> adapter = new UserListAdapter();
        userListView.setAdapter(adapter);
    }

    private void addUser(int id,String name, String email, String phone, String address){
        Users.add(new User(id,name, email, phone, address));
    }

    private class UserListAdapter extends ArrayAdapter<User> {
        public UserListAdapter(){
            super(MainActivity.this, R.layout.listview_item, Users);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){
            if (view == null )
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);

            //get elements from listview
            User  currentUser = Users.get(position);

            TextView name = (TextView) view.findViewById(R.id.UserName);
            name.setText(currentUser.getName());
            TextView email = (TextView) view.findViewById(R.id.Email);
            email.setText(currentUser.getEmail());
            TextView phone = (TextView) view.findViewById(R.id.PhoneNumber);
            phone.setText(currentUser.getPhone());
            TextView address = (TextView) view.findViewById(R.id.Address);
            address.setText(currentUser.getAddress());

            return view;
        }
    }
}
