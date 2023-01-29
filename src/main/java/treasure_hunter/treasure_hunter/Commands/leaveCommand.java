package treasure_hunter.treasure_hunter.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import treasure_hunter.treasure_hunter.Treasure_Hunter;

public class leaveCommand implements CommandExecutor {

    Treasure_Hunter main;
    public leaveCommand(Treasure_Hunter treasure_hunter) {
        main = treasure_hunter;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            int i;
            for (i = 0; i < main.getQueue().PlayerList.size(); i++) {
                if (p.getName().equals(main.getQueue().PlayerList.get(i).getName())) {
                    main.getQueue().PlayerList.remove(i);
                    i--;
                }
            }
        }
        return true;
    }
}
