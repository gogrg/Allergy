package com.example.allergi.optimize_activity.list_allergy;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;

import com.example.allergi.R;
import com.example.allergi.optimize_activity.dto.AllergenOptimizeDTO;
import com.example.allergi.accounting_activity.dto.ItemSpinnerTypeAllergyDTO;
import com.example.allergi.accounting_activity.dto.TypesAllergen;
import com.example.allergi.optimize_activity.dto.AllergenOptimizeDTO;
import com.example.allergi.utils.MessageDialog;
import com.example.allergi.utils.StaticSharedPreferences;

import java.util.List;

public class OptimizeDialogAddAllergen extends DialogFragment {
    protected final int DEFAULT_VALUE_SPINNER_TYPES_ALLERGY_ICON = -10;
    protected Integer[] severities;
    protected Integer[] pleasure;
    protected List<AllergenOptimizeDTO> listAllergens;
    protected OptimizeListAllergiAdapter adapter;
    protected OnChangeVisibleDefaultTitle switchVisible;

    public interface OnChangeVisibleDefaultTitle {
        void execute();
    }

    public OptimizeDialogAddAllergen(List<AllergenOptimizeDTO> listAllergens, OptimizeListAllergiAdapter adapter) {
        this.listAllergens = listAllergens;
        this.adapter = adapter;
    }

    public OptimizeDialogAddAllergen(List<AllergenOptimizeDTO> listAllergens, OptimizeListAllergiAdapter adapter, OnChangeVisibleDefaultTitle switchVisible) {
        this.listAllergens = listAllergens;
        this.adapter = adapter;
        this.switchVisible = switchVisible;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstance) {
        super.onCreate(savedInstance);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);

        severities = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        pleasure = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    }

    @NonNull
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstance) {
        return inflater.inflate(R.layout.new_allergen_dialog, container, false);
    }

    @NonNull
    public void onViewCreated (@NonNull View view, @Nullable Bundle savesInstance) {
        TextView spinnerPleasureTitle = view.findViewById(R.id.new_allergen_amount_pleasure);
        spinnerPleasureTitle.setVisibility(View.VISIBLE);
        //спинер тяжести аллергии
        Spinner spinnerSeverity = view.findViewById(R.id.spinner_severity_allergy);
        ArrayAdapter<Integer> spinnerSeverityAdapter = new ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, severities) {
            @Override
            public View getView (int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                customizeItem(textView, position);
                return textView;
            }

            @Override
            public View getDropDownView (int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                customizeItem(textView, position);
                return textView;
            }

            @SuppressLint("ResourceAsColor")
            private void customizeItem (TextView textView, int position) {
                Typeface font = ResourcesCompat.getFont(requireContext(), R.font.sigmar_cyrillic);
                textView.setTypeface(font);

                textView.setTextColor(R.color.black);
            }
        };
        spinnerSeverityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerSeverity.setAdapter(spinnerSeverityAdapter);


        //Спинер удовольствия от аллергена
        Spinner spinnerPleasure = view.findViewById(R.id.spinner_amount_pleasure);
        ArrayAdapter<Integer> spinnerPleasureAdapter = new ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, pleasure) {
            @Override
            public View getView (int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                customizeItem(textView, position);
                return textView;
            }

            @Override
            public View getDropDownView (int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                customizeItem(textView, position);
                return textView;
            }

            @SuppressLint("ResourceAsColor")
            private void customizeItem (TextView textView, int position) {
                Typeface font = ResourcesCompat.getFont(requireContext(), R.font.sigmar_cyrillic);
                textView.setTypeface(font);

                textView.setTextColor(R.color.black);
            }
        };
        spinnerPleasureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerPleasure.setAdapter(spinnerSeverityAdapter);
        spinnerPleasure.setVisibility(View.VISIBLE);

        //настройка кнопок
        Button negativeButton = view.findViewById(R.id.new_allergen_negative_button);
        negativeButton.setOnClickListener(v -> {
            dismiss();
        });


        Button positiveButton = view.findViewById(R.id.new_allergen_positive_button);
        positiveButton.setOnClickListener(v -> {
            String nameAllergen = ((EditText)view.findViewById(R.id.edit_text_new_element)).getText().toString();
            Integer severity = ((Spinner)view.findViewById(R.id.spinner_severity_allergy)).getSelectedItemPosition();
            Integer pleasure = ((Spinner)view.findViewById(R.id.spinner_amount_pleasure)).getSelectedItemPosition();



            AllergenOptimizeDTO newAllergen = new AllergenOptimizeDTO(nameAllergen, severity, pleasure);

            listAllergens.add(newAllergen);

            StaticSharedPreferences.putObject(getString(R.string.storage_file), getString(R.string.key_list_optimize_allergy), listAllergens, requireContext());
            adapter.notifyDataSetChanged();

            switchVisible.execute();
            dismiss();
        });
    }

}
