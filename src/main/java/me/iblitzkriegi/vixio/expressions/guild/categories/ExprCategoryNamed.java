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
                "category named %string% [in %guild%]")
                .setName("Category named")
                .setDesc("Get a Category by its name in a Guild.")
                .setExample("command /channel:" +
                        "\ttrigger:" +
                        "\t\tcreate text channel:" +
                        "\t\t\tset name of the channel to \"Vixio!\"" +
                        "\t\t\tset {guild} to guild with id \"219967335266648065\"" +
                        "\t\t\tset nsfw state of the channel as \"Jewel\" to true" +
                        "\t\t\tset topic of the channel as \"Jewel\" to \"Jewel testing\"" +
                        "\t\t\tset parent of the channel to category named \"Test Category\" in {guild}" +
                        "\t\tcreate the channel in {guild} as \"Jewel\"");
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

        try {
            return new Category[]{categories.get(0)};
        } catch (IndexOutOfBoundsException x) {

        }

        return null;
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
