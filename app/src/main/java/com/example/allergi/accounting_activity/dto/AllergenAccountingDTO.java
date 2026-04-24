package com.example.allergi.accounting_activity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AllergenAccountingDTO {
    private String nameAllergen;
    private int severityAllergen;
    private TypesAllergen typeAllergen;

//    public AllergenDTO (String nameAllergen, int severityAllergen, TypesAllergen typeAllergen) {
//        this.nameAllergen = nameAllergen;
//        this.severityAllergen = severityAllergen;
//        this.typeAllergen = typeAllergen;
//    }
//
//    public String getNameAllergen() {
//        return nameAllergen;
//    }
//
//    public int getSeverityAllergen() {
//        return severityAllergen;
//    }
//
//    public TypesAllergen getTypeAllergen() {
//        return typeAllergen;
//    }
}
