public class MyGameClass implements Game {

    // Tracks the last two moves for undo functionality
    private final MyQueue<Integer> lastTwoIndexTracker;

    // Tracks indices that were reset because their lifetime reached zero
    private final MyQueue<Integer> lastTwoIndexReset;

    // The two players
    private final Player player1;
    private final Player player2;

    // Maximum number of moves allowed before hitting the history limit
    private final int movingHistoryLimit;

    // Current move number
    private int moveNumber;

    // Lifetime of each cell (0 = empty)
    private final int[] indexLifetime = {0, 0, 0, 0, 0, 0, 0, 0, 0};

    // Symbols in each cell (' ' if empty)
    private final char[] indexSymbol = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};

    // Tracks if each cell is empty (true = empty)
    private final boolean[] isEmpty = {true, true, true, true, true, true, true, true, true};

    // Maximum number of undos allowed at the moment
    private int maxAllowedUndo;

    // --- Constructors ---
    public MyGameClass(Player player1, Player player2) {
        // Default constructor with a very large history limit
        this(player1, player2, Integer.MAX_VALUE / 2);
    }

    public MyGameClass(Player player1, Player player2, int movingHistory) {
        // Constructor that sets a specific moving history limit
        this.player1 = player1;
        this.player2 = player2;
        this.movingHistoryLimit = movingHistory;
        this.moveNumber = 0;
        this.maxAllowedUndo = movingHistoryLimit / 2;

        // Initialize the queues to track moves and resets
        this.lastTwoIndexTracker = new MyQueue<>(movingHistoryLimit + 2);
        this.lastTwoIndexReset = new MyQueue<>(movingHistoryLimit + 2);
    }

    // --- Accessors ---
    @Override
    public int[] lifetimes() {
        return indexLifetime; // Return array of cell lifetimes
    }

    @Override
    public char[] position() {
        return indexSymbol; // Return array of current symbols on the board
    }

    @Override
    public int currentMoveNumber() {
        return moveNumber; // Return the current move number
    }

    @Override
    public boolean canUndo() {
        // Can undo if there are moves made and undo limit is not zero
        return maxAllowedUndo != 0 && moveNumber >= 2;
    }

    @Override
    public char turn() {
        // Return 'X' if move number is even, 'O' if odd
        return (moveNumber % 2 == 0) ? 'X' : 'O';
    }

    // --- Main Game Loop ---
    @Override
    public Character playGame() {
        while (true) {
            increaseMaxAllowedUndo(); // Gradually increase undo allowance

            // Each player takes a turn
            for (int turn = 0; turn < 2; turn++) {
                Player current = (turn == 0) ? player1 : player2;
                Player opponent = (turn == 0) ? player2 : player1;
                char symbol = (turn == 0) ? 'X' : 'O';
                char opponentSymbol = (symbol == 'X') ? 'O' : 'X';

                updateIndexIfNotEmpty(); // Decrease lifetimes and reset expired cells
                checkMemory(); // Ensure history queues don't exceed limit

                int move = current.chooseMove(this);

                // Handle undo requests
                while (move == Game.REQUEST_UNDO) {
                    if (opponent.requestUndo(this) && canUndo()) {
                        undoLastTwoMoves(); // Undo last two moves
                    }
                    move = current.chooseMove(this);
                }

                // Handle draw offers
                while (move == Game.OFFER_DRAW) {
                    if (opponent.offerDraw(this)) return null; // Game ends in a draw
                    move = current.chooseMove(this);
                }

                // Handle resignation
                if (move == Game.RESIGN) return opponentSymbol;

                // Handle a valid move
                if (move >= 0 && move < indexLifetime.length && isEmpty[move]) {
                    isEmpty[move] = false; // Mark cell as occupied
                    indexSymbol[move] = symbol; // Set player's symbol
                    indexLifetime[move] = 7; // Reset cell lifetime
                    moveNumber++; // Increment move number
                    lastTwoIndexTracker.offer(move); // Track this move

                    if (checkWin(symbol)) return symbol; // Check for a win
                }
            }
        }
    }

    // Increase the maximum allowed undo gradually, up to half the history limit
    private void increaseMaxAllowedUndo() {
        if (maxAllowedUndo != (movingHistoryLimit / 2)) {
            maxAllowedUndo++;
        }
    }

    // Ensure move history queues do not exceed the set limit
    private void checkMemory() {
        if (lastTwoIndexTracker.size() > movingHistoryLimit + 1) lastTwoIndexTracker.removeLast();
        if (lastTwoIndexReset.size() > movingHistoryLimit + 1) lastTwoIndexReset.removeLast();
    }

    @Override
    public boolean isBlockingMove(int index) {
        // Checks if the move at 'index' would block the opponent from winning
        char opponent = (turn() == 'X') ? 'O' : 'X';
        if (index < 0 || index >= indexSymbol.length || indexSymbol[index] != ' ') return false;

        indexSymbol[index] = opponent;
        boolean result = checkWin(opponent);
        indexSymbol[index] = ' ';
        return result;
    }

    @Override
    public boolean isWinningMove(int index) {
        // Checks if the move at 'index' would result in a win for the current player
        char current = turn();
        if (index < 0 || index >= indexSymbol.length || indexSymbol[index] != ' ') return false;

        indexSymbol[index] = current;
        boolean result = checkWin(current);
        indexSymbol[index] = ' ';
        return result;
    }

    // Decrease lifetimes of non-empty cells and reset cells that expire
    private void updateIndexIfNotEmpty() {
        for (int i = 0; i < isEmpty.length; i++) {
            if (!isEmpty[i]) {
                indexLifetime[i]--;
                if (indexLifetime[i] <= 0) {
                    lastTwoIndexReset.offer(i); // Track reset cell
                    indexSymbol[i] = ' '; // Clear symbol
                    isEmpty[i] = true; // Mark as empty
                }
            }
        }
    }

    // Undo the last two moves
    private void undoLastTwoMoves() {
        char opponent = (turn() == 'X') ? 'O' : 'X';

        // Undo last two tracked moves
        for (int i = 0; i < 2; i++) {
            Integer last = lastTwoIndexTracker.peekFirst();
            if (last != null) {
                indexSymbol[last] = ' '; // Clear symbol
                isEmpty[last] = true; // Mark cell empty
                indexLifetime[last] = 0; // Reset lifetime

                // Increase lifetime of remaining cells
                for (int j = 0; j < 9; j++) {
                    if (!isEmpty[j]) {
                        indexLifetime[j]++;
                    }
                }

                // Remove move from tracker unless it is the last move by player X
                if (opponent != 'X' || lastTwoIndexTracker.size() != 1) {
                    lastTwoIndexTracker.removeFirst();
                }
                moveNumber--; // Decrement move number
            }
        }

        // Restore any previously reset indices
        if (!lastTwoIndexReset.isEmpty()) {
            Integer first = lastTwoIndexReset.removeFirst();
            if (first != null) {
                indexSymbol[first] = opponent;
                indexLifetime[first] = 2;
                isEmpty[first] = false;
            }
            if (!lastTwoIndexReset.isEmpty()) {
                Integer second = lastTwoIndexReset.removeFirst();
                if (second != null) {
                    indexSymbol[second] = turn();
                    indexLifetime[second] = 1;
                    isEmpty[second] = false;
                }
            }
        }

        maxAllowedUndo--; // Reduce the remaining allowed undo
    }

    // Check if a given symbol has a winning combination
    private boolean checkWin(char symbol) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (indexSymbol[i * 3] == symbol && indexSymbol[i * 3 + 1] == symbol && indexSymbol[i * 3 + 2] == symbol) {
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (indexSymbol[i] == symbol && indexSymbol[i + 3] == symbol && indexSymbol[i + 6] == symbol) return true;
        }

        // Check diagonals
        return (indexSymbol[0] == symbol && indexSymbol[4] == symbol && indexSymbol[8] == symbol) ||
                (indexSymbol[2] == symbol && indexSymbol[4] == symbol && indexSymbol[6] == symbol);
    }
}
