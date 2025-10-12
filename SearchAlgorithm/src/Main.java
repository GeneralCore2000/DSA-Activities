import java.util.Scanner;

public class Main {

    Scanner in = new Scanner(System.in);
    private int[][] keys = {
            {16, 30, 42, 26, 13, 25, 23, 16, 1, 5, 12, 18, 35, 33, 61, 21, 44, 28, 42, 10, 14, 20, 42, 12, 19, 26, 17, 29, 33, 42},
            {39, 160, 400, 184, 254, 36, 58, 38, 126, 427, 327, 378, 31, 188, 234, 286, 345, 74, 150, 407, 69, 181, 203, 309, 367, 17, 341, 347, 113, 390},
            {4005, 333, 3023, 2855, 4695, 2399, 591, 3322, 788, 1278, 3866, 3000, 116, 1201, 4674, 350, 1686, 529, 974, 393, 3308, 2967, 4603, 3967, 479, 841, 4503, 836, 3677, 1895}};
    public Main() {
        outerloop:
        while (true) {
            System.out.println("-".repeat(20) + " SEARCH ALGORITHM " + "-".repeat(20));
            System.out.println("[0] Exit");
            System.out.println("[1] Linear Search");
            System.out.println("[2] Binary Search");
            System.out.print("Input choice >>: ");

            int userChoice;
            try {
                userChoice = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException ae) {
                System.err.println("Invalid input.");
                continue;
            }
            switch (userChoice) {
                case 0:
                    System.out.println("Good bye.");
                    break outerloop;
                case 1:
                    new LinearSearch(keys);
                    break ;
                case 2:
                    new BinarySearch(keys);
                    break ;
                default:
                    System.err.println("Invalid choice.");
            }
        }
    }

    static void main(String[] args) {
        new Main();
    }
}
