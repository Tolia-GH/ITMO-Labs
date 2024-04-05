package org.tolia;

import lombok.AllArgsConstructor;
import org.tolia.expression.EquationA;
import org.tolia.expression.EquationB;

@AllArgsConstructor
public class EquationSystem {
    EquationA equationA;
    EquationB equationB;

    public EquationSystem(){
        equationB = new EquationB();
        equationA = new EquationA();
    }
    public double getValue(double x, double acc, int terms) {
        if (x <= 0) {
            return equationA.getValue(x, acc, terms);
        } else {
            return equationB.getValue(x, acc, terms);
        }
    }

    public double getMathValue(double x) {
        if (x <= 0) {
            return equationA.getMathValue(x);
        } else {
            return equationB.getMathValue(x);
        }
    }
}