package TicTacToe;

import java.util.Scanner;

public class NonAI2 {

    private static final int BLANK = 2;
    private static final int X = 3;
    private static final int O = 5;
    private int[] board = new int[10];
    private int turn = 1;

    public NonAI2() {
        for (int i = 1; i < board.length; i++) {
            board[i] = BLANK;
        }
    }

    public int make2() {
        if (board[5] == BLANK) {
            return 5;
        } else {
            for (int i : new int[] { 2, 4, 6, 8 }) {
                if (board[i] == BLANK) {
                    return i;
                }
            }
        }
        for (int i : new int[] { 1, 3, 7, 9 }) {
            if (board[i] == BLANK) {
                return i;
            }
        }
        return -1;
    }

    public int possWin(int p) {
        int[][] winPositions = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 1, 4, 7 }, { 2, 5, 8 }, { 3, 6, 9 },
                { 1, 5, 9 }, { 3, 5, 7 } };
        for (int[] pos : winPositions) {
            int prod = board[pos[0]] * board[pos[1]] * board[pos[2]];
            if (p == X && prod == X * X * BLANK || p == O && prod == O * O * BLANK) {
                for (int i = 0; i < 3; i++) {
                    if (board[pos[i]] == BLANK) {
                        return pos[i];
                    }
                }
            }
        }
        return 0;
    }

    public void go(int n) {
        if (turn % 2 == 0) {
            board[n] = O;
        } else {
            board[n] = X;
        }
        turn++;
    }

    public void printBoard() {
        for (int i = 1; i < board.length; i++) {
            String val = board[i] == BLANK ? " " : (board[i] == X ? "X" : "O");
            System.out.print(val + " ");
            if (i % 3 == 0) {
                System.out.println();
            }
        }
    }

    public boolean isGameOver() {
        int[][] winPositions = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 1, 4, 7 }, { 2, 5, 8 }, { 3, 6, 9 },
                { 1, 5, 9 }, { 3, 5, 7 } };
        int c = 0;
        for (int[] pos : winPositions) {
            int prod = board[pos[0]] * board[pos[1]] * board[pos[2]];
            if (prod == X * X * X || prod == O * O * O) {
                return true;
            }
        }

        for (int a : board) {
            if (a == BLANK) {
                c++;
            }
        }
        if (c == 0) {
            return true;
        }

        return false;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        // Ask the user to choose X or O
        System.out.println("Choose 1 for X, 2 for O:");
        int choice = scanner.nextInt();
        while (choice != 1 && choice != 2) {
            System.out.println("Invalid choice. Choose 1 for X, 2 for O:");
            choice = scanner.nextInt();
        }

        if (choice == 1) {
            System.out.println("You chose X. You go first.");
        } else {
            System.out.println("You chose O. AI goes first.");
            turn = 2; // AI goes first
        }

        while (turn <= 9) {
            printBoard();
            if (turn % 2 == 0) {
                int pos = possWin(O);
                if (pos == 0) {
                    pos = make2();
                }
                if (pos != -1) {
                    go(pos);
                    System.out.println("AI moved to position " + pos);
                }
            } else {
                System.out.println("Your turn. Enter position (1-9): ");
                int pos = scanner.nextInt();
                while (pos < 1 || pos > 9 || board[pos] != BLANK) {
                    System.out.println("Invalid move. Try again: ");
                    pos = scanner.nextInt();
                }
                go(pos);
            }
            if (isGameOver()) {
                System.out.println("Game Over!");
                break;
            }
        }
        printBoard();
    }

    public static void main(String[] args) {
        NonAI2 game = new NonAI2();
        game.play();
    }
}