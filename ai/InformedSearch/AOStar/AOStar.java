package InformedSearch.AOStar;

import java.util.*;

public class AOStar {
    static class Node {
        String name;
        int heuristic;
        int cost;
        boolean isAndNode;
        List<Node> children;

        Node(String name, int heuristic, int cost, boolean isAndNode) {
            this.name = name;
            this.heuristic = heuristic;
            this.cost = cost;
            this.isAndNode = isAndNode;
            this.children = new ArrayList<>();
        }

        void addChild(Node child) {
            children.add(child);
        }
    }

    private Node startNode;
    private Node goalNode;

    public AOStar(Node startNode, Node goalNode) {
        this.startNode = startNode;
        this.goalNode = goalNode;
    }

    public int f(Node node) {
        return g(node) + h(node);
    }

    public int g(Node node) {
        return node.cost;
    }

    public int h(Node node) {
        return node.heuristic;
    }

    public void runAOStar(Node node) {
        if (node == goalNode) {
            return;
        }

        if (node.children.isEmpty()) {
            return;
        }

        if (node.isAndNode) {
            int sumCost = 0;
            for (Node child : node.children) {
                runAOStar(child);
                sumCost += f(child);
            }
            node.heuristic = sumCost;
        } else {
            Node bestChild = null;
            int minCost = Integer.MAX_VALUE;
            for (Node child : node.children) {
                runAOStar(child);
                int currentCost = f(child);
                if (currentCost < minCost) {
                    minCost = currentCost;
                    bestChild = child;
                }
            }
            node.heuristic = minCost;
            node.children.clear();
            if (bestChild != null) {
                node.addChild(bestChild);
            }
        }
    }

    public Node findPath() {
        runAOStar(startNode);
        return startNode;
    }

    public void printSolution(Node node, String indent) {
        if (node == null) {
            return;
        }
        System.out.println(indent + node.name + " (h=" + node.heuristic + ")");
        for (Node child : node.children) {
            printSolution(child, indent + "  ");
        }
    }

    public static void main(String[] args) {
        // Create nodes as per the given example
        Node A = new Node("A", Integer.MAX_VALUE, 0, false);
        Node B = new Node("B", 5, 1, false);
        Node C = new Node("C", 2, 1, true);
        Node D = new Node("D", 4, 1, false);
        Node E = new Node("E", 7, 1, false);
        Node F = new Node("F", 9, 1, false);
        Node G = new Node("G", 3, 1, false);
        Node H = new Node("H", 0, 1, false);
        Node I = new Node("I", 0, 1, false);
        Node J = new Node("J", 0, 1, false);

        // Build the AND-OR graph
        A.addChild(B);
        A.addChild(C);
        A.addChild(D);
        B.addChild(E);
        B.addChild(F);
        C.addChild(G);
        C.addChild(H);
        C.addChild(I);
        D.addChild(J);

        // Set goal node
        Node goalNode = J;

        // Create an instance of AOStar and find the path
        AOStar aoStar = new AOStar(A, goalNode);
        Node path = aoStar.findPath();

        // Print the solution path
        System.out.println("Solution path:");
        aoStar.printSolution(path, "");
    }
}
