package com.pjz.bin;

public class BinarySearch {

    public static int mySqrt(int x) {

        int left = 0;
        int right = x;

        int mid = -1;

        while (left <= right) {

            mid = (left + right) >> 1;

            int tmp = mid * mid;

            if (tmp > x) {
                right = mid - 1;
            } else if (tmp < x) {
                left = mid + 1;
            } else {
                return mid;
            }
        }

        if (mid * mid > x) {
            return mid - 1;
        } else {
            return mid;
        }
    }

    public static int search(int[] arr, int target) {

        int left = 0;
        int right = arr.length;

        while (1 < right - left) {

            int mid = (left + right) >> 1;

            if (arr[mid] > target) {
                right = mid;
            } else if (arr[mid] < target) {
                left = mid;
            }
        }

        if (arr[left] == target) {
            return left;
        } else {
            return -1;
        }
    }


    public static int binarySearch2(int[] arr, int target, int left, int right) {

        int mid = (left + right) >> 1;

        if (arr[mid] > target) {
            right = mid;
            binarySearch2(arr,target,left,right);
        } else if (arr[mid] < target) {
            left = mid;
            binarySearch2(arr,target,left,right);
        }

        if (arr[left] == target) {
            return left;
        } else {
            return -1;
        }
    }


    public static void main(String[] args) {
        System.out.println(mySqrt(2147395599));

    }
}
