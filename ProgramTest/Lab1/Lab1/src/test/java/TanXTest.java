import org.itmo.main.TanX;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TanXTest {

    final double accuracy = 0.001;

    @DisplayName("Test for tan(x)")
    @ParameterizedTest
    @ValueSource(doubles = {-150, -120, -60, -45, -30, 0, 30, 45, 60, 120, 150})
    public void testTanX(double x) {
        System.out.println("Testing for x= " + x);
        System.out.println("-------------------------");
        double resTanX = TanX.tanX(x);
        double resMathTan = Math.tan(Math.toRadians(x));
        Assertions.assertEquals(resTanX, resMathTan, accuracy);
        System.out.println();
    }

    @DisplayName("Test for exception of tan(x)")
    @ParameterizedTest
    @ValueSource(doubles = {-270, -90, 90, 270})
    public void testExceptionTanX(double x) {
        System.out.println("Testing for x= " + x);
        System.out.println("-------------------------");

        Assertions.assertThrows(IllegalArgumentException.class, () -> TanX.tanX(x));
        System.out.println();
    }
}
