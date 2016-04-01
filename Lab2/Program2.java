/*
 * Name: Jonathon Reynolds
 * EID: jar6493
 */

import java.util.ArrayList;
import java.util.ListIterator;

/* Your solution goes in this file.
 *
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */

public class Program2 extends VertexNetwork {
    /* DO NOT FORGET to add a graph representation and 
       any other fields and/or methods that you think 
       will be useful. 
       DO NOT FORGET to modify the constructors when you 
       add new fields to the Program2 class. */

    private AdjacencyList adjList = null;

    Program2() {
        super();
    }

    Program2(String locationFile) {
        super(locationFile);
    }

    Program2(String locationFile, double transmissionRange) {
        super(locationFile, transmissionRange);
    }

    Program2(double transmissionRange, String locationFile) {
        super(transmissionRange, locationFile);
    }

    public void setTransmissionRange(double transmissionRange) {
        super.setTransmissionRange(transmissionRange);

        buildGraph();
    }

    private void buildGraph() {
        adjList = new AdjacencyList(this.location.size());

        for (Edge edge : this.edges) {
            Vertex u = location.get(edge.getU());
            Vertex v = location.get(edge.getV());

            if (u.distance(v) < this.transmissionRange) {
                adjList.add(edge);
            }
        }

        // adjList.print();
    }

    public ArrayList<Vertex> gpsrPath(int sourceIndex, int sinkIndex) {
        /* This method returns a path from a source at location sourceIndex 
           and a sink at location sinkIndex using the GPSR algorithm. An empty 
           path is returned if the GPSR algorithm fails to find a path. */
        /* The following code is meant to be a placeholder that simply 
           returns an empty path. Replace it with your own code that 
               implements the GPSR algorithm. */
        ArrayList<Vertex> result = new ArrayList<Vertex>();
        ArrayList<Vertex> emptyResult = new ArrayList<Vertex>(0);
        Vertex source = location.get(sourceIndex);
        Vertex sink = location.get(sinkIndex);

        // System.out.println("Starting from " + sourceIndex + ", going to " + sinkIndex);

        // add the source vertex to the list to start
        int closestIndex = sourceIndex;
        Vertex closestVertex = source;
        result.add(source);

        // if the source node is also the sink node, add it to the result again and return
        if (sourceIndex == sinkIndex) {
            // System.out.println("The source and sink are the same.");
            result.add(source);
            return result;
        }

        // every step in GPSR should produce a vertex that is at least closer to sink than source
        double closestDistance = source.distance(sink);

        Boolean keepGoing = true;
        while (keepGoing) { // continue looping until inner loop has explored all nodes
            Boolean foundCloser = false;
            ListIterator<Data> edges = adjList.getEdges(closestIndex);

            // System.out.println("Looking for node closer to sink from " + closestIndex);

            while (edges.hasNext()) {
                Data d = edges.next();
                int nextIndex = d.getNext();
                // System.out.println("Exploring edge to " + nextIndex);
                Vertex nextVertex = location.get(nextIndex);
                if (nextVertex.distance(sink) < closestDistance) {
                    // System.out.println(nextIndex + " is closer.");
                    closestDistance = nextVertex.distance(sink);
                    closestVertex = nextVertex;
                    closestIndex = nextIndex;
                    foundCloser = true;
                }
            }

            if (foundCloser) { // found a closer node to sink, so add it to list
                // System.out.println(closestIndex + " is closest.");
                // System.out.println("====================================================");
                // System.out.println("");
                result.add(closestVertex);
                
                if (closestIndex == sinkIndex) { // found the actual sink, so end routing function
                    // System.out.println("All done with GPSR.");
                    keepGoing = false;
                }
            }
            else { // failure to find a closer vertex along all edges
                // System.out.println("Failed to find a closer vertex");
                result = emptyResult;
                keepGoing = false;
            }
        }

        return result;
    }

    public ArrayList<Vertex> dijkstraPathLatency(int sourceIndex, int sinkIndex) {
        /* This method returns a path (shortest in terms of latency) from a source at
           location sourceIndex and a sink at location sinkIndex using Dijkstra's algorithm.
           An empty path is returned if Dijkstra's algorithm fails to find a path. */
        /* The following code is meant to be a placeholder that simply 
           returns an empty path. Replace it with your own code that 
               implements Dijkstra's algorithm. */
        return new ArrayList<Vertex>(0);
    }

    public ArrayList<Vertex> dijkstraPathHops(int sourceIndex, int sinkIndex) {
        /* This method returns a path (shortest in terms of hops) from a source at
           location sourceIndex and a sink at location sinkIndex using Dijkstra's algorithm.
           An empty path is returned if Dijkstra's algorithm fails to find a path. */
        /* The following code is meant to be a placeholder that simply 
           returns an empty path. Replace it with your own code that 
               implements Dijkstra's algorithm. */
        return new ArrayList<Vertex>(0);
    }

}

