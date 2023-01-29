package treasure_hunter.treasure_hunter;

import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.util.*;

public class Corpse implements InventoryHolder {
    Treasure_Hunter main;
    double PosX;
    double PosY;
    double PosZ;
    Location position;
    Inventory inventory;
    Entity texture;
    Player player;
    World world;
    boolean isBlue;
    boolean Revivable;
    boolean Reviving;

    public Corpse(Location position, Player player, int reviveTime, boolean isBlue, Treasure_Hunter main) {
        this.main = main;
        this.position = position;
        PosX = position.getX();
        PosY = position.getY();
        PosZ = position.getZ();
        world = position.getWorld();
        this.player = player;
        this.isBlue = isBlue;
        Revivable = true;
        Reviving = false;
        createTexture();
        createInventory();
        updateCorpseInventory();
        ReviveTimer(reviveTime);
    }

    public Entity getTexture() {
        return texture;
    }

    public void createTexture() {
        float Rotation = player.getLocation().getYaw();
        if (Rotation < 0) {
            Rotation += 360;
        }
        texture = world.spawn(new Location(world, PosX + (0.42 * Math.sin(Math.toRadians(Rotation))), PosY - 1.3, PosZ - (0.42 * Math.cos(Math.toRadians(Rotation))), Rotation, 0), ArmorStand.class, entity -> {
            ItemStack item = new ItemStack(Material.STICK);
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            if (isBlue) {
                meta.setCustomModelData(1);
            }else {
                meta.setCustomModelData(2);
            }
            item.setItemMeta(meta);
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta headMeta = (SkullMeta) head.getItemMeta();
            assert headMeta != null;
            headMeta.setOwningPlayer(player);
            head.setItemMeta(headMeta);
            Objects.requireNonNull(entity.getEquipment()).setItemInOffHand(item);
            entity.setGravity(false);
            entity.setInvulnerable(true);
            entity.setArms(true);
            entity.setVisible(false);
            entity.setBasePlate(false);
            entity.setPersistent(true);
            entity.addScoreboardTag("corpse");
            entity.setCustomName(player.getName());
            entity.setCustomNameVisible(true);
            Objects.requireNonNull(entity.getEquipment()).setHelmet(head);
            EulerAngle angle = new EulerAngle(Math.toRadians(270.0), 0.0, 0.0);
            entity.setHeadPose(angle);
            entity.setLeftArmPose(angle);
            entity.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.ADDING_OR_CHANGING);
            entity.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.REMOVING_OR_CHANGING);
            entity.addEquipmentLock(EquipmentSlot.OFF_HAND, ArmorStand.LockType.ADDING_OR_CHANGING);
            entity.addEquipmentLock(EquipmentSlot.OFF_HAND, ArmorStand.LockType.REMOVING_OR_CHANGING);
            entity.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.ADDING_OR_CHANGING);
            entity.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.REMOVING_OR_CHANGING);
            entity.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.ADDING_OR_CHANGING);
            entity.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.REMOVING_OR_CHANGING);
            entity.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.ADDING_OR_CHANGING);
            entity.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.REMOVING_OR_CHANGING);
            entity.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.ADDING_OR_CHANGING);
            entity.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.REMOVING_OR_CHANGING);
        });
    }

    public void removeTexture() {
        texture.remove();
    }

    public void createInventory() {
        inventory = Bukkit.createInventory(this, 45, format("&8Corpse"));
    }

    public void updateCorpseInventory() {
        int i;
        inventory.setItem(2, player.getInventory().getItemInOffHand());
        inventory.setItem(3, player.getInventory().getHelmet());
        inventory.setItem(4, player.getInventory().getChestplate());
        inventory.setItem(5, player.getInventory().getLeggings());
        inventory.setItem(6, player.getInventory().getBoots());
        for (i = 9; i < 36; i++) {
            inventory.setItem(i, player.getInventory().getItem(i));
        }
        for (i = 0; i < 9; i++) {
            inventory.setItem(i + 36, player.getInventory().getItem(i));
        }
    }

    public void updatePlayerInventory() {
        int i;
        player.getInventory().setItemInOffHand(inventory.getItem(2));
        player.getInventory().setHelmet(inventory.getItem(3));
        player.getInventory().setChestplate(inventory.getItem(4));
        player.getInventory().setLeggings(inventory.getItem(5));
        player.getInventory().setBoots(inventory.getItem(6));
        for (i = 9; i < 36; i++) {
            player.getInventory().setItem(i, inventory.getItem(i));
        }
        for (i = 0; i < 9; i++) {
            player.getInventory().setItem(i, inventory.getItem(i + 36));
        }
    }

    public void setIdentity(boolean visible) {
        if (visible) {
            texture.setCustomNameVisible(true);
            ItemStack item = new ItemStack(Material.STICK);
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            if (isBlue) {
                meta.setCustomModelData(1);
            }else {
                meta.setCustomModelData(2);
            }
            item.setItemMeta(meta);
            Objects.requireNonNull(((ArmorStand) texture).getEquipment()).setItemInOffHand(item);
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta headMeta = (SkullMeta) head.getItemMeta();
            assert headMeta != null;
            headMeta.setOwningPlayer(player);
            head.setItemMeta(headMeta);
            Objects.requireNonNull(((ArmorStand) texture).getEquipment()).setHelmet(head);
        }else {
            texture.setCustomNameVisible(false);
            ItemStack item = new ItemStack(Material.STICK);
            ItemMeta meta = item.getItemMeta();
            assert meta != null;
            meta.setCustomModelData(3);
            item.setItemMeta(meta);
            Objects.requireNonNull(((ArmorStand) texture).getEquipment()).setItemInOffHand(item);
            Objects.requireNonNull(((ArmorStand) texture).getEquipment()).setHelmet(new ItemStack(Material.AIR));
        }
    }

    public void OpenCorpse(Player p) {
        p.openInventory(inventory);
    }

    public void ReviveTimer(int reviveTime) {
        new BukkitRunnable() {
            int number = reviveTime;
            @Override
            public void run() {
                if (number > 0) {
                    number--;
                }else {
                    setIdentity(false);
                    Revivable = false;
                    cancel();
                }
            }
        }.runTaskTimer(main, 0L, 20L);
    }

    public double getPosX() {
        return PosX;
    }
    public double getPosY() {
        return PosY;
    }
    public double getPosZ() {
        return PosZ;
    }
    public Location getPosition() {
        return position;
    }
    public Player getPlayer() {
        return player;
    }
    public boolean isReviving() {
        return Reviving;
    }
    public void setReviving(boolean reviving) {
        Reviving = reviving;
    }
    public boolean isRevivable() {
        return Revivable;
    }

    public String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
