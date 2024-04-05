package org.example.trigofunction.frombasefunction;

import org.example.trigofunction.Cos;
import org.example.trigofunction.Sin;

public class Cot {

    private static double initDegree(double degree) {
        if (Double.isInfinite(degree) || Double.isNaN(degree)) {
            throw new IllegalArgumentException("Value invalid");
        }

        degree = degree % 360;

        System.out.println("degree = " + degree);

        return Math.toRadians(degree);
    }

    public static double getValue(double degree, double acc, int terms) {
        //return Sin.getValue(degree, acc , terms) / Cos.getValue(degree, acc, terms);

        double x = initDegree(degree);
        System.out.println("Radian = " + x);

        double resLast = 0;
        double res = Cos.getTaylorRes(x , terms) / Sin.getTaylorRes(x, terms);

        while (Math.abs(res - resLast) > acc) {
            terms++;
            resLast = res;
            res = Cos.getTaylorRes(x , terms) / Sin.getTaylorRes(x, terms);
        }
        System.out.println("Taylor terms = " + terms);
        return res;
    }
}
