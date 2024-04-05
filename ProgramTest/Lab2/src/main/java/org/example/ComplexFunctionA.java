package org.example;

import org.example.trigofunction.Cos;
import org.example.trigofunction.Sin;
import org.example.trigofunction.frombasefunction.Cot;
import org.example.trigofunction.frombasefunction.Csc;
import org.example.trigofunction.frombasefunction.Sec;
import org.example.trigofunction.frombasefunction.Tan;

/**
 * The type Complex function A.
 */
public class ComplexFunctionA extends Function {
    /**
     * Gets value.
     *
     * @param degree the degree
     * @return the value
     */
    public static double getValue(double degree, double acc, int terms) {
        return (
            (
                (
                    (
                        (
                            Tan.getValue(degree, acc, terms) - Csc.getValue(degree, acc, terms)
                        ) / Cos.getValue(degree, acc, terms)
                    ) / Sin.getValue(degree, acc, terms)
                ) +
                (
                    Cot.getValue(degree, acc, terms) - (Csc.getValue(degree, acc, terms) - Csc.getValue(degree, acc, terms))
                )
            ) /
            Sec.getValue(degree, acc, terms)
        );
    }
}
