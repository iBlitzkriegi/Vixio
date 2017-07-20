package me.iblitzkriegi.vixio.commands;

import me.iblitzkriegi.vixio.Vixio;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Blitz on 5/7/2017.
 */
public class VixioCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("vixio")){
            if(sender instanceof Player){
                if(args.length == 0){
                    StringBuilder builder = new StringBuilder();
                    builder.append("&8{&b*&8}&f&m------&bVixio 1/1&f&m------&8{&b*&8}").append("\n");
                    builder.append("&b/vixio reload &f- Reload the Vixio config.yml").append("\n");
                    builder.append("&b/vixio debug &f- Get Blitz some information to help").append("\n");
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(builder)));
                    return true;
                }else if(args.length == 1){
                    if(args[0].equalsIgnoreCase("reload")){
                        Vixio.getPl().reloadConfig();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bVixio&8]&fSuccessfully reloaded Vixio's config located at plugins/Vixio/config.yml!"));
                        return true;
                    }else if(args[0].equalsIgnoreCase("debug")){
                        StringBuilder builder = new StringBuilder();
                        builder.append("&8{&b*&8}&f&m------&bVixio&f&m------&8{&b*&8}").append("\n");
                        builder.append("&bSpigot version: &f" + Bukkit.getBukkitVersion()).append("\n");
                        builder.append("&bVixio version: &f" + Vixio.getPl().getDescription().getVersion()).append("\n");
                        builder.append("&bSkript version: &f" + Bukkit.getPluginManager().getPlugin("Skript").getDescription().getVersion()).append("\n");
                        sendMessage(sender, builder.toString());
                        return true;
                    }else{
                        sendMessage(sender, "&8[&bVixio&8]&fUnknown Vixio command, type &b/vixio &ffor help.");
                        return true;
                    }
                }else{
                    sendMessage(sender, "&8[&bVixio&8]&fUnknown Vixio command, type &b/vixio &ffor help.");
                    return true;
                }
            }
        }
        return true;
    }
    private static void sendMessage(CommandSender sender, String msg){
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
}
