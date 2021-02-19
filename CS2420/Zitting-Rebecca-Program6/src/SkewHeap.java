public class SkewHeap<E extends Comparable> { //min Heap - values > currMedian
    private static class Node<E> {
        E       element;      // The data in the node
        Node<E> left;         // Left child
        Node<E> right;        // Right child


        Node( E theElement ) {
            this( theElement, null, null );
        }


        Node( E theElement, Node<E> lt, Node<E> rt ){
            element = theElement;
            left = lt;
            right = rt;
        }
    }
    private int size;
    private Node<E> root;

    public SkewHeap(){
        root = null;
    }

    public static void main(String[] args) {
        SkewHeap<Integer> test = new SkewHeap<>();

        System.out.println("Skew Heap");
        System.out.println("Insert 2");
        test.insert(2);
        System.out.println("Size: " +  test.size);
        System.out.println("Root element: " + test.root.element);

        test.insert(4);
        test.insert(10);
        test.insert(6);
        System.out.println("Root after inserting 4, 10, & 6: " + test.root.element);

        test.deleteMin();
        test.deleteMin();
        Integer top =  test.deleteMin();
        System.out.println("Element after removing top 3 elements: " + top);
    }

    /**
     * A method to find the size of the Skew Heap
     * @author Rebecca Zitting
     * @return the size of the Skew Heap
     */
    public int getSize(){ return this.size; }

    /**
     * A method to insert a new value into the
     * Skew Heap
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
     * A method to merge two trees together
     * @param t1 The 1st tree to be merged
     * @param t2 The 2nd tree to be merged
     * @return the resulting tree after merge(t1, t2)
     */
    private Node<E> merge( Node<E>t1, Node<E> t2)       {
        Node<E> small;
        if (t1==null)  return t2;
        if (t2==null) return t1;
        if (t1.element.compareTo( t2.element ) < 0) {
            t1.right = merge(t1.right, t2);
            small=t1;}
        else {
            t2.right = merge(t2.right, t1);
            small=t2;}
        swapkids(small);
        return small;
    }

    /**
     * A method to perform an integral operation of the Skew heap,
     * where a node's children need to be swapped in order to keep the
     * structure of the Skew heap.
     * @author Rebecca Zitting
     * @param t root of tree whose kids need to be swapped
     */
    private void swapkids(Node<E> t){
        Node<E> tmp = t.right;
        t.right = t.left;
        t.left = tmp;
    }

    /**
     * A method to delete the min element in a skew heap,
     * which is always at the root by definition of a
     * min skew heap.
     * @author Rebecca Zitting
     * @return the min element (root) that's being deleted
     */
    public E deleteMin(){
        this.size--;
        Node<E> tmp = root;
        root = merge(root.left, root.right);
        return tmp.element;
    }
}
