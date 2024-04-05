/**
 * @author Zhou Hongxiang
 * @variant 9319
 */

import org.junit.jupiter.api.BeforeAll;
import org.tolia.EquationSystem;
import org.tolia.expression.EquationA;
import org.tolia.expression.EquationB;
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

import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

public class MainTest {
    final double targetAcc = 0.0001;
    final int terms = 1;
    final int taylor_terms = 20;
    final double testAcc = 0.1;

    @Nested
    class TestWithStub {
        static Sin sinMock = mock(Sin.class);
        static Cos cosMock = mock(Cos.class);
        static Tan tanMock = mock(Tan.class);
        static Cot cotMock = mock(Cot.class);
        static Sec secMock = mock(Sec.class);
        static Csc cscMock = mock(Csc.class);

        @BeforeAll
        static void init() {

        }
    }

    @Nested
    class FunctionTest {
        @Nested
        class LegalInputTest {
            @Nested
            class TrigFunctionTest {
                Sin sin = new Sin();
                Cos cos = new Cos();
                Tan tan = new Tan();
                Cot cot = new Cot();
                Sec sec = new Sec();
                Csc csc = new Csc();
                @ParameterizedTest
                @CsvFileSource(resources = "inputs/-721~721(2).csv")
                void testSin(double x) {
                    System.out.println("degree of x= " + x);
                    System.out.println("radian of x= " + Math.toRadians(x));
                    System.out.println("-------------------------");
                    double resSinX = sin.getValue(x, targetAcc, terms);
                    double resMathSinX = Math.sin(Math.toRadians(x));
                    System.out.println("Taylor sin(x) = " + resSinX);
                    System.out.println("Taylor sin(x) = " + sin.getTaylorRes(Math.toRadians(x), taylor_terms));
                    System.out.println("Math.sin(x)   = " + resMathSinX);
                    Assertions.assertEquals(resMathSinX, resSinX, testAcc);
                    System.out.println();
                }

                @ParameterizedTest
                @CsvFileSource(resources = "inputs/-721~721(2).csv")
                void testCos(double x) {
                    System.out.println("degree of x= " + x);
                    System.out.println("radian of x= " + Math.toRadians(x));
                    System.out.println("-------------------------");
                    double resCosX = cos.getValue(x, targetAcc, terms);
                    double resMathCosX = Math.cos(Math.toRadians(x));
                    System.out.println("Taylor cos(x) = " + resCosX);
                    System.out.println("Taylor cos(x) = " + cos.getTaylorRes(Math.toRadians(x), taylor_terms));
                    System.out.println("Math.cos(x)   = " + resMathCosX);
                    Assertions.assertEquals(resMathCosX, resCosX, testAcc);
                    System.out.println();
                }

                @ParameterizedTest
                @CsvFileSource(resources = "inputs/-721~721(2).csv")
                void testTan(double x) {
                    System.out.println("degree of x= " + x);
                    System.out.println("radian of x= " + Math.toRadians(x));
                    System.out.println("-------------------------");
                    double resTanX = tan.getValue(x, targetAcc, terms);
                    double resMathTanX = Math.tan(Math.toRadians(x));
                    System.out.println("Taylor tan(x) = " + resTanX);
                    System.out.println("Taylor tan(x) = " + tan.getTaylorRes(Math.toRadians(x), taylor_terms));
                    System.out.println("Math.tan(x)   = " + resMathTanX);
                    Assertions.assertEquals(resMathTanX, resTanX, testAcc);
                    System.out.println();
                }

                @ParameterizedTest
                @CsvFileSource(resources = "inputs/-721~721(2).csv")
                void testCot(double x) {
                    System.out.println("degree of x= " + x);
                    System.out.println("radian of x= " + Math.toRadians(x));
                    System.out.println("-------------------------");
                    double resCotX = cot.getValue(x, targetAcc, terms);
                    double resMathCotX = 1 / Math.tan(Math.toRadians(x));
                    System.out.println("Taylor cot(x) = " + resCotX);
                    System.out.println("Taylor cot(x) = " + cot.getTaylorRes(Math.toRadians(x), taylor_terms));
                    System.out.println("Math.cot(x)   = " + resMathCotX);
                    Assertions.assertEquals(resMathCotX, resCotX, testAcc);
                    System.out.println();
                }

                @ParameterizedTest
                @CsvFileSource(resources = "inputs/-721~721(2).csv")
                void testSec(double x) {
                    System.out.println("degree of x= " + x);
                    System.out.println("radian of x= " + Math.toRadians(x));
                    System.out.println("-------------------------");
                    double resSecX = sec.getValue(x, targetAcc, terms);
                    double resMathSecX = 1 / Math.cos(Math.toRadians(x));
                    System.out.println("Taylor sec(x) = " + resSecX);
                    System.out.println("Taylor sec(x) = " + sec.getTaylorRes(Math.toRadians(x), taylor_terms));
                    System.out.println("Math.sec(x)   = " + resMathSecX);
                    Assertions.assertEquals(resMathSecX, resSecX, testAcc);
                    System.out.println();
                }

                @ParameterizedTest
                @CsvFileSource(resources = "inputs/-721~721(2).csv")
                void testCsc(double x) {
                    System.out.println("degree of x= " + x);
                    System.out.println("radian of x= " + Math.toRadians(x));
                    System.out.println("-------------------------");
                    double resCscX = csc.getValue(x, targetAcc, terms);
                    double resMathCscX = 1 / Math.sin(Math.toRadians(x));
                    System.out.println("Taylor csc(x) = " + resCscX);
                    System.out.println("Taylor csc(x) = " + csc.getTaylorRes(Math.toRadians(x), taylor_terms));
                    System.out.println("Math.csc(x)   = " + resMathCscX);
                    Assertions.assertEquals(resMathCscX, resCscX, testAcc);
                    System.out.println();
                }
            }

            @Nested
            class LogFunctionTest {
                @ParameterizedTest
                @CsvFileSource(resources = "inputs/0.01~2(0.01)~100(3).csv")
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
                @CsvFileSource(resources = "inputs/0.01~2(0.01)~100(3).csv")
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
                @CsvFileSource(resources = "inputs/0.01~2(0.01)~100(3).csv")
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
                @CsvFileSource(resources = "inputs/0.01~2(0.01)~100(3).csv")
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
    class EquationsTest {
        EquationA equationA = new EquationA();
        EquationB equationB= new EquationB();
        @Nested
        class LegalInputTest {
            @Nested
            class EquationTest {
                @ParameterizedTest
                @CsvFileSource(resources = "inputs/-0.01~-2(0.01)~-100(3).csv")
                void testEquationA(double x) {
                    System.out.println("radian of x= " + x);
                    System.out.println("degree of x= " + Math.toDegrees(x));
                    System.out.println("-------------------------");
                    double resExpressionA = equationA.getValue(x, targetAcc, terms);
                    double resMathExpressionA = equationA.getMathValue(x);
                    System.out.println("Taylor expressionA(x) = " + resExpressionA);
                    System.out.println("Math.expressionA(x)  = " + resMathExpressionA);
                    Assertions.assertEquals(resMathExpressionA, resExpressionA, testAcc);
                    System.out.println();
                }

                @ParameterizedTest
                @CsvFileSource(resources = "inputs/0.01~2(0.01)~100(3).csv")
                void testEquationB(double x) {
                    System.out.println("x= " + x);
                    System.out.println("-------------------------");
                    double resExpressionB = equationB.getValue(x, targetAcc, terms);
                    double resMathExpressionB = equationB.getMathValue(x);
                    System.out.println("Taylor expressionB(x) = " + resExpressionB);
                    System.out.println("Math.expressionB(x)  = " + resMathExpressionB);
                    Assertions.assertEquals(resMathExpressionB, resExpressionB, testAcc);
                    System.out.println();
                }
            }

            @Nested
            class EquationSystemTest {
                @ParameterizedTest
                @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~2(0.01)~100(3).csv")
                void testEquationSystem(double x) {
                    System.out.println("x= " + x);
                    System.out.println("-------------------------");
                    double resEquationSystem = new EquationSystem().getValue(x, targetAcc, terms);
                    double resMathEquationSystem = new EquationSystem().getMathValue(x);
                    System.out.println("Taylor expressionB(x) = " + resEquationSystem);
                    System.out.println("Math.expressionB(x)  = " + resMathEquationSystem);
                    Assertions.assertEquals(resMathEquationSystem, resEquationSystem, testAcc);
                    System.out.println();
                }
            }
        }
    }
}
