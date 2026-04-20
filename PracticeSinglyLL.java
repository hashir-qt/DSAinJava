public class PracticeSinglyLL {

    public static void main(String[] args) {
        Node head = null;

        // Build list: 1 -> 2 -> 3 -> null
        head = insertAtEnd(head, 1);
        head = insertAtEnd(head, 2);
        head = insertAtEnd(head, 3);

        System.out.print("Original:       ");
        traverse(head);                       // 1 -> 2 -> 3 -> null

        // Insert at front
        head = insertAtFront(head, 0);
        System.out.print("After insertFront(0): ");
        traverse(head);                       // 0 -> 1 -> 2 -> 3 -> null

        // Delete node with value 2
        head = deleteNode(head, 2);
        System.out.print("After delete(2): ");
        traverse(head);                       // 0 -> 1 -> 3 -> null

        // Search
        System.out.println("Search for 3: " + search(head, 3));   // true
        System.out.println("Search for 9: " + search(head, 9));   // false

        // Reverse
        head = reverse(head);
        System.out.print("After reverse:  ");
        traverse(head);                       // 3 -> 1 -> 0 -> null
    }

    // -------------------------------------------------------
    // 1. INSERT AT FRONT
    // Algorithm: point newNode.next to current head, update head
    // Time: O(1)
    // -------------------------------------------------------
    static Node insertAtFront(Node head, int data) {
        Node newNode = new Node(data);
        newNode.next = head;                 // new node points to old head
        return newNode;                      // new node becomes head
    }

    // -------------------------------------------------------
    // 2. INSERT AT END
    // Algorithm: traverse to last node, link it to newNode
    // Time: O(n)
    // -------------------------------------------------------
    static Node insertAtEnd(Node head, int data) {
        Node newNode = new Node(data);

        if (head == null) {
            return newNode;                  // list was empty
        }

        Node temp = head;
        while (temp.next != null) {         // go to last node
            temp = temp.next;
        }
        temp.next = newNode;                 // link last node to new node
        return head;
    }

    // -------------------------------------------------------
    // 3. DELETE NODE (by value)
    // Algorithm: find node before target, skip over it
    // Time: O(n)
    // -------------------------------------------------------
    static Node deleteNode(Node head, int key) {
        if (head == null) return null;

        if (head.data == key) {
            return head.next;                // deleting head — move head forward
        }

        Node temp = head;
        while (temp.next != null) {
            if (temp.next.data == key) {
                temp.next = temp.next.next;  // skip over the target node
                return head;
            }
            temp = temp.next;
        }
        return head;                         // key not found, list unchanged
    }

    // -------------------------------------------------------
    // 4. SEARCH
    // Algorithm: traverse and compare each node's data
    // Time: O(n)
    // -------------------------------------------------------
    static boolean search(Node head, int key) {
        Node temp = head;
        while (temp != null) {
            if (temp.data == key) return true;
            temp = temp.next;
        }
        return false;
    }

    // -------------------------------------------------------
    // 5. REVERSE  *** most common exam question ***
    // Algorithm: 3 pointers — prev, curr, next
    //   - save next before breaking the link
    //   - point curr.next backward to prev
    //   - advance both pointers
    // Time: O(n)
    // -------------------------------------------------------
    static Node reverse(Node head) {
        Node prev = null;
        Node curr = head;

        while (curr != null) {
            Node next = curr.next;           // 1. save next
            curr.next = prev;               // 2. reverse the link
            prev = curr;                    // 3. move prev forward
            curr = next;                    // 4. move curr forward
        }
        return prev;                         // prev is now the new head
    }

    // helper to print list
    static void traverse(Node head) {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
}

class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}
