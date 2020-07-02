package me.iblitzkriegi.vixio.scopes;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;
import ch.njol.skript.registrations.EventValues;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.scope.EffectSection;
import me.iblitzkriegi.vixio.util.skript.SkriptUtil;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import org.bukkit.event.Event;

import java.util.Arrays;

public class ScopeMakeEmbed extends EffectSection {

    public static EmbedBuilder lastEmbed;
    public static Expression<Object> sources;
    public static Expression<Object> bot;

    static {
        Vixio.getInstance().registerCondition(ScopeMakeEmbed.class,
                "(make|create) (embed|embed %-embedbuilder%)",
                "(make|create) (embed|embed %-embedbuilder%) and send [(it|the embed)] to %users/channels% [(with|using) %bot/string%] [and store (it|the message) in %-objects%]",
                "(make|create) (embed|embed %-embedbuilder%) and reply with [(it|the embed)] [and store (it|the message) in %-objects%]")
                .setName("Make Embed")
                .setDesc("Provides a pretty and easy way of making a new embed with a bunch of different attributes")
                .setExample(
                        "command $scope:",
                        "\ttrigger:",
                        "\t\tmake a new embed:",
                        "\t\t\tset color of embed the embed to red",
                        "\t\t\tset url of the embed to \"https://google.com\"",
                        "\t\t\tset title of the embed to \"Google!\"",
                        "\t\tset {_embed} to last made embed"
                );
    }

    private Expression<EmbedBuilder> builder;
    private Variable<?> varExpr;
    private VariableString varName;
    private int matchedPattern;

    @Override
    public void execute(Event e) {
        EmbedBuilder embed = builder == null ? new EmbedBuilder() : builder.getSingle(e);
        lastEmbed = embed == null ? new EmbedBuilder() : embed;
        runSection(e);
        if (matchedPattern == 1) {
            Bot currentBot = Util.botFrom(bot.getSingle(e));
            if (currentBot == null) {
                return;
            }
            try {
                for (Object source : sources.getArray(e)) {
                    MessageChannel messageChannel = Util.getMessageChannel(currentBot, source);
                    if (messageChannel != null) {
                        if (varExpr == null) {
                            messageChannel.sendMessage(embed.build()).queue();
                        } else {
                            Message resultingMessage = messageChannel.sendMessage(embed.build()).complete(true);
                            if (resultingMessage != null) {
                                Util.storeInVar(varName, varExpr, UpdatingMessage.from(resultingMessage), e);
                            }
                        }
                    }
                }
            } catch (RateLimitedException e1) {
                Vixio.getErrorHandler().cantOpenPrivateChannel();
            } catch (PermissionException x) {
                Vixio.getErrorHandler().needsPerm(currentBot, "send embed", x.getPermission().getName());
            }
        } else if (matchedPattern == 2) {
            MessageChannel messageChannel = EventValues.getEventValue(e, MessageChannel.class, 0);
            Bot currentBot = EventValues.getEventValue(e, Bot.class, 0);
            if (currentBot == null | messageChannel == null) {
                return;
            }
            try {
                MessageChannel boundChannel = Util.bindChannel(currentBot, messageChannel);
                if (boundChannel == null) {
                    return;
                }
                if (varExpr == null) {
                    boundChannel.sendMessage(embed.build()).queue();
                } else {
                    Message resultingMessage = boundChannel.sendMessage(embed.build()).complete(true);
                    if (resultingMessage != null) {
                        Util.storeInVar(varName, varExpr, UpdatingMessage.from(resultingMessage), e);
                    }
                }
            } catch (PermissionException x) {
                Vixio.getErrorHandler().needsPerm(currentBot, "send embed", x.getPermission().getName());
            } catch (RateLimitedException x) {
                Vixio.getErrorHandler().warn("Vixio attempted to send a embed but got rate limited.");
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "make embed " + builder.toString(e, debug);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if (checkIfCondition())
            return false;
        if (!hasSection()) {
            Skript.error("An embed creation scope is useless without any content!");
            return false;
        }
        builder = (Expression<EmbedBuilder>) exprs[0];
        loadSection(true);
        matchedPattern = i;
        if (matchedPattern == 1) {
            sources = (Expression<Object>) exprs[1];
            bot = (Expression<Object>) exprs[2];
            if (exprs[3] instanceof Variable) {
                varExpr = (Variable<?>) exprs[3];
                varName = SkriptUtil.getVariableName(varExpr);
            }

        } else if (matchedPattern == 2) {
            if (ScriptLoader.getCurrentEvents() != null && Arrays.stream(ScriptLoader.getCurrentEvents())
                    .anyMatch(event -> EventValues.getEventValueGetter(event, MessageChannel.class, 0) != null) && Arrays.stream(ScriptLoader.getCurrentEvents())
                    .anyMatch(event -> EventValues.getEventValueGetter(event, Bot.class, 0) != null)) {
                if (exprs[1] instanceof Variable) {
                    varExpr = (Variable<?>) exprs[1];
                    varName = SkriptUtil.getVariableName(varExpr);
                }
            } else {
                Skript.error("You can't use reply with in events that do not have a channel and bot to reply with.");
                return false;
            }
        }

        return true;
    }
}
