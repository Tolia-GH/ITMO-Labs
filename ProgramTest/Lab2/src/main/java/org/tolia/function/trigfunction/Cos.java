package org.tolia.function.trigfunction;

import org.tolia.Function;
import org.tolia.tools.MyMath;

public class Cos extends Function {

    private static double initDegree(double degree) {
        if (Double.isInfinite(degree) || Double.isNaN(degree)) {
            throw new IllegalArgumentException("Value invalid");
        }

        degree = degree % 360;

        //System.out.println("degree = " + degree);

        return Math.toRadians(degree);
    }

    public double getValue(double degree, double acc, int terms) {
        double x = initDegree(degree);

        double resLast = 0;
        double res = getTaylorRes(x, terms);

        while (Math.abs(res - resLast) > acc) {
            terms++;
            resLast = res;
            res = getTaylorRes(x, terms);
        }
        System.out.println("Cos Taylor terms = " + terms);
        return res;
    }

    public double getTaylorRes(double x, int terms) {
        x = x % (2 * Math.PI);
        double resCos = 0;
        for (int i = 1; i <= terms; i++) {
            resCos += taylorCosX(x, i);
        }

        return resCos;
    }

    private static double taylorCosX(double x, int n) {
        return (Math.pow(-1, n - 1) * Math.pow(x, (2 * n - 2))) / MyMath.factorial(2 * n - 2);
    }
}
