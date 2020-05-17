package dangames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Compass implements CommandExecutor, Listener {

    private static final HashMap<UUID, ArrayList<ItemStack>> heldCompasses = new HashMap<UUID, ArrayList<ItemStack>>();

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (label.equalsIgnoreCase("compass")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage("Hey console");
                return true;
            }
            // player
            final Player player = (Player) sender;

            // give compass
            if (!player.hasPermission("compass.use")) {
                player.sendMessage(ChatColor.RED + "You do not have permission!");
                return true;
            }

            if(args.length == 0) {
                player.sendMessage(ChatColor.RED + "You must specify a player to track!");
                return true;
            }

            // Send message give compass
            player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Compass given to track " + args[0] + "!");
            ItemStack newCompass = getCompass(args[0]);
            player.getInventory().addItem(newCompass);
            if(heldCompasses.get(player.getUniqueId()) == null) {
                heldCompasses.put(player.getUniqueId(), new ArrayList<ItemStack>());
            }
            heldCompasses.get(player.getUniqueId()).add(newCompass);            

            return true;
        }
        return false;
    }

    // Compass right click handler
    @EventHandler
    public void onInteract(final PlayerInteractEvent e) {
        final ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        if(item != null && item.hasItemMeta()) {
            final String compassDisplayName = item.getItemMeta().getDisplayName();
            if (isCompass(item) && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
                    && !compassDisplayName.equalsIgnoreCase("Compass")) {
                final Player targetPlayer = Bukkit.getPlayer(compassDisplayName);
                e.getPlayer().setCompassTarget(targetPlayer.getLocation());
                e.getPlayer()
                        .sendMessage("Compass set to " + targetPlayer.getDisplayName() + "'s current location: "
                                + ChatColor.GREEN + Math.round(targetPlayer.getLocation().getX()) + ","
                                + Math.round(targetPlayer.getLocation().getY()) + "," + Math.round(targetPlayer.getLocation().getZ()));

            }
        }
    }

    @EventHandler
    public void onRespawn(final PlayerRespawnEvent event) {
        final Player player = event.getPlayer();
        ArrayList<ItemStack> items = heldCompasses.get(player.getUniqueId());
        if(items != null) {
            for (final ItemStack compass : items) {
                player.getInventory().addItem(compass);
            }
            player.sendMessage(ChatColor.GREEN + "Your compass(es) have been returned!");
        }
    }

    public boolean isCompass(final ItemStack item) {
        return item.getType() == Material.COMPASS;
    }

    public ItemStack getCompass(final String playerName) {
        final ItemStack compass = new ItemStack(Material.COMPASS);
        final ItemMeta meta = compass.getItemMeta();
        
        meta.setUnbreakable(true);
        meta.setDisplayName(playerName);

        compass.setItemMeta(meta);

        
        
        return compass;
    }
}