package com.venpillar.testprogrammer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.venpillar.testprogrammer.util.Database;
import com.venpillar.testprogrammer.util.Helper;

public class FormActivity extends AppCompatActivity {
    private EditText etId, etName, etAge, etDate;
    private String mode;
    private Button saveBtn;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void init(){
        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etDate = findViewById(R.id.etDate);
        saveBtn = findViewById(R.id.savebtn);
        etId.setEnabled(false);

        Helper.initDate(FormActivity.this, etDate,null);

        db = new Database(getApplicationContext());
        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");

        String id;
        String name;
        String age;
        String date;

        if(mode.equals("edit")){
            etId.setEnabled(false);
            id = intent.getStringExtra("id");
            etId.setText(id);

            name = intent.getStringExtra("name");
            etName.setText(name);

            age = intent.getStringExtra("age");
            etAge.setText(age);

            date = intent.getStringExtra("date");
            etDate.setText(date);
        }


        saveBtn.setOnClickListener(v->{
            if(mode.equals("add")){
                add(etName.getText().toString(), etDate.getText().toString(), etAge.getText().toString());
            }
            else{
                update(etId.getText().toString(), etName.getText().toString(), etDate.getText().toString(), etAge.getText().toString());
            }

        });
    }

    private void add(String name, String age, String date){

        Boolean checkinsertdata = db.insertKaryawan(name, age, date);

        if(checkinsertdata)
            Toast.makeText(FormActivity.this, "Berhasil di insert", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(FormActivity.this, "Gagal insert", Toast.LENGTH_SHORT).show();
    }

    private void update(String id, String name, String age, String date){
        Boolean checkupdatedata = db.updateKaryawan(id, name, age, date);

        if(checkupdatedata==true)
            Toast.makeText(FormActivity.this, "Entry Updated", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(FormActivity.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show();
    }
}