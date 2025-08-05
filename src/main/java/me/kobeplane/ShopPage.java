package me.kobeplane;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URI;

public class ShopPage {

    private JFrame frame;

    public ShopPage() {
        frame = new JFrame("Workout Equipment");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        // Top Panel (Navigation Buttons)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        JButton backButton = new JButton("Back to home");
        backButton.addActionListener(e -> {
            frame.dispose();
            SwingUtilities.invokeLater(CalorieInputPage::new);
        });

        JButton viewStoreButton = new JButton("View full webstore");
        viewStoreButton.addActionListener(this::openWebstoreLink);

        topPanel.add(backButton);
        topPanel.add(viewStoreButton);
        frame.add(topPanel, BorderLayout.NORTH);

        // Grid Panel (Store Items)
        JPanel gridPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        gridPanel.add(createStoreItem("Weighted Belt"));
        gridPanel.add(createStoreItem("Lifting Belt"));
        gridPanel.add(createStoreItem("Kickback Attachment"));
        gridPanel.add(createStoreItem("Barbell Foam Pad"));
        gridPanel.add(createStoreItem("Pump Cover Hoodie"));
        gridPanel.add(createStoreItem("Sports Bottle"));


        frame.add(gridPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JPanel createStoreItem(String name) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));
        panel.setBorder(new LineBorder(Color.BLACK, 1));
        panel.setPreferredSize(new Dimension(100, 100));

        JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);

        JButton buyButton = new JButton("Buy");
        buyButton.setBorder(new LineBorder(Color.BLACK, 1));
        buyButton.addActionListener(e -> openCheckoutPage());

        panel.add(nameLabel, BorderLayout.NORTH);
        panel.add(buyButton, BorderLayout.CENTER);

        return panel;
    }

    private void openCheckoutPage() {
        frame.dispose();
        SwingUtilities.invokeLater(CheckoutPage::new);
    }

    private void openWebstoreLink(ActionEvent e) {
        try {
            Desktop.getDesktop().browse(new URI("https://example.com/store"));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Failed to open link.");
            ex.printStackTrace();
        }
    }
}
