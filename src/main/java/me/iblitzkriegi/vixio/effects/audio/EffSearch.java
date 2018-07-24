package me.iblitzkriegi.vixio.effects.audio;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.enums.SearchableSite;
import me.iblitzkriegi.vixio.util.skript.AsyncEffect;
import me.iblitzkriegi.vixio.util.skript.SkriptUtil;
import org.bukkit.event.Event;

public class EffSearch extends AsyncEffect {

    public static AudioTrack[] lastResults;

    static {
        Vixio.getInstance().registerEffect(EffSearch.class, "search %searchablesite% for %strings% [and store the results in %-objects%]")
                .setUserFacing("search (youtube|soundcloud) for %strings% [and store the results in %listvariable%]")
                .setName("Search Audio")
                .setDesc("Lets you search various music sites for a query. You can either access the results via the search results expression, or store them in a variable.")
                .setExample(
                        "on join:",
                        "\tsearch youtube for \"%player%\" and store the results in {_results::*}",
                        "\tif {_results::*} is set:",
                        "\t\tmessage \"Did you know there are %size of {_results::*}% videos about you on YouTube?\" to player"
                );
    }

    private Expression<SearchableSite> site;
    private Expression<String> queries;
    private boolean local;
    private VariableString variable;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        site = (Expression<SearchableSite>) exprs[0];
        queries = (Expression<String>) exprs[1];
        if (exprs[2] != null) {
            Expression<?> expr = exprs[2];
            if (expr instanceof Variable) {
                Variable<?> varExpr = (Variable<?>) expr;
                if (varExpr.isList()) {
                    variable = SkriptUtil.getVariableName(varExpr);
                    local = varExpr.isLocal();
                    return true;
                }
            }
            Skript.error(expr + " is not a list variable");
            return false;
        }
        return true;
    }

    @Override
    public void execute(Event e) {
        EffSearch.lastResults = null; // null out previous results
        SearchableSite site = this.site.getSingle(e);
        if (site != null) {
            AudioTrack[] results = Util.search(site, queries.getAll(e));
            lastResults = results;
            if (variable != null) {
                SkriptUtil.setList(variable.toString(e), e, local, (Object[]) results);
            }
        }
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "search " + site.toString(event, debug) + " for " + queries.toString(event, debug) + (variable == null ? "" : " and store the results in " + variable.toString(event, debug));
    }

}
