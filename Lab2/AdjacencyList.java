/* 
 * Jonathon Reynolds
 * jar6493
 *
 */

import java.util.LinkedList;
import java.util.ArrayList;

public class AdjacencyList {
    private ArrayList<LinkedList<Data>> adjList;

    public AdjacencyList(int numVertices) {
        this.adjList = new ArrayList<LinkedList<Data>>();

        // initialize all internal lists
        for (int i = 0; i < numVertices; i++) {
           adjList.add(new LinkedList<Data>());
        }
    }

    public void add(Edge e) {
        int from = e.getU();
        int next = e.getV();
        double latency = e.getW();
        Data d = new Data(next, latency);

        LinkedList<Data> ll = adjList.get(from);
        ll.add(d);
    }

    public LinkedList<Data>getEdges(int u) {
        return adjList.get(u);
    }
    
}
