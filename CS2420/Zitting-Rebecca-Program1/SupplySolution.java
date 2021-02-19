public class SupplySolution {

    int supplyNodeCount;  //Number of nodes which are supply nodes
    int cityCt;  //Total number of nodes in graph
    int needToCover;  // Nodes which are not covered.
    boolean[] supplies;  //supplies[i] is true if node i holds supplies
    boolean[] covered;   // covered[i] is ture if node i is covered
    Graph graph; //allows SS to access linked list for graph and see adjacent cities

//        public SupplySolution(SupplySolution ss){
//        this.supplies = ss.supplies.clone();
//        this.covered = ss.covered.clone();
//        this.cityCt = ss.cityCt;
//        supplyNodeCount = ss.supplyNodeCount;
//        needToCover = ss.needToCover;
//    }
    public SupplySolution(int cityCt, Graph graph){
        this.cityCt = cityCt;
        this.supplies = new boolean[cityCt];
        this.covered = new boolean[cityCt];
        this.supplyNodeCount = 0;
        this.needToCover = cityCt;
        this.graph = graph;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("SupplyNodeCount " + supplyNodeCount + " Need to cover " + needToCover + "\n SUPPLIES:");
        for (int i = 0; i < cityCt; i++)
            if (supplies[i]) sb.append("1");
            else sb.append("0");
        sb.append("\n COVERED :");
        for (int i = 0; i < cityCt; i++)
            if (covered[i]) sb.append("1");
            else sb.append("0");
        sb.append("\n ");
        return sb.toString();
    }
    /** Recursively calls findMax, addSupplies,
     *  and addCoverage to display final result
     *  of Greedy Approach Algorithm
     *  @author Rebecca Zitting */
    public void findSol() {
        if (this.needToCover == 0){
            return;
        }
        int maxIndex = this.findMax();
        this.addSupplies(maxIndex);
        this.addCoverage(maxIndex);
        this.findSol();
    }
    /** Goes to the id specified in the supplies
     * and covered arrays and changes values to true
     *  @author Rebecca Zitting */
    public void addSupplies(int id ) {
        this.supplies[id] = true;
        this.covered[id] = true;
        this.needToCover--;
        this.supplyNodeCount++;
    }
    /** Traverses city A's "to" list, obtains the city ids connected
     * to city A, and changes those city ids to true in the covered array
     * @author Rebecca Zitting */
    public void addCoverage(int id) {
        int[] nodeTos = graph.graphNodeArray[id].traverseList();
        for (int i = 0; i < nodeTos.length; i++) {
        if (!this.covered[nodeTos[i]]){
                this.covered[nodeTos[i]] = true;
                this.needToCover--;
            }
        }
    }
    /** Traverses city A's "to" list and sees which cities would be covered.
     * Adds one at the end to account for also covering that city itself. */
    public int howManyWouldCover(int id) {
        int wouldCover = graph.graphNodeArray[id].graphNodeCount;
        int[] nodeTos = graph.graphNodeArray[id].traverseList();
        for (int i = 0; i < wouldCover; i++) {
            if (this.covered[nodeTos[i]]) {
                wouldCover--;
            }
        }
        return wouldCover+1;
    }
    /** Calls howManyWouldCover on each city and finds
     * the index of the city that covers the most cities
     * initially. This index is essential to findSol.  */
    public int findMax() {
        int max = 0;
        int indexOfMax = -1;
        for (int i = 0; i < cityCt; i++) {
            int numberCovered = this.howManyWouldCover(i);
            if (numberCovered > max && !this.covered[i]) {
                max = numberCovered;
                indexOfMax = i;
            }
        }
        return indexOfMax;
    }
}
