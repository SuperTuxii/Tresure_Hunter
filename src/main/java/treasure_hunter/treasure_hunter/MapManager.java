package treasure_hunter.treasure_hunter;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Objects;

public class MapManager {

    Treasure_Hunter main;
    public MapManager(Treasure_Hunter treasure_hunter) {
        main = treasure_hunter;
    }

    public void addMap(Player p) {
        int MapNumber = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("MapCount").getScore() + 1;
        Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("MapCount").setScore(MapNumber);
        setMapDefaults(MapNumber);
        if (p != null) {
            p.sendMessage(format("&aCreated new Map with MapNumber: " + MapNumber));
        }
    }

    public String getMapName(int MapNumber) {
        for (String name : main.mainScoreboard.getEntries()) {
            if (Objects.requireNonNull(main.mainScoreboard.getObjective("TH_MapNames")).getScore(name).isScoreSet()) {
                if (Objects.requireNonNull(main.mainScoreboard.getObjective("TH_MapNames")).getScore(name).getScore() == MapNumber) {
                    return name;
                }
            }
        }
        return null;
    }

    public void setMapDefaults(int MapNumber) {
        Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "CustomModelData").setScore(1);
        Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "BlauSpawnX").setScore(0);
        Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "BlauSpawnY").setScore(0);
        Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "BlauSpawnZ").setScore(0);
        Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "RotSpawnX").setScore(0);
        Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "RotSpawnY").setScore(0);
        Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "RotSpawnZ").setScore(0);

    }

    public void setCustomModelData(int MapNumber, int CustomModelData, Player p) {
        Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "CustomModelData").setScore(CustomModelData);
        p.sendMessage(format("&aCustomModelData for preview item set to " + CustomModelData));
    }

    public void setSpawn(int MapNumber, boolean isBlue, int x, int y, int z, Player p) {
        if (isBlue) {
            if (p != null) {
                p.sendMessage(format("&aSpawn for blue team set to " + x + " " + y + " " + z));
            }
            Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "BlauSpawnX").setScore(x);
            Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "BlauSpawnY").setScore(y);
            Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "BlauSpawnZ").setScore(z);
        }else {
            if (p != null) {
                p.sendMessage(format("&aSpawn for red team set to " + x + " " + y + " " + z));
            }
            Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "RotSpawnX").setScore(x);
            Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "RotSpawnY").setScore(y);
            Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "RotSpawnZ").setScore(z);
        }
    }

    public void setTreasureSpawn(int MapNumber, int TreasureNumber, int x, int y, int z, int r, Player p) {
        if (p != null) {
            p.sendMessage(format("&aTreasure Spawnpoint " + TreasureNumber + " set to " + x + " " + y + " " + z));
        }
        Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "TreasureSpawn" + TreasureNumber + "X").setScore(x);
        Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "TreasureSpawn" + TreasureNumber + "Y").setScore(y);
        Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "TreasureSpawn" + TreasureNumber + "Z").setScore(z);
        Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "TreasureSpawn" + TreasureNumber + "R").setScore(r);
    }

    public void setShopSpawn(int MapNumber, int ShopNumber, int x, int y, int z, Player p) {
        if (p != null) {
            p.sendMessage(format("&aShop Spawnpoint " + ShopNumber + " set to " + x + " " + y + " " + z));
        }
        Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "ShopSpawn" + ShopNumber + "X").setScore(x);
        Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "ShopSpawn" + ShopNumber + "Y").setScore(y);
        Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "ShopSpawn" + ShopNumber + "Z").setScore(z);
    }

    public String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
