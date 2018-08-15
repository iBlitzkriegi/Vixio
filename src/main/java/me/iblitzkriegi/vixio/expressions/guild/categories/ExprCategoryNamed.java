package me.iblitzkriegi.vixio.expressions.guild.categories;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.event.Event;

import java.util.List;

public class ExprCategoryNamed extends SimpleExpression<Category> {
    static {
        Vixio.getInstance().registerExpression(ExprCategoryNamed.class, Category.class, ExpressionType.SIMPLE,
                "[the] category (with [the] name|named) %string% [in %guild%]")
                .setName("Category named")
                .setDesc("Get a Category by its name in a Guild.")
                .setExample(
                        "discord command $create <text>:",
                        "\ttrigger:",
                        "\t\tcreate text channel:",
                        "\t\t\tset the name of the channel to arg-1 ",
                        "\t\t\tset the parent of the channel to category named \"xd\"",
                        "\t\tcreate the last made channel in event-guild and store it in {_chnl}",
                        "\t\treply with \"I've successfully created a channel named `%arg-1%`, ID: %id of {_chnl}%\""
                );
    }

    private Expression<Guild> guild;
    private Expression<String> category;

    @Override
    protected Category[] get(Event e) {
        Guild guild = this.guild.getSingle(e);
        String category = this.category.getSingle(e);
        if (guild == null || category == null) {
            return null;
        }

        List<Category> categories = guild.getCategoriesByName(category, false);
        if (categories.size() > 1) {
            Vixio.getErrorHandler().warn("Vixio attempted to get a Category with the name " + category + " but more than one category exists with that name.");
            return null;
        }

        return categories.isEmpty() ? null : new Category[]{categories.get(0)};

    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Category> getReturnType() {
        return Category.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "category named " + category.toString(e, debug) + " in " + guild.toString(e, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        category = (Expression<String>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return true;
    }
}
