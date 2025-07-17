package me.kobeplane;

import javax.swing.*;
import java.awt.*;

public class CalorieInputPage extends JFrame {

    private JFrame frame;
    private JTextField inputField;
    private JLabel valueLabel;

    public CalorieInputPage() {
        // Setup main frame
        frame = new JFrame("Calorie Input");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 130);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // Top label
        JPanel valuePanel = new JPanel();
        valuePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        valueLabel = new JLabel("Value: 0", SwingConstants.CENTER);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        frame.add(valueLabel, BorderLayout.NORTH);

        // Input calories
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputField = new JTextField(10);
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(e -> handleSubmit());
        inputPanel.add(inputField);
        inputPanel.add(submitButton);
        frame.add(inputPanel, BorderLayout.CENTER);

        // Set frame wtih content as active
        frame.setVisible(true);
    }

    private void handleSubmit() {
        String input = inputField.getText().trim();
        if (input.matches("\\d+")) {
            valueLabel.setText("Value: " + input);
        } else {
            JOptionPane.showMessageDialog(frame, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
}
