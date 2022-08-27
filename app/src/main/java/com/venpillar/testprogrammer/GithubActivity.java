package com.venpillar.testprogrammer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.venpillar.testprogrammer.model.PersonModel;

import java.util.List;

public class GithubActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    GithubAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        getData();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void init(){
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
    }

    private void getData(){
        progressBar.setVisibility(View.GONE);
        Call<List<PersonModel>> call = Api.getInstance(getApplicationContext()).getApiGithub().getPeople();
        call.enqueue(new Callback<List<PersonModel>>() {
            @Override
            public void onResponse(Call<List<PersonModel>> call, Response<List<PersonModel>> response) {
                if(response.isSuccessful()){
                    List<PersonModel> list = response.body();
                    adapter = new GithubAdapter(list);

                    LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(llm);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                    recyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<List<PersonModel>> call, Throwable t) {
                Toast.makeText(GithubActivity.this, "Gagal Konek", Toast.LENGTH_SHORT).show();
                Log.e("Error", t.getLocalizedMessage());
            }
        });
    }
}