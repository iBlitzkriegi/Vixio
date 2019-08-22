package me.iblitzkriegi.vixio.util.wrapper;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.IMentionable;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;

public class Emote implements IMentionable {
    private String name;
    private net.dv8tion.jda.api.entities.Emote emote;
    private boolean isEmote = false;
    private String mention;

    public Emote(String name) {
        this.name = name;
        this.mention = name;
    }

    public Emote(net.dv8tion.jda.api.entities.Emote emote) {
        this.name = emote.getName();
        this.emote = emote;
        this.isEmote = true;
        this.mention = emote.getAsMention();
    }

    public Guild getGuild() {
        return isEmote ? emote.getGuild() : null;
    }

    public net.dv8tion.jda.api.entities.Emote getEmote() {
        return isEmote ? emote : null;
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

    @Override
    public String getAsMention() {
        return isEmote ? mention : name;
    }

    @Override
    public long getIdLong() {
        return 0;
    }
}
