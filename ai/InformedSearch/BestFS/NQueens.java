// Does not work

package InformedSearch.BestFS;

import java.util.*;

class NQueens {
    private int N;

    public NQueens(int N) {
        this.N = N;
    }

    private class BoardState implements Comparable<BoardState> {
        int[] queens; // queens[i] = column of queen in row i
        int h; // heuristic cost (number of misplaced queens)

        BoardState(int[] queens) {
            this.queens = Arrays.copyOf(queens, N);
            this.h = calculateHeuristic(queens);
        }

        private int calculateHeuristic(int[] queens) {
            int misplacedQueens = 0;
            for (int i = 0; i < N; i++) {
                if (queens[i] != i) { // If the queen is not in its expected row
                    misplacedQueens++;
                }
            }
            return misplacedQueens;
        }

        @Override
        public int compareTo(BoardState other) {
            return Integer.compare(this.h, other.h); // Prioritize based on heuristic cost
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            BoardState that = (BoardState) obj;
            return Arrays.equals(queens, that.queens);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(queens);
        }
    }

    public int[] solve() {
        PriorityQueue<BoardState> openList = new PriorityQueue<>();
        Set<BoardState> closedList = new HashSet<>();

        int[] initialQueens = new int[N];
        Arrays.fill(initialQueens, -1); // Initialize with empty board
        openList.add(new BoardState(initialQueens));

        while (!openList.isEmpty()) {
            BoardState currentState = openList.poll();

            if (currentState.h == 0) {
                return currentState.queens; // Solution found
            }

            closedList.add(currentState);

            int currentRow = currentState.queens.length;
            for (int col = 0; col < N; col++) {
                if (isValid(currentState.queens, currentRow, col)) {
                    int[] newQueens = Arrays.copyOf(currentState.queens, N);
                    newQueens[currentRow] = col;
                    BoardState newState = new BoardState(newQueens);

                    if (!closedList.contains(newState)) {
                        openList.add(newState);
                    }
                }
            }
        }

        return null; // No solution found
    }

    private boolean isValid(int[] queens, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (queens[i] == col || Math.abs(queens[i] - col) == row - i) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int N = 8; // Example for 8-Queens problem
        NQueens solver = new NQueens(N);
        int[] solution = solver.solve();

        if (solution != null) {
            System.out.println("Solution found:");
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (solution[i] == j) {
                        System.out.print("Q ");
                    } else {
                        System.out.print(". ");
                    }
                }
                System.out.println();
            }
        } else {
            System.out.println("No solution found.");
        }
    }
}
