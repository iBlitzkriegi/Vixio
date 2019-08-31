package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimpleExpression;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.exceptions.PermissionException;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.Objects;

public class ExprAfkChannel extends ChangeableSimpleExpression<VoiceChannel> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprAfkChannel.class, VoiceChannel.class,
                "afk channel", "guilds")
                .setName("Afk channel of Guild")
                .setDesc("Get the AFK voice channel of a guild. You can set this to another channel.")
                .setExample(
                        "discord command $afk:",
                        "\ttrigger:",
                        "\t\treply with \"%afk channel of event-guild%\"");
    }

    private Expression<Guild> guilds;

    @Override
    protected VoiceChannel[] get(Event e) {
        Guild[] guilds = this.guilds.getAll(e);
        if (guilds == null) {
            return null;
        }
        return Arrays.stream(guilds)
                .filter(Objects::nonNull)
                .map(Guild::getAfkChannel)
                .toArray(VoiceChannel[]::new);
    }

    @Override
    public boolean isSingle() {
        return guilds.isSingle();
    }

    @Override
    public Class<? extends VoiceChannel> getReturnType() {
        return VoiceChannel.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode, boolean vixioChanger) {
        if (mode == Changer.ChangeMode.SET) {
            return new Class[]{VoiceChannel.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Bot bot, Changer.ChangeMode mode) {
        Guild[] guilds = this.guilds.getAll(e);
        if (guilds == null) {
            return;
        }
        VoiceChannel channel = (VoiceChannel) delta[0];
        for (Guild guild : guilds) {
            Guild boundGuild = Util.bindGuild(bot, guild);
            try {
                if (boundGuild != null) {
                    boundGuild.getManager().setAfkChannel(channel).queue();
                }
            } catch (PermissionException x) {
                Vixio.getErrorHandler().needsPerm(bot, "set afk channel", x.getPermission().getName());
            }
        }

    }

    @Override
    public String toString(Event e, boolean debug) {
        return "afk channel of " + guilds.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        guilds = (Expression<Guild>) exprs[0];
        return true;
    }
}
