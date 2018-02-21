package me.iblitzkriegi.vixio.registration;

import ch.njol.skript.classes.Converter;
import ch.njol.skript.registrations.Converters;
import ch.njol.skript.util.Color;
import me.iblitzkriegi.vixio.commands.DiscordCommand;
import me.iblitzkriegi.vixio.util.wrapper.Avatar;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import me.iblitzkriegi.vixio.util.wrapper.Emoji;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.ISnowflake;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

public class VixioConverters {
    public static void register(){
        Converters.registerConverter(ch.njol.skript.util.Color.class, java.awt.Color.class, (Converter<Color, java.awt.Color>) color -> {
            org.bukkit.Color bukkitColor = color.getBukkitColor();
            return new java.awt.Color(bukkitColor.getRed(), bukkitColor.getGreen(), bukkitColor.getBlue());
        });
        Converters.registerConverter(EmbedBuilder.class, MessageEmbed.class,
                (Converter<EmbedBuilder, MessageEmbed>) e -> e.isEmpty() ? null : e.build()
        );
        Converters.registerConverter(MessageBuilder.class, Message.class, (Converter<MessageBuilder, Message>) builder -> builder.isEmpty() ? null : builder.build());
        Converters.registerConverter(ISnowflake.class, String.class, (Converter<ISnowflake, String>) u -> u.getId());
        Converters.registerConverter(Bot.class, String.class, (Converter<Bot, String>) u -> u.getSelfUser().getId());
        Converters.registerConverter(Member.class, String.class, (Converter<Member, String>) u -> u.getUser().getId());
        Converters.registerConverter(Member.class, User.class, (Converter<Member, User>) u -> u.getUser());
        Converters.registerConverter(Avatar.class, String.class, (Converter<Avatar, String>) u -> u.getAvatar());
        Converters.registerConverter(DiscordCommand.class, String.class, (Converter<DiscordCommand, String>) c -> c.getName());
        Converters.registerConverter(Emoji.class, String.class, (Converter<Emoji, String>) u -> u.isEmote() ? u.getEmote().getAsMention() : u.getName());
        Converters.registerConverter(Category.class, String.class, (Converter<Category, String>) u -> u.getName());
        Converters.registerConverter(EmbedBuilder.class, Message.class, new Converter<EmbedBuilder, Message>() {
            @Override
            public Message convert(EmbedBuilder embedBuilder) {
                return embedBuilder.isEmpty() ? null : new MessageBuilder().setEmbed(embedBuilder.build()).build();
            }
        });
    }
}
