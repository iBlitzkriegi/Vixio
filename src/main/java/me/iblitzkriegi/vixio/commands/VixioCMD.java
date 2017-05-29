package me.iblitzkriegi.vixio.commands;

import me.iblitzkriegi.vixio.Vixio;
import net.md_5.bungee.api.ChatColor;
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
                    builder.append("&8&l{&b&l*&8&l}&f&l&m------&b&lVixio 1/5&f&l&m------&8&l{&b&l*&8&l}").append("\n");
                    builder.append("&b&l/vixio reload &f&l- Reload the Vixio config.yml").append("\n");
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(builder)));
                    return true;
                }else if(args.length == 1){
                    if(args[0].equalsIgnoreCase("reload")){
                        Vixio.getPl().reloadConfig();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bVixio&8]&fSuccessfully reloaded Vixio's config located at plugins/Vixio/config.yml!"));
                        return true;
                    }
                }
            }
        }
        return true;
    }
}
