package com.venpillar.testprogrammer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.venpillar.testprogrammer.model.PersonModel;
import com.venpillar.testprogrammer.util.Database;
import com.venpillar.testprogrammer.util.Helper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button newBtn, editBtn, deleteBtn, closeBtn, searchBtn;
    private EditText fromNameEt, toNameEt, fromAgeEt, toAgeEt, fromDateEt, toDateEt;
    private String selectedId = "", selectedName = "", selectedAge = "", selectedDate = "";
    private String id = "", name = "", age= "", date = "";
    private Database db;
    private TableLayout tableLayout;
    private LinearLayout tbody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if( item.getItemId() == R.id.github ){
            Intent intent = new Intent(MainActivity.this, GithubActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void init(){
        db = new Database(getApplicationContext());

        tbody = findViewById(R.id.tbody);

        Intent intent = new Intent(MainActivity.this, FormActivity.class);

        db.insertKaryawan("Impolana1","2022-02-12","28");
        db.insertKaryawan("Randy","2022-02-12","20");
        db.insertKaryawan("Alex","2022-02-12","21");
        db.insertKaryawan("Seiya","2022-02-12","23");

        fromNameEt = findViewById(R.id.fromName);
        toNameEt = findViewById(R.id.toName);

        fromAgeEt = findViewById(R.id.fromAge);
        toAgeEt = findViewById(R.id.toAge);

        fromDateEt = findViewById(R.id.fromDate);
        toDateEt = findViewById(R.id.toDate);
        Helper.initDateRange(MainActivity.this, fromDateEt, toDateEt,null);

        searchBtn = findViewById(R.id.search);
        searchBtn.setOnClickListener(v->{
            getData();
        });

        newBtn = findViewById(R.id.mmNew);
        newBtn.setOnClickListener(v->{
            intent.putExtra("mode", "add");
            startActivity(intent);
        });

        tableLayout = findViewById(R.id.table);

        editBtn = findViewById(R.id.mmEdit);
        editBtn.setOnClickListener(v->{
            intent.putExtra("mode", "edit");
            intent.putExtra("id", selectedId);
            intent.putExtra("name", selectedName);
            intent.putExtra("date", selectedDate);
            intent.putExtra("age", selectedAge);
            startActivity(intent);
        });

        deleteBtn = findViewById(R.id.mmDelete);

        deleteBtn.setOnClickListener(v->{
            delete();
        });

        closeBtn = findViewById(R.id.mmClose);
        closeBtn.setOnClickListener(v->{
            finish();
        });
    }

    private void delete(){
        Boolean checkudeletedata = db.deleteKaryawan(selectedId);

        if(checkudeletedata==true)
            Toast.makeText(MainActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivity.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();

        getData();
    }

    private void buildTextView(TextView textView, String position){
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

        switch (position){
            case "left":
                params.setMargins(0,1,0,0);
                break;
            case "center":
                params.setMargins(1, 1, 1,0);
                break;
            case "right":
                params.setMargins(0, 1, 0,0);
                break;
            case "bottom-left":
                params.setMargins(0,1, 0, 1);
                break;
            case "bottom-center":
                params.setMargins(1,1,1,1);
                break;
            default: //bottom right
                params.setMargins(0,1,0,1);
        }

//        textView.setGravity(Gravity.CENTER);
        textView.setPadding(25,25,25,25);
        textView.setLayoutParams(params);
    }

    private void selectData(String id, String name, String date, String age){
        selectedId = id;
        selectedName = name;
        selectedDate = date;
        selectedAge = age;
        int count = tableLayout.getChildCount();

        if(!id.equals("")) {
            editBtn.setEnabled(true);
            deleteBtn.setEnabled(true);
        }

        for(int i = 1; i < count ; i ++){
            tableLayout.getChildAt(i).findViewWithTag("impolana").setBackgroundColor(getResources().getColor(R.color.white));
            tableLayout.getChildAt(i).findViewWithTag("impolana1").setBackgroundColor(getResources().getColor(R.color.white));
            tableLayout.getChildAt(i).findViewWithTag("impolana2").setBackgroundColor(getResources().getColor(R.color.white));
            tableLayout.getChildAt(i).findViewWithTag("impolana3").setBackgroundColor(getResources().getColor(R.color.white));
        }

    }

    private void clearData(){
        selectedId = "";
        selectedName = "";
        selectedDate = "";
        selectedAge = "";
        tableLayout.removeViews(1, Math.max(0, tableLayout.getChildCount() - 1));
        editBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
    }

    private void getData() {
        clearData();


        String sFromName = fromNameEt.getText().toString();
        String sToName = toNameEt.getText().toString();
        String sFromAge = fromAgeEt.getText().toString();
        String sToAge = toAgeEt.getText().toString();
        String sFromDate = fromDateEt.getText().toString();
        String sToDate = toDateEt.getText().toString();

        Cursor res = db.getdata(sFromName,sToName, sFromAge, sToAge, sFromDate, sToDate);
        if (res.getCount() == 0) {
            Toast.makeText(MainActivity.this, "Tidak ada data", Toast.LENGTH_SHORT).show();
            return;
        }

        int x = 0;

        while (res.moveToNext()) {
            String id = res.getString(0);
            String name = res.getString(1);
            String date = res.getString(2);
            String age = res.getString(3);

            TableRow row = new TableRow(this);

            final String fid = id;
            final String fname = name;
            final String fdate = date;
            final String fage = age;

            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));

            TextView teId = new TextView(this);
            teId.setText(id);
            teId.setTag("impolana");

            TextView teName = new TextView(this);
            teName.setText(name);
            teName.setTag("impolana1");

            TextView teDate = new TextView(this);
            teDate.setText(date);
            teDate.setTag("impolana2");

            TextView teAge = new TextView(this);
            teAge.setText(age);
            teAge.setTag("impolana3");

            row.setOnClickListener(v->{
                selectData(fid, fname, fdate, fage);

                teId.setBackgroundColor(getResources().getColor(R.color.table_row));
                teName.setBackgroundColor(getResources().getColor(R.color.table_row));
                teDate.setBackgroundColor(getResources().getColor(R.color.table_row));
                teAge.setBackgroundColor(getResources().getColor(R.color.table_row));
            });

            if(x < res.getCount()){
                buildTextView(teId,"left");
                buildTextView(teName, "center");
                buildTextView(teDate, "center");
                buildTextView(teAge,"right");
            }
            else{
                buildTextView(teId,"bottom-left");
                buildTextView(teName,"bottom-center");
                buildTextView(teDate, "bottom-center");
                buildTextView(teAge,"bottom-right");
            }

            if( (x+1) % 2 == 0){
                teId.setBackgroundColor(getResources().getColor(R.color.table_row));
                teName.setBackgroundColor(getResources().getColor(R.color.table_row));
                teDate.setBackgroundColor(getResources().getColor(R.color.table_row));
                teAge.setBackgroundColor(getResources().getColor(R.color.table_row));
            }
            else{
                teId.setBackgroundColor(getResources().getColor(R.color.white));
                teName.setBackgroundColor(getResources().getColor(R.color.white));
                teDate.setBackgroundColor(getResources().getColor(R.color.white));
                teAge.setBackgroundColor(getResources().getColor(R.color.white));
            }

            row.addView(teId);
            row.addView(teName);
            row.addView(teDate);
            row.addView(teAge);
            tableLayout.addView(row);

        }
    }
}