package me.kobeplane;

import javax.swing.*;
import java.awt.*;

public class ShopPage {

    private JFrame frame;
    private JLabel valueLabel;


    public ShopPage() {

        frame = new JFrame("Workout Equipment Store");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 130);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        valueLabel = new JLabel("Workout Equipment Store", SwingConstants.CENTER);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        frame.add(valueLabel, BorderLayout.NORTH);

        JButton goToCaloriePage = new JButton("Calorie Input Page");
        goToCaloriePage.addActionListener(e -> {goToCaloriePage();});

        frame.add(goToCaloriePage);

        frame.setVisible(true);
    }

    private void goToCaloriePage() {
        frame.dispose();
        SwingUtilities.invokeLater(CalorieInputPage::new);
    }

}
