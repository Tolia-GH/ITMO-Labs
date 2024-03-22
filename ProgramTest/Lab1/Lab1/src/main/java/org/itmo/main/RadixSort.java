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

        System.out.println("-------------------------------------------------------");
        System.out.println("Step0: Checking arrays");

        System.out.println("    arr[]:   " + Arrays.toString(arr));
        System.out.println("    count[]: " + Arrays.toString(count));
        System.out.println("    output[]:" + Arrays.toString(output));

        System.out.println("Step1: Setting count array according to arr array");

        // setting count array according to arr array
        for (int i = 0; i < n; i++) {
            count[(arr[i] / exp) % 10]++;
        }

        System.out.println("    arr[]:   " + Arrays.toString(arr));
        System.out.println("    count[]: " + Arrays.toString(count));
        System.out.println("    output[]:" + Arrays.toString(output));
        System.out.println("Step2: Setting count to address of output");

        // setting count to address of output
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        System.out.println("    arr[]:   " + Arrays.toString(arr));
        System.out.println("    count[]: " + Arrays.toString(count));
        System.out.println("    output[]:" + Arrays.toString(output));
        System.out.println("Step3: Setting output array according to count array");

        // setting output array according to count array
        for (int i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        System.out.println("    arr[]:   " + Arrays.toString(arr));
        System.out.println("    count[]: " + Arrays.toString(count));
        System.out.println("    output[]:" + Arrays.toString(output));
        System.out.println("Step4: Copy output to arr");

        // copy output to arr
        System.arraycopy(output, 0, arr, 0, n);

        System.out.println("    arr[]:   " + Arrays.toString(arr));
        System.out.println("    count[]: " + Arrays.toString(count));
        System.out.println("    output[]:" + Arrays.toString(output));
    }

    // Radix Sort
    public static void radixSort(int[] arr) {
        if (arr.length == 0) {
            throw new IllegalArgumentException("Invalid array");
        }

        for (int i = 0 ; i < arr.length; i ++) {
            if (arr[i] < 0) {
                throw new IllegalArgumentException("Invalid array");
            }
        }

        int max = getMax(arr);

        // exp of 1, 10, 100...
        for (int exp = 1; max / exp > 0; exp *= 10) {
            System.out.println("Checking with exp = " + exp);
            countingSort(arr, exp);
        }
        System.out.println("-------------------------------------------------------");
    }
}

