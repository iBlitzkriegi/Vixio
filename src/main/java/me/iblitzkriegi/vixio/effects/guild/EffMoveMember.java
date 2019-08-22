package me.iblitzkriegi.vixio.effects.guild;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.exceptions.PermissionException;
import org.bukkit.event.Event;

public class EffMoveMember extends Effect {
    static {
        Vixio.getInstance().registerEffect(EffMoveMember.class, "move %member% to %channel/voicechannel% [with %bot/string%]")
                .setName("Move Member to Voice Channel")
                .setDesc("Move a member to a different voice channel, they must already be in a voice channel to be moved.")
                .setUserFacing("move %member% to %voicechannel% [with %bot/string%]")
                .setExample(
                        "discord command $move <member> <string>:",
                        "\ttrigger:",
                        "\t\tset {_voiceChannel} to voice channel with id arg-2",
                        "\t\tif {_voiceChannel} is not set:",
                        "\t\t\treply with \"I could not find a voice channel by that id\"",
                        "\t\t\tstop",
                        "\t\tmove arg-1 to {_voiceChannel}"
                );
    }

    Expression<Object> bot;
    Expression<Member> member;
    Expression<GuildChannel> channel;

    @Override
    protected void execute(Event e) {
        Bot bot = Util.botFrom(this.bot.getSingle(e));
        Member member = this.member.getSingle(e);
        Object object = channel.getSingle(e);
        if (bot == null || member == null || (!(object instanceof VoiceChannel))) {
            return;
        }
        VoiceChannel voiceChannel = Util.bindVoiceChannel(bot, (VoiceChannel) object);
        if (voiceChannel == null || (!(member.getVoiceState().inVoiceChannel()))) {
            return;
        }
        try {
            voiceChannel.getGuild().moveVoiceMember(member, voiceChannel).queue();
        } catch (PermissionException x) {
            Vixio.getErrorHandler().needsPerm(bot, "move member to voice channel", x.getPermission().getName());
        }

    }

    @Override
    public String toString(Event e, boolean debug) {
        return "move " + member.toString(e, debug) + " to " + channel.toString(e, debug) + " with " + bot.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        member = (Expression<Member>) exprs[0];
        channel = (Expression<GuildChannel>) exprs[1];
        bot = (Expression<Object>) exprs[2];
        return true;
    }
}
