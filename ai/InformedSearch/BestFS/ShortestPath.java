package InformedSearch.BestFS;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

public class ShortestPath {
    private static Stack<Integer> st = new Stack<>();

    public static class State implements Comparable<State> {
        int index;
        int heuristics;
        State parent;
        int cost;

        public State(int index, int heuristics, State parent, int cost) {
            this.index = index;
            this.heuristics = heuristics;
            this.parent = parent;
            this.cost = cost;
        }

        @Override
        public int compareTo(State o) {
            return this.heuristics - o.heuristics;
        }
    }

    // Best First Search (BFS) function
    public static int bfs(int adjMatrix[][], int s, int d, HashMap<Integer, Integer> heuristicMap) {
        PriorityQueue<State> openQueue = new PriorityQueue<>();
        HashSet<Integer> closedSet = new HashSet<>();
        int h = heuristicMap.get(s);
        openQueue.add(new State(s, h, null, 0));
        int pathCost = 0;

        while (!openQueue.isEmpty()) {
            State currentState = openQueue.poll();
            int currentIndex = currentState.index;

            if (currentIndex == d) {
                System.out.println("Destination reached!");
                State temp = currentState;
                while (temp != null) {
                    st.push(temp.index);
                    temp = temp.parent;
                }
                System.out.print("Route is: ");
                while (!st.isEmpty()) {
                    System.out.print(st.pop() + " ");
                }
                return currentState.cost;
            }

            if (closedSet.contains(currentIndex)) {
                continue;
            }

            closedSet.add(currentIndex);
            for (int j = 0; j < adjMatrix.length; j++) {
                if (adjMatrix[currentIndex][j] != 0 && adjMatrix[currentIndex][j] != 9999) {
                    int heu = heuristicMap.get(j);
                    openQueue.add(new State(j, heu, currentState, currentState.cost + adjMatrix[currentIndex][j]));
                }
            }
        }

        return pathCost;
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

        // Heuristic values (straight line distances from each city to node 7)
        HashMap<Integer, Integer> heuristicMap = new HashMap<>();
        heuristicMap.put(0, 366);
        heuristicMap.put(1, 374);
        heuristicMap.put(2, 380);
        heuristicMap.put(3, 253);
        heuristicMap.put(4, 193);
        heuristicMap.put(5, 178);
        heuristicMap.put(6, 98);
        heuristicMap.put(7, 0);

        int cost = bfs(adjMatrix, 0, 7, heuristicMap);
        System.out.println();
        System.out.println("Path cost is: " + cost);
    }
}
