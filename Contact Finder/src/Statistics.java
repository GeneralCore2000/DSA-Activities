import java.util.ArrayList;
import java.util.Scanner;

public class Statistics {
    private final Scanner in = new Scanner(System.in);
    private final int totalNumberOfContacts;
    private final ArrayList<ArrayList<String>> contactRecords;
    private final int minimumID;
    private final int maximumID;
    private final int linearSearchCount;
    private final int binarySearchCount;
    private final String recentKeyValue;
    private final boolean recentKeyResult;
    private boolean isBinarySearchLastUsed;
    private ArrayList<String> uniqueNames = new ArrayList<>();

    public Statistics(ArrayList<ArrayList<String>> contactRecords, int linearSearchCount, int binarySearchCount,
                      String recentKeyValue, boolean recentKeyResult, boolean isBinarySearchLastUsed) {
        this.contactRecords = contactRecords;
        this.linearSearchCount = linearSearchCount;
        this.binarySearchCount = binarySearchCount;
        this.recentKeyValue = (recentKeyValue == null) ? "" : recentKeyValue;
        this.recentKeyResult = recentKeyResult;
        this.isBinarySearchLastUsed = isBinarySearchLastUsed;
        uniqueNames = compileUniqueFirstNames();
        totalNumberOfContacts = contactRecords.size();
        minimumID = getMinimumID();
        maximumID = getMaximumID();
    }

    public void summarizeStats() {
        System.out.println("\n" + "═".repeat(50));
        System.out.println("📊 CONTACT RECORDS STATISTICS");
        System.out.println("═".repeat(50));
        System.out.printf("Total Contacts       : %d%n", totalNumberOfContacts);
        System.out.printf("Sorted by ID         : %b%n", isSorted());
        System.out.printf("Minimum ID           : %d%n", minimumID);
        System.out.printf("Maximum ID           : %d%n", maximumID);
        System.out.printf("Linear Searches Done : %d%n", linearSearchCount);
        System.out.printf("Binary Searches Done : %d%n", binarySearchCount);
        System.out.printf("Last Search Result   : %s (%s)%n", recentKeyResult, recentKeyValue);
        System.out.println("─".repeat(50));
    }

    public void menu() {
        while (true) {
            System.out.println("\n[0] 🔙 Go Back");
            System.out.println("[1] 🧾 Unique Names & Frequency");
            System.out.print("Enter choice >> ");
            int choice = Integer.parseInt(in.nextLine());
            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> displayNameStats();
                default -> System.out.println("⚠️ Invalid option. Try again.");
            }
        }
    }

    public void trackMetrics() {
        System.out.println("\n" + "═".repeat(50));
        System.out.println("⚙️  SEARCH METRICS");
        System.out.println("═".repeat(50));
        if (isBinarySearchLastUsed) {
            System.out.println("Algorithm Used   : Binary Search");
            System.out.println("Reason           : Best for sorted dataset, and is efficient.");
            System.out.println("Comparisons Made : " + Main.binaryComparisonCount);
            System.out.println("Execution Time   : " + Main.binarySearchTime + " ns");
        } else {
            System.out.println("Algorithm Used   : Linear Search");
            System.out.println("Reason           : Names are unsorted.");
            System.out.println("Comparisons Made : " + Main.linearComparisonCount);
            System.out.println("Execution Time   : " + Main.linearSearchTime + " ns");
        }
        System.out.println("Found Index      : " + (Main.lastFoundIndex == -1 ? "N/A" : Main.lastFoundIndex));
        System.out.println("Found Value      : " + (Main.lastFoundValue.isEmpty() ? "N/A" : Main.lastFoundValue));
        System.out.println("─".repeat(50));
    }

    private void displayNameStats() {
        System.out.println("\n" + "═".repeat(50));
        System.out.println("🧾 UNIQUE NAMES & FREQUENCY");
        System.out.println("═".repeat(50));
        ArrayList<String> uniqueNames = compileUniqueFirstNames();
        ArrayList<Integer> nameCounts = countFirstNameFrequency();
        bubbleSort(uniqueNames, nameCounts);
        for (int i = 0; i < uniqueNames.size(); i++) {
            System.out.printf("%-3d %-15s (%dx)%n", i + 1, uniqueNames.get(i), nameCounts.get(i));
        }
        System.out.println("─".repeat(50));
    }

    private ArrayList<Integer> countFirstNameFrequency() {
        ArrayList<Integer> nameCountList = new ArrayList<>();

        for (String name : uniqueNames) {
            int count = 0;
            for (ArrayList<String> contactRecord : contactRecords) {
                String fullName = contactRecord.get(1);
                int spaceIndex = fullName.indexOf(' ');
                String firstName = (spaceIndex != -1) ? fullName.substring(0, spaceIndex) : fullName;

                if (firstName.equals(name)) {
                    count++;
                }
            }
            nameCountList.add(count);
        }

        return nameCountList;
    }

    private ArrayList<String> compileUniqueFirstNames() {
        ArrayList<String> uniqueNamesList = new ArrayList<>();

        for (ArrayList<String> contactRecord : contactRecords) {
            String fullName = contactRecord.get(1);
            int spaceIndex = fullName.indexOf(' ');
            String firstName = (spaceIndex != -1) ? fullName.substring(0, spaceIndex) : fullName;

            if (!uniqueNamesList.contains(firstName)) {
                uniqueNamesList.add(firstName);
            }
        }

        return uniqueNamesList;
    }

    private boolean isSorted() {
        for (int i = 0; i < contactRecords.size() - 1; i++) {
            if (Integer.parseInt(contactRecords.get(i).getFirst()) > Integer.parseInt(contactRecords.get(i + 1).getFirst())) {
                return false;
            }
        }
        return true;
    }

    private int getMinimumID() {
        int minimumID = Integer.parseInt(contactRecords.getFirst().getFirst());
        for (int i = 1; i < contactRecords.size(); i++) {
            int currentIDIndex = Integer.parseInt(contactRecords.get(i).getFirst());
            if (currentIDIndex < minimumID) {
                minimumID = currentIDIndex;
            }
        }
        return minimumID;
    }

    private int getMaximumID() {
        int maximumID = Integer.parseInt(contactRecords.getFirst().getFirst());
        for (int i = 1; i < contactRecords.size(); i++) {
            int currentIDIndex = Integer.parseInt(contactRecords.get(i).getFirst());
            if (currentIDIndex > maximumID) {
                maximumID = currentIDIndex;
            }
        }
        return maximumID;
    }

    private void bubbleSort(ArrayList<String> unsortedArray, ArrayList<Integer> counts) {
        for (int i = 0; i < unsortedArray.size() - 1; i++) {
            for (int j = 0; j < unsortedArray.size() - 1; j++) {
                int firstLetter = unsortedArray.get(j).charAt(0);
                int secondLetter = unsortedArray.get(j + 1).charAt(0);

                if (firstLetter > secondLetter) {  //Swap if out of order
                    String temp = unsortedArray.get(j);
                    unsortedArray.set(j, unsortedArray.get(j + 1));
                    unsortedArray.set(j + 1, temp);

                    int tempCount = counts.get(j);
                    counts.set(j, counts.get(j + 1));
                    counts.set(j + 1, tempCount);
                }
            }
        }
    }


}
