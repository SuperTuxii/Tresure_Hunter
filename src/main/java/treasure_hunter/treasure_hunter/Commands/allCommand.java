package treasure_hunter.treasure_hunter.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import treasure_hunter.treasure_hunter.Treasure_Hunter;

public class allCommand implements CommandExecutor {
    Treasure_Hunter main;
    public allCommand(Treasure_Hunter treasureHunter) {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return true;
    }
}
