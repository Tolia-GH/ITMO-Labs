package org.tolia.expression;

import lombok.AllArgsConstructor;
import org.tolia.Function;
import org.tolia.function.trigfunction.Cos;
import org.tolia.function.trigfunction.Sin;
import org.tolia.function.trigfunction.frombasefunction.Cot;
import org.tolia.function.trigfunction.frombasefunction.Csc;
import org.tolia.function.trigfunction.frombasefunction.Sec;
import org.tolia.function.trigfunction.frombasefunction.Tan;

/**
 * The type Complex function A.
 */

public class EquationA extends Function {
    Sin sin;
    Cos cos;
    Tan tan;
    Cot cot;
    Sec sec;
    Csc csc;

    public EquationA(){
        sin = new Sin();
        cos = new Cos();
        tan = new Tan();
        cot = new Cot();
        sec = new Sec();
        csc = new Csc();
    }

    public EquationA(Sin sin, Cos cos, Tan tan, Cot cot, Sec sec, Csc csc){
        this.sin = sin;
        this.cos = cos;
        this.tan = tan;
        this.cot = cot;
        this.sec = sec;
        this.csc = csc;
    }


    public double expression(double x, int terms) {

        double res = (
            (
                (
                    (
                        (
                            tan.getTaylorRes(x, terms) - csc.getTaylorRes(x, terms)
                        ) / cos.getTaylorRes(x, terms)
                    ) / sin.getTaylorRes(x, terms)
                ) +
                (
                    cot.getTaylorRes(x, terms) -
                    (csc.getTaylorRes(x, terms) - csc.getTaylorRes(x, terms))
                )
            ) /
            sec.getTaylorRes(x, terms)
        );
        System.out.printf("%s = %-3d, %s = %.20f\n","terms", terms, "res", res);
        return res;
    }

    public double getMathValue(double x) {

        return (
            (
                (
                    (
                        (
                            Math.tan(x) - (1 / Math.sin(x))
                        ) / Math.cos(x)
                    ) / Math.sin(x)
                ) +
                (
                    (1 / Math.tan(x)) -
                    ((1 / Math.sin(x)) - (1 / Math.sin(x)))
                )
            ) /
            (1 / Math.cos(x))
        );
    }
    /**
     * Gets value.
     *
     * @param x the x
     * @return the value
     */
    public double getValue(double x, double acc, int terms) {
        double resLast = 0;
        double res = expression(x, terms);

        while (Math.abs(res - resLast) > acc) {
            terms++;
            resLast = res;
            res = expression(x, terms);
        }
        System.out.println("E Taylor terms = " + terms);
        return res;
    }


}
