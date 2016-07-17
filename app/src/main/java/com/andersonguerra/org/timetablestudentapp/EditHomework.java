package com.andersonguerra.org.timetablestudentapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditHomework extends AppCompatActivity {

    EditText nameTxtEdit, phoneTxtEdit, emailTxtEdit, addressTxtEdit;
    ImageView contactImageImgViewEdit;
    List<Contact> Contacts = new ArrayList<Contact>();
    ListView contactListView;
    Uri imageUri = Uri.parse("android.resource://com.andersonguerra.org/res/drawable/notification_icon.png");
    DatabaseHandler dbHandler;
    int longClickedItemIndex;
    ArrayAdapter<Contact> contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_homework);

        nameTxtEdit = (EditText) findViewById(R.id.txtNameEdit);
        phoneTxtEdit = (EditText) findViewById(R.id.txtPhoneEdit);
        emailTxtEdit = (EditText) findViewById(R.id.txtEmailEdit);
        addressTxtEdit = (EditText) findViewById(R.id.txtAddressEdit);
        contactListView = (ListView) findViewById(R.id.listView);
        contactImageImgViewEdit = (ImageView) findViewById(R.id.imgViewContactImageEdit);
        dbHandler = new DatabaseHandler(getApplicationContext());

        // añadiendo datos a los textview
        Intent intent = getIntent();
        final int idTarea = intent.getIntExtra("EXTRA_ID", 0);
        String nombreTarea = intent.getStringExtra("EXTRA_NOMBRE");
        nameTxtEdit.setText(nombreTarea);
        String descripcionTarea = intent.getStringExtra("EXTRA_DESCRIPCION");
        phoneTxtEdit.setText(descripcionTarea);
        String punteoTarea = intent.getStringExtra("EXTRA_PUNTEO");
        emailTxtEdit.setText(punteoTarea);
        String fechaTarea = intent.getStringExtra("EXTRA_FECHA");
        addressTxtEdit.setText(fechaTarea);

        final Button editBtn = (Button) findViewById(R.id.btnEdit);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact contact = new Contact(idTarea, String.valueOf(nameTxtEdit.getText()), String.valueOf(phoneTxtEdit.getText()), String.valueOf(emailTxtEdit.getText()), String.valueOf(addressTxtEdit.getText()), imageUri);
                if (!contactExists(contact)) {
                    dbHandler.updateContact(contact);
                    Contacts.add(contact);
                    Toast.makeText(getApplicationContext(), String.valueOf(nameTxtEdit.getText()) + " Tarea editada con exito!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditHomework.this, Homework.class);
                    startActivity(intent);
                    return;
                }
                Toast.makeText((getApplicationContext()), String.valueOf(nameTxtEdit.getText()) + " Tarea existente. Use un nombre diferente.", Toast.LENGTH_SHORT).show();
            }
        });

        nameTxtEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                editBtn.setEnabled(String.valueOf(nameTxtEdit.getText()).trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private boolean contactExists(Contact contact) {
        String name = contact.getName();
        int contactCount = Contacts.size();

        for (int i = 0; i < contactCount; i++) {
            if (name.compareToIgnoreCase(Contacts.get(i).getName()) == 0)
                return true;
        }
        return false;
    }
}
