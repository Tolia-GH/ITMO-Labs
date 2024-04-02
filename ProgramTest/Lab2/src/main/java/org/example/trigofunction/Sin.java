package org.example.trigofunction;

import org.example.Function;

public class Sin extends Function {
    @Override
    public double getValue(double x, double acc) {
        return 0;
    }

    private static int factorial(int n) {
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    private static double taylorSinX(double x, int n) {
        return (Math.pow(-1,n-1) * Math.pow(x, (2 * n - 1))) / factorial(2 * n - 1);
    }
}
