package com.ffs1985.mybakingapp.data;

import com.ffs1985.mybakingapp.model.Recipe;
import com.ffs1985.mybakingapp.model.Step;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipesUtil {
    private final static String RECIPES_URL_DATA = "https://d17h27t6h515a5.cloudfront.net/";
    private static HashMap<Integer, Recipe> recipes = new HashMap<>();

    public static void loadData(Callback<List<Recipe>> callback) {
        if(recipes.isEmpty()) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RECIPES_URL_DATA)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RecipesService service = retrofit.create(RecipesService.class);
            Call<List<Recipe>> call = service.listRecipes();
            call.enqueue(callback);
        }
    }
}
