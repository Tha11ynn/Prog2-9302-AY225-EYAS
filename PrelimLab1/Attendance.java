import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Attendance Tracker - Like a digital sign-in sheet!
 * Students type their name and class, computer writes the time.
 */
public class Attendance extends JFrame {
    
    // These are our empty boxes waiting to be filled
    private JTextField nameField;        // For student name
    private JTextField courseField;      // For class/year
    private JTextField timeInField;      // Shows arrival time
    private JTextField eSignatureField;  // Shows unique ID
    private JButton submitButton;        // The "I'm here!" button
    private JButton clearButton;         // The "Erase all" button
    
    /**
     * This builds our window when the program starts
     */
    public Attendance() {
        setTitle("Attendance Tracker");              // Window title
        setSize(400, 300);                           // Window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close when X clicked
        setLocationRelativeTo(null);                 // Center on screen
        setResizable(false);                         // Can't resize
        
        initializeComponents();  // Build all the buttons and boxes
        setVisible(true);        // Show the window
    }
    
    /**
     * This creates and arranges everything inside the window
     */
    private void initializeComponents() {
        // Main background panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 248, 255));  // Light blue
        
        // Title at the top
        JLabel titleLabel = new JLabel("Student Attendance System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(25, 25, 112));  // Navy blue
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Form with 4 rows for our input fields
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2, 10, 15));
        formPanel.setBackground(new Color(240, 248, 255));
        
        // 1. Name field
        JLabel nameLabel = new JLabel("Attendance Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        
        // 2. Course field
        JLabel courseLabel = new JLabel("Course/Year:");
        courseLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        courseField = new JTextField(20);
        courseField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(courseLabel);
        formPanel.add(courseField);
        
        // 3. Time field (computer fills this in)
        JLabel timeLabel = new JLabel("Time In:");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        timeInField = new JTextField(20);
        timeInField.setFont(new Font("Arial", Font.PLAIN, 12));
        timeInField.setEditable(false);  // Can't type here
        timeInField.setBackground(Color.WHITE);
        formPanel.add(timeLabel);
        formPanel.add(timeInField);
        
        // 4. Signature field (computer fills this in)
        JLabel signatureLabel = new JLabel("E-Signature:");
        signatureLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        eSignatureField = new JTextField(20);
        eSignatureField.setFont(new Font("Arial", Font.PLAIN, 10));
        eSignatureField.setEditable(false);  // Can't type here
        eSignatureField.setBackground(Color.WHITE);
        formPanel.add(signatureLabel);
        formPanel.add(eSignatureField);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Buttons at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));
        
        // Green submit button
        submitButton = new JButton("Submit Attendance");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setBackground(new Color(34, 139, 34));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(e -> submitAttendance());  // Click = run submitAttendance()
        
        // Red clear button
        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setBackground(new Color(220, 20, 60));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.addActionListener(e -> clearFields());  // Click = run clearFields()
        
        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
    
    /**
     * Runs when you click "Submit" - checks form and fills in time + signature
     */
    private void submitAttendance() {
        // Check if name and course are filled in
        if (nameField.getText().trim().isEmpty() || courseField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all required fields (Name and Course/Year)",
                "Missing Information",
                JOptionPane.WARNING_MESSAGE);
            return;  // Stop here if empty
        }
        
        // Get current time from computer
        LocalDateTime now = LocalDateTime.now();
        
        // Make it look nice (2026-01-06 14:30:45)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        
        // Put time in the box
        timeInField.setText(formattedDateTime);
        
        // Create a unique ID (like a random serial number)
        String eSignature = UUID.randomUUID().toString();
        
        // Put signature in the box
        eSignatureField.setText(eSignature);
        
        // Show success message
        JOptionPane.showMessageDialog(this,
            "Attendance submitted successfully!\n\nName: " + nameField.getText() +
            "\nCourse/Year: " + courseField.getText() +
            "\nTime In: " + formattedDateTime,
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Runs when you click "Clear" - erases everything
     */
    private void clearFields() {
        nameField.setText("");       // Empty the name box
        courseField.setText("");     // Empty the course box
        timeInField.setText("");     // Empty the time box
        eSignatureField.setText(""); // Empty the signature box
        nameField.requestFocus();    // Put cursor back in name box
    }
    
    /**
     * This is where the program starts - the "power button"
     */
    public static void main(String[] args) {
        // Start the window safely
        SwingUtilities.invokeLater(() -> {
            new Attendance();  // Create the attendance tracker
        });
    }
}