package me.iblitzkriegi.vixio.registration;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.util.EnumUtils;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.VixioChanger;
import me.iblitzkriegi.vixio.commands.DiscordCommand;
import me.iblitzkriegi.vixio.commands.DiscordCommandFactory;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.embed.Title;
import me.iblitzkriegi.vixio.util.skript.EnumMapper;
import me.iblitzkriegi.vixio.util.skript.SimpleType;
import me.iblitzkriegi.vixio.util.wrapper.Avatar;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import me.iblitzkriegi.vixio.util.wrapper.ChannelBuilder;
import me.iblitzkriegi.vixio.util.wrapper.Emote;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.Region;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;

public class Types {

    @SuppressWarnings("unchecked")
    public static void register() {
        new SimpleType<Channel>(Channel.class, "channel", "channels?") {

            @Override
            public Channel parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(Channel channel, int arg1) {
                return channel.getName();
            }

            @Override
            public String toVariableNameString(Channel channel) {
                return channel.getId();
            }

        };

        EnumUtils<Region> regionEnumUtils = new EnumUtils<>(Region.class, "regions");
        new SimpleType<Region>(Region.class, "serverregion", "serverregions?") {

            @Override
            public Region parse(String s, ParseContext pc) {
                return regionEnumUtils.parse(s);
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(Region region, int arg1) {
                return regionEnumUtils.toString(region, arg1);
            }

            @Override
            public String toVariableNameString(Region region) {
                return region.toString();
            }

        };

        new SimpleType<Message>(Message.class, "message", "messages?") {

            @Override
            public Message parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(Message message, int arg1) {
                return message.getContentRaw();
            }

            @Override
            public String toVariableNameString(Message message) {
                return message.getContentRaw();
            }

        }.changer(new VixioChanger<Message>() {

            @Override
            public Class<?>[] acceptChange(ChangeMode mode, boolean vixioChanger) {
                if (mode == ChangeMode.DELETE) {
                    return new Class[]{Message.class};
                }
                return null;
            }

            @Override
            public void change(Message[] messages, Object[] delta, Bot bot, ChangeMode mode) {
                for (Message message : messages) {
                    MessageChannel channel = Util.bindMessageChannel(bot, message.getChannel());
                    if (channel != null) {
                        if (channel.getType() == ChannelType.PRIVATE) {
                            if (message.getAuthor().getId().equalsIgnoreCase(bot.getJDA().getSelfUser().getId())) {
                                if (Util.botIsConnected(bot, message.getJDA())) {
                                    message.delete().queue();
                                } else {
                                    channel.getMessageById(message.getId()).queue(m -> m.delete().queue());
                                }
                            } else {
                                Vixio.getErrorHandler().warn("Vixio attempted to delete a message sent by another user in DM but that is impossible.");
                            }
                        } else if (channel.getType() == ChannelType.TEXT) {
                            try {
                                if (Util.botIsConnected(bot, message.getJDA())) {
                                    message.delete().queue();
                                } else {
                                    channel.getMessageById(message.getId()).queue(m -> m.delete().queue());
                                }
                            } catch (PermissionException x) {
                                Vixio.getErrorHandler().needsPerm(bot, "delete message", x.getPermission().getName());
                            }
                        }
                    }
                }
            }

        }.documentation("Delete message",
                "Delete a %message% with a specific bot",
                "delete %message% with %bot/string%",
                "delete event-message with \"Jewel\""));

        new SimpleType<Avatar>(Avatar.class, "avatar", "avatars?") {

            @Override
            public Avatar parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(Avatar avatar, int arg1) {
                return avatar.getAvatar();
            }

            @Override
            public String toVariableNameString(Avatar avatar) {
                return avatar.getAvatar();
            }

        };

        EnumUtils<OnlineStatus> status = new EnumUtils<>(OnlineStatus.class, "online status");
        new SimpleType<OnlineStatus>(OnlineStatus.class, "onlinestatus", "onlinestatus") {

            @Override
            public OnlineStatus parse(String s, ParseContext pc) {
                return status.parse(s);
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return true;
            }

            @Override
            public String toString(OnlineStatus onlineStatus, int flags) {
                return status.toString(onlineStatus, flags);
            }

            @Override
            public String toVariableNameString(OnlineStatus onlineStatus) {
                return onlineStatus.toString();
            }

        };

        new SimpleType<Emote>(Emote.class, "emote", "emotes?") {

            @Override
            public Emote parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(Emote emoji, int arg1) {
                return emoji.getAsMention();
            }

            @Override
            public String toVariableNameString(Emote emoji) {
                return emoji.getAsMention();
            }

        };


        new SimpleType<TextChannel>(TextChannel.class, "textchannel", "textchannels?") {

            @Override
            public TextChannel parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(TextChannel textChannel, int arg1) {
                return textChannel.getName();
            }

            @Override
            public String toVariableNameString(TextChannel textChannel) {
                return textChannel.getId();
            }

        };

        new SimpleType<Guild>(Guild.class, "guild", "guilds?") {

            @Override
            public Guild parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(Guild guild, int arg1) {
                return guild.getName();
            }

            @Override
            public String toVariableNameString(Guild guild) {
                return guild.getId();
            }

        };

        new SimpleType<VoiceChannel>(VoiceChannel.class, "voicechannel", "voicechannels?") {

            @Override
            public VoiceChannel parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(VoiceChannel voiceChannel, int arg1) {
                return voiceChannel.getName();
            }

            @Override
            public String toVariableNameString(VoiceChannel voiceChannel) {
                return voiceChannel.getId();
            }

        };

        new SimpleType<ChannelBuilder>(ChannelBuilder.class, "channelbuilder", "channelbuilders?") {

            @Override
            public ChannelBuilder parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(ChannelBuilder channelBuilder, int arg1) {
                return channelBuilder.getName();
            }

            @Override
            public String toVariableNameString(ChannelBuilder channelBuilder) {
                return channelBuilder.getName();
            }

        };

        new SimpleType<Bot>(Bot.class, "bot", "(discord )?bots?") {

            @Override
            public Bot parse(String s, ParseContext pc) {
                return Util.botFrom(s);
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return true;
            }

            @Override
            public String toString(Bot bot, int arg1) {
                return bot.getName();
            }

            @Override
            public String toVariableNameString(Bot bot) {
                return bot.getName();
            }

        };

        new SimpleType<User>(User.class, "user", "users?") {

            @Override
            public User parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(User user, int arg1) {
                return user.getName();
            }

            @Override
            public String toVariableNameString(User user) {
                return user.getId();
            }

        };

        new SimpleType<Category>(Category.class, "category", "categories?") {

            @Override
            public Category parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(Category category, int arg1) {
                return category.getName();
            }

            @Override
            public String toVariableNameString(Category category) {
                return category.getId();
            }

        };

        new SimpleType<Role>(Role.class, "role", "roles?") {

            @Override
            public Role parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(Role role, int arg1) {
                return role.getName();
            }

            @Override
            public String toVariableNameString(Role role) {
                return role.getId();
            }

        };

        new SimpleType<Member>(Member.class, "member", "members?") {

            @Override
            public Member parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(Member member, int arg1) {
                return member.getUser().getName();
            }

            @Override
            public String toVariableNameString(Member member) {
                return member.getUser().getId();
            }

        };

        new SimpleType<MessageBuilder>(MessageBuilder.class, "messagebuilder", "message ? builders?") {
            public MessageBuilder parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(MessageBuilder builder, int arg1) {
                return builder.isEmpty() ? null : builder.build().getContentRaw();
            }

            @Override
            public String toVariableNameString(MessageBuilder builder) {
                return builder.isEmpty() ? null : builder.build().getContentRaw();

            }
        };

        new SimpleType<EmbedBuilder>(EmbedBuilder.class, "embedbuilder", "embed ?(builder)?s?") {
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
        }.changer(new Changer<EmbedBuilder>() {
            @Override
            public Class<?>[] acceptChange(ChangeMode mode) {
                return mode != ChangeMode.ADD ? null : new Class[]{MessageEmbed.Field.class};
            }

            @Override
            public void change(EmbedBuilder[] what, Object[] delta, ChangeMode mode) {
                for (EmbedBuilder builder : what) {
                    for (Object field : delta) {
                        builder.addField((MessageEmbed.Field) field);
                    }
                }
            }
        });

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

        new SimpleType<AudioTrack>(AudioTrack.class, "track", "tracks?") {

            @Override
            public AudioTrack parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(AudioTrack track, int arg1) {
                return track.getInfo().title;
            }

            @Override
            public String toVariableNameString(AudioTrack track) {
                return track.getInfo().title;
            }

        };

        new SimpleType<DiscordCommand>(DiscordCommand.class, "discordcommand", "discord ?commands?") {

            @Override
            public DiscordCommand parse(String s, ParseContext pc) {
                return DiscordCommandFactory.getInstance().commandMap.get(s);
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return true;
            }

            @Override
            public String toString(DiscordCommand cmd, int arg1) {
                return cmd.getName();
            }

            @Override
            public String toVariableNameString(DiscordCommand cmd) {
                return cmd.getName();
            }

        };

        new SimpleType<MessageChannel>(MessageChannel.class, "messagechannel", "message ?channels?") {

            @Override
            public MessageChannel parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(MessageChannel channel, int arg1) {
                return channel.getName();
            }

            @Override
            public String toVariableNameString(MessageChannel channel) {
                return channel.getId();
            }

        };

        new SimpleType<ChannelType>(ChannelType.class, "channeltype", "channel ? types?") {

            @Override
            public ChannelType parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(ChannelType type, int arg1) {
                return type.name();
            }

            @Override
            public String toVariableNameString(ChannelType type) {
                return type.name();
            }

        };

        final EnumMapper<Permission> PERMISSION_PARSER = new EnumMapper<>(Permission.class);

        new SimpleType<Permission>(Permission.class, "permission", "permissions?") {

            @Override
            public String toString(Permission permisson, int flags) {
                return PERMISSION_PARSER.toString(permisson, flags);
            }

            @Override
            public Permission parse(String str, ParseContext pc) {
                return PERMISSION_PARSER.parse(str);
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return true;
            }

        };

    }
}
