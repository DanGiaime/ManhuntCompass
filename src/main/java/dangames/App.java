package dangames;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin{
    
    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("hello")) {
            if(sender instanceof Player) {
                //player
                Player player = (Player) sender;
                if (player.hasPermission("hello.use")) {
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Hey welcome to the server");
                    return true;
                }
                player.sendMessage(ChatColor.RED + "You do not have permission!");
                return true;
            }
            else {
                sender.sendMessage("Hey console");
                return true;
                //console

            }
        }
        return false;
    }
}