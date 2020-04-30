import java.util.Random;

public class Mergesort {
    public static void main(String[] args) {
        int sampleSize = Integer.parseInt(args[1]);
        long[] slice = generateLongArray(sampleSize);
        mergeSort(slice, 0, slice.length);

        System.out.println("Sorted");
        System.out.println(slice[0]);
    }

    public static long[] generateLongArray(int n) {
        Random rd = new Random();
        long[] arr = new long[n];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rd.nextLong();
        }
        return arr;
    }

    public static void mergeSort(long[] array, int low, int high) {
        if (high <= low) return;

        int mid = (low + high) / 2;
        mergeSort(array, low, mid);
        mergeSort(array, mid + 1, high);
        merge(array, low, mid, high);
    }

    public static void merge(long[] array, int low, int mid, int high) {
        long[] leftArray = new long[mid - low + 1];
        long[] rightArray = new long[high - mid];

        for (int i = 0; i < leftArray.length; i++)
            leftArray[i] = array[low + i];

        for (int i = 0; i < rightArray.length; i++)
            rightArray[i] = array[mid + i + 1];

        int leftIndex = 0;
        int rightIndex = 0;

        for (int i = low; i < high + 1; i++) {
            if (leftIndex < leftArray.length && rightIndex < rightArray.length) {
                if (leftArray[leftIndex] < rightArray[rightIndex]) {
                    array[i] = leftArray[leftIndex];
                    leftIndex++;
                } else {
                    array[i] = rightArray[rightIndex];
                    rightIndex++;
                }
            } else if (leftIndex < leftArray.length) {
                array[i] = leftArray[leftIndex];
                leftIndex++;
            } else if (rightIndex < rightArray.length) {
                array[i] = rightArray[rightIndex];
                rightIndex++;
            }
        }
    }
}