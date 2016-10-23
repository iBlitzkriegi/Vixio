package me.iblitzkriegi.vixio.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.events.EvntGuildMsgReceived;
import net.dv8tion.jda.entities.TextChannel;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.api.API.getAPI;

/**
 * Created by Blitz on 10/18/2016.
 */
public class EffReplyToMessage extends Effect {
    private Expression<String> string;
    @Override
    protected void execute(Event e) {
        String txtChnlId = ((EvntGuildMsgReceived)e).getEvtChannel();
        TextChannel chnl = getAPI().getJDA().getTextChannelById(txtChnlId);
        chnl.sendMessage(String.valueOf(string.getSingle(e)));
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        string = (Expression<String>) expr[0];
        return true;
    }
}
