package me.kobeplane;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import me.kobeplane.data.DailyCalorieLogService;

import javax.swing.*;

public class Main {

    public static DailyCalorieLogService calorieLogService;

    public static void main(String[] args) {
        setupDatabase();
        SwingUtilities.invokeLater(CalorieInputPage::new);
    }

    private static void setupDatabase() {
        try {
            String connectionUrl;
            ConnectionSource connectionSource;
            connectionUrl = "jdbc:sqlite:" + System.getProperty("user.dir") + "/planpal.db";
            connectionSource = new JdbcConnectionSource(connectionUrl);
            calorieLogService = new DailyCalorieLogService(connectionSource);
        } catch (Exception ex) {
            System.out.println("Connection to database failed.");
            System.out.println("Error: " + ex.getMessage());
        }
    }
}