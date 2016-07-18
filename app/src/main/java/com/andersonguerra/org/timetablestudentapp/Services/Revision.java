package com.andersonguerra.org.timetablestudentapp.Services;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.andersonguerra.org.timetablestudentapp.Models.Contact1;
import com.andersonguerra.org.timetablestudentapp.R;

import java.util.ArrayList;
import java.util.List;

public class Revision extends AppCompatActivity {

    EditText nameTxt1, phoneTxt1, emailTxt1, addressTxt1;
    List<Contact1> Contacts = new ArrayList<Contact1>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision);

        nameTxt1 = (EditText) findViewById(R.id.txtName1);
        phoneTxt1 = (EditText)findViewById(R.id.txtPhone1);
        emailTxt1 = (EditText) findViewById(R.id.txtEmail1);
        addressTxt1 = (EditText) findViewById(R.id.txtAddress1);
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost1);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("creator");
        tabSpec.setContent(R.id.tabCreator1);
        tabSpec.setIndicator("Creator");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("list");
        tabSpec.setContent(R.id.tabContactList1);
        tabSpec.setIndicator("Revision");
        tabHost.addTab(tabSpec);

        final Button addBtn = (Button) findViewById(R.id.btnAdd1);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact1(nameTxt1.getText().toString(), phoneTxt1.getText().toString(), emailTxt1.getText().toString(), addressTxt1.getText().toString());
                Toast.makeText(getApplicationContext(), nameTxt1.getText().toString() + "Your Revision has been set!", Toast.LENGTH_SHORT).show();
            }
        });

        nameTxt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                addBtn.setEnabled(!nameTxt1.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void addContact1(String name, String phone, String email, String address) {
        Contacts.add(new Contact1(name, phone, email, address));
    }

    private class ContactListAdapter extends ArrayAdapter<Contact1> {
        public ContactListAdapter() {
            super(Revision.this, R.layout.listview_item1, Contacts);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item1, parent, false);

            Contact1 currentContact = Contacts.get(position);

            TextView name = (TextView) view.findViewById(R.id.contactName1);
            name.setText(currentContact.getName1());
            TextView phone = (TextView) view.findViewById(R.id.phoneNumber1);
            phone.setText(currentContact.get_phone1());
            TextView email = (TextView) view.findViewById(R.id.emailAddress1);
            email.setText(currentContact.get_email1());
            TextView address = (TextView) view.findViewById(R.id.cAddress1);
            address.setText(currentContact.get_address1());

            return view;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_revision, menu);
        return true;
    }
}
