package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimpleExpression;
import me.iblitzkriegi.vixio.changers.EffChange;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import me.iblitzkriegi.vixio.util.wrapper.ChannelBuilder;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.managers.GuildController;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 7/25/2017.
 */
public class ExprDiscordNameOf extends ChangeableSimpleExpression<String> {
    static {
        Vixio.getInstance().registerExpression(ExprDiscordNameOf.class, String.class, ExpressionType.SIMPLE,
                "name of %channel/guild/user/bot/role/audiotrack/category/channelbuilder%")
                .setName("Name of")
                .setDesc("Get the name of something/someone. There is a set changer for channel, guild, and bot, category, channelbuilder.")
                .setExample("broadcast \"%name of event-user%\"");
    }

    private Expression<Object> object;

    @Override
    protected String[] get(Event e) {
        Object o = this.object.getSingle(e);

        if (o instanceof User) {
            return new String[]{((User) o).getName()};
        } else if (o instanceof Guild) {
            return new String[]{((Guild) o).getName()};
        } else if (o instanceof Channel) {
            return new String[]{((Channel) o).getName()};
        } else if (o instanceof Bot) {
            return new String[]{((Bot) o).getName()};
        } else if (o instanceof Role) {
            return new String[]{((Role) o).getName()};
        } else if (o instanceof ChannelBuilder) {
            return new String[]{((ChannelBuilder) o).getName()};
        } else if (o instanceof AudioTrack) {
            return new String[]{((AudioTrack) o).getInfo().title};
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "name of " + object.toString(e, debug);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        object = (Expression<Object>) exprs[0];
        return true;
    }

    @Override
    public Class<?>[] acceptChange(final Changer.ChangeMode mode, boolean vixioChanger) {
        if ((mode == Changer.ChangeMode.SET && object.isSingle())) {
            return new Class[]{String.class};
        }
        return null;
    }

    @Override
    public void change(final Event e, final Object[] delta, Bot bot, final Changer.ChangeMode mode) {
        Object object = this.object.getSingle(e);

        String name = (String) delta[0];
        switch (mode) {
            case SET:
                if (object instanceof Bot) {
                    bot.getSelfUser().getManager().setName(name).queue();
                } else if (object instanceof Channel) {
                    Channel channel = Util.bindChannel(bot, (Channel) object);
                    try {
                        channel.getManager().setName(name).queue();
                    } catch (PermissionException x) {
                        Vixio.getErrorHandler().needsPerm(bot, EffChange.format(mode, "name of", this.object, bot),
                                x.getPermission().getName());
                    }
                } else if (object instanceof Guild) {
                    Guild guild = Util.bindGuild(bot, (Guild) object);
                    if (guild == null) {
                        return;
                    }

                    try {
                        guild.getManager().setName(name).queue();
                    } catch (PermissionException x) {
                        Vixio.getErrorHandler().needsPerm(bot, EffChange.format(mode,"name of", this.object, bot),
                                x.getPermission().getName());
                    }
                } else if (object instanceof ChannelBuilder) {
                    ((ChannelBuilder) object).setName(name);
                }
        }
    }
}

