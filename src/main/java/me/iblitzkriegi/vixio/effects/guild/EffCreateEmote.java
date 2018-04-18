package me.iblitzkriegi.vixio.effects.guild;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Icon;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class EffCreateEmote extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffCreateEmote.class, "create emote %string% with name %string% [in %guild%] [with %bot/string%]")
                .setName("Create emote")
                .setDesc("Create a emote in a specific guild, the first %string% can either be a direct url or a direct local path to a file.")
                .setExample("create emote \"C:\\Users\\matthew\\Desktop\\Vixio2\\plugins\\Cat.jpg");
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
        Guild bindedGuild = Util.bindGuild(bot, guild);
        if (bindedGuild == null) {
            return;
        }

        if (Util.isLink(file)) {
            InputStream inputStream = Util.getInputStreamFromUrl(file);
            if (inputStream != null) {
                try {
                    bindedGuild.getController().createEmote(name, Icon.from(inputStream)).queue();
                } catch (IOException e1) {

                } catch (PermissionException x) {
                    Vixio.getErrorHandler().needsPerm(bot, "create emote", x.getPermission().getName());
                }
            }
        } else {
            File toSend = new File(file);
            if (toSend.exists()) {
                try {
                    bindedGuild.getController().createEmote(name, Icon.from(toSend)).queue();
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
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        file = (Expression<String>) exprs[0];
        name = (Expression<String>) exprs[1];
        guild = (Expression<Guild>) exprs[2];
        bot = (Expression<Object>) exprs[3];
        return true;
    }
}
