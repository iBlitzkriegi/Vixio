package me.iblitzkriegi.vixio.effects.effGuildManagement;

import ch.njol.skript.Skript;
import ch.njol.skript.effects.EffLog;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import me.iblitzkriegi.vixio.util.AudioPlayerSendHandler;
import me.iblitzkriegi.vixio.util.TrackScheduler;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.audio.hooks.ConnectionListener;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.managers.AudioManager;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;

/**
 * Created by Blitz on 12/17/2016.
 */
@EffectAnnotation.Effect(syntax = "[discord] join voice channel %string% with %string%")
public class EffJoinVoiceChannel extends Effect {
    private Expression<String> vID;
    private Expression<String> vBot;
    @Override
    protected void execute(Event e) {
        JDA jda = bots.get(vBot.getSingle(e));
        TrackScheduler trackScheduler = EffLogin.trackSchedulers.get(vBot.getSingle(e));
        AudioManager manager = jda.getVoiceChannelById(vID.getSingle(e)).getGuild().getAudioManager();
        manager.openAudioConnection(jda.getVoiceChannelById(vID.getSingle(e)));
        Guild g = jda.getVoiceChannelById(vID.getSingle(e)).getGuild();
        g.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(trackScheduler.getPlayer()));

    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vID = (Expression<String>) expr[0];
        vBot = (Expression<String>) expr[1];
        return true;
    }
}
