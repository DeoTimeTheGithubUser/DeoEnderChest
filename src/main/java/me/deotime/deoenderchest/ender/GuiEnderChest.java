package me.deotime.deoenderchest.ender;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class GuiEnderChest {

    private final Player player;
    private Inventory inventory;
    private DeoEnderChest enderChest;

    private GuiEnderChest(Player player) {
        this.player = player;

        EnderChestConfig config = EnderChestConfig.getEnderChestConfig();
        this.inventory = Bukkit.createInventory(null, config.getEnderChestSize(),
                config.getEnderChestName().replaceAll("&", "\u00a7"));
        this.enderChest = EnderChestHandler.getEnderChestHandler().getEnderChest(player);
        inventory.setContents(enderChest.getContents());
        EnderChestHandler.getEnderChestHandler().addGuiEnderChest(this);
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public DeoEnderChest getEnderChest() {
        return enderChest;
    }

    public static void open(Player player) {
        if(!isPlayerInventoryType(player.getOpenInventory().getType())) return;
        GuiEnderChest enderChest = new GuiEnderChest(player);
        player.openInventory(enderChest.getInventory());
        player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 100f, 1f);
    }

    private static boolean isPlayerInventoryType(InventoryType type) {
        return type == InventoryType.CRAFTING || type == InventoryType.CREATIVE;
    }

}
