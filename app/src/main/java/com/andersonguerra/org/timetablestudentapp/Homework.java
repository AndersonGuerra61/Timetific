package com.andersonguerra.org.timetablestudentapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Homework extends AppCompatActivity{

    private static final int EDIT = 0, DELETE = 1;
    EditText nameTxt, phoneTxt, emailTxt, addressTxt;
    ImageView contactImageImgView;
    List<Contact> Contacts = new ArrayList<Contact>();
    ListView contactListView;
    Uri imageUri = Uri.parse("android.resource://com.andersonguerra.org/res/drawable/notification_icon.png");
    DatabaseHandler dbHandler;
    int longClickedItemIndex;
    ArrayAdapter<Contact> contactAdapter;

    //para enviar al intent de editar
    public static final int EXTRA_ID = 0;
    public static final String EXTRA_NOMBRE = "nombreTarea";
    public static final String EXTRA_DESCRIPCION = "descripcion";
    public static final String EXTRA_PUNTEO = "100";
    public static final String EXTRA_FECHA = "04/07/2016";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        nameTxt = (EditText) findViewById(R.id.txtName);
        phoneTxt = (EditText) findViewById(R.id.txtPhone);
        emailTxt = (EditText) findViewById(R.id.txtEmail);
        addressTxt = (EditText) findViewById(R.id.txtAddress);
        contactListView = (ListView) findViewById(R.id.listView);
        contactImageImgView = (ImageView) findViewById(R.id.imgViewContactImage);
        dbHandler = new DatabaseHandler(getApplicationContext());

        registerForContextMenu(contactListView);

        contactListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longClickedItemIndex = position;
                return false;
            }
        });

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("creador");
        tabSpec.setContent(R.id.tabCreator);
        tabSpec.setIndicator("Creador");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tareas");
        tabSpec.setContent(R.id.tabContactList);
        tabSpec.setIndicator("Tareas");
        tabHost.addTab(tabSpec);

        // al presional el boton añadir
        final Button addBtn = (Button) findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact contact = new Contact(dbHandler.getContactsCount(), String.valueOf(nameTxt.getText()), String.valueOf(phoneTxt.getText()), String.valueOf(emailTxt.getText()), String.valueOf(addressTxt.getText()), imageUri);
                if (!contactExists(contact)) {
                    dbHandler.createContact(contact);
                    Contacts.add(contact);
                    contactAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), String.valueOf(nameTxt.getText()) + " Tarea guardada con exito!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText((getApplicationContext()), String.valueOf(nameTxt.getText()) + " Tarea existente. Use un nombre diferente.", Toast.LENGTH_SHORT).show();
            }
        });

        nameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                addBtn.setEnabled(String.valueOf(nameTxt.getText()).trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        contactImageImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Seleccionar Imagen de Tarea"), 1);
            }
        });

        if (dbHandler.getContactsCount() !=0)
            Contacts.addAll(dbHandler.getAllContacts());
        populateList();

        contactListView.setClickable(true);
        contactListView.setAdapter(contactAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_homework, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*aqui se crea el menu contextual se accede a el
    manteniendo presionado el elemento de la lista*/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        menu.setHeaderIcon(R.drawable.edit_icon);
        menu.setHeaderTitle("Opciones de Tareas");
        menu.add(Menu.NONE, EDIT, menu.NONE, "Editar Tarea");
        menu.add(Menu.NONE, DELETE, menu.NONE,"Eliminar Tarea");
    }

    //al presionar un registro se elige entre editarlo o eliminarlo
    @Override
    public boolean onContextItemSelected (MenuItem item) {
        System.out.println("si esta entrando");
        switch (item.getItemId()) {
            case EDIT:
                //TODO: Aqui tengo que hacer el editar
                System.out.println("esta es la tarea numero: " + Contacts.get(longClickedItemIndex).getId());
                Intent intent = new Intent(Homework.this, EditHomework.class);
                //con este for recorro la lista y encuentro el id del que deseo editar
                for (Contact contactFor: Contacts) {
                    if (contactFor.getId() == Contacts.get(longClickedItemIndex).getId()){
                        // le mando la informacion a la otra actividad
                        intent.putExtra("EXTRA_ID", contactFor.getId());
                        intent.putExtra("EXTRA_NOMBRE", contactFor.getName());
                        intent.putExtra("EXTRA_DESCRIPCION", contactFor.getPhone());
                        intent.putExtra("EXTRA_PUNTEO", contactFor.getEmail());
                        intent.putExtra("EXTRA_FECHA", contactFor.getAddress());
                        System.out.println("el id de la tarea es: " + contactFor.getId());
                        System.out.println("el nombre de la tarea es: " + contactFor.getName());
                        System.out.println("la descripcion de la tarea es: " + contactFor.getPhone());
                        System.out.println("el punteo de la tarea es: " + contactFor.getEmail());
                        System.out.println("la fecha de la tarea es: " + contactFor.getAddress());
                    }
                }
                startActivity(intent);
                break;
            case DELETE:
                dbHandler.deleteContact(Contacts.get(longClickedItemIndex));
                Contacts.remove(longClickedItemIndex);
                contactAdapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }

    // revisa si el contacto(tarea exista)
    private boolean contactExists(Contact contact) {
        String name = contact.getName();
        int contactCount = Contacts.size();

        for (int i = 0; i < contactCount; i++) {
            if (name.compareToIgnoreCase(Contacts.get(i).getName()) == 0)
                return true;
        }
        return false;
    }

    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == RESULT_OK) {
            if (reqCode == 1) {
                imageUri = data.getData();
                contactImageImgView.setImageURI(data.getData());
            }
        }
    }

    private void populateList() {
        contactAdapter = new ContactListAdapter();
        contactListView.setAdapter(contactAdapter);
    }

    private class ContactListAdapter extends ArrayAdapter<Contact> {
        public ContactListAdapter(){
            super (Homework.this, R.layout.listview_item, Contacts);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item,parent,false);

            Contact currentContact = Contacts.get(position);

            TextView name = (TextView) view.findViewById(R.id.contactName);
            name.setText(currentContact.getName());
            TextView phone =(TextView) view.findViewById(R.id.phoneNumber);
            phone.setText(currentContact.getPhone());
            TextView email = (TextView) view.findViewById(R.id.emailAddress);
            email.setText(currentContact.getEmail());
            TextView address = (TextView) view.findViewById(R.id.cAddress);
            address.setText(currentContact.getAddress());
            ImageView ivContactImage = (ImageView) view.findViewById(R.id.ivContactImage);
            ivContactImage.setImageURI (currentContact.getImageURI());

            return view;
        }
    }
}
