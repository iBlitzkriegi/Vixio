package me.iblitzkriegi.vixio.expressions.user;

import ch.njol.skript.classes.Changer;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimplePropertyExpression;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.event.Event;

public class ExprGame extends ChangeableSimplePropertyExpression<Object, String> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprGame.class, String.class,
                "game", "members/users/bots/strings")
                .setName("Game of User")
                .setDesc("Get the game of a user")
                .setExample("game of user with id \"4165651561561\"");
    }

    @Override
    protected String getPropertyName() {
        return "game";
    }

    @Override
    public String convert(Object object) {
        if (object instanceof Member) {
            Member member = (Member) object;
            return member.getGame() == null ? null : member.getGame().getName();
        } else if (object instanceof User) {
            Member member = Util.getMemberFromUser(object);
            if (member == null) {
                return null;
            }

            return member.getGame() == null ? null : member.getGame().getName();
        } else {
            Bot bot = Util.botFrom(object);
            if (bot == null) {
                return null;
            }
            if (bot.getJDA().getPresence().getGame() == null) {
                return null;
            }
            return Util.botFrom(object).getJDA().getPresence().getGame().getName();
        }
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode, boolean vixioChanger) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET) {
            return new Class[]{String.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Bot bot, Changer.ChangeMode mode) {
        for (Object object : getExpr().getAll(e)) {
            if (Util.botFrom(object) != null) {
                bot.getJDA().getPresence().setGame(Game.of(Game.GameType.DEFAULT, (String) delta[0]));
            }
        }
    }
}
