package me.iblitzkriegi.vixio.util.wrapper;

import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.ChannelType;

public class ChannelBuilder {

    private String name;
    private boolean NSFW = false;
    private String topic;
    private Category parent;
    private ChannelType type;
    private Integer bitRate = 64;
    private Integer userLimit = 0;

    public String getName() {
        return name;
    }

    public void setName(String s) {
        this.name = s;
    }

    public ChannelType getType() {
        return type;
    }

    public void setType(ChannelType type) {
        this.type = type;
    }

    public boolean isNSFW() {
        return NSFW;
    }

    public void setNSFW(boolean NSFW) {
        this.NSFW = NSFW;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Integer getBitRate() {
        return bitRate;
    }

    public void setBitRate(Integer bitRate) {
        this.bitRate = bitRate;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getUserLimit() {
        return userLimit;
    }

    public void setUserLimit(Integer userLimit) {
        this.userLimit = userLimit;
    }

}
