/**
 * @author Zhou Hongxiang
 * @variant 9319
 */

import org.tolia.expression.ExpressionA;
import org.tolia.expression.ExpressionB;
import org.tolia.function.logfunction.Ln;
import org.tolia.function.logfunction.frombasefunction.Lg;
import org.tolia.function.logfunction.frombasefunction.Log;
import org.tolia.function.trigfunction.Cos;
import org.tolia.function.trigfunction.Sin;
import org.tolia.function.trigfunction.frombasefunction.Cot;
import org.tolia.function.trigfunction.frombasefunction.Csc;
import org.tolia.function.trigfunction.frombasefunction.Sec;
import org.tolia.function.trigfunction.frombasefunction.Tan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class MainTest {
    final double targetAcc = 0.0001;
    final int terms = 1;
    final int taylor_terms = 20;
    final double testAcc = 0.1;

    @Nested
    class FunctionTest {
        @Nested
        class LegalInputTest {
            @Nested
            class TrigFunctionTest {
                @ParameterizedTest
                @CsvFileSource(resources = "inputs/degree_range_two_pi.csv")
                void testSin(double x) {
                    System.out.println("degree of x= " + x);
                    System.out.println("radian of x= " + Math.toRadians(x));
                    System.out.println("-------------------------");
                    double resSinX = Sin.getValue(x, targetAcc, terms);
                    double resMathSinX = Math.sin(Math.toRadians(x));
                    System.out.println("Taylor sin(x) = " + resSinX);
                    System.out.println("Taylor sin(x) = " + Sin.getTaylorRes(Math.toRadians(x), taylor_terms));
                    System.out.println("Math.sin(x)   = " + resMathSinX);
                    Assertions.assertEquals(resMathSinX, resSinX, testAcc);
                    System.out.println();
                }

                @ParameterizedTest
                @CsvFileSource(resources = "inputs/degree_range_two_pi.csv")
                void testCos(double x) {
                    System.out.println("degree of x= " + x);
                    System.out.println("radian of x= " + Math.toRadians(x));
                    System.out.println("-------------------------");
                    double resCosX = Cos.getValue(x, targetAcc, terms);
                    double resMathCosX = Math.cos(Math.toRadians(x));
                    System.out.println("Taylor cos(x) = " + resCosX);
                    System.out.println("Taylor cos(x) = " + Cos.getTaylorRes(Math.toRadians(x), taylor_terms));
                    System.out.println("Math.cos(x)   = " + resMathCosX);
                    Assertions.assertEquals(resMathCosX, resCosX, testAcc);
                    System.out.println();
                }

                @ParameterizedTest
                @CsvFileSource(resources = "inputs/degree_range_two_pi.csv")
                void testTan(double x) {
                    System.out.println("degree of x= " + x);
                    System.out.println("radian of x= " + Math.toRadians(x));
                    System.out.println("-------------------------");
                    double resTanX = Tan.getValue(x, targetAcc, terms);
                    double resMathTanX = Math.tan(Math.toRadians(x));
                    System.out.println("Taylor tan(x) = " + resTanX);
                    System.out.println("Taylor tan(x) = " + Tan.getTaylorRes(Math.toRadians(x), taylor_terms));
                    System.out.println("Math.tan(x)   = " + resMathTanX);
                    Assertions.assertEquals(resMathTanX, resTanX, testAcc);
                    System.out.println();
                }

                @ParameterizedTest
                @CsvFileSource(resources = "inputs/degree_range_two_pi.csv")
                void testCot(double x) {
                    System.out.println("degree of x= " + x);
                    System.out.println("radian of x= " + Math.toRadians(x));
                    System.out.println("-------------------------");
                    double resCotX = Cot.getValue(x, targetAcc, terms);
                    double resMathCotX = 1 / Math.tan(Math.toRadians(x));
                    System.out.println("Taylor cot(x) = " + resCotX);
                    System.out.println("Taylor cot(x) = " + Cot.getTaylorRes(Math.toRadians(x), taylor_terms));
                    System.out.println("Math.cot(x)   = " + resMathCotX);
                    Assertions.assertEquals(resMathCotX, resCotX, testAcc);
                    System.out.println();
                }

                @ParameterizedTest
                @CsvFileSource(resources = "inputs/degree_range_two_pi.csv")
                void testSec(double x) {
                    System.out.println("degree of x= " + x);
                    System.out.println("radian of x= " + Math.toRadians(x));
                    System.out.println("-------------------------");
                    double resSecX = Sec.getValue(x, targetAcc, terms);
                    double resMathSecX = 1 / Math.cos(Math.toRadians(x));
                    System.out.println("Taylor sec(x) = " + resSecX);
                    System.out.println("Taylor sec(x) = " + Sec.getTaylorRes(Math.toRadians(x), taylor_terms));
                    System.out.println("Math.sec(x)   = " + resMathSecX);
                    Assertions.assertEquals(resMathSecX, resSecX, testAcc);
                    System.out.println();
                }

                @ParameterizedTest
                @CsvFileSource(resources = "inputs/degree_range_two_pi.csv")
                void testCsc(double x) {
                    System.out.println("degree of x= " + x);
                    System.out.println("radian of x= " + Math.toRadians(x));
                    System.out.println("-------------------------");
                    double resCscX = Csc.getValue(x, targetAcc, terms);
                    double resMathCscX = 1 / Math.sin(Math.toRadians(x));
                    System.out.println("Taylor csc(x) = " + resCscX);
                    System.out.println("Taylor csc(x) = " + Csc.getTaylorRes(Math.toRadians(x), taylor_terms));
                    System.out.println("Math.csc(x)   = " + resMathCscX);
                    Assertions.assertEquals(resMathCscX, resCscX, testAcc);
                    System.out.println();
                }
            }

            @Nested
            class LogFunctionTest {
                @ParameterizedTest
                @CsvFileSource(resources = "inputs/number_from_zero_to_hundred.csv")
                void testLn(double x) {
                    System.out.println("Testing for x= " + x);
                    System.out.println("-------------------------");
                    double resLn = Ln.getValue(x, targetAcc, terms);
                    double resMathLn = Math.log(x);
                    System.out.println("Taylor ln(x) = " + resLn);
                    System.out.println("Math.ln(x)  = " + resMathLn);
                    Assertions.assertEquals(resMathLn, resLn, testAcc);
                    System.out.println();
                }

                @ParameterizedTest
                @CsvFileSource(resources = "inputs/number_from_zero_to_hundred.csv")
                void testLog2(double x) {
                    System.out.println("Testing for x= " + x);
                    System.out.println("-------------------------");
                    double resLog2 = Log.getValue(2, x, targetAcc, terms);
                    double resMathLog2 = Math.log(x) / Math.log(2);
                    System.out.println("Taylor log2(x) = " + resLog2);
                    System.out.println("Math.log2(x)  = " + resMathLog2);
                    Assertions.assertEquals(resMathLog2, resLog2, testAcc);
                    System.out.println();
                }

                @ParameterizedTest
                @CsvFileSource(resources = "inputs/number_from_zero_to_hundred.csv")
                void testLog3(double x) {
                    System.out.println("Testing for x= " + x);
                    System.out.println("-------------------------");
                    double resLog3 = Log.getValue(3, x, targetAcc, terms);
                    double resMathLog3 = Math.log(x) / Math.log(3);
                    System.out.println("Taylor log3(x) = " + resLog3);
                    System.out.println("Math.log3(x)  = " + resMathLog3);
                    Assertions.assertEquals(resMathLog3, resLog3, testAcc);
                    System.out.println();
                }

                @ParameterizedTest
                @CsvFileSource(resources = "inputs/number_from_zero_to_hundred.csv")
                void testLg(double x) {
                    System.out.println("Testing for x= " + x);
                    System.out.println("-------------------------");
                    double resLg = Lg.getValue(x, targetAcc, terms);
                    double resMathLg = Math.log10(x);
                    System.out.println("Taylor lg(x) = " + resLg);
                    System.out.println("Math.lg(x)  = " + resMathLg);
                    Assertions.assertEquals(resMathLg, resLg, testAcc);
                    System.out.println();
                }
            }
        }
    }

    @Nested
    class ExpressionTest {
        @Nested
        class LegalInputTest {
            @Nested
            class ExpressionATest {
                @ParameterizedTest
                @CsvFileSource(resources = "inputs/degree_range_two_pi.csv")
                void testExpressionA(double x) {
                    System.out.println("degree of x= " + x);
                    System.out.println("radian of x= " + Math.toRadians(x));
                    System.out.println("-------------------------");
                    double resExpressionA = ExpressionA.getValue(x, targetAcc, terms);
                    double resMathExpressionA = ExpressionA.getMathValue(x);
                    System.out.println("Taylor expressionA(x) = " + resExpressionA);
                    System.out.println("Math.expressionA(x)  = " + resMathExpressionA);
                    Assertions.assertEquals(resMathExpressionA, resExpressionA, testAcc);
                    System.out.println();
                }

                @ParameterizedTest
                @CsvFileSource(resources = "inputs/number_from_zero_to_hundred.csv")
                void testExpressionB(double x) {
                    System.out.println("x= " + x);
                    System.out.println("-------------------------");
                    double resExpressionB = ExpressionB.getValue(x, targetAcc, terms);
                    double resMathExpressionB = ExpressionB.getMathValue(x);
                    System.out.println("Taylor expressionB(x) = " + resExpressionB);
                    System.out.println("Math.expressionB(x)  = " + resMathExpressionB);
                    Assertions.assertEquals(resMathExpressionB, resExpressionB, testAcc);
                    System.out.println();
                }
            }
        }
    }




}
