package org.itmo;

public class TanX {
    // 计算 n 的阶乘
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

    private static double taylorCosX(double x, int n) {
        return (Math.pow(-1, n - 1) * Math.pow(x, (2 * n - 2))) / factorial(2 * n - 2);
    }

    public static void main(String[] args) {
        double degree = 90;
        degree = ((degree + 90) % 180) - 90; // -90,90
        double x = Math.toRadians(degree);
        int n = 17;

        double resSin = 0;
        double resCos = 0;
        double resTan;

        for (int i = 1; i <= n; i++) {
            resSin += taylorSinX(x, i);
            resCos += taylorCosX(x, i);
        }

        resTan = resSin / resCos;

        System.out.println("Taylor sin(x) = " + resSin);
        System.out.println("Taylor cos(x) = " + resCos);
        System.out.println("Taylor tan(x) = " + resTan);
        System.out.println("Math.sin(x) = " + Math.sin(x));
        System.out.println("Math.cos(x) = " + Math.cos(x));
        System.out.println("Math.tan(x) = " + Math.tan(x));
    }
}
