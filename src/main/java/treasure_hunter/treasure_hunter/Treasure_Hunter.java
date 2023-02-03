package treasure_hunter.treasure_hunter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import treasure_hunter.treasure_hunter.Commands.allCommand;
import treasure_hunter.treasure_hunter.Commands.leaveCommand;
import treasure_hunter.treasure_hunter.Commands.setupCommand;
import treasure_hunter.treasure_hunter.Commands.startCommand;

import java.util.Objects;

public final class Treasure_Hunter extends JavaPlugin {
    private final leaveCommand leaveCommand = new leaveCommand(this);
    private final setupCommand setupCommand = new setupCommand(this);
    private final allCommand allCommand = new allCommand(this);
    private final startCommand startCommand = new startCommand(this);
    private final Queue queue = new Queue(this);
    private GameManager gameManager;
    private final MapManager mapManager = new MapManager(this);


    public Scoreboard mainScoreboard;

    @Override
    public void onEnable() {
        // Plugin startup logic
        createScoreboards();
        gameManager = new GameManager(this);
        registerCommands();
        registerEvents();
        createWorlds();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        gameManager.Shutdown();
    }

    public void registerCommands() {
        Objects.requireNonNull(getCommand("leave")).setExecutor(leaveCommand);
        Objects.requireNonNull(getCommand("setup")).setExecutor(setupCommand);
        Objects.requireNonNull(getCommand("all")).setExecutor(allCommand);
        Objects.requireNonNull(getCommand("start")).setExecutor(startCommand);
    }

    public void registerEvents() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(queue, this);
        pm.registerEvents(gameManager, this);
        pm.registerEvents(gameManager.preGameManager, this);
    }

    public void createScoreboards() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        assert manager != null;
        mainScoreboard = manager.getMainScoreboard();
        setupCommand.mainScoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        if (mainScoreboard.getObjective("CTreasureHunter") == null) {
            mainScoreboard.registerNewObjective("CTreasureHunter", "DUMMY", format("&d&lConfig Treasure Hunter"));
            setConfigDefaults();
        }
        if (mainScoreboard.getObjective("TH_MapNames") == null) {
            mainScoreboard.registerNewObjective("TH_MapNames", "DUMMY", format("&d&lTreasure Hunter Map Names"));
        }
        if (mainScoreboard.getObjective("Kills") == null) {
            mainScoreboard.registerNewObjective("Kills", "DUMMY", format("&d&lTreasure Hunter Kills"));
        }
    }

    public void setConfigDefaults() {
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("InstanceCount").setScore(1);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("QueueSignX").setScore(0);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("QueueSignY").setScore(0);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("QueueSignZ").setScore(0);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("TeamSize").setScore(5);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("MapChoosingTime").setScore(60);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("MapCount").setScore(1);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("RoundTime").setScore(600);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("TreasureAmount").setScore(3);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("TreasureTime").setScore(15);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("TreasureRadius").setScore(2);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("ReviveTime").setScore(180);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("ResTime").setScore(10);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("ResRadius").setScore(2);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("SchiffReadyTime").setScore(60);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("RevolverPrice").setScore(1);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("BulletPrice").setScore(1);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("BulletAmount").setScore(5);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("MedkitPrice").setScore(1);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("SchiffPrice").setScore(1);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("MultirevivePrice").setScore(1);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("DebugMode").setScore(0);
    }

    public void createWorlds() {
        int i;
        for (i = 1; i <= Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("MapCount").getScore(); i++) {
            WorldCreator c = new WorldCreator("Map" + i);
            c.type(WorldType.FLAT);
            c.generateStructures(false);
            c.createWorld();
        }
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public Queue getQueue() {
        return queue;
    }

    public String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
