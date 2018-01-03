package me.iblitzkriegi.vixio;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Converter;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.*;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.Converters;
import me.iblitzkriegi.vixio.registration.Documentation;
import me.iblitzkriegi.vixio.registration.Registration;
import me.iblitzkriegi.vixio.util.Metrics;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import org.apache.commons.logging.impl.SimpleLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Blitz on 7/22/2017.
 */
public class Vixio extends JavaPlugin {
    // Instances \\
    public static Vixio instance;
    public static SkriptAddon addonInstance;
    // Registration \\
    public List<Registration> conditions = new ArrayList<>();
    public List<Registration> events = new ArrayList<>();
    public List<Registration> effects = new ArrayList<>();
    public List<Registration> expressions = new ArrayList<>();
    // JDA Related \\
    public HashMap<String, JDA> bots = new HashMap<>();
    public HashMap<SelfUser, JDA> jdaUsers = new HashMap<>();
    public List<JDA> jdaInstances = new ArrayList<>();
    public static Logger logger;


    public Vixio() {
        if (instance == null) {
            instance = this;
        } else {
            throw new IllegalStateException();
        }
    }
    @Override
    public void onEnable(){

        Converters.registerConverter(ISnowflake.class, String.class, (Converter<ISnowflake, String>) u -> u.getId());
        try {
            getAddonInstance().loadClasses("me.iblitzkriegi.vixio", "effects", "events", "expressions");
            Vixio.setup();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!this.getDataFolder().exists()){
            this.getDataFolder().mkdir();
        }
        Metrics mertrics = new Metrics(this);
        Documentation.setupSyntaxFile();
    }
    public static Vixio getInstance(){
        if(instance == null){
            return null;
        }
        return instance;
    }
    public static SkriptAddon getAddonInstance(){
        if(addonInstance == null) {
            addonInstance = Skript.registerAddon(getInstance());
        }
        return addonInstance;
    }
    public Registration registerCondition(Class<? extends Condition> cond, String... patterns){
        Skript.registerCondition(cond, patterns);
        Registration registration = new Registration(cond, patterns);
        conditions.add(registration);
        return registration;
    }
    public Registration registerEvent(String name, Class type, Class clazz, String... patterns){
        Skript.registerEvent(name, type, clazz, patterns);
        Registration registration = new Registration(clazz, patterns);
        events.add(registration);
        return registration;
    }
    public Registration registerEffect(Class<? extends Effect> eff, String... patterns){
        Skript.registerEffect(eff, patterns);
        Registration reg = new Registration(eff, patterns);
        effects.add(reg);
        return reg;
    }
    public Registration registerExpression(Class<? extends Expression> expr, Class<?> returntype, ExpressionType exprtype, String... patterns){
        Skript.registerExpression(expr, returntype, exprtype, patterns);
        Registration registration = new Registration(expr, patterns);
        expressions.add(registration);
        return registration;
    }
    public Registration registerPropertyExpression(final Class<? extends Expression> c, final Class<?> type, final String property, final String fromType){
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
        Classes.registerClass(new ClassInfo<>(MessageChannel.class, "textchannel").user("textchannel").defaultExpression(new EventValueExpression<>(MessageChannel.class)).name("textchannel").parser(new Parser<MessageChannel>() {
            @Override
            public MessageChannel parse(String s, ParseContext context) {
                return null;
            }
            @Override
            public String toString(MessageChannel msg, int flags) {
                return msg.getId();
            }
            @Override
            public String toVariableNameString(MessageChannel msg) {
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
        Classes.registerClass(new ClassInfo<>(Guild.class, "guild").user("guild").defaultExpression(new EventValueExpression<>(Guild.class)).name("guild").parser(new Parser<Guild>() {
            @Override
            public Guild parse(String s, ParseContext context) {
                return null;
            }
            @Override
            public String toString(Guild msg, int flags) {
                return msg.getId();
            }
            @Override
            public String toVariableNameString(Guild msg) {
                return msg.getId();
            }
            @Override
            public String getVariableNamePattern() {
                return ".+";
            }
        }));
        Classes.registerClass(new ClassInfo<>(VoiceChannel.class, "voicechannel").user("voicechannel").defaultExpression(new EventValueExpression<>(VoiceChannel.class)).name("voicechannel").parser(new Parser<VoiceChannel>() {
            @Override
            public VoiceChannel parse(String s, ParseContext context) {
                return null;
            }
            @Override
            public String toString(VoiceChannel msg, int flags) {
                return msg.getId();
            }
            @Override
            public String toVariableNameString(VoiceChannel msg) {
                return msg.getId();
            }
            @Override
            public String getVariableNamePattern() {
                return ".+";
            }
        }));
        Classes.registerClass(new ClassInfo<>(SelfUser.class, "bot").user("bot").defaultExpression(new EventValueExpression<>(SelfUser.class)).name("bot").parser(new Parser<SelfUser>() {
            @Override
            public SelfUser parse(String s, ParseContext context) {
                return null;
            }
            @Override
            public String toString(SelfUser msg, int flags) {
                return msg.getId();
            }
            @Override
            public String toVariableNameString(SelfUser msg) {
                return msg.getId();
            }
            @Override
            public String getVariableNamePattern() {
                return ".+";
            }
        }));
    }

}
