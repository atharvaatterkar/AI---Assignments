import java.util.Scanner;

public class MissionaryCannibal {

    private int[] left = new int[3];  // [missionaries, cannibals, boat]
    private int[] right = new int[3]; // [missionaries, cannibals, boat]

    public MissionaryCannibal() {
        left[0] = 3;  // 3 missionaries on the left
        left[1] = 3;  // 3 cannibals on the left
        left[2] = 1;  // boat on the left side
        right[0] = 0; // 0 missionaries on the right
        right[1] = 0; // 0 cannibals on the right
        right[2] = 0; // no boat on the right side
    }

    public void displayState() {
        System.out.println("Left side: " + left[0] + " Missionaries, " + left[1] + " Cannibals, Boat: " 
                           + (left[2] == 1 ? "Yes" : "No"));
        System.out.println("Right side: " + right[0] + " Missionaries, " + right[1] + " Cannibals, Boat: " 
                           + (right[2] == 1 ? "Yes" : "No"));
    }

    public boolean move(int missionaries, int cannibals, boolean toRight) {
        if (missionaries + cannibals > 2 || missionaries < 0 || cannibals < 0 || (missionaries == 0 && cannibals == 0)) {
            return false;
        }

        if (toRight) {
            if (left[0] >= missionaries && left[1] >= cannibals && left[2] == 1) {
                left[0] -= missionaries;
                left[1] -= cannibals;
                right[0] += missionaries;
                right[1] += cannibals;
                left[2] = 0;
                right[2] = 1;
                return true;
            }
        } else {
            if (right[0] >= missionaries && right[1] >= cannibals && right[2] == 1) {
                right[0] -= missionaries;
                right[1] -= cannibals;
                left[0] += missionaries;
                left[1] += cannibals;
                right[2] = 0;
                left[2] = 1;
                return true;
            }
        }

        return false;
    }

    public boolean checkLose() {
        if ((left[0] > 0 && left[1] > left[0]) || (right[0] > 0 && right[1] > right[0])) {
            return true;
        }
        return false;
    }

    public boolean win() {
        if (right[0] == 3 && right[1] == 3) {
            return true;
        }
        return false;
    }

    public boolean isBoatOnLeft() {
        return left[2] == 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MissionaryCannibal game = new MissionaryCannibal();

        while (true) {
            game.displayState();

            if (game.win()) {
                System.out.println("Congratulations! All missionaries and cannibals have successfully crossed the river.");
                break;
            }

            if (game.checkLose()) {
                System.out.println("You lose. Cannibals outnumber missionaries on one side.");
                break;
            }

            System.out.print("Enter number of missionaries to move: ");
            int missionaries = scanner.nextInt();
            System.out.print("Enter number of cannibals to move: ");
            int cannibals = scanner.nextInt();

            boolean toRight = game.isBoatOnLeft();  // Check if the boat is on the left side

            if (!game.move(missionaries, cannibals, toRight)) {
                System.out.println("Invalid move. Try again.");
            }
        }

        scanner.close();
    }
}
