import java.util.ArrayList;

public class MyBotClass extends RandomBot {

    @Override
    public int chooseMove(Game game) {
        // Get all available empty spots on the board
        ArrayList<Integer> blankSpots = getBlanks(game);

        // 1. Win if possible: Check all empty spots, if any move results in a win, return that move immediately
        for (int move : blankSpots) {
            if (game.isWinningMove(move)) {
                return move;
            }
        }

        // 2. Block if opponent can win: Check if the opponent has any winning move next turn, block it
        for (int move : blankSpots) {
            if (game.isBlockingMove(move)) {
                return move;
            }
        }

        // 3. Fork if possible: Attempt to create a fork where the bot can have two winning options
        for (int move : blankSpots) {
            if (createsFork(game, move, turn(game))) {
                return move;
            }
        }

        // 4. Block opponent’s fork: Prevent the opponent from creating a fork by checking all their potential fork moves

        // Check if opponent can create a fork in any of the blank spots and block it
        for (int move : blankSpots) {
            if (createsFork(game, move, opponent(turn(game)))) {
                return move;
            }
        }

        // 5. Take center if available: The center position (index 4) is strategic, so take it if open
        if (blankSpots.contains(4)) {
            return 4;
        }

        // 6. Take opposite corner: If opponent occupies a corner, take the opposite corner if it is free
        Integer oppCorner = oppositeCorner(game);
        if (oppCorner != null && blankSpots.contains(oppCorner)) {
            return oppCorner;
        }

        // 7. Take any empty corner: Corners are more powerful than edges, so prioritize them next
        for (int corner : new int[]{0, 2, 6, 8}) {
            if (blankSpots.contains(corner)) {
                return corner;
            }
        }

        // 8. Take any empty side (edges): If no corners or center are available, take one of the sides (edges)
        for (int edge : new int[]{1, 3, 5, 7}) {
            if (blankSpots.contains(edge)) {
                return edge;
            }
        }

        // 9. Fallback: If none of the above conditions apply, choose a random available move
        return random(blankSpots);
    }

    /**
     * Check if making a move creates a fork (i.e., two or more winning moves simultaneously).
     * This simulates the move, then counts potential winning moves afterward.
     */
    private boolean createsFork(Game game, int move, char playerSymbol) {
        char[] board = game.position();
        // If the spot is not empty, cannot create fork here
        if (board[move] != ' ') {
            return false;
        }

        // Simulate placing the player's symbol on the board
        board[move] = playerSymbol;

        int winCount = 0;
        // For each empty spot after the simulated move, check if placing the symbol there results in a winning position
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                board[i] = playerSymbol;
                if (isWinningPosition(board, playerSymbol)) {
                    winCount++;
                }
                board[i] = ' '; // Undo the move
            }
        }

        board[move] = ' '; // Undo the initial simulated move

        // If two or more winning moves are possible after this move, it's a fork
        return winCount >= 2;
    }

    /**
     * Check if the current board position is a winning position for the given player symbol.
     * Winning means three in a row in rows, columns, or diagonals.
     */
    private boolean isWinningPosition(char[] board, char symbol) {
        int[][] wins = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
                {0, 4, 8}, {2, 4, 6}             // diagonals
        };

        // Check all possible winning triplets
        for (int[] combo : wins) {
            if (board[combo[0]] == symbol && board[combo[1]] == symbol && board[combo[2]] == symbol) {
                return true;
            }
        }

        return false; // No winning combination found
    }

    /**
     * Returns whose turn it is in the game (either 'X' or 'O').
     */
    private char turn(Game game) {
        return game.turn();
    }

    /**
     * Returns the index of the opposite corner if opponent occupies one corner and the opposite corner is empty.
     * Returns null if no opposite corner is available.
     */
    private Integer oppositeCorner(Game game) {
        char[] board = game.position();
        // Check each corner and its opposite
        if (board[0] == opponent(turn(game)) && board[8] == ' ') return 8;
        if (board[8] == opponent(turn(game)) && board[0] == ' ') return 0;
        if (board[2] == opponent(turn(game)) && board[6] == ' ') return 6;
        if (board[6] == opponent(turn(game)) && board[2] == ' ') return 2;
        return null; // No opposite corner available
    }

    /**
     * Returns the opponent player's symbol given the current player symbol.
     * If player is 'X', opponent is 'O', else opponent is 'X'.
     */
    private char opponent(char player) {
        if (player == 'X') {
            return 'O';
        } else {
            return 'X';
        }
    }

    // getBlanks() and random() methods are inherited from RandomBot
}
