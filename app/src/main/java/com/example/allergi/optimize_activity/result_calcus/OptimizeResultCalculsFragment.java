package com.example.allergi.optimize_activity.result_calcus;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allergi.R;
import com.example.allergi.optimize_activity.dto.AllergenOptimizeDTO;

import java.util.ArrayList;
import java.util.List;

public class OptimizeResultCalculsFragment extends Fragment {
    private List<AllergenOptimizeDTO> calculsElements;
    private int maxNegative;
    private int amountVars;

    public OptimizeResultCalculsFragment() {
        super(R.layout.fragment_result);
    }

    public static OptimizeResultCalculsFragment newInstance(List<AllergenOptimizeDTO> list, int maxNegative) {
        OptimizeResultCalculsFragment fragment = new OptimizeResultCalculsFragment();
        Bundle args = new Bundle();
        args.putSerializable("LIST_KEY", (ArrayList<AllergenOptimizeDTO>) list);
        args.putInt("MAX_NEG_KEY", maxNegative);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            calculsElements = (List<AllergenOptimizeDTO>) getArguments().getSerializable("LIST_KEY");
            maxNegative = getArguments().getInt("MAX_NEG_KEY");
            if (calculsElements != null) {
                amountVars = calculsElements.size();
            }
        }

        OptimizeCalculator calculator = new OptimizeCalculator(amountVars, calculsElements, maxNegative);
        List<OptimizeResultElement> result = calculator.calculate();

        RecyclerView recyclerView = view.findViewById(R.id.optimize_result_recycler_view);

        OptimizeResultCalculsAdapter adapter = new OptimizeResultCalculsAdapter(this.getContext(), result);

        recyclerView.setAdapter(adapter);
    }
}
