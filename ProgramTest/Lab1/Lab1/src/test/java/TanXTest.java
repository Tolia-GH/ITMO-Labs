import org.itmo.main.TanX;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TanXTest {

    final double accuracy = 5;

    @Test
    @DisplayName("Test for tan(x)")
    public void testTanX() {
        for (double i = -721; i <= 721; i+=2) {
            System.out.println("Testing for i= " + i);
            System.out.println("-------------------------");
            double resTanX = TanX.tanX(i);
            double resMathTan = Math.tan(Math.toRadians(i));
            Assertions.assertEquals(resTanX, resMathTan, accuracy);
            System.out.println();
        }
    }
    @DisplayName("Test for tan(x)")
    @ParameterizedTest
    @ValueSource(doubles = {Double.MIN_VALUE, Double.MIN_VALUE})
    public void testMinMaxTanX(double x) {

        System.out.println("Testing for i= " + x);
        System.out.println("-------------------------");
        double resTanX = TanX.tanX(x);
        double resMathTan = Math.tan(Math.toRadians(x));
        Assertions.assertEquals(resTanX, resMathTan, accuracy);
        System.out.println();

    }

    @DisplayName("Test for exception of tan(x)")
    @ParameterizedTest
    @ValueSource(doubles = {-270, -90, 90, 270, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NaN})
    public void testExceptionTanX(double x) {
        System.out.println("Testing for x= " + x);
        System.out.println("-------------------------");

        Assertions.assertThrows(IllegalArgumentException.class, () -> TanX.tanX(x));
        System.out.println();
    }
}
