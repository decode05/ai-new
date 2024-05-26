package UninformedSearch.DFS;

import java.util.*;

public class DFS_nqueens {

    static boolean isValid(int[] board, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (board[i] == col || Math.abs(board[i] - col) == row - i) {
                return false;
            }
        }
        return true;
    }

    static boolean solveNQueens(int[] board, int row) {
        int N = board.length;
        if (row == N) {
            return true; // All queens are placed
        }
        for (int col = 0; col < N; col++) {
            if (isValid(board, row, col)) {
                board[row] = col; // Place the queen
                if (solveNQueens(board, row + 1)) {
                    return true;
                }
                // Backtrack if placing the queen at (row, col) doesn't lead to a solution
                board[row] = -1;
            }
        }
        return false; // No solution found for this row
    }

    static void printBoard(int[] board) {
        int N = board.length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i] == j) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int N = 8; // Change N to solve for different board sizes
        int[] board = new int[N];
        Arrays.fill(board, -1); // Initialize board with -1 (no queen placed)

        if (solveNQueens(board, 0)) {
            System.out.println("Solution for " + N + "-Queens:");
            printBoard(board);
        } else {
            System.out.println("No solution found for " + N + "-Queens.");
        }
    }
}