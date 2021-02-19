public class UnionFind {
    Integer[] voters;

    /**
     * @author Rebecca Zitting
     * @param numOfVoters number of voters passed into constructor
     * Public constructor for UnionFind class
     * Defines an ArrayList of all voters 0-(numOfVoters - 1)
     * and sets all nodes initially to -1 to denote "roots."
     */
    public UnionFind(int numOfVoters){
        this.voters = new Integer[numOfVoters];

        for (int i = 0; i < numOfVoters; i++){
            voters[i] = -1;
        }
    }

    /**
     * @author Rebecca Zitting
     * Union by Size method for class
     * @param root1 one root of tree to be merged
     * @param root2 other root of tree to be merged
     */
    public void union( int root1, int root2 ) {
        int find1 = find(root1);
        int find2 = find(root2);
        if (find1 == find2){
            return;
        }
        if (voters[find2] < voters[find1]) {
            voters[find2] += voters[find1];
            voters[find1] = find2;
        } else {
            voters[find1] += voters[find2];
            voters[find2] = find1;
        }
    }


    /**
     * @author Rebecca Zitting
     * Method finds the root of a certain node and returns it
     * @param i indicates the node whose root we want to know
     * @return root that holds the node in its tree somewhere
     */
    int find(int i) {
        if (voters[i] < 0) { return i; } //denotes a root with variable amounts of children
        voters[i] = find(voters[i]);
        return voters[i];
    }

    public static void main(String[] args) {
        UnionFind test = new UnionFind(8);
        test.union(4, 3);
        test.union(4, 5);
        test.union(6 ,7);
        test.union(4,7);
        test.union(4,7);
        System.out.println(test);
    }
}
