public class HomeLinkedList<E> {

    private class HomeListNode<E> {
        public E data;
        public HomeListNode<E> next;

        public HomeListNode() {
        }
        public HomeListNode(E data) {
            this.data = data;
        }
    }
    /** Initializing the linked list. Allows the list to
     * always have at least one node so there are no
     * special cases for adding the new node in a class
     * @author Rebecca Zitting */
    public HomeListNode<E> head = new HomeListNode<>();

    /** Allows adding nodes to the list, no matter if
     * it is the "first" in the list or subsequent.
     * @author Rebecca Zitting */
    public void addFirst(E data) {
        HomeListNode<E> newNode = new HomeListNode<>(data);
        HomeListNode<E> currNode = head.next;
        HomeListNode<E> prevNode = head;
        while (currNode != null) {
            prevNode = currNode;
            currNode = currNode.next;
        }
        prevNode.next = newNode;
        newNode.next = currNode;
    }
    /** Allows obtaining of EdgeInfo, or cities
     * that this one city reaches
     * @author Rebecca Zitting */
    public EdgeInfo[] getAllNodes(int size) {
        HomeListNode<E> currNode = head.next;
        EdgeInfo[] extendedCitiesArray = new EdgeInfo[size];
        for (int i = 0; i < size; i++) {
            extendedCitiesArray[i] = (EdgeInfo) currNode.data;
            currNode = currNode.next;
        }
        return extendedCitiesArray;
    }
}
