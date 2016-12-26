package me.iblitzkriegi.vixio.expressions.embeds;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.effembeds.EffCreateEmbed;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import org.bukkit.event.Event;

import java.util.List;

/**
 * Created by Blitz on 12/21/2016.
 */
@ExprAnnotation.Expression(returntype = List.class, type = ExpressionType.SIMPLE, syntax = "fields of embed %string%")
public class ExprFieldsOfEmbed extends SimpleExpression<List>{
    private Expression<String> vEmbed;
    @Override
    protected List[] get(Event e) {
        return new List[]{getEmbed(e)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends List> getReturnType() {
        return List.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vEmbed = (Expression<String>) expr[0];
        return true;
    }
    private List getEmbed(Event e){
        EmbedBuilder embedBuilder = EffCreateEmbed.embedBuilders.get(vEmbed.getSingle(e));
        MessageEmbed embed = embedBuilder.build();
        if(embed.getFields()!=null){
            return embed.getFields();
        }
        return null;
    }
}
