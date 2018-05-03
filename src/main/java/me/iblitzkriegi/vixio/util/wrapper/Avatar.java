package me.iblitzkriegi.vixio.util.wrapper;

import net.dv8tion.jda.core.entities.User;

public class Avatar {
    private User user;
    private String avatar;
    private boolean isDefault;

    public Avatar(User user, String avatar, boolean isDefault) {
        this.user = user;
        this.avatar = avatar;
        this.isDefault = isDefault;
    }

    public User getUser() {
        return user;
    }

    public String getAvatar() {
        return avatar;
    }

    public boolean isDefault() {
        return isDefault;
    }
}
