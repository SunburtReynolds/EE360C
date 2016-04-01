/* 
 * Jonathon Reynolds
 * jar6493
 *
 */

public class Node {

    private int index;
    private double distance;
    private Node previous;
    private Boolean visited;

    public Node(int index) {
        this(index, Double.MAX_VALUE);
    }

    public Node(int index, double distance) {
        this.index = index;
        this.distance = distance;
        this.previous = null;
        this.visited = false;
    }

    public int getIndex() {
        return index;
    }

    public double getDistance() {
        return distance;
    }

    public Node getPrevious() {
        return previous;
    }

    public Boolean getVisited() {
        return visited;
    }

    public void setDistance(double d) {
        distance = d;
    }

    public void setPrevious(Node p) {
        previous = p;
    }

    public void setVisited(Boolean v) {
        visited = v;
    }

}
