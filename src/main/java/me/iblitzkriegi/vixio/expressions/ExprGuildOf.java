package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.Emote;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;

public class ExprGuildOf extends SimplePropertyExpression<Object, Guild> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprGuildOf.class, Guild.class, "guild", "channel/voicechannel/message/emote/category/role/member")
                .setName("Guild of")
                .setDesc("Get the guild of various types.")
                .setExample("set {_guild} to guild of event-message");
    }
    @Override
    protected String getPropertyName() {
        return "guild";
    }

    @Override
    public Guild convert(Object o) {
        if (o instanceof Channel) {
            return ((Channel) o).getGuild();
        }else if (o instanceof Message) {
            return ((Message) o).getGuild();
        } else if (o instanceof Emote) {
            return ((Emote) o).getGuild();
        } else if (o instanceof Role) {
            return ((Role) o).getGuild();
        } else if (o instanceof Member) {
            return ((Member) o).getGuild();
        }
        return null;
    }

    @Override
    public Class<? extends Guild> getReturnType() {
        return Guild.class;
    }
}
