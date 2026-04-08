// ============================================================
// MP01 - Load Dataset and Display Total Records
// Programmer: EYAS, SOPHIA WELYNNE S.
// Course: Programming 2 | BSIT GD - 1st Year
// Description: This program reads a CSV file, loads the
//              dataset, and displays the total number of
//              records (data rows) found in the file.
// ============================================================

import java.io.*;        // For reading files
import java.util.*;      // For Scanner

public class MP01 {

    public static void main(String[] args) {

        // Scanner is used to get keyboard input from the user
        Scanner scanner = new Scanner(System.in);

        // Ask the user to type the path to the CSV file
        System.out.print("Enter the CSV dataset file path: ");
        String filePath = scanner.nextLine(); // Store what the user typed

        // 'totalRecords' counts all valid data rows found after the header
        int totalRecords = 0;

        // 'totalLines' counts every line in the file including blanks and metadata
        int totalLines = 0;

        // 'headerFound' becomes true once we locate the real column header row
        boolean headerFound = false;

        // --- Start reading the file ---
        // BufferedReader reads the file line by line efficiently
        // FileReader opens the actual file from the path the user gave
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line; // 'line' holds the current line being read

            // Keep reading lines until there are no more (null means end of file)
            while ((line = br.readLine()) != null) {
                totalLines++; // Count every line we read

                // Skip completely blank lines (lines with no content at all)
                if (line.trim().isEmpty()) continue;

                // Split the line into individual values using comma as separator
                String[] columns = line.split(",", -1);

                // --- Find the real header row ---
                // We look for the row that starts with "Candidate"
                // That is the actual column header row in this dataset
                if (!headerFound && columns[0].trim().equalsIgnoreCase("Candidate")) {
                    headerFound = true; // Mark that we found the header
                    continue;          // Skip the header line itself
                }

                // --- Count only rows that come AFTER the header ---
                // Also skip rows where the first column is empty (metadata/footer rows)
                if (headerFound && !columns[0].trim().isEmpty()) {
                    totalRecords++; // This is a valid data record
                }
            }

            // --- Display the results ---
            System.out.println("\n============================================================");
            System.out.println("          MP01 - LOAD DATASET AND DISPLAY TOTAL RECORDS");
            System.out.println("============================================================");
            System.out.println("File            : " + filePath);
            System.out.println("Total Lines Read: " + totalLines);
            System.out.println("------------------------------------------------------------");
            System.out.println("  >> Total Records in Dataset: " + totalRecords);
            System.out.println("============================================================");

        } catch (FileNotFoundException e) {
            // This error happens if the file path is wrong or file does not exist
            System.out.println("ERROR: File not found. Please check the file path.");
        } catch (IOException e) {
            // This catches any other file reading errors
            System.out.println("ERROR: Could not read the file. " + e.getMessage());
        }

        scanner.close(); // Always close the scanner when done
    }
}