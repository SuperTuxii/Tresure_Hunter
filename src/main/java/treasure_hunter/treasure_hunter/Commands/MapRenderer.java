package treasure_hunter.treasure_hunter.Commands;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapView;
import treasure_hunter.treasure_hunter.Treasure_Hunter;

public class MapRenderer extends org.bukkit.map.MapRenderer {

    Treasure_Hunter main;
    public MapRenderer(Treasure_Hunter treasureHunter) {
        main = treasureHunter;
    }

    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        int i;
        for (i = 0; i < main.getGameManager().getGameDataList().size(); i++) {
            if (main.getGameManager().getGameDataList().get(i).getBluePlayerList().contains(player)) {

            }
        }
    }
}
