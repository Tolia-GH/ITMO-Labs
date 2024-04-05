package org.tolia;

import org.tolia.expression.EquationA;
import org.tolia.expression.EquationB;

public class EquationSystem {
    public static double getValue(double x, double acc, int terms) {
        if (x <= 0) {
            return EquationA.getValue(x, acc, terms);
        } else {
            return EquationB.getValue(x, acc, terms);
        }
    }

    public static double getMathValue(double x) {
        if (x <= 0) {
            return EquationA.getMathValue(x);
        } else {
            return EquationB.getMathValue(x);
        }
    }
}