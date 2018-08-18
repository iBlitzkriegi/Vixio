package me.iblitzkriegi.vixio.commands;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.localization.Language;
import me.iblitzkriegi.vixio.util.Util;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;

public class CommandListener extends ListenerAdapter {
    public static MessageReceivedEvent lastCommandEvent;


    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getAuthor().isBot()) {
            return;
        }

        String content = e.getMessage().getContentRaw();

        for (DiscordCommand command : DiscordCommandFactory.getInstance().getCommands()) {

            for (Expression<String> prefix : command.getPrefixes()) {

                for (String alias : command.getUsableAliases()) {

                    DiscordCommandEvent event = new DiscordCommandEvent(null, alias, command, null,
                            e.getGuild(), e.getChannel(), e.getTextChannel(), e.getMessage(),
                            e.getAuthor(), e.getMember(), Util.botFrom(e.getJDA()));
                    String rawPrefix = prefix.getSingle(event);
                    String usedCommand = alias.split( " ")[0];
                    if (nonNull(rawPrefix) && content.equalsIgnoreCase(rawPrefix + usedCommand)) {
                        event.setPrefix(rawPrefix);
                        try {
                            event.setArguments(content.substring((rawPrefix + usedCommand).length() + 1));
                        } catch (StringIndexOutOfBoundsException e1) {
                            event.setArguments(null);
                        }
                        // Because most of bukkit's apis are sync only, make sure to run this on bukkit's thread
                        Util.sync(() -> {

                            lastCommandEvent = e;

                            Bukkit.getPluginManager().callEvent(event);
                            if (!event.isCancelled()) {
                                command.execute(event);
                            }

                        });

                        return;

                    }

                }

            }

        }
    }

    /**
     * Checks if a string is null or the localized string form of null in Skript (usually <none>)
     */
    public boolean nonNull(String s) {
        if (s == null) {
            return false;
        }
        String localized = Language.get("none");
        if (localized == null) {
            // on old skript versions you couldn't change <none> so assume that if result is null
            return !s.equals("<none>");
        }
        return !s.equals(localized);
    }

}