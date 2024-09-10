import java.util.*;

public class EightPuzzleDFS {

    private static final int N = 3;

    // Define the goal state
    private static final int[][] goalState = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 0}
    };

    // Directions for moving the blank space (0)
    private static final Map<String, int[]> directions = new HashMap<String, int[]>() {{
        put("up", new int[]{-1, 0});
        put("down", new int[]{1, 0});
        put("left", new int[]{0, -1});
        put("right", new int[]{0, 1});
    }};

    // Check if the current state is the goal state
    private static boolean isGoal(int[][] state) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (state[i][j] != goalState[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Get the position of the blank (0)
    private static int[] getBlankPosition(int[][] state) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (state[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1}; // Should never happen
    }

    // Move the blank space in a specified direction
    private static int[][] move(int[][] state, String direction) {
        int[][] newState = new int[N][N];
        for (int i = 0; i < N; i++) {
            newState[i] = state[i].clone();
        }
        
        int[] blankPos = getBlankPosition(state);
        int i = blankPos[0], j = blankPos[1];
        int di = directions.get(direction)[0], dj = directions.get(direction)[1];
        int newI = i + di, newJ = j + dj;

        // Check if the move is valid
        if (newI >= 0 && newI < N && newJ >= 0 && newJ < N) {
            newState[i][j] = newState[newI][newJ];
            newState[newI][newJ] = 0;
            return newState;
        }
        return null;
    }

    // Convert a state to a string for easier comparison
    private static String stateToString(int[][] state) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : state) {
            for (int tile : row) {
                sb.append(tile).append(" ");
            }
        }
        return sb.toString().trim();
    }

    // DFS function to solve the puzzle
    private static List<int[][]> dfs(int[][] initialState) {
        Stack<int[][]> stack = new Stack<>();
        Set<String> visited = new HashSet<>();
        Map<String, int[][]> parentMap = new HashMap<>();

        stack.push(initialState);
        visited.add(stateToString(initialState));
        parentMap.put(stateToString(initialState), null);

        while (!stack.isEmpty()) {
            int[][] currentState = stack.pop();

            if (isGoal(currentState)) {
                List<int[][]> path = new ArrayList<>();
                int[][] state = currentState;
                while (state != null) {
                    path.add(state);
                    state = parentMap.get(stateToString(state));
                }
                Collections.reverse(path);
                return path;
            }

            // Explore all four possible directions
            for (String dir : directions.keySet()) {
                int[][] newState = move(currentState, dir);
                if (newState != null && !visited.contains(stateToString(newState))) {
                    visited.add(stateToString(newState));
                    parentMap.put(stateToString(newState), currentState);
                    stack.push(newState);
                }
            }
        }

        return Collections.emptyList(); // Return an empty list if no solution is found
    }

    // Print the path to the solution
    private static void printPath(List<int[][]> path) {
        for (int[][] state : path) {
            for (int[] row : state) {
                for (int tile : row) {
                    System.out.print(tile + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // Example initial state
        int[][] initialState = {
            {1, 2, 3},
            {4, 0, 6},
            {7, 5, 8}
        };

        List<int[][]> path = dfs(initialState);

        if (!path.isEmpty()) {
            printPath(path);
        } else {
            System.out.println("No solution found.");
        }
    }
}
