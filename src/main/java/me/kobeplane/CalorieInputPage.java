package me.kobeplane;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class CalorieInputPage {

    private JFrame frame;
    private JLabel caloriesTodayLabel;
    private JLabel caloriesYesterdayLabel;
    private JTextField inputField;

    private int totalCaloriesSoFar = 0;

    public CalorieInputPage() {
        int caloriesOnLoad = loadCalorieValue();

        frame = new JFrame("Calorie Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // Top panel with "Go to Shop" button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton shopButton = new JButton("Go to Shop");
        shopButton.addActionListener(e -> goToShopPage());
        topPanel.add(shopButton);
        frame.add(topPanel, BorderLayout.NORTH);

        // Center panel for calorie display and input
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Calories Today Label (big)
        caloriesTodayLabel = new JLabel("Calories Consumed Today: " + caloriesOnLoad);
        caloriesTodayLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        caloriesTodayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(caloriesTodayLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Calories Yesterday Label (smaller, lighter)
        int calories = Main.calorieLogService.getPreviousCalories();
        caloriesYesterdayLabel = new JLabel("Calories Consumed Yesterday: " + calories);
        caloriesYesterdayLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        caloriesYesterdayLabel.setForeground(Color.DARK_GRAY);
        caloriesYesterdayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(caloriesYesterdayLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Input + Submit Button (side by side)
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        inputField = new JTextField(10);
        inputField.setHorizontalAlignment(JTextField.RIGHT);
        inputPanel.add(inputField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> handleSubmit());
        inputPanel.add(submitButton);

        centerPanel.add(inputPanel);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // Called when submit is pressed
    private void handleSubmit() {
        String input = inputField.getText().trim();
        if (!input.matches("\\d+")) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        totalCaloriesSoFar += Integer.parseInt(input);

        System.out.println("Submitted calories: " + totalCaloriesSoFar);
        caloriesTodayLabel.setText("Calories Consumed Today: " + totalCaloriesSoFar);
        saveCalorieValue(Integer.parseInt(input));
    }

    private int loadCalorieValue() {
        try {
            if (!Main.calorieLogService.dateExists(new Date())) {
                totalCaloriesSoFar = 0;
                return totalCaloriesSoFar;
            }
            totalCaloriesSoFar = Main.calorieLogService.getCaloriesForDate(new Date());
        } catch (Exception e) {
            totalCaloriesSoFar = 0;
            e.printStackTrace();
        }
        return totalCaloriesSoFar;
    }

    private void saveCalorieValue(int calories) {
        try {
            Main.calorieLogService.addCaloriesToToday(calories);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Failed to save calorie value.", "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Navigation
    private void goToShopPage() {
        frame.dispose();
        SwingUtilities.invokeLater(ShopPage::new); // Assuming you have ShopPage.java
    }
}
