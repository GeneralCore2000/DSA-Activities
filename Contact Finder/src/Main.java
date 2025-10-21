import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private BufferedReader br;
    private Scanner in = new Scanner(System.in);
    private ArrayList<ArrayList<String>> contactRecords = new ArrayList<>();
    private ArrayList<Statistics> statList = new ArrayList<>();
    private long TIME_RESULT;

    public Main() {
        try {
            br = new BufferedReader(new FileReader("Contact Records.csv"));
            addToArray();
        } catch (FileNotFoundException ae) {
            System.out.println("File not found");
            throw new RuntimeException();
        }
    }

    private void menu() {
        while (true) {
            System.out.println();
            System.out.println("=".repeat(10) + "Welcome to your contact list." + "=".repeat(10));
            System.out.println("[1] Search by ID (Binary over sorted IDs)");
            System.out.println("[2] Search by Name (Linear over names)");
            System.out.println("[3] Show Stats");
            System.out.println("[4] Exit");
            System.out.print("Enter choice >>: ");
            int userChoice = Integer.parseInt(in.nextLine());
            switch (userChoice) {
                case 1:
                    searchByID();
                    break;
                case 2:
                    searchByName();
                    break;
                case 3:
                    showStats();
                    break;
                case 4:
                    return;
            }
        }
    }

    private void searchByID() {
        insertionSort();
        System.out.print("Enter ID to find >>: ");
        int id = Integer.parseInt(in.nextLine());
        ArrayList<ArrayList<String>> findId = binarySearch(id);
        System.out.println("-".repeat(50));
        System.out.printf("%-5s | %-5s%n", findId.getFirst().getFirst(), findId.getFirst().get(1));
    }

    private void searchByName() {
        System.out.print("Enter name to find >>: ");
        String name = in.nextLine();
        ArrayList<ArrayList<String>> findName = linearSearch(name);
        System.out.println("\nFound " + (findName.size() - 1) + "x record(s) for name " + name);
        System.out.println("-".repeat(50));
        for (int i = 0; i < findName.size(); i++) {
            System.out.printf("%-5s %-5s | %-5s%n", (i + 1) + ".", findName.get(i).get(0), findName.get(i).get(1));
        }
    }

    private void showStats() {
        while (true) {
            System.out.println("=".repeat(10) + " STATISTICS MENU " + "=".repeat(10));
            System.out.println("[0] Go Back");
            System.out.println("[1] Search by ID stats (Binary Search)");
            System.out.println("[2] Search by Name stats (Linear Search)");
            System.out.print("Enter choice >>: ");
            int userChoice = Integer.parseInt(in.nextLine());
            switch (userChoice) {
                case 0:
                    return;
                case 1:
                    System.out.println("\n" + "=".repeat(10) + " STATISTICS " + "=".repeat(10));
                    for (Statistics stats : statList) {
                        if (stats.getSearchType().equals("Binary")) {
                            stats.summarizeStats(TIME_RESULT);
                        }
                    }
                    break;
                case 2:
                    System.out.println("\n" + "=".repeat(10) + " STATISTICS " + "=".repeat(10));
                    for (Statistics stats : statList) {
                        if (stats.getSearchType().equals("Linear")) {
                            stats.summarizeStats();
                        }
                    }
                    break;
            }
        }
    }

    private ArrayList<ArrayList<String>> binarySearch(int key) {
        ArrayList<Long> timeArray = new ArrayList<>();
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        int left = 0;
        int right = contactRecords.size() - 1;

        long TIME_START, TIME_END;
        TIME_START = System.nanoTime();
        while (left <= right) {
            int middle = left + (right - left) / 2;
            int middleId = Integer.parseInt(contactRecords.get(middle).getFirst());

            if (middleId == key) {
                result.add(contactRecords.get(middle));
                TIME_END = System.nanoTime();
                timeArray.add(TIME_END - TIME_START);
                statList.add(new Statistics(timeArray, "Binary", key + ""));
                return result;
            }
            if (middleId < key) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        TIME_END = System.nanoTime();
        timeArray.add(TIME_END - TIME_START);
        statList.add(new Statistics(timeArray, "Binary", key + ""));
        return result;
    }

    private ArrayList<ArrayList<String>> linearSearch(String key) {
        ArrayList<Long> timeArray = new ArrayList<>();
        ArrayList<ArrayList<String>> names = new ArrayList<>();
        long TIME_START, TIME_END;

        for (ArrayList<String> row : contactRecords) {
            TIME_START = System.nanoTime();
            boolean match = row.get(1).contains(key);
            TIME_END = System.nanoTime();
            timeArray.add(TIME_END - TIME_START);

            if (match) {
                names.add(row);
            }
        }
        statList.add(new Statistics(timeArray, "Linear", key));
        return names;
    }

    private void insertionSort() {
        long START = System.nanoTime();

        for (int i = 1; i < contactRecords.size(); i++) {
            ArrayList<String> currentRow = contactRecords.get(i);
            int currentValue = Integer.parseInt(currentRow.get(0));
            int j = i - 1;

            while (j >= 0 && Integer.parseInt(contactRecords.get(j).get(0)) > currentValue) {
                contactRecords.set(j + 1, contactRecords.get(j));
                j--;
            }
            contactRecords.set(j + 1, currentRow);
        }
        long END = System.nanoTime();
        TIME_RESULT = END - START;
    }

    private void addToArray() {
        try {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] tempLine = line.split(",");
                ArrayList<String> row = new ArrayList<>();
                row.add(tempLine[0]);
                row.add(tempLine[1]);
                contactRecords.add(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Main().menu();
    }
}