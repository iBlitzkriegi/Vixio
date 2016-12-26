package me.iblitzkriegi.vixio.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import me.iblitzkriegi.vixio.registration.ExprAnnotation;
import org.bukkit.event.Event;

/**
 * Created by Blitz on 12/21/2016.
 */
@ExprAnnotation.Expression(returntype = String.class, type = ExpressionType.SIMPLE, syntax = "[discord] cleverbot response for %string%")
public class ExprChatterbotResponse extends SimpleExpression<String> {
    Expression<String> vText;
    @Override
    protected String[] get(Event e) {
        return new String[]{getResponse(e)};
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
    public String toString(Event event, boolean b) {
        return getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        vText = (Expression<String>) expr[0];
        return true;
    }
    private String getResponse(Event e){
        ChatterBotFactory factory = new ChatterBotFactory();
        ChatterBot bot1 = null;
        try {
            bot1 = factory.create(ChatterBotType.CLEVERBOT);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        ChatterBotSession bot1session = bot1.createSession();
        String msg = vText.getSingle(e);
        try {
            msg = bot1session.think(msg);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return msg;
    }
}
