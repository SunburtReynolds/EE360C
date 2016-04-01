/* 
 * Jonathon Reynolds
 * jar6493
 *
 */

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.ListIterator;

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
        int u = e.getU();
        int v = e.getV();
        double weight = e.getW();

        Node nodeU = new Node(u);
        Node nodeV = new Node(v);

        adjList.get(u).add(new Data(nodeV, weight));
        adjList.get(v).add(new Data(nodeU, weight));
    }

    public ListIterator<Data> getNeighbors(int fromIndex) {
        return adjList.get(fromIndex).listIterator();
    }

    public void print() {
        System.out.println("");
        for (int i = 0; i < adjList.size(); i++) {
            LinkedList<Data> ll = adjList.get(i);
            ListIterator<Data> li = ll.listIterator();
            System.out.println("LinkedList for " + i);
            while (li.hasNext()) {
                Data d = li.next();
                System.out.print("(" + d.getNode().getIndex() + ", " + d.getWeight() + ") -> ");
            }
            System.out.println("");
        }
    }
}
