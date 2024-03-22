package Tools;

import java.util.Arrays;
import java.util.Random;

public class Tools {

    public static boolean isSorted(int[] arr) {
        if (arr.length == 1) {
            return true;
        }

        for (int i = 1; i < arr.length; i++) {
            if (arr[i-1] > arr[i]) {
                return false;
            }
        }
        return true;
    }

    public static int[] generateRandomArray(int size, int min, int max) {
        if (size <= 0) {
            throw new IllegalArgumentException("Array size must be greater than zero");
        }

        int[] arr = new int[size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt((max - min) + 1) + min;
        }

        return arr;
    }

    public static void main(String[] args) {
        int size = 30;
        int min = -99999;
        int max = 99999;
        int[] randomArray = generateRandomArray(size, min, max);
        System.out.println("Random Array: " + Arrays.toString(randomArray));
    }
}
