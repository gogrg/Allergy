package com.example.allergi.optimize_activity.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemSpinnerTypeAllergyDTO {
    private String nameAllergy;
    private int iconResId;
    private TypesAllergen typeAllergen;

}
