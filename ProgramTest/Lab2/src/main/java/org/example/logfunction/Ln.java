package org.example.logfunction;

import org.example.tools.MyMath;

public class Ln {
    public static double getValue(double x) {
        int tn = 17;

        double resLn = 0;

        for (int i = 1; i <= tn; i++) {
            resLn += taylorLnX(x, i);
        }

        return resLn;
    }

    private static double taylorLnX(double x, int n) {
        double flag = Math.pow(-1,n-1);
        double temp1 = Math.pow(x-1, n);
        double temp2 = n;
        double res = flag * temp1 / temp2;
        return Math.pow(-1,n-1) * Math.pow(x-1, n) / n;
    }
}
