import java.util.Iterator;

/**
 * A simple implementation of a resizable array-based list.
 * Implements the SimpleList interface and provides basic operations like
 * add, remove, set, get, and iteration over elements.
 *
 * @param <T> the type of elements stored in the list
 */
public class ArrayList<T> implements SimpleList<T> {

    private Object[] data;            // Internal array to hold list elements
    private int size;                 // Current number of elements in the list
    private final int DEFAULT_CAPACITY = 10; // Default initial capacity if none specified

    /**
     * Constructs an ArrayList with a specified initial capacity.
     *
     * @param initialCapacity the initial size of the internal array
     */
    public ArrayList(int initialCapacity) {
        data = new Object[initialCapacity]; // Allocate internal array with given capacity
        size = 0;                           // Initialize list as empty
    }

    /**
     * Constructs an ArrayList containing all elements from the given array.
     * Copies each element from the input array into the internal array.
     *
     * @param data the array whose elements are to be placed into this list
     */
    public ArrayList(T[] data) {
        this.data = new Object[data.length]; // Allocate internal array of same length
        for (int i = 0; i < data.length; i++) {
            this.data[i] = data[i];         // Copy each element manually
        }
        this.size = data.length;            // Set size to match input array
    }

    /**
     * Constructs an empty ArrayList with the default initial capacity.
     */
    public ArrayList() {
        data = new Object[DEFAULT_CAPACITY]; // Allocate internal array with default capacity
        size = 0;                            // Initialize list as empty
    }

    /**
     * Returns the current capacity of the internal array.
     *
     * @return the capacity of the internal array
     */
    protected int capacity() {
        return data.length; // Return length of internal array
    }

    /**
     * Ensures that the internal array has at least the specified minimum capacity.
     * If the current array is smaller, a new larger array is created and existing elements are copied.
     *
     * @param minCapacity the desired minimum capacity
     */
    @Override
    public void ensureCapacity(int minCapacity) {
        if (data.length < minCapacity) {
            Object[] newData = new Object[minCapacity]; // Allocate new array with requested capacity
            for (int i = 0; i < size; i++) {
                newData[i] = data[i]; // Copy existing elements
            }
            data = newData; // Replace internal array
        }
    }

    /**
     * Returns the number of elements currently stored in the list.
     *
     * @return the size of the list
     */
    @Override
    public int size() {
        return size; // Return current number of elements
    }

    /**
     * Inserts the specified element at the specified position in the list.
     * Shifts subsequent elements to the right to make space.
     *
     * @param index   position at which to insert the element
     * @param element element to insert
     * @throws IndexOutOfBoundsException if index < 0 or index > size
     */
    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (size >= capacity()) {
            ensureCapacity(Math.max(DEFAULT_CAPACITY, size * 2)); // Double capacity if full
        }
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1]; // Shift elements right
        }
        data[index] = element;
        size++;
    }

    /**
     * Appends the specified element to the end of the list.
     *
     * @param t element to be added
     * @return true
     */
    @Override
    public boolean add(T t) {
        if (size >= capacity()) {
            ensureCapacity(Math.max(DEFAULT_CAPACITY, size * 2)); // Double capacity if full
        }
        data[size++] = t; // Add element at the end and increment size
        return true;
    }

    /**
     * Removes all elements from the list.
     * Resets the internal array to the default capacity.
     */
    @Override
    public void clear() {
        data = new Object[DEFAULT_CAPACITY]; // Allocate new array with default capacity
        size = 0;
    }

    /**
     * Returns the element at the specified position in the list.
     *
     * @param index index of the element to return
     * @return element at the specified index
     * @throws IndexOutOfBoundsException if index < 0 or index >= size
     */
    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        } else {
            return (T) data[index]; // Return element at index
        }
    }

    /**
     * Replaces the element at the specified position with the specified element.
     *
     * @param index   index of the element to replace
     * @param element element to set
     * @return the previous element at the specified position
     * @throws IndexOutOfBoundsException if index < 0 or index >= size
     */
    @Override
    @SuppressWarnings("unchecked")
    public T set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        } else {
            T old = (T) data[index];
            data[index] = element;
            return old;
        }
    }

    /**
     * Removes the element at the specified position.
     * Shifts subsequent elements to the left to fill the gap.
     *
     * @param index index of the element to remove
     * @return the removed element
     * @throws IndexOutOfBoundsException if index < 0 or index >= size
     */
    @Override
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        } else {
            T old = (T) data[index];
            for (int i = index; i < size - 1; i++) {
                data[i] = data[i + 1]; // Shift elements left
            }
            size--;
            return old;
        }
    }

    /**
     * Returns the index of the first occurrence of the specified element,
     * or -1 if not found.
     *
     * @param o element to search for
     * @return index of first occurrence or -1 if not found
     */
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(o)) {
                return i; // Return index of first match
            }
        }
        return -1; // Element not found
    }

    /**
     * Returns an iterator over the elements in this list.
     *
     * @return an Iterator
     */
    @Override
    public Iterator<T> iterator() {
        return new MyIterator(); // Return new iterator instance
    }

    /**
     * Inner class implementing an iterator over the ArrayList.
     */
    @SuppressWarnings("unchecked")
    class MyIterator implements Iterator<T> {
        private int index = 0; // Current index for iteration

        @Override
        public boolean hasNext() {
            return index < size; // Check if more elements remain
        }

        @Override
        public T next() {
            return (T) data[index++]; // Return current element and increment index
        }
    }

    /**
     * Returns an array containing all elements in proper sequence.
     * Creates a new array so the internal array is not exposed.
     *
     * @return array containing all elements
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size]; // Create new array of current size
        for (int i = 0; i < size; i++) {
            array[i] = data[i]; // Copy elements into new array
        }
        return array;
    }
}
