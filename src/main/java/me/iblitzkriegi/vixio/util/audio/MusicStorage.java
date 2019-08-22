package me.iblitzkriegi.vixio.util.audio;

import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;

public class MusicStorage {
    private Bot bot;
    private Guild guild;

    public MusicStorage(Bot bot, Guild guild) {
        this.bot = bot;
        this.guild = guild;
    }

    public Bot getBot() {
        return bot;
    }

    public Guild getGuild() {
        return guild;
    }

}
