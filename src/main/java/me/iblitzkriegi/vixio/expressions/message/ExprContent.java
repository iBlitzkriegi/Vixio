package me.iblitzkriegi.vixio.expressions.message;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.changers.ChangeableSimplePropertyExpression;
import me.iblitzkriegi.vixio.util.UpdatingMessage;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.skript.EasyMultiple;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.PermissionException;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 8/19/2017.
 */
public class ExprContent extends ChangeableSimplePropertyExpression<UpdatingMessage, String> implements EasyMultiple<UpdatingMessage, String> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprContent.class, String.class,
                "[<stripped|display(able)?>] content", "messages")
                .setName("Content of Message")
                .setDesc("Get the content of a Message. The content can be set and deleted.")
                .setExample("content of event-message");
    }

    private boolean hasModifiers;
    private boolean stripped;

    @Override
    public String convert(UpdatingMessage msg) {
        Message message = UpdatingMessage.convert(msg);
        if (hasModifiers) {
            return stripped ? message.getContentStripped() : message.getContentDisplay();
        }
        return message.getContentRaw();
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode, boolean vixioChanger) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE)
            return new Class[]{
                    String.class,
                    Message.class
            };

        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        setExpr((Expression<UpdatingMessage>) exprs[0]);
        hasModifiers = parseResult.regexes.size() == 1;
        stripped = hasModifiers && parseResult.regexes.get(0).group().equals("stripped");
        return true;
    }

    @Override
    public void change(Event e, Object[] delta, Bot bot, Changer.ChangeMode mode) throws UnsupportedOperationException {
        //TODO: also needs a message bind
        change(getExpr().getAll(e), msg -> {
            Message message = UpdatingMessage.convert(msg);
            String content = mode == Changer.ChangeMode.SET ? (String) delta[0] : EmbedBuilder.ZERO_WIDTH_SPACE;
            if (message == null || !message.getAuthor().getId().equals(bot.getJDA().getSelfUser().getId())) {
                return;
            }

            if (Util.botIsConnected(bot, message.getJDA())) {
                try {
                    message.editMessage(content).queue();
                } catch (PermissionException x) {
                    Vixio.getErrorHandler().needsPerm(bot, "edit message", x.getPermission().getName());
                }
                return;
            }

            TextChannel boundChannel = Util.bindChannel(bot, message.getTextChannel());
            if (boundChannel == null) {
                return;
            }

            try {
                boundChannel.retrieveMessageById(message.getId()).queue(message1 -> message1.editMessage(content).queue());
            } catch (PermissionException x) {
                Vixio.getErrorHandler().needsPerm(bot, "edit message", x.getPermission().getName());
            }
        });
    }

    @Override
    protected String getPropertyName() {
        return "content";
    }
}
