package me.iblitzkriegi.vixio.expressions.retrieve.channel;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.bot.sharding.ShardManager;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.VoiceChannel;
import org.bukkit.event.Event;

import java.util.Set;

public class ExprVoiceChannelWithId extends SimpleExpression<VoiceChannel> {
    static {
        Vixio.getInstance().registerExpression(ExprVoiceChannelWithId.class, VoiceChannel.class, ExpressionType.SIMPLE,
                "voice(-| )channel with id %string% [in %-guild%]")
                .setName("Voice Channel with ID")
                .setDesc("Get a Voice channel by it's ID, can include the Guild it is in for faster results.")
                .setExample("discord command topic <string> <string>:",
                        "\tusage: topic <channel id> <new topic>",
                        "\tprefixes: $",
                        "\ttrigger:",
                        "\t\tset name of voice channel with id arg-1 to arg-2 with event-bot"
                );
    }

    private Expression<String> id;
    private Expression<Guild> guild;

    @Override
    protected VoiceChannel[] get(Event e) {
        String id = this.id.getSingle(e);
        if (id == null || id.isEmpty()) {
            return null;
        }
        if (guild != null) {
            Guild guild = this.guild.getSingle(e);
            VoiceChannel voiceChannel = guild.getVoiceChannelById(id);
            if (voiceChannel != null) {
                return new VoiceChannel[]{voiceChannel};
            }

            return null;
        }

        Set<ShardManager> jdaInstances = Vixio.getInstance().botHashMap.keySet();
        if (jdaInstances.isEmpty()) {
            Vixio.getErrorHandler().warn("Vixio attempted to get a voice channel by ID but no Bots were logged in to do so.");
            return null;
        }
        for (ShardManager shardManager : jdaInstances) {
            for (JDA jda : shardManager.getShards()) {
                VoiceChannel voiceChannel = jda.getVoiceChannelById(id);
                if (voiceChannel != null) {
                    return new VoiceChannel[]{voiceChannel};
                }
            }
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends VoiceChannel> getReturnType() {
        return VoiceChannel.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "voice channel with id " + id.toString(e, debug) + (guild == null ? "" : " in " + guild.toString(e, debug));
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        id = (Expression<String>) exprs[0];
        guild = (Expression<Guild>) exprs[1];
        return true;
    }
}
