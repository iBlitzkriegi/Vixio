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
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.entities.VoiceChannel;
import net.dv8tion.jda.managers.AudioManager;
import net.dv8tion.jda.player.MusicPlayer;
import org.bukkit.event.Event;

import static me.iblitzkriegi.vixio.effects.EffLogin.bots;
import static net.dv8tion.jda.player.Bot.DEFAULT_VOLUME;

/**
 * Created by Blitz on 11/8/2016.
 */
@EffectAnnotation.Effect(syntax = "leave all voice channel[']s with %string%")
public class EffLeaveVoiceChannel extends Effect {
    Expression<String> vBot;
    Expression<String> vGuild;
    @Override
    protected void execute(Event e) {
        if (bots.get(vBot.getSingle(e)) != null) {
            MusicPlayer player = EffLogin.musicPlayerHashMap.get(vBot.getSingle(e));
            JDA jda = bots.get(vBot.getSingle(e));
            Guild guild = null;
            for (Guild s : jda.getGuilds()) {
                for (VoiceChannel vc : s.getVoiceChannels()) {
                    for(User user : vc.getUsers()){
                        if(user.getUsername().equalsIgnoreCase(vBot.getSingle(e))){
                            guild = s;
                        }
                    }
                }
            }

            AudioManager manager = guild.getAudioManager();
            if (manager.getSendingHandler() == null) {
                player.setVolume(DEFAULT_VOLUME);
                manager.setSendingHandler(player);
            } else {
                player = (MusicPlayer) manager.getSendingHandler();
            }
            manager.closeAudioConnection();
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vBot = (Expression<String>) expr[0];
        vGuild = (Expression<String>) expr[0];
        return true;
    }
}
