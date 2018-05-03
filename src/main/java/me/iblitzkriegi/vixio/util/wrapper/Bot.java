package me.iblitzkriegi.vixio.util.wrapper;

import me.iblitzkriegi.vixio.util.audio.GuildMusicManager;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.IMentionable;
import net.dv8tion.jda.core.entities.ISnowflake;
import net.dv8tion.jda.core.entities.SelfUser;

import java.util.HashMap;

public class Bot implements IMentionable, ISnowflake {
    private String name;
    private JDA jda;
    private SelfUser selfUser;
    private HashMap<Guild, GuildMusicManager> guildMusicManagerMap = new HashMap<>();

    public Bot(String name, JDA jda) {
        this.name = name;
        this.jda = jda;
        this.selfUser = jda.getSelfUser();
    }

    public Bot(JDA jda) {
        this.jda = jda;
        this.selfUser = jda.getSelfUser();
        this.name = null;

    }

    // Getters \\
    public JDA getJDA() {
        return this.jda;
    }

    public SelfUser getSelfUser() {
        return selfUser;
    }

    public String getName() {
        return this.name;
    }

    public GuildMusicManager getAudioManager(Guild guild) {
        if (guildMusicManagerMap.get(guild) == null) {
            GuildMusicManager musicManager = new GuildMusicManager(guild, this);
            guildMusicManagerMap.put(guild, musicManager);
            guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
            return musicManager;
        }

        return guildMusicManagerMap.get(guild);

    }

    @Override
    public String getAsMention() {
        return selfUser.getAsMention();
    }

    @Override
    public long getIdLong() {
        return selfUser.getIdLong();
    }


    @Override
    public String toString() {
        return getName();
    }

}
