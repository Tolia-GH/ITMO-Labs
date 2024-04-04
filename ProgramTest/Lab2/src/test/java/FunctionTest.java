
import org.example.trigofunction.*;
import org.example.trigofunction.frombasefunction.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class FunctionTest {

    @Nested
    class BasicFunctionTest {
        final double accuracy = 0.0000000001;

        @ParameterizedTest
        @CsvFileSource(resources = "inputs/degree_range_two_pi.csv")
        void testSin(double x) {
            System.out.println("Testing for x= " + x);
            System.out.println("-------------------------");
            double resSinX = Sin.getValue(x);
            double resMathSinX = Math.sin(Math.toRadians(x));
            System.out.println("Taylor sin(x) = " + resSinX);
            System.out.println("Math.sin(x)   = " + resMathSinX);
            Assertions.assertEquals(resMathSinX, resSinX, accuracy);
            System.out.println();
        }

        @ParameterizedTest
        @CsvFileSource(resources = "inputs/degree_range_two_pi.csv")
        void testCos(double x) {
            System.out.println("Testing for x= " + x);
            System.out.println("-------------------------");
            double resCosX = Cos.getValue(x);
            double resMathCosX = Math.cos(Math.toRadians(x));
            System.out.println("Taylor cos(x) = " + resCosX);
            System.out.println("Math.cos(x)   = " + resMathCosX);
            Assertions.assertEquals(resMathCosX, resCosX, accuracy);
            System.out.println();
        }

        @ParameterizedTest
        @CsvFileSource(resources = "inputs/degree_range_two_pi.csv")
        void testTan(double x) {
            System.out.println("Testing for x= " + x);
            System.out.println("-------------------------");
            double resTanX = Tan.getValue(x);
            double resMathTanX = Math.tan(Math.toRadians(x));
            System.out.println("Taylor tan(x) = " + resTanX);
            System.out.println("Math.tan(x)   = " + resMathTanX);
            Assertions.assertEquals(resMathTanX, resTanX, accuracy);
            System.out.println();
        }

        @ParameterizedTest
        @CsvFileSource(resources = "inputs/degree_range_two_pi.csv")
        void testCot(double x) {
            System.out.println("Testing for x= " + x);
            System.out.println("-------------------------");
            double resCotX = Cot.getValue(x);
            double resMathCotX = 1 / Math.tan(Math.toRadians(x));
            System.out.println("Taylor cot(x) = " + resCotX);
            System.out.println("Math.cot(x)   = " + resMathCotX);
            Assertions.assertEquals(resMathCotX, resCotX, accuracy);
            System.out.println();
        }

        @ParameterizedTest
        @CsvFileSource(resources = "inputs/degree_range_two_pi.csv")
        void testSec(double x) {
            System.out.println("Testing for x= " + x);
            System.out.println("-------------------------");
            double resSecX = Sec.getValue(x);
            double resMathSecX = 1 / Math.cos(Math.toRadians(x));
            System.out.println("Taylor sec(x) = " + resSecX);
            System.out.println("Math.sec(x)   = " + resMathSecX);
            Assertions.assertEquals(resMathSecX, resSecX, accuracy);
            System.out.println();
        }

        @ParameterizedTest
        @CsvFileSource(resources = "inputs/degree_range_two_pi.csv")
        void testCsc(double x) {
            System.out.println("Testing for x= " + x);
            System.out.println("-------------------------");
            double resCscX = Csc.getValue(x);
            double resMathCscX = 1 / Math.sin(Math.toRadians(x));
            System.out.println("Taylor csc(x) = " + resCscX);
            System.out.println("Math.csc(x)   = " + resMathCscX);
            Assertions.assertEquals(resMathCscX, resCscX, accuracy);
            System.out.println();
        }
    }
}
