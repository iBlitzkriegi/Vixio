package me.iblitzkriegi.vixio.expressions.guild.invite;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.Invite;
import net.dv8tion.jda.api.entities.GuildChannel;

public class ExprChannelOf extends SimplePropertyExpression<Invite, GuildChannel> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprChannelOf.class, GuildChannel.class,
                "[discord] channel", "invite")
                .setName("Channel of")
                .setDesc("Get the channel an invite was created for.")
                .setExample(
                        "discord command parse <text>:",
                        "\tprefixes: /",
                        "\ttrigger:",
                        "\t\tretrieve the invites of event-guild ",
                        "\t\tset {in::*} to last grabbed invites",
                        "\t\tloop {in::*}:",
                        "\t\t\tif \"%loop-value%\" contains arg-1:",
                        "\t\t\t\tset {_} to loop-value",
                        "\t\tmake embed and send it to event-channel:",
                        "\t\t\tset the title of the embed to title with text \"Parsing results for invite: %invite url of {_}%\"",
                        "\t\t\tset the colour of the embed to Cyan ",
                        "\t\t\tset the thumbnail of embed to \"https://cdn.discordapp.com/icons/236641445363056651/e51b2c2f4d539f7c18ae966d60992d25.png\"",
                        "\t\t\tadd field named \"Max Uses\" with value \"%max uses of {_}%\" to embed ",
                        "\t\t\tadd field named \"Max Age (In Seconds)\" with value \"%max age of {_}%\" to embed",
                        "\t\t\tadd field named \"Time Created\" with value \"%creation date of {_}%\" to embed",
                        "\t\t\tadd field named \"Guild\" with value \"%guild of {_}%\" to embed",
                        "\t\t\tadd field named \"Channel\" with value \"%channel of {_}%\" to embed"
                );
    }

    @Override
    protected String getPropertyName() {
        return "[discord] channel";
    }

    @Override
    public GuildChannel convert(Invite invite) {
        return invite.getChannel();
    }

    @Override
    public Class<? extends GuildChannel> getReturnType() {
        return GuildChannel.class;
    }
}
