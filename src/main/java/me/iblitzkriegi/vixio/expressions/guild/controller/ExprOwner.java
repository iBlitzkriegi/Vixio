package me.iblitzkriegi.vixio.expressions.guild.controller;

import ch.njol.skript.classes.Changer;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimplePropertyExpression;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.skript.EasyMultiple;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.exceptions.PermissionException;
import org.bukkit.event.Event;


public class ExprOwner extends ChangeableSimplePropertyExpression<Guild, Member> implements EasyMultiple<Guild, Member> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprOwner.class, Member.class,
                "discord owner", "guilds")
                .setName("Owner of Guild")
                .setDesc("Get or set the owner of a guild.")
                .setExample(
                        "discord command $transfer <member>:",
                        "\ttrigger:",
                        "\t\tset discord owner of event-guild to arg-1 with event-bot"
                );
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
    public Class<?>[] acceptChange(Changer.ChangeMode mode, boolean vixioChanger) {
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
                guild.transferOwnership((Member) delta[0]).queue();
            } catch (PermissionException x) {
                Vixio.getErrorHandler().needsPerm(bot, "set owner of guild", x.getPermission().getName());
            }
        });
    }

    @Override
    protected String getPropertyName() {
        return "discord owner";
    }
}
