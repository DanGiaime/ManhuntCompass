package dangames;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin{
    
    @Override
    public void onEnable() {
        this.getCommand("compass").setExecutor(new Compass());

        Compass compassListener = new Compass();
        this.getServer().getPluginManager().registerEvents(compassListener, this);
    }

        
    @Override
    public void onDisable() {

    }
   
}