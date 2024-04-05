package org.tolia.function.logfunction.frombasefunction;

import org.tolia.function.logfunction.Ln;

public class Lg {
    private static double init(double x) {
        return x;
    }

    public static double getValue(double x, double acc, int terms) {
        x = init(x);

        double resLast = 0;
        double res = getTaylorRes(x, terms);

        while (Math.abs(res - resLast) > acc) {
            terms++;
            resLast = res;
            res = getTaylorRes(x, terms);
        }
        System.out.println("Taylor terms = " + terms);
        return res;
    }

    public static double getTaylorRes(double x, int terms) {
        return Ln.getTaylorRes(x, terms) / Ln.getTaylorRes(10, terms);
    }
}
