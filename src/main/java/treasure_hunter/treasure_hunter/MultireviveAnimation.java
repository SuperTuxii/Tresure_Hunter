package treasure_hunter.treasure_hunter;

import org.bukkit.*;
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
    public double maxRadius = 1;
    public GameData gameData;
    public Treasure_Hunter main;

    public MultireviveAnimation(Treasure_Hunter treasureHunter, GameData gameData, Player p) {
        main = treasureHunter;
        this.gameData = gameData;
        player = p;
        State = 1;
        createTextures();
        if (TotemCount != 0) {
            startAnimation();
            updateTotemCount();
        }
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
        if (TotemCount <= 3) {
            TotemCount = 3;
        }
        for (i = 0; i < TotemCount; i++) {
            TextureList.add(player.getWorld().spawn(player.getLocation().add(0, -1, 0), ArmorStand.class, entity -> {
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
                Location loc = player.getLocation();
                if (State == 4) {
                    if (getTotenList().size() > 0) {
                        reviveAnimation();
                        cancel();
                    }else {
                        State = 1;
                    }
                    return;
                }
                if (number <= 90) {
                    for (i = 0; i < TextureList.size(); i++) {
                        Entity texture = TextureList.get(i);
                        double RadiusChange = number * Math.PI;
                        RadiusChange /= 180;
                        RadiusChange %= 2 * Math.PI;
                        RadiusChange = Math.sin(RadiusChange);
                        double WinkelChange = number * Math.PI;
                        WinkelChange /= 90;
                        WinkelChange += 2 * Math.PI * (i + 1) / TextureList.size();
                        WinkelChange %= 2 * Math.PI;
                        double x = maxRadius * RadiusChange * Math.sin(WinkelChange);
                        x += loc.getX();
                        double y = loc.getY() - 1;
                        double z = maxRadius * RadiusChange * Math.cos(WinkelChange);
                        z += loc.getZ();
                        texture.teleport(new Location(loc.getWorld(), x, y, z));
                        texture.teleport(faceLocation(texture, player.getLocation()));
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
        new BukkitRunnable() {
            int number = n;
            int i;
            @Override
            public void run() {
                Location loc = player.getLocation();
                for (i = 0; i < TextureList.size(); i++) {
                    Entity texture = TextureList.get(i);
                    double WinkelChange = number * Math.PI;
                    WinkelChange /= 90;
                    WinkelChange += 2 * Math.PI * (i + 1) / TextureList.size();
                    WinkelChange %= 2 * Math.PI;
                    double x = maxRadius * Math.sin(WinkelChange);
                    x += loc.getX();
                    double y = loc.getY() - 1;
                    double z = maxRadius * Math.cos(WinkelChange);
                    z += loc.getZ();
                    texture.teleport(new Location(loc.getWorld(), x, y, z));
                    texture.teleport(faceLocation(texture, player.getLocation()));
                }
                number++;
                if (State == 3) {
                    endAnimation(number);
                    cancel();
                }
                if (State == 4) {
                    if (getTotenList().size() > 0) {
                        reviveAnimation();
                        cancel();
                    }else {
                        State = 2;
                    }
                }
            }
        }.runTaskTimer(main, 0L, 1L);
    }

    public void endAnimation(int n) {
        new BukkitRunnable() {
            int number = 40;
            int number2 = n;
            int i;
            @Override
            public void run() {
                Location loc = player.getLocation();
                if (number >= 0) {
                    for (i = 0; i < TextureList.size(); i++) {
                        Entity texture = TextureList.get(i);
                        double RadiusChange = number * Math.PI;
                        RadiusChange /= 80;
                        RadiusChange %= 2 * Math.PI;
                        RadiusChange = Math.sin(RadiusChange);
                        double WinkelChange = number2 * Math.PI;
                        WinkelChange /= 90;
                        WinkelChange += 2 * Math.PI * (i + 1) / TextureList.size();
                        WinkelChange %= 2 * Math.PI;
                        double x = maxRadius * RadiusChange * Math.sin(WinkelChange);
                        x += loc.getX();
                        double y = loc.getY() - 1;
                        double z = maxRadius * RadiusChange * Math.cos(WinkelChange);
                        z += loc.getZ();
                        texture.teleport(new Location(loc.getWorld(), x, y, z));
                        texture.teleport(faceLocation(texture, player.getLocation()));
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
                    if (getTotenList().size() > 0) {
                        reviveAnimation();
                        cancel();
                    }else {
                        State = 3;
                    }
                }
            }
        }.runTaskTimer(main, 0L, 1L);
    }

    public void reviveAnimation() {
        int i;
        int i2;
        ArrayList<Player> TotenList = getTotenList();
        for (i = 0; i < TotenList.size(); i++) {
            TotenList.get(i).teleport(TextureList.get(i).getLocation().add(0, 1, 0));
            TotenList.get(i).playEffect(EntityEffect.TOTEM_RESURRECT);
            TotenList.get(i).setGameMode(GameMode.ADVENTURE);
            TotenList.get(i).setHealth(20);
            TotenList.get(i).removeScoreboardTag("dead");
            for (i2 = 0; i2 < gameData.getCorpseList().size(); i2++) {
                if (gameData.getCorpseList().get(i2).getPlayer().getName().equals(TotenList.get(i).getName())) {
                    gameData.getCorpseList().get(i2).updatePlayerInventory();
                    gameData.getCorpseList().get(i2).removeTexture();
                    gameData.getCorpseList().remove(i2);
                    i2--;
                }
            }
        }
        player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        deleteTextures();
        deleteClass();
    }

    public ArrayList<Player> getTotenList() {
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
                    if (Objects.requireNonNull(Bukkit.getPlayer(gameData.getBluePlayerList().get(i))).getScoreboardTags().contains("dead")) {
                        TotenList.add(Objects.requireNonNull(Bukkit.getPlayer(gameData.getBluePlayerList().get(i))));
                    }
                }
            }
        }
        return TotenList;
    }

    public void deleteClass() {
        deleteTextures();
        int i;
        for (i = 0; i < gameData.getMultireviveAnimationList().size(); i++) {
            if (gameData.getMultireviveAnimationList().get(i).getPlayer().getName().equals(player.getName())) {
                gameData.getMultireviveAnimationList().remove(i);
                return;
            }
        }
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
                if (TotemCount <= 3) {
                    TotemCount = 3;
                }
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
        }
    }

    public Location faceLocation(Entity entity, Location to) {
        if (entity.getWorld() != to.getWorld()) {
            return null;
        }
        Location fromLocation = entity.getLocation();

        double xDiff = to.getX() - fromLocation.getX();
        double zDiff = to.getZ() - fromLocation.getZ();

        double distanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);

        if (distanceXZ == 0) {
            return entity.getLocation();
        }

        double yaw = Math.toDegrees(Math.acos(xDiff / distanceXZ));
        if (zDiff < 0.0D) {
            yaw += Math.abs(180.0D - yaw) * 2.0D;
        }
        Location loc = entity.getLocation();
        loc.setYaw((float) (yaw - 90.0F));
        return loc;
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
