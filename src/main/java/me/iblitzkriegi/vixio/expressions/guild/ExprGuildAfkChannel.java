package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.Objects;

public class ExprGuildAfkChannel extends SimpleExpression<VoiceChannel> {
    static {
        Vixio.getInstance().registerExpression(ExprGuildAfkChannel.class, VoiceChannel.class, ExpressionType.SIMPLE, "afk channel[s] of %guilds% [(as|with)] [%bot/string%]")
                .setName("Afk channel of Guild")
                .setDesc("Get the afk voice channel of a Guild, has a Set changer.")
                .setExample("coming soon");
    }
    private Expression<Guild> guilds;
    private Expression<Object> bot;
    @Override
    protected VoiceChannel[] get(Event e) {
        Guild[] guilds = this.guilds.getAll(e);
        if (guilds == null) {
            return null;
        }
        return Arrays.stream(guilds)
                .map(guild -> guild.getAfkChannel())
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
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if ((mode == Changer.ChangeMode.SET)) {
            return new Class[]{VoiceChannel.class};
        }
        return super.acceptChange(mode);
    }

    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Guild[] guilds = this.guilds.getAll(e);
        if (bot == null || guilds == null) {
            return;
        }
        VoiceChannel channel = (VoiceChannel) delta[0];
        for (Guild guild : guilds) {
            try {
                if (Util.botIsConnected(bot, guild.getJDA())) {
                    guild.getManager().setAfkChannel(channel).queue();
                } else {
                    Guild bindingGuild = Util.bindGuild(bot, guild);
                    if (bindingGuild != null) {
                        bindingGuild.getManager().setAfkChannel(channel).queue();
                    }
                }
            } catch (PermissionException x) {
                Vixio.getErrorHandler().needsPerm(bot, "set afk channel", x.getPermission().getName());
            }
        }

    }

    @Override
    public String toString(Event e, boolean debug) {
        return "afk channel of " + guilds.toString(e, debug) + (bot == null ? "" : " as " + bot.toString(e, debug));
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        guilds = (Expression<Guild>) exprs[0];
        bot = (Expression<Object>) exprs[1];
        return true;
    }
}
