package algorithm.剑指;

/**
 * 在一个长度为 n 的数组里的所有数字都在 0 到 n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字是重复的，也不知道每个数字重复几次。请找出数组中任意一个重复的数字。
 * Input:
 * {2, 3, 1, 0, 2, 5}
 * Output:
 * 2
 */
public class 数组中重复的数字 {
    /**
     * 题目二: 不修改数组找出重复的数字
     * 在一个长度为 n+1的数组里的所有数字都在 1~n 的范围内,所以数据中至少有一个数字是重复的,请找出数组中任意一个重复的数字,但不能修改输入的数组. 例如
     * 如果输入长度为 8 的数组{2,3,5,4,3,2,6,7}, 那么对应的输出是重复的数字 2 或者 3
     * <p>
     * 我们把从 1~n 的数字从中间的数字 m 分为两部分,前面一半为 1~m,后面一半为 m+1~n. 如果 1~m 的数字的个数超过 m,那么这一半的区间里立定包含重复的数字; 否则,另一半 m+1~n 的区间里一定包含重复的数字.
     * 我们可以继续把包含重复数字的区间一分为二,直到找到一个重复的数字.这个过程和二分查找算法很类似,只是多了一步统计区间里数字的个数
     */
    static class 不修改数组找出重复的数字 {
        public void duplicate(int[] nums, int length) {
            int start = 1;
            int end = length - 1;
            while (end >= start) {
                int middle = ((end - start) >> 1) + start;
                int count = countRange(nums, length, start, end);
                if (end == start) {
                    if (count > 1)
                        System.out.println(start);
                    else
                        break;
                }
                if (count > (middle - start + 1))
                    end = middle;
                else
                    start = middle + 1;
            }
        }

        int countRange(int[] nums, int length, int start, int end) {
            int count = 0;
            for (int i = 1; i < length; i++) {
                if (nums[i] >= start && nums[i] <= end) {
                    count++;
                }
            }
            return count;
        }
    }


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
//        int[] aa = {2, 3, 1, 0, 2, 5, 3};
//        new 数组中重复的数字().duplicate(aa);
        int[] bb = {2, 3, 5, 4, 3, 2, 6, 7};
        int length = 8;
        new 不修改数组找出重复的数字().duplicate(bb, length);
    }
}
