package me.iblitzkriegi.vixio.registration;

import java.util.List;

import org.bukkit.event.Event;

import com.google.gson.annotations.SerializedName;

import ch.njol.util.StringUtils;
import me.iblitzkriegi.vixio.util.skript.SkriptUtil;

/**
 * Created by Blitz on 7/22/2017.
 */
// Vixio.registerCondition(cls, patterns).setName().setDesc();
public class Registration {

    private String name;
    @SerializedName("description")
    private String desc;
    private String example;
    private String category;
    private transient Class<?> clazz;
    @SerializedName("syntax")
    private String[] syntax;
    @SerializedName("friendlySyntax")
    private String[] userFacing;
    private List<String> eventValues;
    private transient Class<? extends Event>[] events;

    public Registration(String category, Class<?> cls, String... syntax) {
        this.clazz = cls;
        this.category = category;
        this.syntax = syntax;
    }

    public Registration(String category, String... syntax) {
        this(category, null, syntax);
    }

    public Registration setExample(String example) {
        this.example = example;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Registration setName(String s) {
        this.name = s;
        return this;
    }

    public String getDesc() {
        return this.desc;
    }

    public Registration setDesc(String s) {
        this.desc = s;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String[] getSyntax() {
        return userFacing == null ? syntax : userFacing;
    }

    public String[] getTrueSyntax() {
        return this.syntax;
    }

    public String getExample() {
        return this.example;
    }

    public Registration setExample(String... example) {
        return setExample(StringUtils.join(example, "\n"));
    }

    public String[] getUserFacing() {
        return this.userFacing;
    }

    public Registration setUserFacing(String... patterns) {
        this.userFacing = patterns;
        return this;
    }

    /**
     * Called to fill in information that is only available after
     * everything is registered
     */
    public void update() {
        this.eventValues = events == null ? null : SkriptUtil.getEventValues(events);
    }

    public Class<? extends Event>[] getEvents() {
        return events;
    }

    @SuppressWarnings("unchecked")
    public Registration setEvents(Class<? extends Event>... events) {
        this.events = events;
        return this;
    }

    public List<String> getEventValues() {
        return eventValues;
    }

}
