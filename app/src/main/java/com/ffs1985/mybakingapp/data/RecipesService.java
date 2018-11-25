package com.ffs1985.mybakingapp.data;

import com.ffs1985.mybakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipesService {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> listRecipes();
}
