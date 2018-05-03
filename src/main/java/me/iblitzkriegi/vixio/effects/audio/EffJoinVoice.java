package me.iblitzkriegi.vixio.effects.audio;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

public class EffJoinVoice extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffJoinVoice.class, "join %voicechannel/channel% [with %bot/string%]")
                .setName("Join voice channel")
                .setDesc("Join a voice channel with a bot")
                .setUserFacing("join %voicechannel% [with %bot/string%]")
                .setExample("join event-channel");
    }

    private Expression<Channel> channel;
    private Expression<Object> bot;

    @Override
    protected void execute(Event e) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Channel channel = this.channel.getSingle(e);
        if (bot == null || channel == null || (!(channel instanceof VoiceChannel))) {
            return;
        }

        VoiceChannel voiceChannel = Util.bindVoiceChannel(bot, (VoiceChannel) channel);
        if (voiceChannel == null) {
            return;
        }

        try {
            voiceChannel.getGuild().getAudioManager().openAudioConnection(voiceChannel);
        } catch (PermissionException x) {
            Vixio.getErrorHandler().needsPerm(bot, "join voice channel", x.getPermission().getName());
        }


    }

    @Override
    public String toString(Event e, boolean debug) {
        return "join " + channel.toString(e, debug) + " with " + bot.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        channel = (Expression<Channel>) exprs[0];
        bot = (Expression<Object>) exprs[1];
        return true;
    }
}
