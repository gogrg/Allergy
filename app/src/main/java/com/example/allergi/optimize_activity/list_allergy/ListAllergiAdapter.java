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
import com.example.allergi.accounting_activity.dto.AllergenDTO;

import java.util.List;

public class ListAllergiAdapter extends RecyclerView.Adapter<ListAllergiAdapter.ListAllergiHolder>{
    private final LayoutInflater inflater;
    private final List<AllergenDTO> allergens;
    private final FragmentManager manager;
    private final TextView defaultTitle;

    ListAllergiAdapter (Context context, List<AllergenDTO> allergens, FragmentManager manager, TextView defaultTitle) {
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
        AllergenDTO allergen = allergens.get(position);

        holder.allergen = allergen;

        holder.severityAllergy.setText(String.valueOf(allergen.getSeverityAllergen()));
        holder.nameAllergen.setText(allergen.getNameAllergen());

        switch (allergen.getTypeAllergen()) {
            case FOOD:
                holder.typeAllergenImage.setImageResource(R.drawable.icon_food);
                break;
            case ANIMAL:
                holder.typeAllergenImage.setImageResource(R.drawable.icon_animal);
                break;
            case MEDICINE:
                holder.typeAllergenImage.setImageResource(R.drawable.icon_medicine);
                break;
            case RESPIRATORY:
                holder.typeAllergenImage.setImageResource(R.drawable.icon_respiratory);
                break;
            case SKIN:
                holder.typeAllergenImage.setImageResource(R.drawable.icon_skin);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + allergen.getTypeAllergen());
        }

        holder.buttonEdit.setOnClickListener(v -> {
            DialogEditAllergen editDialog = new DialogEditAllergen(holder.allergen.getNameAllergen(),
                    holder.allergen.getSeverityAllergen(),
                    holder.allergen.getTypeAllergen(),
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

    public class ListAllergiHolder extends RecyclerView.ViewHolder {
        final ImageView typeAllergenImage;
        final TextView severityAllergy;
        final TextView nameAllergen;
        final ImageButton buttonEdit;
        AllergenDTO allergen;

        ListAllergiHolder (@NonNull View view) {
            super(view);

            typeAllergenImage = view.findViewById(R.id.allergen_element_type_allergen);
            severityAllergy = view.findViewById(R.id.allergen_element_severity_of_allergy);
            nameAllergen = view.findViewById(R.id.allergen_element_name_allergen);
            buttonEdit = view.findViewById(R.id.allergen_element_button_edit);
        }
    }
}
