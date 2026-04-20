class StackLL {
    Node top;
    void push(int x) {
        Node newNode = new Node(x);
        newNode.next = top;
        top = newNode;
    }
    int pop() {
        if (top == null) {
            System.out.println("Stack Underflow!");
            return -1;
        }
        int value = top.data;
        top = top.next;
        return value;
    }
    int peek() {
        if (top == null) return -1;
        return top.data;
    }
    boolean isEmpty() {
        return top == null;
    }


    public static void main(String[] args) {
        StackLL s = new StackLL();
        s.push(1);
        s.push(2);
        while (!s.isEmpty()) {
            System.out.println(s.peek());
            s.pop();
        }

    }
}

class Node{
    int data;
    Node next;

    Node(int data){
        this.data = data;
        this.next = null;
    }
}