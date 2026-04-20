public class PracticeDoublyLL {

    public static void main(String[] args) {
        DoublyNode head = null;

        // Build list: 1 <-> 2 <-> 3 <-> null
        head = insertAtEnd(head, 1);
        head = insertAtEnd(head, 2);
        head = insertAtEnd(head, 3);

        System.out.print("Original (forward):  ");
        traverseForward(head);               // 1 <-> 2 <-> 3 <-> null

        // Insert at front
        head = insertAtFront(head, 0);
        System.out.print("After insertFront(0): ");
        traverseForward(head);               // 0 <-> 1 <-> 2 <-> 3 <-> null

        // Delete node with value 2
        head = deleteNode(head, 2);
        System.out.print("After delete(2): ");
        traverseForward(head);               // 0 <-> 1 <-> 3 <-> null

        // Traverse backward — find tail first
        DoublyNode tail = head;
        while (tail.next != null) tail = tail.next;
        System.out.print("Backward:        ");
        traverseBackward(tail);              // 3 <-> 1 <-> 0 <-> null
    }

    // -------------------------------------------------------
    // 1. INSERT AT FRONT
    // Algorithm: newNode.next = head, head.prev = newNode
    // Key: must update head.prev to point back to newNode
    // Time: O(1)
    // -------------------------------------------------------
    static DoublyNode insertAtFront(DoublyNode head, int data) {
        DoublyNode newNode = new DoublyNode(data);
        newNode.next = head;                 // point forward to old head
        if (head != null) {
            head.prev = newNode;             // old head points back to newNode
        }
        return newNode;                      // newNode is new head
    }

    // -------------------------------------------------------
    // 2. INSERT AT END
    // Algorithm: traverse to last, link both directions
    // Key: newNode.prev = last, last.next = newNode
    // Time: O(n)
    // -------------------------------------------------------
    static DoublyNode insertAtEnd(DoublyNode head, int data) {
        DoublyNode newNode = new DoublyNode(data);

        if (head == null) {
            return newNode;                  // list was empty
        }

        DoublyNode temp = head;
        while (temp.next != null) {         // go to last node
            temp = temp.next;
        }
        temp.next = newNode;                 // last node points forward to new
        newNode.prev = temp;                 // new node points back to last
        return head;
    }

    // -------------------------------------------------------
    // 3. DELETE NODE (by value)
    // Algorithm: find node, then fix both prev and next links
    // Key difference from singly: must update BOTH directions
    // Time: O(n)
    // -------------------------------------------------------
    static DoublyNode deleteNode(DoublyNode head, int key) {
        DoublyNode temp = head;

        // find the node to delete
        while (temp != null && temp.data != key) {
            temp = temp.next;
        }

        if (temp == null) return head;       // key not found

        // fix the forward link of previous node
        if (temp.prev != null) {
            temp.prev.next = temp.next;
        } else {
            head = temp.next;                // deleting head — move head forward
        }

        // fix the backward link of next node
        if (temp.next != null) {
            temp.next.prev = temp.prev;
        }

        return head;
    }

    // -------------------------------------------------------
    // TRAVERSE FORWARD
    // -------------------------------------------------------
    static void traverseForward(DoublyNode head) {
        DoublyNode current = head;
        while (current != null) {
            System.out.print(current.data + " <-> ");
            current = current.next;
        }
        System.out.println("null");
    }

    // -------------------------------------------------------
    // TRAVERSE BACKWARD (start from tail)
    // -------------------------------------------------------
    static void traverseBackward(DoublyNode tail) {
        DoublyNode current = tail;
        while (current != null) {
            System.out.print(current.data + " <-> ");
            current = current.prev;
        }
        System.out.println("null");
    }
}

class DoublyNode {
    int data;
    DoublyNode next;
    DoublyNode prev;

    DoublyNode(int data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
