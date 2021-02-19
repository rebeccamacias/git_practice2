public class EdgeInfo {
    int from;        // source of edge
    int to;          // destination of edge
    int capacity;    // capacity of edge
    int cost;        // cost of edge
    int residualCt;  //how much flow can go along an edge
    int flow;        //how much flow is currently on an edge

    public EdgeInfo(int from, int to, int capacity, int cost){
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.cost = cost;
        this.residualCt = capacity;
        this.flow = 0;
    }

    public String toString(){
        return "Edge " + from + "->" + to + " ("+ capacity + ", " + cost + ") " ;
    }
}
