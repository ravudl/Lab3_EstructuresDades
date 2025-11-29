package cat.udl.eps.ed.heaps;

import java.util.Arrays;

public class HeapArrayQueue<P extends Comparable<? super P>, V> implements PriorityQueue<P, V> {
    private static final int INITIAL_QUEUE_CAPACITY = 1;

    //El array de triplets
    private Triplet<P, V>[] triplets;

    //Numero d'elements reals en el heap (sense contar root)
    private  int size = 0;

    // Contador per desemptar
    private long nextTimeStamp = 0L;

    @SuppressWarnings("unchecked")
    public HeapArrayQueue() {
        // Creamos un array de tamaño INITIAL_QUEUE_CAPACITY + 1
        // para respetar que el índice 0 no se usa.
        triplets = (Triplet<P, V>[]) new Triplet[INITIAL_QUEUE_CAPACITY + 1];
    }

    /**
     * Record interno Triplet.
     * Visibilidad de paquete para poder testearlo desde TripletTest.
     */
    record Triplet<P extends Comparable<? super P>, V>(P priority, long timeStamp, V value)
            implements Comparable<Triplet<P, V>> {

        @Override
        public int compareTo(Triplet<P, V> other) {
            // 1. Comparar prioridades manejando nulls
            if (this.priority == null && other.priority == null) return 0;
            if (this.priority == null) return -1; // null es la prioridad más baja
            if (other.priority == null) return 1;

            int cmp = this.priority.compareTo(other.priority);

            if (cmp != 0) {
                return cmp;
            }

            // 2. Si prioridades son iguales, desempate por timestamp (FIFO).
            // En un MAX-Heap, queremos que el que llegó ANTES (menor timestamp)
            // se considere "mayor" para que suba o se mantenga arriba.
            // Por tanto, comparamos al revés: other vs this.
            return Long.compare(other.timeStamp, this.timeStamp);
        }
    }
    
    // MÉTODOS PÚBLICOS DE LA INTERFAZ (Solo add y size te tocan a ti)
    @Override
    public void add(P priority, V value) {
        // 1. Verificar capacidad y redimensionar si es necesario
        if (size + 1 >= triplets.length) {
            resize();
        }

        // 2. Crear el nuevo triplete
        Triplet<P, V> newTriplet = new Triplet<>(priority, nextTimeStamp++, value);

        // 3. Insertar al final del heap (siguiente posición disponible)
        size++;
        triplets[size] = newTriplet;

        // 4. Restaurar la propiedad de Max-Heap (burbujeo hacia arriba)
        siftUp(size);
    }

    @Override
    public int size() {
        return size;
    }

    // (Los métodos remove() y element() los implementará tu compañero)
    @Override
    public V remove() { throw new UnsupportedOperationException("To be implemented by Persona 2"); }
    @Override
    public V element() { throw new UnsupportedOperationException("To be implemented by Persona 2"); }

    // MÉTODOS PRIVADOS / AUXILIARES=
    /**
     * Dobla el tamaño del array copiando los elementos existentes.
     */
    private void resize() {
        // System.arraycopy o Arrays.copyOf pueden usarse.
        triplets = Arrays.copyOf(triplets, triplets.length * 2);
    }

    /**
     * Reordena el heap subiendo el elemento en 'k' hasta que cumpla la propiedad de heap.
     */
    private void siftUp(int k) {
        // Mientras no sea la raíz (índice 1) y sea mayor que su padre
        while (k > 1) {
            int pIndex = parentIndex(k);
            Triplet<P, V> current = triplets[k];
            Triplet<P, V> parent = triplets[pIndex];

            // Si el hijo es mayor que el padre, intercambiamos
            if (current.compareTo(parent) > 0) {
                swap(k, pIndex);
                k = pIndex; // Subimos el índice para la siguiente iteración
            } else {
                break; // El orden es correcto, terminamos
            }
        }
    }

    private void swap(int i, int j) {
        Triplet<P, V> temp = triplets[i];
        triplets[i] = triplets[j];
        triplets[j] = temp;
    }

    // MÉTODOS DE ÍNDICES (Visibilidad de paquete para testing)
    static int parentIndex(int i) {
        return i / 2;
    }

    static int leftIndex(int i) {
        return 2 * i;
    }

    static int rightIndex(int i) {
        return 2 * i + 1;
    }

    boolean exists(int index) {
        return index >= 1 && index <= size;
    }

}
