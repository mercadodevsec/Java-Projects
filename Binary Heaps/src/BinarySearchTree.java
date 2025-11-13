import java.util.*;

public class BinarySearchTree implements Iterable<Node> {
    Node root;

    public BinarySearchTree() {
        root = null;
    }

    /**
     * Updates the node with name to have a given number of occurrences.
     * <p>
     * Will create a new node if one does not exist
     * <p>
     * Does <b>NOT</b> create duplicate nodes
     *
     * @param name        the name to update
     * @param occurrences the number of occurrences of that name
     * @return the previous number of occurrences if replaced, or null if new node
     */
    public Integer put(String name, int occurrences) {
        if (root == null) {
            root = new Node(name, occurrences);
            return null;
        }

        Node current = root;
        Node parent = null;
        int cmp = 0;

        while (current != null) {
            parent = current;
            cmp = name.compareTo(current.getName());
            if (cmp == 0) {
                int old = current.getOccurrences();
                current.setOccurrences(occurrences);
                return old;
            } else if (cmp < 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }

        Node newNode = new Node(name, occurrences);
        if (cmp < 0) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }
        return null;
    }

    /**
     * Get the number of occurrences of a given name.
     *
     * @param name the name to lookup
     * @return the number of occurrences, or null if no such name exists
     */
    public Integer get(String name) {
        Node current = root;
        while (current != null) {
            int cmp = name.compareTo(current.getName());
            if (cmp == 0) return current.getOccurrences();
            if (cmp < 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        return null;
    }

    /**
     * Deletes the node associated with the given name
     *
     * @param name the name to delete
     * @return the number of occurrences of the deleted node, or null if no node was deleted
     */
    public Integer delete(String name) {
        Node current = root;
        Node parent = null;
        boolean isLeftChild = false;

        while (current != null && !current.getName().equals(name)) {
            parent = current;
            if (name.compareTo(current.getName()) < 0) {
                isLeftChild = true;
                current = current.getLeft();
            } else {
                isLeftChild = false;
                current = current.getRight();
            }
        }

        if (current == null) return null; // Not found

        int deletedOccurrences = current.getOccurrences();

        // Case 1: Node has one child
        if (current.getLeft() == null) { // right child only
            if (current == root) {
                root = current.getRight();
            } else if (isLeftChild) {
                parent.setLeft(current.getRight());
            } else {
                parent.setRight(current.getRight());
            }
        } else if (current.getRight() == null) { // left child only
            if (current == root) {
                root = current.getLeft();
            } else if (isLeftChild) {
                parent.setLeft(current.getLeft());
            } else {
                parent.setRight(current.getLeft());
            }
        }
        // Case 2: Node has two children
        else {
            Node successorParent = current;
            Node successor = current.getRight();
            while (successor.getLeft() != null) {
                successorParent = successor;
                successor = successor.getLeft();
            }

            current.setName(successor.getName());
            current.setOccurrences(successor.getOccurrences());

            // Remove successor
            if (successorParent.getLeft() == successor) {
                successorParent.setLeft(successor.getRight());
            } else {
                successorParent.setRight(successor.getRight());
            }
        }

        return deletedOccurrences;
    }

    /**
     * Should print out the tree with using inorder traversal and no relative formatting for each node,
     * just the name and number of occurrences. <p>
     * Example Output with maxDepth = 4:
     * <pre>
     * Aaliyah,2703
     * Abigail,5499
     * Adeline,3723
     * Amelia,12749
     * Ava,8662
     * Charlotte,12564
     * Eleanor,7137
     * Emma,13501
     * Evelyn,9122
     * Isabella,10777
     * Luna,7143
     * Mia,12131
     * Mila,5472
     * Nora,6128
     * Nova,5220
     * Olivia,14734
     * Penelope,5747
     * Scarlett,5900
     * Serenity,2342
     * Sofia,8099
     * Sol,522
     * Solana,409
     * Soleil,341
     * Sophia,12088
     * Stella,4264
     * Valentina,4438
     * Victoria,4276
     * Violet,6977
     * Willow,4683
     * Zoe,5744
     * Zoey,3600
     * </pre>
     *
     * @param maxDepth the maximum depth to print
     */
    public void printTreeInorder(int maxDepth) {
        printTreeInorder(root, -1, maxDepth);
    }

    private void printTreeInorder(Node node, int depth, int maxDepth) {
        if (node == null || depth >= maxDepth) return;
        printTreeInorder(node.getLeft(), depth + 1, maxDepth);
        System.out.println(node.getName() + "," + node.getOccurrences());
        printTreeInorder(node.getRight(), depth + 1, maxDepth);
    }

    /**
     * Should print out the tree with using preorder traversal and no relative formatting for each node,
     * just the name and number of occurrences. <p>
     * Example Output with maxDepth = 4:
     * <pre>
     * Olivia,14734
     * Emma,13501
     * Amelia,12749
     * Abigail,5499
     * Aaliyah,2703
     * Adeline,3723
     * Charlotte,12564
     * Ava,8662
     * Eleanor,7137
     * Mia,12131
     * Isabella,10777
     * Evelyn,9122
     * Luna,7143
     * Nora,6128
     * Mila,5472
     * Nova,5220
     * Sophia,12088
     * Sofia,8099
     * Scarlett,5900
     * Penelope,5747
     * Serenity,2342
     * Solana,409
     * Sol,522
     * Soleil,341
     * Violet,6977
     * Valentina,4438
     * Stella,4264
     * Victoria,4276
     * Zoe,5744
     * Willow,4683
     * Zoey,3600
     * </pre>
     *
     * @param maxDepth the maximum depth to print
     */
    public void printTreePreorder(int maxDepth) {
        printTreePreorder(root, -1, maxDepth);
    }

    private void printTreePreorder(Node node, int depth, int maxDepth) {
        if (node == null || depth >= maxDepth) return;
        System.out.println(node.getName() + "," + node.getOccurrences());
        printTreePreorder(node.getLeft(), depth + 1, maxDepth);
        printTreePreorder(node.getRight(), depth + 1, maxDepth);
    }

    /**
     * Extra Credit <p>
     * Should print out the tree with using inorder traversal and nice formatting to clearly
     * show the structure of the tree and who is a left or right child. <p>
     * Example Output with maxDepth = 4:
     * <pre>
     *             ,---Aaliyah
     *         ,---Abigail
     *         |   `---Adeline
     *     ,---Amelia
     *     |   |   ,---Ava
     *     |   `---Charlotte
     *     |       `---Eleanor
     * ,---Emma
     * |   |       ,---Evelyn
     * |   |   ,---Isabella
     * |   |   |   `---Luna
     * |   `---Mia
     * |       |   ,---Mila
     * |       `---Nora
     * |           `---Nova
     * Olivia
     * |           ,---Penelope
     * |       ,---Scarlett
     * |       |   `---Serenity
     * |   ,---Sofia
     * |   |   |   ,---Sol
     * |   |   `---Solana
     * |   |       `---Soleil
     * `---Sophia
     *     |       ,---Stella
     *     |   ,---Valentina
     *     |   |   `---Victoria
     *     `---Violet
     *         |   ,---Willow
     *         `---Zoe
     *             `---Zoey
     * </pre>
     *
     * @param maxDepth the maximum depth to print
     */
    public void prettyPrintTreeInorder(int maxDepth) {
        prettyPrintInorderHelper(root, -1, maxDepth, "", false);
    }

    /**
     * Recursive helper method for pretty printing the tree (inorder layout).
     * Prints the right subtree first so that the root appears centered, then left.
     *
     * @param node     the current node
     * @param depth    current recursion depth
     * @param maxDepth maximum depth to print
     * @param prefix   prefix string for formatting branches
     * @param isRight  true if node is a left child (determines branch symbols)
     */
    private void prettyPrintInorderHelper(Node node, int depth, int maxDepth, String prefix, boolean isRight) {
        if (node == null || depth >= maxDepth) return;
        if (depth == -1) {
            prettyPrintInorderHelper(node.getLeft(), 0, maxDepth, prefix, false);
        } else {
            // Print left subtree first (appears above current node)
            prettyPrintInorderHelper(node.getLeft(), depth + 1, maxDepth, prefix + (isRight ? "|   " : "    "), false);
        }

        // Print current node with appropriate formatting
        if (depth == -1) {
            System.out.println(node.getName());
        } else if (isRight) {
            System.out.println(prefix + "`---" + node.getName());
        } else {
            System.out.println(prefix + ",---" + node.getName());
        }

        if (depth == -1) {
            prettyPrintInorderHelper(node.getRight(), 0, maxDepth, prefix, true);
        } else {
            prettyPrintInorderHelper(node.getRight(), depth + 1, maxDepth, prefix + (isRight ? "    " : "|   "), true);
        }
    }

    /**
     * Extra Credit <p>
     * Should print out the tree with using preorder traversal and nice formatting to clearly
     * show the structure of the tree and who is a left or right child. <p>
     * Example Output with maxDepth = 4:
     * <pre>
     * Olivia
     * +---Emma
     * |   +---Amelia
     * |   |   +---Abigail
     * |   |   |   +---Aaliyah
     * |   |   |   `---Adeline
     * |   |   `---Charlotte
     * |   |       +---Ava
     * |   |       `---Eleanor
     * |   `---Mia
     * |       +---Isabella
     * |       |   +---Evelyn
     * |       |   `---Luna
     * |       `---Nora
     * |           +---Mila
     * |           `---Nova
     * `---Sophia
     *     +---Sofia
     *     |   +---Scarlett
     *     |   |   +---Penelope
     *     |   |   `---Serenity
     *     |   `---Solana
     *     |       +---Sol
     *     |       `---Soleil
     *     `---Violet
     *         +---Valentina
     *         |   +---Stella
     *         |   `---Victoria
     *         `---Zoe
     *             +---Willow
     *             `---Zoey
     * </pre>
     *
     * @param maxDepth the maximum depth to print.
     */
    public void prettyPrintTreePreorder(int maxDepth) {
        prettyPrintTreePreorderHelper(root, -1, maxDepth, "", false);
    }

    private void prettyPrintTreePreorderHelper(Node node, int depth, int maxDepth, String prefix, boolean isRight) {
        if (node == null || depth >= maxDepth) return;
        if (depth == -1) {
            System.out.println(node.getName() + prefix);
        } else if (!isRight) {
            System.out.println(prefix + "+---" + node.getName());
        } else {
            System.out.println(prefix + "`---" + node.getName());
        }
        if (depth == -1) {
            prettyPrintTreePreorderHelper(node.getLeft(), 0, maxDepth, prefix, false);
        } else {
            prettyPrintTreePreorderHelper(node.getLeft(),
                    depth + 1, maxDepth, prefix + (isRight ? "    " : "|   "), false);
        }

        if (depth == -1) {
            prettyPrintTreePreorderHelper(node.getRight(), 0, maxDepth, prefix, true);
        } else {
            prettyPrintTreePreorderHelper(node.getRight(),
                    depth + 1, maxDepth, prefix + (isRight ? "    " : "|   "), true);
        }
    }

    /**
     * Return an instance of a class to create
     * an iterator over the Binary Search Tree.
     * <p>
     * This iterator only needs to implement the hasNext and next methods
     * for java to be happy.
     * <p>
     * You should be iterating over the elements in the BST in an <b>In-Order</b> traversal.
     * This means that the nodes should be returned in alphabetical order by name.
     * <p>
     * Hint: You should create a class, just like project 1.
     * <p>
     * Hint: First try to work out how to transform inOrder traversal from a recursive process to an iterative one.
     * This will make it easier to make it return only one Node at a time.
     * If this is too difficult at first,
     * an easier problem to attempt before coming back is to try to make pre-order traversal into an iterative algorithm.
     *
     * @return an object which can iterate over all nodes of the BST.
     */
    @Override
    public Iterator<Node> iterator() {
        return new BSTIterator(root);
    }

    // In-order iterator
    private class BSTIterator implements Iterator<Node> {
        private Stack<Node> stack = new Stack<>();

        public BSTIterator(Node root) {
            pushLeft(root);
        }

        private void pushLeft(Node node) {
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public Node next() {
            Node node = stack.pop();
            pushLeft(node.getRight());
            return node;
        }
    }
}
