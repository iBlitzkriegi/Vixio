package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.*;
import org.bukkit.event.Event;

import java.util.List;

public class ExprVoiceChannels extends SimpleExpression<VoiceChannel> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprVoiceChannels.class, VoiceChannel.class,
                "voice(-| )channel", "guild/category/member")
                .setName("Voice Channels of")
                .setDesc("Get all of the voice channels in a guild or category, or the voice channel a member is in.")
                .setExample(
                        "discord command $voice:",
                        "\ttrigger:",
                        "\t\treply with \"Here are the voice channels you can join! `%voice channels of event-guild%`\""
                );
    }

    private Expression<Object> object;
    private boolean single;

    @Override
    protected VoiceChannel[] get(Event e) {
        Object object = this.object.getSingle(e);
        if (object instanceof Guild) {
            List<VoiceChannel> channels = ((Guild) object).getVoiceChannels();
            return ((Guild) object).getVoiceChannels().toArray(new VoiceChannel[channels.size()]);
        } else if (object instanceof Category) {
            List<VoiceChannel> channels = ((Category) object).getVoiceChannels();
            return ((Guild) object).getVoiceChannels().toArray(new VoiceChannel[channels.size()]);
        } else if (object instanceof Member) {
            GuildVoiceState state = ((Member)object).getVoiceState();
           return state.inVoiceChannel() ? new VoiceChannel[]{state.getChannel()} : null;
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return single;
    }

    @Override
    public Class<? extends VoiceChannel> getReturnType() {
        return VoiceChannel.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "voice channels of " + object.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        object = (Expression<Object>) exprs[0];
        single = exprs[0].getReturnType().isAssignableFrom(Member.class) && exprs[0].isSingle();
        return true;
    }
}
