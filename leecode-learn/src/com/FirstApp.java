package com;

import java.sql.SQLOutput;

public class FirstApp {

    public static void main(String[] args) {
//        int[] nums= {1, 7, 3, 6, 5, 6};
//        int[] nums = {1, 2, 3};
        int[] nums= {-1,-1,-1,-1,-1,0};
        System.out.println(find(nums));
    }

    static int find(int[] nums) {
        int total = 0;
        for (int i = 0; i <= nums.length - 1; i++) {
            total = nums[i] + total;
        }

        int left = 0;
        for (int middle = 0; middle <= nums.length - 1; middle++) {
            if (middle == 0) {
                left = 0;
            } else {
                left = nums[middle - 1] + left;
            }
            if (left == total - left - nums[middle]) {
                return middle;
            }
        }
        return -1;
    }
}
