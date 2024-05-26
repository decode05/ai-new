package UninformedSearch.BFS;

import java.util.*;

public class BFS_nqueens {

    static class State {
        int[] board;
        int row;

        public State(int[] board, int row) {
            this.board = Arrays.copyOf(board, board.length);
            this.row = row;
        }
    }

    static boolean isSafe(int[] board, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (board[i] == col || Math.abs(board[i] - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }

    static List<int[]> generateSuccessors(State state) {
        List<int[]> successors = new ArrayList<>();
        int N = state.board.length;
        int row = state.row;

        for (int col = 0; col < N; col++) {
            if (isSafe(state.board, row, col)) {
                int[] newBoard = Arrays.copyOf(state.board, N);
                newBoard[row] = col;
                successors.add(newBoard);
            }
        }
        return successors;
    }

    static int[] bfsNQueens(int N) {
        Queue<State> queue = new LinkedList<>();
        int[] initialBoard = new int[N];
        Arrays.fill(initialBoard, -1);
        queue.add(new State(initialBoard, 0));

        while (!queue.isEmpty()) {
            State currentState = queue.poll();

            if (currentState.row == N) {
                return currentState.board;
            }

            List<int[]> successors = generateSuccessors(currentState);
            for (int[] successor : successors) {
                queue.add(new State(successor, currentState.row + 1));
            }
        }
        return null; // No solution found
    }

    static void printBoard(int[] board) {
        int N = board.length;
        for (int row : board) {
            for (int col = 0; col < N; col++) {
                if (col == row) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int N = 8;
        int[] solution = bfsNQueens(N);

        if (solution != null) {
            System.out.println("Solution for " + N + "-Queens:");
            printBoard(solution);
        } else {
            System.out.println("No solution found for " + N + "-Queens.");
        }
    }
}