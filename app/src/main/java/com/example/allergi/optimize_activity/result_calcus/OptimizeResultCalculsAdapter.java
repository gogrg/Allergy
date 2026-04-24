package com.example.allergi.optimize_activity.result_calcus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allergi.R;
import com.example.allergi.optimize_activity.list_allergy.OptimizeListAllergiAdapter;

import java.util.List;

import lombok.Getter;

public class OptimizeResultCalculsAdapter extends RecyclerView.Adapter<OptimizeResultCalculsAdapter.OptimizeResultCalculsHolder>{
    private LayoutInflater inflater;
    private List<OptimizeResultElement> listElements;

    public OptimizeResultCalculsAdapter (Context context, List<OptimizeResultElement> listElements) {
        this.inflater = LayoutInflater.from(context);
        this.listElements = listElements;
    }

    @NonNull
    @Override
    public OptimizeResultCalculsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.optimize_result_element, parent, false);

        return new OptimizeResultCalculsHolder(view);
    }

    @Override
    public void onBindViewHolder (OptimizeResultCalculsAdapter.OptimizeResultCalculsHolder holder, int position) {
        OptimizeResultElement element = listElements.get(position);

        holder.getNameAllergen().setText(element.getName());
        holder.getAmount().setText(String.valueOf(element.getAmount()));
    }

    @Override
    public int getItemCount() {
        return listElements.size();
    }

    @Getter
    public class OptimizeResultCalculsHolder extends RecyclerView.ViewHolder {
        private TextView nameAllergen;
        private TextView amount;
        OptimizeResultCalculsHolder (@NonNull View view) {
            super(view);
            nameAllergen = view.findViewById(R.id.optimize_result_element_name);
            amount = view.findViewById(R.id.optimize_result_element_amount);
        }

    }
}
