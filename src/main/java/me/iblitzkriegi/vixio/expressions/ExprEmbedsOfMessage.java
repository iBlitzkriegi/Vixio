package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import org.bukkit.event.Event;

import java.util.ArrayList;

public class ExprEmbedsOfMessage extends PropertyExpression<Message, EmbedBuilder> {

    static {
        Vixio.getInstance().registerExpression(ExprEmbedsOfMessage.class, EmbedBuilder.class, ExpressionType.PROPERTY, "[the] embed of %messages%", "%messages%'[s] embed[s]")
                .setName("Embed of Message")
                .setDesc("Get the Embed of a Message")
                .setExample("broadcast \"%embed of event-message%\"");

    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        setExpr((Expression<Message>) exprs[0]);
        return true;
    }

    @Override
    protected EmbedBuilder[] get(final Event e, final Message[] messages) {
        ArrayList<EmbedBuilder> embeds = new ArrayList<>();
        for (Message message : messages) {
            for (MessageEmbed embed : message.getEmbeds()) {
                embeds.add(new EmbedBuilder(embed));
            }
        }
        return embeds.toArray(new EmbedBuilder[embeds.size()]);
    }

    @Override
    public Class<? extends EmbedBuilder> getReturnType() {
        return EmbedBuilder.class;
    }

    @Override
    public String toString(final Event e, final boolean debug) {
        return "the embed of " + getExpr().toString(e, debug);
    }

}
