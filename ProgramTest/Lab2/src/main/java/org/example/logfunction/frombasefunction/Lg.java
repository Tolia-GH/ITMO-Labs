package org.example.logfunction.frombasefunction;

import org.example.logfunction.Ln;

public class Lg {
    private static double init(double x) {
        return x;
    }

    public static double getValue(double x, double acc, int terms) {
        x = init(x);

        double resLast = 0;
        double res = Ln.getTaylorRes(x, terms) / Ln.getTaylorRes(10, terms);

        while (Math.abs(res - resLast) > acc) {
            terms++;
            resLast = res;
            res = Ln.getTaylorRes(x, terms) / Ln.getTaylorRes(10, terms);
        }
        System.out.println("Taylor terms = " + terms);
        return res;
    }
}
