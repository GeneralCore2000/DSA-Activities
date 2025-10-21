import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private BufferedReader br;
    private Scanner in = new Scanner(System.in);
    private ArrayList<ArrayList<String>> contactRecords = new ArrayList<>();

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
        for (int i = 0; i < findName.size() - 1; i++) {
            System.out.printf("%-5s %-5s | %-5s%n", (i + 1) + ".", findName.get(i).get(0), findName.get(i).get(1));
        }
    }

    private ArrayList<ArrayList<String>> binarySearch(int key) {
        int left = 0;
        int right = contactRecords.size() - 1;
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        while (left <= right) {
            int middle = left + (right - left) / 2;
            int middleId = Integer.parseInt(contactRecords.get(middle).getFirst());

            if (middleId == key) {
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
        ArrayList<ArrayList<String>> names = new ArrayList<>();
        for (ArrayList<String> row : contactRecords) {
            if (row.get(1).contains(key)) {
                names.add(row);
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

    public static void main(String[] args) {
        new Main().menu();
    }
}