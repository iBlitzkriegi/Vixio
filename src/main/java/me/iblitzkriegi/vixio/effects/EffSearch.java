package me.iblitzkriegi.vixio.effects;

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

import java.util.Locale;

public class EffSearch extends AsyncEffect {

    public static AudioTrack[] lastResults;

    static {
        Vixio.getInstance().registerEffect(EffSearch.class, "search <youtube|soundcloud> for %strings% [and store the results in %-objects%]")
                .setName("Search Audio")
                .setDesc("Lets you search various music sites for a query. You can either access the results via the search results expression, or store them in a variable.")
                .setExample("on join:",
                        "\tsearch youtube for \"%player%\" and store the results in {_results::*}",
                        "\tif {_results::*} is set:",
                        "\t\tmessage \"Did you know there are %size of {_results::*}% videos about you on YouTube?\" to player"
                );
    }

    private SearchableSite site;
    private Expression<String> queries;
    private boolean local;
    private VariableString variable;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        queries = (Expression<String>) exprs[0];
        try {
            site = SearchableSite.valueOf(parseResult.regexes.get(0).group().toUpperCase(Locale.ENGLISH));
        } catch (IllegalArgumentException e) {
        }

        if (exprs[1] != null) {
            Expression<?> expr = exprs[1];
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
        AudioTrack[] results = Util.search(site, queries.getAll(e));
        lastResults = results;
        if (variable != null) {
            SkriptUtil.setList(variable.toString(e), e, local, results);
        }

    }

    @Override
    public String toString(Event event, boolean debug) {
        return "search " + site.name().toLowerCase(Locale.ENGLISH) + " for " + queries.toString(event, debug) + (variable == null ? "" : " and store the results in " + variable.toString(event, debug));
    }

}
