package treasure_hunter.treasure_hunter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Objects;

public class MultireviveAnimation {
    private Player player;
    private ArrayList<Entity> TextureList = new ArrayList<>();
    private int State;
    private int TotemCount = 0;
    private GameData gameData;
    private Treasure_Hunter main;

    public MultireviveAnimation(Treasure_Hunter treasureHunter, GameData gameData, Player p) {
        main = treasureHunter;
        this.gameData = gameData;
        player = p;
        State = 1;
        createTextures();
    }

    public void createTextures() {
        int i;
        if (gameData.getRedPlayerList().contains(player.getName())) {
            for (i = 0; i < gameData.getRedPlayerList().size(); i++) {
                if (Bukkit.getPlayerExact(gameData.getRedPlayerList().get(i)) != null) {
                    if (Objects.requireNonNull(Bukkit.getPlayer(gameData.getRedPlayerList().get(i))).getScoreboardTags().contains("dead")) {
                        TotemCount++;
                    }
                }
            }
        }else {
            for (i = 0; i < gameData.getBluePlayerList().size(); i++) {
                if (Bukkit.getPlayerExact(gameData.getBluePlayerList().get(i)) != null) {
                    if (Objects.requireNonNull(Bukkit.getPlayer(gameData.getBluePlayerList().get(i))).getScoreboardTags().contains("dead")) {
                        TotemCount++;
                    }
                }
            }
        }
        for (i = 0; i < TotemCount; i++) {
            TextureList.add(player.getWorld().spawn(player.getLocation(), ArmorStand.class, entity -> {
                Objects.requireNonNull(entity.getEquipment()).setHelmet(new ItemStack(Material.TOTEM_OF_UNDYING, 1));
                entity.setGravity(false);
                entity.setInvulnerable(true);
                entity.setVisible(false);
                entity.addScoreboardTag("MultireviveTexture");
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
            }));
        }
    }

    public void startAnimation() {
        new BukkitRunnable() {
            int number = 0;
            int i;
            @Override
            public void run() {
                double maxRadius = getMaxRadius(loc);
                if (State == 3) {
                    double radius = 
                    endAnimation();
                    cancel();
                    return;
                }
                if (State == 4) {
                    reviveAnimation();
                    cancel();
                    return;
                }
                if (number <= 90) {
                    for (i = 0; i < TextureList.size(); i++) {
                        Entity texture = TextureList.get(i);
                        Location loc = player.getLocation();
                        double x = 0;
                        double y = 0;
                        double z = 0;
                        texture.teleport(new Location(loc.getWorld(), x, y, z));
                    }
                    number++;
                }else {
                    if (State == 1) {
                        State = 2;
                    }
                    Animation();
                    cancel();
                }
            }
        }.runTaskTimer(main, 0L, 1L);
    }

    public void Animation() {
        new BukkitRunnable() {
            int number = 0;
            @Override
            public void run() {
                number++;
                if (State == 3) {
                    endAnimation();
                    cancel();
                }
                if (State == 4) {
                    reviveAnimation();
                    cancel();
                }
            }
        }.runTaskTimer(main, 0L, 1L);
    }

    public void endAnimation() {
        new BukkitRunnable() {
            int number = 0;
            @Override
            public void run() {
                number++;
                if (State == 4) {
                    reviveAnimation();
                    cancel();
                }
                if (State == 1) {
                    startAnimation();
                    cancel();
                }
            }
        }.runTaskTimer(main, 0L, 1L);
    }

    public void endAnimation(int radius) {
        new BukkitRunnable() {
            int number = 0;
            @Override
            public void run() {
                number++;
                if (State == 4) {
                    reviveAnimation();
                    cancel();
                }
                if (State == 1) {
                    startAnimation();
                    cancel();
                }
            }
        }.runTaskTimer(main, 0L, 1L);
    }

    public void reviveAnimation() {

    }

    public double getMaxRadius(Location loc) {
        double maxRadius = 0.5;
        if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.EAST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.EAST).getType().equals(Material.WATER)) {
            if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.WEST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.WEST).getType().equals(Material.WATER)) {
                if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH).getType().equals(Material.WATER)) {
                    if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH).getType().equals(Material.WATER)) {
                        if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_EAST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_EAST).getType().equals(Material.WATER)) {
                            if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_WEST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_WEST).getType().equals(Material.WATER)) {
                                if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_EAST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_EAST).getType().equals(Material.WATER)) {
                                    if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_WEST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_WEST).getType().equals(Material.WATER)) {
                                        if (loc.getBlock().getRelative(BlockFace.EAST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.EAST).getType().equals(Material.WATER)) {
                                            if (loc.getBlock().getRelative(BlockFace.WEST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.WEST).getType().equals(Material.WATER)) {
                                                if (loc.getBlock().getRelative(BlockFace.NORTH).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.NORTH).getType().equals(Material.WATER)) {
                                                    if (loc.getBlock().getRelative(BlockFace.SOUTH).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.SOUTH).getType().equals(Material.WATER)) {
                                                        if (loc.getBlock().getRelative(BlockFace.SOUTH_EAST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.SOUTH_EAST).getType().equals(Material.WATER)) {
                                                            if (loc.getBlock().getRelative(BlockFace.SOUTH_WEST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.SOUTH_WEST).getType().equals(Material.WATER)) {
                                                                if (loc.getBlock().getRelative(BlockFace.NORTH_EAST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.NORTH_EAST).getType().equals(Material.WATER)) {
                                                                    if (loc.getBlock().getRelative(BlockFace.NORTH_WEST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.NORTH_WEST).getType().equals(Material.WATER)) {
                                                                        maxRadius = 1;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (maxRadius == 1) {
            if (loc.getBlock().getRelative(BlockFace.EAST, 2).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.EAST, 2).getType().equals(Material.WATER)) {
                if (loc.getBlock().getRelative(BlockFace.WEST, 2).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.WEST, 2).getType().equals(Material.WATER)) {
                    if (loc.getBlock().getRelative(BlockFace.NORTH, 2).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.NORTH, 2).getType().equals(Material.WATER)) {
                        if (loc.getBlock().getRelative(BlockFace.SOUTH, 2).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.SOUTH, 2).getType().equals(Material.WATER)) {
                            if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.EAST, 2).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.EAST, 2).getType().equals(Material.WATER)) {
                                if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.WEST, 2).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.WEST, 2).getType().equals(Material.WATER)) {
                                    if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH, 2).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH, 2).getType().equals(Material.WATER)) {
                                        if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH, 2).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH, 2).getType().equals(Material.WATER)) {
                                            if (loc.getBlock().getRelative(BlockFace.EAST_NORTH_EAST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.EAST_NORTH_EAST).getType().equals(Material.WATER)) {
                                                if (loc.getBlock().getRelative(BlockFace.EAST_SOUTH_EAST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.EAST_SOUTH_EAST).getType().equals(Material.WATER)) {
                                                    if (loc.getBlock().getRelative(BlockFace.WEST_NORTH_WEST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.WEST_NORTH_WEST).getType().equals(Material.WATER)) {
                                                        if (loc.getBlock().getRelative(BlockFace.WEST_SOUTH_WEST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.WEST_SOUTH_WEST).getType().equals(Material.WATER)) {
                                                            if (loc.getBlock().getRelative(BlockFace.NORTH_NORTH_EAST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.NORTH_NORTH_EAST).getType().equals(Material.WATER)) {
                                                                if (loc.getBlock().getRelative(BlockFace.SOUTH_SOUTH_EAST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.SOUTH_SOUTH_EAST).getType().equals(Material.WATER)) {
                                                                    if (loc.getBlock().getRelative(BlockFace.NORTH_NORTH_WEST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.NORTH_NORTH_WEST).getType().equals(Material.WATER)) {
                                                                        if (loc.getBlock().getRelative(BlockFace.SOUTH_SOUTH_WEST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.SOUTH_SOUTH_WEST).getType().equals(Material.WATER)) {
                                                                            if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.EAST_NORTH_EAST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.EAST_NORTH_EAST).getType().equals(Material.WATER)) {
                                                                                if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.EAST_SOUTH_EAST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.EAST_SOUTH_EAST).getType().equals(Material.WATER)) {
                                                                                    if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.WEST_NORTH_WEST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.WEST_NORTH_WEST).getType().equals(Material.WATER)) {
                                                                                        if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.WEST_SOUTH_WEST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.WEST_SOUTH_WEST).getType().equals(Material.WATER)) {
                                                                                            if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_NORTH_EAST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_NORTH_EAST).getType().equals(Material.WATER)) {
                                                                                                if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_SOUTH_EAST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_SOUTH_EAST).getType().equals(Material.WATER)) {
                                                                                                    if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_NORTH_WEST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_NORTH_WEST).getType().equals(Material.WATER)) {
                                                                                                        if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_SOUTH_WEST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_SOUTH_WEST).getType().equals(Material.WATER)) {
                                                                                                            maxRadius = 2;
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (maxRadius == 2) {
            if (loc.getBlock().getRelative(BlockFace.EAST, 3).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.EAST, 3).getType().equals(Material.WATER)) {
                if (loc.getBlock().getRelative(BlockFace.WEST, 3).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.WEST, 3).getType().equals(Material.WATER)) {
                    if (loc.getBlock().getRelative(BlockFace.NORTH, 3).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.NORTH, 3).getType().equals(Material.WATER)) {
                        if (loc.getBlock().getRelative(BlockFace.SOUTH, 3).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.SOUTH, 3).getType().equals(Material.WATER)) {
                            if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.EAST, 3).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.EAST, 3).getType().equals(Material.WATER)) {
                                if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.WEST, 3).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.WEST, 3).getType().equals(Material.WATER)) {
                                    if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH, 3).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH, 3).getType().equals(Material.WATER)) {
                                        if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH, 3).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH, 3).getType().equals(Material.WATER)) {
                                            if (loc.getBlock().getRelative(BlockFace.NORTH_EAST, 2).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.NORTH_EAST, 2).getType().equals(Material.WATER)) {
                                                if (loc.getBlock().getRelative(BlockFace.SOUTH_WEST, 2).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.SOUTH_WEST, 2).getType().equals(Material.WATER)) {
                                                    if (loc.getBlock().getRelative(BlockFace.NORTH_WEST, 2).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.NORTH_WEST, 2).getType().equals(Material.WATER)) {
                                                        if (loc.getBlock().getRelative(BlockFace.SOUTH_EAST, 2).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.SOUTH_EAST, 2).getType().equals(Material.WATER)) {
                                                            if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_EAST, 2).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_EAST, 2).getType().equals(Material.WATER)) {
                                                                if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_WEST, 2).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_WEST, 2).getType().equals(Material.WATER)) {
                                                                    if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_WEST, 2).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_WEST, 2).getType().equals(Material.WATER)) {
                                                                        if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_EAST, 2).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_EAST, 2).getType().equals(Material.WATER)) {
                                                                            if (loc.getBlock().getRelative(BlockFace.EAST_NORTH_EAST).getRelative(BlockFace.EAST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.EAST_NORTH_EAST).getRelative(BlockFace.EAST).getType().equals(Material.WATER)) {
                                                                                if (loc.getBlock().getRelative(BlockFace.EAST_SOUTH_EAST).getRelative(BlockFace.EAST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.EAST_SOUTH_EAST).getRelative(BlockFace.EAST).getType().equals(Material.WATER)) {
                                                                                    if (loc.getBlock().getRelative(BlockFace.WEST_NORTH_WEST).getRelative(BlockFace.WEST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.WEST_NORTH_WEST).getRelative(BlockFace.WEST).getType().equals(Material.WATER)) {
                                                                                        if (loc.getBlock().getRelative(BlockFace.WEST_SOUTH_WEST).getRelative(BlockFace.WEST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.WEST_SOUTH_WEST).getRelative(BlockFace.WEST).getType().equals(Material.WATER)) {
                                                                                            if (loc.getBlock().getRelative(BlockFace.NORTH_NORTH_EAST).getRelative(BlockFace.NORTH).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.NORTH_NORTH_EAST).getRelative(BlockFace.NORTH).getType().equals(Material.WATER)) {
                                                                                                if (loc.getBlock().getRelative(BlockFace.SOUTH_SOUTH_EAST).getRelative(BlockFace.SOUTH).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.SOUTH_SOUTH_EAST).getRelative(BlockFace.SOUTH).getType().equals(Material.WATER)) {
                                                                                                    if (loc.getBlock().getRelative(BlockFace.NORTH_NORTH_WEST).getRelative(BlockFace.NORTH).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.NORTH_NORTH_WEST).getRelative(BlockFace.NORTH).getType().equals(Material.WATER)) {
                                                                                                        if (loc.getBlock().getRelative(BlockFace.SOUTH_SOUTH_WEST).getRelative(BlockFace.SOUTH).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.SOUTH_SOUTH_WEST).getRelative(BlockFace.SOUTH).getType().equals(Material.WATER)) {
                                                                                                            if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.EAST_NORTH_EAST).getRelative(BlockFace.EAST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.EAST_NORTH_EAST).getRelative(BlockFace.EAST).getType().equals(Material.WATER)) {
                                                                                                                if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.EAST_SOUTH_EAST).getRelative(BlockFace.EAST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.EAST_SOUTH_EAST).getRelative(BlockFace.EAST).getType().equals(Material.WATER)) {
                                                                                                                    if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.WEST_NORTH_WEST).getRelative(BlockFace.WEST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.WEST_NORTH_WEST).getRelative(BlockFace.WEST).getType().equals(Material.WATER)) {
                                                                                                                        if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.WEST_SOUTH_WEST).getRelative(BlockFace.WEST).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.WEST_SOUTH_WEST).getRelative(BlockFace.WEST).getType().equals(Material.WATER)) {
                                                                                                                            if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_NORTH_EAST).getRelative(BlockFace.NORTH).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_NORTH_EAST).getRelative(BlockFace.NORTH).getType().equals(Material.WATER)) {
                                                                                                                                if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_SOUTH_EAST).getRelative(BlockFace.SOUTH).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_SOUTH_EAST).getRelative(BlockFace.SOUTH).getType().equals(Material.WATER)) {
                                                                                                                                    if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_NORTH_WEST).getRelative(BlockFace.NORTH).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.NORTH_NORTH_WEST).getRelative(BlockFace.NORTH).getType().equals(Material.WATER)) {
                                                                                                                                        if (loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_SOUTH_WEST).getRelative(BlockFace.SOUTH).getType().equals(Material.AIR) || loc.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH_SOUTH_WEST).getRelative(BlockFace.SOUTH).getType().equals(Material.WATER)) {
                                                                                                                                            maxRadius = 2.5;
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }   
                        }
                    }
                }
            }
        }
        return maxRadius;
    }

    public Player getPlayer() {
        return player;
    }
    public ArrayList<Entity> getTextureList() {
        return TextureList;
    }
}
