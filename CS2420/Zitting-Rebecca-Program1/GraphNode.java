public class GraphNode {
    public int nodeID;
    public int graphNodeCount;
    public String nodeName;
    public HomeLinkedList<EdgeInfo> graphNode;

//    public GraphNode( ){
//        this.nodeID = 0;
//        this.graphNode = new HomeLinkedList<EdgeInfo>(); //make a linked list for the cities this graph node connects to
//        this.nodeName="";
//        this.graphNodeCount=0;
//     }

    public GraphNode(int nodeID){
        this.nodeID = nodeID;
        this.graphNode = new HomeLinkedList<>(); //make linked list for cities this graph node connects to, will be stored in an index of an array
        this.nodeName="noName";
        this.graphNodeCount=0;
      }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(nodeID+ ": " + nodeName);
        sb.append("\n");
        return sb.toString();
    }
    /** Adds connection of cities in the graph node
     * @author Rebecca Zitting */
    public void addEdge(int startCity, int endCity){
        graphNode.addFirst( new EdgeInfo(startCity,endCity) );
        graphNodeCount++;
    }
    /** Gets all edge info for one node, assigns the "to" cities in
     * a new array to be used in SupplySolution
     * @author Rebecca Zitting */
    public int[] traverseList() {
        EdgeInfo[] nodeList = graphNode.getAllNodes(graphNodeCount);
        int[] extendedCitiesArray = new int[graphNodeCount];
        for (int i = 0; i < graphNodeCount; i++) {
            extendedCitiesArray[i] = nodeList[i].to;
        }
        return extendedCitiesArray;
    }
}
