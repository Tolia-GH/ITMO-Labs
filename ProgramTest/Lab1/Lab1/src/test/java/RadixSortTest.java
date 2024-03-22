import Tools.Tools;
import org.itmo.main.RadixSort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class RadixSortTest {

    @RepeatedTest(100)
    @DisplayName("Random Array Test for RadixSort")
    public void testRadixSort() {
        int min = 0;
        int max = 999;
        int size = 10;
        int[] arr = Tools.generateRandomArray(size, min, max);
        System.out.println("Testing array:    " + Arrays.toString(arr));

        RadixSort.radixSort(arr);
        Assertions.assertTrue(Tools.isSorted(arr));
        System.out.println("Radix Sort(arr):  " + Arrays.toString(arr));
        System.out.println();
    }

    @Test
    @DisplayName("Array Test for RadixSort")
    public void arrayRadixSort() {
        int[][] arr= {
                {1,1,1,1,1,1,1,1,1},
                {9,8,7,6,5,4,3,2,1},
                {1,11,111,1111,11111,111111,1111111},
                {99999999,99999,9999,999,99,9},
                {Integer.MAX_VALUE, Integer.MAX_VALUE},
                {0, Integer.MAX_VALUE},
                {Integer.MAX_VALUE, 0},
                {0}
        };
        for (int i = 0; i < arr.length; i++) {
            int[] temp = arr[i].clone();
            System.out.println("Testing array:    " + Arrays.toString(temp));
            RadixSort.radixSort(temp);
            Assertions.assertTrue(Tools.isSorted(temp));
            System.out.println("Radix Sort(arr):  " + Arrays.toString(temp));
            System.out.println();
        }
    }

    @Test
    @DisplayName("Test for invalid inputs of RadixSort")
    public void invalidArrayRadixSort() {
        int[][] arr= {
                {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                {-1,1,11,111,1111,11111,111111,1111111},
                {Integer.MIN_VALUE, Integer.MIN_VALUE},
                {Integer.MIN_VALUE},
                {}
        };
        for (int i = 0; i < arr.length; i++) {
            int[] temp = arr[i].clone();
            System.out.println("Testing array:    " + Arrays.toString(temp));
            // RadixSort.radixSort(temp);
            Assertions.assertThrows(IllegalArgumentException.class, () -> RadixSort.radixSort(temp));
            System.out.println();
        }
    }
}
