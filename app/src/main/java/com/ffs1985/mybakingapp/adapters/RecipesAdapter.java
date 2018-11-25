package com.ffs1985.mybakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ffs1985.mybakingapp.R;
import com.ffs1985.mybakingapp.model.Recipe;
import com.ffs1985.mybakingapp.model.RecipeObserver;
import com.ffs1985.mybakingapp.model.Step;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesAdapterViewHolder> implements RecipeObserver {
    private List<Recipe> mRecipesListData;

    private final RecipesAdapterOnClickHandler mClickHandler;
    private static final String RECIPE_IMAGE = "https://images.unsplash.com/photo-1530536476203-d77e573bc524?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=c1a0e2785573b4b9d08a7420aeba0c25&auto=format&fit=crop&w=350&q=80";

    public interface RecipesAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    public RecipesAdapter(RecipesAdapterOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }

    public class RecipesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mRecipeTextView;
        public final ImageView mRecipeImageView;

        public RecipesAdapterViewHolder(View view) {
            super(view);
            mRecipeTextView = view.findViewById(R.id.recipe_name);
            mRecipeImageView = view.findViewById(R.id.recipe_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = mRecipesListData.get(adapterPosition);
            mClickHandler.onClick(recipe);
        }
    }

    @NonNull
    @Override
    public RecipesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecipesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapterViewHolder recipesAdapterViewHolder, int position) {
        Recipe recipe = mRecipesListData.get(position);
        recipesAdapterViewHolder.mRecipeTextView.setText(recipe.getName());
        String recipeImage = recipe.getImage();
        if(recipe.getImage() == null || recipe.getImage().isEmpty()) {
            recipeImage = RECIPE_IMAGE;
        }
        Picasso.get().load(recipeImage)
                .placeholder(R.drawable.recipe_placeholder)
                .into(recipesAdapterViewHolder.mRecipeImageView);
    }

    @Override
    public int getItemCount() {
        if(null == mRecipesListData) return 0;
        return mRecipesListData.size();
    }

    public void setRecipesData(List<Recipe> recipesData) {
        mRecipesListData = recipesData;
        notifyDataSetChanged();
    }


    @Override
    public void recipesUpdated(List<Recipe> recipeList) {
        setRecipesData(recipeList);
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        // NOTHING.
    }

    @Override
    public void onStepSelected(Step step, int index) {
        // NOTHING.
    }

    @Override
    public void onWidgetRecipeSelected(Recipe recipe) {
        // NOTHING.
    }
}
