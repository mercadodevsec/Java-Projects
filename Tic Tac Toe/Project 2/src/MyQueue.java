/**
 * MyQueue is a generic circular queue implementation using an array.
 * It supports adding elements to the front, removing elements from both
 * the front and the back, and peeking at the most recently added element.
 *
 * @param <T> the type of elements stored in the queue
 */
public class MyQueue<T> {
    // Array to store queue elements
    private final Object[] data;

    // Index where the next element will be added (front)
    private int head = 0;

    // Index of the oldest element (back)
    private int tail = 0;

    // Number of elements currently in the queue
    private int size = 0;

    /**
     * Constructs a MyQueue with a specified capacity.
     *
     * @param capacity maximum number of elements the queue can hold
     */
    public MyQueue(int capacity) {
        data = new Object[capacity];
    }

    /**
     * Adds an element to the front of the queue.
     *
     * @param value element to add
     * @throws RuntimeException if the queue is full
     */
    public void offer(T value) {
        if (size == data.length) throw new RuntimeException("Queue full");
        data[head] = value;
        head = (head + 1) % data.length; // wrap around using modulo
        size++;
    }

    /**
     * Removes the element at the back (oldest element) of the queue.
     * Does nothing if the queue is empty.
     */
    @SuppressWarnings("unchecked")
    public void removeLast() {
        if (size == 0) return;
        data[tail] = null;
        tail = (tail + 1) % data.length; // wrap around using modulo
        size--;
    }

    /**
     * Removes and returns the element at the front (most recently added).
     *
     * @return the removed element, or null if the queue is empty
     */
    @SuppressWarnings("unchecked")
    public T removeFirst() {
        if (size == 0) return null;
        head = (head - 1 + data.length) % data.length; // move backward circularly
        T value = (T) data[head];
        data[head] = null;
        size--;
        return value;
    }

    /**
     * Returns the element at the front (most recently added) without removing it.
     *
     * @return the element at the front, or null if the queue is empty
     */
    @SuppressWarnings("unchecked")
    public T peekFirst() {
        if (size == 0) return null;
        int firstIndex = (head - 1 + data.length) % data.length;
        return (T) data[firstIndex];
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue has no elements, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements currently in the queue.
     *
     * @return the current size of the queue
     */
    public int size() {
        return size;
    }
}
