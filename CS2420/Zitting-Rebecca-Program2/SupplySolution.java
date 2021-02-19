public class SupplySolution {

    int supplyNodeCount;  //Number of nodes which are supply nodes
    int cityCt;  //Total number of nodes in graph
    int needToCover;  // Nodes which are not covered.
    boolean[] supplies;  //supplies[i] is true if node i holds supplies
    boolean[] covered;   // covered[i] is true if node i is covered
    Graph graph; //allows SS to access linked list for graph and see adjacent cities

    /** Makes a copy of a SupplySolution */
    public SupplySolution(SupplySolution ss){
    this.supplies = ss.supplies.clone();
    this.covered = ss.covered.clone();
    this.cityCt = ss.cityCt;
    this.graph = ss.graph;
    supplyNodeCount = ss.supplyNodeCount;
    needToCover = ss.needToCover;
    }

    /** Makes initial SupplySolution */
    public SupplySolution(int cityCt, Graph graph){
        this.cityCt = cityCt;
        this.supplies = new boolean[cityCt];
        this.covered = new boolean[cityCt];
        this.supplyNodeCount = 0;
        this.needToCover = cityCt;
        this.graph = graph;
    }

    /** Prints out report of Solution's arrays */
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

    /** Recursively calls findSol and finds the max index
     *  of the city that would cover the biggest amount of cities
     *  and prints them out.
     *  @author Rebecca Zitting */
    public void findSol() {
        if (this.needToCover == 0){
            return;
        }
        int maxIndex = this.findMax();
        this.addSuppliesAndCover(maxIndex);
        this.findSol();
    }

    /** Goes to the id specified in the supplies
     * and covered arrays and changes values to true.
     * Returns new SupplySolution for the optimized recursion.
     *  @author Rebecca Zitting */
    public SupplySolution addSuppliesAndCover(int id, SupplySolution partialSol) {
        SupplySolution newSupplySol = new SupplySolution(partialSol);
        newSupplySol.supplies[id] = true;
        if(!newSupplySol.covered[id]) {
            newSupplySol.covered[id] = true;
            newSupplySol.needToCover--;
        }
        newSupplySol.supplyNodeCount++;
        int[] nodeTos = newSupplySol.graph.graphNodeArray[id].traverseList();
        for (int i = 0; i < nodeTos.length; i++) {
        if (!newSupplySol.covered[nodeTos[i]]){
                newSupplySol.covered[nodeTos[i]] = true;
                newSupplySol.needToCover--;
            }
        }
        return newSupplySol;
    }
    /** Goes to the id specified in the supplies
     * and covered arrays and changes values to true.
    @author Rebecca Zitting */
    public void addSuppliesAndCover(int id) {
        this.supplies[id] = true;
        if(!this.covered[id]) {
            this.covered[id] = true;
            this.needToCover--;
        }
        this.supplyNodeCount++;
        int[] nodeTos = this.graph.graphNodeArray[id].traverseList();
        for (int i = 0; i < nodeTos.length; i++) {
            if (!this.covered[nodeTos[i]]){
                this.covered[nodeTos[i]] = true;
                this.needToCover--;
            }
        }
    }
    /** Traverses city A's "to" list and sees which cities would be covered
     * that have not already been covered, if city A were a supply city.
     * Adds one to wouldCover at the end to account for also covering city A itself.
     * @author Rebecca Zitting */
    public int howManyWouldCover(int id) {
        int wouldCover = graph.graphNodeArray[id].graphNodeCount;
        int[] nodeTos = graph.graphNodeArray[id].traverseList();
        for (int i = 0; i < wouldCover; i++) {
            if (this.covered[nodeTos[i]]) { wouldCover--; }
        }
        return wouldCover+1;
    }
    /** Calls howManyWouldCover on each city and finds
     * the index of the city that covers the most cities
     * initially. This index is essential to findSol.
     * @author Rebecca Zitting */
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

    /**
     * @param nodeID The current node you can consider to be a supply city.
     *               Cities with a smaller nodeID are fixed (as being a supply city or not being a supply city)
     * @param allowedSupplyNodes  Total number of cities allowed to be supply cities
     * @param partialSol Current partial solution
     * @param worstSupplyCt the worst case scenario of SupplyNodes for the graph, given by the Greedy Solution
     * @return best solution found from including nodeID or any larger node in the solution
     */

    SupplySolution getSupply(int nodeID, int allowedSupplyNodes, SupplySolution partialSol, int worstSupplyCt){
        if (nodeID >= cityCt){ return partialSol; } //can't get any better
        if (allowedSupplyNodes == 0) { return partialSol; } //can't get any better
        if (partialSol.needToCover == 0){ return partialSol; } //We're done.
        if (partialSol.supplyNodeCount + 1 > worstSupplyCt) { return partialSol; } //Don't even try to go to the next recursive case. Greedy is as good as we can get.
        int[] nodeTos = partialSol.graph.graphNodeArray[nodeID].traverseList();
        int numOfIDsLessThanNodeID = 0;
        for (int i = 0; i < nodeTos.length; i++){
            if (nodeTos[i] < nodeID){
                numOfIDsLessThanNodeID++;
            }
        }
        if (numOfIDsLessThanNodeID == nodeTos.length && numOfIDsLessThanNodeID != 0 && !(partialSol.covered[nodeID])){ //Getting rid of attempts w/ too few nodes
            return getSupply(nodeID + 1, allowedSupplyNodes - 1, addSuppliesAndCover(nodeID, partialSol), worstSupplyCt);
        }
        SupplySolution useIt = getSupply(nodeID + 1, allowedSupplyNodes - 1, addSuppliesAndCover(nodeID, partialSol), worstSupplyCt);
        SupplySolution dont = getSupply(nodeID + 1, allowedSupplyNodes, partialSol, worstSupplyCt);
        return betterOf(useIt, dont);
    }

    /** Returns the case of lowest possible SupplyNodes working solution */
    public SupplySolution betterOf(SupplySolution sol1, SupplySolution sol2) {
        if (sol1.needToCover == 0 && sol1.supplyNodeCount <= sol2.supplyNodeCount){ return sol1; } //Optimal case
        if (sol2.needToCover == 0 && sol2.supplyNodeCount <= sol1.supplyNodeCount){ return sol2; } //Optimal case
        //In some cases, however, the needToCover will NOT
        //be 0, such as in the middle of the recursion.
        //Therefore, we want to take the solution with the
        //smaller supplyNodeCount and the smaller needToCover count.
        if (sol1.supplyNodeCount <= sol2.supplyNodeCount && sol1.needToCover < sol2.needToCover){ return sol1; }
        else if(sol2.supplyNodeCount <= sol1.supplyNodeCount && sol2.needToCover < sol1.needToCover){ return sol2; }
        else{ return sol1; }
        }
    }
