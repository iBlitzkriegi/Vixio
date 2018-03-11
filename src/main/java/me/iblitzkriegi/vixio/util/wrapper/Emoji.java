package me.iblitzkriegi.vixio.util.wrapper;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;

import java.util.List;

public class Emoji {
    private String name;
    private Emote emote;
    private boolean isEmote = false;
    private String mention;

    public Emoji(String name) {
        this.name = name;
    }

    public Emoji(Emote emote) {
        this.name = emote.getName();
        this.emote = emote;
        this.isEmote = true;
        this.mention = emote.getAsMention();
    }

    public Guild getGuild() {
        return isEmote ? emote.getGuild() : null;
    }

    public Emote getEmote() {
        return emote;
    }

    public List<Role> getRoles() {
        return isEmote ? emote.getRoles() : null;
    }

    public String getName() {
        return name;
    }

    public JDA getJDA() {
        return isEmote ? emote.getJDA() : null;
    }

    public boolean isEmote() {
        return isEmote;
    }

    public boolean isAnimated() {
        return isEmote ? emote.isAnimated() : false;
    }

    public String getID() {
        return isEmote ? emote.getId() : null;
    }

    public String getMention() {
        return mention;
    }
}
