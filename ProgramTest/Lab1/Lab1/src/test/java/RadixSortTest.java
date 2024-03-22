import Tools.RandomArrayGenerator;
import org.itmo.main.RadixSort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class RadixSortTest {
    private int min = 0;
    private int max = 999999;
    private int size = 30;
    @RepeatedTest(100)
    @DisplayName("Random Array Test for RadixSort")
    public void testRadixSort() {
        int[] arr = RandomArrayGenerator.generateRandomArray(size, min, max);
        int[] res = arr.clone();
        System.out.println("Testing array:    " + Arrays.toString(arr));
        Arrays.sort(res);
        System.out.println("Arrays.sort(arr): " + Arrays.toString(res));
        RadixSort.radixSort(arr);
        System.out.println("Radix Sort(arr):  " + Arrays.toString(arr));
        Assertions.assertArrayEquals(arr, res);
        System.out.println();
    }
}
