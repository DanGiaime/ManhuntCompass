package dangames;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Compass implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("compass")) {
            
            if (!(sender instanceof Player)) {
                sender.sendMessage("Hey console");
                return true;
            }
            // player
            Player player = (Player) sender;

            // give compass
            if (!player.hasPermission("compass.use")) {
                player.sendMessage(ChatColor.RED + "You do not have permission!");
                return true;
            }
            
            //Send message give compass
            player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Compass given!");
            player.getInventory().addItem(getCompass(args[0]));

            

            return true;
        }
        return false;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Location location = player.getLocation();
        ItemStack item = e.getItem();
        String compassDisplayName = item.getItemMeta().getDisplayName();
        if (isCompass(item) && e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK && !compassDisplayName.equalsIgnoreCase("Compass")) {
            player.setCompassTarget(location);
            player.sendMessage("Compass set to " + player.getDisplayName() + "'s current location: " + player.getLocation());

        }
    }

    public boolean isCompass(ItemStack item) {
        return item.getType() == Material.COMPASS;
    }

    public ItemStack getCompass(String playerName) {
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta meta = compass.getItemMeta();
        
        meta.setUnbreakable(true);
        meta.setDisplayName(playerName);

        compass.setItemMeta(meta);

        
        
        return compass;
    }
}