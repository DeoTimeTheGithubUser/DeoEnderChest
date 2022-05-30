package me.deotime.deoenderchest.ender;

import me.deotime.deoenderchest.PluginDeoEnderChest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class EnderChestConfig {

    private static EnderChestConfig enderChestConfig;

    private final PluginDeoEnderChest plugin;

    private final int ENDER_CHEST_SIZE;
    private final String ENDER_CHEST_NAME;

    public int getEnderChestSize() {
        return ENDER_CHEST_SIZE;
    }

    public String getEnderChestName() {
        return ENDER_CHEST_NAME;
    }

    public int getSaveTimeSeconds() {
        return SAVE_TIME_SECONDS;
    }

    private final int SAVE_TIME_SECONDS;

    private EnderChestConfig(PluginDeoEnderChest plugin) {
        this.plugin = plugin;

        FileConfiguration config = plugin.getConfig();
        this.ENDER_CHEST_SIZE = config.getInt("ender_chest_slot_size");
        this.ENDER_CHEST_NAME = config.getString("ender_chest_name");
        this.SAVE_TIME_SECONDS = config.getInt("save_time_seconds");
    }

    public static void init(PluginDeoEnderChest plugin) {
        enderChestConfig = new EnderChestConfig(plugin);
    }

    public static EnderChestConfig getEnderChestConfig() {
        return enderChestConfig;
    }


}
