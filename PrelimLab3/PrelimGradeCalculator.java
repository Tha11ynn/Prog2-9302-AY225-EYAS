package PrelimLab3;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class PrelimGradeCalculator extends JFrame implements ActionListener {

    JTextField day1, day2, day3, day4, day5;
    JTextField lab1Field, lab2Field, lab3Field, examField;

    JLabel prelimLabel, statusLabel;
    JLabel reqPassLabel, remarkPassLabel;
    JLabel reqExcellentLabel, remarkExcellentLabel;

    JButton computeBtn;

    public PrelimGradeCalculator() {
        setTitle("Prelim Grade Calculator");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(230, 238, 245));

        add(createMainPanel());
    }

    // ================= MAIN PANEL =================
    private JPanel createMainPanel() {
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(new Color(230, 238, 245));
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel attendancePanel = createAttendancePanel();
        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel();
        JPanel resultPanel = createResultPanel();

        attendancePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        main.add(attendancePanel);
        main.add(Box.createVerticalStrut(10));
        main.add(inputPanel);
        main.add(Box.createVerticalStrut(15));
        main.add(buttonPanel);
        main.add(Box.createVerticalStrut(20));
        main.add(resultPanel);

        return main;
    }

    // ================= ATTENDANCE PANEL =================
    private JPanel createAttendancePanel() {
        JPanel panel = new JPanel(new GridLayout(2, 5, 10, 5));
        panel.setMaximumSize(new Dimension(850, 110));
        panel.setBorder(BorderFactory.createTitledBorder("Attendance (P-Present / A-Absent / E-Excused)"));

        day1 = new JTextField();
        day2 = new JTextField();
        day3 = new JTextField();
        day4 = new JTextField();
        day5 = new JTextField();

        // Apply attendance filter
        applyAttendanceFilter(day1);
        applyAttendanceFilter(day2);
        applyAttendanceFilter(day3);
        applyAttendanceFilter(day4);
        applyAttendanceFilter(day5);

        panel.add(new JLabel("Day 1"));
        panel.add(new JLabel("Day 2"));
        panel.add(new JLabel("Day 3"));
        panel.add(new JLabel("Day 4"));
        panel.add(new JLabel("Day 5"));

        panel.add(day1);
        panel.add(day2);
        panel.add(day3);
        panel.add(day4);
        panel.add(day5);

        return panel;
    }

    // ================= APPLY DOCUMENT FILTER =================
    private void applyAttendanceFilter(JTextField field) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (isValid(string)) {
                    super.insertString(fb, offset, string.toUpperCase(), attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (isValid(text)) {
                    super.replace(fb, offset, length, text.toUpperCase(), attrs);
                }
            }

            private boolean isValid(String text) {
                return text != null && text.matches("[PAEpae]");
            }
        });
    }

    // ================= INPUT PANEL =================
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 10, 5));
        panel.setMaximumSize(new Dimension(850, 100));
        panel.setBorder(BorderFactory.createTitledBorder("Grades"));

        lab1Field = new JTextField();
        lab2Field = new JTextField();
        lab3Field = new JTextField();
        examField = new JTextField();

        panel.add(new JLabel("Lab Work 1"));
        panel.add(new JLabel("Lab Work 2"));
        panel.add(new JLabel("Lab Work 3"));
        panel.add(new JLabel("Prelim Exam"));

        panel.add(lab1Field);
        panel.add(lab2Field);
        panel.add(lab3Field);
        panel.add(examField);

        return panel;
    }

    // ================= BUTTON =================
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        computeBtn = new JButton("Calculate Prelim Grade");
        computeBtn.setPreferredSize(new Dimension(230, 40));
        computeBtn.addActionListener(this);
        panel.add(computeBtn);
        return panel;
    }

    // ================= RESULT PANEL =================
    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setMaximumSize(new Dimension(850, 220));
        panel.setBorder(BorderFactory.createTitledBorder("Results"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        prelimLabel = new JLabel("--");
        statusLabel = new JLabel("--");
        reqPassLabel = new JLabel("--");
        remarkPassLabel = new JLabel("--");
        reqExcellentLabel = new JLabel("--");
        remarkExcellentLabel = new JLabel("--");

        // Row 1: Prelim Grade
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        panel.add(new JLabel("Prelim Grade:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.2;
        panel.add(prelimLabel, gbc);
        gbc.gridx = 2; gbc.weightx = 0.3;
        panel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.2;
        panel.add(statusLabel, gbc);

        // Row 2: Passing requirements
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        panel.add(new JLabel("Passing (75) Required Exam:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.2;
        panel.add(reqPassLabel, gbc);
        gbc.gridx = 2; gbc.weightx = 0.3;
        panel.add(new JLabel("Passing Remarks:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.2;
        panel.add(remarkPassLabel, gbc);

        // Row 3: Excellent requirements
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        panel.add(new JLabel("Excellent (100) Required Exam:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.2;
        panel.add(reqExcellentLabel, gbc);
        gbc.gridx = 2; gbc.weightx = 0.3;
        panel.add(new JLabel("Excellent Remarks:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.2;
        panel.add(remarkExcellentLabel, gbc);

        return panel;
    }

    // ================= LOGIC =================
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String[] days = {
                day1.getText(), day2.getText(), day3.getText(),
                day4.getText(), day5.getText()
            };

            int absentCount = 0;

            for (String d : days) {
                if (d.equalsIgnoreCase("A")) {
                    absentCount++;
                }
            }

            if (absentCount > 3) {
                prelimLabel.setText("FAILED");
                statusLabel.setText("FAILED (Too many absences)");
                reqPassLabel.setText("--");
                remarkPassLabel.setText("IMPOSSIBLE");
                reqExcellentLabel.setText("--");
                remarkExcellentLabel.setText("IMPOSSIBLE");
                return;
            }

            double attendanceScore = 100 - (absentCount * 20);

            double lab1 = Double.parseDouble(lab1Field.getText());
            double lab2 = Double.parseDouble(lab2Field.getText());
            double lab3 = Double.parseDouble(lab3Field.getText());
            double exam = Double.parseDouble(examField.getText());

            double labAvg = (lab1 + lab2 + lab3) / 3;
            double classStanding = (attendanceScore * 0.40) + (labAvg * 0.60);
            double prelimGrade = (exam * 0.70) + (classStanding * 0.30);

            prelimLabel.setText(String.format("%.2f", prelimGrade));
            statusLabel.setText(prelimGrade >= 75 ? "PASSED" : "FAILED");

            // PASSING
            double reqPass = (75 - (classStanding * 0.30)) / 0.70;
            if (prelimGrade >= 75) {
                reqPassLabel.setText(String.format("%.2f", exam));
                remarkPassLabel.setText("Requirements met");
            } else if (reqPass > 100 || reqPass < 0) {
                reqPassLabel.setText("--");
                remarkPassLabel.setText("IMPOSSIBLE");
            } else {
                reqPassLabel.setText(String.format("%.2f", reqPass));
                remarkPassLabel.setText("Still needed");
            }

            // EXCELLENT
            double reqExcellent = (100 - (classStanding * 0.30)) / 0.70;
            if (prelimGrade >= 100) {
                reqExcellentLabel.setText(String.format("%.2f", exam));
                remarkExcellentLabel.setText("Requirements met");
            } else if (reqExcellent > 100 || reqExcellent < 0) {
                reqExcellentLabel.setText("--");
                remarkExcellentLabel.setText("IMPOSSIBLE");
            } else {
                reqExcellentLabel.setText(String.format("%.2f", reqExcellent));
                remarkExcellentLabel.setText("Still needed");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Please use P / A / E and valid numbers.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PrelimGradeCalculator().setVisible(true));
    }
}
