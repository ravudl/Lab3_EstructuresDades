package cat.udl.eps.ed.heaps;

import java.util.Arrays;
import java.util.Objects;

public class PriorityQueue<P extends Comparable<? super P>, V> {
    void add(P priority, V value);
    V remove();
    V element();
    int size();
}
