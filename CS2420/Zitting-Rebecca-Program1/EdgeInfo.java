public class EdgeInfo { //implement in LinkedList
    public int from;        // source of edge
    int to;          // destination of edge

    public EdgeInfo(int from, int to){
        this.from = from;
        this.to = to;
    }
    public String toString() { return " " + to ; }
  }
