package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.VoiceChannel;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.Objects;

public class ExprGuildAfkChannel extends SimpleExpression<VoiceChannel> {
    static {
        Vixio.getInstance().registerExpression(ExprGuildAfkChannel.class, VoiceChannel.class, ExpressionType.SIMPLE, "afk channel of %guilds% [(as|with)] [%bot/string%]")
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
                .filter(Objects::nonNull)
                .map(guild -> guild.getAfkChannel())
                .toArray(VoiceChannel[]::new);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends VoiceChannel> getReturnType() {
        return VoiceChannel.class;
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
