package UninformedSearch;

import java.util.*;

public class WaterJugProblem {
    private static final int JUG1_CAPACITY = 3, JUG2_CAPACITY = 5, TARGET = 4;

    public static void main(String[] args) {
        WaterJugProblem problem = new WaterJugProblem();
        System.out.println("BFS:");
        printResult(problem.bfs(), problem.bfsStates);
        System.out.println("\nDFS:");
        printResult(problem.dfs(), problem.dfsStates);
        System.out.println("\nDLS:");
        printResult(problem.dls(10), problem.dlsStates);
        System.out.println("\nIDDFS:");
        printResult(problem.iddfs(), problem.iddfsStates);

        System.out.println("\nComparison:");
        System.out.println("BFS explored " + problem.bfsStates + " states.");
        System.out.println("DFS explored " + problem.dfsStates + " states.");
        System.out.println("DLS explored " + (problem.dlsStates - problem.iddfsStates) + " states.");
        System.out.println("IDDFS explored " + problem.iddfsStates + " states.");
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

    int bfsStates = 0, dfsStates = 0, dlsStates = 0, temp = 0, iddfsStates = 0;

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

    public List<State> bfs() {
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();
        Map<State, State> parentMap = new HashMap<>();
        State initialState = new State(0, 0, "Initial State");
        queue.add(initialState);
        visited.add(initialState);
        while (!queue.isEmpty()) {
            State current = queue.poll();
            bfsStates++;
            if (current.jug1 == TARGET || current.jug2 == TARGET)
                return constructPath(parentMap, current);
            for (State neighbor : getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }
        return Collections.emptyList();
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

    public List<State> dls(int limit) {
        return dlsRecursive(new State(0, 0, "Initial State"), new HashSet<>(), new HashMap<>(), limit);
    }

    private List<State> dlsRecursive(State current, Set<State> visited, Map<State, State> parentMap, int limit) {
        dlsStates++;
        temp++;
        if (current.jug1 == TARGET || current.jug2 == TARGET)
            return constructPath(parentMap, current);
        if (limit == 0)
            return Collections.emptyList();
        visited.add(current);
        for (State neighbor : getNeighbors(current)) {
            if (!visited.contains(neighbor)) {
                parentMap.put(neighbor, current);
                List<State> result = dlsRecursive(neighbor, visited, parentMap, limit - 1);
                if (!result.isEmpty())
                    return result;
            }
        }
        visited.remove(current);
        return Collections.emptyList();
    }

    public List<State> iddfs() {
        List<State> result;
        int depth;
        for (depth = 0;; depth++) {
            temp = 0;
            result = dls(depth);
            iddfsStates += temp;
            if (!result.isEmpty())
                return result;
        }
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
