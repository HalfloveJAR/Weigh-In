package me.kobeplane.data;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DailyCalorieLogService {

    private Dao<DailyCalorieLogData, String> calorieLogDao;


    public DailyCalorieLogService(ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, DailyCalorieLogData.class);
            calorieLogDao = DaoManager.createDao(connectionSource, DailyCalorieLogData.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addCaloriesToToday(int calories) {
        try {
            Date today = normalizeDate(new Date());

            // Try to find today's entry
            QueryBuilder<DailyCalorieLogData, String> queryBuilder = calorieLogDao.queryBuilder();
            queryBuilder.where().eq("logDate", today);
            List<DailyCalorieLogData> existingLogs = calorieLogDao.query(queryBuilder.prepare());
            System.out.println(calorieLogDao);

            if (existingLogs.isEmpty()) {
                // No entry for today, create a new one
                System.out.println("No entry for today, create a new one");
                DailyCalorieLogData newEntry = new DailyCalorieLogData();
                newEntry.setLogDate(today);
                newEntry.setTotalCalories(calories);
                calorieLogDao.create(newEntry);
            } else {
                // Entry exists, update calories
                System.out.println("Entry exists, update calories");
                DailyCalorieLogData todayLog = existingLogs.get(0);
                int updatedCalories = todayLog.getTotalCalories() + calories;
                todayLog.setTotalCalories(updatedCalories);
                calorieLogDao.update(todayLog);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int getCaloriesForDate(Date date) {
        try {
            Date normalizedDate = normalizeDate(date);

            QueryBuilder<DailyCalorieLogData, String> queryBuilder = calorieLogDao.queryBuilder();
            queryBuilder.where().eq("logDate", normalizedDate);
            List<DailyCalorieLogData> results = calorieLogDao.query(queryBuilder.prepare());

            if (!results.isEmpty()) {
                System.out.println("Calories for today: " + results.get(0).getTotalCalories());
                return results.get(0).getTotalCalories();
            } else {
                return 0; // No entry for that date
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public boolean dateExists(Date date) {
        try {
            Date normalizedDate = normalizeDate(date);

            QueryBuilder<DailyCalorieLogData, String> queryBuilder = calorieLogDao.queryBuilder();
            queryBuilder.where().eq("logDate", normalizedDate);
            queryBuilder.setCountOf(true);
            long count = calorieLogDao.countOf(queryBuilder.prepare());
            return count > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    // Helper to normalize date (removes time portion)
    private Date normalizeDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public int getPreviousCalories() {
        try {
            // Step 1: Get today's log entry
            Date today = normalizeDate(new Date());
            QueryBuilder<DailyCalorieLogData, String> todayQuery = calorieLogDao.queryBuilder();
            todayQuery.where().eq("logDate", today);
            List<DailyCalorieLogData> todayResults = calorieLogDao.query(todayQuery.prepare());

            if (todayResults.isEmpty()) {
                // No entry for today; assume 0
                return 0;
            }

            int todayDayId = todayResults.get(0).getDayId();

            // Step 2: Look for the previous day by dayId - 1
            int previousDayId = todayDayId - 1;
            QueryBuilder<DailyCalorieLogData, String> prevQuery = calorieLogDao.queryBuilder();
            prevQuery.where().eq("dayId", previousDayId);
            List<DailyCalorieLogData> prevResults = calorieLogDao.query(prevQuery.prepare());

            if (!prevResults.isEmpty()) {
                return prevResults.get(0).getTotalCalories();
            }

            // Step 3: No previous day found; this must be the first date
            return 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

}
