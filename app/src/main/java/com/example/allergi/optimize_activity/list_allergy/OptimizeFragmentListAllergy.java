package com.example.allergi.optimize_activity.list_allergy;

import static android.view.View.GONE;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allergi.R;
import com.example.allergi.optimize_activity.dto.AllergenOptimizeDTO;
import com.example.allergi.utils.StaticSharedPreferences;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OptimizeFragmentListAllergy extends Fragment {
    private List<AllergenOptimizeDTO> listAllergens;
    private View thisView;
    private int currentMaxNegative;
    private TextView textViewCurrentMaxNegative;
    TextView defaultTitle;
    public OptimizeFragmentListAllergy() {
        super(R.layout.fragment_allergi_list_optimize);

    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        thisView = view;

        TextView defaultTitle = view.findViewById(R.id.list_allergy_fragment_default_title);
        textViewCurrentMaxNegative = view.findViewById(R.id.list_optimize_text_cur);

        SeekBar seekBarMaxNegative = thisView.findViewById(R.id.fragment_allergy_list_optimize_seek_bar);
        currentMaxNegative = seekBarMaxNegative.getProgress();
        seekBarMaxNegative.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewCurrentMaxNegative.setText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                currentMaxNegative = seekBar.getProgress();
            }
        });

        Type typeList = new TypeToken<List<AllergenOptimizeDTO>>(){}.getType();
        try {
            listAllergens = StaticSharedPreferences.getObject(getString(R.string.storage_file), getString(R.string.key_list_optimize_allergy), typeList, getContext());
        } catch (IllegalArgumentException e) {
            listAllergens = new ArrayList<AllergenOptimizeDTO>();
        }

        if (!listAllergens.isEmpty()) {
            defaultTitle.setVisibility(GONE);
        }

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewAllergy);

        OptimizeListAllergiAdapter adapter = new OptimizeListAllergiAdapter(this.getContext(), listAllergens, getParentFragmentManager(), defaultTitle);

        recyclerView.setAdapter(adapter);

        ImageButton newAllergenButton = view.findViewById(R.id.button_new_allergen);

        newAllergenButton.setOnClickListener(v -> {
            OptimizeDialogAddAllergen dialog = new OptimizeDialogAddAllergen(listAllergens, adapter, () -> {defaultTitle.setVisibility(GONE);});
            dialog.show(getParentFragmentManager(), "DialogNewAllergen");
        });
    }

    public int getMaxNegative() {
        return currentMaxNegative;
    }

}
