package me.iblitzkriegi.vixio.effects.audio;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.event.Event;

public class EffLeaveVoice extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffLeaveVoice.class, "make %bots/strings% (leave|disconnect from) (voice|vc) [in %guild%]")
                .setName("Leave Voice Channel")
                .setDesc("Make a bot leave it's voice channel in a guild.")
                .setExample(
                        "discord command leave:",
                        "\tprefixes: ##",
                        "\ttrigger:",
                        "\t\tif voice channel of event-bot in event-guild is set:",
                        "\t\t\tmake event-bot leave vc",
                        "\t\t\treply with \"I've left my voice channel\"",
                        "\t\t\tstop",
                        "\t\treply with \"I'm not currently in any voice channels!\"");
    }

    private Expression<Guild> guild;
    private Expression<Object> bot;

    @Override
    protected void execute(Event e) {
        Guild guild = this.guild.getSingle(e);
        if (guild == null) {
            return;
        }

        for (Object object : bot.getAll(e)) {
            Bot bot = Util.botFrom(object);
            Guild bindedGuild = Util.bindGuild(bot, guild);
            if (bindedGuild != null) {
                new Thread(() -> bindedGuild.getAudioManager().closeAudioConnection()).start();
            }

        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "leave voice channel in " + guild.toString(e, debug) + " with " + bot.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        bot = (Expression<Object>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return true;
    }
}
