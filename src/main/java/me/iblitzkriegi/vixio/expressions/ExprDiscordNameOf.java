package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 7/25/2017.
 */
public class ExprDiscordNameOf extends SimplePropertyExpression<Object, String>{
    static {
        Vixio.getInstance().registerPropertyExpression(ExprDiscordNameOf.class, String.class,"name", "channel/guild/user/member/bot/voicechannel")
                .setName("Name of")
                .setDesc("Get the name of something/someone. There is a SET changer for channel,guild, and bot.")
                .setExample("name of event-user");
    }
    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        super.init(exprs, matchedPattern, isDelayed, parseResult);
        setExpr(exprs[0]);
        return true;
    }

    @Override
    protected String getPropertyName() {
        return "name of channel/guild/user/member/bots";
    }

    @Override
    public String convert(Object o) {
        if(o instanceof User){
            return ((User) o).getName();
        }else if(o instanceof Guild){
            return ((Guild) o).getName();
        }else if(o instanceof Channel){
            return ((Channel) o).getName();
        }else if(o instanceof Member){
            return ((Member) o).getUser().getName();
        }else if(o instanceof Bot){
            return ((Bot) o).getName();
        }else if(o instanceof VoiceChannel){
            return ((VoiceChannel) o).getName();
        }
        Skript.error("Could not parse provided argument, please refer to the syntax.");
        return null;
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if ((mode == Changer.ChangeMode.SET && getExpr().isSingle())) {
            return new Class[]{String.class};
        }
        return super.acceptChange(mode);
    }

    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) {
        Object object = getExpr().getSingle(e);
        if(object instanceof Member || object instanceof User) return;
        switch (mode) {
            case SET:
                if(object instanceof Bot){
                    Bot bot = (Bot) object;
                    bot.getSelfUser().getManager().setName((String) delta[0]).queue();
                }else if(object instanceof Channel) {
                    Channel channel = (Channel) object;
                    try {
                        channel.getManager().setName((String) delta[0]).queue();
                    } catch (PermissionException x) {
                        Skript.error("Bot does not have proper permission to change the name of the provided text channel.");
                    }

                }else if(object instanceof Guild){
                    Guild guild = (Guild) object;
                    try{
                        guild.getManager().setName((String) delta[0]).queue();
                    }catch (PermissionException x){
                        Skript.error("Bot does not have proper permission to change the name of the provided guild.");

                    }
                }
        }
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }
}
