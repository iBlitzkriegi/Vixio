package me.iblitzkriegi.vixio.effects.guild;

import ch.njol.skript.lang.*;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.skript.AsyncEffect;
import me.iblitzkriegi.vixio.util.skript.SkriptUtil;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.exceptions.PermissionException;
import org.bukkit.event.Event;

public class EffCloneChannel extends AsyncEffect {

    static {
        Vixio.getInstance().registerEffect(EffCloneChannel.class,
                "clone %channel% [in[to] %guild%] [with [the] [new] name %-string%] [(with|as) %bot/string%] [and store (it|the channel) in %-objects%]")
                .setName("Clone Channel")
                .setDesc("Clone a existing channel into either a new guild or the current guild under a new name.")
                .setExample("clone event-channel with the new name \"Rawr!\"");
    }

    private Expression<GuildChannel> channel;
    private Expression<Guild> guild;
    private Expression<String> name;
    private Expression<Object> bot;
    private Variable<?> varExpr;
    private VariableString varName;

    @Override
    protected void execute(Event e) {
        Guild guild = this.guild.getSingle(e);
        GuildChannel channel = this.channel.getSingle(e);
        String name = this.name == null ? null : this.name.getSingle(e);
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        if (bot == null || guild == null || channel == null) {
            return;
        }
        GuildChannel boundChannel = Util.bindChannel(bot, channel);
        if (name == null) {
            name = boundChannel.getName();
        } else {
            if (boundChannel.getType() == ChannelType.TEXT) {
                name.replaceAll(" ", "-");
            }
        }
        Guild boundGuild = Util.bindGuild(bot, guild);
        if (boundChannel == null || boundGuild == null) {
            return;
        }
        try {
            if (varExpr == null) {
                boundChannel.createCopy(boundGuild).setName(name).queue();
            } else {
                GuildChannel copiedChannel = boundChannel.createCopy(boundGuild).setName(name).complete();
                Util.storeInVar(varName, varExpr, copiedChannel, e);
            }
        } catch (PermissionException x) {
            Vixio.getErrorHandler().needsPerm(bot, "copy channel ", x.getPermission().getName());
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "clone " + channel.toString(e, debug) + " to " + guild.toString(e, debug) + (name == null ? "" : " with name " + name.toString(e, debug)) + " with " + bot.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        channel = (Expression<GuildChannel>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        name = (Expression<String>) exprs[2];
        bot = (Expression<Object>) exprs[3];
        if (exprs[4] instanceof Variable) {
            varExpr = (Variable<?>) exprs[4];
            varName = SkriptUtil.getVariableName(varExpr);
        }
        return true;
    }
}
