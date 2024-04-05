package org.tolia.expression;

import org.tolia.Function;
import org.tolia.function.trigfunction.Cos;
import org.tolia.function.trigfunction.Sin;
import org.tolia.function.trigfunction.frombasefunction.Cot;
import org.tolia.function.trigfunction.frombasefunction.Csc;
import org.tolia.function.trigfunction.frombasefunction.Sec;
import org.tolia.function.trigfunction.frombasefunction.Tan;

/**
 * The type Complex function A.
 */
public class EquationA extends Function {

    private static double expression(double x, int terms) {

        double res = (
            (
                (
                    (
                        (
                            Tan.getTaylorRes(x, terms) - Csc.getTaylorRes(x, terms)
                        ) / Cos.getTaylorRes(x, terms)
                    ) / Sin.getTaylorRes(x, terms)
                ) +
                (
                    Cot.getTaylorRes(x, terms) -
                    (Csc.getTaylorRes(x, terms) - Csc.getTaylorRes(x, terms))
                )
            ) /
            Sec.getTaylorRes(x, terms)
        );
        System.out.printf("%s = %-3d, %s = %.20f\n","terms", terms, "res", res);
        return res;
    }

    public static double getMathValue(double x) {

        return (
            (
                (
                    (
                        (
                            Math.tan(x) - (1 / Math.sin(x))
                        ) / Math.cos(x)
                    ) / Math.sin(x)
                ) +
                (
                    (1 / Math.tan(x)) -
                    ((1 / Math.sin(x)) - (1 / Math.sin(x)))
                )
            ) /
            (1 / Math.cos(x))
        );
    }
    /**
     * Gets value.
     *
     * @param x the x
     * @return the value
     */
    public static double getValue(double x, double acc, int terms) {
        double resLast = 0;
        double res = expression(x, terms);

        while (Math.abs(res - resLast) > acc) {
            terms++;
            resLast = res;
            res = expression(x, terms);
        }
        System.out.println("E Taylor terms = " + terms);
        return res;
    }


}
