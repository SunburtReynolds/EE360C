/* 
 * Jonathon Reynolds
 * jar6493
 *
 */

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node n, Node m) {
        if (n.getDistance() < m.getDistance()) {
            return -1;
        }
        else if (n.getDistance() > m.getDistance()) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
