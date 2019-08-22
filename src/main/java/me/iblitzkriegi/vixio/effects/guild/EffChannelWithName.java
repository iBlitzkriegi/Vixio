package me.iblitzkriegi.vixio.effects.guild;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.exceptions.PermissionException;
import org.bukkit.event.Event;

public class EffChannelWithName extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffChannelWithName.class,
                "create text[(-| )]channel [named] %string% [in %guild%] [(as|with) %bot/string%]", "create voice[(-| )]channel [named] %string% [in %guild%] [(as|with) %bot/string%]")
                .setName("Create channel")
                .setDesc("Create either a voice channel or a text channel as requested.")
                .setExample(
                        "discord command $create <text> <text>:",
                        "\ttrigger:",
                        "\t\tif arg-1 contains \"voice\":",
                        "\t\t\tcreate voice channel named arg-2",
                        "\t\t\tstop",
                        "\t\telse if arg-1 contains \"text\":",
                        "\t\t\tcreate text channel named arg-2",
                        "\t\t\tstop",
                        "\t\treply with \"The first argument must either be \"\"text\"\" or \"\"voice\"\"\""
                );
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
                bindedGuild.createTextChannel(name).queue();
                return;
            }
            bindedGuild.createVoiceChannel(name).queue();
            return;
        } catch (PermissionException x) {
            Vixio.getErrorHandler().needsPerm(bot, x.getPermission().getName(), "create channel");
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "create channel named " + name.toString(e, debug) + " in " + guild.toString(e, debug) + " as " + bot.toString(e, debug);
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
