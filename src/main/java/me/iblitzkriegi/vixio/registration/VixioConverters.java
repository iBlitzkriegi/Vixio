package me.iblitzkriegi.vixio.registration;

import ch.njol.skript.classes.Converter;
import ch.njol.skript.registrations.Converters;
import ch.njol.skript.util.Color;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.ISnowflake;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;

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
    }
}
