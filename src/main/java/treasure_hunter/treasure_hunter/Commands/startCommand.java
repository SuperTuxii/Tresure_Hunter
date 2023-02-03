package treasure_hunter.treasure_hunter.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import treasure_hunter.treasure_hunter.Treasure_Hunter;

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
            int i2;
            ArrayList<String> PlayerList = new ArrayList<>();
            ArrayList<Player> GamePlayerList = new ArrayList<>();
            ArrayList<String> GamePlayerNameList = new ArrayList<>();
            for (i = 0; i < main.getQueue().PlayerList.size(); i++) {
                PlayerList.add(main.getQueue().PlayerList.get(i).getName());
            }
            if (!PlayerList.contains(p.getName())) {
                p.sendMessage(format("&cDu bist in keiner Warteschlange!"));
                return;
            }
            for (i = 0; i < args.length; i++) {
                if (PlayerList.contains(args[i])) {
                    
                }else {
                    p.sendMessage(format("&cPlayer " + args[i] + "");
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
                        ArrayList<Player> GamePlayerList = new ArrayList<>();
                        
                        main.getGameManager().startGame(GamePlayerList, GameDataNumber);
                    } else {
        }
        return true;
    }

    public String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
