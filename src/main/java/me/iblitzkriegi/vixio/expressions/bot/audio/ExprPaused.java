package me.iblitzkriegi.vixio.expressions.bot.audio;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.event.Event;

public class ExprPaused extends SimpleExpression<Boolean> {
    static {
        Vixio.getInstance().registerExpression(ExprPaused.class, Boolean.class, ExpressionType.SIMPLE,
                "%bot/string% paused state [in %guild%]")
                .setName("Bot paused state")
                .setDesc("Check if a bot is paused or not. Can be set to true/false.")
                .setExample("set event-bot paused state to true");
    }

    private Expression<Object> bot;
    private Expression<Guild> guild;

    @Override
    protected Boolean[] get(Event e) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Guild guild = this.guild.getSingle(e);
        if (guild == null || bot == null) {
            return null;
        }

        return new Boolean[]{bot.getAudioManager(guild).player.isPaused()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return bot.toString(e, debug) + " paused state in " + guild.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        bot = (Expression<Object>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
         if (mode == Changer.ChangeMode.SET) {
             return new Class[]{Boolean.class};
         }

         return super.acceptChange(mode);
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (bot != null) {
            bot.getAudioManager(this.guild.getSingle(e)).player.setPaused((Boolean) delta[0]);
        }
    }
}
