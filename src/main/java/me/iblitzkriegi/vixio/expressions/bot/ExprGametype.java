package me.iblitzkriegi.vixio.expressions.bot;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;

public class ExprGametype extends SimplePropertyExpression<Object, Game.GameType> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprGametype.class, Game.GameType.class, "game type", "bots/users/strings")
                .setName("Game type")
                .setDesc("Get the type of game a user, a bot, or a bot specified by name is playing.")
                .setExample(
                        "discord command $streaming <user>:",
                        "\ttrigger:",
                        "\t\tset {_type} to the game type of event-user",
                        "\t\tif {_type} is streaming:",
                        "\t\t\treply with \"%arg-1% is live!\"",
                        "\t\t\tstop",
                        "\t\treply with \"%arg-1% is not streaming currently\""
                );
    }

    @Override
    protected String getPropertyName() {
        return "game type";
    }

    @Override
    public Game.GameType convert(Object object) {
        if (object instanceof Bot || object instanceof String) {
            Bot bot = Util.botFrom(object);
            if (bot != null) {
                //TODO USE GETJDA HERE!
                return bot.getShardMananger().getApplicationInfo().getJDA().getPresence().getGame().getType();
            }
        } else if (object instanceof User) {
            Member member = Util.getMemberFromUser(object);
            if (member != null) {
                return member.getGame() == null ? null : member.getGame().getType();
            }
            return null;
        }
        return null;
    }

    @Override
    public Class<? extends Game.GameType> getReturnType() {
        return Game.GameType.class;
    }

}
