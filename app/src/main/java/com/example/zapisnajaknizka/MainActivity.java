package com.example.zapisnajaknizka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
  static   ArrayList<String> notes = new ArrayList<>();
  static   ArrayAdapter arrayAdapter;

    SharedPreferences sharedPreferences;//Here
    //  SharedPreferences is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.zapisnajaknizka", Context.MODE_PRIVATE);// Here we are getting
        // access to shared preferences
        ListView listView  = findViewById(R.id.listView);
        HashSet<String>set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);


        if (set == null){
            notes.add("Dobavte zametku sdes");
        }
        else notes = new ArrayList(set);// it will create new arrayList from information in that set
        notes.add("Dobavte zametku sdes");
        arrayAdapter = new ArrayAdapter( this, android.R.layout.simple_list_item_1, notes);// simple_list_item_1
        // gives simple Layout  , notes is specifies with which array we are working with
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
        Intent intent = new Intent (getApplicationContext(), NoteEditorActivity.class);
        intent .putExtra("noteId", i);
        startActivity(intent);
    }
});

listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


        final int itemToDelete = position;

       new  AlertDialog. Builder(MainActivity.this)
               .setIcon(android.R.drawable.ic_dialog_alert)
       .setTitle("Are you sure?")
               .setMessage("Do you want to delete this note")
               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int i) {

                       notes.remove(itemToDelete);// removing as item from arrayList
                     arrayAdapter.notifyDataSetChanged();//Notifying arrayAdapter that item was deleted

                       HashSet<String> set = new HashSet<>(MainActivity.notes);// Here we converting arrayList into a HashSet , and then HashSet is saved in shared preferences
                       sharedPreferences.edit().putStringSet("notes", set).apply();

                   }
               })
               .setNegativeButton("No", null)
              .show();
        return true;
    }
});
    }

    //Menu code

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.add_note)
        {
            Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
            startActivity(intent);
          return true;
        }return false;
    }
}
