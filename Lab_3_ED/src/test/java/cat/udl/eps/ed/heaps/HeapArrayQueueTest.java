package cat.udl.eps.ed.heaps;



import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;


class HeapArrayQueueTest {

    @Test
    void testOnEmptyHeap() {
        var heap = new HeapArrayQueue<Integer, String>();
        assertEquals(heap.size(), 0);
    }


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


    @Test
    void testOnElement() {

        // Comprovamos que obtenemos el valor de la raiz con element()
        var heap = new HeapArrayQueue<Integer, String>();
        heap.add(5, "A");
        heap.add(15, "B");

        assertEquals("B", heap.element()); // el elemento mas grande, la raiz
        assertEquals(2, heap.size());     // sigue mediendo lo mismo
    }

    @Test
    void testOnRemoveWithDifferentPriority() {

        var heap = new HeapArrayQueue<Integer, String>();

        heap.add(10, "Low");
        heap.add(30, "High");
        heap.add(20, "Mid");

        // Eliminamos primero el de mayor prioridad

        assertEquals("High", heap.remove());
        assertEquals(2, heap.size());

        assertEquals("Mid", heap.remove());
        assertEquals(1, heap.size());

        assertEquals("Low", heap.remove());
        assertEquals(0, heap.size());

    }

    @Test
    void testOnRemoveWithSamePriority() {

        var heap = new HeapArrayQueue<Integer, String>();


        heap.add(10, "First");
        heap.add(10, "Second");
        heap.add(10, "Third");

        //El primero en haber llegado se elimina primero, y asi hasta al final.
        assertEquals("First", heap.remove());
        assertEquals(2, heap.size());

        assertEquals("Second", heap.remove());
        assertEquals(1, heap.size());

        assertEquals("Third", heap.remove());
        assertEquals(0, heap.size());
    }

    @Test
    void testResize(){
        var heap = new HeapArrayQueue<Integer, String>();

        int n = 180;
        for(int i = 0; i < n; i++){
            heap.add(i, "Value" + i);
        }

        assertEquals(n, heap.size(), "La cua hauria de tindre " + n + " elements després del resize");

        // El elemento con la máxima prioridad es el último que se añadió en el bucle
        assertEquals("Value179", heap.element(), "Després de fer resize, l'arrel ha de ser correcte.");


    }


}




