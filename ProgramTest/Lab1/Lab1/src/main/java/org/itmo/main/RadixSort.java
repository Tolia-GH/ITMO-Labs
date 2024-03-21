package org.itmo.main;

import java.util.Arrays;

public class RadixSort {

    // 获取数组中最大值
    public static int getMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    // 使用计数排序对数组按照指定位数进行排序
    public static void countingSort(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10]; // 数字范围 0-9

        // 统计每个数字出现的次数
        for (int i = 0; i < n; i++) {
            count[(arr[i] / exp) % 10]++;
        }

        // 将 count 转换为每个数字在排序后的数组中的位置
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // 根据 count 数组将元素放入 output 数组中
        for (int i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        // 将 output 数组的内容复制到 arr 数组中
        System.arraycopy(output, 0, arr, 0, n);
    }

    // 实现 Radix Sort
    public static void radixSort(int[] arr) {
        int max = getMax(arr);

        // 从个位开始，依次对每一位进行计数排序
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSort(arr, exp);
        }
    }
}

