package treasure_hunter.treasure_hunter;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class MultireviveAnimation {
    private Player player;
    private ArrayList<Entity> TextureList = new ArrayList<>();

    public MultireviveAnimation(GameData gameData, Player p) {
        player = p;
    }

    public Player getPlayer() {
        return player;
    }
    public ArrayList<Entity> getTextureList() {
        return TextureList;
    }
}
