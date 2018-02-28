package me.iblitzkriegi.vixio.registration;

import ch.njol.skript.lang.ParseContext;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.changers.VixioChanger;
import me.iblitzkriegi.vixio.commands.DiscordCommand;
import me.iblitzkriegi.vixio.commands.DiscordCommandFactory;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.embed.Title;
import me.iblitzkriegi.vixio.util.skript.SimpleType;
import me.iblitzkriegi.vixio.util.wrapper.Avatar;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import me.iblitzkriegi.vixio.util.wrapper.ChannelBuilder;
import me.iblitzkriegi.vixio.util.wrapper.Emoji;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
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

public class Types {
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
                return channel.getId();
            }

            @Override
            public String toVariableNameString(Channel channel) {
                return channel.getId();
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
                return message.getId();
            }

            @Override
            public String toVariableNameString(Message message) {
                return message.getId();
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
                // TODO - support for generic message channels (mainly for dm usage)
                for (Message message : messages) {
                    TextChannel channel = Util.bindChannel(bot, (TextChannel) message.getChannel());
                    channel.getMessageById(message.getId()).queue(m -> m.delete().queue());
                }
            }
        });

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

        new SimpleType<Emoji>(Emoji.class, "emoji", "emojis?") {

            @Override
            public Emoji parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
            }

            @Override
            public String toString(Emoji emoji, int arg1) {
                return emoji.isEmote() ? emoji.getEmote().getAsMention() : emoji.getName();
            }

            @Override
            public String toVariableNameString(Emoji emoji) {
                return emoji.isEmote() ? emoji.getEmote().getAsMention() : emoji.getName();
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
                return textChannel.getId();
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
                return guild.getId();
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
                return voiceChannel.getId();
            }

            @Override
            public String toVariableNameString(VoiceChannel voiceChannel) {
                return voiceChannel.getId();
            }

        };

        new SimpleType<ChannelBuilder>(ChannelBuilder.class, "channelbuilder", "channelbuilder") {

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

        new SimpleType<Bot>(Bot.class, "bot", "bot?") {

            @Override
            public Bot parse(String s, ParseContext pc) {
                return null;
            }

            @Override
            public boolean canParse(ParseContext pc) {
                return false;
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
                return user.getId();
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
                return category.getName();
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
                return role.getName();
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
                return member.getNickname();
            }

            @Override
            public String toVariableNameString(Member member) {
                return member.getNickname();
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

        new SimpleType<AudioTrack>(AudioTrack.class, "audiotrack", "audio ?tracks?") {

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
                return channel.getName();
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

    }
}
