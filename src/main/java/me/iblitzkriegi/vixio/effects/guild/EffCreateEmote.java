package me.iblitzkriegi.vixio.effects.guild;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.skript.AsyncEffect;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.exceptions.PermissionException;
import org.bukkit.event.Event;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class EffCreateEmote extends AsyncEffect {
    static {
        Vixio.getInstance().registerEffect(EffCreateEmote.class, "create emote %string% with [the] name %string% [in %guild%] [with %bot/string%]")
                .setName("Create emote")
                .setDesc("Create a emote in a specific guild, the first %string% can either be a direct url or a direct local path to a file.")
                .setExample(
                        "discord command $createEmote <text> <text>:",
                        "\ttrigger:",
                        "\t\tcreate emote arg-1 with name arg-2 ",
                        "\t\twait 2 seconds",
                        "\t\treply with \"%reaction arg-2%\""
                );
    }

    private Expression<String> file;
    private Expression<String> name;
    private Expression<Guild> guild;
    private Expression<Object> bot;

    @Override
    protected void execute(Event e) {
        String file = this.file.getSingle(e);
        String name = this.name.getSingle(e);
        Guild guild = this.guild.getSingle(e);
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (file == null || name == null || guild == null || bot == null) {
            return;
        }
        Guild boundGuild = Util.bindGuild(bot, guild);
        if (boundGuild == null) {
            return;
        }

        if (Util.isLink(file)) {
            InputStream inputStream = Util.getInputStreamFromUrl(file);
            if (inputStream != null) {
                try {
                    boundGuild.createEmote(name, Icon.from(inputStream)).queue();
                } catch (IOException e1) {

                } catch (PermissionException x) {
                    Vixio.getErrorHandler().needsPerm(bot, "create emote", x.getPermission().getName());
                }
            }
        } else {
            File toSend = new File(file);
            if (toSend.exists()) {
                try {
                    boundGuild.createEmote(name, Icon.from(toSend)).queue();
                } catch (IOException e1) {

                } catch (PermissionException x) {
                    Vixio.getErrorHandler().needsPerm(bot, "create emote", x.getPermission().getName());
                }
            }
        }


    }

    @Override
    public String toString(Event e, boolean debug) {
        return "create emote " + file.toString(e, debug) + " with name " + name.toString(e, debug) + " in " + guild.toString(e, debug) + " with " + bot.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        file = (Expression<String>) exprs[0];
        name = (Expression<String>) exprs[1];
        guild = (Expression<Guild>) exprs[2];
        bot = (Expression<Object>) exprs[3];
        return true;
    }
}
