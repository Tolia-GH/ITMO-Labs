package org.example.trigofunction;

import org.example.Function;
import org.example.tools.MyMath;

public class Sin extends Function {

    private static double initDegree(double degree) {
        if (Double.isInfinite(degree) || Double.isNaN(degree)) {
            throw new IllegalArgumentException("Value invalid");
        }

        degree = degree % 360;

        System.out.println("degree = " + degree);

        return Math.toRadians(degree);
    }

    public static double getValue(double degree, double acc, int terms) {
        double x = initDegree(degree);

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
        double resSin = 0;
        for (int i = 1; i <= terms; i++) {
            resSin += taylorSinX(x, i);
        }

        return resSin;
    }

    private static double taylorSinX(double x, int n) {
        return Math.pow(-1,n-1) * Math.pow(x, (2 * n - 1)) / MyMath.factorial(2 * n - 1);
    }
}
