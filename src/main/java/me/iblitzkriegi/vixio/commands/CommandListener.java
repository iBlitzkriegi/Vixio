package me.iblitzkriegi.vixio.commands;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.localization.Language;
import me.iblitzkriegi.vixio.util.Util;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

public class CommandListener extends ListenerAdapter {
    public static MessageReceivedEvent lastCommandEvent;


    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getAuthor().isBot()) {
            return;
        }

        String content = e.getMessage().getContentRaw();

        for (Signature storage : DiscordCommandFactory.getInstance().getCommands()) {
            DiscordCommand command = storage.getCommand();
            for (Expression<String> prefix : command.getPrefixes()) {

                for (String alias : command.getUsableAliases()) {

                    DiscordCommandEvent event = new DiscordCommandEvent(null, alias, command, null,
                            e.getGuild(), e.getChannel(), e.getTextChannel(), e.getMessage(),
                            e.getAuthor(), e.getMember(), Util.botFrom(e.getJDA()));

                    String usedCommand = null;
                    String rawPrefix = prefix.getSingle(event);
                    boolean mentions = false;
                    if (!e.getMessage().getMentionedMembers().isEmpty()) {
                        Member suspectedBot = e.getMessage().getMentionedMembers().get(0);
                        if (rawPrefix.contains(suspectedBot.getUser().getId())) {
                            rawPrefix = rawPrefix.replaceFirst("!", "");
                            mentions = true;
                        }
                    }

                    if (rawPrefix.endsWith(" ")) {
                        String[] spacedCommand = content.split(" ");
                        String suspectedPrefix = mentions ? spacedCommand[0].replaceFirst("!", "") : spacedCommand[0];
                        if ((suspectedPrefix + " ").equalsIgnoreCase(rawPrefix)) {
                            usedCommand = rawPrefix + spacedCommand[1];
                        }

                    } else {
                        usedCommand = mentions ? content.split(" ")[0].replaceFirst("!", "") : content.split(" ")[0];
                    }

                    if (nonNull(usedCommand)) {
                        if (nonNull(rawPrefix) && usedCommand.equalsIgnoreCase(rawPrefix + alias)) {
                            event.setPrefix(rawPrefix);
                            try {
                                //event.setArguments(content.replaceFirst(rawPrefix, "").replaceFirst(alias, ""));
                                 event.setArguments(content.substring((usedCommand).length() + 1));
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