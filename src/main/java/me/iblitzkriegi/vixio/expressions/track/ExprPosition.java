package me.iblitzkriegi.vixio.expressions.track;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.util.Timespan;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.events.base.TrackEvent;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class ExprPosition extends SimplePropertyExpression<AudioTrack, Timespan> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprPosition.class, Timespan.class,
                "position", "tracks")
                .setName("Position of Track")
                .setDesc("Get the position a track is at. This is the current play time the track is at.")
                .setExample("broadcast \"%position of track event-bot is playing%\"");
    }

    @Override
    protected String getPropertyName() {
        return "position";
    }

    @Override
    public Timespan convert(AudioTrack audioTrack) {
        return Timespan.fromTicks_i((audioTrack.getPosition() / 1000) * 20);
    }

    @Override
    public Class<? extends Timespan> getReturnType() {
        return Timespan.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if ((mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET)) {
            return new Class[]{Timespan.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        long position = mode == Changer.ChangeMode.SET ? (((Timespan) delta[0]).getTicks_i() / 20) * 1000 : 0;
        for (AudioTrack track : getExpr().getAll(e)) {
            track.setPosition(position);
            Util.async(() -> {
                for (ShardManager shardManager : Vixio.getInstance().botHashMap.keySet()) {
                    Bot bot = Util.botFrom(shardManager);
                    for (Guild guild : bot.getGuildMusicManagerMap().keySet()) {
                        if (bot.getAudioManager(guild).player.getPlayingTrack() == track) {
                            Util.sync(() -> {
                                Bukkit.getPluginManager().callEvent(new TrackEvent(TrackEvent.TrackState.SEEK, bot, guild, track));
                            });
                        }
                    }
                }
            });
        }
    }

}
