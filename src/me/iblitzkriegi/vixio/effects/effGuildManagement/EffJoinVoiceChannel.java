package me.iblitzkriegi.vixio.effects.effGuildManagement;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.effects.EffLogin;
import me.iblitzkriegi.vixio.registration.EffectAnnotation;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.VoiceChannel;
import net.dv8tion.jda.managers.AudioManager;
import net.dv8tion.jda.player.Bot;
import net.dv8tion.jda.player.MusicPlayer;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;
import static net.dv8tion.jda.player.Bot.DEFAULT_VOLUME;

/**
 * Created by Blitz on 11/7/2016.
 */
@EffectAnnotation.Effect(syntax = "[discord] join voice channel %string% with %string%")
public class EffJoinVoiceChannel extends Effect {
    Expression<String> vID;
    Expression<String> vBot;
    Expression<String> vGuild;
    @Override
    protected void execute(Event e) {
        try {
            if (bots.get(vBot.getSingle(e)) != null) {
                MusicPlayer player = EffLogin.musicPlayerHashMap.get(vBot.getSingle(e));
                JDA jda = bots.get(vBot.getSingle(e));
                Guild guild = null;
                for (Guild s : jda.getGuilds()) {
                    for (VoiceChannel vc : s.getVoiceChannels()) {
                        if (vc.getId().equalsIgnoreCase(vID.getSingle(e))) ;
                        guild = s;
                    }
                }

                AudioManager manager = guild.getAudioManager();
                VoiceChannel channel = jda.getVoiceChannelById(vID.getSingle(e));
                if (manager.getSendingHandler() == null) {
                    player.setVolume(DEFAULT_VOLUME);
                    manager.setSendingHandler(player);
                } else {
                    player = (MusicPlayer) manager.getSendingHandler();
                }
                manager.openAudioConnection(channel);
            }
        }catch (IllegalStateException x){
            Skript.warning("You may only join one voice channel at a time.");
        }
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
