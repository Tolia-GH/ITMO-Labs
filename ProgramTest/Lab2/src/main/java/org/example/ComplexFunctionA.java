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
    public static double getValue(double degree) {
        return (
            (
                (
                    (
                        (
                            Tan.getValue(degree) - Csc.getValue(degree)
                        ) / Cos.getValue(degree)
                    ) / Sin.getValue(degree)
                ) +
                (
                    Cot.getValue(degree) - (Csc.getValue(degree) - Csc.getValue(degree))
                )
            ) /
            Sec.getValue(degree)
        );
    }
}
