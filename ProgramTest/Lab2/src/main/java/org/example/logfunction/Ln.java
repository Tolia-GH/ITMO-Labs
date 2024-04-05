package org.example.logfunction;

import org.example.tools.MyMath;

public class Ln {
    private static double init(double x) {
        return x;
    }
    public static double getValue(double x, double acc, int terms) {

        x = init(x);

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
        double resLn = 0;
        for (int i = 1; i <= terms; i++) {
            resLn += taylorLnX(x, i);
        }

        return resLn;
    }

    private static double taylorLnX(double x, int n) {
        double z = (x - 1) / (x + 1);
        double res = (2 * Math.pow(z, 2 * n - 1)) / (2 * n - 1);
        return res;
    }
}
