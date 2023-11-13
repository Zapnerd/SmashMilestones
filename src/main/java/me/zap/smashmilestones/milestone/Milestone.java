package me.zap.smashmilestones.milestone;

import org.bukkit.configuration.file.FileConfiguration;

public class Milestone {
    private final String id;
    private final String displayName;

    public Milestone(String id, FileConfiguration config) {
        this.id = id;
        this.displayName = config.getString("settings.name", id); // Default to id if not set
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

}
