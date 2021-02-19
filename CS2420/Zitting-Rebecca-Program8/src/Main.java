import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            FlowGraph graph = new FlowGraph();
            graph.makeGraph(args[i]);
            System.out.println();
            System.out.print(args   [i] + "Max Flow SPACE " + graph.getMaxFlowIntoSink() + " ASSIGNED " + Math.min(graph.getMaxFlowIntoSink(), graph.getMaxFlowFromSource()));
            for (int j = 0; j < graph.getMaxFlowIntoSink(); j++){
                if (graph.bellmanFord()){
                    LinkedList<GraphNode> path = graph.paths.get(j);
                    System.out.print("Found Flow " + graph.getMinCapacity(path) + ": ");
                    for (int node = 0; node < path.size(); node++){
                        System.out.print(path.get(node).nodeID + " ");
                    }
                    System.out.println();
                    graph.addFlow(graph.paths.get(j));
                } else {
                    break;
                }
            }
            System.out.println("TOTAL COST: " + graph.totalCost);
        }
    }
}
