package com.example.allergi.accounting_activity.list_allergy;

import static android.view.View.GONE;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allergi.R;
import com.example.allergi.accounting_activity.dto.AllergenAccountingDTO;
import com.example.allergi.utils.StaticSharedPreferences;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AccountingFragmentListAllergy extends Fragment {
    private List<AllergenAccountingDTO> listAllergens;
    TextView defaultTitle;
    public AccountingFragmentListAllergy() {
        super(R.layout.fragment_allergi_list_optimize);

    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView defaultTitle = view.findViewById(R.id.list_allergy_fragment_default_title);


        Type typeList = new TypeToken<List<AllergenAccountingDTO>>(){}.getType();
        try {
            listAllergens = StaticSharedPreferences.getObject(getString(R.string.storage_file), getString(R.string.key_list_accounting_allergy), typeList, getContext());
        } catch (IllegalArgumentException e) {
            listAllergens = new ArrayList<AllergenAccountingDTO>();
        }

        if (!listAllergens.isEmpty()) {
            defaultTitle.setVisibility(GONE);
        }

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewAllergy);

        AccountingListAllergiAdapter adapter = new AccountingListAllergiAdapter(this.getContext(), listAllergens, getParentFragmentManager(), defaultTitle);

        recyclerView.setAdapter(adapter);

        ImageButton newAllergenButton = view.findViewById(R.id.button_new_allergen);

        newAllergenButton.setOnClickListener(v -> {
            AccountingDialogAddAllergen dialog = new AccountingDialogAddAllergen(listAllergens, adapter, () -> {defaultTitle.setVisibility(GONE);});
            dialog.show(getParentFragmentManager(), "DialogNewAllergen");
        });
    }

}
