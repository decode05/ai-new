package TicTacToe;

import java.util.Scanner;

public class AI {
    private static final int HUMAN = -1, COMPUTER = 1;
    private int[] board = new int[9];

    public void playGame() {
        for (int turn = 0; turn < 9; turn++) {
            if (turn % 2 == 0)
                computerMove();
            else
                humanMove();
            printBoard();
            int result = checkWin();
            if (result != 0) {
                System.out.println("Game Over. " + (result == COMPUTER ? "Computer wins!" : "You win!"));
                return;
            }
        }
        System.out.println("Game Over. It's a draw.");
    }

    private void computerMove() {
        int bestScore = Integer.MIN_VALUE, move = -1;
        for (int i = 0; i < 9; i++) {
            if (board[i] == 0) {
                board[i] = COMPUTER;
                int score = minimax(false);
                board[i] = 0;
                if (score > bestScore) {
                    bestScore = score;
                    move = i;
                }
            }
        }
        board[move] = COMPUTER;
    }

    private void humanMove() {
        Scanner scanner = new Scanner(System.in);
        int move;
        do {
            System.out.println("Enter your move (1-9): ");
            move = scanner.nextInt() - 1;
        } while (move < 0 || move >= 9 || board[move] != 0);
        board[move] = HUMAN;
    }

    private int minimax(boolean isMaximizing) {
        int result = checkWin();
        if (result != 0 || isBoardFull())
            return result;
        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < 9; i++) {
            if (board[i] == 0) {
                board[i] = isMaximizing ? COMPUTER : HUMAN;
                int score = minimax(!isMaximizing);
                board[i] = 0;
                bestScore = isMaximizing ? Math.max(score, bestScore) : Math.min(score, bestScore);
            }
        }
        return bestScore;
    }

    private boolean isBoardFull() {
        for (int i : board)
            if (i == 0)
                return false;
        return true;
    }

    private int checkWin() {
        int[][] winLines = {
                { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, // rows
                { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, // columns
                { 0, 4, 8 }, { 2, 4, 6 } // diagonals
        };
        for (int[] line : winLines) {
            int sum = board[line[0]] + board[line[1]] + board[line[2]];
            if (sum == 3)
                return COMPUTER;
            if (sum == -3)
                return HUMAN;
        }
        return 0;
    }

    private void printBoard() {
        System.out.println();
        for (int i = 0; i < 9; i++) {
            System.out.print(board[i] == COMPUTER ? "X " : board[i] == HUMAN ? "O " : (i + 1) + " ");
            if (i % 3 == 2)
                System.out.println();
        }
    }

    public static void main(String[] args) {
        new AI().playGame();
    }
}
