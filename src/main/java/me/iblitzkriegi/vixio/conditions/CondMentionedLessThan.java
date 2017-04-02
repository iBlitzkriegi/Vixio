package me.iblitzkriegi.vixio.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.registration.CondAnnotation;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 2/11/2017.
 */
@CondAnnotation.Condition(
        name = "MessageHasLessThanMentioned",
        title = "Message has less than Mentioned users",
        desc = "Check if a message has less than a certain amount of mentions",
        syntax = "size of mentioned users in message %message% is less than %number%",
        example = "on guild message received seen by \\\"Rawr\\\":\\n" +
        "\\tset {_args::*} to event-string split at \\\" \\\"\\n" +
        "\\tset {_command} to {_args::1}\\n" +
        "\\tremove {_args::1} from {_args::*}\\n" +
        "\\tif {_command} starts with \\\"$info\\\":\\n" +
        "\\t\\treply with \\\"hi\\\"\\n" +
        "\\t\\tif message event-message contains mention:\\n" +
        "\\t\\t\\tif size of mentioned users in message event-message is less than 2:\\n" +
        "\\t\\t\\t\\tset {_isbot} to false\\n" +
        "\\t\\t\\t\\tset {num} to 0\\n" +
        "\\t\\t\\t\\tset {_var::*} to event-mentioned\\n" +
        "\\t\\t\\t\\tloop {_var::*}:\\n" +
        "\\t\\t\\t\\t\\tif {num} = 0:\\n" +
        "\\t\\t\\t\\t\\t\\tif user loop-value is bot:\\n" +
        "\\t\\t\\t\\t\\t\\t\\tset {_isbot} to true\\n" +
        "\\t\\t\\t\\t\\t\\tmake codeblock \\\"Ӝ Username[->](%name of loop-value%):::Ӝ Nickname[->](%nickname of loop-value in event-guild%):::Ӝ ID[->](%id of loop-value%):::Ӝ Playing[->](%current game of user loop-value in guild event-guild%):::Ӝ FriendTag[->](%name of loop-value%##%discriminator of loop-value%):::Ӝ MentionTag[->](%mention tag of loop-value%):::Ӝ Status[->](%online status of loop-value in guild event-guild%):::Ӝ JoinedGuild[->](%join guild event-guild date of user loop-value%):::Ӝ JoinedDiscord[->](%join discord date of user loop-value%):::Ӝ IsBot[->](%{_isbot}%):::Ӝ AvatarUrl[->](%icon url of loop-value%)\\\" with lang \\\"md\\\" for event-channel with \\\"Rawr\\\"\\n" +
        "\\t\\t\\t\\t\\t\\texit\\n" +
        "\\t\\t\\telse:\\n" +
        "\\t\\t\\t\\treply with \\\"You may only specify one user at a time silly!\\\"\\n" +
        "\\t\\telse:\\n" +
        "\\t\\t\\treply with \\\"You must specify a user to get information on silly!\\\"")
public class CondMentionedLessThan extends Condition{
    Expression<net.dv8tion.jda.core.entities.Message> vMessage;
    Expression<Number> vNum;
    @Override
    public boolean check(Event event) {
        Number number = vMessage.getSingle(event).getMentionedUsers().size();
        Number number1 = vNum.getSingle(event);
        if(Integer.valueOf(String.valueOf(number)) < Integer.valueOf(String.valueOf(number1))){
            return true;
        }
        return false;
    }

    @Override
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vMessage = (Expression<net.dv8tion.jda.core.entities.Message>) expressions[0];
        vNum = (Expression<Number>) expressions[1];
        return true;
    }
}
