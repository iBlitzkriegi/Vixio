package me.iblitzkriegi.vixio.effects.guild;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import me.iblitzkriegi.vixio.util.wrapper.ChannelBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.bukkit.event.Event;

public class EffCreateChannelBuilder extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffCreateChannelBuilder.class,
                "create %channelbuilder% in %guild% [(with|as) %bot/string%]")
                .setName("Create channel builder")
                .setDesc("Create a channel created with the create channel scope")
                .setExample("command /channel:" +
                        "\ttrigger:" +
                        "\t\tcreate text channel:" +
                        "\t\t\tset name of the channel to \"Testingxdxd\"" +
                        "\t\t\tset {guild} to guild with id \"219967335266648065\"" +
                        "\t\t\tset nsfw state of the channel as \"Jewel\" to true" +
                        "\t\t\tset topic of the channel as \"Jewel\" to \"Jewel testing\"" +
                        "\t\t\tset parent of the channel to category named \"xd\" in {guild}" +
                        "\t\t\tcreate the channel in {guild} as \"Jewel\"");
    }

    private Expression<ChannelBuilder> channelBuilder;
    private Expression<Guild> guild;
    private Expression<Object> bot;

    @Override
    protected void execute(Event e) {
        ChannelBuilder channelBuilder = this.channelBuilder.getSingle(e);
        Guild guild = this.guild.getSingle(e);
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Guild bindedGuild = Util.bindGuild(bot, guild);
        if (bot == null || guild == null || channelBuilder == null || bindedGuild == null) {
            return;
        }

        try {
            if (channelBuilder.getType() == ChannelType.TEXT) {
                bindedGuild.getController().createTextChannel(channelBuilder.getName())
                        .setParent(channelBuilder.getParent())
                        .setNSFW(channelBuilder.isNSFW())
                        .setTopic(channelBuilder.getTopic()).queue();
            } else {
                bindedGuild.getController().createVoiceChannel(channelBuilder.getName())
                        .setParent(channelBuilder.getParent())
                        .setBitrate(channelBuilder.getBitRate() * 1000)
                        .setUserlimit(channelBuilder.getUserLimit()).queue();
            }
        } catch (PermissionException x) {

        }

    }

    @Override
    public String toString(Event e, boolean debug) {
        return "create " + channelBuilder.toString(e, debug) + " in " + guild.toString(e, debug) + " as " + bot.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        channelBuilder = (Expression<ChannelBuilder>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        bot = (Expression<Object>) exprs[2];
        return true;
    }
}
