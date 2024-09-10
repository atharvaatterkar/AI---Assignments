import java.io.*;
import java.util.*;

public class EightPuzzleBFS {

    private static final int SIZE = 9;

    // Function to find the location of the blank tile (0)
    private static int blankTileLocation(List<Integer> N) {
        for (int i = 0; i < SIZE; i++) {
            if (N.get(i) == 0) return i;
        }
        return -1;
    }

    // Move blank tile up
    private static List<Integer> actionMoveUp(int b, List<Integer> N) {
        if (b > 2) {
            Collections.swap(N, b, b - 3);
        }
        return N;
    }

    // Move blank tile down
    private static List<Integer> actionMoveDown(int b, List<Integer> N) {
        if (b < 6) {
            Collections.swap(N, b, b + 3);
        }
        return N;
    }

    // Move blank tile left
    private static List<Integer> actionMoveLeft(int b, List<Integer> N) {
        if (b != 0 && b != 3 && b != 6) {
            Collections.swap(N, b, b - 1);
        }
        return N;
    }

    // Move blank tile right
    private static List<Integer> actionMoveRight(int b, List<Integer> N) {
        if (b != 2 && b != 5 && b != 8) {
            Collections.swap(N, b, b + 1);
        }
        return N;
    }

    // Convert the state to a string for easier comparison and storage
    private static String vecToString(List<Integer> N) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            result.append(N.get(i)).append(" ");
        }
        return result.toString().trim();
    }

    // Breadth-First Search (BFS) to solve the 8-puzzle
    private static boolean bfs(List<Integer> start) {
        // The goal state of the 8-puzzle
        List<Integer> goal = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 0);

        Queue<List<Integer>> frontier = new LinkedList<>();  // Queue for BFS
        Map<String, String> parent = new HashMap<>();         // Map to store parent states
        Map<String, Character> moveMade = new HashMap<>();    // Map to store the move made to reach the state
        parent.put(vecToString(start), "");
        frontier.add(start);

        // To write explored nodes to file
        try (BufferedWriter exploredFile = new BufferedWriter(new FileWriter("Nodes.txt"))) {
            while (!frontier.isEmpty()) {
                List<Integer> current = frontier.poll();

                if (current.equals(goal)) {  // Goal found
                    exploredFile.write(vecToString(current) + '\n');
                    return true;
                }

                int blankPos = blankTileLocation(current);
                List<Integer> up = actionMoveUp(blankPos, new ArrayList<>(current));
                List<Integer> down = actionMoveDown(blankPos, new ArrayList<>(current));
                List<Integer> left = actionMoveLeft(blankPos, new ArrayList<>(current));
                List<Integer> right = actionMoveRight(blankPos, new ArrayList<>(current));

                String currentState = vecToString(current);
                String upState = vecToString(up);
                if (!up.equals(current) && !parent.containsKey(upState)) {
                    parent.put(upState, currentState);
                    moveMade.put(upState, 'U');
                    frontier.add(up);
                }
                String downState = vecToString(down);
                if (!down.equals(current) && !parent.containsKey(downState)) {
                    parent.put(downState, currentState);
                    moveMade.put(downState, 'D');
                    frontier.add(down);
                }
                String leftState = vecToString(left);
                if (!left.equals(current) && !parent.containsKey(leftState)) {
                    parent.put(leftState, currentState);
                    moveMade.put(leftState, 'L');
                    frontier.add(left);
                }
                String rightState = vecToString(right);
                if (!right.equals(current) && !parent.containsKey(rightState)) {
                    parent.put(rightState, currentState);
                    moveMade.put(rightState, 'R');
                    frontier.add(right);
                }

                exploredFile.write(vecToString(current) + '\n');  // Write explored nodes to file
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> start = new ArrayList<>(SIZE);

        System.out.println("Enter the start state row-wise (e.g. 1 2 3 0 4 5 8 6 7): ");
        for (int i = 0; i < SIZE; i++) {
            start.add(scanner.nextInt());
        }

        if (bfs(start)) {
            System.out.println("Solution found!");
        } else {
            System.out.println("No solution exists for this initial state.");
        }

        scanner.close();
    }
}
