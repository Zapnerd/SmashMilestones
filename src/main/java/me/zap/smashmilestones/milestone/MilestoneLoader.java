package me.zap.smashmilestones.milestone;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MilestoneLoader {
    private final JavaPlugin plugin;
    private final Map<String, Milestone> milestones = new HashMap<>();

    public MilestoneLoader(JavaPlugin plugin) {
        this.plugin = plugin;
        loadMilestones();
    }

    private void loadMilestones() {
        File milestonesFolder = new File(plugin.getDataFolder(), "milestones");
        if (!milestonesFolder.exists()) {
            milestonesFolder.mkdirs();
        }

        // Check and create default milestone files
        createDefaultMilestone("mining.yml");

        File[] files = milestonesFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".yml")) {
                    String milestoneId = file.getName().replace(".yml", "");
                    FileConfiguration milestoneConfig = YamlConfiguration.loadConfiguration(file);
                    Milestone milestone = new Milestone(milestoneId, milestoneConfig);
                    milestones.put(milestoneId, milestone);
                }
            }
        }
    }

    private void createDefaultMilestone(String fileName) {
        File milestoneFile = new File(plugin.getDataFolder(), "milestones/" + fileName);
        if (!milestoneFile.exists()) {
            try (InputStream in = plugin.getResource(fileName)) {
                if (in == null) {
                    plugin.getLogger().warning("Resource file " + fileName + " not found");
                    return;
                }
                FileConfiguration config = YamlConfiguration.loadConfiguration(new InputStreamReader(in));
                config.save(milestoneFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, Milestone> getAllMilestones() {
        return Collections.unmodifiableMap(milestones);
    }

}
