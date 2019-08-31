package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.Emote;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.event.Event;

import java.util.ArrayList;

public class ExprEmotes extends SimpleExpression<Emote> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprEmotes.class, Emote.class,
                "[<global>] emote", "guild")
                .setName("Emotes of guild")
                .setDesc("Get all of the emotes a guild has added.")
                .setExample(
                        "discord command $emotes:",
                        "\texecutable in: guild",
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

    private Expression<Guild> guild;
    private boolean global;

    @Override
    protected Emote[] get(Event e) {
        Guild guild = this.guild.getSingle(e);
        ArrayList<Emote> emotes = new ArrayList<>();
        if (global) {
            for (net.dv8tion.jda.api.entities.Emote emote : guild.getEmotes()) {
                emotes.add(new Emote(emote));
            }
        } else {
            for (net.dv8tion.jda.api.entities.Emote emote : guild.getEmotes()) {
                if (emote.isManaged()) {
                    emotes.add(new Emote(emote));
                }
            }
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
        global = parseResult.regexes.size() == 0;
        return true;
    }
}

