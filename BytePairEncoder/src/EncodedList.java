import java.util.Iterator;

class EncodedList extends ArrayList<EncodingValue> {
    private TokenMap map;

    public EncodedList(TokenMap map) {
        super();
        this.map = map;
    }

    /**
     * Increases the capacity of this {@code SimpleList}, if
     * necessary, to ensure that it can hold at least the number of elements
     * specified by the minimum capacity argument.
     *
     * @param minCapacity the desired minimum capacity
     */
    @Override
    public void ensureCapacity(int minCapacity) {
        super.ensureCapacity(minCapacity);
    }

    /**
     * Returns the number of elements in this list.  If this list contains
     * more than {@code Integer.MAX_VALUE} elements, returns
     * {@code Integer.MAX_VALUE}.
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return super.size();
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an iterator over the elements in this list in proper sequence
     */
    @Override
    public Iterator<EncodingValue> iterator() {
        return super.iterator();
    }

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence (from first to last element).
     *
     * @return an array containing all the elements in this list in proper
     * sequence
     */
    @Override
    public Object[] toArray() {
        return super.toArray();
    }

    /**
     * Appends the specified element to the end of this list and maintains encoding.
     *
     * @param element element to be appended to this list
     * @return {@code true} (as specified by {@link java.util.Collection#add})
     */
    @Override
    public boolean add(EncodingValue element) {
        super.add(element);
        encodeFully();
        return true;
    }

    /**
     * Removes all elements from this list (optional operation).
     * The list will be empty after this call returns.
     */
    @Override
    public void clear() {
        super.clear();
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     */
    @Override
    public EncodingValue get(int index) {
        return super.get(index);
    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified element, maintaining encoding.
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     */
    @Override
    public EncodingValue set(int index, EncodingValue element) {
        EncodingValue old = super.set(index, element);
        encodeFully();
        return old;
    }

    /**
     * Inserts the specified element at the specified position in this list
     * and maintains encoding.
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     */
    @Override
    public void add(int index, EncodingValue element) {
        super.add(index, element);
        encodeFully();
    }

    /**
     * Adds an array of raw bytes as ByteValues and maintains encoding.
     *
     * @param rawData array of bytes to add
     */
    public void addBytes(byte[] rawData) {
        for (byte b : rawData) {
            super.add(new ByteValue(b));
        }
        encodeFully();
    }

    /**
     * Removes the element at the specified position in this list and maintains encoding.
     *
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     */
    @Override
    public EncodingValue remove(int index) {
        EncodingValue removed = super.remove(index);
        encodeFully();
        return removed;
    }

    /**
     * Returns the index of the first occurrence of the specified element in this list.
     *
     * @param o element to search for
     * @return the index of the first occurrence of the specified element
     */
    @Override
    public int indexOf(Object o) {
        return super.indexOf(o);
    }

    /**
     * Fully encodes the entire list using the provided TokenMap.
     * Compresses adjacent pairs into TokenValues according to the rules.
     */
    private void encodeFully() {
        boolean changed;
        do {
            changed = false;
            int bestValue = Integer.MAX_VALUE;
            int bestIndex = -1;
            TokenValue bestToken = null;

            // Right-to-left scan to find best pair to encode
            for (int i = size() - 2; i >= 0; i--) {
                TokenValue token = map.getToken(get(i), get(i + 1));
                if (token != null) {
                    if (token.value() < bestValue) {
                        bestValue = token.value();
                        bestIndex = i;
                        bestToken = token;
                    }
                }
            }

            if (bestToken != null) {
                super.set(bestIndex, bestToken);
                super.remove(bestIndex + 1);
                changed = true;
            }

        } while (changed);
    }
}
