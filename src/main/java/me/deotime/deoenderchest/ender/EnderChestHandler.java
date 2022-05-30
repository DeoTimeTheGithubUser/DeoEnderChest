package me.deotime.deoenderchest.ender;

import me.deotime.deoenderchest.PluginDeoEnderChest;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class EnderChestHandler {

    private static EnderChestHandler enderChestHandler;

    private PluginDeoEnderChest plugin;
    private Set<DeoEnderChest> enderChests;
    private Set<GuiEnderChest> guis;
    private File enderChestDataFolder;

    private EnderChestHandler(PluginDeoEnderChest plugin) {
        this.plugin = plugin;
        this.enderChests = new HashSet<>();
        this.guis = new HashSet<>();
        this.enderChestDataFolder =
                new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "enderchests");
        enderChestDataFolder.mkdir();
        startSaveTimer();
    }

    private void startSaveTimer() {
        long saveTicks = EnderChestConfig.getEnderChestConfig().getSaveTimeSeconds() * 20L;
        new BukkitRunnable() {

            @Override
            public void run() {
                saveAll();
            }

        }.runTaskTimer(plugin, saveTicks, saveTicks);
    }

    public void addEnderChest(DeoEnderChest enderChest) {
        enderChests.removeIf(echest -> echest.getOwnerUUID().equals(enderChest.getOwnerUUID()));
        enderChests.add(enderChest);
    }

    public void addGuiEnderChest(GuiEnderChest gui) {
        guis.add(gui);
    }

    public DeoEnderChest getEnderChest(Player player) {
        return enderChests.stream().filter(echest -> echest.getOwnerUUID().equals(player.getUniqueId())).findFirst().orElse(null);
    }

    public GuiEnderChest getGuiEnderChest(Inventory inventory) {
        return guis.stream().filter(gui -> gui.getInventory().equals(inventory)).findFirst().orElse(null);
    }

    public void saveAll() {
        new Thread(() -> enderChests.forEach(DeoEnderChest::save)).start();
    }

    public void createEnderChest(Player player) {
        DeoEnderChest.createEmptyEnderChest(plugin, player);
    }

    public File getEnderChestDataFolder() {
        return enderChestDataFolder;
    }

    public static EnderChestHandler getEnderChestHandler() {
        return enderChestHandler;
    }

    public static void init(PluginDeoEnderChest plugin) {
        enderChestHandler = new EnderChestHandler(plugin);
        DeoEnderChest.loadEnderChests(plugin);
    }

}
