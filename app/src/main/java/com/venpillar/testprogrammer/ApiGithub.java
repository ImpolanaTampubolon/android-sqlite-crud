package com.venpillar.testprogrammer;

import com.venpillar.testprogrammer.model.PersonModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiGithub {
    @GET("users/google/repos")
    Call<List<PersonModel>> getPeople();
}
