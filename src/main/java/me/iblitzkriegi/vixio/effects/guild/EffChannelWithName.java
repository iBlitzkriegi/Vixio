package me.iblitzkriegi.vixio.effects.guild;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

public class EffChannelWithName extends Effect{
    static {
        Vixio.getInstance().registerEffect(EffChannelWithName.class,
                "create text[(-| )]channel [named] %string% [in %guild%] [(as|with) %bot/string%] ", "create voice[(-| )]channel [named] %string% [in %guild%] [(as|with) %bot/string%]")
                .setName("Create channel")
                .setDesc("Create either a voice channel or a text channel as requested.")
                .setExample("on guild message received:" +
                        "\tif name of event-bot contains \"Jewel\":\t" +
                        "\t\tset {_cmd::*} to split content of event-message at \" \"" +
                        "\t\tif {_cmd::1} is \"create\":" +
                        "\t\t\tif id of event-user is \"98208218022428672\":" +
                        "\t\t\t\tif {_cmd::2} is \"text\":" +
                        "\t\t\t\t\tcreate text channel named \"%{_cmd::3}%\"" +
                        "\t\t\t\telse if {_cmd::2} is \"voice\":" +
                        "\t\t\t\t\tcreate voice channel named \"%{_cmd::3}%\"");
    }
    private Expression<String> name;
    private Expression<Guild> guild;
    private Expression<Object> bot;
    private boolean not;
    @Override
    protected void execute(Event e) {
        String name = this.name.getSingle(e);
        Guild guild = this.guild.getSingle(e);
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Guild bindedGuild = Util.bindGuild(bot, guild);
        if (bot == null || guild == null || name == null || name.isEmpty() || bindedGuild == null) {
            return;
        }
        try {
            if (not) {
                bindedGuild.getController().createTextChannel(name).queue();
                return;
            }
            bindedGuild.getController().createVoiceChannel(name).queue();
            return;
        } catch (PermissionException x) {
            Vixio.getErrorHandler().needsPerm(bot, x.getPermission().getName(), "create channel");
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "create channel named " + name.toString(e, debug) + " in " + guild.toString(e, debug) + " as " + bot.toString(e,debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        name = (Expression<String>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        bot = (Expression<Object>) exprs[2];
        not = matchedPattern == 0;
        return true;
    }
}
