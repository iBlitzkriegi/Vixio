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
    private ShardManager shardManager;
    private SelfUser selfUser;
    private HashMap<Guild, GuildMusicManager> guildMusicManagerMap = new HashMap<>();
    private long uptime;

    public Bot(String name, ShardManager jda) {
        this.name = name;
        this.shardManager = jda;
    }

    public Bot(ShardManager shardManager) {
        this.shardManager = shardManager;
        this.name = null;
        this.selfUser = shardManager.getApplicationInfo().getJDA().getSelfUser();

    }

    public void setLoginTime(long uptime) {
        this.uptime = uptime;
    }

    // Getters \\
    public ShardManager getShardMananger() {
        return this.shardManager;
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
        return selfUser.getAsMention();
    }

    @Override
    public long getIdLong() {
        return shardManager.getApplicationInfo().getJDA()
    }

    public SelfUser getSelfUser() {
        return selfUser;
    }

    public HashMap<Guild, GuildMusicManager> getGuildMusicManagerMap() {
        return guildMusicManagerMap;
    }

    @Override
    public String toString() {
        return getName();
    }

}
