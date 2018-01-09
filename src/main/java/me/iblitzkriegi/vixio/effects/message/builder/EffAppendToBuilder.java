package me.iblitzkriegi.vixio.effects.message.builder;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.MessageBuilder;
import org.bukkit.event.Event;

public class EffAppendToBuilder extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffAppendToBuilder.class, "append %strings% to %messagebuilder%")
                .setName("Append String to Message Buillder")
                .setDesc("Add text to a Message Builder. ")
                .setExample(
                        "command /build:",
                        "\ttrigger:",
                        "\t\tset {e} to a new message builder",
                        "\t\tset text of {e} to \"Hello\"",
                        "\t\tappend \" World!\" to {e}",
                        "broadcast \"The final product is: %text of {e}%\""
                );
    }
    private Expression<MessageBuilder> builder;
    private Expression<String> toAppend;
    @Override
    protected void execute(Event e) {
        MessageBuilder builder = this.builder.getSingle(e);
        if (builder == null){
            Skript.error("You must input a %messagebuilder% for this effect! Please refer to the syntax.");
            return;
        }
        String[] toAppend = this.toAppend.getAll(e);
        if (toAppend == null){
            Skript.error("You must include text to be appended on to the Message Builder...!");
        }
        for(String s : toAppend){
            builder.append(s);
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "append " + toAppend.toString(e, debug) + " to " + builder.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        toAppend = (Expression<String>) exprs[0];
        builder = (Expression<MessageBuilder>) exprs[1];
        return true;
    }
}
