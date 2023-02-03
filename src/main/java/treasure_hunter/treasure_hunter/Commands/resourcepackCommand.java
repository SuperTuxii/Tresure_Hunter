package treasure_hunter.treasure_hunter.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class resourcepackCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            ((Player) sender).setResourcePack("https://www.udrop.com/file/7OBA/Server_Resourcepack_Plugin_(2).zip");
        }
        return true;
    }
}
