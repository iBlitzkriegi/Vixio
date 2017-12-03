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
import me.iblitzkriegi.vixio.events.DiscordEventHandler;
import me.iblitzkriegi.vixio.registration.Documentation;
import me.iblitzkriegi.vixio.registration.Registration;
import me.iblitzkriegi.vixio.util.DiscordEventCompare;
import me.iblitzkriegi.vixio.util.Metrics;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.channel.text.update.TextChannelUpdateTopicEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import org.bukkit.plugin.java.JavaPlugin;

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
    public static HashMap<String, Registration> eventsSyntax = new HashMap<>();
    // JDA Related \\
    public HashMap<String, JDA> bots = new HashMap<>();
    public HashMap<SelfUser, JDA> jdaUsers = new HashMap<>();
    public List<JDA> jdaInstances = new ArrayList<>();
    public ArrayList<Class<?>> jdaEvents = new ArrayList<>();
    // Syntax related \\
    public ArrayList<String> patterns = new ArrayList<>();
    public HashMap<Class<?>, String> syntaxEvent = new HashMap<>();

    public Vixio() {
        if (instance == null) {
            instance = this;
        } else {
            throw new IllegalStateException();
        }
    }
    @Override
    public void onEnable(){
        jdaEvents.add(GuildVoiceLeaveEvent.class);
        jdaEvents.add(GuildMessageReceivedEvent.class);
        jdaEvents.add(GuildVoiceJoinEvent.class);
        jdaEvents.add(GuildMessageReactionAddEvent.class);
        jdaEvents.add(TextChannelUpdateTopicEvent.class);
        for(Class<?> clz : jdaEvents){
            String syntax = getPattern(clz);
            patterns.add(syntax);
            syntaxEvent.put(clz, syntax);
        }
        registerEvent("DiscordEventHandler", DiscordEventCompare.class, DiscordEventHandler.class, patterns.toArray(new String[0]));
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
        new Metrics(this);
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
        eventsSyntax.put(clazz.getSimpleName(), registration);

        return registration;
    }
    public Registration registerEffect(Class<? extends Effect> eff, String... patterns){
        Skript.registerEffect(eff, patterns[0]);
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
        Classes.registerClass(new ClassInfo<>(Channel.class, "channel").user("channel").defaultExpression(new EventValueExpression<>(TextChannel.class)).name("channel").parser(new Parser<TextChannel>() {
            @Override
            public TextChannel parse(String s, ParseContext context) {
                return null;
            }
            @Override
            public String toString(TextChannel msg, int flags) {
                return msg.getId();
            }
            @Override
            public String toVariableNameString(TextChannel msg) {
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
        Classes.registerClass(new ClassInfo<>(VoiceChannel.class, "voice").user("voice").defaultExpression(new EventValueExpression<>(VoiceChannel.class)).name("voice").parser(new Parser<VoiceChannel>() {
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
    public static String getPattern(Class<?> clazz){
        return clazz.getSimpleName().replaceAll("(?<!^)(?=[A-Z])", " ").toLowerCase().replaceFirst("event", "");
    }

}
