package me.deotime.deoenderchest;

import me.deotime.deoenderchest.ender.CommandEnderChest;
import me.deotime.deoenderchest.ender.EnderChestConfig;
import me.deotime.deoenderchest.ender.EnderChestHandler;
import me.deotime.deoenderchest.ender.EnderChestListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class PluginDeoEnderChest extends JavaPlugin {

    @Override
    public void onEnable() {
        FileConfiguration config = getConfig();
        config.addDefault("ender_chest_slot_size", 27);
        config.addDefault("ender_chest_name", "&a&lEnder Chest");
        config.addDefault("save_time_seconds", 30);
        config.options().copyDefaults(true);
        saveConfig();

        EnderChestConfig.init(this);
        EnderChestHandler.init(this);

        getCommand("enderchest").setExecutor(new CommandEnderChest());
        getServer().getPluginManager().registerEvents(new EnderChestListener(), this);
    }

    @Override
    public void onDisable() {
        EnderChestHandler.getEnderChestHandler().saveAll();
    }
}
