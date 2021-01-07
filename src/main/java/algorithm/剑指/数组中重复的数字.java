package algorithm.剑指;

/**
 * 在一个长度为 n 的数组里的所有数字都在 0 到 n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字是重复的，也不知道每个数字重复几次。请找出数组中任意一个重复的数字。
 * Input:
 * {2, 3, 1, 0, 2, 5}
 * Output:
 * 2
 */
public class 数组中重复的数字 {
    public void duplicate(int[] nums) {
        if (nums == null)
            return;
        flag:
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i) {
                if (nums[i] == nums[nums[i]]) {
                    System.out.println(nums[i]);
                    continue flag;
                }
                swap(nums, i, nums[i]);
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    public static void main(String[] args) {
        int[] aa = {2, 3, 1, 0, 2, 5, 3};
        new 数组中重复的数字().duplicate(aa);
    }
}
