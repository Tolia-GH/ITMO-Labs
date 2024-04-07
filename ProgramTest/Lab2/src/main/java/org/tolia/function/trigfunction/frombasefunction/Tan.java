package org.tolia.function.trigfunction.frombasefunction;

import org.tolia.Function;
import org.tolia.function.trigfunction.Cos;
import org.tolia.function.trigfunction.Sin;

public class Tan extends Function {
    Sin sin = new Sin();
    Cos cos = new Cos();

    public double initDegree(double x) {
        if (Double.isInfinite(x) || Double.isNaN(x)) {
            throw new IllegalArgumentException("Value invalid");
        }

        return x % (2 * Math.PI);

        //System.out.println("degree = " + degree);

        //return Math.toRadians(x);
    }

    public double getValue(double x, double acc, int terms) {
        //return Sin.getValue(degree, acc , terms) / Cos.getValue(degree, acc, terms);

        x = initDegree(x);
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

    public double getTaylorRes(double x, int terms) {
        return sin.getTaylorRes(x , terms) / cos.getTaylorRes(x, terms);
    }
}
