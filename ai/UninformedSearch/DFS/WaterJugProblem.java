package UninformedSearch.DFS;

import java.util.*;

public class WaterJugProblem {
    private static final int JUG1_CAPACITY = 3, JUG2_CAPACITY = 5, TARGET = 4;

    public static void main(String[] args) {
        WaterJugProblem problem = new WaterJugProblem();
        System.out.println("\nDFS:");
        printResult(problem.dfs(), problem.dfsStates);
    }

    static class State {
        int jug1, jug2;
        String action;

        State(int jug1, int jug2, String action) {
            this.jug1 = jug1;
            this.jug2 = jug2;
            this.action = action;
        }

        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            State state = (State) o;
            return jug1 == state.jug1 && jug2 == state.jug2;
        }

        public int hashCode() {
            return Objects.hash(jug1, jug2);
        }

        public String toString() {
            return "jug1: " + jug1 + ", jug2: " + jug2 + " - " + action;
        }
    }

    int dfsStates = 0;

    private List<State> getNeighbors(State state) {
        List<State> neighbors = new ArrayList<>();
        int jug1 = state.jug1, jug2 = state.jug2;
        neighbors.add(new State(JUG1_CAPACITY, jug2, "Fill Jug1"));
        neighbors.add(new State(jug1, JUG2_CAPACITY, "Fill Jug2"));
        neighbors.add(new State(0, jug2, "Empty Jug1"));
        neighbors.add(new State(jug1, 0, "Empty Jug2"));
        neighbors.add(new State(Math.max(0, jug1 - (JUG2_CAPACITY - jug2)), Math.min(JUG2_CAPACITY, jug1 + jug2),
                "Pour Jug1 -> Jug2"));
        neighbors.add(new State(Math.min(JUG1_CAPACITY, jug1 + jug2), Math.max(0, jug2 - (JUG1_CAPACITY - jug1)),
                "Pour Jug2 -> Jug1"));
        return neighbors;
    }

    private List<State> constructPath(Map<State, State> parentMap, State goal) {
        List<State> path = new ArrayList<>();
        for (State at = goal; at != null; at = parentMap.get(at))
            path.add(at);
        Collections.reverse(path);
        return path;
    }

    public List<State> dfs() {
        Stack<State> stack = new Stack<>();
        Set<State> visited = new HashSet<>();
        Map<State, State> parentMap = new HashMap<>();
        stack.push(new State(0, 0, "Initial State"));
        visited.add(new State(0, 0, "Initial State"));
        while (!stack.isEmpty()) {
            State current = stack.pop();
            dfsStates++;
            if (current.jug1 == TARGET || current.jug2 == TARGET)
                return constructPath(parentMap, current);
            for (State neighbor : getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }
        return Collections.emptyList();
    }

    private static void printResult(List<State> result, int statesExplored) {
        if (result.isEmpty()) {
            System.out.println("No solution found.");
        } else {
            for (State state : result) {
                System.out.println(state);
            }
            System.out.println("Total states explored: " + statesExplored);
        }
    }
}
