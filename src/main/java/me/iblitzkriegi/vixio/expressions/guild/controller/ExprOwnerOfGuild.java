package me.iblitzkriegi.vixio.expressions.guild.controller;

import ch.njol.skript.classes.Changer;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimplePropertyExpression;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.skript.EasyMultiple;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;


public class ExprOwnerOfGuild extends ChangeableSimplePropertyExpression<Guild, Member> implements EasyMultiple<Guild, Member> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprOwnerOfGuild.class, Member.class,
                "owner", "guilds")
                .setName("Owner of Guild")
                .setDesc("Get or set the owner of a guild.")
                .setExample("command /transfer <text>:" +
                        "\ttrigger:" +
                        "\t\tset {guild} to guild with id \"5611840019819\"" +
                        "\t\tset owner of {guild} to user with id \"16518918911891\" in {guild} as \"Jewel\"");
    }

    @Override
    public Member convert(Guild guild) {
        return guild.getOwner();
    }

    @Override
    public Class<? extends Member> getReturnType() {
        return Member.class;
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode, boolean vixioChanger) {
        if (mode == Changer.ChangeMode.SET) {
            return new Class[]{Member.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Bot bot, Changer.ChangeMode mode) {
        change(getExpr().getAll(e), guild -> {
            guild = Util.bindGuild(bot, guild);
            if (guild == null) {
                return;
            }

            try {
                guild.getController().transferOwnership((Member) delta[0]).queue();
            } catch (PermissionException x) {
                Vixio.getErrorHandler().needsPerm(bot, "set owner of guild", x.getPermission().getName());
            }
        });
    }

    @Override
    protected String getPropertyName() {
        return "owner";
    }
}
