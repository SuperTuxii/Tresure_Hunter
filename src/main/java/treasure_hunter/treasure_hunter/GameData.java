package treasure_hunter.treasure_hunter;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GameData {
    private ArrayList<Player> PlayerList = new ArrayList<>();
    private ArrayList<String> RedPlayerList = new ArrayList<>();
    private ArrayList<String> BluePlayerList = new ArrayList<>();
    private ArrayList<Corpse> CorpseList = new ArrayList<>();
    private int Gamestate = 1;
    //GAMESTATES:
    //1 = Waiting
    //2 = PreGame
    //3 = InGame
    //4 = EndGame
    //5 = Restarting
    private int MapNumber1 = -1;
    private ArrayList<String> Map1List = new ArrayList<>();
    private int MapNumber2 = -1;
    private ArrayList<String> Map2List = new ArrayList<>();
    private int MapNumber3 = -1;
    private ArrayList<String> Map3List = new ArrayList<>();
    private int SelectedMapNumber = -1;
    private ArrayList<Integer> TreasureNumberList = new ArrayList<>();
    private ArrayList<Boolean> TreasureStatusList = new ArrayList<>();

    public int getGamestate() {
        return Gamestate;
    }
    public ArrayList<Player> getPlayerList() {
        return PlayerList;
    }
    public ArrayList<String> getBluePlayerList() {
        return BluePlayerList;
    }
    public ArrayList<String> getRedPlayerList() {
        return RedPlayerList;
    }
    public int getMapNumber1() {
        return MapNumber1;
    }
    public ArrayList<String> getMap1List() {
        return Map1List;
    }
    public int getMapNumber2() {
        return MapNumber2;
    }
    public ArrayList<String> getMap2List() {
        return Map2List;
    }
    public int getMapNumber3() {
        return MapNumber3;
    }
    public ArrayList<String> getMap3List() {
        return Map3List;
    }
    public int getSelectedMapNumber() {
        return SelectedMapNumber;
    }
    public ArrayList<Integer> getTreasureNumberList() {
        return TreasureNumberList;
    }
    public ArrayList<Boolean> getTreasureStatusList() {
        return TreasureStatusList;
    }
    public ArrayList<Corpse> getCorpseList() {
        return CorpseList;
    }
    
    public void setGamestate(int gamestate) {
        Gamestate = gamestate;
    }
    public void setMapNumber1(int mapNumber1) {
        MapNumber1 = mapNumber1;
    }
    public void setMapNumber2(int mapNumber2) {
        MapNumber2 = mapNumber2;
    }
    public void setMapNumber3(int mapNumber3) {
        MapNumber3 = mapNumber3;
    }
    public void setSelectedMapNumber(int selectedMapNumber) {
        SelectedMapNumber = selectedMapNumber;
    }
}
