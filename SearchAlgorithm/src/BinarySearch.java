import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class BinarySearch {
    private Scanner in = new Scanner(System.in);
    private ArrayList<Integer> intList = new ArrayList<>();
    private final long[] timeArray = new long[30];
    private long TIME_RESULT;
    BufferedReader br;

    public BinarySearch() {
        Statistics stats = new Statistics(timeArray);
        System.out.println("=".repeat(10) + " BINARY SEARCH " + "=".repeat(10));
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
                    System.err.println("Not in the choices.");
            }
        }
        stats.summarizeStats(TIME_RESULT);
    }

    private void insertionSort(ArrayList<Integer> inputArray) {
        long START = System.nanoTime();

        for (int i = 1; i < inputArray.size(); i++) {
            int currentValue = inputArray.get(i);
            int j = i - 1;
            while (j >= 0 && inputArray.get(j) > currentValue) {
                inputArray.set(j + 1, inputArray.get(j));
                j--;
            }
            inputArray.set(j + 1, currentValue);
        }
        long END = System.nanoTime();
        TIME_RESULT = END - START;
    }

    private void checkKey(int randomBound) {
        Random generator = new Random();
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
        insertionSort(intList);
        for (int i = 0; i < 5; i++) {
            int dummyKey = generator.nextInt(randomBound) + 1;
            binarySearch(dummyKey);
        }

        for (int i = 0; i < 30; i++) {
            int key2 = generator.nextInt(randomBound) + 1;
            System.out.print((i + 1) + ".\t Key: " + key2 + " \t| ");

            long START = System.nanoTime();
            int index = binarySearch(key2);
            long END = System.nanoTime();

            long TIME_RESULT = END - START;
            timeArray[i] = TIME_RESULT;

            String result = (index != -1) ? "Key found" : "Key not existing";
            System.out.println((TIME_RESULT) + " ns: " + result);
        }
    }

    private int binarySearch(int key) {
        int left = 0;
        int right = intList.size() - 1;

        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (intList.get(middle) == key) {
                return middle;
            }
            if (intList.get(middle) < key) {
                left = middle + 1;
            } else {
                right = middle - 1;
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
