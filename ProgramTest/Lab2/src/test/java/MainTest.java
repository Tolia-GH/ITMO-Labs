/**
 * @author Zhou Hongxiang
 * @variant 9319
 */

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;
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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class MainTest {

    @Nested
    static class TestWithStub {
        static final double targetAcc = 0.0001;
        static final int terms = 1;
        static final double testAcc = 0.1;

        static Sin sinMock = mock(Sin.class);
        static Cos cosMock = mock(Cos.class);
        static Tan tanMock = mock(Tan.class);
        static Cot cotMock = mock(Cot.class);
        static Sec secMock = mock(Sec.class);
        static Csc cscMock = mock(Csc.class);
        static Ln lnMock = mock(Ln.class);
        static Log logMock = mock(Log.class);
        static Lg lgMock = mock(Lg.class);


        @BeforeAll
        static void initAll() {

            String fileSin = "src/test/resources/stubTables/sin.csv";
            String fileCos = "src/test/resources/stubTables/cos.csv";
            String fileTan = "src/test/resources/stubTables/tan.csv";
            String fileCot = "src/test/resources/stubTables/cot.csv";
            String fileCsc = "src/test/resources/stubTables/csc.csv";
            String fileSec = "src/test/resources/stubTables/sec.csv";
            String fileLn = "src/test/resources/stubTables/ln.csv";
            String fileLog2 = "src/test/resources/stubTables/log2.csv";
            String fileLog3 = "src/test/resources/stubTables/log3.csv";
            String fileLog5 = "src/test/resources/stubTables/log5.csv";
            String fileLg = "src/test/resources/stubTables/lg.csv";

            List<String> filePath = new ArrayList<>();
            filePath.add(fileSin);
            for (int i = 0; i < filePath.size(); i++) {
                try (FileInputStream fis = new FileInputStream(filePath.get(i));
                     InputStreamReader isr = new InputStreamReader(fis,
                             StandardCharsets.UTF_8);
                     CSVReader reader = new CSVReader(isr)) {
                    String[] nextLine;

                    while ((nextLine = reader.readNext()) != null) {
                        switch (i) {
                            case 0:
                                Mockito.when(sinMock.getValue(Double.parseDouble(nextLine[0]), targetAcc, terms)).thenReturn(Double.parseDouble(nextLine[1]));
                                break;
                            default:
                                return;
                        }
                    }
                } catch (IOException | CsvValidationException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        @ParameterizedTest
        @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~2(0.01)~100(3).csv")
        void testSin(double x){
            EquationA a = new EquationA(new Sin(), cosMock, tanMock, cotMock, secMock, cscMock);
            EquationB b = new EquationB(lnMock, logMock, lgMock);
            EquationSystem equations = new EquationSystem(a,b);
            double resSystem = equations.getValue(x, targetAcc, terms);
            double resMathSystem = equations.getMathValue(x);
            assertEquals(resMathSystem, resSystem, testAcc);
        }
    }


    @Nested
    class FunctionTest {
        @Nested
        class LegalInputTest {
            final double targetAcc = 0.0001;
            final int terms = 1;
            final int taylorTerms = 20;
            final double testAcc = 0.1;

            @Nested
            class TrigFunctionTest {
                Sin sin = new Sin();
                Cos cos = new Cos();
                Tan tan = new Tan();
                Cot cot = new Cot();
                Sec sec = new Sec();
                Csc csc = new Csc();

                @ParameterizedTest
                @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~2(0.01)~100(3).csv")
                void testSin(double x) {
                    System.out.println("degree of x= " + x);
                    System.out.println("radian of x= " + Math.toRadians(x));
                    System.out.println("-------------------------");
                    double resSinX = sin.getValue(x, targetAcc, terms);
                    double resMathSinX = Math.sin(x);
                    System.out.println("Taylor sin(x) = " + resSinX);
                    System.out.println("Taylor sin(x) = " + sin.getTaylorRes(x, taylorTerms));
                    System.out.println("Math.sin(x)   = " + resMathSinX);
                    Assertions.assertEquals(resMathSinX, resSinX, testAcc);
                    System.out.println();
                }

                @ParameterizedTest
                @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~2(0.01)~100(3).csv")
                void testCos(double x) {
                    System.out.println("degree of x= " + x);
                    System.out.println("radian of x= " + Math.toRadians(x));
                    System.out.println("-------------------------");
                    double resCosX = cos.getValue(x, targetAcc, terms);
                    double resMathCosX = Math.cos(x);
                    System.out.println("Taylor cos(x) = " + resCosX);
                    System.out.println("Taylor cos(x) = " + cos.getTaylorRes(x, taylorTerms));
                    System.out.println("Math.cos(x)   = " + resMathCosX);
                    Assertions.assertEquals(resMathCosX, resCosX, testAcc);
                    System.out.println();
                }

                @ParameterizedTest
                @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~2(0.01)~100(3).csv")
                void testTan(double x) {
                    System.out.println("degree of x= " + x);
                    System.out.println("radian of x= " + Math.toRadians(x));
                    System.out.println("-------------------------");
                    double resTanX = tan.getValue(x, targetAcc, terms);
                    double resMathTanX = Math.tan(x);
                    System.out.println("Taylor tan(x) = " + resTanX);
                    System.out.println("Taylor tan(x) = " + tan.getTaylorRes(x, taylorTerms));
                    System.out.println("Math.tan(x)   = " + resMathTanX);
                    Assertions.assertEquals(resMathTanX, resTanX, testAcc);
                    System.out.println();
                }

                @ParameterizedTest
                @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~2(0.01)~100(3).csv")
                void testCot(double x) {
                    System.out.println("degree of x= " + x);
                    System.out.println("radian of x= " + Math.toRadians(x));
                    System.out.println("-------------------------");
                    double resCotX = cot.getValue(x, targetAcc, terms);
                    double resMathCotX = 1 / Math.tan(x);
                    System.out.println("Taylor cot(x) = " + resCotX);
                    System.out.println("Taylor cot(x) = " + cot.getTaylorRes(x, taylorTerms));
                    System.out.println("Math.cot(x)   = " + resMathCotX);
                    Assertions.assertEquals(resMathCotX, resCotX, testAcc);
                    System.out.println();
                }

                @ParameterizedTest
                @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~2(0.01)~100(3).csv")
                void testSec(double x) {
                    System.out.println("degree of x= " + x);
                    System.out.println("radian of x= " + Math.toRadians(x));
                    System.out.println("-------------------------");
                    double resSecX = sec.getValue(x, targetAcc, terms);
                    double resMathSecX = 1 / Math.cos(x);
                    System.out.println("Taylor sec(x) = " + resSecX);
                    System.out.println("Taylor sec(x) = " + sec.getTaylorRes(x, taylorTerms));
                    System.out.println("Math.sec(x)   = " + resMathSecX);
                    Assertions.assertEquals(resMathSecX, resSecX, testAcc);
                    System.out.println();
                }

                @ParameterizedTest
                @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~2(0.01)~100(3).csv")
                void testCsc(double x) {
                    System.out.println("degree of x= " + x);
                    System.out.println("radian of x= " + Math.toRadians(x));
                    System.out.println("-------------------------");
                    double resCscX = csc.getValue(x, targetAcc, terms);
                    double resMathCscX = 1 / Math.sin(x);
                    System.out.println("Taylor csc(x) = " + resCscX);
                    System.out.println("Taylor csc(x) = " + csc.getTaylorRes(x, taylorTerms));
                    System.out.println("Math.csc(x)   = " + resMathCscX);
                    Assertions.assertEquals(resMathCscX, resCscX, testAcc);
                    System.out.println();
                }
            }

            @Nested
            class LogFunctionTest {
                Ln ln = new Ln();
                Log log = new Log();
                Lg lg = new Lg();
                final double targetAcc = 0.0001;
                final int terms = 1;
                final double testAcc = 0.1;

                @ParameterizedTest
                @CsvFileSource(resources = "inputs/0.01~2(0.01)~100(3).csv")
                void testLn(double x) {
                    System.out.println("Testing for x= " + x);
                    System.out.println("-------------------------");
                    double resLn = ln.getValue(x, targetAcc, terms);
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
                    double resLog2 = log.getValue(2, x, targetAcc, terms);
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
                    double resLog3 = log.getValue(3, x, targetAcc, terms);
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
                    double resLg = lg.getValue(x, targetAcc, terms);
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
        EquationB equationB = new EquationB();

        @Nested
        class LegalInputTest {
            final double targetAcc = 0.0001;
            final int terms = 1;

            final double testAcc = 0.1;

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
