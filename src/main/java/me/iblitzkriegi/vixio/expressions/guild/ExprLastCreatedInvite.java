package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.effects.guild.EffCreateInvite;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Invite;
import org.bukkit.event.Event;

public class ExprLastCreatedInvite extends SimpleExpression<String> {
    static {
        Vixio.getInstance().registerExpression(ExprLastCreatedInvite.class, String.class, ExpressionType.SIMPLE,
                "[the] last created invite [for %-channel%]")
                .setName("Last Created Invitation")
                .setDesc("Get the last invite created by vixio, can specify the specific channel if you want.")
                .setExample(
                        "discord command invite:",
                        "\ttrigger:",
                        "\t\tcreate an invite to event-channel",
                        "\t\treply with \"%the last created invite%\""
                );
    }

    private Expression<GuildChannel> channel;

    @Override
    protected String[] get(Event e) {
        if (channel != null) {
            Invite invite = EffCreateInvite.guildInviteHashMap.get(channel.getSingle(e));
            return invite == null ? null : new String[]{invite.getUrl()};
        }

        return EffCreateInvite.lastCreatedInvite == null ? null : new String[]{EffCreateInvite.lastCreatedInvite.getUrl()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "last created invite" + (channel == null ? "" : " for " + channel.toString(e, debug));
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        channel = (Expression<GuildChannel>) exprs[0];
        return true;
    }
}
