import java.io.File;
import java.util.Scanner;

public class Graph {
    int cityCt;  //Number of nodes in the graph.
    GraphNode[] graphNodeArray;  //Tells which cities are adjacent to one node
    String graphName;  //The file from which the graph was created.

    public Graph() {
        this.cityCt = 0;
        this.graphName = "";
      }

    public void makeGraph(String filename) {
        try {
            graphName = filename;
            Scanner reader = new Scanner( new File( filename ) );
            System.out.println( "\n" + filename );
            cityCt = Integer.parseInt(reader.nextLine()); //reads the first line of the txt to find out how many cities are in the text doc
            graphNodeArray = new GraphNode[cityCt]; //make a new empty array of type graphNode of size cityCt
            for (int i = 0; i < cityCt; i++) {
                graphNodeArray[i] = new GraphNode( i ); //make a new graph node object at index[i] with nodeID i
            }
            for (int i = 0; i < cityCt; i++) {
                String[] values = reader.nextLine().split(" "); //make an array of type string for each line. resets each line. strings are split on the space and accessed with index
                int startCity = Integer.parseInt(values[0]); //central city in graph from which lines extend
                graphNodeArray[startCity].nodeName = values[1]; //city name
                for (int v = 2; v < values.length; v++){
                    int endCity = Integer.parseInt(values[v]); //city where central city extends to
                    graphNodeArray[startCity].addEdge(startCity,endCity);
                }
             }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}