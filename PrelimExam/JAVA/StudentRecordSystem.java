/*
Programmer: Sophia Welynne S. Eyas - 23-0118-315
*/
package JAVA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class StudentRecordSystem extends JFrame {

    JTable table;
    DefaultTableModel model;
    JTextField txtID, txtName, txtGrade;

    public StudentRecordSystem() {
        setTitle("Student Records - Sophia Welynne S. Eyas - 23-0118-315");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(mainPanel);

        // Table with 3 columns
        model = new DefaultTableModel(new String[]{"ID", "Name", "Grade"}, 0);
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Student Records"));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Student"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.NONE;

        // ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("ID:"), gbc);

        txtID = new JTextField();
        txtID.setPreferredSize(new Dimension(100, 25)); // smaller width
        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(txtID, gbc);

        // Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Full Name:"), gbc);

        txtName = new JTextField();
        txtName.setPreferredSize(new Dimension(150, 25)); // smaller width
        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(txtName, gbc);

        // Grades
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Grades:"), gbc);

        txtGrade = new JTextField();
        txtGrade.setPreferredSize(new Dimension(200, 25)); // smaller width
        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(txtGrade, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        inputPanel.add(buttonPanel, gbc);

        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // Load CSV
        loadCSV();

        // Add record
        btnAdd.addActionListener(e -> {
            String id = txtID.getText().trim();
            String name = txtName.getText().trim();
            String grade = txtGrade.getText().trim();

            if (id.isEmpty() || name.isEmpty() || grade.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.");
                return;
            }

            model.addRow(new String[]{id, name, grade});

            txtID.setText("");
            txtName.setText("");
            txtGrade.setText("");
        });

        // Delete record
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                model.removeRow(row);
            } else {
                JOptionPane.showMessageDialog(this, "Select a row to delete.");
            }
        });
    }

    private void loadCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("PrelimExam/JAVA/MOCK_DATA.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                String id = parts[0];
                String fullName = parts[1] + " " + parts[2];

                // Combine all grades into a single string
                StringBuilder grades = new StringBuilder();
                for (int i = 3; i < parts.length; i++) {
                    grades.append(parts[i]);
                    if (i < parts.length - 1) grades.append(", ");
                }

                model.addRow(new String[]{id, fullName, grades.toString()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reading MOCK_DATA.csv");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentRecordSystem().setVisible(true));
    }
}
