package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.classes.Changer;
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

public class ExprEmbedsOfMessage extends PropertyExpression<Message, MessageEmbed> {

    static {
        Vixio.getInstance().registerExpression(ExprEmbedsOfMessage.class, MessageEmbed.class, ExpressionType.PROPERTY,
                "[the] embed[s] of %messages%", "%messages%'[s] embed[s]"
        );
    }

    private Expression<EmbedBuilder> embeds;

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        setExpr((Expression<Message>) exprs[0]);
        return true;
    }

    @Override
    protected MessageEmbed[] get(final Event e, final Message[] messages) {
        ArrayList<MessageEmbed> embeds = new ArrayList<MessageEmbed>();
        for (Message message : messages) {
            for (MessageEmbed embed : message.getEmbeds()) {
                embeds.add(embed);
            }
        }
        return embeds.toArray(new MessageEmbed[embeds.size()]);
    }

    @Override
    public Class<? extends MessageEmbed> getReturnType() {
        return MessageEmbed.class;
    }

    @Override
    public String toString(final Event e, final boolean debug) {
        return "the embeds of " + getExpr().toString(e, debug);
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        return new Class[]{
                MessageEmbed.class
        };
    }

}
