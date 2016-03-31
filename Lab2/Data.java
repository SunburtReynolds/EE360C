/* 
 * Jonathon Reynolds
 * jar6493
 *
 */

public class Data {
    private int next;
    private double latency;
    private int weight;

    public Data(int next, double latency) {
        this(next, latency, 0);
    }

    public Data(int next, double latency, int weight) {
        this.next = next;
        this.latency = latency;
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }

    public int getNext() {
        return this.next;
    } 

    public double getLatency() {
        return this.latency;
    }
}