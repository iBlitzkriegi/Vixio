package me.iblitzkriegi.vixio.util.wrapper;

import me.iblitzkriegi.vixio.util.audio.GuildMusicManager;
import net.dv8tion.jda.bot.sharding.ShardManager;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.IMentionable;
import net.dv8tion.jda.core.entities.ISnowflake;
import net.dv8tion.jda.core.entities.SelfUser;

import java.util.HashMap;

public class Bot implements IMentionable, ISnowflake {
    private String name;
    private ShardManager jda;
    private SelfUser selfUser;
    private HashMap<Guild, GuildMusicManager> guildMusicManagerMap = new HashMap<>();
    private long uptime;

    public Bot(String name, ShardManager jda) {
        this.name = name;
        this.jda = jda;
    }

    public Bot(ShardManager jda) {
        this.jda = jda;
        this.name = null;
        this.selfUser = jda.getShards().get(0).getSelfUser();

    }

    public void setLoginTime(long uptime) {
        this.uptime = uptime;
    }

    // Getters \\
    public ShardManager getJDA() {
        return this.jda;
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

    public long getUptime() {
        return uptime;
    }

    @Override
    public String getAsMention() {
        return jda.getShards().get(0).getSelfUser().getAsMention();
    }

    @Override
    public long getIdLong() {
        return jda.getShards().get(0).getSelfUser().getIdLong();
    }

    public SelfUser getSelfUser() {
        return jda.getShards().get(0).getSelfUser();
    }

    public HashMap<Guild, GuildMusicManager> getGuildMusicManagerMap() {
        return guildMusicManagerMap;
    }

    @Override
    public String toString() {
        return getName();
    }

}
