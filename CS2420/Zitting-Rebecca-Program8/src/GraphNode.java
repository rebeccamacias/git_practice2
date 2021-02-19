import java.util.*;

public class GraphNode {
    static int INF = 9999;
    public int nodeID;
    public LinkedList<EdgeInfo> succ;
    public int prevNode;
    public int distance;

    public GraphNode( ){
        this.nodeID = 0;
        this.succ = new LinkedList<EdgeInfo>();
        this.prevNode = 0;
        this.distance = INF;
     }

    public GraphNode(int nodeID){
        this.nodeID = nodeID;
        this.succ = new LinkedList<EdgeInfo>();
        this.prevNode = 0;
        if (nodeID == 0){
            this.distance = 0;
        } else {
            this.distance = INF;
        }
      }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(nodeID+ ": ");
        Iterator<EdgeInfo> itr = succ.iterator();
       while (itr.hasNext()){
            sb.append(itr.next().toString());
        }
        sb.append("\n");
        return sb.toString();
    }

    public void addEdge(int v1, int v2, int capacity, int cost){
        succ.addFirst( new EdgeInfo(v1,v2,capacity,cost) );
    }

    public int getCost(int destination){
        Iterator<EdgeInfo> itr = succ.iterator();
        while (itr.hasNext()){
            EdgeInfo e = itr.next();
            if (e.to == destination)
                return e.cost;
        }
        return 0;
    }

    public int getResidual(int destination){
        Iterator<EdgeInfo> itr = succ.iterator();
        while (itr.hasNext()) {
            EdgeInfo e = itr.next();
            if (e.to == destination) {
                return e.residualCt;
            }
        }
        return 0;
    }
}
