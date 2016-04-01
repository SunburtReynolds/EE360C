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
    private ArrayList<Node> nodes;

    public AdjacencyList(int numVertices) {
        this.adjList = new ArrayList<LinkedList<Data>>();
        this.nodes = new ArrayList<Node>();

        for (int i = 0; i < numVertices; i++) {
            // initialize all internal lists
            adjList.add(new LinkedList<Data>());

            // create list of nodes
            nodes.add(new Node(i));
        }
    }

    public void add(Edge e) {
        int u = e.getU();
        int v = e.getV();
        double weight = e.getW();

        Node nodeU = nodes.get(u);
        Node nodeV = nodes.get(v);

        adjList.get(u).add(new Data(nodeV, weight));
        adjList.get(v).add(new Data(nodeU, weight));
    }

    public ListIterator<Data> getNeighbors(int fromIndex) {
        return adjList.get(fromIndex).listIterator();
    }

    public ArrayList<Node> getNodes() {
        return nodes;
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
