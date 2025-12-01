package cat.udl.eps.ed.heaps;



import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class HeapArrayQueueTest {
    @Test
    public void testCompareToDifferentPriorities() {
        var t1 = new HeapArrayQueue.Triplet<>(10, 1L, "Low");
        var t2 = new HeapArrayQueue.Triplet<>(20, 2L, "High");

        // t2 (20) > t1 (10) -> debe retornar positivo
        assertTrue(t2.compareTo(t1) > 0);
        assertTrue(t1.compareTo(t2) < 0);
    }

    @Test
    public void testCompareToEqualPrioritiesCheckFIFO() {
        // Misma prioridad, t1 llegó antes (timestamp 1) que t2 (timestamp 2)
        var t1 = new HeapArrayQueue.Triplet<>(10, 1L, "First");
        var t2 = new HeapArrayQueue.Triplet<>(10, 2L, "Second");

        // En un Max-Heap FIFO, el más antiguo (t1) tiene "más prioridad" para salir.
        // Por tanto t1.compareTo(t2) debe ser positivo (>0).
        assertTrue(t1.compareTo(t2) > 0, "El elemento más antiguo debe ser mayor en caso de empate");
        assertTrue(t2.compareTo(t1) < 0);
    }

    @Test
    public void testCompareToNulls() {
        var tNormal = new HeapArrayQueue.Triplet<>(10, 1L, "A");
        var tNull = new HeapArrayQueue.Triplet<>((Integer)null, 2L, "B");
        var tNull2 = new HeapArrayQueue.Triplet<>((Integer)null, 3L, "C");

        // Normal > Null
        assertTrue(tNormal.compareTo(tNull) > 0);
        // Null < Normal
        assertTrue(tNull.compareTo(tNormal) < 0);
        // Null == Null
        assertEquals(0, tNull.compareTo(tNull2));
    }

    @Test
    void removeAndElementThrowOnEmptyHeap() {
        // Tests that calling remove() or element() on empty heap throws exception

    }


    @Test
    public void removeReturnsMaxInOrder() {
        // Tests that remove() returns elements in correct max-heap order
        var heap = new HeapArrayQueue<Integer, String>();
        heap.add(10, "Low");
        heap.add(30, "High");
        heap.add(20, "Mid");

        assertEquals("High", heap.remove());
        assertEquals("Mid", heap.remove());
        assertEquals("Low", heap.remove());
    }

    @Test
    void elementReturnsRootWithoutRemoving() {
        // Tests that element() returns the root value without removing it
        var heap = new HeapArrayQueue<Integer, String>();
        heap.add(5, "A");
        heap.add(15, "B");

        assertEquals("B", heap.element()); // max element
        assertEquals(2, heap.size());     // size should not change
    }



}