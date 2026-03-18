package com.example.allergi.accounting_activity.list_allergy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.allergi.R;
import com.example.allergi.accounting_activity.dto.AllergenDTO;
import com.example.allergi.accounting_activity.dto.TypesAllergen;
import com.example.allergi.utils.MessageDialog;
import com.example.allergi.utils.StaticSharedPreferences;

import java.util.List;

public class DialogEditAllergen extends DialogAddAllergen {
    private String curName;
    private int severity;
    private TypesAllergen typesAllergen;
    private int position;
    public DialogEditAllergen (String curName, int severity, TypesAllergen typesAllergen, int position,
                               List<AllergenDTO> listAllergens, ListAllergiAdapter adapter, OnChangeVisibleDefaultTitle switchVisible) {
        super(listAllergens, adapter);
        this.curName = curName;
        this.severity = severity;
        this.typesAllergen = typesAllergen;
        this.position = position;
        this.switchVisible = switchVisible;
    }

    @NonNull
    public void onViewCreated (@NonNull View view, @Nullable Bundle savesInstance) {
        super.onViewCreated(view, savesInstance);

        EditText editNameAllergen = view.findViewById(R.id.edit_text_new_element);
        Spinner spinnerSeverity = view.findViewById(R.id.spinner_severity_allergy);
        Spinner spinnerTypes = view.findViewById(R.id.spinner_type_allergy);

        editNameAllergen.setText(curName);
        spinnerSeverity.setSelection(severity);
        switch (typesAllergen) {
            case ANIMAL:
                spinnerTypes.setSelection(1);
                break;
            case FOOD:
                spinnerTypes.setSelection(2);
                break;
            case MEDICINE:
                spinnerTypes.setSelection(3);
                break;
            case RESPIRATORY:
                spinnerTypes.setSelection(4);
                break;
            case SKIN:
                spinnerTypes.setSelection(5);
                break;
        }

        Button positiveButton = view.findViewById(R.id.new_allergen_positive_button);
        positiveButton.setOnClickListener(v -> {
            String nameAllergen = ((EditText)view.findViewById(R.id.edit_text_new_element)).getText().toString();
            Integer severity = ((Spinner)view.findViewById(R.id.spinner_severity_allergy)).getSelectedItemPosition();
            TypesAllergen typeAllergen = typesAllergyToSpinner[((Spinner)view.findViewById(R.id.spinner_type_allergy)).getSelectedItemPosition()].getTypeAllergen();

            if (nameAllergen.isEmpty() || typeAllergen == TypesAllergen.NO_SELECTED) {
                MessageDialog errorDialog = new MessageDialog("Не заполнены поля", "", "Ок", false);
                errorDialog.show(getParentFragmentManager(), "MessageErrorAddElement");
            }

            AllergenDTO newAllergen = new AllergenDTO(nameAllergen, severity, typeAllergen);

            listAllergens.set(position, newAllergen);

            StaticSharedPreferences.putObject(getString(R.string.storage_file), getString(R.string.key_list_allergy), listAllergens, requireContext());
            adapter.notifyDataSetChanged();
            dismiss();
        });

        Button removeButton = view.findViewById(R.id.new_allergen_remove_button);
        removeButton.setVisibility(View.VISIBLE);
        removeButton.setOnClickListener(v -> {
            listAllergens.remove(position);
            StaticSharedPreferences.putObject(getString(R.string.storage_file), getString(R.string.key_list_allergy), listAllergens, requireContext());

            adapter.notifyDataSetChanged();
            if (listAllergens.isEmpty()) {
                switchVisible.execute();
            }
            dismiss();
        });
    }

}
