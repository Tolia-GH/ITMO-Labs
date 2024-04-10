package org.tolia.function.trigfunction;

import org.tolia.Function;
import org.tolia.tools.MyMath;

public class Sin extends Function {

    public double initDegree(double x) {
        if (Double.isInfinite(x) || Double.isNaN(x)) {
            throw new IllegalArgumentException("Value invalid");
        }

        return x % (2 * Math.PI);
    }

    public double getValue(double x, double acc, int terms) {
        x = initDegree(x);

        double resLast = 0;
        double res = getTaylorRes(x, terms);

        while (Math.abs(res - resLast) > acc) {
            terms++;
            resLast = res;
            res = getTaylorRes(x, terms);
        }
        System.out.println("Sin Taylor terms = " + terms);
        return res;
    }

    public double getTaylorRes(double x, int terms) {
        x = x % (2 * Math.PI);
        double resSin = 0;
        for (int i = 1; i <= terms; i++) {
            resSin += taylorSinX(x, i);
        }

        return resSin;
    }

    public double taylorSinX(double x, int n) {
        return Math.pow(-1,n-1) * Math.pow(x, (2 * n - 1)) / MyMath.factorial(2 * n - 1);
    }
}
