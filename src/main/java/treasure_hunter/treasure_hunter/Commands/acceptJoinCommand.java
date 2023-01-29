package treasure_hunter.treasure_hunter.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import treasure_hunter.treasure_hunter.Treasure_Hunter;

public class acceptJoinCommand implements CommandExecutor {

    Treasure_Hunter main;
    public acceptJoinCommand(Treasure_Hunter treasure_hunter) {
        main = treasure_hunter;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        return true;
    }
}
