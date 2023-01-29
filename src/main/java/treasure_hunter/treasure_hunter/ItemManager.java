package treasure_hunter.treasure_hunter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class ItemManager {

    public ItemStack getRevolver() {
        ItemStack Item = new ItemStack(Material.CROSSBOW, 1);
        ItemMeta itemMeta = Item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(format("&rRevolver"));
        itemMeta.setCustomModelData(1);
        Item.setItemMeta(itemMeta);
        
        return Item;
    }
    
    public ItemStack getBullet(int count) {
        ItemStack Item = new ItemStack(Material.ARROW, count);
        ItemMeta itemMeta = Item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(format("&rBullet"));
        itemMeta.setLore(Collections.singletonList(format("&r&dAmmunition für den Revolver")));
        itemMeta.setCustomModelData(1);
        Item.setItemMeta(itemMeta);
        
        return Item;
    }
    
    public ItemStack getMedkit() {
        ItemStack Item = new ItemStack(Material.GOLDEN_APPLE, 1);
        ItemMeta itemMeta = Item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(format("&rMedkit"));
        itemMeta.setLore(Arrays.asList(format("&r&dWiederbeleben eines Toten and deren Leiche"), format("&r&dAktivierung durch Sneak")));
        itemMeta.setCustomModelData(1);
        Item.setItemMeta(itemMeta);

        return Item;
    }

    public ItemStack getSchiff() {
        ItemStack Item = new ItemStack(Material.JUNGLE_BOAT);
        ItemMeta itemMeta = Item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(format("&rSchiff"));
        itemMeta.setLore(Arrays.asList(format("&r&dZum verlassen des Spiels und zum sichern von Schätzen"), format("&r&dZeit bis Schiff bereit ist: " + Objects.requireNonNull(Bukkit.getScoreboardManager().getMainScoreboard().getObjective("CTreasureHunter")).getScore("SchiffReadyTime").getScore())));
        itemMeta.setCustomModelData(1);
        Item.setItemMeta(itemMeta);
        
        return Item;
    }
    
    public ItemStack getMultirevive() {
        ItemStack Item = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta itemMeta = Item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(format("&rMultirevive"));
        itemMeta.setLore(Collections.singletonList(format("&r&dBelebt alle Toten des Teams wieder")));
        itemMeta.setCustomModelData(1);
        Item.setItemMeta(itemMeta);

        return Item;
    }

    public ItemStack getCoins(int count) {
        ItemStack Item = new ItemStack(Material.DIAMOND, count);
        ItemMeta itemMeta = Item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(format("&rCoin"));
        itemMeta.setLore(Collections.singletonList(format("&r&dUsed to buy items in shops")));
        itemMeta.setCustomModelData(1);
        Item.setItemMeta(itemMeta);

        return Item;
    }

    public ItemStack getCoin() {
        ItemStack Item = new ItemStack(Material.DIAMOND);
        ItemMeta itemMeta = Item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(format("&rCoin"));
        itemMeta.setLore(Collections.singletonList(format("&r&dUsed to buy items in shops")));
        itemMeta.setCustomModelData(1);
        Item.setItemMeta(itemMeta);

        return Item;
    }

    public ItemStack getTreasure() {
        ItemStack Item = new ItemStack(Material.CHEST, 1);
        ItemMeta itemMeta = Item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(format("&r&6Treasure"));
        itemMeta.setLore(Collections.singletonList(format("&r&dVery heavy")));
        itemMeta.setCustomModelData(1);
        Item.setItemMeta(itemMeta);

        return Item;
    }
    
    public String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
