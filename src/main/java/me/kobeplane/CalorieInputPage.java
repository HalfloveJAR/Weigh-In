package me.kobeplane;

import javax.swing.*;
import java.awt.*;
import java.nio.file.*;
import java.util.Date;

public class CalorieInputPage {

    private JFrame frame;
    private JTextField inputField;
    private JLabel valueLabel;

    private int calorieValue = 0;

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
        //Will need to check the current date and the latest date in the database, if they do not match, then the GUI should show that
    }

    private void loadCalorieValue() {
        try {
            if (!Main.calorieLogService.dateExists(new Date())) {
                calorieValue = 0;
            }
            calorieValue = Main.calorieLogService.getCaloriesForDate(new Date());
        } catch (Exception e) {
            calorieValue = 0;
            e.printStackTrace();
        }
    }

    private void saveCalorieValue() {
        try {
            Main.calorieLogService.addCaloriesToToday(calorieValue);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Failed to save calorie value.", "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void goToShopPage() {
        frame.dispose();
        SwingUtilities.invokeLater(ShopPage::new);
    }
}
