import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class LinearSearch {
    private Scanner in = new Scanner(System.in);
    private ArrayList<Integer> intList = new ArrayList<>();
    private final long[] timeArray = new long[30];
    BufferedReader br;

    public LinearSearch() {
        Statistics stats = new Statistics(timeArray);
        System.out.println("=".repeat(10) + " LINEAR SEARCH " + "=".repeat(10));
        System.out.println("[1] 50 ITEMS\n[2] 500 ITEMS\n[3] 5000 ITEMS");
        outerloop:
        while (true) {
            System.out.print("Choose numbers of data units >>: ");
            int userChoice = Integer.parseInt(in.nextLine());
            switch (userChoice) {
                case 1:
                    br = findFile("50 Unsort.csv");
                    checkKey(50);
                    break outerloop;
                case 2:
                    br = findFile("500 Unsort.csv");
                    checkKey(500);
                    break outerloop;
                case 3:
                    br = findFile("5000 Unsort.csv");
                    checkKey(5000);
                    break outerloop;
                default:
                    System.out.println("Not in the choices");
            }
        }
        stats.summarizeStats();
    }

    private void checkKey(int randomBound) {
        try {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    intList.add(Integer.parseInt(line));
                } catch (NumberFormatException ae) {
                    br.readLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Random generator = new Random();

        for (int i = 0; i < 5; i++) {
            int dummyKey = generator.nextInt(randomBound) + 1;
            linearSearch(dummyKey);
        }

        for (int i = 0; i < 30; i++) {
            int key2 = generator.nextInt(randomBound) + 1;
            System.out.print((i + 1) + ".\t Key: " + key2 + " \t| ");

            long START = System.nanoTime();
            int index = linearSearch(key2);
            long END = System.nanoTime();

            long TIME_RESULT = END - START;
            timeArray[i] = TIME_RESULT;

            String result = (index != -1) ? "Key found" : "Key not existing";
            System.out.println((TIME_RESULT) + " ns: " + result);
        }
    }

    private int linearSearch(int key) {
        for (int integer : intList) {
            if (key == integer) {
                return integer;
            }
        }
        return -1;
    }

    private BufferedReader findFile(String fileName) {
        try {
            return new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return null;
        }
    }
}