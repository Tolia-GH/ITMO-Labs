import org.itmo.main.text_model.Entity.Dolphin;
import org.itmo.main.text_model.Attributes.Gender;
import org.itmo.main.text_model.Entity.Human;
import org.junit.jupiter.api.*;

public class ModelTest {
    Human h1;
    Human h2;
    Human h3;
    Dolphin d1;
    Dolphin d2;
    Dolphin d3;
    @BeforeEach
    void init() {
        h1 = new Human("Peter", Gender.MALE, 28);
        h2 = new Human("Tom", Gender.MALE, 19);
        h3 = new Human("Alice", Gender.FEMALE, 23);

        d1 = new Dolphin("Doll", Gender.FEMALE, 8);
        d2 = new Dolphin("Fill", Gender.MALE, 10);
        d3 = new Dolphin("Lili", Gender.FEMALE, 12);
    }

    @Nested
    class ClassTest {
        @Test
        @DisplayName("Test for constructor")
        void testConstructor() {
            System.out.println("Testing constructor of Human");
            Exception e1 = Assertions.assertThrows(IllegalArgumentException.class, () -> new Human(null, Gender.NONE, 1));
            Assertions.assertEquals("Name shouldn't be null", e1.getMessage());

            Exception e2 = Assertions.assertThrows(IllegalArgumentException.class, () -> new Human("TestName", null, 1));
            Assertions.assertEquals("Gender shouldn't be null", e2.getMessage());

            Exception e3 = Assertions.assertThrows(IllegalArgumentException.class, () -> new Human("TestName", Gender.NONE, -1));
            Assertions.assertEquals("Age must > 0", e3.getMessage());
            System.out.println("Test done");
            System.out.println();

            System.out.println("Testing constructor of Dolphin");
            Exception e4 = Assertions.assertThrows(IllegalArgumentException.class, () -> new Dolphin(null, Gender.NONE, 1));
            Assertions.assertEquals("Name shouldn't be null", e4.getMessage());

            Exception e5 = Assertions.assertThrows(IllegalArgumentException.class, () -> new Dolphin("TestName", null, 1));
            Assertions.assertEquals("Gender shouldn't be null", e5.getMessage());

            Exception e6 = Assertions.assertThrows(IllegalArgumentException.class, () -> new Dolphin("TestName", Gender.NONE, -1));
            Assertions.assertEquals("Age must >= 0", e6.getMessage());
            System.out.println("Test done");
            System.out.println();
        }

        @Test
        @DisplayName("Test for setter")
        void testSetter() {
            System.out.println("Testing setter of Human");

            Human h = new Human();

            Exception e1 = Assertions.assertThrows(IllegalArgumentException.class, () -> h.setName(null));
            Assertions.assertEquals("Name shouldn't be null", e1.getMessage());

            Exception e2 = Assertions.assertThrows(IllegalArgumentException.class, () -> h.setGender(null));
            Assertions.assertEquals("Gender shouldn't be null", e2.getMessage());

            Exception e3 = Assertions.assertThrows(IllegalArgumentException.class, () -> h.setAge(-1));
            Assertions.assertEquals("Age must >= 0", e3.getMessage());

            System.out.println("Test done");
            System.out.println();
            System.out.println("Testing setter of Dolphin");

            Dolphin d = new Dolphin();

            Exception e4 = Assertions.assertThrows(IllegalArgumentException.class, () -> d.setName(null));
            Assertions.assertEquals("Name shouldn't be null", e4.getMessage());

            Exception e5 = Assertions.assertThrows(IllegalArgumentException.class, () -> d.setGender(null));
            Assertions.assertEquals("Gender shouldn't be null", e5.getMessage());

            Exception e6 = Assertions.assertThrows(IllegalArgumentException.class, () -> d.setAge(-1));
            Assertions.assertEquals("Age must >= 0", e6.getMessage());

            System.out.println("Test done");
            System.out.println();
        }
    }
}
