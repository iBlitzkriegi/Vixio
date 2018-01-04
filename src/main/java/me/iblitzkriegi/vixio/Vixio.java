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
import me.iblitzkriegi.vixio.util.SimpleType;
import me.iblitzkriegi.vixio.util.Title;
import me.iblitzkriegi.vixio.util.Util;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import org.apache.logging.log4j.Logger;
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
            getAddonInstance().loadClasses("me.iblitzkriegi.vixio", "effects", "events", "expressions", "scopes");
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

        new SimpleType<EmbedBuilder>(EmbedBuilder.class, "embedbuilder", "embed ? builders?") {
            public EmbedBuilder parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(EmbedBuilder builder, int arg1) {
                return "embed";
            }

            @Override
            public String toVariableNameString(EmbedBuilder builder) {
                return "embed";

            }
        };

        new SimpleType<java.awt.Color>(java.awt.Color.class, "javacolor", "java ?colors?") {

            @Override
            public java.awt.Color parse(String s, ParseContext context) {
                return Util.getColorFromString(s);
            }

            @Override
            public String toString(java.awt.Color c, int flags) {
                return "color from rgb " + c.getRed() + ", " + c.getGreen() + " and " + c.getBlue();
            }

            @Override
            public String toVariableNameString(java.awt.Color c) {
                return "color from rgb " + c.getRed() + ", " + c.getGreen() + " and " + c.getBlue();
            }

        };

        new SimpleType<MessageEmbed.Thumbnail>(MessageEmbed.Thumbnail.class, "thumbnail", "thumbnails?") {

            @Override
            public MessageEmbed.Thumbnail parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(MessageEmbed.Thumbnail thumb, int arg1) {
                return thumb.getUrl();
            }

            @Override
            public String toVariableNameString(MessageEmbed.Thumbnail thumb) {
                return thumb.getUrl();
            }

        };

        new SimpleType<MessageEmbed.ImageInfo>(MessageEmbed.ImageInfo.class, "imageinfo", "image ? infos?") {

            @Override
            public MessageEmbed.ImageInfo parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(MessageEmbed.ImageInfo image, int arg1) {
                return image.getUrl();
            }

            @Override
            public String toVariableNameString(MessageEmbed.ImageInfo image) {
                return image.getUrl();
            }

        };

        new SimpleType<MessageEmbed.Footer>(MessageEmbed.Footer.class, "footer", "footers?") {

            @Override
            public MessageEmbed.Footer parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(MessageEmbed.Footer footer, int arg1) {
                return footer.getText();
            }

            @Override
            public String toVariableNameString(MessageEmbed.Footer footer) {
                return footer.getText();
            }

        };

        new SimpleType<MessageEmbed.AuthorInfo>(MessageEmbed.AuthorInfo.class, "authorinfo", "author ?infos?") {

            @Override
            public MessageEmbed.AuthorInfo parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(MessageEmbed.AuthorInfo author, int arg1) {
                return author.getName();
            }

            @Override
            public String toVariableNameString(MessageEmbed.AuthorInfo author) {
                return author.getName();
            }

        };

        new SimpleType<MessageEmbed>(MessageEmbed.class, "embed", "message ?embeds?") {

            @Override
            public MessageEmbed parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(MessageEmbed message, int arg1) {
                return "embed";
            }

            @Override
            public String toVariableNameString(MessageEmbed message) {
                return "embed";
            }

        };

        new SimpleType<Title>(Title.class, "title", "titles?") {

            @Override
            public Title parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(Title title, int arg1) {
                return title.getText();
            }

            @Override
            public String toVariableNameString(Title title) {
                return title.getText();
            }

        };

        new SimpleType<MessageEmbed.Field>(MessageEmbed.Field.class, "field", "fields?") {

            @Override
            public MessageEmbed.Field parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(MessageEmbed.Field field, int arg1) {
                return field.getValue();
            }

            @Override
            public String toVariableNameString(MessageEmbed.Field field) {
                return field.getValue();
            }

        };

        Converters.registerConverter(ch.njol.skript.util.Color.class, java.awt.Color.class, new Converter<ch.njol.skript.util.Color, java.awt.Color>() {
            @Override
            public java.awt.Color convert(ch.njol.skript.util.Color color) {
                org.bukkit.Color bukkitColor = color.getBukkitColor();
                return new java.awt.Color(bukkitColor.getRed(), bukkitColor.getGreen(), bukkitColor.getBlue());
            }
        });

        Converters.registerConverter(EmbedBuilder.class, MessageEmbed.class,
                (Converter<EmbedBuilder, MessageEmbed>) e -> e.isEmpty() ? null : e.build()
        );

    }

    public Registration registerPropertyExpression(final Class<? extends Expression> c, final Class<?> type, final String property, final String fromType) {
        Skript.registerExpression(c, type, ExpressionType.PROPERTY, "[the] " + property + " of %" + fromType + "%", "%" + fromType + "%'[s] " + property);
        Registration registration = new Registration(c, "[the] " + property + " of %" + fromType + "%", "%" + fromType + "%'[s] " + property);
        expressions.add(registration);
        return registration;
    }

    public static String getPattern(Class<?> clazz){
        return clazz.getSimpleName().replaceAll("(?<!^)(?=[A-Z])", " ").toLowerCase().replaceFirst("event", "");
    }

}
