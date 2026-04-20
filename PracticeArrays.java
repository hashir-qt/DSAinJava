public class PracticeArrays {

    public static void main(String[] args) {
        // Arrays have fixed size — CRUD uses a "size" counter to track filled slots
        int[] arr = new int[10];             // capacity = 10
        int size = 0;                        // actual number of elements

        // --- CREATE: insert elements ---
        arr = insert(arr, size, 5); size++;
        arr = insert(arr, size, 3); size++;
        arr = insert(arr, size, 8); size++;
        arr = insert(arr, size, 1); size++;
        arr = insert(arr, size, 9); size++;
        System.out.print("After inserts:      "); printArray(arr, size);  // 5 3 8 1 9

        // --- INSERT AT POSITION ---
        arr = insertAt(arr, size, 2, 99); size++;
        System.out.print("After insertAt(2,99): "); printArray(arr, size); // 5 3 99 8 1 9

        // --- READ: linear search ---
        System.out.println("Search for 8: index " + linearSearch(arr, size, 8));

        // --- READ: binary search (needs sorted array) ---
        int[] sorted = {1, 3, 5, 7, 9, 11, 15};
        System.out.println("Binary Search for 7: index " + binarySearch(sorted, 0, sorted.length - 1, 7));

        // --- READ: find max ---
        System.out.println("Max element: " + findMax(arr, size));

        // --- UPDATE ---
        update(arr, size, 2, 77);            // change index 2 from 99 to 77
        System.out.print("After update(2,77): "); printArray(arr, size);

        // --- DELETE by index ---
        size = deleteAt(arr, size, 2);       // remove index 2 (77)
        System.out.print("After deleteAt(2):  "); printArray(arr, size);

        // --- DELETE by value ---
        size = deleteByValue(arr, size, 9);
        System.out.print("After deleteVal(9): "); printArray(arr, size);

        // --- BUBBLE SORT ---
        bubbleSort(arr, size);
        System.out.print("After bubbleSort:   "); printArray(arr, size);

        // --- REVERSE ---
        reverseArray(arr, size);
        System.out.print("After reverse:      "); printArray(arr, size);
    }

    // =======================================================
    //  CRUD OPERATIONS
    // =======================================================

    // -------------------------------------------------------
    // CREATE — Insert at end
    // Algorithm: place new value at index 'size', increment size
    // Time: O(1)
    // -------------------------------------------------------
    static int[] insert(int[] arr, int size, int value) {
        arr[size] = value;                   // put value at next free slot
        return arr;
    }

    // -------------------------------------------------------
    // CREATE — Insert at a specific position
    // Algorithm: shift elements right from pos to size-1, then place value
    // Time: O(n)
    // -------------------------------------------------------
    static int[] insertAt(int[] arr, int size, int pos, int value) {
        for (int i = size; i > pos; i--) {  // shift right from the end
            arr[i] = arr[i - 1];
        }
        arr[pos] = value;                    // place new value at pos
        return arr;
    }

    // -------------------------------------------------------
    // READ — Linear Search
    // Algorithm: check each element one by one
    // Time: O(n)
    // -------------------------------------------------------
    static int linearSearch(int[] arr, int n, int key) {
        for (int i = 0; i < n; i++) {
            if (arr[i] == key) return i;     // found — return index
        }
        return -1;                           // not found
    }

    // -------------------------------------------------------
    // READ — Binary Search (array must be sorted)
    // Algorithm: compare key with mid, cut array in half each time
    // Time: O(log n)
    // -------------------------------------------------------
    static int binarySearch(int[] arr, int low, int high, int key) {
        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid] == key) {
                return mid;                  // found
            } else if (arr[mid] < key) {
                low = mid + 1;               // key is in right half
            } else {
                high = mid - 1;              // key is in left half
            }
        }
        return -1;                           // not found
    }

    // -------------------------------------------------------
    // READ — Find Maximum
    // Algorithm: track current max while scanning
    // Time: O(n)
    // -------------------------------------------------------
    static int findMax(int[] arr, int n) {
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] > max) max = arr[i];
        }
        return max;
    }

    // -------------------------------------------------------
    // UPDATE — Change value at a given index
    // Algorithm: direct index access, replace value
    // Time: O(1)
    // -------------------------------------------------------
    static void update(int[] arr, int size, int pos, int newValue) {
        if (pos < 0 || pos >= size) {
            System.out.println("Update failed: index out of bounds");
            return;
        }
        arr[pos] = newValue;                 // direct replacement
    }

    // -------------------------------------------------------
    // DELETE — Remove element at a given index
    // Algorithm: shift elements left from pos+1 onward, decrement size
    // Time: O(n)
    // -------------------------------------------------------
    static int deleteAt(int[] arr, int size, int pos) {
        if (pos < 0 || pos >= size) {
            System.out.println("Delete failed: index out of bounds");
            return size;
        }
        for (int i = pos; i < size - 1; i++) {  // shift left
            arr[i] = arr[i + 1];
        }
        return size - 1;                     // return new size
    }

    // -------------------------------------------------------
    // DELETE — Remove first occurrence of a value
    // Algorithm: find index via linear search, then deleteAt
    // Time: O(n)
    // -------------------------------------------------------
    static int deleteByValue(int[] arr, int size, int key) {
        int index = linearSearch(arr, size, key);
        if (index == -1) {
            System.out.println("Value " + key + " not found");
            return size;
        }
        return deleteAt(arr, size, index);
    }

    // =======================================================
    //  SORTING & MANIPULATION
    // =======================================================

    // -------------------------------------------------------
    // BUBBLE SORT
    // Algorithm:
    //   - outer loop runs n-1 passes
    //   - inner loop compares adjacent pairs
    //   - if left > right, swap them (bubble larger values to the end)
    //   - after each pass, the last i elements are sorted
    // Time: O(n²)
    // -------------------------------------------------------
    static void bubbleSort(int[] arr, int n) {
        for (int i = 0; i < n - 1; i++) {           // n-1 passes
            for (int j = 0; j < n - 1 - i; j++) {  // last i elements already sorted
                if (arr[j] > arr[j + 1]) {           // wrong order — swap
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // -------------------------------------------------------
    // REVERSE ARRAY
    // Algorithm: two pointers (left, right) swap and move inward
    // Time: O(n)
    // -------------------------------------------------------
    static void reverseArray(int[] arr, int n) {
        int left = 0;
        int right = n - 1;

        while (left < right) {
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }

    // helper to print array
    static void printArray(int[] arr, int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
