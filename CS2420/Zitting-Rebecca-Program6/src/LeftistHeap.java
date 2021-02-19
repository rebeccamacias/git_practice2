public class LeftistHeap<E extends Comparable> { //max heap - values < currMedian
    private static class Node<E> {
        E element;      // The data in the node
        Node<E> left;         // Left child
        Node<E> right;        // Right child
        int npl;          // null path length


        Node(E theElement) { //initial constructor
            this(theElement, null, null);
        }

        Node(E theElement, Node<E> lt, Node<E> rt) { //subsequent constructor
            element = theElement;
            left = lt;
            right = rt;
            npl = 0;
        }
    }
    private int size;
    private Node<E> root;

    public LeftistHeap(){ root = null; }

    public static void main(String[] args) {
        LeftistHeap<Integer> test = new LeftistHeap<>();

        System.out.println("Leftist Heap");
        System.out.println("Insert 2");
        test.insert(2);
        System.out.println("Size: " +  test.size);
        System.out.println("Root element: " + test.root.element);

        test.insert(4);
        test.insert(10);
        test.insert(6);
        System.out.println("Root after inserting 4, 10, & 6: " + test.root.element);

        test.deleteMax();
        test.deleteMax();
        Integer top =  test.deleteMax();
        System.out.println("Element after removing top 3 elements: " + top);
    }

    /**
     * A method to insert a new value into the
     * Leftist Heap
     * @author Rebecca Zitting
     * @param value the value being inserted
     */
    public void insert(E value){
        if (root == null){
            root = new Node<>(value);
            this.size = 1;
            return;
        }
        Node<E> newElement = new Node<>(value);
        root = merge(root, newElement);
        this.size++;
    }

    /**
     * A method to delete the max element in a leftist heap,
     * which is always at the root by definition of a
     * max leftist heap.
     * @author Rebecca Zitting
     * @return the max element (root) that's being deleted
     * the heap
     */
    public E deleteMax(){
        if (root == null){
            return null;
        }
        this.size--;
        Node<E> tmp = root;
        root = merge(root.left, root.right);
        return tmp.element;
    }

    /**
     * @param t1 1st tree to be merged
     * @param t2 2nd tree to be merged
     * @return resulting tree of merge(t1, t2)
     */
    private Node<E> merge(Node<E> t1, Node<E> t2) { //everything is a merge
        Node<E> big;
        if (t1 == null) return t2;
        if (t2 == null) return t1;
        if (t1.element.compareTo(t2.element) > 0) {
            t1.right = merge(t1.right, t2);
            big = t1;
        } else {
            t2.right = merge(t2.right, t1);
            big = t2;
        }
        if (getNpl(big.left) < getNpl(big.right)) { swapkids(big); }
        setNullPathLength(big);
        return big;
    }

    /**
     * A method to get the size of the Leftist Heap
     * @author Rebecca Zitting
     * @return the size of the Leftist Heap
     */
    public int getSize(){ return this.size; }

    /**
     * A method to perform an integral operation of the leftist heap,
     * where a node's children need to be swapped in order to keep the
     * structure of the leftist heap.
     * @author Rebecca Zitting
     * @param t root of tree whose kids need to be swapped
     */
    private void swapkids(Node<E> t){
        Node<E> tmp = t.right;
        t.right = t.left;
        t.left = tmp;
    }

    /**
     * A method to set the Null Path Length of
     * a certain node based on it's children
     * @author Rebecca Zitting
     * @param t Node whose npl we are setting
     */
    private void setNullPathLength(Node<E> t){
        int left = getNpl(t.left);
        int right = getNpl(t.right);
        if (left <= right){         //arbitrarily chooses left as the npl to add to if both npls are the same
            t.npl = getNpl(t.left) + 1;
        } else {
            t.npl = getNpl(t.right) + 1;
        }
    }

    /**
     * A method to find out the Null Path Length
     * of a certain node
     * @param t Node whose npl we are checking
     * @return the value of t's npl
     */
    private int getNpl(Node<E> t) {
        if (t == null) return -1;
        return t.npl;
    }

}
