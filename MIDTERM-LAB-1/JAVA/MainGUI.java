import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class MainGUI extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public MainGUI() {

        setTitle("Top 10 Customers Report");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel();
        JButton uploadButton = new JButton("Upload CSV File");
        topPanel.add(uploadButton);
        add(topPanel, BorderLayout.NORTH);

        // Table setup
        String[] columns = {"Rank", "Customer", "Total Sales"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        uploadButton.addActionListener(e -> chooseFile());

        setVisible(true);
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            if (!file.getName().toLowerCase().endsWith(".csv")) {
                JOptionPane.showMessageDialog(this,
                        "Invalid file format. Please select a CSV file.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            processFile(file);
        }
    }

    private void processFile(File file) {

        Map<String, Double> customerSales = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String header = br.readLine();
            if (header == null || !header.contains(",")) {
                JOptionPane.showMessageDialog(this,
                        "Invalid CSV format.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String line;
            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length < 8) continue; // must have at least 8 columns

                try {
                    String customer = data[4].trim(); // publisher column
                    double sales = Double.parseDouble(data[7].trim()); // total_sales column

                    if (customer.isEmpty()) continue;

                    customerSales.put(customer,
                            customerSales.getOrDefault(customer, 0.0) + sales);

                } catch (NumberFormatException ex) {
                    continue; // skip bad rows
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error reading file: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        displayTop10(customerSales);
    }

    private void displayTop10(Map<String, Double> customerSales) {

        tableModel.setRowCount(0); // clear previous results

        List<DataRecord> records = new ArrayList<>();
        for (Map.Entry<String, Double> entry : customerSales.entrySet()) {
            records.add(new DataRecord(entry.getKey(), entry.getValue()));
        }

        // Sort descending
        records.sort((a, b) -> Double.compare(b.getTotalSales(), a.getTotalSales()));

        // Show top 10
        for (int i = 0; i < Math.min(10, records.size()); i++) {
            DataRecord record = records.get(i);
            tableModel.addRow(new Object[]{
                    i + 1,
                    record.getCustomerName(),
                    String.format("%.2f", record.getTotalSales())
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}
