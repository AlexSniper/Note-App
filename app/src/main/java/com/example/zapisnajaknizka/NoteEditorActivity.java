package com.example.zapisnajaknizka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {
     int noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        EditText editText = findViewById(R.id.editText);
        Intent intent = getIntent();
     noteId = intent.getIntExtra("noteId", -1);// -1 because number which could be passed in could be as low as zero
        if (noteId != -1 ){
            editText.setText(MainActivity.notes.get(noteId));
        }
        else {
           MainActivity.notes.add("");
           noteId = MainActivity.notes.size() -1;
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(noteId, String.valueOf(s));// If someone did updates this code will perform updates inside of array
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.zapisnajaknizka", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.notes);// Here we converting arrayList into a hash set , and they HashSet is saved in shared preferences
           sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
