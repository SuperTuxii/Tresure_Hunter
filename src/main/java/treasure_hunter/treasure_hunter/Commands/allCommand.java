package treasure_hunter.treasure_hunter.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import treasure_hunter.treasure_hunter.Treasure_Hunter;

public class allCommand implements CommandExecutor {
    Treasure_Hunter main;
    public allCommand(Treasure_Hunter treasureHunter) {
        main = treasureHunter;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && args.length != 0) {
            Player p = (Player) sender;
            int i;
            int i2;
            int i3;
            int i4;
            for (i = 0; i < main.getGameManager().getGameDataList().size(); i++) {
                for (i2 = 0; i2 < main.getGameManager().getGameDataList().get(i2).getPlayerList().size(); i2++) {
                    if (main.getGameManager().getGameDataList().get(i).getPlayerList().get(i2).getName().equals(p.getName())) {
                        for (i3 = 0; i3 < main.getGameManager().getGameDataList().get(i).getRedPlayerList().size(); i3++) {
                            Player pS = Bukkit.getPlayerExact(main.getGameManager().getGameDataList().get(i).getRedPlayerList().get(i3));
                            StringBuilder Message = new StringBuilder(args[0]);
                            for (i4 = 1; i4 < args.length; i4++) {
                                Message.append(" ");
                                Message.append(args[1]);
                            }
                            if (pS != null) {
                                pS.sendMessage(format("&c[All] &f<" + p.getName() + "> " + Message));
                            }
                        }
                        return true;
                    }
                }
            }
            if (Bukkit.getOperators().contains(p)) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    StringBuilder Message = new StringBuilder(args[0]);
                    for (i2 = 1; i2 < args.length; i2++) {
                        Message.append(" ");
                        Message.append(args[1]);
                    }
                    player.sendMessage(format("&c[All] &f<" + p.getName() + "> " + Message));
                }
            }else {
                p.sendMessage(format("&cDu bist nicht in einem Spiel"));
            }
        }else {
            sender.sendMessage(format("&cKeine Nachricht"));
        }
        return true;
    }

    public String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
