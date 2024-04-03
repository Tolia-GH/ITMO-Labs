package org.example.trigofunction.frombasefunction;

import org.example.trigofunction.Cos;
import org.example.trigofunction.Sin;

public class Cot {

    public static double getValue(double degree) {
        return Cos.getValue(degree) / Sin.getValue(degree);
    }
}
