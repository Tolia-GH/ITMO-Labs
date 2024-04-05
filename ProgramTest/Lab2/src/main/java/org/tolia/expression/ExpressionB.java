package org.tolia.expression;

import org.tolia.function.logfunction.Ln;
import org.tolia.function.logfunction.frombasefunction.Lg;
import org.tolia.function.logfunction.frombasefunction.Log;

public class ExpressionB {
    public static double expression(double x, int terms) {
        double res = (
            (
                (
                    (
                        (
                            (
                                Ln.getTaylorRes(x, terms) * Ln.getTaylorRes(x, terms)
                            ) - Ln.getTaylorRes(x, terms)
                        ) -
                        (
                            Math.pow(Ln.getTaylorRes(x, terms), 2)
                        )
                    ) *
                    (
                        (
                            Log.getTaylorRes(5, x, terms) + Lg.getTaylorRes(x, terms)
                        ) - Log.getTaylorRes(2, x, terms)
                    )
                ) /
                (
                    (
                        Log.getTaylorRes(3, x, terms) * Log.getTaylorRes(5, x, terms)
                    ) *
                    (
                        (
                            Math.pow(Log.getTaylorRes(2, x, terms), 2)
                        ) /
                        (
                            Log.getTaylorRes(2, x, terms) +
                            (
                                Lg.getTaylorRes(x, terms) - Lg.getTaylorRes(x, terms)
                            )
                        )
                    )
                )
            )
        );
        System.out.printf("%s = %-3d, %s = %.20f\n","terms", terms, "res", res);
        return res;
    }

    public static double getMathValue(double x) {

        double lnx = Math.log(x);
        double log5x = Math.log(x) / Math.log(5);
        double log2x = Math.log(x) / Math.log(2);
        double log3x = Math.log(x) / Math.log(3);
        double lgx = Math.log10(x);
        return (
            (
                (
                    (
                        (
                            (
                                lnx * lnx
                            ) - lnx
                        ) - Math.pow(lnx, 2)
                    ) *
                    (
                        (
                            log5x + lgx
                        ) - log2x
                    )
                ) /
                (
                    (
                        log3x * log5x
                    ) *
                    (
                        Math.pow(log2x, 2) /
                        (
                            log2x + (lgx - lgx)
                        )
                    )
                )
            )
        );
    }

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
