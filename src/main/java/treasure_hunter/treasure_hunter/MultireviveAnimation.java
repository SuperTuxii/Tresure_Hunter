package treasure_hunter.treasure_hunter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
    public Player player;
    public ArrayList<Entity> TextureList = new ArrayList<>();
    public int State;
    public int TotemCount = 0;
    public GameData gameData;
    public Treasure_Hunter main;

    public MultireviveAnimation(Treasure_Hunter treasureHunter, GameData gameData, Player p) {
        System.out.println("Animation created");
        main = treasureHunter;
        this.gameData = gameData;
        player = p;
        State = 1;
        createTextures();
        if (TotemCount != 0) {
            System.out.println("Starting Animation");
            startAnimation();
            updateTotemCount();
        }else {
            waitForDeath();
        }
    }

    public void waitForDeath() {
        new BukkitRunnable() {
            @Override
            public void run() {
                int i;
                TotemCount = 0;
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
                if (TotemCount != 0) {
                    createTextures();
                    startAnimation();
                    updateTotemCount();
                    cancel();
                }
            }
        }.runTaskTimer(main, 0L, 1L);
    }

    public void createTextures() {
        int i;
        TotemCount = 0;
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
        TotemCount = 3;
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
        System.out.println("createdTextures");
    }

    public void startAnimation() {
        System.out.println("startAnimation");
        new BukkitRunnable() {
            int number = 0;
            int i;
            final double maxRadius = getMaxRadius(player.getLocation());
            @Override
            public void run() {
                Location loc = player.getLocation();
                if (State == 3) {
                    double radius = maxRadius * Math.sin(number * Math.PI / 180);
                    endAnimation(number, radius);
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
                        double x = maxRadius * Math.sin(number * Math.PI / 180) * Math.sin(number * Math.PI / 90 + 2 * Math.PI / TextureList.size() * (i + 1));
                        x += loc.getX();
                        double y = loc.getY() - 2;
                        double z = maxRadius * Math.sin(number * Math.PI / 180) * Math.cos(number * Math.PI / 90 + 2 * Math.PI / TextureList.size() * (i + 1));
                        z += loc.getZ();
                        texture.teleport(new Location(loc.getWorld(), x, y, z));
                    }
                    number++;
                }else {
                    if (State == 1) {
                        State = 2;
                    }
                    Animation(number);
                    cancel();
                }
            }
        }.runTaskTimer(main, 0L, 1L);
    }

    public void startAnimation(int n, double radius) {
        System.out.println("startAnimation2");
        new BukkitRunnable() {
            int number = n;
            int i;
            @Override
            public void run() {
                Location loc = player.getLocation();
                double maxRadius = getMaxRadius(loc);
                if (maxRadius >= radius) {
                    maxRadius = radius;
                }
                int nn = number - n;
                if (State == 3) {
                    double radius = maxRadius * Math.sin(number * Math.PI / 180);
                    endAnimation(number, radius);
                    cancel();
                    return;
                }
                if (State == 4) {
                    reviveAnimation();
                    cancel();
                    return;
                }
                if (nn <= 90) {
                    for (i = 0; i < TextureList.size(); i++) {
                        Entity texture = TextureList.get(i);
                        double x = maxRadius * Math.sin(number * Math.PI / 180) * Math.sin(number * Math.PI / 90 + 2 * Math.PI / TextureList.size() * (i + 1));
                        x += loc.getX();
                        double y = loc.getY() - 2;
                        double z = maxRadius * Math.sin(number * Math.PI / 180) * Math.cos(number * Math.PI / 90 + 2 * Math.PI / TextureList.size() * (i + 1));
                        z += loc.getZ();
                        texture.teleport(new Location(loc.getWorld(), x, y, z));
                    }
                    number++;
                }else {
                    if (State == 1) {
                        State = 2;
                    }
                    Animation(number);
                    cancel();
                }
            }
        }.runTaskTimer(main, 0L, 1L);
    }

    public void Animation(int n) {
        System.out.println("Animation");
        new BukkitRunnable() {
            int number = n;
            int i;
            @Override
            public void run() {
                Location loc = player.getLocation();
                double maxRadius = getMaxRadius(loc);
                for (i = 0; i < TextureList.size(); i++) {
                    Entity texture = TextureList.get(i);
                    double x = maxRadius * Math.sin(number * Math.PI / 90 + 2 * Math.PI / TextureList.size() * (i + 1));
                    x += loc.getX();
                    double y = loc.getY() - 2;
                    double z = maxRadius * Math.cos(number * Math.PI / 90 + 2 * Math.PI / TextureList.size() * (i + 1));
                    z += loc.getZ();
                    texture.teleport(new Location(loc.getWorld(), x, y, z));
                }
                number++;
                if (State == 3) {
                    endAnimation(number);
                    cancel();
                }
                if (State == 4) {
                    reviveAnimation();
                    cancel();
                }
            }
        }.runTaskTimer(main, 0L, 1L);
    }

    public void endAnimation(int n) {
        System.out.println("EndAnimation");
        new BukkitRunnable() {
            int number = 40;
            int number2 = n;
            int i;
            @Override
            public void run() {
                Location loc = player.getLocation();
                double maxRadius = getMaxRadius(loc);
                int n2n = number2 - n;
                if (number >= 0 && n2n <= 40) {
                    for (i = 0; i < TextureList.size(); i++) {
                        Entity texture = TextureList.get(i);
                        double x = maxRadius * (Math.sin(-(number * Math.PI / 80)) + 1) * Math.sin(number2 * Math.PI / 90 + 2 * Math.PI / TextureList.size() * (i + 1));
                        x += loc.getX();
                        double y = loc.getY() - 1;
                        double z = maxRadius * (Math.sin(-(number * Math.PI / 80)) + 1) * Math.cos(number2 * Math.PI / 90 + 2 * Math.PI / TextureList.size() * (i + 1));
                        z += loc.getZ();
                        texture.teleport(new Location(loc.getWorld(), x, y, z));
                    }
                    number2++;
                    number--;
                }else {
                    if (State != 0) {
                        State = 0;
                    }
                    cancel();
                    deleteClass();
                    return;
                }
                if (State == 4) {
                    reviveAnimation();
                    cancel();
                }
                if (State == 1) {
                    double radius = maxRadius * (Math.sin(-(number * Math.PI / 80)) + 1);
                    startAnimation(number2, radius);
                    cancel();
                }
            }
        }.runTaskTimer(main, 0L, 1L);
    }

    public void endAnimation(int n, double radius) {
        System.out.println("EndAnimation2");
        new BukkitRunnable() {
            int number = 40;
            int number2 = n;
            int i;
            @Override
            public void run() {
                Location loc = player.getLocation();
                double maxRadius = getMaxRadius(loc);
                if (maxRadius >= radius) {
                    maxRadius = radius;
                }
                int n2n = number2 - n;
                if (number >= 0 && n2n <= 40) {
                    for (i = 0; i < TextureList.size(); i++) {
                        Entity texture = TextureList.get(i);
                        double x = maxRadius * (Math.sin(-(number * Math.PI / 80)) + 1) * Math.sin(number2 * Math.PI / 90 + 2 * Math.PI / TextureList.size() * (i + 1));
                        x += loc.getX();
                        double y = loc.getY() - 1;
                        double z = maxRadius * (Math.sin(-(number * Math.PI / 80)) + 1) * Math.cos(number2 * Math.PI / 90 + 2 * Math.PI / TextureList.size() * (i + 1));
                        z += loc.getZ();
                        texture.teleport(new Location(loc.getWorld(), x, y, z));
                    }
                    number2++;
                    number--;
                }else {
                    if (State != 0) {
                        State = 0;
                    }
                    cancel();
                    deleteClass();
                    return;
                }
                if (State == 4) {
                    reviveAnimation();
                    cancel();
                }
                if (State == 1) {
                    double radius = maxRadius * (Math.sin(-(number * Math.PI / 80)) + 1);
                    startAnimation(number2, radius);
                    cancel();
                }
            }
        }.runTaskTimer(main, 0L, 1L);
    }

    public void reviveAnimation() {
        int i;
        ArrayList<Player> TotenList = new ArrayList<>();
        if (gameData.getRedPlayerList().contains(player.getName())) {
            for (i = 0; i < gameData.getRedPlayerList().size(); i++) {
                if (Bukkit.getPlayerExact(gameData.getRedPlayerList().get(i)) != null) {
                    if (Objects.requireNonNull(Bukkit.getPlayer(gameData.getRedPlayerList().get(i))).getScoreboardTags().contains("dead")) {
                        TotenList.add(Objects.requireNonNull(Bukkit.getPlayer(gameData.getRedPlayerList().get(i))));
                    }
                }
            }
        }else {
            for (i = 0; i < gameData.getBluePlayerList().size(); i++) {
                if (Bukkit.getPlayerExact(gameData.getBluePlayerList().get(i)) != null) {
                    if (Objects.requireNonNull(Bukkit.getPlayer(gameData.getRedPlayerList().get(i))).getScoreboardTags().contains("dead")) {
                        TotenList.add(Objects.requireNonNull(Bukkit.getPlayer(gameData.getRedPlayerList().get(i))));
                    }
                }
            }
        }
        for (i = 0; i < TotenList.size(); i++) {
            TotenList.get(i).teleport(TextureList.get(i).getLocation());
        }
        deleteTextures();
        deleteClass();
    }

    public void deleteClass() {
        int i;
        for (i = 0; i < gameData.getMultireviveAnimationList().size(); i++) {
            if (gameData.getMultireviveAnimationList().get(i).getPlayer().getName().equals(player.getName())) {
                gameData.getMultireviveAnimationList().get(i).deleteTextures();
                gameData.getMultireviveAnimationList().remove(i);
                return;
            }
        }
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

    public void updateTotemCount() {
        new BukkitRunnable() {
            @Override
            public void run() {
                int i;
                TotemCount = 0;
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
                TotemCount = 3;
                if (TotemCount != TextureList.size()) {
                    deleteTextures();
                    createTextures();
                }
            }
        }.runTaskTimer(main, 0L, 1L);
    }

    public void deleteTextures() {
        int i;
        for (i = 0; i < TextureList.size(); i++) {
            TextureList.get(i).remove();
            TextureList.remove(i);
            i--;
        }
    }

    public void setState(int state) {
        State = state;
    }

    public Player getPlayer() {
        return player;
    }

    public String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
