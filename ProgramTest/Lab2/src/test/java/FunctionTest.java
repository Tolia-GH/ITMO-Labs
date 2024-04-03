import org.example.trigofunction.Cos;
import org.example.trigofunction.Sin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class FunctionTest {

    final double accuracy = 0.001;
    @Nested
    class BasicFunctionTest {
        @Test
        @DisplayName("Test for sin(x)")
        void testSin() {
            for (double i = -721; i <= 721; i+=2) {
                System.out.println("Testing for i= " + i);
                System.out.println("-------------------------");
                double resSinX = Sin.getValue(i);
                double resMathSin = Math.sin(Math.toRadians(i));
                System.out.println("Taylor sin(x) = " + resSinX);
                System.out.println("Math.sin(x) = " + resMathSin);
                Assertions.assertEquals(resSinX, resMathSin, accuracy);
                System.out.println();
            }
        }

        @Test
        @DisplayName("Test for cos(x)")
        void testCos() {
            for (double i = -721; i <= 721; i+=2) {
                System.out.println("Testing for i= " + i);
                System.out.println("-------------------------");
                double resCosX = Cos.getValue(i);
                double resMathCos = Math.cos(Math.toRadians(i));
                System.out.println("Taylor cos(x) = " + resCosX);
                System.out.println("Math.cos(x) = " + resMathCos);
                Assertions.assertEquals(resCosX, resMathCos, accuracy);
                System.out.println();
            }
        }
    }
}
