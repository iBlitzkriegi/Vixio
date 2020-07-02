package me.iblitzkriegi.vixio.effects.message.builder;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.expressions.message.builder.ExprMessageBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import org.bukkit.event.Event;

public class EffAppendToBuilder extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffAppendToBuilder.class, "append [(1¦line)] %strings% [to %-messagebuilder%]")
                .setName("Append String to Message Buillder")
                .setDesc("Add text to a Message Builder. If you include the word 'line' then it will append a new line for you after your text.")
                .setUserFacing("append [line] %strings% to %messagebuilder%")
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
    private int mark;

    @Override
    protected void execute(Event e) {
        MessageBuilder builder = this.builder == null ? ExprMessageBuilder.lastMessageBuilder : this.builder.getSingle(e);

        String[] toAppend = this.toAppend.getAll(e);
        if (toAppend == null || builder == null) {
            return;
        }
        for (String s : toAppend) {
            if (mark == 0) {
                builder.append(s);
            } else {
                builder.append(s + "\n");
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "append " + (mark == 0 ? "" : "line ") + toAppend.toString(e, debug) + " to " + builder.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        toAppend = (Expression<String>) exprs[0];
        builder = (Expression<MessageBuilder>) exprs[1];
        mark = parseResult.mark;
        return true;
    }
}
