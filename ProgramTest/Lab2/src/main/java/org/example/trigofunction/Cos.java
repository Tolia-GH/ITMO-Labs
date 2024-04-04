package org.example.trigofunction;

import org.example.Function;
import org.example.tools.MyMath;

public class Cos extends Function {

    private static double initDegree(double degree) {
        if (Double.isInfinite(degree) || Double.isNaN(degree)) {
            throw new IllegalArgumentException("Value invalid");
        }

        degree = degree % 360;

        System.out.println("degree = " + degree);

        return Math.toRadians(degree);
    }

    public static double getValue(double degree) {
        double x = initDegree(degree);
        int tn = 20;

        double resCos = 0;

        for (int i = 1; i <= tn; i++) {
            resCos += taylorCosX(x, i);
        }

        return resCos;
    }

    private static double taylorCosX(double x, int n) {
        return (Math.pow(-1, n - 1) * Math.pow(x, (2 * n - 2))) / MyMath.factorial(2 * n - 2);
    }
}

