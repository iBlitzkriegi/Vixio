package me.iblitzkriegi.vixio.registration;

import ch.njol.skript.lang.ParseContext;
import me.iblitzkriegi.vixio.util.SimpleType;
import me.iblitzkriegi.vixio.util.Title;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.*;

public class VixioTypes {
    public static void register(){
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
                return bot.getSelfUser().getId();
            }

            @Override
            public String toVariableNameString(Bot bot) {
                return bot.getSelfUser().getId();
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
                return member.getUser().getId();
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
    }
}
