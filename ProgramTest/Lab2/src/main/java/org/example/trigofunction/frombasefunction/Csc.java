package org.example.trigofunction.frombasefunction;

import org.example.trigofunction.Cos;
import org.example.trigofunction.Sin;

public class Csc {
    public static double getValue(double degree) {
        return 1/Sin.getValue(degree);
    }
}
