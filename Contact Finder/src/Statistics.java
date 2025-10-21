import java.util.ArrayList;
import java.util.Scanner;

public class Statistics {
    private final Scanner in = new Scanner(System.in);
    private final int totalNumberOfContacts;
    private final ArrayList<ArrayList<String>> contactRecords;
    private ArrayList<String> uniqueNames = new ArrayList<>();
    private final int minimumID;
    private final int maximumID;
    private final int linearSearchCount;
    private final int binarySearchCount;
    private final String recentKeyValue;
    private final boolean recentKeyResult;

    public Statistics(ArrayList<ArrayList<String>> contactRecords, int linearSearchCount, int binarySearchCount,
                      String recentKeyValue, boolean recentKeyResult) {
        this.contactRecords = contactRecords;
        this.linearSearchCount = linearSearchCount;
        this.binarySearchCount = binarySearchCount;
        this.recentKeyValue = recentKeyValue;
        this.recentKeyResult = recentKeyResult;
        uniqueNames = compileUniqueFirstNames();
        totalNumberOfContacts = contactRecords.size();
        minimumID = getMinimumID();
        maximumID = getMaximumID();

    }

    public void summarizeStats() {
        System.out.println("\n" + "=".repeat(20) + " CONTACT RECORDS STATISTICS " + "=".repeat(20));
        System.out.println("Total number of contacts: " + totalNumberOfContacts);
        System.out.println("Is contact records sorted: " + isSorted());
        System.out.println("\nMinimum ID: " + minimumID);
        System.out.println("Maximum ID: " + maximumID);
        System.out.println("\nLinear Searches Performed: " + linearSearchCount);
        System.out.println("Binary Searches Performed: " + binarySearchCount);
        System.out.println("\nLast search result: " + recentKeyResult + " (" + recentKeyValue + ")");
        System.out.println("-".repeat(68) + "\n");
    }

    public void menu() {
        System.out.println("[0] Go back");
        System.out.println("[1] See unique names and frequency");
        System.out.print("Enter choice >>: ");
        int choice = Integer.parseInt(in.nextLine());
        switch (choice) {
            case 0:
                return;
            case 1:
                System.out.println("\n" + "=".repeat(20) + " UNIQUE NAMES " + "=".repeat(20));
                displayNameStats();
                break;
        }
    }

    private void displayNameStats() {
        ArrayList<String> uniqueNames = compileUniqueFirstNames();
        ArrayList<Integer> nameCounts = countFirstNameFrequency();
        bubbleSort(uniqueNames);
        for (int i = 0; i < uniqueNames.size(); i++) {
            System.out.println((i + 1) + ". " + uniqueNames.get(i) + " (" + nameCounts.get(i) + "x)");
        }
    }

    private ArrayList<Integer> countFirstNameFrequency() {
        ArrayList<Integer> nameCountList = new ArrayList<>();
        for (String uniqueNames : uniqueNames) {
            int count = 0;
            for (ArrayList<String> contactRecords : contactRecords) {
                String fullName = contactRecords.get(1);
                int spaceIndex = fullName.indexOf(' ');
                String firstName = fullName.substring(0, spaceIndex);
                if (firstName.equals(uniqueNames)) {
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
            boolean existing = false;
            String fullName = contactRecord.get(1);
            int spaceIndex = fullName.indexOf(' ');

            String firstName = fullName.substring(0, spaceIndex);

            for (String name : uniqueNamesList) {
                if (firstName.equals(name)) {
                    existing = true;
                    break;
                }
            }
            if (!existing) {
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

    private void bubbleSort(ArrayList<String> unsortedArray) {
        for (int i = 0; i < unsortedArray.size() - 1; i++) {
            for (int j = 0; j < unsortedArray.size() - 1; j++) {
                int firstLetter = unsortedArray.get(j).charAt(0);
                int secondLetter = unsortedArray.get(j + 1).charAt(0);

                if (firstLetter > secondLetter) {  //Swap if out of order
                    String temp = unsortedArray.get(j);
                    unsortedArray.set(j, unsortedArray.get(j + 1));
                    unsortedArray.set(j + 1, temp);
                }
            }
        }
    }


}
