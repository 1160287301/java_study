package 数组;

public class 搜索算法 {
    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,5,6,7,8,9};
        int key = 8;
        System.out.println(binarySearch(arr, key));
    }

    private static int binarySearch(int[] arr, int key) {
        int low = 0;
        int hight = arr.length - 1;
        while (low <= hight){
            int mid = (low + hight) >> 1;
            int midVal = arr[mid];
            if (midVal > key){
                hight = mid - 1;
            }
            else if (midVal < key){
                low = mid + 1;

            }
            else{
                return mid;
            }
        }
        return -1;
    }
}
