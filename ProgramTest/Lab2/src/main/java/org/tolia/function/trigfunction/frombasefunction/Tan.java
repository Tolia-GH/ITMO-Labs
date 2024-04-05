package org.tolia.function.trigfunction.frombasefunction;

import org.tolia.function.trigfunction.Cos;
import org.tolia.function.trigfunction.Sin;

public class Tan {

    private static double initDegree(double degree) {
        if (Double.isInfinite(degree) || Double.isNaN(degree)) {
            throw new IllegalArgumentException("Value invalid");
        }

        degree = degree % 360;

        //System.out.println("degree = " + degree);

        return Math.toRadians(degree);
    }

    public static double getValue(double degree, double acc, int terms) {
        //return Sin.getValue(degree, acc , terms) / Cos.getValue(degree, acc, terms);

        double x = initDegree(degree);
        //System.out.println("Radian = " + x);

        double resLast = 0;
        double res = getTaylorRes(x, terms);

        while (Math.abs(res - resLast) > acc) {
            terms++;
            resLast = res;
            res = getTaylorRes(x, terms);
        }
        System.out.println("Tan Taylor terms = " + terms);
        return res;
    }

    public static double getTaylorRes(double x, int terms) {
        return Sin.getTaylorRes(x , terms) / Cos.getTaylorRes(x, terms);
    }
}
