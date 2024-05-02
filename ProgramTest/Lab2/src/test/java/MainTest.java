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
import org.junit.jupiter.params.provider.ValueSource;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;

public class MainTest {
    final double targetAcc = 0.000001;
    final int terms = 50;
    final int taylorTerms = 20;
    final double testAcc = 3;

    @Nested
    class TestWithStub {

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
            filePath.add(fileCos);
            filePath.add(fileTan);
            filePath.add(fileCot);
            filePath.add(fileSec);
            filePath.add(fileCsc);
            filePath.add(fileLn);
            filePath.add(fileLog2);
            filePath.add(fileLog3);
            filePath.add(fileLog5);
            filePath.add(fileLg);

            for (int i = 0; i < filePath.size(); i++) {
                try (FileInputStream fis = new FileInputStream(filePath.get(i));
                     InputStreamReader isr = new InputStreamReader(fis,
                             StandardCharsets.UTF_8);
                     CSVReader reader = new CSVReader(isr)) {
                    String[] nextLine;

                    while ((nextLine = reader.readNext()) != null) {
                        switch (i) {
                            case 0:
                                double x = Double.parseDouble(nextLine[0]);
                                double res = Double.parseDouble(nextLine[1]);
                                Mockito.when(sinMock.getValue(eq(Double.parseDouble(nextLine[0])), anyDouble(), anyInt())).thenReturn(Double.parseDouble(nextLine[1]));
                                Mockito.when(sinMock.getTaylorRes(eq(Double.parseDouble(nextLine[0])), anyInt())).thenReturn(Double.parseDouble(nextLine[1]));
                                break;
                            case 1:
                                Mockito.when(cosMock.getValue(eq(Double.parseDouble(nextLine[0])), anyDouble(), anyInt())).thenReturn(Double.parseDouble(nextLine[1]));
                                Mockito.when(cosMock.getTaylorRes(eq(Double.parseDouble(nextLine[0])), anyInt())).thenReturn(Double.parseDouble(nextLine[1]));
                                break;
                            case 2:
                                Mockito.when(tanMock.getValue(eq(Double.parseDouble(nextLine[0])), anyDouble(), anyInt())).thenReturn(Double.parseDouble(nextLine[1]));
                                Mockito.when(tanMock.getTaylorRes(eq(Double.parseDouble(nextLine[0])), anyInt())).thenReturn(Double.parseDouble(nextLine[1]));
                                break;
                            case 3:
                                Mockito.when(cotMock.getValue(eq(Double.parseDouble(nextLine[0])), anyDouble(), anyInt())).thenReturn(Double.parseDouble(nextLine[1]));
                                Mockito.when(cotMock.getTaylorRes(eq(Double.parseDouble(nextLine[0])), anyInt())).thenReturn(Double.parseDouble(nextLine[1]));
                                break;
                            case 4:
                                Mockito.when(secMock.getValue(eq(Double.parseDouble(nextLine[0])), anyDouble(), anyInt())).thenReturn(Double.parseDouble(nextLine[1]));
                                Mockito.when(secMock.getTaylorRes(eq(Double.parseDouble(nextLine[0])), anyInt())).thenReturn(Double.parseDouble(nextLine[1]));
                                break;
                            case 5:
                                Mockito.when(cscMock.getValue(eq(Double.parseDouble(nextLine[0])), anyDouble(), anyInt())).thenReturn(Double.parseDouble(nextLine[1]));
                                Mockito.when(cscMock.getTaylorRes(eq(Double.parseDouble(nextLine[0])), anyInt())).thenReturn(Double.parseDouble(nextLine[1]));
                                break;
                            case 6:
                                Mockito.when(lnMock.getValue(eq(Double.parseDouble(nextLine[0])), anyDouble(), anyInt())).thenReturn(Double.parseDouble(nextLine[1]));
                                Mockito.when(lnMock.getTaylorRes(eq(Double.parseDouble(nextLine[0])), anyInt())).thenReturn(Double.parseDouble(nextLine[1]));
                                break;
                            case 7:
                                Mockito.when(logMock.getValue(eq(2.0), eq(Double.parseDouble(nextLine[0])), anyDouble(), anyInt())).thenReturn(Double.parseDouble(nextLine[1]));
                                Mockito.when(logMock.getTaylorRes(eq(2.0), eq(Double.parseDouble(nextLine[0])), anyInt())).thenReturn(Double.parseDouble(nextLine[1]));
                                break;
                            case 8:
                                Mockito.when(logMock.getValue(eq(3.0), eq(Double.parseDouble(nextLine[0])), anyDouble(), anyInt())).thenReturn(Double.parseDouble(nextLine[1]));
                                Mockito.when(logMock.getTaylorRes(eq(3.0), eq(Double.parseDouble(nextLine[0])), anyInt())).thenReturn(Double.parseDouble(nextLine[1]));
                                break;
                            case 9:
                                Mockito.when(logMock.getValue(eq(5.0), eq(Double.parseDouble(nextLine[0])), anyDouble(), anyInt())).thenReturn(Double.parseDouble(nextLine[1]));
                                Mockito.when(logMock.getTaylorRes(eq(5.0), eq(Double.parseDouble(nextLine[0])), anyInt())).thenReturn(Double.parseDouble(nextLine[1]));
                                break;
                            case 10:
                                Mockito.when(lgMock.getValue(eq(Double.parseDouble(nextLine[0])), anyDouble(), anyInt())).thenReturn(Double.parseDouble(nextLine[1]));
                                Mockito.when(lgMock.getTaylorRes(eq(Double.parseDouble(nextLine[0])), anyInt())).thenReturn(Double.parseDouble(nextLine[1]));
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

        @Nested
        class MockFunctionTest {
            @Nested
            class LegalInputMockTest {
                @Nested
                class TrigFunctionMockTest {
                    @ParameterizedTest
                    @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
                        //@CsvFileSource(resources = "inputs/illegal_inputs.csv")
                    void testSinMock(double x){
                        Sin sin = new Sin();
                        System.out.println("radian of x= " + x);
                        System.out.println("degree of x= " + Math.toDegrees(x));
                        System.out.println("-------------------------");
                        double resMock = sinMock.getValue(x, targetAcc, terms);
                        double res = sin.getValue(x, targetAcc, terms);
                        System.out.println("Sin(x) = " + res);
                        System.out.println("cosMock = " + resMock);
                        assertEquals(res, resMock, testAcc);
                        System.out.println();
                    }

                    @ParameterizedTest
                    @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
                        //@CsvFileSource(resources = "inputs/illegal_inputs.csv")
                    void testCosMock(double x){
                        Cos cos = new Cos();
                        System.out.println("radian of x= " + x);
                        System.out.println("degree of x= " + Math.toDegrees(x));
                        System.out.println("-------------------------");
                        double resMock = cosMock.getValue(x, targetAcc, terms);
                        double res = cos.getValue(x, targetAcc, terms);
                        System.out.println("Cos(x) = " + res);
                        System.out.println("cosMock = " + resMock);
                        assertEquals(res, resMock, testAcc);
                        System.out.println();
                    }

                    @ParameterizedTest
                    @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
                        //@CsvFileSource(resources = "inputs/illegal_inputs.csv")
                    void testTanMock(double x){
                        Tan tan = new Tan();
                        System.out.println("radian of x= " + x);
                        System.out.println("degree of x= " + Math.toDegrees(x));
                        System.out.println("-------------------------");
                        double resMock = tanMock.getValue(x, targetAcc, terms);
                        double res = tan.getValue(x, targetAcc, terms);
                        System.out.println("Tan(x) = " + res);
                        System.out.println("tanMock = " + resMock);
                        assertEquals(res, resMock, testAcc);
                        System.out.println();
                    }

                    @ParameterizedTest
                    @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
                        //@CsvFileSource(resources = "inputs/illegal_inputs.csv")
                    void testCotMock(double x){
                        Cot cot = new Cot();
                        System.out.println("radian of x= " + x);
                        System.out.println("degree of x= " + Math.toDegrees(x));
                        System.out.println("-------------------------");
                        double resMock = cotMock.getValue(x, targetAcc, terms);
                        double res = cot.getValue(x, targetAcc, terms);
                        System.out.println("Cot(x) = " + res);
                        System.out.println("cotMock = " + resMock);
                        assertEquals(res, resMock, testAcc);
                        System.out.println();
                    }

                    @ParameterizedTest
                    @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
                        //@CsvFileSource(resources = "inputs/illegal_inputs.csv")
                    void testSecMock(double x){
                        Sec sec = new Sec();
                        System.out.println("radian of x= " + x);
                        System.out.println("degree of x= " + Math.toDegrees(x));
                        System.out.println("-------------------------");
                        double resMock = secMock.getValue(x, targetAcc, terms);
                        double res = sec.getValue(x, targetAcc, terms);
                        System.out.println("Sec(x) = " + res);
                        System.out.println("secMock = " + resMock);
                        assertEquals(res, resMock, testAcc);
                        System.out.println();
                    }

                    @ParameterizedTest
                    @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
                        //@CsvFileSource(resources = "inputs/illegal_inputs.csv")
                    void testCscMock(double x){
                        Csc csc = new Csc();
                        System.out.println("radian of x= " + x);
                        System.out.println("degree of x= " + Math.toDegrees(x));
                        System.out.println("-------------------------");
                        double resMock = cscMock.getValue(x, targetAcc, terms);
                        double res = csc.getValue(x, targetAcc, terms);
                        System.out.println("Csc(x) = " + res);
                        System.out.println("cscMock = " + resMock);
                        assertEquals(res, resMock, testAcc);
                        System.out.println();
                    }
                }
                @Nested
                class LogFunctionMockTest {
                    @ParameterizedTest
                    @CsvFileSource(resources = "inputs/0.01~2(0.01)~100(3).csv")
                        //@CsvFileSource(resources = "inputs/illegal_inputs.csv")
                    void testLnMock(double x){
                        Ln ln = new Ln();
                        System.out.println("x= " + x);
                        System.out.println("-------------------------");
                        double resMock = lnMock.getValue(x, targetAcc, terms);
                        double res = ln.getValue(x, targetAcc, terms);
                        System.out.println("Ln(x) = " + res);
                        System.out.println("lnMock = " + resMock);
                        assertEquals(res, resMock, testAcc);
                        System.out.println();
                    }

                    @ParameterizedTest
                    @CsvFileSource(resources = "inputs/0.01~2(0.01)~100(3).csv")
                        //@CsvFileSource(resources = "inputs/illegal_inputs.csv")
                    void testLog2Mock(double x){
                        Log log2 = new Log();
                        System.out.println("x= " + x);
                        System.out.println("-------------------------");
                        double resMock = logMock.getValue(2, x, targetAcc, terms);
                        double res = log2.getValue(2, x, targetAcc, terms);
                        System.out.println("Lpg2(x) = " + res);
                        System.out.println("log2Mock = " + resMock);
                        assertEquals(res, resMock, testAcc);
                        System.out.println();
                    }

                    @ParameterizedTest
                    @CsvFileSource(resources = "inputs/0.01~2(0.01)~100(3).csv")
                        //@CsvFileSource(resources = "inputs/illegal_inputs.csv")
                    void testLog3Mock(double x){
                        Log log3 = new Log();
                        System.out.println("x= " + x);
                        System.out.println("-------------------------");
                        double resMock = logMock.getValue(3, x, targetAcc, terms);
                        double res = log3.getValue(3, x, targetAcc, terms);
                        System.out.println("Log3(x) = " + res);
                        System.out.println("log3Mock = " + resMock);
                        assertEquals(res, resMock, testAcc);
                        System.out.println();
                    }

                    @ParameterizedTest
                    @CsvFileSource(resources = "inputs/0.01~2(0.01)~100(3).csv")
                        //@CsvFileSource(resources = "inputs/illegal_inputs.csv")
                    void testLog5Mock(double x){
                        Log log5 = new Log();
                        System.out.println("x= " + x);
                        System.out.println("-------------------------");
                        double resMock = logMock.getValue(5, x, targetAcc, terms);
                        double res = log5.getValue(5, x, targetAcc, terms);
                        System.out.println("Log5(x) = " + res);
                        System.out.println("log5Mock = " + resMock);
                        assertEquals(res, resMock, testAcc);
                        System.out.println();
                    }

                    @ParameterizedTest
                    @CsvFileSource(resources = "inputs/0.01~2(0.01)~100(3).csv")
                        //@CsvFileSource(resources = "inputs/illegal_inputs.csv")
                    void testLgMock(double x){
                        Lg lg = new Lg();
                        System.out.println("x= " + x);
                        System.out.println("-------------------------");
                        double resMock = lgMock.getValue(x, targetAcc, terms);
                        double res = lg.getValue( x, targetAcc, terms);
                        System.out.println("Lg(x) = " + res);
                        System.out.println("lgMock = " + resMock);
                        assertEquals(res, resMock, testAcc);
                        System.out.println();
                    }
                }
                @Nested
                class TrigFunctionTest {
                    @ParameterizedTest
                    @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
                        //@CsvFileSource(resources = "inputs/illegal_inputs.csv")
                    void testSin(double x){
                        Sin sin = new Sin();
                        System.out.println("radian of x= " + x);
                        System.out.println("degree of x= " + Math.toDegrees(x));
                        System.out.println("-------------------------");
                        EquationA equationA = new EquationA(sin, cosMock, tanMock, cotMock, secMock, cscMock);
                        double resMock = equationA.getValue(x, targetAcc, terms);
                        double res = equationA.getMathValue(x);
                        assertEquals(res, resMock, testAcc);
                        System.out.println();
                    }

                    @ParameterizedTest
                    @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
                        //@CsvFileSource(resources = "inputs/illegal_inputs.csv")
                    void testCos(double x){
                        Cos cos = new Cos();
                        System.out.println("radian of x= " + x);
                        System.out.println("degree of x= " + Math.toDegrees(x));
                        System.out.println("-------------------------");
                        EquationA equationA = new EquationA(sinMock, cos, tanMock, cotMock, secMock, cscMock);
                        double resMock = equationA.getValue(x, targetAcc, terms);
                        double res = equationA.getMathValue(x);
                        assertEquals(res, resMock, testAcc);
                        System.out.println();
                    }

                    @ParameterizedTest
                    @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
                        //@CsvFileSource(resources = "inputs/illegal_inputs.csv")
                    void testTanMock(double x){
                        Tan tan = new Tan();
                        System.out.println("radian of x= " + x);
                        System.out.println("degree of x= " + Math.toDegrees(x));
                        System.out.println("-------------------------");
                        EquationA equationA = new EquationA(sinMock, cosMock, tan, cotMock, secMock, cscMock);
                        double resMock = equationA.getValue(x, targetAcc, terms);
                        double res = equationA.getMathValue(x);
                        assertEquals(res, resMock, testAcc);
                        System.out.println();
                    }

                    @ParameterizedTest
                    @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
                        //@CsvFileSource(resources = "inputs/illegal_inputs.csv")
                    void testCotMock(double x){
                        Cot cot = new Cot();
                        System.out.println("radian of x= " + x);
                        System.out.println("degree of x= " + Math.toDegrees(x));
                        System.out.println("-------------------------");
                        EquationA equationA = new EquationA(sinMock, cosMock, tanMock, cot, secMock, cscMock);
                        double resMock = equationA.getValue(x, targetAcc, terms);
                        double res = equationA.getMathValue(x);
                        assertEquals(res, resMock, testAcc);
                        System.out.println();
                    }

                    @ParameterizedTest
                    @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
                        //@CsvFileSource(resources = "inputs/illegal_inputs.csv")
                    void testSecMock(double x){
                        Sec sec = new Sec();
                        System.out.println("radian of x= " + x);
                        System.out.println("degree of x= " + Math.toDegrees(x));
                        System.out.println("-------------------------");
                        EquationA equationA = new EquationA(sinMock, cosMock, tanMock, cotMock, sec, cscMock);
                        double resMock = equationA.getValue(x, targetAcc, terms);
                        double res = equationA.getMathValue(x);
                        assertEquals(res, resMock, testAcc);
                        System.out.println();
                    }

                    @ParameterizedTest
                    @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
                        //@CsvFileSource(resources = "inputs/illegal_inputs.csv")
                    void testCscMock(double x){
                        Csc csc = new Csc();
                        System.out.println("radian of x= " + x);
                        System.out.println("degree of x= " + Math.toDegrees(x));
                        System.out.println("-------------------------");
                        EquationA equationA = new EquationA(sinMock, cosMock, tanMock, cotMock, secMock, csc);
                        double resMock = equationA.getValue(x, targetAcc, terms);
                        double res = equationA.getMathValue(x);
                        assertEquals(res, resMock, testAcc);
                        System.out.println();
                    }
                }
                @Nested
                class LogFunctionTest {
                    @ParameterizedTest
                    @CsvFileSource(resources = "inputs/0.01~0.99~1.01~2(0.01)~100(3).csv")
                        //@CsvFileSource(resources = "inputs/illegal_inputs.csv")
                    void testLnMock(double x){
                        Ln ln = new Ln();
                        System.out.println("x= " + x);
                        System.out.println("-------------------------");
                        EquationB equationB = new EquationB(ln, logMock, lgMock);
                        double resMock = equationB.getValue(x, targetAcc, terms);
                        double res = equationB.getMathValue(x);
                        assertEquals(res, resMock, testAcc);
                        System.out.println();
                    }

                    @ParameterizedTest
                    @CsvFileSource(resources = "inputs/0.01~0.99~1.01~2(0.01)~100(3).csv")
                        //@CsvFileSource(resources = "inputs/illegal_inputs.csv")
                    void testLogMock(double x){
                        Log log = new Log();
                        System.out.println("x= " + x);
                        System.out.println("-------------------------");
                        EquationB equationB = new EquationB(lnMock, log, lgMock);
                        double resMock = equationB.getValue(x, targetAcc, terms);
                        double res = equationB.getMathValue(x);
                        assertEquals(res, resMock, testAcc);
                        System.out.println();
                    }

                    @ParameterizedTest
                    @CsvFileSource(resources = "inputs/0.01~0.99~1.01~2(0.01)~100(3).csv")
                        //@CsvFileSource(resources = "inputs/illegal_inputs.csv")
                    void testLgMock(double x){
                        Lg lg = new Lg();
                        System.out.println("x= " + x);
                        System.out.println("-------------------------");
                        EquationB equationB = new EquationB(lnMock, logMock, lg);
                        double resMock = equationB.getValue(x, targetAcc, terms);
                        double res = equationB.getMathValue(x);
                        assertEquals(res, resMock, testAcc);
                        System.out.println();
                    }
                }
            }
        }

        @Nested
        class MockEquationTest {
            @Nested
            class LegalInputMockTest {
                @Nested
                class EquationSystemMockTest {
                    @ParameterizedTest
                    @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
                        //@CsvFileSource(resources = "inputs/illegal_inputs.csv")
                    void testSystemMock(double x){
                        EquationA a = new EquationA(sinMock, cosMock, tanMock, cotMock, secMock, cscMock);
                        EquationB b = new EquationB(lnMock, logMock, lgMock);
                        EquationSystem equations = new EquationSystem(a, b);
                        double resSystem = equations.getValue(x, targetAcc, terms);
                        double resMathSystem = equations.getMathValue(x);
                        System.out.println("res = " + resSystem);
                        System.out.println("resMock = " + resMathSystem);
                        assertEquals(resMathSystem, resSystem, testAcc);
                        System.out.println();
                    }
                }

                @Nested
                class EquationMockTest {
                    @ParameterizedTest
                    @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01).csv")
                        //@CsvFileSource(resources = "inputs/illegal_inputs.csv")
                    void testEquationAMock(double x){
                        EquationA a = new EquationA(sinMock, cosMock, tanMock, cotMock, secMock, cscMock);
                        double resSystem = a.getValue(x, targetAcc, terms);
                        double resMathSystem = a.getMathValue(x);
                        System.out.println("res = " + resSystem);
                        System.out.println("resMock = " + resMathSystem);
                        assertEquals(resMathSystem, resSystem, testAcc);
                        System.out.println();
                    }

                    @ParameterizedTest
                    @CsvFileSource(resources = "inputs/0.01~0.99~1.01~2(0.01)~100(3).csv")
                        //@CsvFileSource(resources = "inputs/illegal_inputs.csv")
                    void testEquationBMock(double x){
                        EquationB b = new EquationB(lnMock, logMock, lgMock);
                        double resSystem = b.getValue(x, targetAcc, terms);
                        double resMathSystem = b.getMathValue(x);
                        System.out.println("res = " + resSystem);
                        System.out.println("resMock = " + resMathSystem);
                        assertEquals(resMathSystem, resSystem, testAcc);
                        System.out.println();
                    }
                }
            }
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
                @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
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
                @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
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
                @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
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
                @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
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
                @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
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
                @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
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

        @Nested
        class IllegalInputTest {

            @Nested
            class TrigFunctionTest {
                Sin sin = new Sin();
                Cos cos = new Cos();
                Tan tan = new Tan();
                Cot cot = new Cot();
                Sec sec = new Sec();
                Csc csc = new Csc();

                @ParameterizedTest
                @ValueSource(doubles = {Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NaN})
                void testSin(double x) {
                    System.out.println("radian of x= " + x);
                    System.out.println("degree of x= " + Math.toDegrees(x));
                    System.out.println("-------------------------");
                    Assertions.assertThrows(IllegalArgumentException.class, () -> sin.getValue(x, targetAcc, terms));
                    Assertions.assertThrows(IllegalArgumentException.class, () -> sin.getTaylorRes(x, terms));
                    System.out.println();
                }

                @ParameterizedTest
                @ValueSource(doubles = {Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NaN})
                void testCos(double x) {
                    System.out.println("radian of x= " + x);
                    System.out.println("degree of x= " + Math.toDegrees(x));
                    System.out.println("-------------------------");
                    Assertions.assertThrows(IllegalArgumentException.class, () -> cos.getValue(x, targetAcc, terms));
                    Assertions.assertThrows(IllegalArgumentException.class, () -> cos.getTaylorRes(x, terms));
                    System.out.println();
                }

                @ParameterizedTest
                @ValueSource(doubles = {Math.PI / 2, - Math.PI / 2, 3 * Math.PI / 2, - 3 * Math.PI / 2,  Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NaN})
                void testTan(double x) {
                    System.out.println("radian of x= " + x);
                    System.out.println("degree of x= " + Math.toDegrees(x));
                    System.out.println("-------------------------");
                    Assertions.assertThrows(IllegalArgumentException.class, () -> tan.getValue(x, targetAcc, terms));
                    Assertions.assertThrows(IllegalArgumentException.class, () -> tan.getTaylorRes(x, terms));
                    System.out.println();
                }

                @ParameterizedTest
                @ValueSource(doubles = {Math.PI / 2, - Math.PI / 2, 3 * Math.PI / 2, - 3 * Math.PI / 2,  Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NaN})
                void testCot(double x) {
                    System.out.println("radian of x= " + x);
                    System.out.println("degree of x= " + Math.toDegrees(x));
                    System.out.println("-------------------------");
                    Assertions.assertThrows(IllegalArgumentException.class, () -> cot.getValue(x, targetAcc, terms));
                    Assertions.assertThrows(IllegalArgumentException.class, () -> cot.getTaylorRes(x, terms));
                    System.out.println();
                }

                @ParameterizedTest
                @ValueSource(doubles = {Math.PI / 2, - Math.PI / 2, 3 * Math.PI / 2, - 3 * Math.PI / 2,  Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NaN})
                void testSec(double x) {
                    System.out.println("radian of x= " + x);
                    System.out.println("degree of x= " + Math.toDegrees(x));
                    System.out.println("-------------------------");
                    Assertions.assertThrows(IllegalArgumentException.class, () -> sec.getValue(x, targetAcc, terms));
                    Assertions.assertThrows(IllegalArgumentException.class, () -> sec.getTaylorRes(x, terms));
                    System.out.println();
                }

                @ParameterizedTest
                @ValueSource(doubles = {Math.PI / 2, - Math.PI / 2, 3 * Math.PI / 2, - 3 * Math.PI / 2,  Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NaN})
                void testCsc(double x) {
                    System.out.println("radian of x= " + x);
                    System.out.println("degree of x= " + Math.toDegrees(x));
                    System.out.println("-------------------------");
                    Assertions.assertThrows(IllegalArgumentException.class, () -> csc.getValue(x, targetAcc, terms));
                    Assertions.assertThrows(IllegalArgumentException.class, () -> csc.getTaylorRes(x, terms));
                    System.out.println();
                }
            }

            @Nested
            class LogFunctionTest {
                Ln ln = new Ln();
                Log log = new Log();
                Lg lg = new Lg();

                @ParameterizedTest
                @ValueSource(doubles = {0, -1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NaN})
                void testLn(double x) {
                    System.out.println("Testing for x= " + x);
                    System.out.println("-------------------------");
                    Assertions.assertThrows(IllegalArgumentException.class, () -> ln.getValue(x, targetAcc, terms));
                    Assertions.assertThrows(IllegalArgumentException.class, () -> ln.getTaylorRes(x, terms));
                    System.out.println();
                }

                @ParameterizedTest
                @ValueSource(doubles = {0, -1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NaN})
                void testLog2(double x) {
                    System.out.println("Testing for x= " + x);
                    System.out.println("-------------------------");
                    Assertions.assertThrows(IllegalArgumentException.class, () -> log.getValue(2, x, targetAcc, terms));
                    Assertions.assertThrows(IllegalArgumentException.class, () -> log.getTaylorRes(2, x, terms));
                    System.out.println();
                }

                @ParameterizedTest
                @ValueSource(doubles = {0, -1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NaN})
                void testLog3(double x) {
                    System.out.println("Testing for x= " + x);
                    System.out.println("-------------------------");
                    Assertions.assertThrows(IllegalArgumentException.class, () -> log.getValue(3, x, targetAcc, terms));
                    Assertions.assertThrows(IllegalArgumentException.class, () -> log.getTaylorRes(3, x, terms));
                    System.out.println();
                }

                @ParameterizedTest
                @ValueSource(doubles = {0, -1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NaN})
                void testLog5(double x) {
                    System.out.println("Testing for x= " + x);
                    System.out.println("-------------------------");
                    Assertions.assertThrows(IllegalArgumentException.class, () -> log.getValue(5, x, targetAcc, terms));
                    Assertions.assertThrows(IllegalArgumentException.class, () -> log.getTaylorRes(5, x, terms));
                    System.out.println();
                }

                @ParameterizedTest
                @ValueSource(doubles = {0, -1, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NaN})
                void testLg(double x) {
                    System.out.println("Testing for x= " + x);
                    System.out.println("-------------------------");
                    Assertions.assertThrows(IllegalArgumentException.class, () -> lg.getValue(x, targetAcc, terms));
                    Assertions.assertThrows(IllegalArgumentException.class, () -> lg.getTaylorRes(x, terms));
                    System.out.println();
                }
            }
        }
    }

    @Nested
    class EquationsTest {
        EquationA equationA = new EquationA();
        EquationB equationB = new EquationB();

        EquationSystem system = new EquationSystem(equationA, equationB);

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
                @CsvFileSource(resources = "inputs/0.01~0.99~1.01~2(0.01)~100(3).csv")
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
                @CsvFileSource(resources = "inputs/-100~2(3)~-0.01(0.01)~0.01~0.99~1.00~2(0.01)~100(3).csv")
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

        @Nested
        class illegalInputTest {

            @Nested
            class EquationTest {
                @ParameterizedTest
                @ValueSource(doubles = {Math.PI / 2, - Math.PI / 2, 3 * Math.PI / 2, - 3 * Math.PI / 2,  Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NaN})
                void testEquationA(double x) {
                    System.out.println("radian of x= " + x);
                    System.out.println("degree of x= " + Math.toDegrees(x));
                    System.out.println("-------------------------");
                    Assertions.assertThrows(IllegalArgumentException.class, () -> equationA.getValue(x, targetAcc, terms));
                    System.out.println();
                }

                @ParameterizedTest
                @ValueSource(doubles = {-1, 0, 1, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NaN})
                void testEquationB(double x) {
                    System.out.println("x= " + x);
                    System.out.println("-------------------------");
                    Assertions.assertThrows(IllegalArgumentException.class, () -> equationB.getValue(x, targetAcc, terms));
                    System.out.println();
                }
            }

            @Nested
            class EquationSystemTest {
                @ParameterizedTest
                @ValueSource(doubles = {- Math.PI / 2, - 3 * Math.PI / 2, 0, 1,  Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NaN})
                void testEquationSystem(double x) {
                    System.out.println("x= " + x);
                    System.out.println("-------------------------");
                    Assertions.assertThrows(IllegalArgumentException.class, () -> system.getValue(x, targetAcc, terms));
                    System.out.println();
                }
            }
        }
    }
}
