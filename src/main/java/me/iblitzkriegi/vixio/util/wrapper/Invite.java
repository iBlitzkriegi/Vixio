package me.iblitzkriegi.vixio.util.wrapper;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.restaction.InviteAction;

import java.time.OffsetDateTime;

public class Invite {

    private int maxUses;
    private int maxAge;
    private boolean temporary;
    private boolean unique;
    private GuildChannel channel;
    private Guild guild;
    private User inviter;
    private String url;
    private String code;
    private OffsetDateTime timeCreated;
    private boolean isExpanded;
    private boolean isCreated = false;

    public static Invite parseInvite(net.dv8tion.jda.api.entities.Invite invite) {
        Invite newInvite = new Invite();

        newInvite.setMaxAge(invite.getMaxAge());
        newInvite.setInviter(invite.getInviter());
        newInvite.setChannel(invite.getJDA().getGuildChannelById(invite.getChannel().getId()));
        newInvite.setTimeCreated(invite.getTimeCreated());

        newInvite.setCode(invite.getCode());
        newInvite.setUrl(invite.getUrl());
        newInvite.setGuild(invite.getJDA().getGuildById(invite.getGuild().getId()));
        newInvite.setMaxUses(invite.getMaxUses());
        newInvite.setTemporary(invite.isTemporary());
        newInvite.setUnique(invite.isExpanded());
        newInvite.setExpanded(invite.isExpanded());
        newInvite.setCreated(true);
        return newInvite;
    }

    public void setGuild(Guild parsedGuild) {
        this.guild = parsedGuild;
    }

    public Guild getGuild() {
        return this.guild;
    }

    public int getMaxUses() {
        return maxUses;
    }

    public void setMaxUses(int maxUses) {
        this.maxUses = maxUses;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public boolean isTemporary() {
        return temporary;
    }

    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public GuildChannel getChannel() {
        return channel;
    }

    public void setChannel(GuildChannel channel) {
        this.channel = channel;
    }

    public User getInviter() {
        return inviter;
    }

    public void setInviter(User inviter) {
        this.inviter = inviter;
    }

    public OffsetDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(OffsetDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public InviteAction create() {
        if (this.getChannel() == null) {
            return null;
        }
        return this.getChannel().createInvite()
                .setMaxAge(this.getMaxAge())
                .setMaxUses(this.getMaxUses())
                .setTemporary(this.isTemporary())
                .setUnique(this.isUnique());
    }

    public boolean isCreated() {
        return isCreated;
    }

    private void setCreated(boolean b) {
        this.isCreated = b;
    }

}
