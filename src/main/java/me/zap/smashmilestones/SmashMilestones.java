package me.zap.smashmilestones;

import me.zap.smashengine.SmashEngine;
import me.zap.smashengine.database.Database;
import me.zap.smashmilestones.data.PlayerData;
import me.zap.smashmilestones.milestone.MilestoneLoader;
import org.bukkit.plugin.java.JavaPlugin;

public final class SmashMilestones extends JavaPlugin {
    private Database database;
    private MilestoneLoader milestoneLoader;
    private PlayerData playerData;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        if (SmashEngine.getInstance() == null){
            getLogger().severe("SmashEngine is not installed! Disabling SmashMilestones...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        database = Database.initializeFromConfig(this);
        try {
            database.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        milestoneLoader = new MilestoneLoader(this);
        playerData = new PlayerData(database, milestoneLoader);
    }

    @Override
    public void onDisable() {
        if (database != null && database.isConnected()) {
            database.disconnect();
        }
    }

}
