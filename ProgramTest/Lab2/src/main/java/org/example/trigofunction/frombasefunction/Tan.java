package org.example.trigofunction.frombasefunction;

import org.example.trigofunction.Cos;
import org.example.trigofunction.Sin;

public class Tan {

    public static double getValue(double degree) {
        return Sin.getValue(degree) / Cos.getValue(degree);
    }
}
