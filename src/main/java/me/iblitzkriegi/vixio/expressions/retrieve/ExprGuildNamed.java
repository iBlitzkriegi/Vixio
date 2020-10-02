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

import java.util.ArrayList;

public class ExprGuildNamed extends SimpleExpression<Guild> {
    static {
        Vixio.getInstance().registerExpression(ExprGuildNamed.class, Guild.class, ExpressionType.SIMPLE,
                "guild[s] named %string%")
                .setName("Guild  Named")
                .setDesc("Get all the guilds with a certain name that vixio can find.")
                .setExample("set {_var::*} to guilds named \"Vixio\"");
    }

    private Expression<String> name;

    @Override
    protected Guild[] get(Event e) {
        String name = this.name.getSingle(e);
        if (name == null) {
            return null;
        }
        ArrayList<Guild> guilds = new ArrayList<>();
        for (ShardManager shardManager : Vixio.getInstance().botHashMap.keySet()) {
            guilds.addAll(shardManager.getGuildsByName(name, false));
        }
        return guilds.toArray(new Guild[guilds.size()]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Guild> getReturnType() {
        return Guild.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "guilds named " + name.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        name = (Expression<String>) exprs[0];
        return true;
    }
}
