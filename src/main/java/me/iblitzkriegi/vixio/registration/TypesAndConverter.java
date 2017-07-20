package me.iblitzkriegi.vixio.registration;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Converter;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.Converters;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.*;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;

import java.net.URL;

/**
 * Created by Blitz on 10/30/2016.
 */
public class TypesAndConverter {
    public static void setupTypes() {
        Converters.registerConverter(Member.class, User.class, (Converter<Member, User>) u -> u.getUser());
        Converters.registerConverter(URL.class, String.class, (Converter<URL, String>) u -> u.toString());
        Converters.registerConverter(ISnowflake.class, String.class, (Converter<ISnowflake, String>) u -> u.getId());
        Classes.registerClass(new ClassInfo<>(Message.class, "message")
                .user("message")
                .defaultExpression(new EventValueExpression<>(Message.class))
                .name("message").parser(new Parser<Message>() {
                    @Override
                    @Nullable
                    public Message parse(String s, ParseContext context) {
                        return null;
                    }

                    @Override
                    public String toString(Message msg, int flags) {
                        return msg.getId();
                    }

                    @Override
                    public String toVariableNameString(Message msg) {
                        return msg.getId();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }
                }
                ));
        Classes.registerClass(new ClassInfo<>(Guild.class, "guild")
                .user("guild")
                .defaultExpression(new EventValueExpression<>(Guild.class))
                .name("guild").parser(new Parser<Guild>() {
                    @Override
                    public Guild parse(String s, ParseContext parseContext) {
                        return null;
                    }

                    @Override
                    public String toString(Guild guild, int i) {
                        return guild.getId();
                    }

                    @Override
                    public String toVariableNameString(Guild guild) {
                        return guild.getId();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }
                })



        );
        Classes.registerClass(new ClassInfo<>(User.class, "user")
                .user("user")
                .defaultExpression(new EventValueExpression<>(User.class))
                .name("user").parser(new Parser<User>() {
                    @Override
                    @Nullable
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

                }
                ));
        Classes.registerClass(new ClassInfo<>(AudioTrack.class, "track")
                .user("track")
                .defaultExpression(new EventValueExpression<>(AudioTrack.class))
                .name("track").parser(new Parser<AudioTrack>() {
                    @Override
                    public AudioTrack parse(String s, ParseContext parseContext) {
                        return null;
                    }

                    @Override
                    public String toString(AudioTrack audioTrack, int i) {
                        return audioTrack.toString();
                    }

                    @Override
                    public String toVariableNameString(AudioTrack audioTrack) {
                        return audioTrack.toString();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }
                }));
        Classes.registerClass(new ClassInfo<>(Channel.class, "channel")
                .user("channel")
                .defaultExpression(new EventValueExpression<>(Channel.class))
                .name("channel").parser(new Parser<Channel>() {
                    @Override
                    @Nullable
                    public Channel parse(String s, ParseContext context) {
                        return null;
                    }

                    @Override
                    public String toString(Channel msg, int flags) {
                        return msg.getId();
                    }

                    @Override
                    public String toVariableNameString(Channel msg) {
                        return null;
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                }));
        Classes.registerClass(new ClassInfo<>(URL.class, "url")
                .user("url")
                .defaultExpression(new EventValueExpression<>(URL.class))
                .name("url").parser(new Parser<URL>() {
                    @Override
                    public URL parse(String s, ParseContext parseContext) {
                        return null;
                    }

                    @Override
                    public String toString(URL url, int i) {
                        return url.toString();
                    }

                    @Override
                    public String toVariableNameString(URL url) {
                        return null;
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }
                })
        );
        Classes.registerClass(new ClassInfo<>(OnlineStatus.class, "status")
        .user("status")
                .defaultExpression(new EventValueExpression<>(OnlineStatus.class))
                .name("status").parser(new Parser<OnlineStatus>() {
                            @Override
                            public OnlineStatus parse(String s, ParseContext parseContext) {
                                return null;
                            }

                            @Override
                            public String toString(OnlineStatus onlineStatus, int i) {
                                if(onlineStatus.name().equalsIgnoreCase("DO NOT DISTURB")){
                                    return "Do Not Disturb";
                                }
                                return onlineStatus.name();
                            }

                            @Override
                            public String toVariableNameString(OnlineStatus onlineStatus) {
                                return null;
                            }

                            @Override
                            public String getVariableNamePattern() {
                                return ".+";
                            }
                        })
        );

    }
}
