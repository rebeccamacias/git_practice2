public class DynamicMedian<E extends Comparable> {
    private E element;
    private DynamicMedian<E> currMedian;
    private SkewHeap<E> greaterThan = new SkewHeap<>();
    private LeftistHeap<E> lessThan = new LeftistHeap<>();

    public DynamicMedian(){ currMedian = null; }

    public DynamicMedian(E value){ this.element = value; }

    public static void main(String[] args) {
        DynamicMedian<Integer> test = new DynamicMedian<>();

        test.addValue(5);
        test.addValue(7);
        test.addValue(10);
        test.addValue(15);
        test.addValue(12);
        test.addValue(8);
        test.addValue(3);
        test.addValue(1);
        test.addValue(2);
        test.addValue(42);
        test.addValue(20);
        System.out.println(test.currMedian.element);
    }

    /**
     * A method to insert a new value into
     * the median calculator
     * Does not allow for duplicates
     * @author Rebecca Zitting
     * @param newValue value to be inserted
     */
    public void addValue(E newValue){
        if (currMedian == null){
            currMedian = new DynamicMedian<>(newValue);
            return;
        }
        if (newValue.compareTo(currMedian.element) < 0){
            lessThan.insert(newValue);
        }
        if (newValue.compareTo(currMedian.element) > 0){
            greaterThan.insert(newValue);
        }
        if (lessThan.getSize() + 1 != greaterThan.getSize() && lessThan.getSize() < greaterThan.getSize()){
            lessThan.insert(currMedian.element);
            currMedian = new DynamicMedian<>(greaterThan.deleteMin());
            return;
        }
        if (lessThan.getSize() - 1 != greaterThan.getSize() && lessThan.getSize() > greaterThan.getSize()){
            greaterThan.insert(currMedian.element);
            currMedian = new DynamicMedian<>(lessThan.deleteMax());
            return;
        }
    }

    /**
     * A method to return the current median's element
     * @author Rebecca Zitting
     * @return current median's element
     */
    public E getMedian(){
        return currMedian.element;
    }
}
