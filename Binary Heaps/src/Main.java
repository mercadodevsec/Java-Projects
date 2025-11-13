import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

// Data originally from
// https://www.ssa.gov/OACT/babynames/limits.html
public class Main {
    static Scanner stdin = new Scanner(System.in);

    public static void main(String[] args) {
        Scanner s = null;
        try {
            s = new Scanner(new File("names-2024.txt"));
        } catch (FileNotFoundException e) {
            System.err.println("Could not find file!");
            System.exit(1);
        }

        BinarySearchTree bst = new BinarySearchTree();

        while (s.hasNextLine()) {
            String line = s.nextLine().trim();
            if (!line.isEmpty()) {
                String[] input = line.split(",");
                bst.put(input[0], Integer.parseInt(input[1]));
            }
        }
        int choice;
        int depth;
        char letter;
        int numPrint;
        int countNodes;
        do {
            printMenuOptions();
            System.out.print("Enter choice: ");
            while(!stdin.hasNextInt()) {
                System.out.println("Please enter an integer!\n");
                printMenuOptions();
                System.out.print("Enter choice: ");
                stdin.nextLine();
            }
            choice = stdin.nextInt();
            stdin.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Max depth to print: ");
                    depth = stdin.nextInt();
                    stdin.nextLine();
                    bst.printTreeInorder(depth);
                    break;
                case 2:
                    System.out.print("Max depth to pretty print (Extra Credit): ");
                    depth = stdin.nextInt();
                    stdin.nextLine();
                    bst.prettyPrintTreeInorder(depth);
                    break;
                case 3:
                    System.out.print("Max depth to print: ");
                    depth = stdin.nextInt();
                    stdin.nextLine();
                    bst.printTreePreorder(depth);
                    break;
                case 4:
                    System.out.print("Max depth to pretty print (Extra Credit): ");
                    depth = stdin.nextInt();
                    stdin.nextLine();
                    bst.prettyPrintTreePreorder(depth);
                    break;
                case 5:
                    System.out.print("Enter name to look up: ");
                    String lookup = stdin.nextLine().toLowerCase();
                    letter = lookup.charAt(0);
                    lookup = lookup.substring(1);
                    lookup = Character.toString(letter).toUpperCase() + lookup;
                    Integer occurrences = bst.get(lookup);
                    if (occurrences != null) {
                        System.out.println(lookup + " occurs " + occurrences + " times.");
                    } else {
                        System.out.println(lookup + " not found.");
                    }
                    break;
                case 6:
                    System.out.print("Enter name to delete: ");
                    String delName = stdin.nextLine();
                    letter = delName.charAt(0);
                    delName = delName.substring(1);
                    delName = Character.toString(letter).toUpperCase() + delName;
                    Integer deleted = bst.delete(delName);
                    if (deleted != null) {
                        System.out.println(delName + " deleted with occurrences " + deleted);
                    } else {
                        System.out.println(delName + " not found.");
                    }
                    break;
                case 7:
                    System.out.print("Enter name to set: ");
                    String setName = stdin.nextLine();
                    letter = setName.charAt(0);
                    setName = setName.substring(1);
                    setName = Character.toString(letter).toUpperCase() + setName;
                    System.out.print("Enter occurrences: ");
                    int setOcc = stdin.nextInt();
                    stdin.nextLine();
                    Integer prev = bst.put(setName, setOcc);
                    if (prev != null) {
                        System.out.println(setName + " updated from " + prev + " to " + setOcc);
                    } else {
                        System.out.println(setName + " added with occurrences " + setOcc);
                    }
                    break;
                case 8:
                    countNodes = 0;
                    for (Node node : bst) {
                        countNodes++;
                    }
                    System.out.println("Total nodes: " + countNodes);
                    break;
                case 9:
                    System.out.print("Enter number of names to print from start: ");
                    numPrint = stdin.nextInt();
                    stdin.nextLine();
                    Iterator<Node> iter1 = bst.iterator();
                    for (int i = 0; i < numPrint && iter1.hasNext(); i++) {
                        Node node = iter1.next();
                        System.out.println(node.getName() + "," + node.getOccurrences());
                    }
                    break;
                case 10:
                    System.out.print("Enter number of names to print from end: ");
                    numPrint = stdin.nextInt();
                    stdin.nextLine();
                    countNodes = 0;
                    for (Node node : bst) {
                        countNodes++;
                    }
                    int startIndex = countNodes - numPrint;
                    Stack<Node> stackReverse =  new Stack<>();
                    int counter = 0;
                    for (Node node : bst) {
                        if(counter >= startIndex) {
                            stackReverse.push(node);
                        }
                        counter++;
                    }
                    int size = stackReverse.size();
                   for(int i = 0; i < size; i++){
                       Node node = stackReverse.pop();
                       System.out.println(node.getName() + "," + node.getOccurrences());
                   }
                    break;
                case 11:
                    System.out.println("Exiting program.");
                    break;
                default:
                    System.out.println("Please enter a valid choice!");
            }
            System.out.println();
        } while (choice != 11);


        s.close();
    }

    /**
     * Print out all required options for the project.
     * You do not need to implement the options marked as extra credit.
     */
    public static void printMenuOptions() {
        // Should take O(n) time and use space only for recursion
        System.out.println("1. Print tree inorder");
        // Should take O(n) time and use O(h) space (can also use recursion)
        System.out.println("2. (Extra Credit) Pretty Print tree inorder");
        // Should take O(n) time and use space only for recursion
        System.out.println("3. Print tree preorder");
        // Should take O(n) time and use O(h) space (can also use recursion)
        System.out.println("4. (Extra Credit) Pretty Print tree preorder");
        // Should take O(h) time and O(1) space (try to avoid recursion)
        System.out.println("5. Print number of occurrences for a given name");
        // Should take O(h) time and O(1) space (try to avoid recursion)
        System.out.println("6. Delete record of a given name");
        // Should take O(h) time and O(1) space (try to avoid recursion)
        System.out.println("7. Set record for a given name");
        // Should take O(n) time and O(h) space
        // (all space complexity is in iterator, this should just be a simple loop)
        System.out.println("8. Print the number of nodes using inorder iterator");
        // Should take O(k) time and O(h) space (k = number of names specified)
        // (all space complexity is in iterator, this should just be a simple loop)
        System.out.println("9. Print names at beginning of alphabet using inorder iterator");
        // Should take O(n) time and O(h) space
        // (all space complexity is in iterator, this should just be a simple loop or so)
        System.out.println("10. Print names at end of alphabet using inorder iterator");
        // Self-Explanatory
        System.out.println("11. Exit");
    }
}