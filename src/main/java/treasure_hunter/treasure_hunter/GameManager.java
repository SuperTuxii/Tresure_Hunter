package treasure_hunter.treasure_hunter;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
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

    private ArrayList<GameData> GameDataList = new ArrayList<>();

    public void startGame(ArrayList<Player> PlayerList, int GameDataNumber) {
        int i;
        GameDataList.get(GameDataNumber).getPlayerList().clear();
        for (i = 0; i < PlayerList.size(); i++) {
            GameDataList.get(GameDataNumber).getPlayerList().add(PlayerList.get(i));
        }

        new BukkitRunnable() {

            int number = 0;

            @Override
            public void run() {
                if (GameDataList.get(GameDataNumber).getGamestate() == 1) {
                    preGameManager.MapChoosing(GameDataNumber);
                }else if (GameDataList.get(GameDataNumber).getGamestate() == 2 && GameDataList.get(GameDataNumber).getSelectedMapNumber() != -1) {
                    startGame(GameDataNumber);
                }
                number--;
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
            p.getInventory().addItem(new ItemStack(Material.APPLE, 16));
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
            Item = new ItemStack(Material.WOODEN_SWORD);
            ItemMeta itemMeta = Item.getItemMeta();
            assert itemMeta != null;
            itemMeta.setUnbreakable(true);
            Item.setItemMeta(itemMeta);
            p.getInventory().addItem(Item);
        }
        for (i = GameDataList.get(GameDataNumber).getPlayerList().size() / 2; i < GameDataList.get(GameDataNumber).getPlayerList().size(); i++) {
            Player p = GameDataList.get(GameDataNumber).getPlayerList().get(i);
            GameDataList.get(GameDataNumber).getBluePlayerList().add(p.getName());
            int MapNumber = GameDataList.get(GameDataNumber).getSelectedMapNumber();
            p.teleport(new Location(Bukkit.getWorld("Map" + MapNumber), Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "BlauSpawnX").getScore(), Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "BlauSpawnY").getScore(), Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + MapNumber + "BlauSpawnZ").getScore()));
            p.getInventory().clear();
            p.setGameMode(GameMode.ADVENTURE);
            p.getInventory().addItem(new ItemStack(Material.APPLE, 16));
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
            Item = new ItemStack(Material.WOODEN_SWORD);
            ItemMeta itemMeta = Item.getItemMeta();
            assert itemMeta != null;
            itemMeta.setUnbreakable(true);
            Item.setItemMeta(itemMeta);
            p.getInventory().addItem(Item);
        }

        i = 1;
        while (Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(GameDataNumber).getSelectedMapNumber() + "TreasureSpawn" + i + "X").isScoreSet()) {
            i++;
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
            Objects.requireNonNull(Bukkit.getWorld("Map" + GameDataList.get(GameDataNumber).getSelectedMapNumber())).getBlockAt(x, y, z).setType(Material.BARRIER);
            assert world != null;
            world.spawn(new Location(world, x, y, z), ItemFrame.class, entity -> {
                /*entity.setFacingDirection(BlockFace.UP, true);
                ItemStack item = new ItemStack(Material.CHEST);
                ItemMeta meta = item.getItemMeta();
                assert meta != null;
                meta.setCustomModelData(1);
                item.setItemMeta(meta);
                entity.setItem(item);
                entity.setItemDropChance(0.0f);
                entity.setFixed(true);
                entity.setVisible(false);
                entity.addScoreboardTag("treasure_texture");*/
            });
        }

        new BukkitRunnable() {

            int number = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("RoundTime").getScore();

            @Override
            public void run() {
                number--;
                int i;
                for (i = 0; i < GameDataList.get(GameDataNumber).getCorpseList().size(); i++) {
                    GameDataList.get(GameDataNumber).getCorpseList().get(i).updatePlayerInventory();
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
                            }else if (Objects.requireNonNull(event.getItem()).isSimilar(itemManager.getMultirevive())) {
                                event.setCancelled(true);

                            }else if (event.getItem().isSimilar(itemManager.getRevolver())) {
                                p.getWorld().playEffect(p.getLocation(), Effect.SMOKE, 10);
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
                            }else if (event.getSlot() == 1) {
                                if (p.getInventory().containsAtLeast(itemManager.getCoin(), Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("BulletPrice").getScore())) {
                                    clearCoins(p, Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("BulletPrice").getScore());
                                    HashMap<Integer, ItemStack> remainingItems = p.getInventory().addItem(itemManager.getBullet(Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("BulletAmount").getScore()));
                                    if (!remainingItems.isEmpty()) {
                                        for (i3 = 0; i3 < remainingItems.size(); i3++) {
                                            p.getWorld().dropItemNaturally(p.getLocation(), remainingItems.get(i));
                                        }
                                    }
                                }
                            }else if (event.getSlot() == 2) {
                                if (p.getInventory().containsAtLeast(itemManager.getCoin(), Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("MedkitPrice").getScore())) {
                                    clearCoins(p, Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("MedkitPrice").getScore());
                                    HashMap<Integer, ItemStack> remainingItems = p.getInventory().addItem(itemManager.getMedkit());
                                    if (!remainingItems.isEmpty()) {
                                        for (i3 = 0; i3 < remainingItems.size(); i3++) {
                                            p.getWorld().dropItemNaturally(p.getLocation(), remainingItems.get(i));
                                        }
                                    }
                                }
                            }else if (event.getSlot() == 3) {
                                if (p.getInventory().containsAtLeast(itemManager.getCoin(), Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("SchiffPrice").getScore())) {
                                    clearCoins(p, Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("SchiffPrice").getScore());
                                    HashMap<Integer, ItemStack> remainingItems = p.getInventory().addItem(itemManager.getSchiff());
                                    if (!remainingItems.isEmpty()) {
                                        for (i3 = 0; i3 < remainingItems.size(); i3++) {
                                            p.getWorld().dropItemNaturally(p.getLocation(), remainingItems.get(i));
                                        }
                                    }
                                }
                            }else if (event.getSlot() == 4) {
                                if (p.getInventory().containsAtLeast(itemManager.getCoin(), Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("MultirevivePrice").getScore())) {
                                    clearCoins(p, Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("MultirevivePrice").getScore());
                                    HashMap<Integer, ItemStack> remainingItems = p.getInventory().addItem(itemManager.getMultirevive());
                                    if (!remainingItems.isEmpty()) {
                                        for (i3 = 0; i3 < remainingItems.size(); i3++) {
                                            p.getWorld().dropItemNaturally(p.getLocation(), remainingItems.get(i));
                                        }
                                    }
                                }
                            }else if (event.getSlot() == 5) {
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
            Player p = (Player) event.getEntity();
            int i;
            int i2;
            for (i = 0; i < GameDataList.size(); i++) {
                for (i2 = 0; i2 < GameDataList.get(i).getPlayerList().size(); i2++) {
                    if (GameDataList.get(i).getPlayerList().get(i2).getName().equals(p.getName())) {
                        if (p.getHealth() - event.getFinalDamage() <= 0) {
                            if (GameDataList.get(i).getGamestate() == 3) {
                                event.setCancelled(true);
                                p.addScoreboardTag("dead");
                                GameDataList.get(i).getCorpseList().add(new Corpse(p.getLocation(), p, Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("ReviveTime").getScore(), GameDataList.get(i).getBluePlayerList().contains(p.getName()), main));
                                p.setGameMode(GameMode.SPECTATOR);
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
                    Player p = (Player) event.getEntity();
                    p.addScoreboardTag("dead");
                    gameData.getCorpseList().add(new Corpse(p.getLocation(), p, Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("ReviveTime").getScore(), gameData.getBluePlayerList().contains(p.getName()), main));
                    p.setGameMode(GameMode.SPECTATOR);
                }
                cancel();
            }
        }.runTaskTimer(main, 1L, 0L);
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
                                    GameDataList.get(i).getCorpseList().get(i3).setReviving(true);
                                    ReviveCorpse(Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("ResTime").getScore(), GameDataList.get(i).getCorpseList().get(i3), p, GameDataList.get(i));
                                    break;
                                }
                            }
                        }
                        for (i3 = 0; i3 < GameDataList.get(i).getTreasureNumberList().size(); i3++) {
                            if (GameDataList.get(i).getTreasureStatusList().get(i3)) {
                                int radius = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("ResRadius").getScore();
                                int x = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(i).getSelectedMapNumber() + "TreasureSpawn" + GameDataList.get(i).getTreasureNumberList().get(i3) + "X").getScore();
                                int y = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(i).getSelectedMapNumber() + "TreasureSpawn" + GameDataList.get(i).getTreasureNumberList().get(i3) + "Y").getScore();
                                int z = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(i).getSelectedMapNumber() + "TreasureSpawn" + GameDataList.get(i).getTreasureNumberList().get(i3) + "Z").getScore();
                                if (x + radius >= p.getLocation().getX() && p.getLocation().getX() >= x - radius && y + radius >= p.getLocation().getY() && p.getLocation().getY() >= y - radius && z + radius >= p.getLocation().getZ() && p.getLocation().getZ() >= z - radius) {
                                    PickupTreasure(Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("TreasureTime").getScore(), i3, p, GameDataList.get(i));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void ReviveCorpse(int resTime, Corpse corpse, Player p, GameData gameData) {
        new BukkitRunnable() {
            int number = resTime;
            @Override
            public void run() {
                if (number > 0) {
                    if (!p.isSneaking() && p.getInventory().getItemInMainHand().isSimilar(itemManager.getMedkit()) && corpse.isRevivable()) {
                        corpse.setReviving(false);
                        cancel();
                    }
                    number--;
                }else {
                    if (!p.isSneaking() && p.getInventory().getItemInMainHand().isSimilar(itemManager.getMedkit()) && corpse.isRevivable()) {
                        corpse.setReviving(false);
                        cancel();
                    }
                    corpse.getPlayer().teleport(corpse.getPosition());
                    corpse.getPlayer().setGameMode(GameMode.ADVENTURE);
                    corpse.getPlayer().setHealth(Objects.requireNonNull(corpse.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue());
                    corpse.getPlayer().removeScoreboardTag("dead");
                    corpse.updatePlayerInventory();
                    corpse.removeTexture();
                    gameData.getCorpseList().remove(corpse);
                    int amount = 1;
                    for (ItemStack invItem : p.getInventory().getContents()) {
                        if (invItem != null) {
                            if (invItem.isSimilar(itemManager.getMedkit())) {
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
                    cancel();
                }
            }
        }.runTaskTimer(main, 0L, 20L);
    }

    public void PickupTreasure(int pickupTime, int TreasureNumber, Player p, GameData gameData) {
        new BukkitRunnable() {
            int number = pickupTime;
            @Override
            public void run() {
                if (number > 0) {
                    if (!p.isSneaking() && !gameData.getTreasureStatusList().get(TreasureNumber)) {
                        cancel();
                    }
                    number--;
                }else {
                    if (!p.isSneaking() && !gameData.getTreasureStatusList().get(TreasureNumber)) {
                        cancel();
                    }
                    int i;
                    int x = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + gameData.getSelectedMapNumber() + "TreasureSpawn" + gameData.getTreasureNumberList().get(TreasureNumber) + "X").getScore();
                    int y = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + gameData.getSelectedMapNumber() + "TreasureSpawn" + gameData.getTreasureNumberList().get(TreasureNumber) + "Y").getScore();
                    int z = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + gameData.getSelectedMapNumber() + "TreasureSpawn" + gameData.getTreasureNumberList().get(TreasureNumber) + "Z").getScore();
                    p.getWorld().getBlockAt(x, y, z).setType(Material.AIR);
                    HashMap<Integer, ItemStack> remainingItems = p.getInventory().addItem(itemManager.getTreasure());
                    if (!remainingItems.isEmpty()) {
                        for (i = 0; i < remainingItems.size(); i++) {
                            p.getWorld().dropItemNaturally(p.getLocation(), remainingItems.get(i));
                        }
                    }
                    gameData.getTreasureStatusList().set(TreasureNumber, false);
                    cancel();
                }
            }
        }.runTaskTimer(main, 0L, 20L);
    }

    public void spawnBoat(Location location, Player p, int time, int GameDataNumber) {

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setblock " + location.getBlockX() + " " + location.getBlock().getRelative(BlockFace.UP).getY() + " " + location.getBlockZ() + " structure_block{name:\"minecraft:boat_red\",posX:-3,posY:-1,posZ:-9,sizeX:7,sizeY:6,sizeZ:17,rotation:\"NONE\",mirror:\"NONE\",mode:\"LOAD\"} replace");
        location.getBlock().getRelative(BlockFace.UP).getRelative(BlockFace.UP).setType(Material.REDSTONE_BLOCK);


        new BukkitRunnable() {
            int number = time;

            @Override
            public void run() {
                if (number > 0) {
                    number--;
                }else {
                    int x;
                    int y;
                    int z;
                    for (x = location.getBlockX() - 3; x <= location.getBlockX() + 3; x++) {
                        for (y = location.getBlockY(); y <= location.getBlockY() + 6; y++) {
                            for (z = location.getBlockZ() - 9; z <= location.getBlockZ() + 7; z++) {
                                if (y == location.getBlockY()) {
                                    Objects.requireNonNull(location.getWorld()).getBlockAt(x, y, z).setType(Material.WATER, false);
                                }else {
                                    Objects.requireNonNull(location.getWorld()).getBlockAt(x, y, z).setType(Material.AIR, false);
                                }
                            }
                        }
                    }
                    int i;
                    if (GameDataList.get(GameDataNumber).getBluePlayerList().contains(p.getName())) {
                        for (i = 0; i < GameDataList.get(GameDataNumber).getBluePlayerList().size(); i++) {
                            Bukkit.getPlayer(GameDataList.get(GameDataNumber).getBluePlayerList().get(i)).sendMessage(format("&9Das Schiff ist abgefahren"));
                        }
                    }else {
                        for (i = 0; i < GameDataList.get(GameDataNumber).getRedPlayerList().size(); i++) {
                            Bukkit.getPlayer(GameDataList.get(GameDataNumber).getBluePlayerList().get(i)).sendMessage(format("&cDas Schiff ist abgefahren"));
                        }
                    }
                    cancel();
                }
            }
        }.runTaskTimer(main, 0L, 20L);
    }

    public void Restart(int GameDataNumber) {
        int i;
        GameData gameData = GameDataList.get(GameDataNumber);
        gameData.setGamestate(1);
        for (i = 0; i < gameData.getCorpseList().size(); i++) {
            gameData.getCorpseList().get(i).removeTexture();
        }
        for (i = 0; i < gameData.getPlayerList().size(); i++) {
            gameData.getPlayerList().get(i).removeScoreboardTag("dead");
            gameData.getPlayerList().get(i).getInventory().clear();
        }
        for (i = 0; i < gameData.getTreasureNumberList().size(); i++) {
            int x = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + gameData.getSelectedMapNumber() + "TreasureSpawn" + gameData.getTreasureNumberList().get(i) + "X").getScore();
            int y = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + gameData.getSelectedMapNumber() + "TreasureSpawn" + gameData.getTreasureNumberList().get(i) + "Y").getScore();
            int z = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + gameData.getSelectedMapNumber() + "TreasureSpawn" + gameData.getTreasureNumberList().get(i) + "Z").getScore();
            Objects.requireNonNull(Bukkit.getWorld("Map" + gameData.getSelectedMapNumber())).getBlockAt(x, y, z).setType(Material.AIR);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill @e[tag=treasure_texture]");
        }
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

    public ArrayList<GameData> getGameDataList() {
        return GameDataList;
    }

    public String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
