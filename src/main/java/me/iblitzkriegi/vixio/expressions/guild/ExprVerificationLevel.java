package me.iblitzkriegi.vixio.expressions.guild;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimplePropertyExpression;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.exceptions.PermissionException;
import org.bukkit.event.Event;

public class ExprVerificationLevel extends ChangeableSimplePropertyExpression<Guild, Guild.VerificationLevel> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprVerificationLevel.class, Guild.VerificationLevel.class,
                "discord verification level", "guilds")
                .setName("Discord Verification level of Guild")
                .setDesc("Get a Guild's verification level. This can be reset and set.")
                .setExample("broadcast \"%discord verification level of event-guild%\"");
    }

    @Override
    protected String getPropertyName() {
        return "discord verification level";
    }

    @Override
    public Guild.VerificationLevel convert(Guild guild) {
        return guild.getVerificationLevel();
    }

    @Override
    public Class<? extends Guild.VerificationLevel> getReturnType() {
        return Guild.VerificationLevel.class;
    }

    @Override
    public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelayed, final SkriptParser.ParseResult parseResult) {
        setExpr((Expression<? extends Guild>) exprs[0]);
        return true;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode, boolean vixioChanger) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.REMOVE || mode == Changer.ChangeMode.RESET) {
            return new Class[]{Guild.VerificationLevel.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Bot bot, Changer.ChangeMode mode) {
        switch (mode) {
            case RESET:
                for (Guild guild : getExpr().getAll(e)) {
                    Guild boundGuild = Util.bindGuild(bot, guild);
                    if (boundGuild != null) {
                        try {
                            boundGuild.getManager().setVerificationLevel(Guild.VerificationLevel.NONE).queue();
                        } catch (PermissionException x) {
                            Vixio.getErrorHandler().needsPerm(bot, "reset guild verification level", x.getPermission().getName());
                        }
                    }
                }
                break;
            case SET:
                for (Guild guild : getExpr().getAll(e)) {
                    Guild boundGuild = Util.bindGuild(bot, guild);
                    if (boundGuild != null) {
                        try {
                            boundGuild.getManager().setVerificationLevel((Guild.VerificationLevel) delta[0]).queue();
                        } catch (PermissionException x) {
                            Vixio.getErrorHandler().needsPerm(bot, "set verification level", x.getPermission().getName());
                        }
                    }
                }
        }
    }

}
