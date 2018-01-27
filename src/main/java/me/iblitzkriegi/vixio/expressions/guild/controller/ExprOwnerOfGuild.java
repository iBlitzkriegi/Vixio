package me.iblitzkriegi.vixio.expressions.guild.controller;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.Objects;


public class ExprOwnerOfGuild extends SimpleExpression<Member> {
    static {
        Vixio.getInstance().registerExpression(ExprOwnerOfGuild.class, Member.class, ExpressionType.SIMPLE,
                "owner of %guilds% [(with|as) %-bot/string%]")
                .setName("Owner of Guild")
                .setDesc("Get the current Owner of a Guild. Must include bot to set the owner! Changers: SET, RESET")
                .setExample("command /transfer <text>:" +
                        "\ttrigger:" +
                        "\t\tset {guild} to guild with id \"5611840019819\"" +
                        "\t\tset owner of {guild} as \"Jewel\" to member with id \"16518918911891\" in {guild}");
    }

    private Expression<Object> bot;
    private Expression<Guild> guilds;

    @Override
    protected Member[] get(Event e) {
        Guild[] guilds = this.guilds.getAll(e);
        if (guilds == null) {
            return null;
        }

        return Arrays.stream(guilds)
                .filter(Objects::nonNull)
                .map(Guild::getOwner)
                .toArray(Member[]::new);
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Member> getReturnType() {
        return Member.class;
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET)
            return new Class[]{Member.class};
        return null;
    }

    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) throws UnsupportedOperationException {
        if (bot == null) {
            Vixio.getErrorHandler().noBotProvided("set owner of guild");
            return;
        }

        Member member = (Member) delta[0];
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Guild[] guilds = this.guilds.getAll(e);
        if (bot == null || guilds == null) {
            return;
        }

        for (Guild guild : guilds) {
            try {
                Guild bindedGuild = Util.bindGuild(bot, guild);
                bindedGuild.getController().transferOwnership(member).queue();
            } catch (PermissionException x) {
                Vixio.getErrorHandler().needsPerm(bot, "set owner of guild", x.getPermission().getName());
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "owner of " + guilds.toString() + (bot == null ? "" : " as " + bot.toString(e, debug));
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        guilds = (Expression<Guild>) exprs[0];
        bot = (Expression<Object>) exprs[1];
        return true;
    }

}
