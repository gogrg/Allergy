package com.example.allergi.optimize_activity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AllergenOptimizeDTO implements java.io.Serializable{
    private String nameAllergen;
    private int severityAllergen;
    private int pleasureAllergen;
}
