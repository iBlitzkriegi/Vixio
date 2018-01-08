package me.iblitzkriegi.vixio.expressions.message;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 8/19/2017.
 */
public class ExprContentOfMessage extends SimpleExpression<String> {
    static {
        Vixio.getInstance().registerExpression(ExprContentOfMessage.class, String.class, ExpressionType.SIMPLE, "content of %message% [(with|as) %-bot%]")
            .setName("Content of message")
            .setDesc("Get the content of a message")
            .setExample("content of event-message");
    }
    Expression<Message> message;
    Expression<Bot> bot;
    @Override
    protected String[] get(Event event) {
        if(message.getSingle(event)!=null) {
            return new String[]{message.getSingle(event).getContent()};
        }else{
            Skript.error("You must provided a message, refer to the syntax");
            return null;
        }
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
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET)
            return new Class[] {String.class};
        return null;
    }

    @Override
    public void change(final Event e, final Object[] delta, final Changer.ChangeMode mode) throws UnsupportedOperationException {
        if(bot!=null||Vixio.getInstance().botHashMap.containsKey(bot)){
            if(message.getSingle(e) != null){
                switch (mode){
                    case SET:
                        JDA jda = bot.getSingle(e).getJDA();
                        TextChannel textChannel = jda.getTextChannelById(message.getSingle(e).getTextChannel().getId());
                        try{
                            String edit;
                            if(delta[0]!=null){
                                edit = String.valueOf(delta[0]);
                            }else{
                                Skript.error("You must include something to edit the message to");
                                return;
                            }
                            textChannel.getMessageById(message.getSingle(e).getId()).queue(message1 -> message1.editMessage(edit).queue());
                        }catch (IllegalStateException x){
                            Skript.error("You may only edit your own messages");
                        }

                }
            }else{
                Skript.error("You must provide a Message to be modified..");
            }
        }else{
            Skript.error("You must provide a bot in order to modify a message");
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "content of message " + message.toString(event, b);
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        message = (Expression<Message>) expressions[0];
        bot = (Expression<Bot>) expressions[1];
        return true;
    }
}
