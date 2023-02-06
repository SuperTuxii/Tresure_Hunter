
public class MapMapRenderer extends MapRenderer {
    Treasure_Hunter main;
    public MapMapRenderer(Treasure_Hunter treasure_hunter) {
        main = treasure_hunter;
    }
    
    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        int i;
        int i2;
        for (i = 0; i < main.getGameManager().getGameDataList().size(); i++) {
            if (main.getGameManager().getGameDataList().get(i).getBluePlayerList().contains(player.getName()) {
                map.setCenterX(Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(i).getSelectedMapNumber() + "MapCenterX").getScore());
                map.setCenterZ(Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(i).getSelectedMapNumber() + "MapCenterZ").getScore());
                if ((Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(i).getSelectedMapNumber() + "MapScale").getScore() == 1)) {
                    map.setScale(org.bukkit.map.MapView.Scale.CLOSEST);
                }else if ((Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(i).getSelectedMapNumber() + "MapScale").getScore() == 2)) {
                    map.setScale(org.bukkit.map.MapView.Scale.CLOSE);
                }else if ((Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(i).getSelectedMapNumber() + "MapScale").getScore() == 3)) {
                    map.setScale(org.bukkit.map.MapView.Scale.NORMAL);
                }else if ((Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(i).getSelectedMapNumber() + "MapScale").getScore() == 4)) {
                    map.setScale(org.bukkit.map.MapView.Scale.FAR);
                }else if ((Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(i).getSelectedMapNumber() + "MapScale").getScore() == 5)) {
                    map.setScale(org.bukkit.map.MapView.Scale.FARTHEST);
                }
                map.setTrackingPosition(true);
                map.setUnlimitedTracking(true);
                MapCursorCollection Cursors = new MapCursorCollection();
                for (i2 = 0; i2 < main.getGameManager().getGameDataList().get(i).getTreasureNumberList().size(); i2++) {
                    World world = Bukkit.getWorld("Map" + GameDataList.get(GameDataNumber).getSelectedMapNumber());
                    int x = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(GameDataNumber).getSelectedMapNumber() + "TreasureSpawn" + GameDataList.get(GameDataNumber).getTreasureNumberList().get(i2) + "X").getScore();
                    int y = Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(GameDataNumber).getSelectedMapNumber() + "TreasureSpawn" + GameDataList.get(GameDataNumber).getTreasureNumberList().get(i2) + "Z").getScore();
                    x -= map.getCenterX();
                    y -= map.getCenterZ();
                    x /= Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(i).getSelectedMapNumber() + "MapScale").getScore();
                    y /= Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("Map" + GameDataList.get(i).getSelectedMapNumber() + "MapScale").getScore();
                    MapCursor cursor = Cursors.addCursor(x, y, 0);
                    cursor.setVisible(true);
                    cursor.setType(MapCursor.Type.RED_X);
                }
                canvas.setCursors(Cursors);
            }
        }
    }
}
