package me.iblitzkriegi.vixio.effects.guild;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.event.Event;

public class EffLeaveGuild extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffLeaveGuild.class, "make %bot/string% leave %guild%")
                .setName("Make Bot leave Guild")
                .setDesc("Force a bot to leave a guild.")
                .setExample("make event-bot leave event-guild");
    }

    private Expression<Object> bot;
    private Expression<Guild> guild;

    @Override
    protected void execute(Event e) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Guild guild = this.guild.getSingle(e);
        if (bot == null || guild == null) {
            return;
        }
        Guild boundGuild = Util.bindGuild(bot, guild);
        if (boundGuild != null) {
            boundGuild.leave().queue();
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "make " + bot.toString(e, debug) + " leave " + guild.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        bot = (Expression<Object>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return true;
    }
}
