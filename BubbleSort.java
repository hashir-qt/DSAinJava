public class BubbleSort {
    
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        
        // Outer loop: number of passes
        for (int i = 0; i < n - 1; i++) {
            
            boolean swapped = false;  // Optimization flag
            
            // Inner loop: compare adjacent elements
            // Each pass, the largest unsorted element bubbles to position (n-1-i)
            for (int j = 0; j < n - 1 - i; j++) {
                
                if (arr[j] > arr[j + 1]) {
                    // Swap arr[j] and arr[j+1]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    
                    swapped = true;
                }
            }
            
            // If no swaps happened, array is already sorted
            if (!swapped) {
                break;
            }
        }
    }
    
    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        
        System.out.println("Before sorting:");
        printArray(arr);
        
        bubbleSort(arr);
        
        System.out.println("After sorting:");
        printArray(arr);
    }
}