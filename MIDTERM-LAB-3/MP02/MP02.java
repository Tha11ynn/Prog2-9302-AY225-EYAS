// ============================================================
// MP02 - Display First 10 Rows of Dataset
// Programmer: EYAS, SOPHIA WELYNNE S.
// Course: Programming 2 | BSIT GD - 1st Year
// Description: This program reads a CSV file and displays
//              the first 10 data rows in a readable format,
//              along with the column headers of the dataset.
// ============================================================

import java.io.*;        // For reading files
import java.util.*;      // For ArrayList and Scanner

public class MP02 {

    public static void main(String[] args) {

        // Scanner is used to get keyboard input from the user
        Scanner scanner = new Scanner(System.in);

        // Ask the user to type the path to the CSV file
        System.out.print("Enter the CSV dataset file path: ");
        String filePath = scanner.nextLine(); // Store what the user typed

        // This list will hold the first 10 data rows we find
        ArrayList<String[]> first10Rows = new ArrayList<>();

        // 'headerColumns' will store the column names from the header row
        String[] headerColumns = null;

        // 'dataRowCount' counts how many actual data rows we have collected
        int dataRowCount = 0;

        // --- Start reading the file ---
        // BufferedReader reads the file line by line efficiently
        // FileReader opens the actual file from the path the user gave
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line; // 'line' holds the current line being read

            // Keep reading lines until there are no more (null means end of file)
            while ((line = br.readLine()) != null) {

                // Skip completely blank lines
                if (line.trim().isEmpty()) continue;

                // Split the line into columns using comma as separator
                // -1 ensures trailing empty values are kept
                String[] columns = line.split(",", -1);

                // --- Find the real header row ---
                // We look for the row that starts with "Candidate"
                if (headerColumns == null && columns[0].trim().equalsIgnoreCase("Candidate")) {
                    headerColumns = columns; // Save the header columns
                    continue;               // Skip to the next line
                }

                // --- Collect data rows that come AFTER the header ---
                // Skip rows where the first column is empty (metadata/footer)
                if (headerColumns != null && !columns[0].trim().isEmpty()) {
                    first10Rows.add(columns); // Add this row to our list
                    dataRowCount++;

                    // Stop reading once we have collected 10 rows
                    if (dataRowCount == 10) break;
                }
            }

            // --- Display the results ---
            System.out.println("\n============================================================");
            System.out.println("           MP02 - FIRST 10 ROWS OF DATASET");
            System.out.println("============================================================");
            System.out.println("File: " + filePath);
            System.out.println("------------------------------------------------------------");

            // If no header was found, the file format may be unexpected
            if (headerColumns == null) {
                System.out.println("  ERROR: Could not find the dataset header row.");
            } else {
                // Print each of the first 10 rows in a labeled format
                for (int i = 0; i < first10Rows.size(); i++) {
                    String[] row = first10Rows.get(i);
                    System.out.println("\n  Record #" + (i + 1));
                    System.out.println("  ---------------------------");

                    // Print each field with its column name as a label
                    for (int j = 0; j < headerColumns.length; j++) {
                        String colName = headerColumns[j].trim();

                        // Only display columns that have a real header name
                        if (!colName.isEmpty()) {
                            // Get the value safely; show "(empty)" if missing
                            String value = (j < row.length) ? row[j].trim() : "(empty)";
                            if (value.isEmpty()) value = "(empty)";
                            System.out.printf("  %-12s : %s%n", colName, value);
                        }
                    }
                }

                System.out.println("\n------------------------------------------------------------");
                System.out.println("  Rows Displayed: " + first10Rows.size());
            }

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