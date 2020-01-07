package 数组;

import java.util.Arrays;

public class 排序算法 {
    public static void main(String[] args) {
        int[] arr = new int[]{2, 9, 6, 7, 4, 1};

//        int[] arr1 = bubbleSort(arr);
        int[] arr2 = selectionSort(arr);
    }

    //选择排序
    private static int[] selectionSort(int[] arr) {
        for (int times = 0; times < arr.length - 1; times++) {
            int minIndex = times;
            for (int i = times; i < arr.length; i++) {
                    if(arr[i]<arr[minIndex]){
                        minIndex = i;
                    }
            }
            int other = arr[minIndex];
            arr[minIndex] = arr[times];
            arr[times] = other;
        }
        System.out.println(Arrays.toString(arr));
        return arr;
    }

    // 冒泡排序
    private static int[] bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int other = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = other;
                }
            }
        }
        return arr;
    }
}
