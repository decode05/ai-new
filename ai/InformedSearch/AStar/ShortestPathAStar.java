package InformedSearch.AStar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

public class ShortestPathAStar {
    private static Stack<Integer> st = new Stack<>();

    public static class State implements Comparable<State> {
        int index;
        int g; // Cost from start to this state
        int h; // Heuristic cost to goal
        State parent;

        public State(int index, int g, int h, State parent) {
            this.index = index;
            this.g = g;
            this.h = h;
            this.parent = parent;
        }

        public int f() {
            return this.g + this.h;
        }

        @Override
        public int compareTo(State other) {
            return this.f() - other.f();
        }
    }

    public static int aStarSearch(int adjMatrix[][], int start, int goal, HashMap<Integer, Integer> heuristics) {
        PriorityQueue<State> openSet = new PriorityQueue<>();
        HashSet<Integer> closedSet = new HashSet<>();

        openSet.add(new State(start, 0, heuristics.get(start), null));

        while (!openSet.isEmpty()) {
            State current = openSet.poll();

            if (current.index == goal) {
                System.out.println("Destination reached!");
                printPath(current);
                return current.g;
            }

            if (closedSet.contains(current.index)) {
                continue;
            }

            closedSet.add(current.index);

            for (int neighbor = 0; neighbor < adjMatrix.length; neighbor++) {
                if (adjMatrix[current.index][neighbor] != 0 && adjMatrix[current.index][neighbor] != 9999) {
                    int tentativeG = current.g + adjMatrix[current.index][neighbor];
                    int h = heuristics.get(neighbor);
                    State neighborState = new State(neighbor, tentativeG, h, current);

                    if (!closedSet.contains(neighbor)) {
                        openSet.add(neighborState);
                    }
                }
            }
        }
        return -1; // Path not found
    }

    private static void printPath(State goalState) {
        Stack<Integer> path = new Stack<>();
        State current = goalState;
        while (current != null) {
            path.push(current.index);
            current = current.parent;
        }
        System.out.print("Route is: ");
        while (!path.isEmpty()) {
            System.out.print(path.pop() + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int adjMatrix[][] = {
                { 0, 75, 9999, 140, 9999, 9999, 9999, 9999 },
                { 75, 0, 71, 9999, 9999, 9999, 9999, 9999 },
                { 9999, 71, 0, 151, 9999, 9999, 9999, 9999 },
                { 140, 9999, 151, 0, 80, 99, 9999, 9999 },
                { 9999, 9999, 9999, 80, 0, 9999, 97, 9999 },
                { 9999, 9999, 9999, 99, 9999, 0, 9999, 211 },
                { 9999, 9999, 9999, 9999, 97, 9999, 0, 101 },
                { 9999, 9999, 9999, 9999, 9999, 211, 101, 0 }
        };
        // Source - 0, Destination - 7
        HashMap<Integer, Integer> heuristics = new HashMap<>();
        // Heuristics (straight line distances from each city to 7)
        heuristics.put(0, 366);
        heuristics.put(1, 374);
        heuristics.put(2, 380);
        heuristics.put(3, 253);
        heuristics.put(4, 193);
        heuristics.put(5, 178);
        heuristics.put(6, 98);
        heuristics.put(7, 0);

        int cost = aStarSearch(adjMatrix, 0, 7, heuristics);
        System.out.println("Path cost is: " + cost);
    }
}
