# DSA Cheatsheet — Arrays & Linked Lists

---

## PART 1 — ARRAYS

### What is an Array?
An array is a **fixed-size**, **contiguous block of memory** that stores elements of the same type.
Each element is accessed directly via its **index** in O(1) time.

```
Index:   0    1    2    3    4
Array: [ 5  | 3  | 8  | 1  | 9 ]
         ↑
       arr[0] = 5
```

> **Key point:** Because Java arrays have fixed size, we track a `size` variable to know how many slots are actually used. CRUD operations manipulate `size`, not the array's physical length.

---

### Time Complexity Summary

| Operation        | Time   | Why                              |
|------------------|--------|----------------------------------|
| Access by index  | O(1)   | Direct memory address            |
| Search (linear)  | O(n)   | May check every element          |
| Search (binary)  | O(log n) | Halves the search space each step |
| Insert at end    | O(1)   | Just place at `arr[size]`        |
| Insert at index  | O(n)   | Must shift elements right        |
| Delete at index  | O(n)   | Must shift elements left         |
| Bubble Sort      | O(n²)  | Nested loops over all elements   |

---

### CRUD Operations

#### CREATE — Insert at End
**Theory:** Place the new value at `arr[size]` (the next free slot) and increment `size`. No shifting needed.

```
Algorithm InsertAtEnd(arr, size, value):
1. arr[size] = value
2. size = size + 1
```

```java
static int[] insert(int[] arr, int size, int value) {
    arr[size] = value;
    return arr;
}
```

---

#### CREATE — Insert at Position
**Theory:** To insert at index `pos`, shift every element from `pos` to `size-1` one step to the right, then place the new value at `pos`.

```
Before: [ 5 | 3 | 8 | 1 | 9 | _ ]   insert 99 at index 2
Shift:  [ 5 | 3 | _ | 8 | 1 | 9 ]
After:  [ 5 | 3 | 99| 8 | 1 | 9 ]
```

```
Algorithm InsertAt(arr, size, pos, value):
1. FOR i = size DOWN TO pos+1:
2.     arr[i] = arr[i-1]       ← shift right
3. arr[pos] = value
4. size = size + 1
```

```java
static int[] insertAt(int[] arr, int size, int pos, int value) {
    for (int i = size; i > pos; i--) {
        arr[i] = arr[i - 1];              // shift right
    }
    arr[pos] = value;
    return arr;
}
```

---

#### READ — Linear Search
**Theory:** Scan each element from index 0 to n-1. Return the index when the key is found, or -1 if not found. Works on **unsorted** arrays.

```
Algorithm LinearSearch(arr, n, key):
1. FOR i = 0 TO n-1:
2.     IF arr[i] == key: RETURN i
3. RETURN -1
```

```java
static int linearSearch(int[] arr, int n, int key) {
    for (int i = 0; i < n; i++) {
        if (arr[i] == key) return i;
    }
    return -1;
}
```

---

#### READ — Binary Search *(sorted array only)*
**Theory:** Compare the key with the **middle element**. If equal, found. If key is smaller, search the left half. If larger, search the right half. Repeat until found or range is empty.

```
Array: [ 1 | 3 | 5 | 7 | 9 | 11 | 15 ]   search key = 7
Pass 1: low=0, high=6, mid=3 → arr[3]=7 → FOUND at index 3
```

```
Algorithm BinarySearch(arr, low, high, key):
1. WHILE low <= high:
2.     mid = (low + high) / 2
3.     IF arr[mid] == key: RETURN mid
4.     ELSE IF arr[mid] < key: low = mid + 1
5.     ELSE: high = mid - 1
6. RETURN -1
```

```java
static int binarySearch(int[] arr, int low, int high, int key) {
    while (low <= high) {
        int mid = (low + high) / 2;
        if      (arr[mid] == key) return mid;
        else if (arr[mid] <  key) low  = mid + 1;
        else                      high = mid - 1;
    }
    return -1;
}
```

---

#### READ — Find Maximum
**Theory:** Assume the first element is the max. Scan the rest — update max whenever a larger element is found.

```
Algorithm FindMax(arr, n):
1. max = arr[0]
2. FOR i = 1 TO n-1:
3.     IF arr[i] > max: max = arr[i]
4. RETURN max
```

```java
static int findMax(int[] arr, int n) {
    int max = arr[0];
    for (int i = 1; i < n; i++) {
        if (arr[i] > max) max = arr[i];
    }
    return max;
}
```

---

#### UPDATE — Change Value at Index
**Theory:** Arrays allow O(1) direct index access. Simply overwrite `arr[pos]` with the new value.

```
Algorithm Update(arr, pos, newValue):
1. arr[pos] = newValue
```

```java
static void update(int[] arr, int size, int pos, int newValue) {
    if (pos >= 0 && pos < size)
        arr[pos] = newValue;
}
```

---

#### DELETE — Remove at Index
**Theory:** Shift every element after `pos` one step to the left, overwriting the deleted slot. Decrement `size`.

```
Before: [ 5 | 3 | 99| 8 | 1 | 9 ]   delete index 2
Shift:  [ 5 | 3 | 8 | 1 | 9 | 9 ]   ← last 9 is stale, size shrinks
After:  [ 5 | 3 | 8 | 1 | 9 ]  size=5
```

```
Algorithm DeleteAt(arr, size, pos):
1. FOR i = pos TO size-2:
2.     arr[i] = arr[i+1]       ← shift left
3. RETURN size - 1
```

```java
static int deleteAt(int[] arr, int size, int pos) {
    for (int i = pos; i < size - 1; i++) {
        arr[i] = arr[i + 1];              // shift left
    }
    return size - 1;
}
```

---

#### DELETE — Remove by Value
**Theory:** Use linear search to find the index of the value, then call deleteAt.

```java
static int deleteByValue(int[] arr, int size, int key) {
    int index = linearSearch(arr, size, key);
    if (index == -1) return size;         // not found
    return deleteAt(arr, size, index);
}
```

---

### Bubble Sort

**Theory:**
- Run `n-1` passes over the array.
- In each pass, compare every adjacent pair `(arr[j], arr[j+1])`.
- If they are out of order, **swap** them.
- After each pass, the largest unsorted element "bubbles up" to its correct position at the end.
- After pass `i`, the last `i` elements are sorted and never touched again.

```
Pass 1: [5, 3, 8, 1, 9] → [3, 5, 1, 8, 9]  ← 9 in place
Pass 2: [3, 5, 1, 8, 9] → [3, 1, 5, 8, 9]  ← 8,9 in place
Pass 3: [3, 1, 5, 8, 9] → [1, 3, 5, 8, 9]  ← sorted
```

```
Algorithm BubbleSort(arr, n):
1. FOR i = 0 TO n-2:                       ← n-1 passes
2.     FOR j = 0 TO n-2-i:                 ← skip last i sorted elements
3.         IF arr[j] > arr[j+1]:
4.             SWAP arr[j] and arr[j+1]
```

```java
static void bubbleSort(int[] arr, int n) {
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - 1 - i; j++) {
            if (arr[j] > arr[j + 1]) {
                int temp  = arr[j];
                arr[j]    = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }
}
```

---

### Reverse Array

**Theory:** Use two pointers — `left` starting at index 0, `right` at index `n-1`. Swap them, then move both pointers inward. Stop when they meet.

```
[ 5 | 3 | 8 | 1 | 9 ]
  ↑               ↑
 left           right     swap → [ 9 | 3 | 8 | 1 | 5 ]
       ↑       ↑
      left   right         swap → [ 9 | 1 | 8 | 3 | 5 ]
           ↑↑
        left=right          stop
```

```
Algorithm ReverseArray(arr, n):
1. left = 0,  right = n-1
2. WHILE left < right:
3.     SWAP arr[left] and arr[right]
4.     left++,  right--
```

```java
static void reverseArray(int[] arr, int n) {
    int left = 0, right = n - 1;
    while (left < right) {
        int temp   = arr[left];
        arr[left]  = arr[right];
        arr[right] = temp;
        left++;  right--;
    }
}
```

---

## PART 2 — SINGLY LINKED LIST

### What is a Singly Linked List?
A chain of **nodes** where each node holds a **value** and a **pointer to the next node**. The last node points to `null`.

```
head
 ↓
[1 | next] → [2 | next] → [3 | next] → null
```

```java
class Node {
    int data;
    Node next;
    Node(int data) { this.data = data; this.next = null; }
}
```

---

### Time Complexity Summary

| Operation      | Time | Why                                  |
|----------------|------|--------------------------------------|
| Insert at front | O(1) | Just update head                    |
| Insert at end   | O(n) | Must traverse to find last node     |
| Delete          | O(n) | Must find node before target        |
| Search          | O(n) | Must scan from head                 |
| Reverse         | O(n) | One full traversal                  |
| Traverse        | O(n) | Visit every node                    |

---

### Traverse
**Theory:** Start at `head`, print each node's data, move to `node.next` until `null`.

```java
static void traverse(Node head) {
    Node current = head;
    while (current != null) {
        System.out.print(current.data + " -> ");
        current = current.next;
    }
    System.out.println("null");
}
```

---

### Insert at Front
**Theory:** Create a new node. Point `newNode.next` to the current head. The new node becomes the new head.

```
Before: head → [1] → [2] → [3] → null
After:  head → [0] → [1] → [2] → [3] → null
```

```
Algorithm InsertAtFront(head, data):
1. newNode = new Node(data)
2. newNode.next = head
3. head = newNode
4. RETURN head
```

```java
static Node insertAtFront(Node head, int data) {
    Node newNode = new Node(data);
    newNode.next = head;
    return newNode;
}
```

---

### Insert at End
**Theory:** If list is empty, the new node is the head. Otherwise, traverse to the last node (where `next == null`) and link it to the new node.

```
Algorithm InsertAtEnd(head, data):
1. newNode = new Node(data)
2. IF head == null: RETURN newNode
3. temp = head
4. WHILE temp.next != null: temp = temp.next
5. temp.next = newNode
6. RETURN head
```

```java
static Node insertAtEnd(Node head, int data) {
    Node newNode = new Node(data);
    if (head == null) return newNode;
    Node temp = head;
    while (temp.next != null) temp = temp.next;
    temp.next = newNode;
    return head;
}
```

---

### Delete by Value
**Theory:**
- If the head itself matches, return `head.next` (skip head).
- Otherwise, traverse with `temp`. When `temp.next.data == key`, set `temp.next = temp.next.next` to skip over the target.

```
Before: [1] → [2] → [3] → null   delete 2
After:  [1] → [3] → null
              ↑
         temp.next = temp.next.next
```

```
Algorithm DeleteNode(head, key):
1. IF head == null: RETURN null
2. IF head.data == key: RETURN head.next
3. temp = head
4. WHILE temp.next != null:
5.     IF temp.next.data == key:
6.         temp.next = temp.next.next
7.         RETURN head
8.     temp = temp.next
9. RETURN head
```

```java
static Node deleteNode(Node head, int key) {
    if (head == null) return null;
    if (head.data == key) return head.next;
    Node temp = head;
    while (temp.next != null) {
        if (temp.next.data == key) {
            temp.next = temp.next.next;   // skip target node
            return head;
        }
        temp = temp.next;
    }
    return head;
}
```

---

### Search
**Theory:** Traverse from head and compare each node's data to the key.

```java
static boolean search(Node head, int key) {
    Node temp = head;
    while (temp != null) {
        if (temp.data == key) return true;
        temp = temp.next;
    }
    return false;
}
```

---

### Reverse *(most common exam question)*
**Theory:** Use 3 pointers — `prev`, `curr`, `next`.
For each node, save `next`, reverse the link (`curr.next = prev`), then advance both `prev` and `curr` forward. At the end, `prev` is the new head.

```
Start:  null ← ? [1] → [2] → [3] → null
                  ↑prev  ↑curr

Step 1: save next=2, point 1→null, prev=1, curr=2
Step 2: save next=3, point 2→1,    prev=2, curr=3
Step 3: save next=null, point 3→2, prev=3, curr=null

Result: null ← [1] ← [2] ← [3]   new head = 3
```

```
Algorithm Reverse(head):
1. prev = null
2. curr = head
3. WHILE curr != null:
4.     next = curr.next      ← save
5.     curr.next = prev      ← reverse link
6.     prev = curr           ← advance prev
7.     curr = next           ← advance curr
8. RETURN prev               ← new head
```

```java
static Node reverse(Node head) {
    Node prev = null, curr = head;
    while (curr != null) {
        Node next  = curr.next;           // 1. save next
        curr.next  = prev;                // 2. reverse link
        prev       = curr;                // 3. move prev forward
        curr       = next;                // 4. move curr forward
    }
    return prev;                          // new head
}
```

---

## PART 3 — DOUBLY LINKED LIST

### What is a Doubly Linked List?
Each node has **two pointers** — `next` (forward) and `prev` (backward). You can traverse in **both directions**.

```
null ← [prev|1|next] ↔ [prev|2|next] ↔ [prev|3|next] → null
        ↑ head                              ↑ tail
```

```java
class DoublyNode {
    int data;
    DoublyNode next;
    DoublyNode prev;
    DoublyNode(int data) { this.data = data; this.next = null; this.prev = null; }
}
```

> **Key difference from singly:** Every insertion and deletion must update **both** `next` and `prev` pointers.

---

### Time Complexity Summary

| Operation       | Time | Why                                      |
|-----------------|------|------------------------------------------|
| Insert at front | O(1) | Update head and its prev                 |
| Insert at end   | O(n) | Must traverse to tail                    |
| Delete          | O(n) | Must find node, then fix both directions |
| Traverse        | O(n) | Visit every node                         |

---

### Insert at Front
**Theory:** Point `newNode.next` to head, and point `head.prev` back to `newNode`. The new node becomes head with `prev = null`.

```
Before: null ← [1] ↔ [2] ↔ [3] → null
After:  null ← [0] ↔ [1] ↔ [2] ↔ [3] → null
```

```
Algorithm InsertAtFront(head, data):
1. newNode = new Node(data)
2. newNode.next = head
3. IF head != null: head.prev = newNode
4. RETURN newNode
```

```java
static DoublyNode insertAtFront(DoublyNode head, int data) {
    DoublyNode newNode = new DoublyNode(data);
    newNode.next = head;
    if (head != null) head.prev = newNode;  // link old head back
    return newNode;
}
```

---

### Insert at End
**Theory:** Traverse to the last node. Link `last.next = newNode` and `newNode.prev = last`.

```
Algorithm InsertAtEnd(head, data):
1. newNode = new Node(data)
2. IF head == null: RETURN newNode
3. temp = head
4. WHILE temp.next != null: temp = temp.next
5. temp.next = newNode
6. newNode.prev = temp          ← extra step vs singly
7. RETURN head
```

```java
static DoublyNode insertAtEnd(DoublyNode head, int data) {
    DoublyNode newNode = new DoublyNode(data);
    if (head == null) return newNode;
    DoublyNode temp = head;
    while (temp.next != null) temp = temp.next;
    temp.next    = newNode;
    newNode.prev = temp;                    // link back
    return head;
}
```

---

### Delete by Value
**Theory:** Find the target node. Fix the link of the node **before** it and the node **after** it — in both directions.

```
Before: [1] ↔ [2] ↔ [3]    delete 2
              ↑ target
  target.prev.next = target.next   → [1].next = [3]
  target.next.prev = target.prev   → [3].prev = [1]
After:  [1] ↔ [3]
```

```
Algorithm DeleteNode(head, key):
1. Find node temp where temp.data == key
2. IF temp == null: RETURN head            ← not found
3. IF temp.prev != null: temp.prev.next = temp.next
4. ELSE: head = temp.next                  ← deleting head
5. IF temp.next != null: temp.next.prev = temp.prev
6. RETURN head
```

```java
static DoublyNode deleteNode(DoublyNode head, int key) {
    DoublyNode temp = head;
    while (temp != null && temp.data != key) temp = temp.next;
    if (temp == null) return head;                    // not found

    if (temp.prev != null) temp.prev.next = temp.next;
    else                   head = temp.next;          // deleting head

    if (temp.next != null) temp.next.prev = temp.prev;
    return head;
}
```

---

### Traverse Forward & Backward

```java
static void traverseForward(DoublyNode head) {
    DoublyNode current = head;
    while (current != null) {
        System.out.print(current.data + " <-> ");
        current = current.next;
    }
    System.out.println("null");
}

static void traverseBackward(DoublyNode tail) {
    DoublyNode current = tail;
    while (current != null) {
        System.out.print(current.data + " <-> ");
        current = current.prev;
    }
    System.out.println("null");
}
```

---

## PART 4 — QUICK COMPARISON

| Feature              | Array       | Singly LL   | Doubly LL   |
|----------------------|-------------|-------------|-------------|
| Memory layout        | Contiguous  | Scattered   | Scattered   |
| Access by index      | O(1)        | O(n)        | O(n)        |
| Insert at front      | O(n)        | O(1)        | O(1)        |
| Insert at end        | O(1)*       | O(n)        | O(n)        |
| Delete               | O(n)        | O(n)        | O(n)        |
| Traverse backward    | Yes (index) | No          | Yes (prev)  |
| Extra memory per node| None        | 1 pointer   | 2 pointers  |

*O(1) insert at end for arrays assumes there is free space and we track `size`.

---

## PART 5 — KEY RULES TO REMEMBER

### Arrays
- Binary search **requires a sorted array**
- InsertAt / DeleteAt always **shift elements** — that's why they are O(n)
- Bubble sort inner loop bound shrinks by `i` each pass — avoids re-comparing sorted tail

### Singly Linked List
- To delete a node, you need the **node before** it (not the node itself)
- Reverse uses exactly **3 pointers**: `prev`, `curr`, `next`
- Always return the new `head` from insert/delete/reverse methods

### Doubly Linked List
- Every operation must update **both** `node.prev` and `node.next`
- When deleting the **head**, set `head = head.next` and clear `head.prev`
- When deleting the **tail**, just set `tail.prev.next = null`
- Doubly LL allows O(1) delete if you already have the node reference (no need to find previous node)
