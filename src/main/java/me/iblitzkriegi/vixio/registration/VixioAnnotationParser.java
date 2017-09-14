package me.iblitzkriegi.vixio.registration;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.events.*;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Blitz on 10/30/2016.
 */
public class VixioAnnotationParser {
    public static int classes = 0;
    public static HashMap<String, String> vEventExample = new HashMap<>();
    public static HashMap<String, String> vEventTitle = new HashMap<>();
    public static HashMap<String, String> vEvntShowroom = new HashMap<>();
    public static HashMap<String, String> vEventSyntax = new HashMap<>();
    public static HashMap<String, String> vEventDesc = new HashMap<>();
    public static HashMap<String, String> vCondExample = new HashMap<>();
    public static HashMap<String, String> vCondTitle = new HashMap<>();
    public static HashMap<String, String> vCondShowroom = new HashMap<>();
    public static HashMap<String, String> vCondSyntax = new HashMap<>();
    public static HashMap<String, String> vCondDesc = new HashMap<>();

    public static HashMap<String, String> vEffExample = new HashMap<>();
    public static HashMap<String, String> vEffTitle = new HashMap<>();
    public static HashMap<String, String> vEffShowroom = new HashMap<>();
    public static HashMap<String, String> vEffSyntax = new HashMap<>();
    public static HashMap<String, String> vEffDesc = new HashMap<>();

    public static HashMap<String, String> vExprExample = new HashMap<>();
    public static HashMap<String, String> vExprTitle = new HashMap<>();
    public static HashMap<String, String> vExprShowroom = new HashMap<>();
    public static HashMap<String, String> vExprSyntax = new HashMap<>();
    public static HashMap<String, String> vExprDesc = new HashMap<>();
    public static void parse() throws Exception {
        File file = null;
        try {
            file = new File(URLDecoder.decode(Vixio.class.getProtectionDomain().getCodeSource().getLocation().getFile(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.out.println("Could not find main jar file!");
            e.printStackTrace();
        }
        registerValues();
        for(Class clazz : getClasses(file, "me.iblitzkriegi.vixio")) {
            if (clazz.isAnnotationPresent(ExprAnnotation.Expression.class)) {
                ExprAnnotation.Expression ExprAnon = (ExprAnnotation.Expression) clazz.getAnnotation(ExprAnnotation.Expression.class);
                String syntax = ExprAnon.syntax();
                Class returntype = ExprAnon.returntype();
                ExpressionType exprType = ExprAnon.type();
                Skript.registerExpression(clazz, returntype, exprType, syntax);
                vExprTitle.put(ExprAnon.name(), ExprAnon.name());
                vExprSyntax.put(ExprAnon.name(), ExprAnon.syntax());
                vExprExample.put(ExprAnon.name(), ExprAnon.example());
                vExprShowroom.put(ExprAnon.name(), ExprAnon.title());
                vExprDesc.put(ExprAnon.name(), ExprAnon.desc());
            }else if (clazz.isAnnotationPresent(EffectAnnotation.Effect.class)) {
                EffectAnnotation.Effect EffAnon = (EffectAnnotation.Effect) clazz.getAnnotation(EffectAnnotation.Effect.class);
                String syntax = EffAnon.syntax();
                Skript.registerEffect(clazz, syntax);
                vEffTitle.put(EffAnon.name(), EffAnon.name());
                vEffSyntax.put(EffAnon.name(), EffAnon.syntax());
                vEffExample.put(EffAnon.name(), EffAnon.example());
                vEffShowroom.put(EffAnon.name(), EffAnon.title());
                vEffDesc.put(EffAnon.name(), EffAnon.desc());
            } else if (clazz.isAnnotationPresent(EvntAnnotation.Event.class)) {
                EvntAnnotation.Event EvntAnon = (EvntAnnotation.Event) clazz.getAnnotation(EvntAnnotation.Event.class);
                String syntax = EvntAnon.syntax();
                String name = EvntAnon.name();
                Class type = EvntAnon.type();
                Skript.registerEvent(name, type, clazz, syntax);
                vEventTitle.put(EvntAnon.name(), EvntAnon.name());
                vEventSyntax.put(EvntAnon.name(), EvntAnon.syntax());
                vEventExample.put(EvntAnon.name(), EvntAnon.example());
                vEvntShowroom.put(EvntAnon.name(), EvntAnon.title());
                vEventDesc.put(EvntAnon.name(), EvntAnon.desc());
            }else if(clazz.isAnnotationPresent(CondAnnotation.Condition.class)) {
                CondAnnotation.Condition CondAnon = (CondAnnotation.Condition) clazz.getAnnotation(CondAnnotation.Condition.class);
                String syntax = CondAnon.syntax();
                Skript.registerCondition(clazz, syntax);
                vCondTitle.put(CondAnon.name(), CondAnon.name());
                vCondSyntax.put(CondAnon.name(), CondAnon.syntax());
                vCondExample.put(CondAnon.name(), CondAnon.example());
                vCondShowroom.put(CondAnon.name(), CondAnon.title());
                vCondDesc.put(CondAnon.name(), CondAnon.desc());
            }
            classes = vEventTitle.size() + vCondTitle.size() + vEffTitle.size();

        }




    }
    public static Set<Class<?>> getClasses(File jarFile, String packageName) {
        Set<Class<?>> classes = new HashSet<>();
        try {
            JarFile file = new JarFile(jarFile);
            for (Enumeration<JarEntry> entry = file.entries(); entry.hasMoreElements();) {
                JarEntry jarEntry = entry.nextElement();
                String name = jarEntry.getName().replace("/", ".");
                if(name.startsWith(packageName) && name.endsWith(".class")){
                    classes.add(Class.forName(name.substring(0, name.length() - 6)));}
            }
            file.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return classes;
    }
    private static void registerValues(){
        // Guild Message Event \\
        EventValues.registerEventValue(EvntGuildMessageReceive.class, User.class, new Getter<User, EvntGuildMessageReceive>() {
            @Override
            public User get(EvntGuildMessageReceive evntGuildMessageReceive) {
                return evntGuildMessageReceive.getEvntUser();
            }

        }, 0);
        EventValues.registerEventValue(EvntGuildMessageReceive.class, Message.class, new Getter<Message, EvntGuildMessageReceive>() {
            @Override
            public Message get(EvntGuildMessageReceive evntGuildMessageReceive) {
                return evntGuildMessageReceive.getEvntMessage();
            }

        }, 0);
        EventValues.registerEventValue(EvntGuildMessageReceive.class, Channel.class, new Getter<Channel, EvntGuildMessageReceive>() {
            @Override
            public Channel get(EvntGuildMessageReceive evntGuildMessageReceive) {
                return evntGuildMessageReceive.getEvntChannel();
            }

        }, 0);
        EventValues.registerEventValue(EvntGuildMessageReceive.class, Guild.class, new Getter<Guild, EvntGuildMessageReceive>() {
            @Override
            public Guild get(EvntGuildMessageReceive evntGuildMessageReceive) {
                return evntGuildMessageReceive.getGuild();
            }

        }, 0);
        EventValues.registerEventValue(EvntGuildMessageReceive.class, String.class, new Getter<String, EvntGuildMessageReceive>() {
            @Override
            public String get(EvntGuildMessageReceive evntGuildMessageReceive) {
                return evntGuildMessageReceive.getEvntMessage().getContent();
            }
        }, 0);


        // Track End \\
        EventValues.registerEventValue(EvntAudioPlayerTrackEnd.class, com.sedmelluq.discord.lavaplayer.track.AudioTrack.class, new Getter<com.sedmelluq.discord.lavaplayer.track.AudioTrack, EvntAudioPlayerTrackEnd>() {
            @Override
            public com.sedmelluq.discord.lavaplayer.track.AudioTrack get(EvntAudioPlayerTrackEnd e) {
                return e.getTrack();
            }

        }, 0);
        EventValues.registerEventValue(EvntAudioPlayerTrackEnd.class, Guild.class, new Getter<Guild, EvntAudioPlayerTrackEnd>() {
            @Override
            public Guild get(EvntAudioPlayerTrackEnd e) {
                return e.getGuild();
            }

        }, 0);

        // Track Start \\
        EventValues.registerEventValue(EvntAudioPlayerTrackStart.class, com.sedmelluq.discord.lavaplayer.track.AudioTrack.class, new Getter<com.sedmelluq.discord.lavaplayer.track.AudioTrack, EvntAudioPlayerTrackStart>() {
            @Override
            public com.sedmelluq.discord.lavaplayer.track.AudioTrack get(EvntAudioPlayerTrackStart e) {
                return e.getTrack();
            }

        }, 0);
        EventValues.registerEventValue(EvntAudioPlayerTrackStart.class, Guild.class, new Getter<Guild, EvntAudioPlayerTrackStart>() {
            @Override
            public Guild get(EvntAudioPlayerTrackStart e) {
                return e.getGuild();
            }

        }, 0);



        //Guild Ban\\
        EventValues.registerEventValue(EvntGuildBan.class, User.class, new Getter<User, EvntGuildBan>() {
            @Override
            public User get(EvntGuildBan e) {
                return e.getEvntUser();
            }

        }, 0);
        EventValues.registerEventValue(EvntGuildBan.class, Guild.class, new Getter<Guild, EvntGuildBan>() {
            @Override
            public Guild get(EvntGuildBan e) {
                return e.getEvntGuild();
            }

        }, 0);


        // Guild Member Join \\
        EventValues.registerEventValue(EvntGuildMemberJoin.class, User.class, new Getter<User, EvntGuildMemberJoin>() {
            @Override
            public User get(EvntGuildMemberJoin e) {
                return e.getEvntUser();
            }

        }, 0);
        EventValues.registerEventValue(EvntGuildMemberJoin.class, Guild.class, new Getter<Guild, EvntGuildMemberJoin>() {
            @Override
            public Guild get(EvntGuildMemberJoin e) {
                return e.getEvntGuild();
            }

        }, 0);


        // Guild Member Leave \\
        EventValues.registerEventValue(EvntGuildMemberLeave.class, User.class, new Getter<User, EvntGuildMemberLeave>() {
            @Override
            public User get(EvntGuildMemberLeave e) {
                return e.getEvntUser();
            }

        }, 0);
        EventValues.registerEventValue(EvntGuildMemberLeave.class, Guild.class, new Getter<Guild, EvntGuildMemberLeave>() {
            @Override
            public Guild get(EvntGuildMemberLeave e) {
                return e.getEvntGuild();
            }

        }, 0);


        // Bot send Message \\
        EventValues.registerEventValue(EvntGuildMessageBotSend.class, Guild.class, new Getter<Guild, EvntGuildMessageBotSend>() {
            @Override
            public Guild get(EvntGuildMessageBotSend e) {
                return e.getGuild();
            }

        }, 0);
        EventValues.registerEventValue(EvntGuildMessageBotSend.class, Channel.class, new Getter<Channel, EvntGuildMessageBotSend>() {
            @Override
            public Channel get(EvntGuildMessageBotSend e) {
                return e.getEvntChannel();
            }

        }, 0);
        EventValues.registerEventValue(EvntGuildMessageBotSend.class, Message.class, new Getter<Message, EvntGuildMessageBotSend>() {
            @Override
            public Message get(EvntGuildMessageBotSend e) {
                return e.getEvntMessage();
            }
        }, 0);
        EventValues.registerEventValue(EvntGuildMessageBotSend.class, String.class, new Getter<String, EvntGuildMessageBotSend>() {
            @Override
            public String get(EvntGuildMessageBotSend e) {
                return e.getEvntMessage().getContent();
            }

        }, 0);
        EventValues.registerEventValue(EvntGuildMessageBotSend.class, User.class, new Getter<User, EvntGuildMessageBotSend>() {
            @Override
            public User get(EvntGuildMessageBotSend e) {
                return e.getEvntUser();
            }

        }, 0);
        // Message Add Reaction \\
        EventValues.registerEventValue(EvntMessageAddReaction.class, User.class, new Getter<User, EvntMessageAddReaction>() {
            @Override
            public User get(EvntMessageAddReaction e) {
                return e.getEvntUser();
            }

        }, 0);
        EventValues.registerEventValue(EvntMessageAddReaction.class, Message.class, new Getter<Message, EvntMessageAddReaction>() {
            @Override
            public Message get(EvntMessageAddReaction e) {
                return e.getEvntMessage();
            }

        }, 0);
        EventValues.registerEventValue(EvntMessageAddReaction.class, String.class, new Getter<String, EvntMessageAddReaction>() {
            @Override
            public String get(EvntMessageAddReaction e) {
                return e.getEvntEmoji();
            }

        }, 0);
        EventValues.registerEventValue(EvntMessageAddReaction.class, Channel.class, new Getter<Channel, EvntMessageAddReaction>() {
            @Override
            public Channel get(EvntMessageAddReaction e) {
                return e.getEvntChannel();
            }

        }, 0);

        // Private Message Rec \\
        EventValues.registerEventValue(EvntPrivateMessageReceive.class, User.class, new Getter<User, EvntPrivateMessageReceive>() {
            @Override
            public User get(EvntPrivateMessageReceive e) {
                return e.getEvntUser();
            }

        }, 0);
        EventValues.registerEventValue(EvntPrivateMessageReceive.class, Message.class, new Getter<Message, EvntPrivateMessageReceive>() {
            @Override
            public Message get(EvntPrivateMessageReceive e) {
                return e.getEvntMessage();
            }

        }, 0);
        EventValues.registerEventValue(EvntPrivateMessageReceive.class, String.class, new Getter<String, EvntPrivateMessageReceive>() {
            @Override
            public String get(EvntPrivateMessageReceive e) {
                return e.getEvntMessage().getContent();
            }

        }, 0);


        // Textchannel created \\
        EventValues.registerEventValue(EvntTextChannelCreated.class, Channel.class, new Getter<Channel, EvntTextChannelCreated>() {
            @Override
            public Channel get(EvntTextChannelCreated e) {
                return e.getEvntChannel();
            }

        }, 0);
        EventValues.registerEventValue(EvntTextChannelCreated.class, Guild.class, new Getter<Guild, EvntTextChannelCreated>() {
            @Override
            public Guild get(EvntTextChannelCreated e) {
                return e.getEvntGuild();
            }

        }, 0);
        EventValues.registerEventValue(EvntTextChannelCreated.class, Channel.class, new Getter<Channel, EvntTextChannelCreated>() {
            @Override
            public Channel get(EvntTextChannelCreated e) {
                return e.getEvntChannel();
            }

        }, 0);

        // Textchannel deleted \\
        EventValues.registerEventValue(EvntTextChannelDeleted.class, Channel.class, new Getter<Channel, EvntTextChannelDeleted>() {
            @Override
            public Channel get(EvntTextChannelDeleted e) {
                return e.getEvntChannel();
            }

        }, 0);
        EventValues.registerEventValue(EvntTextChannelDeleted.class, Guild.class, new Getter<Guild, EvntTextChannelDeleted>() {
            @Override
            public Guild get(EvntTextChannelDeleted e) {
                return e.getEvntGuild();
            }

        }, 0);

        // Avatar Update \\
        EventValues.registerEventValue(EvntUserAvatarUpdate.class, User.class, new Getter<User, EvntUserAvatarUpdate>() {
            @Override
            public User get(EvntUserAvatarUpdate e) {
                return e.getEvntUser();
            }

        }, 0);
        EventValues.registerEventValue(EvntUserAvatarUpdate.class, String.class, new Getter<String, EvntUserAvatarUpdate>() {
            @Override
            public String get(EvntUserAvatarUpdate e) {
                return e.getOld();
            }

        }, 0);

        // User join vc \\
        EventValues.registerEventValue(EvntUserJoinVc.class, User.class, new Getter<User, EvntUserJoinVc>() {
            @Override
            public User get(EvntUserJoinVc e) {
                return e.getEvntUser();
            }

        }, 0);
        EventValues.registerEventValue(EvntUserJoinVc.class, Guild.class, new Getter<Guild, EvntUserJoinVc>() {
            @Override
            public Guild get(EvntUserJoinVc e) {
                return e.getEvntGuild();
            }

        }, 0);
        EventValues.registerEventValue(EvntUserJoinVc.class, Channel.class, new Getter<Channel, EvntUserJoinVc>() {
            @Override
            public Channel get(EvntUserJoinVc e) {
                return e.getEvntChannel();
            }

        }, 0);

        // User leave vc \\
        EventValues.registerEventValue(EvntUserLeaveVc.class, User.class, new Getter<User, EvntUserLeaveVc>() {
            @Override
            public User get(EvntUserLeaveVc e) {
                return e.getEvntUser();
            }

        }, 0);
        EventValues.registerEventValue(EvntUserLeaveVc.class, Guild.class, new Getter<Guild, EvntUserLeaveVc>() {
            @Override
            public Guild get(EvntUserLeaveVc e) {
                return e.getEvntGuild();
            }

        }, 0);
        EventValues.registerEventValue(EvntUserLeaveVc.class, Channel.class, new Getter<Channel, EvntUserLeaveVc>() {
            @Override
            public Channel get(EvntUserLeaveVc e) {
                return e.getEvntChannel();
            }

        }, 0);

        // User start Streaming \\
        EventValues.registerEventValue(EvntUserStartStreaming.class, URL.class, new Getter<URL, EvntUserStartStreaming>() {
            @Override
            public URL get(EvntUserStartStreaming e) {
                return e.getEvntUrl();
            }

        }, 0);
        EventValues.registerEventValue(EvntUserStartStreaming.class, User.class, new Getter<User, EvntUserStartStreaming>() {
            @Override
            public User get(EvntUserStartStreaming e) {
                return e.getEvntUser();
            }

        }, 0);
        EventValues.registerEventValue(EvntUserStartStreaming.class, Guild.class, new Getter<Guild, EvntUserStartStreaming>() {
            @Override
            public Guild get(EvntUserStartStreaming e) {
                return e.getEvntGuild();
            }

        }, 0);

        // Status Change \\
        EventValues.registerEventValue(EvntUserStatusChange.class, Guild.class, new Getter<Guild, EvntUserStatusChange>() {
            @Override
            public Guild get(EvntUserStatusChange e) {
                return e.getEvntGuild();
            }

        }, 0);
        EventValues.registerEventValue(EvntUserStatusChange.class, User.class, new Getter<User, EvntUserStatusChange>() {
            @Override
            public User get(EvntUserStatusChange e) {
                return e.getEvntUser();
            }

        }, 0);
        EventValues.registerEventValue(EvntUserStatusChange.class, OnlineStatus.class, new Getter<OnlineStatus, EvntUserStatusChange>() {
            @Override
            public OnlineStatus get(EvntUserStatusChange e) {
                return e.getEvntStatus();
            }

        }, 0);

        // Private Message Send \\
        EventValues.registerEventValue(EvntPrivateMessageSend.class, String.class, new Getter<String, EvntPrivateMessageSend>() {
            @Override
            public String get(EvntPrivateMessageSend e) {
                return e.getEvntMessage().getContent();
            }
        }, 0);
        EventValues.registerEventValue(EvntPrivateMessageSend.class, User.class, new Getter<User, EvntPrivateMessageSend>() {
            @Override
            public User get(EvntPrivateMessageSend e) {
                return e.getEvntUser();
            }
        }, 0);
    }
}
