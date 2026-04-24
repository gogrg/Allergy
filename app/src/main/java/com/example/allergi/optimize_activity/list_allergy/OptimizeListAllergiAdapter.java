package com.example.allergi.optimize_activity.list_allergy;

import static android.view.View.VISIBLE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allergi.R;
import com.example.allergi.optimize_activity.dto.AllergenOptimizeDTO;

import java.util.List;

import lombok.Getter;

public class OptimizeListAllergiAdapter extends RecyclerView.Adapter<OptimizeListAllergiAdapter.ListAllergiHolder>{
    private final LayoutInflater inflater;
    private final List<AllergenOptimizeDTO> allergens;
    private final FragmentManager manager;
    private final TextView defaultTitle;

    OptimizeListAllergiAdapter(Context context, List<AllergenOptimizeDTO> allergens, FragmentManager manager, TextView defaultTitle) {
        this.allergens = allergens;
        this.inflater = LayoutInflater.from(context);
        this.manager = manager;
        this.defaultTitle = defaultTitle;
    }


    @NonNull
    @Override
    public ListAllergiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.allergen_element, parent, false);

        return new ListAllergiHolder(view);
    }

    @Override
    public void onBindViewHolder (ListAllergiHolder holder, int position) {
        AllergenOptimizeDTO allergen = allergens.get(position);

        holder.allergen = allergen;

        holder.severityAllergy.setText(String.valueOf(allergen.getSeverityAllergen()));
        holder.pleasureAllergen.setText(String.valueOf(allergen.getPleasureAllergen()));
        holder.nameAllergen.setText(allergen.getNameAllergen());

        holder.buttonEdit.setOnClickListener(v -> {
            OptimizeDialogEditAllergen editDialog = new OptimizeDialogEditAllergen(holder.allergen.getNameAllergen(),
                    holder.allergen.getSeverityAllergen(),
                    holder.allergen.getPleasureAllergen(),
                    position,
                    allergens,
                    this,
                    () -> {
                defaultTitle.setVisibility(VISIBLE);
                    });
            editDialog.show(manager, "Edit allergen dialog");
        });

    }

    @Override
    public int getItemCount() {
        return allergens.size();
    }

    @Getter
    public class ListAllergiHolder extends RecyclerView.ViewHolder {
        final private TextView pleasureAllergen;
        final private TextView severityAllergy;
        final private TextView nameAllergen;
        final private ImageButton buttonEdit;
        private AllergenOptimizeDTO allergen;

        ListAllergiHolder (@NonNull View view) {
            super(view);

            pleasureAllergen = view.findViewById(R.id.allergen_element_amount_pleasure);
            pleasureAllergen.setVisibility(VISIBLE);
            severityAllergy = view.findViewById(R.id.allergen_element_severity_of_allergy);
            nameAllergen = view.findViewById(R.id.allergen_element_name_allergen);
            buttonEdit = view.findViewById(R.id.allergen_element_button_edit);
        }
    }
}
