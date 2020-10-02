package me.iblitzkriegi.vixio.expressions.bot;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class ExprGametype extends SimplePropertyExpression<Object, Activity.ActivityType> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprGametype.class, Activity.ActivityType.class, "game type", "bots/users/strings")
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
    public Activity.ActivityType convert(Object object) {
        if (object instanceof Bot || object instanceof String) {
            Bot bot = Util.botFrom(object);
            if (bot != null) {
                return bot.getJDA().getShards().get(0).getPresence().getActivity().getType();
            }
        } else if (object instanceof User) {
            Member member = Util.getMemberFromUser(object);
            List<Activity> thing = member.getActivities();
            Activity t = thing.get(0);

            if (member != null) {
                return member.getActivities().isEmpty() ? null : member.getActivities().get(0).getType();
            }
            return null;
        }
        return null;
    }

    @Override
    public Class<? extends Activity.ActivityType> getReturnType() {
        return Activity.ActivityType.class;
    }

}
