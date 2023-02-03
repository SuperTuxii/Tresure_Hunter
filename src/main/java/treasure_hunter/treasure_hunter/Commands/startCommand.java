package treasure_hunter.treasure_hunter.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import treasure_hunter.treasure_hunter.Treasure_Hunter;

import java.util.ArrayList;

public class startCommand implements CommandExecutor {
    Treasure_Hunter main;
    public startCommand(Treasure_Hunter treasureHunter) {
        main = treasureHunter;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && args.length != 0) {
            Player p = (Player) sender;
            int i;
            ArrayList<String> PlayerList = new ArrayList<>();
            ArrayList<Player> GamePlayerList = new ArrayList<>();
            ArrayList<String> GamePlayerNameList = new ArrayList<>();
            for (i = 0; i < main.getQueue().PlayerList.size(); i++) {
                PlayerList.add(main.getQueue().PlayerList.get(i).getName());
            }
            if (!PlayerList.contains(p.getName())) {
                p.sendMessage(format("&cDu bist in keiner Warteschlange!"));
                return true;
            }
            GamePlayerList.add(p);
            GamePlayerNameList.add(p.getName());
            for (i = 0; i < args.length; i++) {
                if (GamePlayerNameList.contains(args[i])) {
                    p.sendMessage(format("&cSpieler " + args[i] + " ist bereits einmal in der Liste"));
                    return true;
                }
                if (PlayerList.contains(args[i])) {
                    GamePlayerNameList.add(args[i]);
                    GamePlayerList.add(Bukkit.getPlayerExact(args[i]));
                }else {
                    p.sendMessage(format("&cSpieler " + args[i] + " nicht in der Warteschlange!"));
                    return true;
                }
            }
            int GameDataNumber = -1;
            for (i = 0; i < main.getGameManager().getGameDataList().size(); i++) {
                if (main.getGameManager().getGameDataList().get(i).getGamestate() == 1) {
                    GameDataNumber = i;
                    break;
                }
            }
            if (GameDataNumber != -1) {
                main.getGameManager().startGame(GamePlayerList, GameDataNumber);
                int i2;
                for (i = 0; i < GamePlayerList.size(); i++) {
                    for (i2 = 0; i2 < PlayerList.size(); i2++) {
                        if (GamePlayerNameList.get(i).equals(PlayerList.get(i2))) {
                            main.getQueue().PlayerList.remove(i2);
                        }
                    }
                }
            } else {
                p.sendMessage(format("&c"));
            }
        }
        return true;
    }

    public String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
