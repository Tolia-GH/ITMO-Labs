package org.example.trigofunction.frombasefunction;

import org.example.trigofunction.Cos;
import org.example.trigofunction.Sin;

public class Sec {
    public static double getValue(double degree) {
        return 1 / Cos.getValue(degree);
    }
}
