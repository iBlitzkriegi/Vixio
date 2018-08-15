package me.iblitzkriegi.vixio.expressions.member;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;

public class ExprVoiceChannel extends SimplePropertyExpression<Member, VoiceChannel> {
    static {
        Vixio.getInstance().registerExpression(ExprVoiceChannel.class, VoiceChannel.class, ExpressionType.PROPERTY,
                "[the] [current] voice[(-| )]channel of %members%", "%members%'[s] [current] voice[(-| )]channel")
                .setName("Voice Channel of Member")
                .setDesc("Get the voice channel a member is in if they are in one.")
                .setExample("join voice channel of event-member");
    }

    @Override
    protected String getPropertyName() {
        return "[current] voice[(-| )] channel";
    }

    @Override
    public VoiceChannel convert(Member member) {
        return member.getVoiceState().getChannel();
    }

    @Override
    public Class<? extends VoiceChannel> getReturnType() {
        return VoiceChannel.class;
    }
}
