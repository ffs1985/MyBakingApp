package com.ffs1985.mybakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ffs1985.mybakingapp.R;
import com.ffs1985.mybakingapp.model.Step;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder> {
    private List<Step> stepList;
    private StepsAdapterOnClickHandler mClickHandler;

    public StepsAdapter(StepsAdapterOnClickHandler mClickHandler){
        this.mClickHandler = mClickHandler;
    }

    @Override
    public StepsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.step_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new StepsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsAdapterViewHolder holder, int position) {
        Step step = stepList.get(position);
        holder.tvStepDescription.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if(null == stepList) return 0;
        return stepList.size();
    }

    public interface StepsAdapterOnClickHandler {
        void onClick(Step step);
    }

    public class StepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvStepDescription;

        public StepsAdapterViewHolder(View view) {
            super(view);
            tvStepDescription = view.findViewById(R.id.step_description);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Step step = stepList.get(adapterPosition);
            mClickHandler.onClick(step);
        }
    }

    public void setStepData(List<Step> steps) {
        stepList = steps;
        notifyDataSetChanged();
    }
}
