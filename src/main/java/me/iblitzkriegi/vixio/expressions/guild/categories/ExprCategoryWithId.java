package me.iblitzkriegi.vixio.expressions.guild.categories;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.bukkit.event.Event;

public class ExprCategoryWithId extends SimpleExpression<Category> {
    static {
        Vixio.getInstance().registerExpression(ExprCategoryWithId.class, Category.class, ExpressionType.SIMPLE,
                "category with id %string% [in %-guild%]")
                .setName("Category with ID")
                .setDesc("Get a Category via it's ID.")
                .setExample("reply with \"%category with id \"\"4516161651\"\"%\"");
    }

    private Expression<String> categoryId;

    @Override
    protected Category[] get(Event e) {
        String id = this.categoryId.getSingle(e);
        if (id == null || id.isEmpty()) {
            return null;
        }
        for (ShardManager shardManager : Vixio.getInstance().botHashMap.keySet()) {
            Category category = shardManager.getCategoryById(id);
            if (category != null) {
                return new Category[]{category};
            }
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
        return "category with id " + categoryId.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        categoryId = (Expression<String>) exprs[0];
        return true;
    }
}
