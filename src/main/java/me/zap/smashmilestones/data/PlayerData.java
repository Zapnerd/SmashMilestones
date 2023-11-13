package me.zap.smashmilestones.data;

import me.zap.smashengine.database.Database;
import me.zap.smashmilestones.milestone.Milestone;
import me.zap.smashmilestones.milestone.MilestoneLoader;

import java.sql.SQLException;
import java.util.Map;

public class PlayerData {
    private final Database database;
    private final MilestoneLoader milestoneLoader;

    public PlayerData(Database database, MilestoneLoader milestoneLoader) {
        this.database = database;
        this.milestoneLoader = milestoneLoader;
        createTable();
    }

    private void createTable() {
        StringBuilder createTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS milestones_data (");
        createTableSQL.append("UUID CHAR(36) PRIMARY KEY");

        Map<String, Milestone> milestones = milestoneLoader.getAllMilestones();
        for (String milestoneId : milestones.keySet()) {
            createTableSQL.append(", ")
                    .append(milestoneId)
                    .append("_stats INT DEFAULT 0, ")
                    .append(milestoneId)
                    .append("_rewards INT DEFAULT 0");
        }

        createTableSQL.append(");");

        try {
            database.execute(createTableSQL.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
