public class Main {
    public static void main(String[] args) throws Exception {
        // Load the token map from the specified file.
        // The TokenMap defines how byte/character pairs are compressed into tokens.
        String tokenFile = "BytePairEncoder/token-files/poems.bpet";
        TokenMap map = FileHelper.loadTokens(tokenFile);

        // Create the first token (token ID 0) and expand it back to its string form for debugging.
        TokenValue firstToken = new TokenValue((short) 0);
        System.out.printf("'%s'%n", firstToken.expandAsString(map));

        // Create a new EncodedList that will hold compressed data using the TokenMap.
        EncodedList myCompressedList = new EncodedList(map);

        // Load the source text file and append it to the EncodedList.
        // This step automatically compresses the raw file contents using BPE tokens.
        String sourceFile = "BytePairEncoder/input-files/poems/A Jelly-Fish -- Marianne Moore.txt";
        FileHelper.appendFile(myCompressedList, sourceFile);

        // Store the compressed EncodedList to disk in a binary compressed format.
        FileHelper.storeEncodedList(myCompressedList, "BytePairEncoder/output-file.bprl");

        // Load an already-compressed file into a new EncodedList for comparison.
        String providedOutput = "BytePairEncoder/output-files/poem-tkn/A Jelly-Fish -- Marianne Moore.txt.bprl";
        EncodedList newList = new EncodedList(map);
        FileHelper.appendEncodedList(newList, providedOutput);

        // Compare the two lists for equality and print the result.
        if (encodedListsEqualityCheck(myCompressedList, newList)) {
            System.out.println("The lists are identical!");
        } else {
            System.out.println("The lists differ.");
        }
    }

    /**
     * Compares two EncodedLists for equality.
     * <p>
     * Two EncodedLists are considered equal if:
     * - They have the same size.
     * - All corresponding EncodingValues are equal at each index.
     *
     * @param a the first EncodedList (usually the freshly compressed one)
     * @param b the second EncodedList (usually the one loaded from disk)
     * @return true if the lists are identical, false otherwise
     */
    public static boolean encodedListsEqualityCheck(EncodedList a, EncodedList b) {
        if (a.size() != b.size()) {
            return false;
        }
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i))) {
                return false;
            }
        }
        return true;
    }
}
