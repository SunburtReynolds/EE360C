/* 
 * Jonathon Reynolds
 * jar6493
 *
 */

public class Data {
    // private int next;
    // private double latency;
    private double weight;
    private Node node;

    public Data(Node node, double weight) {
        // this(next, latency, 0);
        this.node = node;
        this.weight = weight;
    }

    // public Data(int next, double latency, int weight) {
    //     this.next = next;
    //     this.latency = latency;
    //     this.weight = weight;
    //     // this.explored = false;
    // }

    public double getWeight() {
        return this.weight;
    }

    public Node getNode() {
        return this.node;
    } 

    // public double getLatency() {
    //     return this.latency;
    // }

    // public Boolean isExplored() {
    //     return this.explored;
    // }

    // public void explore() {
    //     this.explored = true;
    // }
}
