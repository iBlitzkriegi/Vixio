package me.iblitzkriegi.vixio.expressions.audio;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.audio.GuildMusicManager;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.event.Event;

public class ExprVolume extends SimpleExpression<Number> {
    static {
        Vixio.getInstance().registerExpression(ExprVolume.class, AudioTrack.class, ExpressionType.SIMPLE,
                "[the] volume of %bot/string% [in %guild%]")
                .setName("Volume of bot")
                .setDesc("Get the volume a bot is set to in a guild. Can be set to a number that is between 0 and 150. This can also be reset which sets the volume to 150. Anything over 150 is ignored and the volume is set to 150.")
                .setExample(
                        "discord command $volume [<number>]:",
                        "\ttrigger:",
                        "\t\tif arg-1 is not set:",
                        "\t\t\treply with \"%volume of event-bot%\"",
                        "\t\t\tstop",
                        "\t\tset the volume of event-bot to arg-1",
                        "\t\treply with \"My volume is now: `%volume of event-bot%`\""
                );
    }

    private Expression<Object> bot;
    private Expression<Guild> guild;

    @Override
    protected Number[] get(Event e) {
        Guild guild = this.guild.getSingle(e);
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (guild == null || bot == null) {
            return null;
        }

        GuildMusicManager musicManager = bot.getAudioManager(guild);
        return new Number[]{musicManager.player.getVolume()};
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "volume of " + bot.toString(e, debug) + " in " + guild.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        bot = (Expression<Object>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET) {
            return new Class[]{Number.class};
        }

        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (bot != null) {
            int volume = mode == Changer.ChangeMode.SET ? ((Number) delta[0]).intValue() : 100;
            bot.getAudioManager(this.guild.getSingle(e)).player.setVolume(volume);
        }
    }

}
