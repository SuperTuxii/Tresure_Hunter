package treasure_hunter.treasure_hunter;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Objects;

public class Queue implements Listener {

    Treasure_Hunter main;

    public Queue(Treasure_Hunter treasure_hunter) {
        main = treasure_hunter;
    }

    public ArrayList<Player> PlayerList = new ArrayList<>();

    private boolean WaitingforFreeGame = false;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            Block block = event.getClickedBlock();
            assert block != null;
            if (block.getLocation().getBlockX() == Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("QueueSignX").getScore() && block.getLocation().getBlockY() == Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("QueueSignY").getScore() && block.getLocation().getBlockZ() == Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("QueueSignZ").getScore()) {
                int i;
                for (i = 0; i < PlayerList.size(); i++) {
                    if (PlayerList.get(i).getName().equals(p.getName())) {
                        PlayerList.remove(i);
                    }
                }
                PlayerList.add(p);
                p.sendMessage(format("&aDu bist nun in der Warteschlange!"));
                if (PlayerList.size() >= Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("TeamSize").getScore() * 2) {
                    int GameDataNumber = -1;
                    for (i = 0; i < main.getGameManager().getGameDataList().size(); i++) {
                        if (main.getGameManager().getGameDataList().get(i).getGamestate() == 1) {
                            GameDataNumber = i;
                            break;
                        }
                    }
                    if (GameDataNumber != -1) {
                        ArrayList<Player> GamePlayerList = new ArrayList<>();
                        while (GamePlayerList.size() != Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("TeamSize").getScore() * 2) {
                            GamePlayerList.add(PlayerList.get(0));
                            PlayerList.remove(0);
                        }
                        main.getGameManager().startGame(GamePlayerList, GameDataNumber);
                    } else {
                        if (!WaitingforFreeGame) {
                            WaitingforFreeGame = true;
                            WaitingforFreeGame();
                        }
                    }
                }
            }
        }
    }

    public void WaitingforFreeGame() {
        new BukkitRunnable() {

            @Override
            public void run() {
                if (WaitingforFreeGame) {
                    int i;
                    int GameDataNumber = -1;
                    for (i = 0; i < main.getGameManager().getGameDataList().size(); i++) {
                        if (main.getGameManager().getGameDataList().get(i).getGamestate() == 1) {
                            GameDataNumber = i;
                            break;
                        }
                    }
                    if (GameDataNumber != -1) {
                        if (PlayerList.size() >= Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("TeamSize").getScore() * 2) {
                            ArrayList<Player> GamePlayerList = new ArrayList<>();
                            while (GamePlayerList.size() != Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("TeamSize").getScore() * 2) {
                                GamePlayerList.add(PlayerList.get(0));
                                PlayerList.remove(0);
                            }
                            main.getGameManager().startGame(GamePlayerList, GameDataNumber);
                        }
                        if (PlayerList.size() < Objects.requireNonNull(main.mainScoreboard.getObjective("CTreasureHunter")).getScore("TeamSize").getScore() * 2) {
                            WaitingforFreeGame = false;
                        }
                    }
                }else {
                    cancel();
                }
            }
        }.runTaskTimer(main, 0L, 1L);
    }

    public String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}