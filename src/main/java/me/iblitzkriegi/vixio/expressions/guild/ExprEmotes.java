package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.Emote;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.event.Event;

import java.util.ArrayList;

public class ExprEmotes extends SimpleExpression<Emote> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprEmotes.class, Emote.class,
                "emote", "guild")
                .setName("Emotes of guild")
                .setDesc("Get all of the emotes a guild has added.")
                .setExample("set {var::*} to the emotes of event-guild");
    }

    private Expression<Guild> guild;

    @Override
    protected Emote[] get(Event e) {
        Guild guild = this.guild.getSingle(e);
        ArrayList<Emote> emotes = new ArrayList<>();
        for (net.dv8tion.jda.core.entities.Emote emote : guild.getEmotes()) {
            emotes.add(new Emote(emote));
        }

        return emotes.toArray(new Emote[emotes.size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Emote> getReturnType() {
        return Emote.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "emotes of " + guild.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        guild = (Expression<Guild>) exprs[0];
        return true;
    }
}
