package com.ffs1985.mybakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ffs1985.mybakingapp.R;
import com.ffs1985.mybakingapp.model.Ingredient;

import org.w3c.dom.Text;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsAdapterViewHolder> {
    private List<Ingredient> ingredientList;

    public IngredientsAdapter(){}

    @Override
    public IngredientsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.ingredient_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new IngredientsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsAdapterViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        holder.tvIngredientName.setText(ingredient.getIngredient());
        holder.tvIngredientQuantity.setText(ingredient.getQuantity()+ " " + ingredient.getMeasure());
    }

    @Override
    public int getItemCount() {
        if(null == ingredientList) return 0;
        return ingredientList.size();
    }

    public class IngredientsAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIngredientName;
        private TextView tvIngredientQuantity;

        public IngredientsAdapterViewHolder(View view) {
            super(view);
            tvIngredientName = view.findViewById(R.id.ingredient_name);
            tvIngredientQuantity = view.findViewById(R.id.ingredient_quantity);
        }
    }

    public void setIngredientData(List<Ingredient> ingredients) {
        ingredientList = ingredients;
        notifyDataSetChanged();
    }
}
