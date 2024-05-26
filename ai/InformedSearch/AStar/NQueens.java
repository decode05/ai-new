package InformedSearch.AStar;

import java.util.*;

class NQueens {
    private int N;

    public NQueens(int N) {
        this.N = N;
    }

    private class BoardState implements Comparable<BoardState> {
        int[] queens; // queens[i] = column of queen in row i
        int g; // cost to reach this state (number of queens placed)
        int h; // heuristic cost (estimated number of conflicts)
        int f; // total cost (g + h)

        BoardState(int[] queens, int g) {
            this.queens = Arrays.copyOf(queens, N);
            this.g = g;
            this.h = calculateHeuristic(queens, g);
            this.f = this.g + this.h;
        }

        private int calculateHeuristic(int[] queens, int g) {
            int conflicts = 0;
            for (int i = 0; i < g; i++) {
                for (int j = i + 1; j < g; j++) {
                    if (queens[i] == queens[j] || Math.abs(queens[i] - queens[j]) == j - i) {
                        conflicts++;
                    }
                }
            }
            return conflicts;
        }

        @Override
        public int compareTo(BoardState other) {
            return Integer.compare(this.f, other.f);
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
        Arrays.fill(initialQueens, -1);
        openList.add(new BoardState(initialQueens, 0));

        while (!openList.isEmpty()) {
            BoardState currentState = openList.poll();

            if (currentState.g == N && currentState.h == 0) {
                return currentState.queens; // Solution found
            }

            closedList.add(currentState);

            int currentRow = currentState.g;
            for (int col = 0; col < N; col++) {
                if (isValid(currentState.queens, currentRow, col)) {
                    int[] newQueens = Arrays.copyOf(currentState.queens, N);
                    newQueens[currentRow] = col;
                    BoardState newState = new BoardState(newQueens, currentRow + 1);

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