package org.itmo.main;

import java.util.Arrays;

public class RadixSort {
    public static int getMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    public static void countingSort(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10]; // from 0 to 9

        // setting count array according to arr array
        for (int i = 0; i < n; i++) {
            count[(arr[i] / exp) % 10]++;
        }

        // setting count to address of output
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // setting output array according to count array
        for (int i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        // copy output to arr
        System.arraycopy(output, 0, arr, 0, n);
    }

    // Radix Sort
    public static void radixSort(int[] arr) {
        int max = getMax(arr);

        // exp of 1, 10, 100...
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSort(arr, exp);
        }
    }
}

