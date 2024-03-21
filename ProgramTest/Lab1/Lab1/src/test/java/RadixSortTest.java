import Tools.RandomArrayGenerator;
import org.itmo.main.RadixSort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class RadixSortTest {
    private int min = 0;
    private int max = 999999;
    private int size = 30;
    @Test
    public void testRadixSort() {
        for (int i = 1; i < 10; i++) {
            int[] arr = RandomArrayGenerator.generateRandomArray(size, min, max);
            int[] res = arr.clone();
            System.out.println("Testing array: " + Arrays.toString(arr));
            Arrays.sort(res);
            System.out.println("Arrays.sort(arr): " + Arrays.toString(res));
            RadixSort.radixSort(arr);
            System.out.println("RadixSort(arr): " + Arrays.toString(arr));
            Assertions.assertArrayEquals(arr, res);
            System.out.println();
        }
    }
}
