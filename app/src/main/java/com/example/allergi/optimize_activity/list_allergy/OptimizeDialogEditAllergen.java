package com.example.allergi.optimize_activity.list_allergy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.allergi.R;
import com.example.allergi.optimize_activity.dto.AllergenOptimizeDTO;
import com.example.allergi.utils.StaticSharedPreferences;

import java.util.List;

public class OptimizeDialogEditAllergen extends OptimizeDialogAddAllergen {
    private String curName;
    private int severity;
    private int pleasure;
    private int position;
    public OptimizeDialogEditAllergen(String curName, int severity, int pleasure, int position,
                                      List<AllergenOptimizeDTO> listAllergens, OptimizeListAllergiAdapter adapter, OnChangeVisibleDefaultTitle switchVisible) {
        super(listAllergens, adapter);
        this.curName = curName;
        this.severity = severity;
        this.pleasure = pleasure;
        this.position = position;
        this.switchVisible = switchVisible;
    }

    @NonNull
    public void onViewCreated (@NonNull View view, @Nullable Bundle savesInstance) {
        super.onViewCreated(view, savesInstance);

        EditText editNameAllergen = view.findViewById(R.id.edit_text_new_element);
        Spinner spinnerSeverity = view.findViewById(R.id.spinner_severity_allergy);
        Spinner spinnerPleasure = view.findViewById(R.id.spinner_amount_pleasure);

        editNameAllergen.setText(curName);
        spinnerSeverity.setSelection(severity);
        spinnerPleasure.setSelection(pleasure);

        Button positiveButton = view.findViewById(R.id.new_allergen_positive_button);
        positiveButton.setOnClickListener(v -> {
            String nameAllergen = ((EditText)view.findViewById(R.id.edit_text_new_element)).getText().toString();
            Integer severity = ((Spinner)view.findViewById(R.id.spinner_severity_allergy)).getSelectedItemPosition();
            Integer pleasure = ((Spinner)view.findViewById(R.id.spinner_amount_pleasure)).getSelectedItemPosition();

            AllergenOptimizeDTO newAllergen = new AllergenOptimizeDTO(nameAllergen, severity, pleasure);

            listAllergens.set(position, newAllergen);

            StaticSharedPreferences.putObject(getString(R.string.storage_file), getString(R.string.key_list_optimize_allergy), listAllergens, requireContext());
            adapter.notifyDataSetChanged();
            dismiss();
        });

        Button removeButton = view.findViewById(R.id.new_allergen_remove_button);
        removeButton.setVisibility(View.VISIBLE);
        removeButton.setOnClickListener(v -> {
            listAllergens.remove(position);
            StaticSharedPreferences.putObject(getString(R.string.storage_file), getString(R.string.key_list_optimize_allergy), listAllergens, requireContext());

            adapter.notifyDataSetChanged();
            if (listAllergens.isEmpty()) {
                switchVisible.execute();
            }
            dismiss();
        });
    }

}
