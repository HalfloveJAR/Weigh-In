package me.kobeplane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckoutPage extends JFrame {

    public CheckoutPage() {
        setTitle("Checkout");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        // Top navigation
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton homeButton = new JButton("Back to home");
        JButton shopButton = new JButton("Back to shop page");

        topPanel.add(homeButton);
        topPanel.add(shopButton);
        add(topPanel, BorderLayout.NORTH);

        homeButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(CalorieInputPage::new);
        });
        shopButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(ShopPage::new);
        });

        // Center form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;
        String[] labels = {
                "Full Name:", "Address:", "City:", "State:", "ZIP Code:",
                "Card Number:", "Expiry Date:", "CVV:"
        };
        JTextField[] fields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = row;
            formPanel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            fields[i] = new JTextField(20);
            formPanel.add(fields[i], gbc);

            row++;
        }

        add(formPanel, BorderLayout.CENTER);

        // Submit button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton submitButton = new JButton("Submit");
        bottomPanel.add(submitButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Action Listener
        submitButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Example purchase complete."));

        // Keep theme black and white
        getContentPane().setBackground(Color.WHITE);
        topPanel.setBackground(Color.WHITE);
        formPanel.setBackground(Color.WHITE);
        bottomPanel.setBackground(Color.WHITE);

        setVisible(true);
    }
}
