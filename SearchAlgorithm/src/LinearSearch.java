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
    private int[][] keys = {{2, 30, 42, 26, 13, 25, 23, 16, 1, 5, 12, 18, 35, 33, 61, 21, 44, 28, 42, 10, 14, 20, 42, 12, 19, 26, 17, 29, 33, 42},
            {39, 160, 400, 184, 254, 36, 58, 38, 126, 427, 327, 378, 31, 188, 234, 286, 345, 74, 150, 407, 69, 181, 203, 309, 367, 17, 341, 347, 113, 390},
            {4005, 333, 3023, 2855, 4695, 2399, 591, 3322, 788, 1278, 3866, 3000, 116, 1201, 4674, 350, 1686, 529, 974, 393, 3308, 2967, 4603, 3967, 479, 841, 4503, 836, 3677, 1895}};
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
                    checkKey(0);
                    break outerloop;
                case 2:
                    br = findFile("500 Unsort.csv");
                    checkKey(1);
                    break outerloop;
                case 3:
                    br = findFile("5000 Unsort.csv");
                    checkKey(2);
                    break outerloop;
                default:
                    System.out.println("Not in the choices");
            }
        }
        stats.summarizeStats();
    }

    private void checkKey(int keyIndex) {
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


        for (int i = 0; i < 30; i++) {
            int currentKeyIndex = keys[keyIndex][i];
            System.out.print((i + 1) + ".\t Key: " + currentKeyIndex + " \t| ");

            long START = System.nanoTime();
            int index = linearSearch(currentKeyIndex);
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