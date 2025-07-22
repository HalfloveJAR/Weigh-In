package me.kobeplane;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;

public class CalorieInputPage {

    private JFrame frame;
    private JTextField inputField;
    private JLabel valueLabel;

    private int calorieValue = 0;
    private final Path dateFile = Paths.get("lastDate.txt");
    private final Path calorieFile = Paths.get("calories.txt");

    public CalorieInputPage() {
        checkAndResetIfNewDay(); // <- Handles date-based reset
        loadCalorieValue();

        frame = new JFrame("Calorie Input");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 130);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        valueLabel = new JLabel("Calories Eaten Today: " + calorieValue, SwingConstants.CENTER);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        frame.add(valueLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputField = new JTextField(10);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> handleSubmit());

        JButton testDayReset = new JButton("Test New Day");
        testDayReset.addActionListener(e -> checkAndResetIfNewDay());

        JButton goToShopPage = new JButton("Shop Page");
        goToShopPage.addActionListener(e -> {goToShopPage();});

        inputPanel.add(inputField);
        inputPanel.add(submitButton);
        inputPanel.add(testDayReset);
        inputPanel.add(goToShopPage);
        frame.add(inputPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void handleSubmit() {
        String input = inputField.getText().trim();
        if (input.matches("\\d+")) {
            calorieValue += Integer.parseInt(input);
            valueLabel.setText("Calories Eaten Today: " + calorieValue);
            saveCalorieValue();
        } else {
            JOptionPane.showMessageDialog(frame, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkAndResetIfNewDay() {
        LocalDate today = LocalDate.now();

        try {
            if (Files.exists(dateFile)) {
                String lastDate = Files.readString(dateFile).trim();
                if (!lastDate.equals(today.toString())) {
                    // New day, reset calorie value and update date file
                    calorieValue = 0;
                    saveCalorieValue();
                    Files.writeString(dateFile, today.toString());
                }
            } else {
                // First run
                Files.writeString(dateFile, today.toString());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to check/reset day.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadCalorieValue() {
        try {
            if (Files.exists(calorieFile)) {
                String calorieStr = Files.readString(calorieFile).trim();
                calorieValue = Integer.parseInt(calorieStr);
            }
        } catch (IOException | NumberFormatException e) {
            calorieValue = 0;
        }
    }

    private void saveCalorieValue() {
        try {
            Files.writeString(calorieFile, String.valueOf(calorieValue));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Failed to save calorie value.", "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void goToShopPage() {
        frame.dispose();
        SwingUtilities.invokeLater(ShopPage::new);
    }
}
