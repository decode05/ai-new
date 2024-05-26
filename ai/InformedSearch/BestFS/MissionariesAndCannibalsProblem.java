package InformedSearch.BestFS;

import java.util.*;

public class MissionariesAndCannibalsProblem {

    private static final int MISSIONARIES = 3, CANNIBALS = 3;

    public static void main(String[] args) {
        MissionariesAndCannibalsProblem problem = new MissionariesAndCannibalsProblem();
        System.out.println("Best First Search:");
        printResult(problem.bestFirstSearch(), problem.bfsStates);
    }

    static class State {
        int missionariesLeft, cannibalsLeft, missionariesRight, cannibalsRight;
        boolean isBoatOnLeft;
        String action;

        State(int missionariesLeft, int cannibalsLeft, int missionariesRight, int cannibalsRight, boolean isBoatOnLeft,
                String action) {
            this.missionariesLeft = missionariesLeft;
            this.cannibalsLeft = cannibalsLeft;
            this.missionariesRight = missionariesRight;
            this.cannibalsRight = cannibalsRight;
            this.isBoatOnLeft = isBoatOnLeft;
            this.action = action;
        }
    }

    private PriorityQueue<State> priorityQueue;
    private int bfsStates = 0;

    private List<State> getNeighbors(State state) {
        List<State> neighbors = new ArrayList<>();
        int missionariesLeft = state.missionariesLeft, cannibalsLeft = state.cannibalsLeft;
        int missionariesRight = state.missionariesRight, cannibalsRight = state.cannibalsRight;
        boolean isBoatOnLeft = state.isBoatOnLeft;

        if (isBoatOnLeft) {
            // Boat going from left to right
            for (int m = 0; m <= 2; m++) {
                for (int c = 0; c <= 2 - m; c++) {
                    if (m + c >= 1 && m + c <= 2) {
                        int newMissionariesLeft = missionariesLeft - m;
                        int newCannibalsLeft = cannibalsLeft - c;
                        int newMissionariesRight = missionariesRight + m;
                        int newCannibalsRight = cannibalsRight + c;
                        if (isValidState(newMissionariesLeft, newCannibalsLeft) &&
                                isValidState(newMissionariesRight, newCannibalsRight)) {
                            neighbors.add(new State(newMissionariesLeft, newCannibalsLeft,
                                    newMissionariesRight, newCannibalsRight,
                                    false, "Move " + m + " missionaries and " + c + " cannibals from left to right"));
                        }
                    }
                }
            }
        } else {
            // Boat going from right to left
            for (int m = 0; m <= 2; m++) {
                for (int c = 0; c <= 2 - m; c++) {
                    if (m + c >= 1 && m + c <= 2) {
                        int newMissionariesLeft = missionariesLeft + m;
                        int newCannibalsLeft = cannibalsLeft + c;
                        int newMissionariesRight = missionariesRight - m;
                        int newCannibalsRight = cannibalsRight - c;
                        if (isValidState(newMissionariesLeft, newCannibalsLeft) &&
                                isValidState(newMissionariesRight, newCannibalsRight)) {
                            neighbors.add(new State(newMissionariesLeft, newCannibalsLeft,
                                    newMissionariesRight, newCannibalsRight,
                                    true, "Move " + m + " missionaries and " + c + " cannibals from right to left"));
                        }
                    }
                }
            }
        }
        return neighbors;
    }

    private boolean isValidState(int missionaries, int cannibals) {
        if ((missionaries > 0 && missionaries < cannibals) ||
                (MISSIONARIES - missionaries > 0 && MISSIONARIES - missionaries < CANNIBALS - cannibals))
            return false;
        return missionaries >= 0 && missionaries <= MISSIONARIES && cannibals >= 0 && cannibals <= CANNIBALS;
    }

    private List<State> constructPath(Map<State, State> parentMap, State goal) {
        List<State> path = new ArrayList<>();
        for (State at = goal; at != null; at = parentMap.get(at))
            path.add(at);
        Collections.reverse(path);
        return path;
    }

    public List<State> bestFirstSearch() {
        priorityQueue = new PriorityQueue<>(Comparator.comparingInt(this::heuristic));
        Set<State> visited = new HashSet<>();
        Map<State, State> parentMap = new HashMap<>();
        State initialState = new State(MISSIONARIES, CANNIBALS, 0, 0, true, "Initial State");
        priorityQueue.add(initialState);
        visited.add(initialState);
        while (!priorityQueue.isEmpty()) {
            State current = priorityQueue.poll();
            bfsStates++;
            if (current.missionariesLeft == 0 && current.cannibalsLeft == 0)
                return constructPath(parentMap, current);
            for (State neighbor : getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    priorityQueue.add(neighbor);
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }
        return Collections.emptyList();
    }

    private int heuristic(State state) {
        return (MISSIONARIES + CANNIBALS) - (state.missionariesRight + state.cannibalsRight);
    }

    private static void printResult(List<State> result, int statesExplored) {
        if (result.isEmpty()) {
            System.out.println("No solution found.");
        } else {
            for (int i = 0; i < result.size(); i++) {
                State state = result.get(i);
                System.out.println("Step " + (i + 1) + ": " + state.action);
            }
            System.out.println("Total steps: " + result.size());
            System.out.println("Total states explored: " + statesExplored);
        }
    }
}
