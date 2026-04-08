/*
Programmer: Sophia Welynne S. Eyas - 23-0118-315
Task: Top 10 Customers Report (Terminal)
*/

import java.io.*;
import java.util.*;

class DataRecord {
    String customerName;
    double saleAmount;

    public DataRecord(String customerName, double saleAmount) {
        this.customerName = customerName;
        this.saleAmount = saleAmount;
    }
}

public class TopCustomers {

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            File file;

            // Ask for CSV path until valid
            while (true) {
                System.out.print("Enter dataset file path: ");
                String path = input.nextLine();
                file = new File(path);

                if (file.exists() && file.isFile() && path.toLowerCase().endsWith(".csv")) {
                    break;
                } else {
                    System.out.println("Invalid file path or not a CSV. Try again.");
                }
            }

            List<DataRecord> records = readCSV(file);
            if (records.isEmpty()) {
                System.out.println("No valid data found in the CSV.");
                return;
            }

            // Aggregate sales
            Map<String, Double> salesMap = new HashMap<>();
            for (DataRecord record : records) {
                salesMap.put(record.customerName,
                        salesMap.getOrDefault(record.customerName, 0.0) + record.saleAmount);
            }

            // Sort descending
            List<Map.Entry<String, Double>> sortedList = new ArrayList<>(salesMap.entrySet());
            sortedList.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

            // Display top 10
            System.out.println("\n=== Top 10 Customers by Revenue ===");
            System.out.printf("%-5s %-25s %-12s\n", "No.", "Customer Name", "Total Sales");
            System.out.println("---------------------------------------------");

            for (int i = 0; i < Math.min(10, sortedList.size()); i++) {
                Map.Entry<String, Double> entry = sortedList.get(i);
                System.out.printf("%-5d %-25s $%,.2f\n", i + 1, entry.getKey(), entry.getValue());
            }
        }
    }

    private static List<DataRecord> readCSV(File file) {
        List<DataRecord> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) { // skip header
                    firstLine = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length < 2) continue;

                String customer = parts[0].trim();
                double sales;
                try {
                    sales = Double.parseDouble(parts[1].trim());
                } catch (NumberFormatException e) {
                    continue;
                }

                records.add(new DataRecord(customer, sales));
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        }
        return records;
    }
}
