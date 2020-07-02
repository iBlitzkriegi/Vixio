package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.skript.EasyMultiple;
import me.iblitzkriegi.vixio.util.wrapper.Emote;
import org.bukkit.event.Event;

public class CondEmoteAnimated extends Condition implements EasyMultiple<Emote, Void> {
    static {
        Vixio.getInstance().registerCondition(CondEmoteAnimated.class,
                "%emotes% (is|are) animated", "%emotes% (is|are)(n't| not) animated")
                .setName("Emote is Animated")
                .setDesc("Check if a emote is animated.")
                .setExample(
                        "discord command $emotes:",
                        "\ttrigger:",
                        "\t\tloop emotes of event-guild:",
                        "\t\t\tif loop-value is animated:",
                        "\t\t\t\tadd loop-value to {_animated::*}",
                        "\t\t\telse:",
                        "\t\t\t\tadd loop-value to {_non::*}",
                        "\t\tcreate embed:",
                        "\t\t\tset the author info of the embed to author named \"Emotes %reaction \"\":smile:\"\"%\" with no url and no icon",
                        "\t\t\tset the description of the embed to \"%name of event-guild% has %size of {_non::*}% regular emotes and %size of {_animated::*}% animated emotes!!\"",
                        "\t\t\tset the colour of the embed to Purple",
                        "\t\t\tadd field named \"Local emotes (1/1)\" with value \"%{_non::*}%\" to embed",
                        "\t\t\tadd field named \"Animated emotes (1/1)\" with value \"%{_animated::*}%\" to embed",
                        "\t\tsend the last created embed to event-channel with event-bot"
                );
    }

    private Expression<Emote> emotes;

    @Override
    public boolean check(Event e) {
        return check(emotes.getAll(e), emote -> emote.isAnimated(), isNegated());
    }

    @Override
    public String toString(Event e, boolean debug) {
        return emotes.toString(e, debug) + " is " + (isNegated() ? " not " : "") + "animated";
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        emotes = (Expression<Emote>) exprs[0];
        setNegated(matchedPattern == 1);
        return true;
    }
}
