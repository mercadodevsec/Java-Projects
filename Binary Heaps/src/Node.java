// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
public class Node {
    // added fields means delete code needs update
    private String name;
    private int occurrences;

    private Node left;
    private Node right;

    public Node(String name, int occurrences) {
        this.name = name;
        this.occurrences = occurrences;
    }

    /**
     * Get the name stored in the node.
     * This is the key that the BST is searching by.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name stored in the node.
     * This is the key that the BST is searching by.
     * <p>
     * Do not update this, unless you are sure the resulting BST is valid.
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the number of times a child was given the name associated with the node.
     * @return occurrences
     */
    public int getOccurrences() {
        return occurrences;
    }

    /**
     * Set the number of times a child was given the name associated with the node.
     * @param occurrences the new value
     */
    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }

    /**
     * Get the node to the left of the current node.
     * <p>
     * If the BST is valid, this node, and all of its
     * descendants should come before this alphabetically.
     * <p>
     * This can be null if there is not a left child.
     * @return the left node
     */
    public Node getLeft() {
        return left;
    }

    /**
     * Set the node to the left of the current node.
     * <p>
     * If the BST is valid, this node, and all of its
     * descendants should come before this alphabetically.
     * <p>
     * This can be null if there is not a left child.
     * @param left the new left child
     */
    public void setLeft(Node left) {
        this.left = left;
    }

    /**
     * Get the node to the right of the current node.
     * <p>
     * If the BST is valid, this node, and all of its
     * descendants should come before this alphabetically.
     * <p>
     * This can be null if there is not a right child.
     * @return the right node
     */
    public Node getRight() {
        return right;
    }

    /**
     * Set the node to the right of the current node.
     * <p>
     * If the BST is valid, this node, and all of its
     * descendants should come before this alphabetically.
     * <p>
     * This can be null if there is not a right child.
     * @param right the new right child
     */
    public void setRight(Node right) {
        this.right = right;
    }
}
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// PROVIDED FILE: DO NOT EDIT! ALL CHANGES WILL BE REVERTED!
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
