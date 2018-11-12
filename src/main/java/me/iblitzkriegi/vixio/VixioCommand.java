package me.iblitzkriegi.vixio;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class VixioCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("vixio")) {
            if (args.length == 0) {
                StringBuilder builder = new StringBuilder();
                builder.append("&8&l{&b&l*&8&l}&f&l&m------&b&lVixio 1/5&f&l&m------&8&l{&b&l*&8&l}").append("\n");
                builder.append("&b&l/vixio reload &f&l- Reload the Vixio config.yml").append("\n");
                builder.append("&b&l/vixio debug &f&l- Get information about your server.").append("\n");
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(builder)));
                return true;
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (!sender.hasPermission("vixio.use")) {
                        sendMessage(sender, "You do not have the required permissions to execute this command.");
                        return true;
                    }
                    Vixio.getInstance().reloadConfig();
                    sendMessage(sender, "Successfully reloaded Vixio's config.");
                    return true;
                } else if (args[0].equalsIgnoreCase("debug")) {
                    if (!sender.hasPermission("vixio.use")) {
                        sendMessage(sender, "You do not have the required permissions to execute this command.");
                        return true;
                    }
                    StringBuilder builder = new StringBuilder();
                    Server server = Vixio.getInstance().getServer();
                    builder.append("&8&l{&b&l*&8&l}&f&l&m------&b&lVixio debug&f&l&m------&8&l{&b&l*&8&l}");
                    builder.append("\n");
                    builder.append("&b&lMinecraft: &f" + server.getVersion());
                    builder.append("\n");
                    builder.append("&b&lServer: &f" + server.getBukkitVersion());
                    builder.append("\n");
                    builder.append("&b&lVixio: &f" + Vixio.getInstance().getDescription().getVersion());
                    builder.append("\n");
                    builder.append("&b&lSkript: &f" + server.getPluginManager().getPlugin("Skript").getDescription().getVersion());
                    builder.append("\n");
                    builder.append("&8&l{&b&l*&8&l}&f&l&m---------------------&8&l{&b&l*&8&l}");
                    sendMessage(sender, builder.toString(), false);
                }
            }
        }
        return true;
    }

    public void sendMessage(CommandSender sender, String text) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bVixio&8] &f" + text));
    }

    public void sendMessage(CommandSender sender, String text, boolean b) {
        if (b) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bVixio&8] &f" + text));
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
        }
    }
}