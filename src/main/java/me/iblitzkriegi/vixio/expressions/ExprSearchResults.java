package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.effects.EffSearch;
import org.bukkit.event.Event;

public class ExprSearchResults extends SimpleExpression<AudioTrack> {

    static {
        Vixio.getInstance().registerExpression(ExprSearchResults.class, AudioTrack.class, ExpressionType.SIMPLE, "[the] [last] search results")
                .setName("Search Results")
                .setDesc("Represents the search results from the last usage of the search effect.")
                .setExample("on join:",
                        "\tsearch youtube for \"%player%\" and store the results in {_results::*}",
                        "\tif search results are set:",
                        "\t\tmessage \"Did you know there are %size of search results% videos about you on YouTube?\" to player"
                );
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public AudioTrack[] get(final Event e) {
        return EffSearch.lastResults;
    }

    @Override
    public Class<? extends AudioTrack> getReturnType() {
        return AudioTrack.class;
    }

    @Override
    public String toString(final Event e, final boolean debug) {
        return "the search results";
    }

    @Override
    public boolean isSingle() {
        return false;
    }

}
