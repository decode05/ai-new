package InformedSearch.AStar;

import java.util.*;

public class EightPuzzleProblem {
    private static final int SIZE = 3; // Size of the puzzle board
    private static final int[][] GOAL_STATE = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } }; // Goal state

    public static void main(String[] args) {
        EightPuzzleProblem problem = new EightPuzzleProblem();
        int[][] initialState = { { 1, 2, 3 }, { 0, 4, 6 }, { 7, 5, 8 } }; // Initial state

        System.out.println("A* Search:");
        printResult(problem.aStarSearch(initialState));
    }

    static class PuzzleState {
        int[][] board;
        String action;
        int g; // Cost from initial state to current state

        PuzzleState(int[][] board, String action, int g) {
            this.board = board;
            this.action = action;
            this.g = g;
        }

        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            PuzzleState state = (PuzzleState) o;
            return Arrays.deepEquals(board, state.board);
        }

        public int hashCode() {
            return Arrays.deepHashCode(board);
        }
    }

    private List<PuzzleState> getNeighbors(PuzzleState state) {
        List<PuzzleState> neighbors = new ArrayList<>();
        int zeroRow = -1, zeroCol = -1;
        // Find the position of the zero (empty) tile
        outerloop: for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (state.board[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                    break outerloop;
                }
            }
        }

        // Generate neighbors by moving the zero tile
        int[][] moves = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        for (int[] move : moves) {
            int newRow = zeroRow + move[0];
            int newCol = zeroCol + move[1];
            if (isValidMove(newRow, newCol)) {
                int[][] newBoard = copyBoard(state.board);
                swap(newBoard, zeroRow, zeroCol, newRow, newCol);
                neighbors.add(new PuzzleState(newBoard,
                        "Move 0 from (" + zeroRow + "," + zeroCol + ") to (" + newRow + "," + newCol + ")",
                        state.g + 1)); // Increment cost (g-value) by 1
            }
        }
        return neighbors;
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    private int[][] copyBoard(int[][] board) {
        int[][] newBoard = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, SIZE);
        }
        return newBoard;
    }

    private void swap(int[][] board, int row1, int col1, int row2, int col2) {
        int temp = board[row1][col1];
        board[row1][col1] = board[row2][col2];
        board[row2][col2] = temp;
    }

    private List<PuzzleState> constructPath(Map<PuzzleState, PuzzleState> parentMap, PuzzleState goal) {
        List<PuzzleState> path = new ArrayList<>();
        for (PuzzleState at = goal; at != null; at = parentMap.get(at))
            path.add(at);
        Collections.reverse(path);
        return path;
    }

    public List<PuzzleState> aStarSearch(int[][] initialState) {
        PriorityQueue<PuzzleState> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(this::f));
        Set<PuzzleState> visited = new HashSet<>();
        Map<PuzzleState, PuzzleState> parentMap = new HashMap<>();
        PuzzleState initialPuzzleState = new PuzzleState(initialState, "Initial State", 0);
        priorityQueue.add(initialPuzzleState);
        visited.add(initialPuzzleState);
        while (!priorityQueue.isEmpty()) {
            PuzzleState current = priorityQueue.poll();
            if (isGoalState(current))
                return constructPath(parentMap, current);
            for (PuzzleState neighbor : getNeighbors(current)) {
                int tentativeG = current.g + 1; // Cost of moving to neighbor is always 1
                if (!visited.contains(neighbor) || tentativeG < neighbor.g) {
                    neighbor.g = tentativeG;
                    priorityQueue.add(neighbor);
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }
        return Collections.emptyList();
    }

    private int f(PuzzleState state) {
        return g(state) + h(state);
    }

    private int g(PuzzleState state) {
        return state.g;
    }

    private int h(PuzzleState state) {
        int misplacedTiles = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (state.board[i][j] != 0 && state.board[i][j] != GOAL_STATE[i][j]) {
                    misplacedTiles++;
                }
            }
        }
        return misplacedTiles;
    }

    private boolean isGoalState(PuzzleState state) {
        return Arrays.deepEquals(state.board, GOAL_STATE);
    }

    private static void printResult(List<PuzzleState> result) {
        if (result.isEmpty()) {
            System.out.println("No solution found.");
        } else {
            for (int i = 0; i < result.size(); i++) {
                PuzzleState state = result.get(i);
                System.out.println("Step " + (i + 1) + ": " + state.action);
                printBoard(state.board);
            }
            System.out.println("Total steps: " + result.size());
        }
    }

    private static void printBoard(int[][] board) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
