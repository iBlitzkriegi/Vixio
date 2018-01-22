package me.iblitzkriegi.vixio.expressions.channel.builder;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import me.iblitzkriegi.vixio.util.wrapper.ChannelBuilder;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class ExprNsfwState extends SimpleExpression<Boolean> {
    static {
        Vixio.getInstance().registerExpression(ExprNsfwState.class, Boolean.class, ExpressionType.SIMPLE,
                "[the] nsfw state of %channels/channelbuilders% [(with|as) %bot/string%]")
                .setName("NSFW State")
                .setDesc("Get the Not Safe For Work state of a text channel, this is set to false by default. Has SET and RESET changers")
                .setExample(
                        "command /channel:",
                        "\ttrigger:",
                        "\t\tcreate text channel:",
                        "\t\tset name of the channel to \"Testing\"",
                        "\t\tset {guild} to guild with id \"56156156615611\"",
                        "\t\tset nsfw state of this channel as \"Jewel\" to true",
                        "\t\tcreate the channel in {guild} as \"Jewel\""
                );
    }
    private Expression<Object> bot;
    private Expression<Object> channels;

    @Override
    protected Boolean[] get(Event e) {
        Object[] channels = this.channels.getAll(e);
        if (channels == null) {
            return null;
        }
        List<Boolean> nsfwStates = new ArrayList<>();
        for(Object object : channels) {
            if (object instanceof Channel && ((Channel) object).getType() == ChannelType.TEXT) {
                TextChannel channel = (TextChannel) object;
                nsfwStates.add(channel.isNSFW());
            } else if (object instanceof ChannelBuilder) {
                nsfwStates.add(((ChannelBuilder) object).isNSFW());
            }
        }
        return nsfwStates.toArray(new Boolean[nsfwStates.size()]);
    }

    @Override
    public boolean isSingle() {
        return channels.isSingle();
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "nsfw state of " + channels.toString(e, debug) + (bot != null ? " as " + bot.toString() : "");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        channels = (Expression<Object>) exprs[0];
        bot = (Expression<Object>) exprs[1];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET)
            return new Class[] {Boolean.class};
        return null;
    }

    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) {
        Boolean isNsfw = mode == Changer.ChangeMode.SET ? (Boolean) delta[0] : false;
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Object[] channels = this.channels.getAll(e);
        if (bot == null || channels == null) {
            return;
        }
        for(Object object : channels) {
            if (object instanceof Channel && ((Channel) object).getType() == ChannelType.TEXT) {
                TextChannel channel = (TextChannel) object;
                try {
                    if (Util.botIsConnected(bot, channel.getJDA())) {
                        channel.getManager().setNSFW(isNsfw).queue();
                    } else {
                        TextChannel bindingChannel = bot.getJDA().getTextChannelById(channel.getId());
                        if (bindingChannel != null) {
                            bindingChannel.getManager().setNSFW(isNsfw).queue();
                        }
                    }
                }catch (PermissionException x) {
                    Vixio.getErrorHandler().needsPerm(bot, "set nsfw state", x.getPermission().getName());
                }
            } else if (object instanceof ChannelBuilder) {
                ((ChannelBuilder) object).setNSFW(isNsfw);
            }
        }
    }
}
