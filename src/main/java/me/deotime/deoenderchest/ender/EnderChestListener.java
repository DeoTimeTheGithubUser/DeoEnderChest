package me.deotime.deoenderchest.ender;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class EnderChestListener implements Listener {

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(event.getClickedBlock() == null || event.getClickedBlock().getType() != Material.ENDER_CHEST) return;
        GuiEnderChest.open(event.getPlayer());
        event.setCancelled(true);
    }

    @EventHandler
    private void onInventoryClose(InventoryCloseEvent event) {
        GuiEnderChest gui = EnderChestHandler.getEnderChestHandler().getGuiEnderChest(event.getInventory());
        if(gui== null) return;
        gui.getEnderChest().setContents(gui.getInventory().getContents());
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        if(EnderChestHandler.getEnderChestHandler().getEnderChest(event.getPlayer()) == null)
            EnderChestHandler.getEnderChestHandler().createEnderChest(event.getPlayer());
    }

}
