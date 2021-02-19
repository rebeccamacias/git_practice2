import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class FlowGraph {
    int vertexCt;  // Number of vertices in the graph.
    GraphNode[] G;  // Adjacency list for graph.
    String graphName;  //The file from which the graph was created.
    int maxFlowFromSource;
    int maxFlowIntoSink;
    int totalCost = 0;
    static final int INF = 9999;
    ArrayList<LinkedList<GraphNode>> paths = new ArrayList<>();

    public FlowGraph() {
        this.vertexCt = 0;
        this.graphName = "";
        this.maxFlowFromSource = 0;
        this.maxFlowIntoSink = 0;
    }

    /**
     * create a graph with vertexCt nodes
     * @param vertexCt
     */
    public FlowGraph(int vertexCt) {
        this.vertexCt = vertexCt;
        G = new GraphNode[vertexCt];
        for (int i = 0; i < vertexCt; i++) {
            G[i] = new GraphNode(i);
        }
        this.maxFlowFromSource = 0;
        this.maxFlowIntoSink = 0;
    }

    public static void main(String[] args) {
        FlowGraph graph1 = new FlowGraph();
        graph1.makeGraph("group7.txt");
        for (int i = 0; i < graph1.getMaxFlowIntoSink(); i++){
            if (graph1.bellmanFord()){
                LinkedList<GraphNode> path = graph1.paths.get(i);
                System.out.print("Found flow " + graph1.getMinCapacity(path) + ": ");
                for (int node = 0; node < path.size(); node++){
                    System.out.print(path.get(node).nodeID + " ");
                }
                System.out.println();
                graph1.addFlow(graph1.paths.get(i));
                System.out.println("Current Cost: " + graph1.totalCost);
            }
        }
        System.out.println("Total Cost: " + graph1.totalCost);
    }

    public int getVertexCt() { return vertexCt; }

    public int getMaxFlowFromSource() { return maxFlowFromSource; }

    public int getMaxFlowIntoSink() { return maxFlowIntoSink; }

    /** *
     * @param source
     * @param destination
     * @return the amount of flow that can be added to this edge
     */
    public int getResidual(int source, int destination) { return G[source].getResidual(destination); }

    /**
     * @param source
     * @param destination
     * @return the cost of the edge from source to destination
     */
    public int getCost(int source, int destination) {
        return G[source].getCost(destination);
    }

    /**
     * @return string representing the graph
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("The Graph " + graphName + " \n");
        sb.append("Source " + maxFlowFromSource + " :  Sink " + maxFlowIntoSink + "\n");
//        sb.append("Edge Count: " + edgeCt);

        for (int i = 0; i < vertexCt; i++) {
            sb.append(G[i].toString());
        }
        return sb.toString();
    }

    /**
     * Builds a graph from filename. It automatically
     * inserts backward edges needed for mincost max flow.
     * @param filename
     */
    public void makeGraph(String filename) {
        try {
            graphName = filename;
            Scanner reader = new Scanner(new File(filename));
            vertexCt = reader.nextInt();
            G = new GraphNode[vertexCt];
            for (int i = 0; i < vertexCt; i++) {
                G[i] = new GraphNode(i);
            }
            while (reader.hasNextInt()) {
                int v1 = reader.nextInt();
                int v2 = reader.nextInt();
                int cap = reader.nextInt();
                int cost = reader.nextInt();
                G[v1].addEdge(v1, v2, cap, cost);
                G[v2].addEdge(v2, v1, 0, -cost);
                if (v1 == 0) { maxFlowFromSource += cap; }
                if (v2 == vertexCt - 1) {maxFlowIntoSink += cap;}
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return whether the last path found in
     * the bellmanFord algorithm is a valid path
     * or not
     */
    public boolean bellmanFord(){
        for (int k = 1; k < vertexCt; k++){
            G[k].distance = INF;
            G[k].prevNode = 0;
        }
        int maxTimes = getVertexCt() - 1;
        for (int i = 0; i < maxTimes; i++){
            for (int vertex = 0; vertex < getVertexCt(); vertex++) {
                for (int j = 0; j < G[vertex].succ.size(); j++){
                    int destination = G[vertex].succ.get(j).to;
                    if (G[vertex].distance + getCost(vertex, destination) < G[destination].distance && getResidual(vertex, destination) > 0){
                        G[destination].distance = G[vertex].distance + getCost(vertex, destination);
                        G[destination].prevNode = vertex;
                    }
                }
            }
        }
        for (int vertex = 0; vertex < getVertexCt(); vertex++) {
            for (int j = 0; j < G[vertex].succ.size(); j++){
                int destination = G[vertex].succ.get(j).to;
                if (G[vertex].distance + getCost(vertex, destination) < G[destination].distance && getResidual(vertex, destination) > 0){
                    System.out.println("Negative cycle!");
                }
            }
        }
        findShortestPath(vertexCt - 1);
        System.out.println();
        return !(paths.get(paths.size() - 1).get(0).nodeID == 0 && paths.get(paths.size() - 1).get(1).nodeID == vertexCt - 1);
    }

    /**
     * @param end the node id of the last node
     * in the graph
     */
    public void findShortestPath(int end){
        LinkedList<GraphNode> nodePath = new LinkedList<>();
        while(end > 0){
            nodePath.addFirst(G[end]);
            end = G[end].prevNode;
        }
        nodePath.addFirst(G[0]);
        System.out.println();
        paths.add(nodePath);
    }

    /**
     * @param path a valid shortest path found from the
     * bellmanFord algorithm
     * @return smallest capacity along an edge, tells
     * the largest amount of flow that can be pushed thru
     * that path
     */
    public int getMinCapacity(LinkedList<GraphNode> path){
        int minCapacity = INF;
        for (int vertex = 0; vertex < path.size() - 1; vertex++){
            int from = path.get(vertex).nodeID;
            int to = path.get(vertex + 1).nodeID;
            for (EdgeInfo edge : this.G[from].succ){
                if (edge.to == to){
                    if (minCapacity > edge.residualCt){
                        minCapacity = edge.residualCt;
                    }
                }
            }
        }
        return minCapacity;
    }

    /**
     * @param path a valid shortest path found from the
     * bellmanFord algorithm
     */
    public void addFlow(LinkedList<GraphNode> path){
        int minCapacity = this.getMinCapacity(path);
        for (int vertex = 0; vertex < path.size() - 1; vertex++){
            int from = path.get(vertex).nodeID;
            int to = path.get(vertex + 1).nodeID;
            for (EdgeInfo edge : this.G[from].succ){
                if (edge.to == to){
                    if (edge.capacity != 0){ //forward edge
                        edge.flow += minCapacity;
                        totalCost += getCost(from, to) * minCapacity;
                        edge.residualCt -= minCapacity;
                        this.addReverse(from, to, minCapacity);
                    } else { //backward edge
                        edge.flow += minCapacity;
                        totalCost += getCost(from, to) * minCapacity;
                        edge.residualCt += minCapacity;
                        this.addReverse(from, to, minCapacity);
                    }
                    System.out.println("Edge " + from + "->" + to + " assigned " + edge.flow + " of " + edge.capacity + " at cost " + edge.cost);
                }
            }
        }
    }

    /**
     * @param from source node
     * @param to destination node
     * @param minCapacity smallest edge's capacity -
     * tells amount of flow that can be pushed thru edge
     * */

    public void addReverse(int from, int to, int minCapacity){
        for (EdgeInfo edge : this.G[to].succ){
            if (edge.to == from){
                if (edge.capacity != 0){
                    edge.flow += minCapacity;
                    edge.residualCt -= minCapacity;
                } else {
                    edge.flow += minCapacity;
                    edge.residualCt += minCapacity;
                }
            }
        }
    }
}