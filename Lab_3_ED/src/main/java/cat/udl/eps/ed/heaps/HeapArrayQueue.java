package cat.udl.eps.ed.heaps;

import java.util.Arrays;
import java.util.NoSuchElementException;


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


    record Triplet<P extends Comparable<? super P>, V>(P priority, long timeStamp, V value)
            implements Comparable<Triplet<P, V>> {

        @Override
        public int compareTo(Triplet<P, V> other) {
            // Comparar prioritats
            if (this.priority == null && other.priority == null) return 0;
            if (this.priority == null) return -1; // null es la prioridad más baja
            if (other.priority == null) return 1;
            int cmp = this.priority.compareTo(other.priority);

            if (cmp != 0) {
                return cmp;
            }

            //Desempat per temps d'espera
            return Long.compare(other.timeStamp, this.timeStamp);
        }
    }
    
    // Mètodes de l'interfaz
    @Override
    public void add(P priority, V value) {
        //Verificació de capacitat i redimenció si cal.
        if (size + 1 >= triplets.length) {
            resize();
        }

        // Crear el nou triplet
        Triplet<P, V> newTriplet = new Triplet<>(priority, nextTimeStamp++, value);

        //Insertar el final de heap.
        size++;
        triplets[size] = newTriplet;

        //Max-Heap
        siftUp(size);
    }

    @Override
    public int size() {
        return size;
    }


    @Override
    public V remove() {

        if (size == 0) {
            throw new NoSuchElementException();
        }
        V rootValue = element();

        //Cambiamos el primero por el ultimo, i eliminamos el ultimo.
        triplets[1] = triplets[size];
            triplets[size] = null;
            size--;

        //Se vuelve a poner orden seguiendo la normativa de Heap
        if (size > 0) {
            orderDown(1);
        }

            return rootValue;

    }


    @Override
    public V element() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }
        return triplets[1].value();
    }

    private void orderDown(int k) {

        while (true) {
            int left = leftIndex(k);
            int right = rightIndex(k);
            int largest = k;

            // Comparamos que el lado ezquierdo no sea mayor, en caso de serlo intercambiamos.
            if (exists(left) && triplets[left].compareTo(triplets[largest]) > 0) {
                largest = left;
            }
            // Repetimos lo mismo para la derecha.
            if (exists(right) && triplets[right].compareTo(triplets[largest]) > 0) {
                largest = right;
            }
            //el intercambio
            if (largest != k) {
                swap(k, largest);
                k = largest;
            } else {
                break;
            }
        }
    }


    //Mètodes Auxiliars
    //Dobla la dimensió de l'abre
    private void resize() {
        // System.arraycopy o Arrays.copyOf pueden usarse.
        triplets = Arrays.copyOf(triplets, triplets.length * 2);
    }

    private void siftUp(int k) {
        // Busca de arrel (root) o major que l'arrel
        while (k > 1) {
            int pIndex = parentIndex(k);
            Triplet<P, V> current = triplets[k];
            Triplet<P, V> parent = triplets[pIndex];

            // Fill major que pare
            if (current.compareTo(parent) > 0) {
                swap(k, pIndex);
                k = pIndex; //Pujem el valor en l'abre
            } else {
                break; // En cas correcte surtim
            }
        }
    }

    private void swap(int i, int j) {
        Triplet<P, V> temp = triplets[i];
        triplets[i] = triplets[j];
        triplets[j] = temp;
    }

    // Mètodes d'index (per a test)
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
