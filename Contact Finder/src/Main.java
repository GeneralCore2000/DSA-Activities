import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static int binaryComparisonCount = 0;
    public static int linearComparisonCount = 0;
    public static long binarySearchTime = 0;
    public static long linearSearchTime = 0;
    public static int lastFoundIndex = -1;
    public static String lastFoundValue = "";
    private BufferedReader br;
    private Scanner in = new Scanner(System.in);
    private ArrayList<ArrayList<String>> contactRecords = new ArrayList<>();
    private int binarySearchCount = 0;
    private int linearSearchCount = 0;
    private String recentKeyValue;
    private boolean recentKeyResult = false;
    private boolean isBinarySearchLastUsed = true;

    public Main() {
        try {
            br = new BufferedReader(new FileReader("Contact Records.csv"));
            addToArray();
        } catch (FileNotFoundException ae) {
            System.out.println("File not found");
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) {
        new Main().menu();
    }

    private void menu() {
        while (true) {
            System.out.println();
            System.out.println("═".repeat(50));
            System.out.println("📇  CONTACT MANAGEMENT SYSTEM");
            System.out.println("═".repeat(50));
            System.out.println("[1] 🔍 Search by ID (Binary Search)");
            System.out.println("[2] 🔎 Search by Name (Linear Search)");
            System.out.println("[3] 📊 Show Statistics");
            System.out.println("[4] 🚪 Exit");
            System.out.println("─".repeat(50));
            System.out.print("Enter choice >> ");
            int userChoice = Integer.parseInt(in.nextLine());

            switch (userChoice) {
                case 1 -> searchByID();
                case 2 -> searchByName();
                case 3 -> {
                    Statistics stats = new Statistics(contactRecords, linearSearchCount, binarySearchCount,
                            recentKeyValue, recentKeyResult, isBinarySearchLastUsed);
                    stats.summarizeStats();
                    stats.menu();
                }
                case 4 -> {
                    System.out.println("\n👋 Exiting program... Goodbye!");
                    return;
                }
                default -> System.out.println("⚠️ Invalid option. Try again.");
            }
        }
    }

    private void searchByID() {
        insertionSort();
        System.out.println("\n═ SEARCH BY ID ═");
        System.out.print("Enter ID to find >> ");
        int key = Integer.parseInt(in.nextLine());

        long start = System.nanoTime();
        ArrayList<ArrayList<String>> findId = binarySearch(key);
        long end = System.nanoTime();
        binarySearchTime = end - start;

        recentKeyValue = key + "";
        binarySearchCount++;
        System.out.println("─".repeat(50));
        if (findId == null) {
            System.out.println("❌ ID " + key + " not found.");
            recentKeyResult = false;
            return;
        }
        System.out.printf("%-10s | %-30s%n", "ID", "Name");
        System.out.println("─".repeat(50));
        System.out.printf("%-10s | %-30s%n", findId.getFirst().getFirst(), findId.getFirst().get(1));
        Statistics stats = new Statistics(contactRecords, linearSearchCount, binarySearchCount,
                recentKeyValue, recentKeyResult, isBinarySearchLastUsed);
        stats.trackMetrics();
        recentKeyResult = true;
    }

    private void searchByName() {

        System.out.println("\n═ SEARCH BY NAME ═");
        System.out.print("Enter name to find >> ");
        String name = in.nextLine();

        long start = System.nanoTime();
        ArrayList<ArrayList<String>> findName = linearSearch(name);
        long end = System.nanoTime();
        linearSearchTime = end - start;

        recentKeyValue = name;
        linearSearchCount++;

        System.out.println("\n" + "─".repeat(50));
        System.out.println("🔎 Found " + findName.size() + " record(s) for name: " + name);
        System.out.println("─".repeat(50));

        if (findName.isEmpty()) {
            System.out.println("❌ No matching name found.");
        } else {
            System.out.printf("%-5s %-10s | %-30s%n", "#", "ID", "Name");
            System.out.println("─".repeat(50));
            for (int i = 0; i < findName.size(); i++) {
                System.out.printf("%-5s %-10s | %-30s%n", (i + 1) + ".", findName.get(i).get(0), findName.get(i).get(1));
            }
        }
        Statistics stats = new Statistics(contactRecords, linearSearchCount, binarySearchCount,
                recentKeyValue, recentKeyResult, isBinarySearchLastUsed);
        stats.trackMetrics();
        recentKeyResult = !findName.isEmpty();
    }

    private ArrayList<ArrayList<String>> binarySearch(int key) {
        isBinarySearchLastUsed = true;
        int left = 0;
        int right = contactRecords.size() - 1;
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        binaryComparisonCount = 0;
        lastFoundIndex = -1;
        lastFoundValue = "";

        while (left <= right) {
            int middle = left + (right - left) / 2;
            binaryComparisonCount++;
            int middleId = Integer.parseInt(contactRecords.get(middle).getFirst());

            if (middleId == key) {
                lastFoundIndex = middle;
                lastFoundValue = contactRecords.get(middle).get(1);
                result.add(contactRecords.get(middle));
                return result;
            }

            if (middleId < key) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return null;
    }

    private ArrayList<ArrayList<String>> linearSearch(String key) {
        isBinarySearchLastUsed = false;
        ArrayList<ArrayList<String>> names = new ArrayList<>();
        linearComparisonCount = 0;
        lastFoundIndex = -1;
        lastFoundValue = "";

        for (int i = 0; i < contactRecords.size(); i++) {
            linearComparisonCount++;
            String fullName = contactRecords.get(i).get(1);
            String firstName = fullName.substring(0, fullName.indexOf(' '));

            if (firstName.equalsIgnoreCase(key) || fullName.equalsIgnoreCase(key)) {
                names.add(contactRecords.get(i));
                lastFoundIndex = i;
                lastFoundValue = fullName;
            }
        }
        return names;
    }

    private void insertionSort() {
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
}
