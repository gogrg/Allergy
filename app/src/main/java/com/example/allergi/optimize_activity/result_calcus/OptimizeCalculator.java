package com.example.allergi.optimize_activity.result_calcus;

import com.example.allergi.optimize_activity.dto.AllergenOptimizeDTO;

import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.PointValuePair;

import java.util.ArrayList;
import java.util.List;

public class OptimizeCalculator {
    private int amountVars;
    private List<AllergenOptimizeDTO> list;

    private final int max_negative;
    private final int AMOUNT_CONSTRAINT = 1;
    private final int MAX_ONE_VAR = 5;

    public OptimizeCalculator (int amountVars, List<AllergenOptimizeDTO> list, int max) {
        this.amountVars = amountVars;
        this.list = list;
        this.max_negative = max;
    }

    public List<OptimizeResultElement> calculate() {
        double[] coefs = new double[amountVars];
        double[] constraintCoefs = new double[amountVars];

        for (int i = 0; i < list.size(); i++) {
            coefs[i] = list.get(i).getPleasureAllergen();
            constraintCoefs[i] = list.get(i).getSeverityAllergen();
        }
        //задание целевой функции
        LinearObjectiveFunction objective = new LinearObjectiveFunction(coefs, 0.0);
        //задание ограничения
        List<LinearConstraint> listConstraints = new ArrayList<>();
        listConstraints.add(new LinearConstraint(constraintCoefs, Relationship.LEQ, max_negative));

        for (int i = 0; i < amountVars; i++) {
            double[] individualConstraint = new double[amountVars];
            individualConstraint[i] = 1.0;
            listConstraints.add(new LinearConstraint(individualConstraint, Relationship.LEQ, MAX_ONE_VAR));
        }


        //настройка решателя на симплекс метод
        SimplexSolver solver = new SimplexSolver();

        //Запуск оптимизации
        PointValuePair solution = solver.optimize(
                new MaxIter(1000),                  // лимит итераций
                objective,                          // целевая функция
                new LinearConstraintSet(listConstraints),                      // ограничения
                GoalType.MAXIMIZE,                  // MAXIMIZE или MINIMIZE
                new NonNegativeConstraint(true)     // xi >= 0 (по умолчанию в LP)
        );

        double[] optimalValues = solution.getPoint();

        List<OptimizeResultElement> listResult = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (Math.round(optimalValues[i]) != 0){
                int realVal = (int) Math.round(optimalValues[i]);
                listResult.add(new OptimizeResultElement(list.get(i).getNameAllergen(), realVal));
            }
        }

        return listResult;
    }
}
