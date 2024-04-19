package org.tolia.function.trigfunction.frombasefunction;

import org.tolia.Function;
import org.tolia.function.trigfunction.Cos;
import org.tolia.function.trigfunction.Sin;

public class Tan extends Function {
    Sin sin = new Sin();
    Cos cos = new Cos();

    public double initDegree(double x) {
        if (Double.isInfinite(x) || Double.isNaN(x)) {
            throw  new IllegalArgumentException("Value Invalid");
        }

        x = x % (Math.PI);

        if (x == Math.PI / 2 || x == - Math.PI / 2) {
            throw  new IllegalArgumentException("Value Invalid");
        }

        return x;
    }

    public double getValue(double x, double acc, int terms) {
        //return Sin.getValue(degree, acc , terms) / Cos.getValue(degree, acc, terms);


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
        x = initDegree(x);
        return sin.getTaylorRes(x , terms) / cos.getTaylorRes(x, terms);
    }
}
