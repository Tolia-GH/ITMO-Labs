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
public class ExpressionA extends Function {

    private static double expression(double degree, int terms) {
        double x = Math.toRadians(degree);

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

    public static double getMathValue(double degree) {
        double x = Math.toRadians(degree);

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
     * @param degree the degree
     * @return the value
     */
    public static double getValue(double degree, double acc, int terms) {
        double resLast = 0;
        double res = expression(degree, terms);

        while (Math.abs(res - resLast) > acc) {
            terms++;
            resLast = res;
            res = expression(degree, terms);
        }
        System.out.println("E Taylor terms = " + terms);
        return res;
    }


}
