package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimplePropertyExpression;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.Region;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.exceptions.PermissionException;
import org.bukkit.event.Event;

public class ExprRegionOfGuild extends ChangeableSimplePropertyExpression<Guild, Region> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprRegionOfGuild.class, Region.class,
                "region", "guilds")
                .setName("Region of Guild")
                .setDesc("Get the current region of a guild.")
                .setExample("broadcast \"%region of event-guild%\"");
    }

    @Override
    protected String getPropertyName() {
        return "region of";
    }

    @Override
    public Region convert(Guild guild) {
        return guild.getRegion();
    }

    @Override
    public Class<? extends Region> getReturnType() {
        return Region.class;
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        setExpr((Expression<? extends Guild>) exprs[0]);
        return true;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode, boolean vixioChanger) {
        if (mode == Changer.ChangeMode.SET) {
            return new Class[]{Region.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Bot bot, Changer.ChangeMode mode) {
        for (Guild guild : getExpr().getAll(e)) {
            Guild bindedGuild = Util.bindGuild(bot, guild);
            if (bindedGuild != null) {
                try {
                    bindedGuild.getManager().setRegion((Region) delta[0]).queue();
                } catch (PermissionException x) {
                    Vixio.getErrorHandler().needsPerm(bot, "set region", x.getPermission().getName());
                }
            }
        }
    }
}
