public class dsadoubly {
    


    public static void main(String[] args) {
        DNode first = new DNode(1);
        DNode second = new DNode(2);
        DNode third = new DNode(3);

        first.next = second;
        second.next = third;

        third.prev = second;
        second.prev = first;

        second.traversebackwrd(third);
    }
}

class DNode {
    int data;
    DNode next;
    DNode prev;

    DNode(int data) {
       this.data = data;
       this.next = null;
       this.prev = null;

    }
    
    void traverseforwrd(DNode head){
        DNode current = head;
        while(current != null){
            System.out.print(current.data +" <-> ");
            current = current.next;
        } 
    }

    void traversebackwrd(DNode tail){
        DNode current = tail;
        while(current != null){
            System.out.println(current.data +" <->");
            current = current.prev;
        }

    }

}