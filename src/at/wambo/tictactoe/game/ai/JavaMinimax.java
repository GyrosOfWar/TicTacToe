package at.wambo.tictactoe.game.ai;

/**
 * Created with IntelliJ IDEA.
 * User: Martin
 * Date: 24.06.13
 * Time: 10:44
 */

import at.wambo.tictactoe.game.TTTGame;

import java.util.ArrayList;
import java.util.List;

/**
 * AIPlayer using Minimax algorithm
 */
public class JavaMinimax {
    private final char player;
    private final char computer;
    private final char empty = ' ';
    private char[][] cells;

    /**
     * Constructor with the given game board
     */
    public JavaMinimax(TTTGame game) {
        player = game.Player1();
        computer = game.Player2();
        cells = game.field();
    }

    /**
     * Get next best move for computer. Return int[2] of {row, col}
     */
    public int[] move() {
        int[] result = minimax(2, computer); // depth, max turn
        return new int[]{result[1], result[2]};   // row, col
    }

    /**
     * Recursive minimax at level of depth for either maximizing or minimizing player.
     * Return int[3] of {score, row, col}
     */
    public int[] minimax(int depth, char player) {
        // Generate possible next moves in a List of int[2] of {row, col}.
        List<int[]> nextMoves = generateMoves();

        // computer is maximizing; while player is minimizing
        int bestScore = (player == computer) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestRow = -1;
        int bestCol = -1;

        if (nextMoves.isEmpty() || depth == 0) {
            // Gameover or depth reached, evaluate score
            bestScore = evaluate();
        } else {
            for (int[] move : nextMoves) {
                // Try this move for the current "player"
                cells[move[0]][move[1]] = player;
                printCells();
                if (player == computer) {  // computer (computer) is maximizing player
                    currentScore = minimax(depth - 1, this.player)[0];
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                } else {  // player is minimizing player
                    currentScore = minimax(depth - 1, computer)[0];
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                }
                // Undo move
                cells[move[0]][move[1]] = empty;
            }
        }
        return new int[]{bestScore, bestRow, bestCol};
    }

    private void printCells() {
        for (char[] row : cells) {
            for (char c : row) {
                System.out.println(" | " + c + " | ");
            }
            System.out.println("\n");
        }
    }

    /**
     * Find all valid next moves.
     * Return List of moves in int[2] of {row, col} or empty list if gameover
     */
    public List<int[]> generateMoves() {
        List<int[]> nextMoves = new ArrayList<>(); // allocate List

        // If gameover, i.e., no next move
        if (hasWon(computer) || hasWon(player)) {
            return nextMoves;   // return empty list
        }

        // Search for empty cells and add to the List
        for (int row = 0; row < cells.length; ++row) {
            for (int col = 0; col < cells[row].length; ++col) {
                if (cells[row][col] == empty) {
                    nextMoves.add(new int[]{row, col});
                }
            }
        }
        return nextMoves;
    }

    /**
     * The heuristic evaluation function for the current board
     *
     * @return +100, +10, +1 for EACH 3-, 2-, 1-in-a-line for computer.
     *         -100, -10, -1 for EACH 3-, 2-, 1-in-a-line for opponent.
     *         0 otherwise
     */
    public int evaluate() {
        int score = 0;
        // Evaluate score for each of the 8 lines (3 rows, 3 columns, 2 diagonals)
        score += evaluateLine(0, 0, 0, 1, 0, 2);  // row 0
        score += evaluateLine(1, 0, 1, 1, 1, 2);  // row 1
        score += evaluateLine(2, 0, 2, 1, 2, 2);  // row 2
        score += evaluateLine(0, 0, 1, 0, 2, 0);  // col 0
        score += evaluateLine(0, 1, 1, 1, 2, 1);  // col 1
        score += evaluateLine(0, 2, 1, 2, 2, 2);  // col 2
        score += evaluateLine(0, 0, 1, 1, 2, 2);  // diagonal
        score += evaluateLine(0, 2, 1, 1, 2, 0);  // alternate diagonal
        return score;
    }

    /**
     * The heuristic evaluation function for the given line of 3 cells
     *
     * @return +100, +10, +1 for 3-, 2-, 1-in-a-line for computer.
     *         -100, -10, -1 for 3-, 2-, 1-in-a-line for opponent.
     *         0 otherwise
     */
    public int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3) {
        int score = 0;

        // First cell
        if (cells[row1][col1] == computer) {
            score = 1;
        } else if (cells[row1][col1] == player) {
            score = -1;
        }

        // Second cell
        if (cells[row2][col2] == computer) {
            if (score == 1) {   // cell1 is computer
                score = 10;
            } else if (score == -1) {  // cell1 is player
                return 0;
            } else {  // cell1 is empty
                score = 1;
            }
        } else if (cells[row2][col2] == player) {
            if (score == -1) { // cell1 is player
                score = -10;
            } else if (score == 1) { // cell1 is computer
                return 0;
            } else {  // cell1 is empty
                score = -1;
            }
        }

        // Third cell
        if (cells[row3][col3] == computer) {
            if (score > 0) {  // cell1 and/or cell2 is computer
                score *= 10;
            } else if (score < 0) {  // cell1 and/or cell2 is player
                return 0;
            } else {  // cell1 and cell2 are empty
                score = 1;
            }
        } else if (cells[row3][col3] == player) {
            if (score < 0) {  // cell1 and/or cell2 is player
                score *= 10;
            } else if (score > 1) {  // cell1 and/or cell2 is computer
                return 0;
            } else {  // cell1 and cell2 are empty
                score = -1;
            }
        }
        return score;
    }

    private int[] winningPatterns = {
            0b111000000, 0b000111000, 0b000000111, // rows
            0b100100100, 0b010010010, 0b001001001, // cols
            0b100010001, 0b001010100               // diagonals
    };

    /**
     * Returns true if thePlayer wins
     */
    public boolean hasWon(char thePlayer) {
        int pattern = 0b000000000;  // 9-bit pattern for the 9 cells
        for (int row = 0; row < cells.length; ++row) {
            for (int col = 0; col < cells[row].length; ++col) {
                if (cells[row][col] == thePlayer) {
                    pattern |= (1 << (row * cells[row].length + col));
                }
            }
        }
        for (int winningPattern : winningPatterns) {
            if ((pattern & winningPattern) == winningPattern) return true;
        }
        return false;
    }
}