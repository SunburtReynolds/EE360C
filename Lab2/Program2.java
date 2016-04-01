/*
 * Name: Jonathon Reynolds
 * EID: jar6493
 */

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.PriorityQueue;

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
            ListIterator<Data> neighbors = adjList.getNeighbors(closestIndex);

            // System.out.println("Looking for node closer to sink from " + closestIndex);

            while (neighbors.hasNext()) {
                Data d = neighbors.next();
                int nextIndex = d.getNode().getIndex();
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
        return dijkstra(sourceIndex, sinkIndex, false);
    }

    public ArrayList<Vertex> dijkstraPathHops(int sourceIndex, int sinkIndex) {
        /* This method returns a path (shortest in terms of hops) from a source at
           location sourceIndex and a sink at location sinkIndex using Dijkstra's algorithm.
           An empty path is returned if Dijkstra's algorithm fails to find a path. */
        /* The following code is meant to be a placeholder that simply 
           returns an empty path. Replace it with your own code that 
               implements Dijkstra's algorithm. */
        return dijkstra(sourceIndex, sinkIndex, true);
    }

    private ArrayList<Vertex> dijkstra(int sourceIndex, int sinkIndex, Boolean withHops) {
        // initialize all nodes
        ArrayList<Node> nodes = adjList.getNodes();
        for (Node n : nodes) {
            n.setDistance(Double.MAX_VALUE);
            n.setPrevious(null);
            n.setVisited(false);
        }

        // set distance from source to source as 0
        Node source = nodes.get(sourceIndex);
        source.setDistance(0);

        // add all nodes to the priority queue
        PriorityQueue<Node> q = new PriorityQueue<Node>(location.size(), new NodeComparator());
        for (Node n : nodes) {
            q.add(n);
        }

        // System.out.println("Looking for path from " + sourceIndex + " to " + sinkIndex);
        Boolean failed = false;
        while (q.size() > 0) {
            // get node with lowest distance to source, remove it from queue, and mark it visited
            Node nodeU = q.poll();
            // System.out.println("Centered at node " + nodeU.getIndex());
            nodeU.setVisited(true);

            // the algo has failed if the node at the top of the queue has a distance of infinity
            if (nodeU.getDistance() == Double.MAX_VALUE) {
                // System.out.println("Top of the queue is unreachable node.");
                failed = true;
                break;
            }

            // the algo has finished if the node at the top of the queue is the sink
            if (nodeU.getIndex() == sinkIndex) {
                // System.out.println("Found the sink.");
                break;
            }

            // for each neighbor that hasn't been visited, update distances
            ListIterator<Data> li = adjList.getNeighbors(nodeU.getIndex());
            while (li.hasNext()) {
                Data d = li.next();
                Node nodeV = d.getNode();
                // System.out.println("Inspecting neighbor node " + nodeV.getIndex());

                if (!nodeV.getVisited()) {
                    // System.out.println(nodeV.getIndex() + " has not been visited.");
                    // use a weight of 1.0 if this is dijkstra with Hops instead of Latency
                    double weight = withHops ? 1.0 : d.getWeight();
                    double alt = nodeU.getDistance() + weight;
                    // System.out.println("Current distance " + nodeV.getDistance());
                    // System.out.println("Alt distance " + alt);
                    if (alt < nodeV.getDistance()) { // if the path to nodeV is shorter through nodeU, change distance and previous on nodeV
                        nodeV.setDistance(alt);
                        nodeV.setPrevious(nodeU);

                        // must update priority queue by removing and inserting updated nodeV
                        q.remove(nodeV);
                        q.add(nodeV);
                    }
                }
            }
        }

        if (failed) {
            return new ArrayList<Vertex>(0);
        }

        ArrayList<Node> reverseResult = new ArrayList<Node>();
        ArrayList<Vertex> result = new ArrayList<Vertex>();

        // if sink is the source, then add vertex twice and return
        if (sinkIndex == sourceIndex) {
            result.add(location.get(sourceIndex));
            result.add(location.get(sourceIndex));
            return result;
        }

        // walk through the shortest path from sink to source (backward) using previous pointer
        Node currentNode = nodes.get(sinkIndex);
        while (currentNode.getPrevious() != null) {
            reverseResult.add(currentNode);
            currentNode = currentNode.getPrevious();
        }
        // add on last node
        reverseResult.add(currentNode);

        // flip order to match requirements
        for (int i = reverseResult.size() - 1; i >= 0; i--) {
            Node n = reverseResult.get(i);
            Vertex v = location.get(n.getIndex());
            result.add(v);
        }

        return result;
    }
}

