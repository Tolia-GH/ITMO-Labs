package org.tolia.function.logfunction.frombasefunction;

import org.tolia.Function;
import org.tolia.function.logfunction.Ln;

/**
 * The type Log.
 */
public class Log extends Function {
    Ln ln = new Ln();
    public double init(double base, double x) {
        return x;
    }

    public double getValue(double base, double x, double acc, int terms) {



        double resLast = 0;
        double res = getTaylorRes(base, x, terms);

        while (Math.abs(res - resLast) > acc) {
            terms++;
            resLast = res;
            res = getTaylorRes(base, x, terms);
        }
        System.out.println("Taylor terms = " + terms);
        return res;
    }

    public double getTaylorRes(double base, double x, int terms) {
        x = init(base, x);
        return ln.getTaylorRes(x, terms) / ln.getTaylorRes(base, terms);
    }
}
