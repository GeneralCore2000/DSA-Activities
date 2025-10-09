import java.util.Scanner;

public class Main {

    Scanner in = new Scanner(System.in);

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
                    new LinearSearch();
                    break ;
                case 2:
                    new BinarySearch();
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
