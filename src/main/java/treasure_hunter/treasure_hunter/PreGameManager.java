package treasure_hunter.treasure_hunter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class PreGameManager implements Listener {

    GameManager gameManager;

    public PreGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    private final Random random = new Random();

    public void MapChoosing(int GameDataNumber) {
        if (Objects.requireNonNull(gameManager.main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
            int pi;
            for (pi = 0; pi < gameManager.getGameDataList().get(GameDataNumber).getPlayerList().size(); pi++) {
                gameManager.getGameDataList().get(GameDataNumber).getPlayerList().get(pi).sendMessage(format("&6Debug: Map Wahl Funktion gestartet"));
            }
        }
        gameManager.getGameDataList().get(GameDataNumber).setGamestate(2);
        if (Objects.requireNonNull(gameManager.main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
            int pi;
            for (pi = 0; pi < gameManager.getGameDataList().get(GameDataNumber).getPlayerList().size(); pi++) {
                gameManager.getGameDataList().get(GameDataNumber).getPlayerList().get(pi).sendMessage(format("&6Debug: Gamestate auf 2 gesetzt"));
            }
        }
        int[] Maps = choose3randomMaps();
        if (Maps == null) {
            int i;
            for (i = 0; i < gameManager.getGameDataList().get(GameDataNumber).getPlayerList().size(); i++) {
                Player p = gameManager.getGameDataList().get(GameDataNumber).getPlayerList().get(i);
                p.sendMessage(format("&4ERROR: Keine freie Map gefunden! Diesen Error bitte melden!"));
            }
        }
        assert Maps != null;
        if (Maps[1] == -1 && Maps[2] == -1) {
            gameManager.getGameDataList().get(GameDataNumber).setMapNumber1(Maps[0]);
            gameManager.getGameDataList().get(GameDataNumber).setMapNumber2(Maps[0]);
            gameManager.getGameDataList().get(GameDataNumber).setMapNumber3(Maps[0]);
            if (Objects.requireNonNull(gameManager.main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                int pi;
                for (pi = 0; pi < gameManager.getGameDataList().get(GameDataNumber).getPlayerList().size(); pi++) {
                    gameManager.getGameDataList().get(GameDataNumber).getPlayerList().get(pi).sendMessage(format("&6Debug: 1 Map gefunden"));
                }
            }
        }else if (Maps[2] == -1) {
            gameManager.getGameDataList().get(GameDataNumber).setMapNumber1(Maps[0]);
            gameManager.getGameDataList().get(GameDataNumber).setMapNumber2(Maps[1]);
            gameManager.getGameDataList().get(GameDataNumber).setMapNumber3(Maps[0]);
            if (Objects.requireNonNull(gameManager.main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                int pi;
                for (pi = 0; pi < gameManager.getGameDataList().get(GameDataNumber).getPlayerList().size(); pi++) {
                    gameManager.getGameDataList().get(GameDataNumber).getPlayerList().get(pi).sendMessage(format("&6Debug: 2 Maps gefunden"));
                }
            }
        }else {
            gameManager.getGameDataList().get(GameDataNumber).setMapNumber1(Maps[0]);
            gameManager.getGameDataList().get(GameDataNumber).setMapNumber2(Maps[1]);
            gameManager.getGameDataList().get(GameDataNumber).setMapNumber3(Maps[2]);
            if (Objects.requireNonNull(gameManager.main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                int pi;
                for (pi = 0; pi < gameManager.getGameDataList().get(GameDataNumber).getPlayerList().size(); pi++) {
                    gameManager.getGameDataList().get(GameDataNumber).getPlayerList().get(pi).sendMessage(format("&6Debug: 3 oder mehr Maps gefunden"));
                }
            }
        }
        MapChoosingInv(GameDataNumber);
    }

    public void MapChoosingInv(int GameDataNumber) {
        new BukkitRunnable() {

            int number = Objects.requireNonNull(gameManager.main.mainScoreboard.getObjective("CTreasureHunter")).getScore("MapChoosingTime").getScore();

            @Override
            public void run() {
                if (Objects.requireNonNull(gameManager.main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                    int pi;
                    for (pi = 0; pi < gameManager.getGameDataList().get(GameDataNumber).getPlayerList().size(); pi++) {
                        gameManager.getGameDataList().get(GameDataNumber).getPlayerList().get(pi).sendMessage(format("&6Debug: Map Wahl Inventory Funktion gestartet" + getMostVotedMap(gameManager.getGameDataList().get(GameDataNumber).getMap1List().size(), gameManager.getGameDataList().get(GameDataNumber).getMap2List().size(), gameManager.getGameDataList().get(GameDataNumber).getMap3List().size())));
                    }
                }
                if (number >= 0) {
                    int i;
                    for (i = 0; i < gameManager.getGameDataList().get(GameDataNumber).getPlayerList().size(); i++) {
                        Player p = gameManager.getGameDataList().get(GameDataNumber).getPlayerList().get(i);
                        p.openInventory(getMapChoosingInv(number, GameDataNumber));
                    }
                    number--;
                }else {
                    if (Objects.requireNonNull(gameManager.main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                        int pi;
                        for (pi = 0; pi < gameManager.getGameDataList().get(GameDataNumber).getPlayerList().size(); pi++) {
                            gameManager.getGameDataList().get(GameDataNumber).getPlayerList().get(pi).sendMessage(format("&6Debug: Wahl Ergebniss wird ausgewährtet"));
                        }
                    }
                    int Map1Size = gameManager.getGameDataList().get(GameDataNumber).getMap1List().size();
                    int Map2Size = gameManager.getGameDataList().get(GameDataNumber).getMap2List().size();
                    int Map3Size = gameManager.getGameDataList().get(GameDataNumber).getMap3List().size();
                    if (Map1Size > Map2Size) {
                        if (Map1Size > Map3Size) {
                            gameManager.getGameDataList().get(GameDataNumber).setSelectedMapNumber(gameManager.getGameDataList().get(GameDataNumber).getMapNumber1());
                        }else if (Map1Size < Map3Size) {
                            gameManager.getGameDataList().get(GameDataNumber).setSelectedMapNumber(gameManager.getGameDataList().get(GameDataNumber).getMapNumber3());
                        }else {
                            if (random.nextBoolean()) {
                                gameManager.getGameDataList().get(GameDataNumber).setSelectedMapNumber(gameManager.getGameDataList().get(GameDataNumber).getMapNumber1());
                            }else {
                                gameManager.getGameDataList().get(GameDataNumber).setSelectedMapNumber(gameManager.getGameDataList().get(GameDataNumber).getMapNumber3());
                            }
                        }
                    }else if (Map1Size < Map2Size) {
                        if (Map2Size > Map3Size) {
                            gameManager.getGameDataList().get(GameDataNumber).setSelectedMapNumber(gameManager.getGameDataList().get(GameDataNumber).getMapNumber2());
                        }else if (Map2Size < Map3Size) {
                            gameManager.getGameDataList().get(GameDataNumber).setSelectedMapNumber(gameManager.getGameDataList().get(GameDataNumber).getMapNumber3());
                        }else {
                            if (random.nextBoolean()) {
                                gameManager.getGameDataList().get(GameDataNumber).setSelectedMapNumber(gameManager.getGameDataList().get(GameDataNumber).getMapNumber2());
                            }else {
                                gameManager.getGameDataList().get(GameDataNumber).setSelectedMapNumber(gameManager.getGameDataList().get(GameDataNumber).getMapNumber3());
                            }
                        }
                    }else {
                        if (Map3Size > Map1Size) {
                            gameManager.getGameDataList().get(GameDataNumber).setSelectedMapNumber(gameManager.getGameDataList().get(GameDataNumber).getMapNumber3());
                        }else {
                            if (random.nextBoolean()) {
                                gameManager.getGameDataList().get(GameDataNumber).setSelectedMapNumber(gameManager.getGameDataList().get(GameDataNumber).getMapNumber1());
                            }else {
                                gameManager.getGameDataList().get(GameDataNumber).setSelectedMapNumber(gameManager.getGameDataList().get(GameDataNumber).getMapNumber2());
                            }
                        }
                    }
                    cancel();
                }
            }
        }.runTaskTimer(gameManager.main, 0L, 20L);
    }

    public Inventory getMapChoosingInv(int time_in_seconds, int GameDataNumber) {
        Inventory inventory = Bukkit.createInventory(new ChoosingHolder(), 27, format("&f七七七七七七七七ㇺ") + time_in_seconds);
        inventory.setItem(0, getMapItemInvisible(gameManager.getGameDataList().get(GameDataNumber).getMapNumber1()));
        inventory.setItem(1, getMapItemInvisible(gameManager.getGameDataList().get(GameDataNumber).getMapNumber1()));
        inventory.setItem(2, getMapItemInvisible(gameManager.getGameDataList().get(GameDataNumber).getMapNumber1()));
        inventory.setItem(3, getMapItemInvisible(gameManager.getGameDataList().get(GameDataNumber).getMapNumber2()));
        inventory.setItem(4, getMapItemInvisible(gameManager.getGameDataList().get(GameDataNumber).getMapNumber2()));
        inventory.setItem(5, getMapItemInvisible(gameManager.getGameDataList().get(GameDataNumber).getMapNumber2()));
        inventory.setItem(6, getMapItemInvisible(gameManager.getGameDataList().get(GameDataNumber).getMapNumber3()));
        inventory.setItem(7, getMapItemInvisible(gameManager.getGameDataList().get(GameDataNumber).getMapNumber3()));
        inventory.setItem(8, getMapItemInvisible(gameManager.getGameDataList().get(GameDataNumber).getMapNumber3()));
        inventory.setItem(9, getMapItemInvisible(gameManager.getGameDataList().get(GameDataNumber).getMapNumber1()));
        inventory.setItem(10, getMapItem(gameManager.getGameDataList().get(GameDataNumber).getMapNumber1()));
        inventory.setItem(11, getMapItemInvisible(gameManager.getGameDataList().get(GameDataNumber).getMapNumber1()));
        inventory.setItem(12, getMapItemInvisible(gameManager.getGameDataList().get(GameDataNumber).getMapNumber2()));
        inventory.setItem(13, getMapItem(gameManager.getGameDataList().get(GameDataNumber).getMapNumber2()));
        inventory.setItem(14, getMapItemInvisible(gameManager.getGameDataList().get(GameDataNumber).getMapNumber2()));
        inventory.setItem(15, getMapItemInvisible(gameManager.getGameDataList().get(GameDataNumber).getMapNumber3()));
        inventory.setItem(16, getMapItem(gameManager.getGameDataList().get(GameDataNumber).getMapNumber3()));
        inventory.setItem(17, getMapItemInvisible(gameManager.getGameDataList().get(GameDataNumber).getMapNumber3()));
        inventory.setItem(18, getMapItemInvisible(gameManager.getGameDataList().get(GameDataNumber).getMapNumber1()));
        if (getMostVotedMap(gameManager.getGameDataList().get(GameDataNumber).getMap1List().size(), gameManager.getGameDataList().get(GameDataNumber).getMap2List().size(), gameManager.getGameDataList().get(GameDataNumber).getMap3List().size()) == 1) {
            inventory.setItem(19, getMapItemPin(gameManager.getGameDataList().get(GameDataNumber).getMapNumber1()));
        }else {
            inventory.setItem(19, getMapItemInvisible(gameManager.getGameDataList().get(GameDataNumber).getMapNumber1()));
        }
        inventory.setItem(20, getMapItemInvisibleWithCount(gameManager.getGameDataList().get(GameDataNumber).getMapNumber1(), GameDataNumber, 1));
        inventory.setItem(21, getMapItemInvisible(gameManager.getGameDataList().get(GameDataNumber).getMapNumber2()));
        if (getMostVotedMap(gameManager.getGameDataList().get(GameDataNumber).getMap1List().size(), gameManager.getGameDataList().get(GameDataNumber).getMap2List().size(), gameManager.getGameDataList().get(GameDataNumber).getMap3List().size()) == 2) {
            inventory.setItem(22, getMapItemPin(gameManager.getGameDataList().get(GameDataNumber).getMapNumber2()));
        }else {
            inventory.setItem(22, getMapItemInvisible(gameManager.getGameDataList().get(GameDataNumber).getMapNumber2()));
        }
        inventory.setItem(23, getMapItemInvisibleWithCount(gameManager.getGameDataList().get(GameDataNumber).getMapNumber2(), GameDataNumber, 2));
        inventory.setItem(24, getMapItemInvisible(gameManager.getGameDataList().get(GameDataNumber).getMapNumber3()));
        if (getMostVotedMap(gameManager.getGameDataList().get(GameDataNumber).getMap1List().size(), gameManager.getGameDataList().get(GameDataNumber).getMap2List().size(), gameManager.getGameDataList().get(GameDataNumber).getMap3List().size()) == 3) {
            inventory.setItem(25, getMapItemPin(gameManager.getGameDataList().get(GameDataNumber).getMapNumber3()));
        }else {
            inventory.setItem(25, getMapItemInvisible(gameManager.getGameDataList().get(GameDataNumber).getMapNumber3()));
        }
        inventory.setItem(26, getMapItemInvisibleWithCount(gameManager.getGameDataList().get(GameDataNumber).getMapNumber3(), GameDataNumber, 3));
        return inventory;
    }

    public ItemStack getMapItem(int MapNumber) {
        ItemStack MapItem;
        MapItem = new ItemStack(Material.PAPER, 1);
        ItemMeta MapItemMeta = MapItem.getItemMeta();
        assert MapItemMeta != null;
        MapItemMeta.setDisplayName(format("&r" + gameManager.main.getMapManager().getMapName(MapNumber)));
        MapItemMeta.setCustomModelData(Objects.requireNonNull(gameManager.main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "CustomModelData").getScore());
        MapItem.setItemMeta(MapItemMeta);
        return MapItem;
    }

    public ItemStack getMapItemInvisible(int MapNumber) {

        ItemStack MapItem;
        MapItem = new ItemStack(Material.PAPER, 1);
        ItemMeta MapItemMeta = MapItem.getItemMeta();
        assert MapItemMeta != null;
        MapItemMeta.setDisplayName(format("&r" + gameManager.main.getMapManager().getMapName(MapNumber)));
        MapItemMeta.setCustomModelData(1);
        MapItem.setItemMeta(MapItemMeta);
        return MapItem;
    }

    public ItemStack getMapItemPin(int MapNumber) {
        ItemStack MapItem;
        MapItem = new ItemStack(Material.PAPER, 1);
        ItemMeta MapItemMeta = MapItem.getItemMeta();
        assert MapItemMeta != null;
        MapItemMeta.setDisplayName(format("&r" + gameManager.main.getMapManager().getMapName(MapNumber)));
        MapItemMeta.setCustomModelData(2);
        MapItem.setItemMeta(MapItemMeta);
        return MapItem;
    }

    public ItemStack getMapItemInvisibleWithCount(int MapNumber, int GameDataNumber, int Map) {
        int Map1 = gameManager.getGameDataList().get(GameDataNumber).getMap1List().size();
        int Map2 = gameManager.getGameDataList().get(GameDataNumber).getMap2List().size();
        int Map3 = gameManager.getGameDataList().get(GameDataNumber).getMap3List().size();
        if (Map1 == 0) Map1++;
        if (Map2 == 0) Map2++;
        if (Map3 == 0) Map3++;

        ItemStack MapItem;
        if (Map == 1) {
            MapItem = new ItemStack(Material.PAPER, Map1);
        }else if (Map == 2) {
            MapItem = new ItemStack(Material.PAPER, Map2);
        }else {
            MapItem = new ItemStack(Material.PAPER, Map3);
        }
        ItemMeta MapItemMeta = MapItem.getItemMeta();
        assert MapItemMeta != null;
        MapItemMeta.setDisplayName(format("&r" + gameManager.main.getMapManager().getMapName(MapNumber)));
        MapItemMeta.setCustomModelData(1);
        MapItem.setItemMeta(MapItemMeta);
        return MapItem;
    }

    public int getMostVotedMap(int Map1Size, int Map2Size, int Map3Size) {
        if (Map1Size > Map2Size) {
            if (Map1Size > Map3Size) {
                return 1;
            }else if (Map1Size < Map3Size) {
                return 3;
            }
        }else if (Map1Size < Map2Size) {
            if (Map2Size > Map3Size) {
                return 2;
            }else if (Map2Size < Map3Size) {
                return 3;
            }
        }else {
            if (Map3Size > Map1Size) {
                return 3;
            }
        }
        return 0;
    }

    public int[] choose3randomMaps() {
        int i;
        int Map1 = -1;
        int Map2 = -1;
        int Map3 = -1;
        boolean[] Maps = new boolean[Objects.requireNonNull(gameManager.main.mainScoreboard.getObjective("CTreasureHunter")).getScore("MapCount").getScore()];
        for (i = 0; i < Maps.length; i++) {
            Maps[i] = true;
        }
        int MapCount = Objects.requireNonNull(gameManager.main.mainScoreboard.getObjective("CTreasureHunter")).getScore("MapCount").getScore();
        for (i = 0; i < gameManager.getGameDataList().size(); i++) {
            if (gameManager.getGameDataList().get(i).getGamestate() != 1) {
                if (gameManager.getGameDataList().get(i).getSelectedMapNumber() == -1) {
                    if (gameManager.getGameDataList().get(i).getMapNumber1() != -1) {
                        if (Maps[gameManager.getGameDataList().get(i).getMapNumber1() - 1]) {
                            Maps[gameManager.getGameDataList().get(i).getMapNumber1() - 1] = false;
                            MapCount--;
                        }
                    }
                    if (gameManager.getGameDataList().get(i).getMapNumber2() != -1) {
                        if (Maps[gameManager.getGameDataList().get(i).getMapNumber2() - 1]) {
                            Maps[gameManager.getGameDataList().get(i).getMapNumber2() - 1] = false;
                            MapCount--;
                        }
                    }
                    if (gameManager.getGameDataList().get(i).getMapNumber3() != -1) {
                        if (Maps[gameManager.getGameDataList().get(i).getMapNumber3() - 1]) {
                            Maps[gameManager.getGameDataList().get(i).getMapNumber3() - 1] = false;
                            MapCount--;
                        }
                    }
                }else {
                    if (Maps[gameManager.getGameDataList().get(i).getSelectedMapNumber() - 1]) {
                        Maps[gameManager.getGameDataList().get(i).getSelectedMapNumber() - 1] = false;
                        MapCount--;
                    }
                }
            }
        }

        if (MapCount == 0) {
            return null;
        }else if (MapCount == 1) {
            for (i = 0; i < Maps.length; i++) {
                if (Maps[i]) {
                    i++;
                    Map1 = i;
                    break;
                }
            }
        }else if (MapCount == 2) {
            for (i = 0; i < Maps.length; i++) {
                if (Maps[i]) {
                    i++;
                    if (Map1 == -1) {
                        Map1 = i;
                    }else {
                        Map2 = i;
                    }
                    i--;
                }
            }
        }else {
            ArrayList<Integer> MapsNumber = new ArrayList<>();
            for (i = 0; i < Maps.length; i++) {
                if (Maps[i]) {
                    i++;
                    MapsNumber.add(i);
                    i--;
                }
            }
            int Map1Number = random.nextInt(MapsNumber.size());
            Map1 = MapsNumber.get(Map1Number);
            MapsNumber.remove(Map1Number);
            int Map2Number = random.nextInt(MapsNumber.size());
            Map2 = MapsNumber.get(Map2Number);
            MapsNumber.remove(Map2Number);
            int Map3Number = random.nextInt(MapsNumber.size());
            Map3 = MapsNumber.get(Map3Number);
            MapsNumber.remove(Map3Number);
        }

        return new int[]{Map1, Map2, Map3};
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inv = event.getClickedInventory();
        assert inv != null;
        int i;
        int i2;
        int i3;
        for (i = 0; i < gameManager.getGameDataList().size(); i++) {
            if (gameManager.getGameDataList().get(i).getGamestate() == 2) {
                for (i2 = 0; i2 < gameManager.getGameDataList().get(i).getPlayerList().size(); i2++) {
                    if (gameManager.getGameDataList().get(i).getPlayerList().get(i2).getName().equals(event.getWhoClicked().getName())) {
                        event.setCancelled(true);
                        Player p = gameManager.getGameDataList().get(i).getPlayerList().get(i2);
                        for (i3 = 0; i3 < gameManager.getGameDataList().get(i).getMap1List().size(); i3++) {
                            if (gameManager.getGameDataList().get(i).getMap1List().get(i3).equals(p.getName())) {
                                gameManager.getGameDataList().get(i).getMap1List().remove(i3);
                                i3--;
                            }
                        }
                        for (i3 = 0; i3 < gameManager.getGameDataList().get(i).getMap2List().size(); i3++) {
                            if (gameManager.getGameDataList().get(i).getMap2List().get(i3).equals(p.getName())) {
                                gameManager.getGameDataList().get(i).getMap2List().remove(i3);
                                i3--;
                            }
                        }
                        for (i3 = 0; i3 < gameManager.getGameDataList().get(i).getMap3List().size(); i3++) {
                            if (gameManager.getGameDataList().get(i).getMap3List().get(i3).equals(p.getName())) {
                                gameManager.getGameDataList().get(i).getMap3List().remove(i3);
                                i3--;
                            }
                        }
                        if (event.getSlot() == 0 || event.getSlot() == 1 || event.getSlot() == 2 || event.getSlot() == 9 || event.getSlot() == 10 || event.getSlot() == 11 || event.getSlot() == 18 || event.getSlot() == 19 || event.getSlot() == 20) {
                            gameManager.getGameDataList().get(i).getMap1List().add(p.getName());
                        }else if (event.getSlot() == 3 || event.getSlot() == 4 || event.getSlot() == 5 || event.getSlot() == 12 || event.getSlot() == 13 || event.getSlot() == 14 || event.getSlot() == 21 || event.getSlot() == 22 || event.getSlot() == 23) {
                            gameManager.getGameDataList().get(i).getMap2List().add(p.getName());
                        }else if (event.getSlot() == 6 || event.getSlot() == 7 || event.getSlot() == 8 || event.getSlot() == 15 || event.getSlot() == 16 || event.getSlot() == 17 || event.getSlot() == 24 || event.getSlot() == 25 || event.getSlot() == 26) {
                            gameManager.getGameDataList().get(i).getMap3List().add(p.getName());
                        }
                    }
                }
            }
        }
    }

    public String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
