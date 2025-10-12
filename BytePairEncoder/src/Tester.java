public class Tester {
    private static final String dir = "BytePairEncoder/class-example/";

    public static void main(String[] args) {
        // load the token file
        TokenMap map = FileHelper.loadTokens(dir + "/tokens.bpet");
        simple_example(map);
        split_example(map);
        combined_example(map);
    }

    public static void simple_example(TokenMap map) {
        EncodedList example = new EncodedList(map);

        example.add(new ByteValue((byte) 'i'));
        print_list_directly_ascii(example, map);

        example.add(0, new ByteValue((byte) 'h'));
        print_list_directly_ascii(example, map);

        example.add(1, new ByteValue((byte) 'e'));
        print_list_directly_ascii(example, map);

        example.add(new ByteValue((byte) 'n'));
        print_list_directly_ascii(example, map);

        example.set(0, new ByteValue((byte) 'p'));
        print_list_directly_ascii(example, map);

        example.add(new ByteValue((byte) ' '));
        print_list_directly_ascii(example, map);

        example.add(1, new ByteValue((byte) 'a'));
        print_list_directly_ascii(example, map);

        System.out.printf("Only %d EncodingValue\n", example.size());
        print_list_human(example, map);
    }

    public static void split_example(TokenMap map) {
        EncodedList splitList = new EncodedList(map);
        FileHelper.appendFile(splitList, dir + "input1.txt");
        System.out.println("Input 1 Encoded:");
        print_list_directly_ascii(splitList, map);
        // check with output1.bprl here

        FileHelper.appendFile(splitList, dir + "input2.txt");
        System.out.println("Input 1 then Input 2 Encoded:");
        print_list_directly_ascii(splitList, map);
        // check with output1-then-output2.bprl here
    }

    public static void combined_example(TokenMap map) {
        EncodedList listTogether = new EncodedList(map);
        FileHelper.appendFile(listTogether, dir + "input1-input2.txt");
        System.out.println("Input1-Input2 Encoded Together:");
        print_list_directly_ascii(listTogether, map);
        // check with output1-output2.bprl here
    }

    // don't need the map, but passing it so it has same signature as
    // print_list_human
    // in hindsight, I probably should have required a getter method for the map...
    // oh well
    //
    // try changing any print_list_directly into print_list_human to see the output!
    public static void print_list_directly(EncodedList list, TokenMap map) {
        System.out.print("(");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0)
                System.out.print(", ");

            System.out.print(list.get(i));
        }
        System.out.println(")");
    }

    // since we know out input files are ascii, we can make the output a little more
    // specialized
    public static void print_list_directly_ascii(EncodedList list, TokenMap map) {
        System.out.print("(");
        for (int i = 0; i < list.size(); i++) {
            // if (i > 0) System.out.print(", ");

            EncodingValue value = list.get(i);
            if (value instanceof ByteValue) {
                System.out.printf("[%c]", (char) ((ByteValue) value).value());
            } else {
                System.out.print(value);
            }
        }
        System.out.println(")");
    }

    public static void print_list_human(EncodedList list, TokenMap map) {
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%s|", list.get(i).expandAsString(map));
        }
        System.out.println();
    }

    public static void print_token_info(TokenMap map) {
        for (int i = 0; i < map.numTokens(); ++i) {
            TokenValue tokenI = new TokenValue((short) i);
            // what does token <i> map to?
            Pair tokenPair = map.getPair(tokenI);
            EncodingValue first = tokenPair.first();
            EncodingValue second = tokenPair.second();
            // write it out to see!
            System.out.printf("%s = %s %s", tokenI, first, second);
            System.out.printf(" (expands to '%s')\n", tokenI.expandAsString(map));
        }
    }
}
