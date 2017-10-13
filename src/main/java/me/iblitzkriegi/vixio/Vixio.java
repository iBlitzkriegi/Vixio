package me.iblitzkriegi.vixio;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Converter;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.*;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.Converters;
import ch.njol.yggdrasil.Fields;
import me.iblitzkriegi.vixio.events.DiscordEventHandler;
import me.iblitzkriegi.vixio.registration.Documentation;
import me.iblitzkriegi.vixio.registration.Registration;
import me.iblitzkriegi.vixio.util.Metrics;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.entities.impl.TextChannelImpl;
import net.dv8tion.jda.core.entities.impl.UserImpl;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.guild.member.GuildMemberNickChangeEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Blitz on 7/22/2017.
 */
public class Vixio extends JavaPlugin {
    private static Vixio instance;
    private static SkriptAddon addonInstance;
    public static List<Registration> conditions = new ArrayList<>();
    public static List<Registration> events = new ArrayList<>();
    public static HashMap<String, Registration> eventsSyntax = new HashMap<>();
    public static List<Registration> effects = new ArrayList<>();
    public static List<Registration> expressions = new ArrayList<>();
    public static HashMap<String, JDA> bots = new HashMap<>();
    public static HashMap<String, Class<? extends Event>> eventSyntax = new HashMap<>();
    public static HashMap<SelfUser, JDA> jdaUsers = new HashMap<>();
    public static List<JDA> jdaInstances = new ArrayList<>();
    public static List<Class<? extends Event>> list = new ArrayList<>();
    public static Message lastRetrievedMessage;
    public Vixio() {
        if (instance == null) {
            instance = this;
        } else {
            throw new IllegalStateException();
        }
    }
    @Override
    public void onEnable(){
        list.add(GuildVoiceLeaveEvent.class); list.add(GuildMemberNickChangeEvent.class);
        for(Class<? extends Event> clazz : list){
            String syntax = clazz.getSimpleName().replaceAll("(?<!^)(?=[A-Z])", " ").toLowerCase().replaceFirst("Event", "");
            registerEvent(clazz.getSimpleName(), SimpleEvent.class, DiscordEventHandler.class, syntax)
                .setName(syntax)
                .setDesc("Discord event")
                .setExample("on " + syntax);
            eventSyntax.put("on " + syntax, clazz);
        }
        try {
            getAddonInstance().loadClasses("me.iblitzkriegi.vixio", "effects", "events", "expressions");
            Vixio.setup();
            Converters.registerConverter(ISnowflake.class, String.class, (Converter<ISnowflake, String>) u -> u.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!this.getDataFolder().exists()){
            this.getDataFolder().mkdir();
        }
        Metrics metrics = new Metrics(this);
        Documentation.setupSyntaxFile();
    }
    public static Vixio getInstance(){
        if(instance == null){
            throw new IllegalStateException();
        }
        return instance;
    }
    public static SkriptAddon getAddonInstance(){
        if(addonInstance == null) {
            addonInstance = Skript.registerAddon(getInstance());
        }
        return addonInstance;
    }
    public static Registration registerCondition(Class<? extends Condition> cond, String... patterns){
        Skript.registerCondition(cond, patterns);
        Registration registration = new Registration(cond, patterns);
        conditions.add(registration);
        return registration;
    }
    public static Registration registerEvent(String name, Class type, Class clazz, String... patterns){
        Skript.registerEvent(name, type, clazz, patterns[0]);
        Registration registration = new Registration(clazz, patterns);
        events.add(registration);
        eventsSyntax.put(clazz.getSimpleName(), registration);
        return registration;
    }
    public static Registration registerEffect(Class<? extends Effect> eff, String... patterns){
        Skript.registerEffect(eff, patterns[0]);
        Registration reg = new Registration(eff, patterns);
        effects.add(reg);
        return reg;
    }
    public static Registration registerExpression(Class<? extends Expression> expr, Class<?> returntype, ExpressionType exprtype, String... patterns){
        Skript.registerExpression(expr, returntype, exprtype, patterns);
        Registration registration = new Registration(expr, patterns);
        expressions.add(registration);
        return registration;
    }
    public static Registration registerPropertyExpression(final Class<? extends Expression> c, final Class<?> type, final String property, final String fromType){
        Skript.registerExpression(c, type, ExpressionType.PROPERTY, "[the] " + property + " of %" + fromType + "%", "%" + fromType + "%'[s] " + property);
        Registration registration = new Registration(c, "[the] " + property + " of %" + fromType + "%", "%" + fromType + "%'[s] " + property);
        expressions.add(registration);
        return registration;
    }
    private static void setup(){
        Classes.registerClass(new ClassInfo<>(Message.class, "message").user("message").defaultExpression(new EventValueExpression<>(Message.class)).name("message").parser(new Parser<Message>() {
            @Override
            public Message parse(String s, ParseContext context) {return null;}
            @Override
            public String toString(Message msg, int flags) {return msg.getId();}
            @Override
            public String toVariableNameString(Message msg) {return msg.getId();}
            @Override
            public String getVariableNamePattern() {return ".+";}}));
        Classes.registerClass(new ClassInfo<>(Channel.class, "channel").user("channel").defaultExpression(new EventValueExpression<>(Channel.class)).name("channel").parser(new Parser<Channel>() {
            @Override
            public Channel parse(String s, ParseContext context) {
                return null;
            }
            @Override
            public String toString(Channel msg, int flags) {
                return msg.getId();
            }
            @Override
            public String toVariableNameString(Channel msg) {
                return msg.getId();
            }
            @Override
            public String getVariableNamePattern() {
                return ".+";
            }
        }));
        Classes.registerClass(new ClassInfo<>(User.class, "user").user("user").defaultExpression(new EventValueExpression<>(User.class)).name("user").parser(new Parser<User>() {
            @Override
            public User parse(String s, ParseContext context) {
                return null;
            }
            @Override
            public String toString(User usr, int flags) {
                return usr.getId();
            }
            @Override
            public String toVariableNameString(User usr) {
                return usr.getId();
            }
            @Override
            public String getVariableNamePattern() {
                return ".+";
            }
        }));
        Classes.registerClass(new ClassInfo<>(Member.class, "member").user("member").defaultExpression(new EventValueExpression<>(Member.class)).name("member").parser(new Parser<Member>() {
            @Override
            public Member parse(String s, ParseContext context) {
                return null;
            }
            @Override
            public String toString(Member usr, int flags) {
                return usr.getUser().getId();
            }
            @Override
            public String toVariableNameString(Member usr) {
                return usr.getUser().getId();
            }
            @Override
            public String getVariableNamePattern() {
                return ".+";
            }
        }));
        Classes.registerClass(new ClassInfo<>(Guild.class, "guild").user("guild").defaultExpression(new EventValueExpression<>(Guild.class)).name("user").parser(new Parser<Guild>() {
            @Override
            public Guild parse(String s, ParseContext context) {return null;}
            @Override
            public String toString(Guild gui, int flags) {return gui.getId();}
            @Override
            public String toVariableNameString(Guild gui) {return gui.getId();}
            @Override
            public String getVariableNamePattern() {return ".+";}}));
        Classes.registerClass(new ClassInfo<>(VoiceChannel.class, "voicechannel").user("voicechannel").defaultExpression(new EventValueExpression<>(VoiceChannel.class)).name("user").parser(new Parser<VoiceChannel>() {
            @Override
            public VoiceChannel parse(String s, ParseContext context) {return null;}
            @Override
            public String toString(VoiceChannel gui, int flags) {return gui.getId();}
            @Override
            public String toVariableNameString(VoiceChannel gui) {return gui.getId();}
            @Override
            public String getVariableNamePattern() {return ".+";}}));
        Classes.registerClass(new ClassInfo<>(SelfUser.class, "bot").user("bot").defaultExpression(new EventValueExpression<>(SelfUser.class)).name("bot").parser(new Parser<SelfUser>() {
            @Override
            public SelfUser parse(String s, ParseContext context) {return null;}
            @Override
            public String toString(SelfUser gui, int flags) {return gui.getId();}
            @Override
            public String toVariableNameString(SelfUser gui) {return gui.getId();}
            @Override
            public String getVariableNamePattern() {return ".+";}}));


    }

}
