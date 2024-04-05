package org.example.logfunction.frombasefunction;

import org.example.logfunction.Ln;

/**
 * The type Log.
 */
public class Log {
    private static double init(double base, double x) {
        return x;
    }

    public static double getValue(double base, double x, double acc, int terms) {

        x = init(base, x);

        double resLast = 0;
        double res = Ln.getTaylorRes(x, terms) / Ln.getTaylorRes(base, terms);

        while (Math.abs(res - resLast) > acc) {
            terms++;
            resLast = res;
            res = Ln.getTaylorRes(x, terms) / Ln.getTaylorRes(base, terms);
        }
        System.out.println("Taylor terms = " + terms);
        return res;
    }
}
