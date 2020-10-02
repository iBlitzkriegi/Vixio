package me.iblitzkriegi.vixio.expressions.retrieve;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 7/26/2017.
 */
public class ExprGuildWithId extends SimpleExpression<Guild> {
    static {
        Vixio.getInstance().registerExpression(ExprGuildWithId.class, Guild.class, ExpressionType.SIMPLE,
                "(server|guild) with id %string%")
                .setName("Guild with ID")
                .setDesc("Get a Guild via it's ID")
                .setExample("broadcast name of guild with id \"16165192162168461\"");
    }

    private Expression<String> id;

    @Override
    protected Guild[] get(Event e) {
        String id = this.id.getSingle(e);
        if (id == null || id.isEmpty()) {
            return null;
        }

        for (ShardManager shardManager : Vixio.getInstance().botHashMap.keySet()) {
            Guild guild = shardManager.getGuildById(id);
            if (guild != null) {
                return new Guild[]{guild};
            }
        }

        return null;

    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Guild> getReturnType() {
        return Guild.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "guild with id " + id.toString(event, b);
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        id = (Expression<String>) expressions[0];
        return true;
    }
}
