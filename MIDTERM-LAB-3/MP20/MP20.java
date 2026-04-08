// ============================================================
// MP20 - Convert CSV Dataset to JSON
// Programmer: EYAS, SOPHIA WELYNNE S.
// Course: Programming 2 | BSIT GD - 1st Year
// Description: This program reads a CSV file, parses each
//              data row using the header columns as keys,
//              and outputs the entire dataset as a formatted
//              JSON array saved to a new .json file.
// ============================================================

import java.io.*;        // For reading and writing files
import java.util.*;      // For ArrayList and Scanner

public class MP20 {

    public static void main(String[] args) {

        // Scanner is used to get keyboard input from the user
        Scanner scanner = new Scanner(System.in);

        // Ask the user to type the path to the CSV file
        System.out.print("Enter the CSV dataset file path: ");
        String filePath = scanner.nextLine(); // Store what the user typed

        // Build the output JSON file path by replacing .csv extension with .json
        // If the file has no .csv extension, just append _output.json
        String jsonFilePath = filePath.toLowerCase().endsWith(".csv")
                ? filePath.substring(0, filePath.length() - 4) + ".json"
                : filePath + "_output.json";

        // 'headerColumns' will store the column names from the header row
        String[] headerColumns = null;

        // 'jsonRecords' will hold each data row formatted as a JSON object string
        ArrayList<String> jsonRecords = new ArrayList<>();

        // 'dataRowCount' counts how many records were successfully converted
        int dataRowCount = 0;

        // --- Start reading the CSV file ---
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
                    headerColumns = columns; // Save the header columns for use as JSON keys
                    continue;               // Skip to the next line
                }

                // --- Process data rows that come AFTER the header ---
                // Skip rows where the first column is empty (metadata/footer rows)
                if (headerColumns != null && !columns[0].trim().isEmpty()) {
                    dataRowCount++;

                    // Build a JSON object string for this row
                    // Each field becomes a "key": "value" pair
                    StringBuilder jsonObject = new StringBuilder();
                    jsonObject.append("  {\n"); // Opening brace for this JSON object

                    // Track how many valid fields we have added (for comma placement)
                    int validFieldCount = 0;

                    // Count how many named columns the header has
                    int namedColumns = 0;
                    for (String h : headerColumns) {
                        if (!h.trim().isEmpty()) namedColumns++;
                    }

                    // Loop through each named column and add it as a JSON key-value pair
                    for (int i = 0; i < headerColumns.length; i++) {
                        String colName = headerColumns[i].trim();

                        // Only include columns that have a real header name
                        if (!colName.isEmpty()) {
                            validFieldCount++;

                            // Get the value for this field, or empty string if missing
                            String value = (i < columns.length) ? columns[i].trim() : "";

                            // Escape any double-quote characters inside the value
                            // JSON requires internal quotes to be escaped as \"
                            value = value.replace("\"", "\\\"");

                            // Add comma after each field except the last one
                            String comma = (validFieldCount < namedColumns) ? "," : "";
                            jsonObject.append("    \"").append(colName).append("\": \"")
                                    .append(value).append("\"").append(comma).append("\n");
                        }
                    }

                    jsonObject.append("  }"); // Closing brace for this JSON object
                    jsonRecords.add(jsonObject.toString()); // Add to our list
                }
            }

            // --- Write the JSON output to a file ---
            // PrintWriter creates/overwrites the output file
            // FileWriter points it to the JSON output path
            try (PrintWriter pw = new PrintWriter(new FileWriter(jsonFilePath))) {
                pw.println("["); // JSON array opening bracket

                // Write each JSON object; add comma between objects (not after last)
                for (int i = 0; i < jsonRecords.size(); i++) {
                    pw.print(jsonRecords.get(i));
                    if (i < jsonRecords.size() - 1) {
                        pw.println(","); // Comma after every object except the last
                    } else {
                        pw.println();   // No comma after the last object
                    }
                }

                pw.println("]"); // JSON array closing bracket
            }

            // --- Display the results summary ---
            System.out.println("\n============================================================");
            System.out.println("           MP20 - CONVERT CSV DATASET TO JSON");
            System.out.println("============================================================");
            System.out.println("Input CSV File  : " + filePath);
            System.out.println("Output JSON File: " + jsonFilePath);
            System.out.println("------------------------------------------------------------");
            System.out.println("  >> Total Records Converted: " + dataRowCount);
            System.out.println("  >> JSON file successfully created!");
            System.out.println("============================================================");

        } catch (FileNotFoundException e) {
            // This error happens if the file path is wrong or file does not exist
            System.out.println("ERROR: File not found. Please check the file path.");
        } catch (IOException e) {
            // This catches any other file reading or writing errors
            System.out.println("ERROR: Could not process the file. " + e.getMessage());
        }

        scanner.close(); // Always close the scanner when done
    }
}