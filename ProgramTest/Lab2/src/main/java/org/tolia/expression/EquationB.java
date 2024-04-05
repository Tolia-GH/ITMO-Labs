package org.tolia.expression;

import lombok.AllArgsConstructor;
import org.tolia.function.logfunction.Ln;
import org.tolia.function.logfunction.frombasefunction.Lg;
import org.tolia.function.logfunction.frombasefunction.Log;

@AllArgsConstructor
public class EquationB {
    Ln ln;
    Log log;
    Lg lg;

    public EquationB() {
        ln = new Ln();
        log = new Log();
        lg = new Lg();
    }
    public double expression(double x, int terms) {

        double res = (
            (
                (
                    (
                        (
                            (
                                ln.getTaylorRes(x, terms) * ln.getTaylorRes(x, terms)
                            ) - ln.getTaylorRes(x, terms)
                        ) -
                        (
                            Math.pow(ln.getTaylorRes(x, terms), 2)
                        )
                    ) *
                    (
                        (
                            log.getTaylorRes(5, x, terms) + lg.getTaylorRes(x, terms)
                        ) - log.getTaylorRes(2, x, terms)
                    )
                ) /
                (
                    (
                        log.getTaylorRes(3, x, terms) * log.getTaylorRes(5, x, terms)
                    ) *
                    (
                        (
                            Math.pow(log.getTaylorRes(2, x, terms), 2)
                        ) /
                        (
                            log.getTaylorRes(2, x, terms) +
                            (
                                lg.getTaylorRes(x, terms) - lg.getTaylorRes(x, terms)
                            )
                        )
                    )
                )
            )
        );
        System.out.printf("%s = %-3d, %s = %.20f\n","terms", terms, "res", res);
        return res;
    }

    public double getMathValue(double x) {

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

    public double getValue(double x, double acc, int terms) {
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
