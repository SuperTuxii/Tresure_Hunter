package treasure_hunter.treasure_hunter;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class GameManager implements Listener {

    Treasure_Hunter main;
    public GameManager(Treasure_Hunter treasure_hunter) {
        main = treasure_hunter;
        int i;
        for (i = 1; i <= Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("InstanceCount").getScore(); i++) {
            GameDataList.add(new GameData());
        }
    }

    public PreGameManager preGameManager = new PreGameManager(this);
    
    public ItemManager itemManager = new ItemManager();

    private final Random random = new Random();

    private final ArrayList<GameData> GameDataList = new ArrayList<>();

    public void startGame(ArrayList<Player> PlayerList, int GameDataNumber) {
        int i;
        GameDataList.get(GameDataNumber).getPlayerList().clear();
        for (i = 0; i < PlayerList.size(); i++) {
            GameDataList.get(GameDataNumber).getPlayerList().add(PlayerList.get(i));
            Collections.shuffle(GameDataList.get(GameDataNumber).getPlayerList());
        }
        if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
            int pi;
            for (pi = 0; pi < PlayerList.size(); pi++) {
                PlayerList.get(pi).sendMessage(format("&2Debug Modus gestartet"));
            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if (GameDataList.get(GameDataNumber).getGamestate() == 1) {
                    if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                        int pi;
                        for (pi = 0; pi < PlayerList.size(); pi++) {
                            PlayerList.get(pi).sendMessage(format("&6Debug: Map Wahl gestartet. State:" + GameDataList.get(GameDataNumber).getGamestate()));
                        }
                    }
                    preGameManager.MapChoosing(GameDataNumber);
                }else if (GameDataList.get(GameDataNumber).getGamestate() == 2 && GameDataList.get(GameDataNumber).getSelectedMapNumber() != -1) {
                    startGame(GameDataNumber);
                    if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                        int pi;
                        for (pi = 0; pi < GameDataList.get(GameDataNumber).getPlayerList().size(); pi++) {
                            GameDataList.get(GameDataNumber).getPlayerList().get(pi).sendMessage(format("&6Debug: Minigame gestartet"));
                        }
                    }
                    cancel();
                }
            }
        }.runTaskTimer(main, 0L, 20L);
    }

    public void startGame(int GameDataNumber) {
        int i;
        GameDataList.get(GameDataNumber).setGamestate(3);

        for (i = 0; i < GameDataList.get(GameDataNumber).getPlayerList().size() / 2; i++) {
            Player p = GameDataList.get(GameDataNumber).getPlayerList().get(i);
            GameDataList.get(GameDataNumber).getRedPlayerList().add(p.getName());
            int MapNumber = GameDataList.get(GameDataNumber).getSelectedMapNumber();
            p.teleport(new Location(Bukkit.getWorld("Map" + MapNumber), Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "RotSpawnX").getScore(), Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "RotSpawnY").getScore(), Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "RotSpawnZ").getScore()));
            p.getInventory().clear();
            p.setGameMode(GameMode.ADVENTURE);
            p.getInventory().addItem(itemManager.getUnbreakableItem(Material.WOODEN_SWORD, 1));
            p.getInventory().addItem(new ItemStack(Material.BREAD, 16));
            ItemStack Item = new ItemStack(Material.LEATHER_HELMET);
            LeatherArmorMeta ItemMeta = (LeatherArmorMeta) Item.getItemMeta();
            assert ItemMeta != null;
            ItemMeta.setColor(Color.RED);
            ItemMeta.setUnbreakable(true);
            Item.setItemMeta(ItemMeta);
            p.getInventory().setHelmet(Item);
            Item = new ItemStack(Material.LEATHER_CHESTPLATE);
            ItemMeta = (LeatherArmorMeta) Item.getItemMeta();
            assert ItemMeta != null;
            ItemMeta.setColor(Color.RED);
            ItemMeta.setUnbreakable(true);
            Item.setItemMeta(ItemMeta);
            p.getInventory().setChestplate(Item);
            Item = new ItemStack(Material.LEATHER_LEGGINGS);
            ItemMeta = (LeatherArmorMeta) Item.getItemMeta();
            assert ItemMeta != null;
            ItemMeta.setColor(Color.RED);
            ItemMeta.setUnbreakable(true);
            Item.setItemMeta(ItemMeta);
            p.getInventory().setLeggings(Item);
            Item = new ItemStack(Material.LEATHER_BOOTS);
            ItemMeta = (LeatherArmorMeta) Item.getItemMeta();
            assert ItemMeta != null;
            ItemMeta.setColor(Color.RED);
            ItemMeta.setUnbreakable(true);
            Item.setItemMeta(ItemMeta);
            p.getInventory().setBoots(Item);
            p.setHealth(20);
            Objects.requireNonNull(main.mainScoreboard.getObjective("Kills")).getScore(p.getName()).setScore(0);
            GameDataList.get(GameDataNumber).getGametimeBar().addPlayer(p);
        }
        for (i = GameDataList.get(GameDataNumber).getPlayerList().size() / 2; i < GameDataList.get(GameDataNumber).getPlayerList().size(); i++) {
            Player p = GameDataList.get(GameDataNumber).getPlayerList().get(i);
            GameDataList.get(GameDataNumber).getBluePlayerList().add(p.getName());
            int MapNumber = GameDataList.get(GameDataNumber).getSelectedMapNumber();
            p.teleport(new Location(Bukkit.getWorld("Map" + MapNumber), Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "BlauSpawnX").getScore(), Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "BlauSpawnY").getScore(), Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "BlauSpawnZ").getScore()));
            p.getInventory().clear();
            p.setGameMode(GameMode.ADVENTURE);
            p.getInventory().addItem(itemManager.getUnbreakableItem(Material.WOODEN_SWORD, 1));
            p.getInventory().addItem(new ItemStack(Material.BREAD, 16));
            ItemStack Item = new ItemStack(Material.LEATHER_HELMET);
            LeatherArmorMeta ItemMeta = (LeatherArmorMeta) Item.getItemMeta();
            assert ItemMeta != null;
            ItemMeta.setColor(Color.BLUE);
            ItemMeta.setUnbreakable(true);
            Item.setItemMeta(ItemMeta);
            p.getInventory().setHelmet(Item);
            Item = new ItemStack(Material.LEATHER_CHESTPLATE);
            ItemMeta = (LeatherArmorMeta) Item.getItemMeta();
            assert ItemMeta != null;
            ItemMeta.setColor(Color.BLUE);
            ItemMeta.setUnbreakable(true);
            Item.setItemMeta(ItemMeta);
            p.getInventory().setChestplate(Item);
            Item = new ItemStack(Material.LEATHER_LEGGINGS);
            ItemMeta = (LeatherArmorMeta) Item.getItemMeta();
            assert ItemMeta != null;
            ItemMeta.setColor(Color.BLUE);
            ItemMeta.setUnbreakable(true);
            Item.setItemMeta(ItemMeta);
            p.getInventory().setLeggings(Item);
            Item = new ItemStack(Material.LEATHER_BOOTS);
            ItemMeta = (LeatherArmorMeta) Item.getItemMeta();
            assert ItemMeta != null;
            ItemMeta.setColor(Color.BLUE);
            ItemMeta.setUnbreakable(true);
            Item.setItemMeta(ItemMeta);
            p.getInventory().setBoots(Item);
            p.setHealth(20);
            Objects.requireNonNull(main.mainScoreboard.getObjective("Kills")).getScore(p.getName()).setScore(0);
            GameDataList.get(GameDataNumber).getGametimeBar().addPlayer(p);
        }
        if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
            int pi;
            for (pi = 0; pi < GameDataList.get(GameDataNumber).getPlayerList().size(); pi++) {
                GameDataList.get(GameDataNumber).getPlayerList().get(pi).sendMessage(format("&6Debug: Gamestate auf 3 gesetzt und Starteritems vergeben"));
            }
        }


        i = 1;
        while (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(GameDataNumber).getSelectedMapNumber() + "TreasureSpawn" + i + "X").isScoreSet()) {
            i++;
        }
        i--;

        if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
            int pi;
            for (pi = 0; pi < getGameDataList().get(GameDataNumber).getPlayerList().size(); pi++) {
                GameDataList.get(GameDataNumber).getPlayerList().get(pi).sendMessage(format("&6Debug: Treasures werden gespawned"));
                GameDataList.get(GameDataNumber).getPlayerList().get(pi).sendMessage(format("&6Debug: Treasure: I" + i));
            }
        }

        int i2;
        if (i < Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("TreasureAmount").getScore()) {
            GameDataList.get(GameDataNumber).getTreasureNumberList().clear();
            for (i2 = 0; i2 < GameDataList.get(GameDataNumber).getPlayerList().size(); i2++) {
                GameDataList.get(GameDataNumber).getPlayerList().get(i2).sendMessage(format("&cERROR: Not Enough Treasure Spawnpoints for this Map! Please report this Error!"));
            }
            for (i2 = 1; i2 <= i; i2++) {
                GameDataList.get(GameDataNumber).getTreasureNumberList().add(i2);
            }
        }else if (i == Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("TreasureAmount").getScore()) {
            GameDataList.get(GameDataNumber).getTreasureNumberList().clear();
            for (i2 = 1; i2 <= i; i2++) {
                GameDataList.get(GameDataNumber).getTreasureNumberList().add(i2);
            }
        }else {
            GameDataList.get(GameDataNumber).getTreasureNumberList().clear();
            for (i2 = 0; i2 < Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("TreasureAmount").getScore(); i2++) {
                int SelectedTreasure = random.nextInt(i);
                SelectedTreasure++;
                while (GameDataList.get(GameDataNumber).getTreasureNumberList().contains(SelectedTreasure)) {
                    SelectedTreasure = random.nextInt(i);
                    SelectedTreasure++;
                }
                GameDataList.get(GameDataNumber).getTreasureNumberList().add(SelectedTreasure);
            }
        }

        for (i2 = 0; i2 < GameDataList.get(GameDataNumber).getTreasureNumberList().size(); i2++) {
            GameDataList.get(GameDataNumber).getTreasureStatusList().add(true);
            World world = Bukkit.getWorld("Map" + GameDataList.get(GameDataNumber).getSelectedMapNumber());
            int x = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(GameDataNumber).getSelectedMapNumber() + "TreasureSpawn" + GameDataList.get(GameDataNumber).getTreasureNumberList().get(i2) + "X").getScore();
            int y = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(GameDataNumber).getSelectedMapNumber() + "TreasureSpawn" + GameDataList.get(GameDataNumber).getTreasureNumberList().get(i2) + "Y").getScore();
            int z = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(GameDataNumber).getSelectedMapNumber() + "TreasureSpawn" + GameDataList.get(GameDataNumber).getTreasureNumberList().get(i2) + "Z").getScore();
            int r = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(GameDataNumber).getSelectedMapNumber() + "TreasureSpawn" + GameDataList.get(GameDataNumber).getTreasureNumberList().get(i2) + "R").getScore();
            assert world != null;
            world.spawn(new Location(world, x, y, z), ItemFrame.class, entity -> {
                entity.setFacingDirection(BlockFace.UP, true);
                if (r == 0) entity.setRotation(Rotation.NONE);
                if (r == 1) entity.setRotation(Rotation.CLOCKWISE_45);
                if (r == 2) entity.setRotation(Rotation.CLOCKWISE);
                if (r == 3) entity.setRotation(Rotation.CLOCKWISE_135);
                if (r == 4) entity.setRotation(Rotation.FLIPPED);
                if (r == 5) entity.setRotation(Rotation.FLIPPED_45);
                if (r == 6) entity.setRotation(Rotation.COUNTER_CLOCKWISE);
                if (r == 7) entity.setRotation(Rotation.COUNTER_CLOCKWISE_45);
                ItemStack item = new ItemStack(Material.CHEST);
                ItemMeta meta = item.getItemMeta();
                assert meta != null;
                meta.setCustomModelData(1);
                item.setItemMeta(meta);
                entity.setItem(item);
                entity.setItemDropChance(0.0f);
                entity.setFixed(true);
                entity.setVisible(false);
                entity.addScoreboardTag("treasure_texture");
            });
            if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                int pi;
                for (pi = 0; pi < GameDataList.get(GameDataNumber).getPlayerList().size(); pi++) {
                    GameDataList.get(GameDataNumber).getPlayerList().get(pi).sendMessage(format("&6Debug: Treasure: TNL" + i2 + GameDataList.get(GameDataNumber).getTreasureNumberList().get(i2)));
                    GameDataList.get(GameDataNumber).getPlayerList().get(pi).sendMessage(format("&6Debug: Treasure: TSL" + i2 + GameDataList.get(GameDataNumber).getTreasureStatusList().get(i2)));
                    GameDataList.get(GameDataNumber).getPlayerList().get(pi).sendMessage(format("&6Debug: Treasure: POS" + i2 + x + " " + y + " " + z + " " + r));
                }
            }
        }

        for (i2 = 0; i2 < GameDataList.get(GameDataNumber).getTreasureNumberList().size(); i2++) {
            int x = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(GameDataNumber).getSelectedMapNumber() + "TreasureSpawn" + GameDataList.get(GameDataNumber).getTreasureNumberList().get(i2) + "X").getScore();
            int y = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(GameDataNumber).getSelectedMapNumber() + "TreasureSpawn" + GameDataList.get(GameDataNumber).getTreasureNumberList().get(i2) + "Y").getScore();
            int z = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(GameDataNumber).getSelectedMapNumber() + "TreasureSpawn" + GameDataList.get(GameDataNumber).getTreasureNumberList().get(i2) + "Z").getScore();
            Objects.requireNonNull(Bukkit.getWorld("Map" + GameDataList.get(GameDataNumber).getSelectedMapNumber())).getBlockAt(x, y, z).setType(Material.BARRIER);
        }

        new BukkitRunnable() {

            int number = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("RoundTime").getScore();

            @Override
            public void run() {
                if (GameDataList.get(GameDataNumber).getGamestate() != 3) {
                    if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                        int pi;
                        for (pi = 0; pi < getGameDataList().get(GameDataNumber).getPlayerList().size(); pi++) {
                            GameDataList.get(GameDataNumber).getPlayerList().get(pi).sendMessage(format("&6Debug: Gamestate ist nicht mehr 3"));
                        }
                    }
                    cancel();
                    return;
                }
                if (number > 0) {
                    int i;
                    for (i = 0; i < GameDataList.get(GameDataNumber).getCorpseList().size(); i++) {
                        GameDataList.get(GameDataNumber).getCorpseList().get(i).updatePlayerInventory();
                    }
                    for (i = 0; i < GameDataList.get(GameDataNumber).getPlayerList().size(); i++) {
                        MultireviveInHandCheck(GameDataList.get(GameDataNumber).getPlayerList().get(i));
                    }
                    for (i = 0; i < GameDataList.get(GameDataNumber).getMultireviveAnimationList().size(); i++) {
                        MultireviveNotInHandCheck(GameDataList.get(GameDataNumber).getMultireviveAnimationList().get(i));
                    }
                    checkForEnd(GameDataList.get(GameDataNumber));
                    double RoundTime = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("RoundTime").getScore();
                    GameDataList.get(GameDataNumber).getGametimeBar().setProgress((1 / RoundTime) * number);
                    number--;
                }else {
                    int i;
                    for (i = 0; i < GameDataList.get(GameDataNumber).getPlayerList().size(); i++) {
                        GameDataList.get(GameDataNumber).getPlayerList().get(i).sendTitle(format("&4Team Blau hat gewonnen"), format("&cdie Zeit ist abgelaufen!"), 20, 160, 20);
                        if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                            GameDataList.get(GameDataNumber).getPlayerList().get(i).sendMessage(format("&6Debug: Zeit ist abgelaufen"));
                        }
                    }
                    Restart(GameDataList.get(GameDataNumber));
                }
            }
        }.runTaskTimer(main, 0L, 20L);
    }

    @EventHandler
    public void OnInteract(PlayerInteractEvent event) {
        int i;
        int i2;
        int i3;
        for (i = 0; i < GameDataList.size(); i++) {
            if (GameDataList.get(i).getGamestate() == 3) {
                for (i2 = 0; i2 < GameDataList.get(i).getPlayerList().size(); i2++) {
                    if (GameDataList.get(i).getPlayerList().get(i2).getName().equals(event.getPlayer().getName())) {
                        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                            Player p = event.getPlayer();
                            for (i3 = 1; true; i3++) {
                                if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(i).getSelectedMapNumber() + "ShopSpawn" + i3 + "X").isScoreSet()) {
                                    int x = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(i).getSelectedMapNumber() + "ShopSpawn" + i3 + "X").getScore();
                                    int y = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(i).getSelectedMapNumber() + "ShopSpawn" + i3 + "Y").getScore();
                                    int z = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(i).getSelectedMapNumber() + "ShopSpawn" + i3 + "Z").getScore();
                                    if (Objects.requireNonNull(event.getClickedBlock()).getLocation().equals(new Location(p.getWorld(), x, y, z))) {
                                        event.setCancelled(true);
                                        p.openInventory(getShopInventory());
                                        if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                                            p.sendMessage(format("&6Debug: Shop Inventory geöffnet"));
                                        }
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                        if (event.hasItem()) {
                            Player p = event.getPlayer();
                            if (Objects.requireNonNull(event.getItem()).isSimilar(itemManager.getMedkit())) {
                                event.setCancelled(true);
                            }else if (Objects.requireNonNull(event.getItem()).isSimilar(itemManager.getSchiff())) {
                                event.setCancelled(true);
                                if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
                                    return;

                                Block block = event.getPlayer().getTargetBlock(null, 4);
                                if (block.getType() != Material.WATER)
                                    return;

                                int x = block.getX();
                                int y = block.getY();
                                int z = block.getZ();
                                while (block.getWorld().getBlockAt(x, y + 1, z).getType() == Material.WATER) {
                                    y++;
                                    block = block.getWorld().getBlockAt(x, y, z);
                                }

                                for (x = block.getX() - 3; x <= block.getX() + 3; x++) {
                                    for (y = block.getY(); y <= block.getY() + 6; y++) {
                                        for (z = block.getZ() - 9; z <= block.getZ() + 7; z++) {
                                            if (block.getWorld().getBlockAt(x, y, z).getType() != Material.AIR && block.getWorld().getBlockAt(x, y, z).getType() != Material.WATER) {
                                                p.sendMessage(format("&cEin Block ist im Weg (" + block.getWorld().getBlockAt(x, y, z).getType() + ")"));
                                                return;
                                            }
                                        }
                                    }
                                }
                                spawnBoat(block.getLocation(), p, Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("SchiffReadyTime").getScore(), i);
                                p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                                if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                                    int pi;
                                    for (pi = 0; pi < getGameDataList().get(i).getPlayerList().size(); pi++) {
                                        GameDataList.get(i).getPlayerList().get(pi).sendMessage(format("&6Debug: Schiff plaziert"));
                                    }
                                }
                            }else if (Objects.requireNonNull(event.getItem()).isSimilar(itemManager.getMultirevive())) {
                                event.setCancelled(true);
                                if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                                    int pi;
                                    for (pi = 0; pi < getGameDataList().get(i).getPlayerList().size(); pi++) {
                                        GameDataList.get(i).getPlayerList().get(pi).sendMessage(format("&6Debug: Multirevive ausgelöst"));
                                    }
                                }
                            }else if (event.getItem().isSimilar(itemManager.getRevolver())) {
                                p.getWorld().playEffect(p.getLocation(), Effect.SMOKE, 10);
                                if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                                    int pi;
                                    for (pi = 0; pi < getGameDataList().get(i).getPlayerList().size(); pi++) {
                                        GameDataList.get(i).getPlayerList().get(pi).sendMessage(format("&6Debug: Revolver Rauch ausgelöst"));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public Inventory getShopInventory() {
        Inventory inv = Bukkit.createInventory(new ShopHolder(), 18, format("&6Shop"));
        inv.setItem(0, itemManager.getRevolver());
        inv.setItem(1, itemManager.getBullet(1));
        inv.setItem(2, itemManager.getMedkit());
        inv.setItem(3, itemManager.getSchiff());
        inv.setItem(4, itemManager.getMultirevive());
        inv.setItem(9, itemManager.getCoins(Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("RevolverPrice").getScore()));
        inv.setItem(10, itemManager.getCoins(Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("BulletPrice").getScore()));
        inv.setItem(11, itemManager.getCoins(Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("MedkitPrice").getScore()));
        inv.setItem(12, itemManager.getCoins(Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("SchiffPrice").getScore()));
        inv.setItem(13, itemManager.getCoins(Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("MultirevivePrice").getScore()));
        return inv;
    }

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null) {
            Inventory inv = event.getClickedInventory();
            assert inv != null;
            int i;
            int i2;
            int i3;
            for (i = 0; i < GameDataList.size(); i++) {
                if (GameDataList.get(i).getGamestate() == 3) {
                    for (i2 = 0; i2 < GameDataList.get(i).getPlayerList().size(); i2++) {
                        if (GameDataList.get(i).getPlayerList().get(i2).getName().equals(event.getWhoClicked().getName())) {
                            Player p = (Player) event.getWhoClicked();
                            if (event.getClickedInventory().getHolder() instanceof ShopHolder) {
                                event.setCancelled(true);
                                if (event.getSlot() == 0) {
                                    if (p.getInventory().containsAtLeast(itemManager.getCoin(), Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("RevolverPrice").getScore())) {
                                        clearCoins(p, Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("RevolverPrice").getScore());
                                        HashMap<Integer, ItemStack> remainingItems = p.getInventory().addItem(itemManager.getRevolver());
                                        if (!remainingItems.isEmpty()) {
                                            for (i3 = 0; i3 < remainingItems.size(); i3++) {
                                                p.getWorld().dropItemNaturally(p.getLocation(), remainingItems.get(i));
                                            }
                                        }
                                    }
                                } else if (event.getSlot() == 1) {
                                    if (p.getInventory().containsAtLeast(itemManager.getCoin(), Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("BulletPrice").getScore())) {
                                        clearCoins(p, Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("BulletPrice").getScore());
                                        HashMap<Integer, ItemStack> remainingItems = p.getInventory().addItem(itemManager.getBullet(Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("BulletAmount").getScore()));
                                        if (!remainingItems.isEmpty()) {
                                            for (i3 = 0; i3 < remainingItems.size(); i3++) {
                                                p.getWorld().dropItemNaturally(p.getLocation(), remainingItems.get(i));
                                            }
                                        }
                                    }
                                } else if (event.getSlot() == 2) {
                                    if (p.getInventory().containsAtLeast(itemManager.getCoin(), Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("MedkitPrice").getScore())) {
                                        clearCoins(p, Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("MedkitPrice").getScore());
                                        HashMap<Integer, ItemStack> remainingItems = p.getInventory().addItem(itemManager.getMedkit());
                                        if (!remainingItems.isEmpty()) {
                                            for (i3 = 0; i3 < remainingItems.size(); i3++) {
                                                p.getWorld().dropItemNaturally(p.getLocation(), remainingItems.get(i));
                                            }
                                        }
                                    }
                                } else if (event.getSlot() == 3) {
                                    if (p.getInventory().containsAtLeast(itemManager.getCoin(), Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("SchiffPrice").getScore()) && GameDataList.get(i).getRedPlayerList().contains(p.getName())) {
                                        clearCoins(p, Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("SchiffPrice").getScore());
                                        HashMap<Integer, ItemStack> remainingItems = p.getInventory().addItem(itemManager.getSchiff());
                                        if (!remainingItems.isEmpty()) {
                                            for (i3 = 0; i3 < remainingItems.size(); i3++) {
                                                p.getWorld().dropItemNaturally(p.getLocation(), remainingItems.get(i));
                                            }
                                        }
                                    }
                                } else if (event.getSlot() == 4) {
                                    if (p.getInventory().containsAtLeast(itemManager.getCoin(), Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("MultirevivePrice").getScore())) {
                                        clearCoins(p, Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("MultirevivePrice").getScore());
                                        HashMap<Integer, ItemStack> remainingItems = p.getInventory().addItem(itemManager.getMultirevive());
                                        if (!remainingItems.isEmpty()) {
                                            for (i3 = 0; i3 < remainingItems.size(); i3++) {
                                                p.getWorld().dropItemNaturally(p.getLocation(), remainingItems.get(i));
                                            }
                                        }
                                    }
                                } else if (event.getSlot() == 5 && Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                                    HashMap<Integer, ItemStack> remainingItems = p.getInventory().addItem(itemManager.getCoins(64));
                                    if (!remainingItems.isEmpty()) {
                                        for (i3 = 0; i3 < remainingItems.size(); i3++) {
                                            p.getWorld().dropItemNaturally(p.getLocation(), remainingItems.get(i));
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

    public void clearCoins(Player p, int amount) {
        for (ItemStack invItem : p.getInventory().getContents()) {
            if (invItem != null) {
                if (invItem.isSimilar(itemManager.getCoin())) {
                    int preAmount = invItem.getAmount();
                    int newAmount = Math.max(0, preAmount - amount);
                    amount = Math.max(0, amount - preAmount);
                    invItem.setAmount(newAmount);
                    if (amount == 0) {
                        break;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            if (p.getWorld().getName().equals(Bukkit.getWorlds().get(0).getName())) {
                event.setCancelled(true);
                return;
            }
            int i;
            int i2;
            for (i = 0; i < GameDataList.size(); i++) {
                for (i2 = 0; i2 < GameDataList.get(i).getPlayerList().size(); i2++) {
                    if (GameDataList.get(i).getPlayerList().get(i2).getName().equals(p.getName())) {
                        if (p.getHealth() - event.getFinalDamage() <= 0) {
                            if (GameDataList.get(i).getGamestate() == 3) {
                                event.setCancelled(true);
                                spawnCorpse(event, GameDataList.get(i));
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getDamager() instanceof Player || event.getDamager() instanceof Arrow) {
                Player pD = null;
                if (event.getDamager() instanceof Player) {
                    pD = (Player) event.getDamager();
                } else if (event.getDamager() instanceof Arrow) {
                    if (((Arrow) event.getDamager()).getShooter() instanceof Player) {
                        pD = (Player) ((Arrow) event.getDamager()).getShooter();
                    }
                }
                assert pD != null;
                Player p = (Player) event.getEntity();
                int i;
                int i2;
                for (i = 0; i < GameDataList.size(); i++) {
                    for (i2 = 0; i2 < GameDataList.get(i).getPlayerList().size(); i2++) {
                        if (GameDataList.get(i).getPlayerList().get(i2).getName().equals(p.getName())) {
                            if (GameDataList.get(i).getGamestate() != 3) {
                                event.setCancelled(true);
                                return;
                            }
                            if (GameDataList.get(i).getRedPlayerList().contains(p.getName()) && GameDataList.get(i).getRedPlayerList().contains(pD.getName())) {
                                event.setCancelled(true);
                                return;
                            }
                            if (GameDataList.get(i).getBluePlayerList().contains(p.getName()) && GameDataList.get(i).getBluePlayerList().contains(pD.getName())) {
                                event.setCancelled(true);
                                return;
                            }
                            if (p.getHealth() - event.getFinalDamage() <= 0) {
                                if (GameDataList.get(i).getGamestate() == 3) {
                                    if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                                        int pi;
                                        for (pi = 0; pi < getGameDataList().get(i).getPlayerList().size(); pi++) {
                                            GameDataList.get(i).getPlayerList().get(pi).sendMessage(format("&6Debug: Spieler " + p.getName() + " wurde von " + pD.getName() + " / " + pD.getName() + " getötet"));
                                        }
                                    }
                                    event.setCancelled(true);
                                    p.addScoreboardTag("dead");
                                    GameDataList.get(i).getCorpseList().add(new Corpse(p.getLocation(), p, Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("ReviveTime").getScore(), GameDataList.get(i).getBluePlayerList().contains(p.getName()), main));
                                    p.setGameMode(GameMode.SPECTATOR);
                                    if (pD instanceof Player) {
                                        Objects.requireNonNull(main.mainScoreboard.getObjective("Kills")).getScore(pD.getName()).setScore(Objects.requireNonNull(main.mainScoreboard.getObjective("Kills")).getScore(pD.getName()).getScore() + 1);
                                        HashMap<Integer, ItemStack> remainingItems = pD.getInventory().addItem(new ItemStack(Material.BREAD));
                                        if (!remainingItems.isEmpty()) {
                                            for (i = 0; i < remainingItems.size(); i++) {
                                                p.getWorld().dropItemNaturally(p.getLocation(), remainingItems.get(i));
                                            }
                                        }
                                        remainingItems = pD.getInventory().addItem(itemManager.getCoin());
                                        if (!remainingItems.isEmpty()) {
                                            for (i = 0; i < remainingItems.size(); i++) {
                                                p.getWorld().dropItemNaturally(p.getLocation(), remainingItems.get(i));
                                            }
                                        }
                                        if (Objects.requireNonNull(main.mainScoreboard.getObjective("Kills")).getScore(pD.getName()).getScore() == 5) {
                                            pD.getInventory().setItem(pD.getInventory().first(Material.WOODEN_SWORD), itemManager.getUnbreakableItem(Material.STONE_SWORD, 1));
                                        } else if (Objects.requireNonNull(main.mainScoreboard.getObjective("Kills")).getScore(pD.getName()).getScore() == 10) {
                                            pD.getInventory().setItem(pD.getInventory().first(Material.STONE_SWORD), itemManager.getUnbreakableItem(Material.IRON_SWORD, 1));
                                            pD.getInventory().setHelmet(itemManager.getUnbreakableItem(Material.CHAINMAIL_HELMET, 1));
                                            pD.getInventory().setChestplate(itemManager.getUnbreakableItem(Material.CHAINMAIL_CHESTPLATE, 1));
                                            pD.getInventory().setLeggings(itemManager.getUnbreakableItem(Material.CHAINMAIL_LEGGINGS, 1));
                                            pD.getInventory().setBoots(itemManager.getUnbreakableItem(Material.CHAINMAIL_BOOTS, 1));
                                        } else if (Objects.requireNonNull(main.mainScoreboard.getObjective("Kills")).getScore(pD.getName()).getScore() == 15) {
                                            pD.getInventory().setItem(pD.getInventory().first(Material.IRON_SWORD), itemManager.getUnbreakableItem(Material.GOLDEN_SWORD, 1));
                                        } else if (Objects.requireNonNull(main.mainScoreboard.getObjective("Kills")).getScore(pD.getName()).getScore() == 20) {
                                            pD.getInventory().setItem(pD.getInventory().first(Material.GOLDEN_SWORD), itemManager.getUnbreakableItem(Material.DIAMOND_SWORD, 1));
                                            pD.getInventory().setHelmet(itemManager.getUnbreakableItem(Material.IRON_HELMET, 1));
                                            pD.getInventory().setChestplate(itemManager.getUnbreakableItem(Material.IRON_CHESTPLATE, 1));
                                            pD.getInventory().setLeggings(itemManager.getUnbreakableItem(Material.IRON_LEGGINGS, 1));
                                            pD.getInventory().setBoots(itemManager.getUnbreakableItem(Material.IRON_BOOTS, 1));
                                        } else if (Objects.requireNonNull(main.mainScoreboard.getObjective("Kills")).getScore(pD.getName()).getScore() == 25) {
                                            pD.getInventory().setItem(pD.getInventory().first(Material.DIAMOND_SWORD), itemManager.getUnbreakableItem(Material.NETHERITE_SWORD, 1));
                                        } else if (Objects.requireNonNull(main.mainScoreboard.getObjective("Kills")).getScore(pD.getName()).getScore() == 30) {
                                            pD.getInventory().setHelmet(itemManager.getUnbreakableItem(Material.GOLDEN_HELMET, 1));
                                            pD.getInventory().setChestplate(itemManager.getUnbreakableItem(Material.GOLDEN_CHESTPLATE, 1));
                                            pD.getInventory().setLeggings(itemManager.getUnbreakableItem(Material.GOLDEN_LEGGINGS, 1));
                                            pD.getInventory().setBoots(itemManager.getUnbreakableItem(Material.GOLDEN_BOOTS, 1));
                                        } else if (Objects.requireNonNull(main.mainScoreboard.getObjective("Kills")).getScore(pD.getName()).getScore() == 40) {
                                            pD.getInventory().setHelmet(itemManager.getUnbreakableItem(Material.DIAMOND_HELMET, 1));
                                            pD.getInventory().setChestplate(itemManager.getUnbreakableItem(Material.DIAMOND_CHESTPLATE, 1));
                                            pD.getInventory().setLeggings(itemManager.getUnbreakableItem(Material.DIAMOND_LEGGINGS, 1));
                                            pD.getInventory().setBoots(itemManager.getUnbreakableItem(Material.DIAMOND_BOOTS, 1));
                                        } else if (Objects.requireNonNull(main.mainScoreboard.getObjective("Kills")).getScore(pD.getName()).getScore() == 50) {
                                            pD.getInventory().setHelmet(itemManager.getUnbreakableItem(Material.NETHERITE_HELMET, 1));
                                            pD.getInventory().setChestplate(itemManager.getUnbreakableItem(Material.NETHERITE_CHESTPLATE, 1));
                                            pD.getInventory().setLeggings(itemManager.getUnbreakableItem(Material.NETHERITE_LEGGINGS, 1));
                                            pD.getInventory().setBoots(itemManager.getUnbreakableItem(Material.NETHERITE_BOOTS, 1));
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

    public void spawnCorpse(EntityDamageEvent event, GameData gameData) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!event.getEntity().getScoreboardTags().contains("dead")) {
                    if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                        int pi;
                        for (pi = 0; pi < gameData.getPlayerList().size(); pi++) {
                            gameData.getPlayerList().get(pi).sendMessage(format("&6Debug: Spieler " + event.getEntity().getName() + "ist gestorben"));
                        }
                    }
                    Player p = (Player) event.getEntity();
                    p.addScoreboardTag("dead");
                    gameData.getCorpseList().add(new Corpse(p.getLocation(), p, Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("ReviveTime").getScore(), gameData.getBluePlayerList().contains(p.getName()), main));
                    p.setGameMode(GameMode.SPECTATOR);
                }
                cancel();
            }
        }.runTaskTimer(main, 1L, 0L);
    }

    public void spawnCorpse(Player p, GameData gameData) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!p.getScoreboardTags().contains("dead")) {
                    if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                        int pi;
                        for (pi = 0; pi < gameData.getPlayerList().size(); pi++) {
                            gameData.getPlayerList().get(pi).sendMessage(format("&6Debug: Spieler " + p.getName() + "ist gestorben"));
                        }
                    }
                    p.addScoreboardTag("dead");
                    gameData.getCorpseList().add(new Corpse(p.getLocation(), p, Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("ReviveTime").getScore(), gameData.getBluePlayerList().contains(p.getName()), main));
                    p.setGameMode(GameMode.SPECTATOR);
                }
                cancel();
            }
        }.runTaskTimer(main, 1L, 1L);
    }

    @EventHandler
    public void onEntityRightClick(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() instanceof ArmorStand) {
            Player p = event.getPlayer();
            ArmorStand as = (ArmorStand) event.getRightClicked();
            if (as.getScoreboardTags().contains("corpse")) {
                int i;
                int i2;
                int i3;
                for (i = 0; i < GameDataList.size(); i++) {
                    for (i2 = 0; i2 < GameDataList.get(i).getPlayerList().size(); i2++) {
                        if (GameDataList.get(i).getPlayerList().get(i2).getName().equals(p.getName())) {
                            for (i3 = 0; i3 < GameDataList.get(i).getCorpseList().size(); i3++) {
                                if (as.getName().equals(GameDataList.get(i).getCorpseList().get(i3).getTexture().getName()) && as.getLocation().equals(GameDataList.get(i).getCorpseList().get(i3).getTexture().getLocation())) {
                                    GameDataList.get(i).getCorpseList().get(i3).OpenCorpse(p);
                                    if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                                        int pi;
                                        for (pi = 0; pi < GameDataList.get(i).getPlayerList().size(); pi++) {
                                            GameDataList.get(i).getPlayerList().get(pi).sendMessage(format("&6Debug: Leiche wurde geöffnet"));
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

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        if (event.isSneaking()) {
            Player p = event.getPlayer();
            int i;
            int i2;
            int i3;
            for (i = 0; i < GameDataList.size(); i++) {
                for (i2 = 0; i2 < GameDataList.get(i).getPlayerList().size(); i2++) {
                    if (GameDataList.get(i).getPlayerList().get(i2).getName().equals(p.getName())) {
                        for (i3 = 0; i3 < GameDataList.get(i).getCorpseList().size(); i3++) {
                            int radius = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("ResRadius").getScore();
                            if (GameDataList.get(i).getCorpseList().get(i3).getPosX() + radius >= p.getLocation().getX() && p.getLocation().getX() >= GameDataList.get(i).getCorpseList().get(i3).getPosX() - radius && GameDataList.get(i).getCorpseList().get(i3).getPosY() + radius >= p.getLocation().getY() && p.getLocation().getY() >= GameDataList.get(i).getCorpseList().get(i3).getPosY() - radius && GameDataList.get(i).getCorpseList().get(i3).getPosZ() + radius >= p.getLocation().getZ() && p.getLocation().getZ() >= GameDataList.get(i).getCorpseList().get(i3).getPosZ() - radius && GameDataList.get(i).getCorpseList().get(i3).isRevivable()) {
                                if (p.getInventory().getItemInMainHand().isSimilar(itemManager.getMedkit())) {
                                    if (GameDataList.get(i).getRedPlayerList().contains(GameDataList.get(i).getCorpseList().get(i3).getPlayer().getName()) && GameDataList.get(i).getRedPlayerList().contains(p.getName())) {
                                        if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                                            int pi;
                                            for (pi = 0; pi < getGameDataList().get(i).getPlayerList().size(); pi++) {
                                                GameDataList.get(i).getPlayerList().get(pi).sendMessage(format("&6Debug: Spieler aus Team Rot wird wiederbelebt"));
                                            }
                                        }
                                        GameDataList.get(i).getCorpseList().get(i3).setReviving(true);
                                        ReviveCorpse(Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("ResTime").getScore(), GameDataList.get(i).getCorpseList().get(i3), p, GameDataList.get(i));
                                        return;
                                    }else if (GameDataList.get(i).getBluePlayerList().contains(GameDataList.get(i).getCorpseList().get(i3).getPlayer().getName()) && GameDataList.get(i).getBluePlayerList().contains(p.getName())) {
                                        if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                                            int pi;
                                            for (pi = 0; pi < getGameDataList().get(i).getPlayerList().size(); pi++) {
                                                GameDataList.get(i).getPlayerList().get(pi).sendMessage(format("&6Debug: Spieler aus Team Blau wird wiederbelebt"));
                                            }
                                        }
                                        GameDataList.get(i).getCorpseList().get(i3).setReviving(true);
                                        ReviveCorpse(Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("ResTime").getScore(), GameDataList.get(i).getCorpseList().get(i3), p, GameDataList.get(i));
                                        return;
                                    }
                                }
                            }
                        }
                        for (i3 = 0; i3 < GameDataList.get(i).getTreasureNumberList().size(); i3++) {
                            if (GameDataList.get(i).getTreasureStatusList().get(i3)) {
                                int radius = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("TreasureRadius").getScore();
                                int x = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(i).getSelectedMapNumber() + "TreasureSpawn" + GameDataList.get(i).getTreasureNumberList().get(i3) + "X").getScore();
                                int y = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(i).getSelectedMapNumber() + "TreasureSpawn" + GameDataList.get(i).getTreasureNumberList().get(i3) + "Y").getScore();
                                int z = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(i).getSelectedMapNumber() + "TreasureSpawn" + GameDataList.get(i).getTreasureNumberList().get(i3) + "Z").getScore();
                                if (x + radius >= p.getLocation().getX() && p.getLocation().getX() >= x - radius && y + radius >= p.getLocation().getY() && p.getLocation().getY() >= y - radius && z + radius >= p.getLocation().getZ() && p.getLocation().getZ() >= z - radius && GameDataList.get(i).getRedPlayerList().contains(p.getName())) {
                                    if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                                        int pi;
                                        for (pi = 0; pi < getGameDataList().get(i).getPlayerList().size(); pi++) {
                                            GameDataList.get(i).getPlayerList().get(pi).sendMessage(format("&6Debug: Treasure wird ausgegraben"));
                                        }
                                    }
                                    PickupTreasure(Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("TreasureTime").getScore(), i3, p, GameDataList.get(i));
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void ReviveCorpse(double resTime, Corpse corpse, Player p, GameData gameData) {
        BossBar bar = createBossbar(format("&4Wiederbeleben ..."), BarColor.RED, BarStyle.SOLID);
        bar.addPlayer(p);
        new BukkitRunnable() {
            int number = 0;
            @Override
            public void run() {
                if (number != resTime) {
                    if (!p.isSneaking() || !p.getInventory().getItemInMainHand().isSimilar(itemManager.getMedkit()) || !corpse.isRevivable()) {
                        corpse.setReviving(false);
                        bar.removePlayer(p);
                        cancel();
                    }
                    bar.setProgress((1 / resTime) * number);
                    number++;
                }else {
                    if (!p.isSneaking() || !p.getInventory().getItemInMainHand().isSimilar(itemManager.getMedkit()) || !corpse.isRevivable()) {
                        corpse.setReviving(false);
                        bar.removePlayer(p);
                        cancel();
                        return;
                    }
                    corpse.getPlayer().teleport(corpse.getPosition());
                    corpse.getPlayer().setGameMode(GameMode.ADVENTURE);
                    corpse.getPlayer().setHealth(20);
                    corpse.getPlayer().removeScoreboardTag("dead");
                    corpse.updatePlayerInventory();
                    corpse.removeTexture();
                    gameData.getCorpseList().remove(corpse);
                    p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                    if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                        int pi;
                        for (pi = 0; pi < gameData.getPlayerList().size(); pi++) {
                            gameData.getPlayerList().get(pi).sendMessage(format("&6Debug: Spieler wiederbelebt"));
                        }
                    }
                    bar.removePlayer(p);
                    cancel();
                }
            }
        }.runTaskTimer(main, 0L, 20L);
    }

    public void PickupTreasure(double pickupTime, int TreasureNumber, Player p, GameData gameData) {
        BossBar bar = createBossbar(format("&6Ausgraben ..."), BarColor.YELLOW, BarStyle.SOLID);
        bar.addPlayer(p);
        new BukkitRunnable() {
            int number = 0;
            @Override
            public void run() {
                int radius = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("TreasureRadius").getScore();
                int x = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + gameData.getSelectedMapNumber() + "TreasureSpawn" + gameData.getTreasureNumberList().get(TreasureNumber) + "X").getScore();
                int y = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + gameData.getSelectedMapNumber() + "TreasureSpawn" + gameData.getTreasureNumberList().get(TreasureNumber) + "Y").getScore();
                int z = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + gameData.getSelectedMapNumber() + "TreasureSpawn" + gameData.getTreasureNumberList().get(TreasureNumber) + "Z").getScore();
                if (number != pickupTime) {
                    if (!(x + radius >= p.getLocation().getX() && p.getLocation().getX() >= x - radius && y + radius >= p.getLocation().getY() && p.getLocation().getY() >= y - radius && z + radius >= p.getLocation().getZ() && p.getLocation().getZ() >= z - radius)) {
                        if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                            int pi;
                            for (pi = 0; pi < gameData.getPlayerList().size(); pi++) {
                                gameData.getPlayerList().get(pi).sendMessage(format("&6Debug: Spieler hat sich zu weit von der Treasure entfernt"));
                            }
                        }
                        bar.removePlayer(p);
                        cancel();
                    }
                    if (!p.isSneaking() || !gameData.getTreasureStatusList().get(TreasureNumber)) {
                        if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                            int pi;
                            for (pi = 0; pi < gameData.getPlayerList().size(); pi++) {
                                gameData.getPlayerList().get(pi).sendMessage(format("&6Debug: Spieler hat aufgehört zu sneaken"));
                            }
                        }
                        bar.removePlayer(p);
                        cancel();
                    }
                    BlockData BlockData = p.getWorld().getBlockAt(x, y - 1, z).getType().createBlockData();
                    p.spawnParticle(Particle.BLOCK_DUST, new Location(p.getWorld(), x + 0.5, y + 0.1, z + 0.5), 100, BlockData);
                    bar.setProgress((1 / pickupTime) * number);
                    number++;
                }else {
                    if (!(x + radius >= p.getLocation().getX() && p.getLocation().getX() >= x - radius && y + radius >= p.getLocation().getY() && p.getLocation().getY() >= y - radius && z + radius >= p.getLocation().getZ() && p.getLocation().getZ() >= z - radius)) {
                        if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                            int pi;
                            for (pi = 0; pi < gameData.getPlayerList().size(); pi++) {
                                gameData.getPlayerList().get(pi).sendMessage(format("&6Debug: Spieler hat sich zu weit von der Treasure entfernt"));
                            }
                        }
                        bar.removePlayer(p);
                        cancel();
                        return;
                    }
                    if (!p.isSneaking() || !gameData.getTreasureStatusList().get(TreasureNumber)) {
                        if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                            int pi;
                            for (pi = 0; pi < gameData.getPlayerList().size(); pi++) {
                                gameData.getPlayerList().get(pi).sendMessage(format("&6Debug: Spieler hat aufgehört zu sneaken"));
                            }
                        }
                        bar.removePlayer(p);
                        cancel();
                        return;
                    }
                    int i;
                    p.getWorld().getBlockAt(x, y, z).setType(Material.AIR);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in map" + gameData.getSelectedMapNumber() + " positioned " + x + " " + y + " " + z + " run kill @e[distance=..1,type=minecraft:item_frame,limit=1,sort=nearest,tag=treasure_texture]");
                    HashMap<Integer, ItemStack> remainingItems = p.getInventory().addItem(itemManager.getTreasure());
                    if (!remainingItems.isEmpty()) {
                        for (i = 0; i < remainingItems.size(); i++) {
                            p.getWorld().dropItemNaturally(p.getLocation(), remainingItems.get(i));
                        }
                    }
                    gameData.getTreasureStatusList().set(TreasureNumber, false);
                    if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                        int pi;
                        for (pi = 0; pi < gameData.getPlayerList().size(); pi++) {
                            gameData.getPlayerList().get(pi).sendMessage(format("&6Debug: Treasure wurde aufgehoben"));
                        }
                    }
                    bar.removePlayer(p);
                    cancel();
                }
            }
        }.runTaskTimer(main, 0L, 20L);
    }

    public void spawnBoat(Location location, Player p, double time, int GameDataNumber) {

        int i;
        BossBar bar = createBossbar(format("&9Schiff wird vorbereitet ..."), BarColor.BLUE, BarStyle.SOLID);
        for (i = 0; i < GameDataList.get(GameDataNumber).getPlayerList().size(); i++) {
            p.playSound(location, Sound.ENTITY_BOAT_PADDLE_WATER, SoundCategory.MASTER, 100, 0.1f);
            p.playSound(location, Sound.BLOCK_WATER_AMBIENT, SoundCategory.MASTER, 100, 2);
        }

        new BukkitRunnable() {
            int number = -2;

            @Override
            public void run() {
                if (number != time) {
                    if (number == 0) {
                        int i;
                        for (i = 0; i < GameDataList.get(GameDataNumber).getPlayerList().size(); i++) {
                            p.playSound(location, Sound.EVENT_RAID_HORN, SoundCategory.MASTER, 100, 0.25f);
                        }
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute at " + p.getName() + " run setblock " + location.getBlockX() + " " + location.getBlock().getRelative(BlockFace.UP).getY() + " " + location.getBlockZ() + " structure_block{name:\"minecraft:boat_red\",posX:-3,posY:-1,posZ:-9,sizeX:7,sizeY:6,sizeZ:17,rotation:\"NONE\",mirror:\"NONE\",mode:\"LOAD\"} replace");
                        location.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.UP).setType(Material.REDSTONE_BLOCK);
                        p.teleport(location.add(0, 1, 0));
                        for (i = 0; i < GameDataList.get(GameDataNumber).getRedPlayerList().size(); i++) {
                            Player player = Bukkit.getPlayer(GameDataList.get(GameDataNumber).getRedPlayerList().get(i));
                            assert player != null;
                            bar.addPlayer(player);
                        }

                    }
                    if (number >= 0) {
                        bar.setProgress((1 / time) * number);
                    }
                    number++;
                }else {
                    int x;
                    int y;
                    int z;
                    for (x = location.getBlockX() - 3; x <= location.getBlockX() + 3; x++) {
                        for (y = location.getBlockY() - 1; y <= location.getBlockY() + 5; y++) {
                            for (z = location.getBlockZ() - 9; z <= location.getBlockZ() + 7; z++) {
                                if (y == location.getBlockY() - 1) {
                                    Objects.requireNonNull(location.getWorld()).getBlockAt(x, y, z).setType(Material.WATER, false);
                                }else {
                                    Objects.requireNonNull(location.getWorld()).getBlockAt(x, y, z).setType(Material.AIR, false);
                                }
                            }
                        }
                    }
                    int i;
                    for (i = 0; i < GameDataList.get(GameDataNumber).getRedPlayerList().size(); i++) {
                        Player p = Bukkit.getPlayer(GameDataList.get(GameDataNumber).getRedPlayerList().get(i));
                        assert p != null;
                        bar.removePlayer(p);
                        p.sendMessage(format("&cDas Schiff ist abgefahren"));
                        if (location.getBlockX() - 3 <= p.getLocation().getBlockX() && location.getBlockX() + 3 >= p.getLocation().getBlockX() && location.getBlockY() <= p.getLocation().getBlockY() && location.getBlockY() + 6 >= p.getLocation().getBlockY() && location.getBlockZ() - 9 <= p.getLocation().getBlockZ() && location.getBlockZ() + 7 >= p.getLocation().getBlockZ()) {
                            p.setGameMode(GameMode.SPECTATOR);
                            p.addScoreboardTag("shipped");
                            int treasureamount = 0;
                            while (p.getInventory().containsAtLeast(itemManager.getTreasure(), 1)) {
                                int amount = 1;
                                for (ItemStack invItem : p.getInventory().getContents()) {
                                    if (invItem != null) {
                                        if (invItem.isSimilar(itemManager.getTreasure())) {
                                            int preAmount = invItem.getAmount();
                                            int newAmount = Math.max(0, preAmount - amount);
                                            amount = Math.max(0, amount - preAmount);
                                            invItem.setAmount(newAmount);
                                            if (amount == 0) {
                                                break;
                                            }
                                        }
                                    }
                                }
                                treasureamount++;
                            }
                            GameDataList.get(GameDataNumber).setSavedTreasure(GameDataList.get(GameDataNumber).getSavedTreasure() + treasureamount);
                        }
                    }
                    for (i = 0; i < GameDataList.get(GameDataNumber).getPlayerList().size(); i++) {
                        p.playSound(location, Sound.ENTITY_BOAT_PADDLE_WATER, SoundCategory.MASTER, 100, 0.1f);
                        p.playSound(location, Sound.BLOCK_WATER_AMBIENT, SoundCategory.MASTER, 100, 2);
                    }
                    if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                        int pi;
                        for (pi = 0; pi < getGameDataList().get(GameDataNumber).getPlayerList().size(); pi++) {
                            GameDataList.get(GameDataNumber).getPlayerList().get(pi).sendMessage(format("&6Debug: Schiff abgefahren"));
                        }
                    }
                    cancel();
                }
            }
        }.runTaskTimer(main, 0L, 20L);
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow) {
            event.getEntity().remove();
        }
    }

    public void checkForEnd(GameData gameData) {
        int i;
        int i2;
        int BlueTeamLeft = 0;
        int RedTeamLeft = 0;
        int RedTeamOnShip = 0;
        int RedTeamLeftOnShip;
        for (i = 0; i < gameData.getRedPlayerList().size(); i++) {
            if (Bukkit.getPlayerExact(gameData.getRedPlayerList().get(i)) != null) {
                if (!Objects.requireNonNull(Bukkit.getPlayer(gameData.getRedPlayerList().get(i))).getScoreboardTags().contains("dead") && !Objects.requireNonNull(Bukkit.getPlayer(gameData.getRedPlayerList().get(i))).getScoreboardTags().contains("shipped")) {
                    RedTeamLeft++;
                }
                if (Objects.requireNonNull(Bukkit.getPlayer(gameData.getRedPlayerList().get(i))).getScoreboardTags().contains("shipped")) {
                    RedTeamOnShip++;
                }
            }
        }
        for (i = 0; i < gameData.getBluePlayerList().size(); i++) {
            if (Bukkit.getPlayerExact(gameData.getBluePlayerList().get(i)) != null) {
                if (!Objects.requireNonNull(Bukkit.getPlayer(gameData.getBluePlayerList().get(i))).getScoreboardTags().contains("dead") && !Objects.requireNonNull(Bukkit.getPlayer(gameData.getRedPlayerList().get(i))).getScoreboardTags().contains("shipped")) {
                    BlueTeamLeft++;
                }
            }
        }

        if (!gameData.getTreasureStatusList().contains(true) && gameData.getSavedTreasure() == gameData.getTreasureNumberList().size()) {
            for (i2 = 0; i2 < gameData.getPlayerList().size(); i2++) {
                gameData.getPlayerList().get(i2).sendTitle(format("&4Team Rot hat gewonnen"), format("&csie sind mit den Schatz entkommen!"), 20, 160, 20);
            }
            if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                int pi;
                for (pi = 0; pi < gameData.getPlayerList().size(); pi++) {
                    gameData.getPlayerList().get(pi).sendMessage(format("&6Debug: Alle Treasure aufgesammelt und gesichert"));
                }
            }
            Restart(gameData);
            return;
        }
        if (RedTeamLeft == 0 && RedTeamOnShip != 0) {
            if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                int pi;
                for (pi = 0; pi < gameData.getPlayerList().size(); pi++) {
                    gameData.getPlayerList().get(pi).sendMessage(format("&6Debug: Keine Roten mehr auf der Insel. " + RedTeamOnShip + " auf dem Schiff"));
                }
            }
            if (gameData.getSavedTreasure() < gameData.getTreasureNumberList().size() / 2) {
                for (i2 = 0; i2 < gameData.getPlayerList().size(); i2++) {
                    gameData.getPlayerList().get(i2).sendTitle(format("&1Team Blau hat gewonnen"), format("&9die Roten sind geflohen!"), 20, 160, 20);
                }
                if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                    int pi;
                    for (pi = 0; pi < gameData.getPlayerList().size(); pi++) {
                        gameData.getPlayerList().get(pi).sendMessage(format("&6Debug: Treasure gesaved: " + gameData.getSavedTreasure() + " von " + gameData.getTreasureNumberList().size()));
                    }
                }
                Restart(gameData);
                return;
            }else if (gameData.getSavedTreasure() >= gameData.getTreasureNumberList().size() / 2 && gameData.getSavedTreasure() < gameData.getTreasureNumberList().size()) {
                for (i2 = 0; i2 < gameData.getPlayerList().size(); i2++) {
                    gameData.getPlayerList().get(i2).sendTitle(format("&3Unentschieden"), format("&bdie Roten sind mit ein paar Schätzen geflohen!"), 20, 160, 20);
                }
                if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                    int pi;
                    for (pi = 0; pi < gameData.getPlayerList().size(); pi++) {
                        gameData.getPlayerList().get(pi).sendMessage(format("&6Debug: Treasure gesaved: " + gameData.getSavedTreasure() + " von " + gameData.getTreasureNumberList().size()));
                    }
                }
                Restart(gameData);
                return;
            }else {
                for (i2 = 0; i2 < gameData.getPlayerList().size(); i2++) {
                    gameData.getPlayerList().get(i2).sendTitle(format("&4Team Rot hat gewonnen"), format("&csie sind mit den Schätzen entkommen!"), 20, 160, 20);
                }
                if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                    int pi;
                    for (pi = 0; pi < gameData.getPlayerList().size(); pi++) {
                        gameData.getPlayerList().get(pi).sendMessage(format("&6Debug: Treasure gesaved: " + gameData.getSavedTreasure() + " von " + gameData.getTreasureNumberList().size()));
                    }
                }
                Restart(gameData);
                return;
            }
        }

        RedTeamLeftOnShip = RedTeamLeft + RedTeamOnShip;
        if (BlueTeamLeft == 0 && RedTeamLeftOnShip == 0) {
            for (i2 = 0; i2 < gameData.getPlayerList().size(); i2++) {
                gameData.getPlayerList().get(i2).sendTitle(format("&3Unentschieden"), format("&balle sind tot!"), 20, 160, 20);
            }
            if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                int pi;
                for (pi = 0; pi < gameData.getPlayerList().size(); pi++) {
                    gameData.getPlayerList().get(pi).sendMessage(format("&6Debug: BTL" + BlueTeamLeft + " RTL" + RedTeamLeft + " RTOS" + RedTeamOnShip + " RTLOS" + RedTeamLeftOnShip));
                }
            }
            Restart(gameData);
        }else if (BlueTeamLeft == 0) {
            for (i2 = 0; i2 < gameData.getPlayerList().size(); i2++) {
                gameData.getPlayerList().get(i2).sendTitle(format("&4Team Rot hat gewonnen"), format("&csie haben alle Blauen eliminiert!"), 20, 160, 20);
            }
            if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                int pi;
                for (pi = 0; pi < gameData.getPlayerList().size(); pi++) {
                    gameData.getPlayerList().get(pi).sendMessage(format("&6Debug: BTL" + BlueTeamLeft + " RTL" + RedTeamLeft + " RTOS" + RedTeamOnShip + " RTLOS" + RedTeamLeftOnShip));
                }
            }
            Restart(gameData);
        }else if (RedTeamLeftOnShip == 0) {
            for (i2 = 0; i2 < gameData.getPlayerList().size(); i2++) {
                gameData.getPlayerList().get(i2).sendTitle(format("&1Team Blau hat gewonnen"), format("&9sie haben alle Roten eliminiert!"), 20, 160, 20);
            }
            if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                int pi;
                for (pi = 0; pi < gameData.getPlayerList().size(); pi++) {
                    gameData.getPlayerList().get(pi).sendMessage(format("&6Debug: BTL" + BlueTeamLeft + " RTL" + RedTeamLeft + " RTOS" + RedTeamOnShip + " RTLOS" + RedTeamLeftOnShip));
                }
            }
            Restart(gameData);
        }
    }

    public void Restart(GameData gameData) {
        gameData.setGamestate(4);
        int i;
        int MapNumber = gameData.getSelectedMapNumber();
        for (i = 0; i < gameData.getPlayerList().size(); i++) {
            gameData.getPlayerList().get(i).teleport(new Location(Bukkit.getWorld("Map" + MapNumber), Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "BlauSpawnX").getScore(), Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "BlauSpawnY").getScore(), Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "BlauSpawnZ").getScore()));
        }
        if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
            int pi;
            for (pi = 0; pi < gameData.getPlayerList().size(); pi++) {
                gameData.getPlayerList().get(pi).sendMessage(format("&6Debug: Alle Spieler zum Spawn von Blau teleportiert"));
            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                gameData.setGamestate(5);
                int i;
                for (i = 0; i < gameData.getCorpseList().size(); i++) {
                    gameData.getCorpseList().get(i).removeTexture();
                }
                for (i = 0; i < gameData.getPlayerList().size(); i++) {
                    if (gameData.getPlayerList().get(i).isOnline()) {
                        ResetPlayer(gameData.getPlayerList().get(i), gameData);
                    }
                }
                for (i = 0; i < gameData.getTreasureNumberList().size(); i++) {
                    int x = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + gameData.getSelectedMapNumber() + "TreasureSpawn" + gameData.getTreasureNumberList().get(i) + "X").getScore();
                    int y = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + gameData.getSelectedMapNumber() + "TreasureSpawn" + gameData.getTreasureNumberList().get(i) + "Y").getScore();
                    int z = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + gameData.getSelectedMapNumber() + "TreasureSpawn" + gameData.getTreasureNumberList().get(i) + "Z").getScore();
                    Objects.requireNonNull(Bukkit.getWorld("Map" + gameData.getSelectedMapNumber())).getBlockAt(x, y, z).setType(Material.AIR);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill @e[tag=treasure_texture]");
                }
                gameData.getPlayerList().clear();
                gameData.getRedPlayerList().clear();
                gameData.getBluePlayerList().clear();
                gameData.getCorpseList().clear();
                gameData.getMap1List().clear();
                gameData.getMap2List().clear();
                gameData.getMap3List().clear();
                gameData.setMapNumber1(-1);
                gameData.setMapNumber2(-1);
                gameData.setMapNumber3(-1);
                gameData.setSelectedMapNumber(-1);
                gameData.getTreasureNumberList().clear();
                gameData.getTreasureStatusList().clear();
                gameData.setSavedTreasure(0);
                gameData.setGamestate(1);
                if (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").getScore() == 1) {
                    int pi;
                    for (pi = 0; pi < gameData.getPlayerList().size(); pi++) {
                        gameData.getPlayerList().get(pi).sendMessage(format("&6Debug: Alles Resetet"));
                    }
                }
                cancel();
            }
        }.runTaskTimer(main, 200L, 1L);
    }

    public void ResetPlayer(Player p, GameData gameData) {
        p.removeScoreboardTag("dead");
        p.removeScoreboardTag("shipped");
        p.getInventory().clear();
        p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
        p.setGameMode(GameMode.SURVIVAL);
        p.setHealth(20);
        Objects.requireNonNull(main.mainScoreboard.getObjective("Kills")).getScore(p.getName()).setScore(0);
        gameData.getGametimeBar().removePlayer(p);
    }

    public void ResetPlayer(Player p) {
        p.removeScoreboardTag("dead");
        p.removeScoreboardTag("shipped");
        p.getInventory().clear();
        p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
        p.setGameMode(GameMode.SURVIVAL);
        p.setHealth(20);
        Objects.requireNonNull(main.mainScoreboard.getObjective("Kills")).getScore(p.getName()).setScore(0);
    }

    public void Shutdown() {
        int i;
        int i2;
        for (i = 0; i < GameDataList.size(); i++) {
            GameData gameData = GameDataList.get(i);
            gameData.setGamestate(1);
            for (i2 = 0; i2 < gameData.getCorpseList().size(); i2++) {
                gameData.getCorpseList().get(i2).removeTexture();
            }
            for (i2 = 0; i2 < gameData.getPlayerList().size(); i2++) {
                gameData.getPlayerList().get(i2).removeScoreboardTag("dead");
                gameData.getPlayerList().get(i2).removeScoreboardTag("shipped");
                gameData.getPlayerList().get(i2).getInventory().clear();
            }
            for (i2 = 0; i2 < gameData.getTreasureNumberList().size(); i2++) {
                int x = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + gameData.getSelectedMapNumber() + "TreasureSpawn" + gameData.getTreasureNumberList().get(i2) + "X").getScore();
                int y = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + gameData.getSelectedMapNumber() + "TreasureSpawn" + gameData.getTreasureNumberList().get(i2) + "Y").getScore();
                int z = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + gameData.getSelectedMapNumber() + "TreasureSpawn" + gameData.getTreasureNumberList().get(i2) + "Z").getScore();
                Objects.requireNonNull(Bukkit.getWorld("Map" + gameData.getSelectedMapNumber())).getBlockAt(x, y, z).setType(Material.AIR);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill @e[tag=treasure_texture]");
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        int i;
        int i2;
        for (i = 0; i < GameDataList.size(); i++) {
            for (i2 = 0; i2 < GameDataList.get(i).getPlayerList().size(); i2++) {
                if (GameDataList.get(i).getPlayerList().get(i2).getName().equals(p.getName())) {
                    spawnCorpse(p, GameDataList.get(i));
                    return;
                }
            }
        }
        ResetPlayer(p);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        Player p = event.getPlayer();
        int i;
        int i2;
        int i3;
        for (i = 0; i < GameDataList.size(); i++) {
            for (i2 = 0; i2 < GameDataList.get(i).getPlayerList().size(); i2++) {
                if (GameDataList.get(i).getPlayerList().get(i2).getName().equals(p.getName())) {
                    if (GameDataList.get(i).getRedPlayerList().contains(p.getName())) {
                        for (i3 = 0; i3 < GameDataList.get(i).getRedPlayerList().size(); i3++) {
                            Player pS = Bukkit.getPlayerExact(GameDataList.get(i).getRedPlayerList().get(i3));
                            if (pS != null) {
                                pS.sendMessage(format("&c[Team] &f<" + p.getName() + "> " + event.getMessage()));
                            }
                        }
                    }else {
                        for (i3 = 0; i3 < GameDataList.get(i).getBluePlayerList().size(); i3++) {
                            Player pS = Bukkit.getPlayerExact(GameDataList.get(i).getBluePlayerList().get(i3));
                            if (pS != null) {
                                pS.sendMessage(format("&9[Team] &f<" + p.getName() + "> " + event.getMessage()));
                            }
                        }
                    }
                    return;
                }
            }
        }
        p.sendMessage(format("&cDu bist nicht in einem Spiel"));
    }

    public void MultireviveInHandCheck(Player p) {
        if (p.getInventory().getItemInMainHand().isSimilar(itemManager.getMultirevive())) {

            return;
        }
        if (p.getInventory().getItemInOffHand().isSimilar(itemManager.getMultirevive())) {

        }
    }

    public void MultireviveNotInHandCheck(MultireviveAnimation mA) {
        if (!mA.getPlayer().getInventory().getItemInMainHand().isSimilar(itemManager.getMultirevive()) && !mA.getPlayer().getInventory().getItemInOffHand().isSimilar(itemManager.getMultirevive())) {

        }
    }

    public BossBar createBossbar(String name, BarColor color, BarStyle style) {
        return Bukkit.createBossBar(name, color, style);
    }

    public ArrayList<GameData> getGameDataList() {
        return GameDataList;
    }

    public String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}