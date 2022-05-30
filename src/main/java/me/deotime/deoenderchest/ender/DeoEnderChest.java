package me.deotime.deoenderchest.ender;

import me.deotime.deoenderchest.PluginDeoEnderChest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.checkerframework.checker.units.qual.UnknownUnits;

import java.io.*;
import java.util.Base64;
import java.util.Scanner;
import java.util.UUID;

public class DeoEnderChest {

    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static final Base64.Decoder decoder = Base64.getDecoder();

    private ItemStack[] contents;
    private UUID ownerUUID;
    private PluginDeoEnderChest plugin;

    private DeoEnderChest(PluginDeoEnderChest plugin, ItemStack[] contents, UUID ownerUUID){
        this.contents = contents;
        this.plugin = plugin;
        this.ownerUUID = ownerUUID;
        EnderChestHandler.getEnderChestHandler().addEnderChest(this);
    }

    public void setContents(ItemStack[] contents) {
        this.contents = contents;
    }

    public ItemStack[] getContents() {
        return this.contents;
    }

    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    public void save() {
        File dataFolder = EnderChestHandler.getEnderChestHandler().getEnderChestDataFolder();
        File echestFile =
                new File(dataFolder.getAbsolutePath() + File.separator + ownerUUID + ".echest");
        try {
            FileWriter writer = new FileWriter(echestFile);
            writer.write(serialize());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String serialize() {
        try {
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            BukkitObjectOutputStream outputStream = new BukkitObjectOutputStream(byteOutput);
            outputStream.writeObject(contents);
            outputStream.writeObject(ownerUUID);
            return encoder.encodeToString(byteOutput.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DeoEnderChest deserialize(PluginDeoEnderChest plugin, String serial) {
        try {
            byte[] decoded = decoder.decode(serial.getBytes());
            ByteArrayInputStream byteInput = new ByteArrayInputStream(decoded);
            BukkitObjectInputStream inputStream = new BukkitObjectInputStream(byteInput);
            ItemStack[] contents = (ItemStack[]) inputStream.readObject();
            UUID ownerUUID = (UUID) inputStream.readObject();
            return new DeoEnderChest(plugin, contents, ownerUUID);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    static DeoEnderChest createEmptyEnderChest(PluginDeoEnderChest plugin, Player player) {
        int echestSize = EnderChestConfig.getEnderChestConfig().getEnderChestSize();
        return new DeoEnderChest(plugin, new ItemStack[echestSize], player.getUniqueId());
    }

    static void loadEnderChests(PluginDeoEnderChest plugin) {
        File[] echestFiles = EnderChestHandler.getEnderChestHandler().getEnderChestDataFolder().listFiles();
        if(echestFiles == null) return;

        for(File file : echestFiles) {
            try {
                Scanner scanner = new Scanner(file);
                String data = scanner.nextLine();
                deserialize(plugin, data);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
