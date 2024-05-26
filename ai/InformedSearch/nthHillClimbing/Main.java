package InformedSearch.nthHillClimbing;

import java.util.*;

public class Main {

    static final int N = 8;

    static int calcEvaluationFunction(int[] board) {
        int cnt = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = i + 1; j < 8; ++j) {
                if (board[i] == board[j])
                    ++cnt;
                else if (Math.abs(i - j) == Math.abs(board[i] - board[j]))
                    ++cnt;
            }
        }
        return 28 - cnt;
    }

    static void printBoard(int[] board) {
        int[][] grid = new int[10][10];
        for (int[] row : grid) {
            Arrays.fill(row, 0);
        }
        for (int i = 0; i < 8; ++i) {
            grid[board[i]][i] = 1;
        }
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j)
                System.out.print(grid[i][j] + " ");
            System.out.println();
        }
    }

    static void copyBoard(int[] board1, int[] board2) {
        for (int i = 0; i < 8; ++i)
            board1[i] = board2[i];
    }

    static boolean hillClimbing(int[] board) {
        int[] bestBoard = new int[N];
        int[] currBoard = new int[N];
        copyBoard(bestBoard, board);

        int bestValue, boardValue;
        int cnt = 0;
        while (true) {
            copyBoard(currBoard, board);
            boardValue = bestValue = calcEvaluationFunction(board);

            if (bestValue == 28) {
                System.out.println("Reached Global Maxima after " + cnt + " moves");
                printBoard(bestBoard);
                return true;
            }

            ++cnt;
            for (int i = 0; i < 8; ++i) {
                int temp = currBoard[i];
                for (int j = 0; j < 8; ++j) {
                    if (j == board[i])
                        continue;
                    currBoard[i] = j;
                    int currValue = calcEvaluationFunction(currBoard);
                    if (currValue > bestValue) {
                        bestValue = currValue;
                        copyBoard(bestBoard, currBoard);
                    }
                }
                currBoard[i] = temp;
            }
            if (bestValue <= boardValue) {
                System.out.println("Stucked in Local Maxima after " + cnt + " moves");
                printBoard(bestBoard);
                return false;
            }
            copyBoard(board, bestBoard);
        }
    }

    public static void main(String[] args) {
        boolean success;
        int cnt = 1;
        int[] board = new int[N];
        Random rand = new Random();
        while (true) {
            for (int i = 0; i < 8; ++i)
                board[i] = rand.nextInt(8);

            System.out.println("======================== Iteration : " + cnt);
            System.out.println("Initial Board");
            printBoard(board);
            success = hillClimbing(board);
            ++cnt;

            if (success)
                break;
        }
    }
}
