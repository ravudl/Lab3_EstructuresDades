package cat.udl.eps.ed.heaps;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class HeapArrayQueueTest {
    @Test
    void testCompareToDifferentPriorities() {
        var t1 = new HeapArrayQueue.Triplet<>(10, 1L, "Low");
        var t2 = new HeapArrayQueue.Triplet<>(20, 2L, "High");

        // t2 (20) > t1 (10) -> debe retornar positivo
        assertTrue(t2.compareTo(t1) > 0);
        assertTrue(t1.compareTo(t2) < 0);
    }

    @Test
    void testCompareToEqualPrioritiesCheckFIFO() {
        // Misma prioridad, t1 llegó antes (timestamp 1) que t2 (timestamp 2)
        var t1 = new HeapArrayQueue.Triplet<>(10, 1L, "First");
        var t2 = new HeapArrayQueue.Triplet<>(10, 2L, "Second");

        // En un Max-Heap FIFO, el más antiguo (t1) tiene "más prioridad" para salir.
        // Por tanto t1.compareTo(t2) debe ser positivo (>0).
        assertTrue(t1.compareTo(t2) > 0, "El elemento más antiguo debe ser mayor en caso de empate");
        assertTrue(t2.compareTo(t1) < 0);
    }

    @Test
    void testCompareToNulls() {
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





}