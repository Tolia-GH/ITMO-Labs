package org.tolia.function.logfunction.frombasefunction;

import org.tolia.Function;
import org.tolia.function.logfunction.Ln;

public class Lg extends Function {
    Ln ln = new Ln();
    public double init(double x) {
        return x;
    }

    public double getValue(double x, double acc, int terms) {


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

    public double getTaylorRes(double x, int terms) {
        x = init(x);
        return ln.getTaylorRes(x, terms) / ln.getTaylorRes(10, terms);
    }
}
